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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Equipment;
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
            String rq = "INSERT INTO reservation (terrain_id, client_id, date_reservation, start_time, end_time, res_status, nb_person,updated_at) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, t.getTerrain_id());
            pst.setInt(2, t.getClient_id());
            pst.setTimestamp(3, Timestamp.valueOf(t.getDateReservation()));
            pst.setTimestamp(4, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getEndTime()));
            pst.setBoolean(6, t.isResStatus());
            pst.setInt(7, t.getNbPerson());
            pst.setTimestamp(8, Timestamp.valueOf(t.getUpdated_at()));
            pst.executeUpdate();
            System.out.println("Reservation has been added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void updateEntity(Reservation t) {
        try {
            String rq = "UPDATE reservation SET terrain_id=?, client_id =? , date_reservation = ?, start_time =?, end_time =?, res_status =?, nb_person = ?,updated_at = ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, t.getTerrain_id());
            pst.setInt(2, t.getClient_id());
            pst.setTimestamp(3, Timestamp.valueOf(t.getDateReservation()));
            pst.setTimestamp(4, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getEndTime()));
            pst.setBoolean(6, t.isResStatus());
            pst.setInt(7, t.getNbPerson());
            pst.setTimestamp(8, Timestamp.valueOf(t.getUpdated_at()));
            pst.setInt(9, t.getId());
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public List<String> reserver(Reservation r) {
        int last_id = -1;
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
            last_id = addEntity_1(r);
            result.add("ok");
            result.add("Added.");
            result.add(String.valueOf(last_id));
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
                result.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public void add_equipment_reservation(int id_reservation, int id_equipment) {
        try {
            String rq = "INSERT INTO reservation_equipment ( reservation_id, equipment_id) VALUES (?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, id_reservation);
            pst.setInt(2, id_equipment);

            pst.executeUpdate();
            System.out.println("Reservation-Equipment has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete_equipment_reservation(int id_reservation, int id_equipment) {
        try {
            String rq = "DELETE FROM reservation_equipment WHERE reservation_id = ? AND equipment_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id_reservation);
            pst.setInt(2, id_equipment);
            pst.executeUpdate();
            System.out.println("Reservation-Equipment has been deleted");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update_equipment_reservation(int id_reservation, int id_equipment) {
        try {
            String rq = "UPDATE reservation_equipment SET reservation_id = ? WHERE equipment_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, id_reservation);
            pst.setInt(2, id_equipment);

            pst.executeUpdate();
            System.out.println("Reservation-Equipment has been updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> terrain_reservations_by_year(int terrain_id, int year) {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation WHERE terrain_id = ? AND YEAR(date_reservation) = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, terrain_id);
            pst.setInt(2, year);
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public List<Reservation> terrain_reservations_by_month(int terrain_id, int year, int month) {
        List<Reservation> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM reservation WHERE terrain_id = ? AND YEAR(date_reservation) = ? AND MONTH(date_reservation) = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, terrain_id);
            pst.setInt(2, year);
            pst.setInt(3, month);
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public List<Reservation> terrain_reservations_by_week(int terrain_id, LocalDate date) {
        List<Reservation> myList = new ArrayList<>();

        // Find the closest Monday before the given date
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Calculate the end of the week (Sunday)
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        try {
            String rq = "SELECT * FROM reservation WHERE terrain_id = ? AND date_reservation >= ? AND date_reservation < ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, terrain_id);
            pst.setTimestamp(2, Timestamp.valueOf(startOfWeek.atStartOfDay()));
            pst.setTimestamp(3, Timestamp.valueOf(endOfWeek.plusDays(1).atStartOfDay()));
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public List<Equipment> myEquipments(int id_reservation) {
        Equipment e;
        EquipmentCRUD ec = new EquipmentCRUD();
        List<Equipment> myList = new ArrayList<>();
        try {
            String rq = "SELECT * FROM reservation_equipment WHERE reservation_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, id_reservation);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                e = ec.getEntity(rs.getInt("equipment_id"));
                myList.add(e);
            }
            System.out.println("Reservation-Equipment has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Reservation find_reservation(Reservation reservation) {
        Reservation r = new Reservation();

        PreparedStatement pst;
        try {
            String rq = "SELECT * FROM reservation WHERE terrain_id = ? AND updated_at = ?";
            pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, reservation.getTerrain_id());
            pst.setTimestamp(2, Timestamp.valueOf(reservation.getUpdated_at()));
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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }

    @Override
    public Reservation find_reservation_update(Reservation reservation) {
        Reservation r = new Reservation();
        PreparedStatement pst;
        try {
            String rq = "SELECT * FROM reservation WHERE terrain_id = ? AND start_time = ? AND end_time = ? AND res_status = ? AND client_id = ? AND nb_person = ? AND updated_at = ? AND id <> ?";
            pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, reservation.getTerrain_id());
            pst.setTimestamp(2, Timestamp.valueOf(reservation.getStartTime()));
            pst.setTimestamp(3, Timestamp.valueOf(reservation.getEndTime()));
            pst.setBoolean(4, false);
            pst.setInt(5, reservation.getClient_id());
            pst.setInt(6, reservation.getNbPerson());
            pst.setTimestamp(7, Timestamp.valueOf(reservation.getUpdated_at()));
            if (reservation.getId() != 0) {
                pst.setInt(8, reservation.getId());
            }

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
                r.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }
      public int addEntity_1(Reservation t) {
        int last_id=-1;
        try {
            String rq = "INSERT INTO reservation (terrain_id, client_id, date_reservation, start_time, end_time, res_status, nb_person,updated_at) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq,Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, t.getTerrain_id());
            pst.setInt(2, t.getClient_id());
            pst.setTimestamp(3, Timestamp.valueOf(t.getDateReservation()));
            pst.setTimestamp(4, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getEndTime()));
            pst.setBoolean(6, t.isResStatus());
            pst.setInt(7, t.getNbPerson());
            pst.setTimestamp(8, Timestamp.valueOf(t.getUpdated_at()));
            pst.executeUpdate();
            
            ResultSet rs = pst.getGeneratedKeys();
             if (rs.next()) {
        last_id = rs.getInt(1);     
    }
            System.out.println("Reservation has been added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return last_id;
    }
}
