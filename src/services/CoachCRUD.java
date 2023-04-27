/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Academy;
import entities.Coach;
import interfaces.CoachIntCRUD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;

/**
 *
 * @author ramib
 */
public class CoachCRUD implements CoachIntCRUD<Coach> {
    
    @Override
    public void addEntity(Coach t) {
        try {
            String requete = "INSERT INTO coach (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getPhone());
            pst.executeUpdate();
            System.out.println("Coach added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }

    @Override
    public void coachDetails(int id) {
        try {
            String requete = "SELECT * FROM coach WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                Coach p = new Coach();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                System.out.println("Coach details: " + p.toString());
            } else {
                System.out.println("No Coach found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
       try {
            String requete = "DELETE FROM coach WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Coach with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Coach found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateEntity(Coach t) {
        try {
            String requete = "UPDATE coach SET name=?, email=?, phone=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getPhone());
            pst.setInt(4, t.getId());
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Coach with ID " + t.getId() + " updated successfully.");
            } else {
                System.out.println("No Coach found with ID " + t.getId() + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Coach getEntity(int coachId) throws SQLException {
        String query = "SELECT * FROM coach WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Coach coach = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            statement = connection.prepareStatement(query);
            statement.setInt(1, coachId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                coach = new Coach(id, name, email, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return coach;
    }
    
    public boolean coachExists(String name) {
        try {
            String requete = "SELECT COUNT(*) FROM coach WHERE name=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Coach> display() {
        List<Coach> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM coach";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Coach p = new Coach();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
}
