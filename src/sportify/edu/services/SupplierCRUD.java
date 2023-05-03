/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import sportify.edu.entities.Supplier;
import sportify.edu.interfaces.EntityCRUD2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author MOLKA
 */
public class SupplierCRUD implements EntityCRUD2<Supplier> {

    @Override
    public void addEntity(Supplier s) {
        try {
            String requete = "INSERT INTO suppliers (name,adress,phone,email) "
                    + "VALUES ("
                    + "'" + s.getName() + "','" + s.getAdresse() + "','" + s.getPhone() + "','" + s.getEmail() + "')";
            Statement st;
            st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(requete);
            System.out.println("Supplier ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }//To change body of generated methods, choose Tools | Templates.
    }

    public int recupererIdSupplierParNom(String t) {

        try {

            String req = "select id from supplier where  name = '" + t + "'";

            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
              
            
                int id = rs.getInt("id");
              
return id;
              
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       

        return 0;
    }

    @Override
    public List<Supplier> display() {
        List<Supplier> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM suppliers";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Supplier s;
                s = new Supplier();
                s.setId(rs.getInt(1));
                s.setName(rs.getString("name"));
                s.setAdresse(rs.getString("adress"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));

                myList.add(s);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void deleteEntity(int id) {
        try {
            String requete = "DELETE FROM suppliers WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Supplier with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Supplier found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateEntity(Supplier s) {
        try {
            String requete = "UPDATE equipment SET name=?, adress=? , phone=? ,email=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, s.getName());
            pst.setString(2, s.getAdresse());
            pst.setString(3, s.getPhone());
            pst.setString(4, s.getEmail());
            pst.setInt(5, s.getId());
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Supplier with ID " + s.getId() + " updated successfully.");
            } else {
                System.out.println("NoSupplier found with ID " + s.getId() + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Supplier getEntity(int supplierId) throws SQLException {
        String query = "SELECT * FROM suppliers WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Supplier supplier = null;

        try {
            connection = MyConnection.getInstance().getCnx();
            statement = connection.prepareStatement(query);
            statement.setInt(1, supplierId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String adresse = resultSet.getString("adress");
                String phone = resultSet.getString("phone");
                String Email = resultSet.getString("email");

                supplier = new Supplier(id, name, adresse, phone, Email);
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

        return supplier;
    }
}
