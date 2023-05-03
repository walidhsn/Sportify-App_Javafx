/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import sportify.edu.entities.Academy;
import sportify.edu.entities.Apply;
import sportify.edu.entities.Coach;
import sportify.edu.interfaces.ApplyInt;
import sportify.edu.interfaces.ApplyInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author ramib
 */
public class ApplyCRUD implements ApplyInt<Apply> {
    
    @Override
    public void addEntity(Apply t) {
        try {
            String requete = "INSERT INTO application (appname, appage, image_name, academy_name) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setInt(2, t.getAge());
            pst.setString(3, t.getImageName());
            pst.setString(4, t.getAcademy_name());
            pst.executeUpdate();
            System.out.println("Application added");
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
    
//    public void addEntity2() {
//    try {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter Academy name: ");
//        String name = scanner.nextLine();
//        System.out.println("Enter Academy category: ");
//        String category = scanner.nextLine();
//        
//        String requete = "INSERT INTO academy (name, category) VALUES (?,?)";
//        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//        pst.setString(1, name);
//        pst.setString(2, category);
//        pst.executeUpdate();
//        System.out.println("Success");
//    } catch (SQLException ex) {
//        System.out.println(ex.getMessage()); 
//    }
//}

   

    @Override
    public List<Apply> display() {
        List<Apply> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM application";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Apply p = new Apply();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("appname"));
                p.setAge(rs.getInt("appage"));
                p.setImageName(rs.getString("image_name"));
                p.setAcademy_name(rs.getString("academy_name"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    public void displayAllApplications() {
        try {
            String query = "SELECT * FROM application";
            Statement stmt = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("appname"));
                System.out.println("Age: " + rs.getString("appage"));
                System.out.println("Image Name: " + rs.getString("image_name"));
                System.out.println("Academy name: " + rs.getString("academy_name"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    @Override
    public void deleteEntity(int id) {
        try {
            String requete = "DELETE FROM application WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("application with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No application found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


//    @Override
//    public void updateEntity(Academy t) {
//        try {
//            String requete = "UPDATE academy SET name=?, category=?, image_name=? WHERE id=?";
//            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//            pst.setString(1, t.getName());
//            pst.setString(2, t.getCategory());
//            pst.setString(3, t.getImageName());
//            pst.setInt(4, t.getId());
//            int nb = pst.executeUpdate();
//            if (nb > 0) {
//                System.out.println("Academy with ID " + t.getId() + " updated successfully.");
//            } else {
//                System.out.println("No Academy found with ID " + t.getId() + ".");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }

    
    @Override
    public void applyDetails(int id) {
        try {
            String requete = "SELECT * FROM application WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                Apply p = new Apply();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("appname"));
                p.setAge(rs.getInt("appage"));
                p.setImageName(rs.getString("image_name"));
                p.setAcademy_name(rs.getString("academy_name"));
                System.out.println("Academy details: " + p.toString());
            } else {
                System.out.println("No Academy found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
//    public boolean applyExists(String name) {
//        try {
//            String requete = "SELECT COUNT(*) FROM academy WHERE name=?";
//            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//            pst.setString(1, name);
//            ResultSet rs = pst.executeQuery();
//            rs.next();
//            int count = rs.getInt(1);
//            return count > 0;
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            return false;
//        }
//    }
    
    @Override
    public Apply getEntity(String applyName) throws SQLException {
        String query = "SELECT * FROM application WHERE appname = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Apply apply = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            statement = connection.prepareStatement(query);
            statement.setString(1, applyName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("appname");
                int age = resultSet.getInt("appage");
                String imageName = resultSet.getString("image_name");
                String academyName = resultSet.getString("academy_name");
                apply = new Apply(id, name, age, imageName, academyName);
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

        return apply;
    }

    
    public List<Apply> findApplyByAcademyName(String academyName) {
    List<Apply> applyList = new ArrayList<>();
    try {
        String query = "SELECT * FROM application WHERE academy_name=?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        pst.setString(1, academyName);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("appname");
            int age = resultSet.getInt("appage");
            String imageName = resultSet.getString("image_name");
            String academy_name = resultSet.getString("academy_name");

            applyList.add(new Apply(id, name, age, imageName, academy_name));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return applyList;
}

    



    
}
