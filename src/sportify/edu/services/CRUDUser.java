/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.json.JSONObject;
import sportify.edu.tools.MyConnection;
import sportify.edu.entities.User;

/**
 *
 * @author moata
 */
public class CRUDUser {

    static Connection cnx;

    public CRUDUser() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void ajouterUtilisateur() {
        try {
            String requete = "INSERT INTO user (email,password) VALUES ('admin@sportify.tn','admin')";
            Statement st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Utilisateur ajoute");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void ajouterUtilisateur2(User u) {

        try {
            String requete2 = "INSERT INTO User (email,password,roles,firstname,lastname,phone,status,nomutilisateur) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete2);

            JSONObject jsonRolesObject = new JSONObject();
            jsonRolesObject.put("roles", u.getRoles());

            pst.setString(1, u.getEmail());
            pst.setString(2, u.getPassword());
            pst.setString(3, jsonRolesObject.get("roles").toString());
            pst.setString(4, u.getFirstname());
            pst.setString(5, u.getLastname());
            pst.setString(6, u.getPhone());
            pst.setBoolean(7, false);
            pst.setString(8, u.getNomUtilisateur());
            pst.executeUpdate();

            System.out.println("Utilisateur ajoute ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimerUtilisateur(int id) {
        try {
            String requete4 = "DELETE FROM user where id=" + id;
            Statement st3 = cnx.createStatement();
            st3.executeUpdate(requete4);
            System.out.println("Utilisateur supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierUtilisateur(int id, String username, String mdp, String email) {
        try {
            String Requete5 = "UPDATE user SET nomutilisateur='" + username + "',password='" + mdp + "', email='" + email + "' where id=" + id;
            PreparedStatement st4 = cnx.prepareStatement(Requete5);
            st4.executeUpdate();
            System.out.println("Utilisateur modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierUtilisateur(int id, boolean status) {
        try {
            String Requete5 = "UPDATE user SET status=? where id=" + id;
            PreparedStatement st4 = cnx.prepareStatement(Requete5);
            st4.setBoolean(1, status);
            st4.executeUpdate();
            System.out.println("Utilisateur modifié");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(status ? "Utilisateur bloqué" : "Utilisateur débloqué");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<User> afficherUtilisateur() {
        List<User> users = new ArrayList<>();
        try {

            String requete3 = "SELECT * FROM user ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setLastname(rs.getString("lastname"));
                u.setFirstname(rs.getString("firstname"));
                u.setNomUtilisateur(rs.getString("nomutilisateur"));
                u.setStatus(rs.getBoolean("status"));
                if (!u.getEmail().equals(SecurityService.getCurrentUtilisateur().getEmail())) {
                    users.add(u);
                }
            }
            System.out.println(users);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }
}
