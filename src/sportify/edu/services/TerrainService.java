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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sportify.edu.entities.Terrain;
import sportify.edu.interfaces.EntityCRUD;
import sportify.edu.interfaces.ITerrain_service;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author WALID
 */
public class TerrainService implements EntityCRUD<Terrain>,ITerrain_service {

    @Override
    public void addEntity(Terrain t) {
        try {
            String rq = "INSERT INTO terrain ( owner_id, name, capacity, sport_type, rent_price, disponibility, postal_code, road_name,road_number, city, country, image_name, updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, t.getOwner_id());
            pst.setString(2, t.getName());
            pst.setInt(3, t.getCapacity());
            pst.setString(4, t.getSportType());
            pst.setFloat(5, t.getRentPrice());
            pst.setBoolean(6, t.isDisponibility());
            pst.setInt(7, t.getPostalCode());
            pst.setString(8, t.getRoadName());
            pst.setInt(9, t.getRoadNumber());
            pst.setString(10, t.getCity());
            pst.setString(11, t.getCountry());
            pst.setString(12, t.getImageName());
            pst.setTimestamp(13, Timestamp.valueOf(t.getUpdatedAt()));
            pst.executeUpdate();
            System.out.println("Terrain has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateEntity(Terrain t) {
        try {
            String rq = "UPDATE terrain SET owner_id = ?, name = ?, capacity = ?, sport_type = ?, rent_price = ?, disponibility = ?, postal_code = ?, road_name = ?, road_number = ?, city = ?, country = ?, image_name = ?, updated_at= ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, t.getOwner_id());
            pst.setString(2, t.getName());
            pst.setInt(3, t.getCapacity());
            pst.setString(4, t.getSportType());
            pst.setFloat(5, t.getRentPrice());
            pst.setBoolean(6, t.isDisponibility());
            pst.setInt(7, t.getPostalCode());
            pst.setString(8, t.getRoadName());
            pst.setInt(9, t.getRoadNumber());
            pst.setString(10, t.getCity());
            pst.setString(11, t.getCountry());
            pst.setString(12, t.getImageName());
            pst.setTimestamp(13, Timestamp.valueOf(t.getUpdatedAt()));
            pst.setInt(14, t.getId());
            pst.executeUpdate();
            System.out.println("Terrain has been updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM terrain WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Terrain has been deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Terrain> displayEntity() {
        List<Terrain> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM terrain";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sport_type"));
                t.setRentPrice(rs.getFloat("rent_price"));
                t.setPostalCode(rs.getInt("postal_code"));
                t.setRoadName(rs.getString("road_name"));
                t.setRoadNumber(rs.getInt("road_number"));
                t.setCity(rs.getString("city"));
                t.setCountry(rs.getString("country"));
                t.setImageName(rs.getString("image_name"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Terrain diplay(int id) {
        Terrain t = new Terrain();
        try {
            String rq = "SELECT * FROM terrain WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {  
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sport_type"));
                t.setRentPrice(rs.getFloat("rent_price"));
                t.setPostalCode(rs.getInt("postal_code"));
                t.setRoadName(rs.getString("road_name"));
                t.setRoadNumber(rs.getInt("road_number"));
                t.setCity(rs.getString("city"));
                t.setCountry(rs.getString("country"));
                t.setImageName(rs.getString("image_name"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

    @Override
    public List<Terrain> myProprieties(int owner_id) {
       List<Terrain> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM terrain WHERE owner_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, owner_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sport_type"));
                t.setRentPrice(rs.getFloat("rent_price"));
                t.setPostalCode(rs.getInt("postal_code"));
                t.setRoadName(rs.getString("road_name"));
                t.setRoadNumber(rs.getInt("road_number"));
                t.setCity(rs.getString("city"));
                t.setCountry(rs.getString("country"));
                t.setImageName(rs.getString("image_name"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public List<Terrain> filterTerrain(String location, String sport_type, String rent_price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Terrain> searchTerrain(String search_term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add_autoCompleteWord(String word) {
         try {
            String rq = "INSERT INTO auto_complete (text) VALUES (?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setString(1, word);
            pst.executeUpdate();
            System.out.println("New word has been added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Set<String> get_autoCompleteWords() {
        Set<String> words = new HashSet<>();
         try {
            String rq = "SELECT * FROM auto_complete";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                words.add(rs.getString("text"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return words;
    }
}
