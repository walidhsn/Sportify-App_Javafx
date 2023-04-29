/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import com.sun.javafx.iio.ImageStorage.ImageType;
import entities.Equipment;
import interfaces.EntityCRUD;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tools.MyConnection;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author karra
 */
public class EquipmentCRUD implements EntityCRUD<Equipment> {

//        @Override
//        public void addEntity(Equipment t) {
//            try {
//                String requete = "INSERT INTO equipment (name,Category,Quantity,Price,imageName) "
//                        + "VALUES ("
//                        + "'"+t.getName()+"','"+t.getCategory()+"','"+t.getQuantity()+"','"+t.getPrice()+",'"+t.getImageName()+"')";
//                Statement st = MyConnection.getInstance().getCnx()
//                        .createStatement();
//                st.executeUpdate(requete);
//                System.out.println("Equipment ajoutée");
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//
//        }
    @Override
    public void addEntity(Equipment t) {
        try {
            String requete = "INSERT INTO equipment (name, Category, Quantity, Price, imageName,id_supp) VALUES (?,?, ?, ?, ?, ?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getCategory());
            pst.setInt(3, t.getQuantity());
            pst.setInt(4, t.getPrice());
            pst.setString(5, t.getImageName());
            pst.setInt(6, t.getId_supp());

            pst.executeUpdate();
            System.out.println("Equipment added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    public void addEntity2(Equipment t) {
//        try {
//            String requete="INSERT INTO personne (nom,prenom)"
//                    + "VALUES (?,?)";
//            PreparedStatement pst = MyConnection.getInstance().getCnx()
//                    .prepareStatement(requete);
//            pst.setString(1, t.getName());
//            pst.setString(2, t.getCategory());
//            pst.setInt(3, t.getPrice());
//            pst.executeUpdate();
//            System.out.println("Success!");
//            
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//                                    
//    }
    @Override
    public List<Equipment> display() {
        List<Equipment> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM equipment";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Equipment p = new Equipment();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("Category"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setPrice(rs.getInt("Price"));
                p.setImageName(rs.getString("imageName"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Equipment> displayrecherche(String name) {
        List<Equipment> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM equipment WHERE name LIKE ?";
            PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(requete);
            st.setString(1, "%" + name + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Equipment p = new Equipment();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("Category"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setPrice(rs.getInt("Price"));
                p.setImageName(rs.getString("imageName"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List<Equipment> trierEvent() throws SQLException {

        List<Equipment> myList = new ArrayList<>();
        String s = "select * FROM equipment order by name";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Equipment p = new Equipment();
            p.setId(rs.getInt(1));
            p.setName(rs.getString("name"));
            p.setCategory(rs.getString("Category"));
            p.setQuantity(rs.getInt("Quantity"));
            p.setPrice(rs.getInt("Price"));
            p.setImageName(rs.getString("imageName"));
            myList.add(p);
        }
        return myList;
    }

    public void deleteEntity(int id) {
        try {
            String requete = "DELETE FROM equipment WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Equipment with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Equipment found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateEntity(Equipment e) {
        try {
            String requete = "UPDATE equipment SET name=?, Category=?, Quantity=?, Price=?, imageName=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, e.getName());
            pst.setString(2, e.getCategory());
            pst.setInt(3, e.getQuantity());
            pst.setInt(4, e.getPrice());
            pst.setString(5, e.getImageName());
            pst.setInt(6, e.getId());
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Equipment with ID " + e.getId() + " updated successfully.");
            } else {
                System.out.println("No Equipment found with ID " + e.getId() + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Equipment getEntity(int equipmentId) throws SQLException {
        String query = "SELECT * FROM equipment WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Equipment equipment = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            statement = connection.prepareStatement(query);
            statement.setInt(1, equipmentId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String Category = resultSet.getString("Category");
                int Quantity = resultSet.getInt("Quantity");
                int Price = resultSet.getInt("Price");
                String imageName = resultSet.getString("imageName");
                equipment = new Equipment(id, name, Category, Quantity, Price, imageName);
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

        return equipment;
    }

    public boolean itemExists(String name) {
        try {
            String requete = "SELECT COUNT(*) FROM equipment WHERE name=?";
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

    public List<Equipment> getAllEquipments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String GenerateQrEvent(Equipment event) throws FileNotFoundException, IOException {
        String eventName = "Equipment name: " + event.getName() + "\n" + "Equipment categorie: " + event.getCategory() + "\n" + "Equipment prix: " + event.getPrice() + "\n";
        ByteArrayOutputStream out = QRCode.from(eventName).to(ImageType.JPG).stream();
        String filename = event.getName() + "_QrCode.jpg";
        //File f = new File("src\\utils\\img\\" + filename);
        File f = new File("C:\\xampp\\htdocs\\imgQr\\qrcode" + filename);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(out.toByteArray());
        fos.flush();

        System.out.println("qr yemshi");
        return filename;
    }

}
