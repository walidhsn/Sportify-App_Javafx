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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Event;
import sportify.edu.entities.Sponsor;
import sportify.edu.interfaces.EntityCRUD;
import sportify.edu.interfaces.IEventService;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author WALID
 */
public class EventService implements EntityCRUD<Event>, IEventService {

    @Override
    public void addEntity(Event t) {
        try {
            String rq = "INSERT INTO event ( organisateur_id, nom, category, date_event, start_time, end_time, image_name, terrain_name, updated_at) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, t.getOrganisateur_id());
            pst.setString(2, t.getNom());
            pst.setString(3, t.getCategory());
            pst.setTimestamp(4, Timestamp.valueOf(t.getDateEvent()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(6, Timestamp.valueOf(t.getEndTime()));
            pst.setString(7, t.getImageName());
            pst.setString(8, t.getTerrainName());
            pst.setTimestamp(9, Timestamp.valueOf(t.getUpdatedAt()));
            pst.executeUpdate();
            System.out.println("Event has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateEntity(Event t) {
        try {
            String rq = "UPDATE event SET organisateur_id = ?, nom = ?, category = ?, date_event = ?, start_time = ?, end_time = ?, image_name = ?, terrain_name = ? , updated_at = ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, t.getOrganisateur_id());
            pst.setString(2, t.getNom());
            pst.setString(3, t.getCategory());
            pst.setTimestamp(4, Timestamp.valueOf(t.getDateEvent()));
            pst.setTimestamp(5, Timestamp.valueOf(t.getStartTime()));
            pst.setTimestamp(6, Timestamp.valueOf(t.getEndTime()));
            pst.setString(7, t.getImageName());
            pst.setString(8, t.getTerrainName());
            pst.setTimestamp(9, Timestamp.valueOf(t.getUpdatedAt()));
            pst.setInt(10, t.getId());

            pst.executeUpdate();
            System.out.println("Event has been updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM event where id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Event has been deleted.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Event> displayEntity() {
        List<Event> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM event";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                Event r = new Event();
                r.setId(rs.getInt("id"));
                r.setOrganisateur_id(rs.getInt("organisateur_id"));
                r.setNom(rs.getString("nom"));
                r.setCategory(rs.getString("category"));
                r.setDateEvent(rs.getTimestamp("date_event").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setImageName(rs.getString("image_name"));
                r.setTerrainName(rs.getString("terrain_name"));
                r.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Event diplay(int id) {
        Event r = new Event();
        try {
            String rq = "SELECT * FROM event WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setOrganisateur_id(rs.getInt("organisateur_id"));
                r.setNom(rs.getString("nom"));
                r.setCategory(rs.getString("category"));
                r.setDateEvent(rs.getTimestamp("date_event").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setImageName(rs.getString("image_name"));
                r.setTerrainName(rs.getString("terrain_name"));
                r.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public List<Event> myEvents(int organisateur_id) {
        List<Event> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM event WHERE organisateur_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, organisateur_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Event r = new Event();
                r.setId(rs.getInt("id"));
                r.setOrganisateur_id(rs.getInt("organisateur_id"));
                r.setNom(rs.getString("nom"));
                r.setCategory(rs.getString("category"));
                r.setDateEvent(rs.getTimestamp("date_event").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setImageName(rs.getString("image_name"));
                r.setTerrainName(rs.getString("terrain_name"));
                r.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Event find_event(int organisateur_id, String nom, String terrainName, LocalDateTime dateEvent) {
        Event r = null;
        try {
            String rq = "SELECT * FROM event WHERE organisateur_id = ? AND nom = ? AND terrain_name = ? AND start_time = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, organisateur_id);
            pst.setString(2, nom);
            pst.setString(3, terrainName);
            pst.setTimestamp(4, Timestamp.valueOf(dateEvent));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r = new Event();
                r.setId(rs.getInt("id"));
                r.setOrganisateur_id(rs.getInt("organisateur_id"));
                r.setNom(rs.getString("nom"));
                r.setCategory(rs.getString("category"));
                r.setDateEvent(rs.getTimestamp("date_event").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setImageName(rs.getString("image_name"));
                r.setTerrainName(rs.getString("terrain_name"));
                r.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public Event find_event_update(int id_event, int organisateur_id, String nom, String terrainName, LocalDateTime dateEvent) {
        Event r = null;
        try {
            String rq = "SELECT * FROM event WHERE organisateur_id = ? AND nom = ? AND terrain_name = ? AND start_time = ? AND id <> ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, organisateur_id);
            pst.setString(2, nom);
            pst.setString(3, terrainName);
            pst.setTimestamp(4, Timestamp.valueOf(dateEvent));
            pst.setInt(5, id_event);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r = new Event();
                r.setId(rs.getInt("id"));
                r.setOrganisateur_id(rs.getInt("organisateur_id"));
                r.setNom(rs.getString("nom"));
                r.setCategory(rs.getString("category"));
                r.setDateEvent(rs.getTimestamp("date_event").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setImageName(rs.getString("image_name"));
                r.setTerrainName(rs.getString("terrain_name"));
                r.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public List<String> createEvent(Event r) {
        List<String> result = new ArrayList<>();
        List<Event> conflictingEvents = findConflictingEvents(r);
        List<String> conflictingTimes = new ArrayList<>();
        boolean hasConfirmedConflict = false;
        if (!conflictingEvents.isEmpty()) {
            for (Event conflictingEvent : conflictingEvents) {

                hasConfirmedConflict = true;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String startTime = conflictingEvent.getStartTime().format(formatter);
                String endTime = conflictingEvent.getEndTime().format(formatter);
                String conflictingTime = startTime + " - " + endTime;
                conflictingTimes.add(conflictingTime);

            }
        }
        if (hasConfirmedConflict) {
            String errorMessage = "Selected date and time range is not available due to a Event conflict. ";
            if (!conflictingTimes.isEmpty()) {
                errorMessage = errorMessage + " Event : ";
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
    public List<String> updateEvent(Event r) {
        List<String> result = new ArrayList<>();
        List<Event> conflictingEvents = findConflictingEvents(r);
        List<String> conflictingTimes = new ArrayList<>();
        boolean hasConfirmedConflict = false;
        if (!conflictingEvents.isEmpty()) {
            for (Event conflictingEvent : conflictingEvents) {

                hasConfirmedConflict = true;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String startTime = conflictingEvent.getStartTime().format(formatter);
                String endTime = conflictingEvent.getEndTime().format(formatter);
                String conflictingTime = startTime + " - " + endTime;
                conflictingTimes.add(conflictingTime);

            }
        }
        if (hasConfirmedConflict) {
            String errorMessage = "Selected date and time range is not available due to a Event conflict. ";
            if (!conflictingTimes.isEmpty()) {
                errorMessage = errorMessage + " Event : ";
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
    public void addEventSponsor(int sponsor_id, int event_id) {
        try {
            String rq = "INSERT INTO sponsor_e_event ( sponsor_e_id, event_id) VALUES (?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, sponsor_id);
            pst.setInt(2, event_id);

            pst.executeUpdate();
            System.out.println("Sponsor-Event has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateEventSponsor(int sponsor_id, int event_id) {
        try {
            String rq = "UPDATE sponsor_e_event SET sponsor_e_id = ? WHERE event_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, sponsor_id);
            pst.setInt(2, event_id);

            pst.executeUpdate();
            System.out.println("Sponsor-Event has been updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Event> findConflictingEvents(Event event) {
        List<Event> result = new ArrayList<>();
        String rq = "SELECT * FROM event "
                + "WHERE terrain_name = ? "
                + "AND start_time < ? "
                + "AND end_time > ?";

        if (event.getId() != 0) {
            rq += " AND id != ?";
        }
        PreparedStatement pst;
        try {
            pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setString(1, event.getTerrainName());
            pst.setTimestamp(2, Timestamp.valueOf(event.getEndTime()));
            pst.setTimestamp(3, Timestamp.valueOf(event.getStartTime()));
            if (event.getId() != 0) {
                pst.setInt(4, event.getId());
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Event r = new Event();
                r.setId(rs.getInt("id"));
                r.setOrganisateur_id(rs.getInt("organisateur_id"));
                r.setNom(rs.getString("nom"));
                r.setCategory(rs.getString("category"));
                r.setDateEvent(rs.getTimestamp("date_event").toLocalDateTime());
                r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                r.setImageName(rs.getString("image_name"));
                r.setTerrainName(rs.getString("terrain_name"));
                r.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                result.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Sponsor> mySponsors(int event_id) {
        Sponsor s;
        SponsorService ss = new SponsorService();
        List<Sponsor> myList = new ArrayList<>();
        try {
            String rq = "SELECT * FROM sponsor_e_event WHERE event_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, event_id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                s = ss.diplay(rs.getInt("sponsor_e_id"));
                myList.add(s);
            }
            System.out.println("Sponsor-Event has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public void deleteEventSponsor(int sponsor_id, int event_id) {
        try {
            String rq = "DELETE FROM sponsor_e_event WHERE sponsor_e_id = ? AND event_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, sponsor_id);
            pst.setInt(2, event_id);
            pst.executeUpdate();
            System.out.println("Sponsor-Event has been deleted");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void join_event(int id_event, int id_client) {
        try {
            String rq = "INSERT INTO event_user (event_id,user_id) VALUES (?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, id_event);
            pst.setInt(2, id_client);

            pst.executeUpdate();
            System.out.println("Client-Event has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete_join_event(int id_event, int id_client) {
          try {
            String rq = "DELETE FROM event_user WHERE event_id = ? AND user_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id_event);   
            pst.setInt(2, id_client);  
            pst.executeUpdate();
            System.out.println("event_user has been deleted");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Event> myEvents_client(int id_client) {
        Event e;
        EventService es = new EventService();
        List<Event> myList = new ArrayList<>();
        try {
            String rq = "SELECT * FROM event_user WHERE user_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setInt(1, id_client);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                e = es.diplay(rs.getInt("event_id"));
                myList.add(e);
            }
            System.out.println("User-Event has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
}
