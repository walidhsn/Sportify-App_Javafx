/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Reservation;
import sportify.edu.interfaces.EntityCRUD;
import sportify.edu.interfaces.IReservation_service;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author WALID
 */
public class ReservationService implements EntityCRUD<Reservation>, IReservation_service {

    @Override
    public void addEntity(Reservation t) {
        try {
            String rq = "INSERT INTO reservation (terrain_id, client_id, date_reservation, start_time, end_time, res_status, nb_person) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, t.getTerrain_id());
            pst.setInt(2, t.getClient_id());
            pst.setTimestamp(3, Timestamp.valueOf(t.getDateReservation()));
            pst.setTimestamp(4, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getEndTime()));
            pst.setBoolean(6, t.isResStatus());
            pst.setInt(7, t.getNbPerson());
            pst.executeUpdate();
            System.out.println("Reservation has been added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void updateEntity(Reservation t) {
        try {
            String rq = "UPDATE reservation SET terrain_id=?, client_id =? , date_reservation = ?, start_time =?, end_time =?, res_status =?, nb_person = ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, t.getTerrain_id());
            pst.setInt(2, t.getClient_id());
            pst.setTimestamp(3, Timestamp.valueOf(t.getDateReservation()));
            pst.setTimestamp(4, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getEndTime()));
            pst.setBoolean(6, t.isResStatus());
            pst.setInt(7, t.getNbPerson());
            pst.setInt(8, t.getId());
            pst.executeUpdate();
            System.out.println("Reservation has been updated.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM reservation where id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Reservation has been deleted.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> displayEntity() {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setTerrain_id(rs.getInt("terrain_id"));
                r.setClient_id(rs.getInt("client_id"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setResStatus(rs.getBoolean("res_status"));
                r.setNbPerson(rs.getInt("nb_person"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Reservation diplay(int id) {
        Reservation r = new Reservation();
        try {
            String rq = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setTerrain_id(rs.getInt("terrain_id"));
                r.setClient_id(rs.getInt("client_id"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setResStatus(rs.getBoolean("res_status"));
                r.setNbPerson(rs.getInt("nb_person"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public List<String> reserver(Reservation r) {
        List<String> result = new ArrayList<>();
        List<Reservation> conflictingReservations = findConflictingReservations(r);
        List<String> conflictingTimes = new ArrayList<>();
        boolean hasConfirmedConflict = false;
        if (!conflictingReservations.isEmpty()) {
            for (Reservation conflictingReservation : conflictingReservations) {
                if (conflictingReservation.isResStatus()) {
                    hasConfirmedConflict = true;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String startTime = conflictingReservation.getStartTime().format(formatter);
                    String endTime = conflictingReservation.getEndTime().format(formatter);
                    String conflictingTime = startTime + " - " + endTime;
                    conflictingTimes.add(conflictingTime);
                }
            }
        }
        if (hasConfirmedConflict) {
            String errorMessage = "Selected date and time range is not available due to a confirmed reservation conflict. ";
            if (!conflictingTimes.isEmpty()) {
                errorMessage = errorMessage + "Other conflicting reservations:";
                errorMessage = errorMessage + conflictingTimes.toString();
                result.add("not_ok");
                result.add(errorMessage);
            }
        } else {
            addEntity(r);
            result.add("ok");
            result.add("Added.");
        }
        return result;
    }

    @Override
    public List<String> reserver_update(Reservation r) {
        List<String> result = new ArrayList<>();
        List<Reservation> conflictingReservations = findConflictingReservations(r);
        List<String> conflictingTimes = new ArrayList<>();
        boolean hasConfirmedConflict = false;
        if (!conflictingReservations.isEmpty()) {
            for (Reservation conflictingReservation : conflictingReservations) {
                if (conflictingReservation.isResStatus()) {
                    hasConfirmedConflict = true;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String startTime = formatter.format(conflictingReservation.getStartTime());
                    String endTime = formatter.format(conflictingReservation.getEndTime());
                    String conflictingTime = startTime + " - " + endTime;
                    conflictingTimes.add(conflictingTime);
                }
            }
        }
        if (hasConfirmedConflict) {
            String errorMessage = "Selected date and time range is not available due to a confirmed reservation conflict. ";
            if (!conflictingTimes.isEmpty()) {
                errorMessage = errorMessage + "Other conflicting reservations:";
                errorMessage = errorMessage + conflictingTimes.toString();
                result.add("not_ok");
                result.add(errorMessage);
            }
        } else {
            updateEntity(r);
            result.add("ok");
            result.add("Updated.");
        }
        return result;
    }

    @Override
    public List<Reservation> myReservation(int client_id) {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation WHERE client_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, client_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setTerrain_id(rs.getInt("terrain_id"));
                r.setClient_id(rs.getInt("client_id"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setResStatus(rs.getBoolean("res_status"));
                r.setNbPerson(rs.getInt("nb_person"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public List<Reservation> terrain_reservations(int terrain_id) {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation WHERE terrain_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, terrain_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setTerrain_id(rs.getInt("terrain_id"));
                r.setClient_id(rs.getInt("client_id"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setResStatus(rs.getBoolean("res_status"));
                r.setNbPerson(rs.getInt("nb_person"));
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Reservation> findConflictingReservations(Reservation reservation) {
        List<Reservation> result = new ArrayList<>();
        String rq = "SELECT * FROM reservation "
                + "WHERE terrain_id = ? "
                + "AND start_time < ? "
                + "AND end_time > ?";

        if (reservation.getId() != 0) {
            rq += " AND id != ?";
        }
        PreparedStatement pst;
        try {
            pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, reservation.getTerrain_id());
            pst.setTimestamp(2, Timestamp.valueOf(reservation.getEndTime()));
            pst.setTimestamp(3, Timestamp.valueOf(reservation.getStartTime()));
            if (reservation.getId() != 0) {
                pst.setInt(4, reservation.getId());
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setTerrain_id(rs.getInt("terrain_id"));
                r.setClient_id(rs.getInt("client_id"));
                r.setDateReservation(rs.getTimestamp("date_reservation").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setResStatus(rs.getBoolean("res_status"));
                r.setNbPerson(rs.getInt("nb_person"));
                result.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public void add_equipment_reservation(int id_reservation, int id_equipment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete_equipment_reservation(int id_reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update_equipment_reservation(int id_reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
