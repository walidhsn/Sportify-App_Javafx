/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Academy;
import interfaces.EntityCRUD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tools.MyConnection;

/**
 *
 * @author ramib
 */
public class AcademyCRUD implements EntityCRUD<Academy> {
    
    @Override
    public void addEntity(Academy t) {
        try {
            String requete = "INSERT INTO academy (name, category, image_name) VALUES (?, ?, ?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getCategory());
            pst.setString(3, t.getImageName());
            pst.executeUpdate();
            System.out.println("Academy added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }

//    @Override
//    public void addEntity(String name, String category) {
//        try {
//            String requete = "INSERT INTO academy (name, category) VALUES (?,?)";
//            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//            pst.setString(1, name);
//            pst.setString(2, category);
//            pst.executeUpdate();
//            System.out.println("Academy added");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage()); 
//        }
//    }

    
//    public void addEntity2(Academy t) {
//        try {
//            String requete = "INSERT INTO academy (name,category) VALUES (?,?)";
//            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//            pst.setString(1, t.getName());
//            pst.setString(2, t.getCategory());
//            pst.executeUpdate();
//            System.out.println("Success");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage()); 
//        }
//    }
    
    public void addEntity2() {
    try {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Academy name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Academy category: ");
        String category = scanner.nextLine();
        
        String requete = "INSERT INTO academy (name, category) VALUES (?,?)";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, name);
        pst.setString(2, category);
        pst.executeUpdate();
        System.out.println("Success");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage()); 
    }
}

   

    @Override
    public List<Academy> display() {
        List<Academy> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM academy";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Academy p = new Academy();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category"));
                p.setImageName(rs.getString("image_name"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    public void displayAllAcademies() {
        try {
            String query = "SELECT * FROM academy";
            Statement stmt = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Image Name: " + rs.getString("image_name"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    @Override
    public void deleteEntity(int id) {
        try {
            String requete = "DELETE FROM academy WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Academy with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Academy found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void updateEntity(Academy t) {
        try {
            String requete = "UPDATE academy SET name=?, category=?, image_name=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getCategory());
            pst.setString(3, t.getImageName());
            pst.setInt(4, t.getId());
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Academy with ID " + t.getId() + " updated successfully.");
            } else {
                System.out.println("No Academy found with ID " + t.getId() + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    @Override
    public void academyDetails(int id) {
        try {
            String requete = "SELECT * FROM academy WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                Academy p = new Academy();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category"));
                p.setImageName(rs.getString("image_name"));
                System.out.println("Academy details: " + p.toString());
            } else {
                System.out.println("No Academy found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean academyExists(String name) {
        try {
            String requete = "SELECT COUNT(*) FROM academy WHERE name=?";
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
    public Academy getEntity(int academyId) throws SQLException {
        String query = "SELECT * FROM academy WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Academy academy = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            statement = connection.prepareStatement(query);
            statement.setInt(1, academyId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                String imageName = resultSet.getString("image_name");
                academy = new Academy(id, name, category, imageName);
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

        return academy;
    }




    


    
}
