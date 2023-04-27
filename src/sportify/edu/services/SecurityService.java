/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import sportify.edu.entities.User;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author moata
 */
public class SecurityService {

    private static User currentUtilisateur;

    public static User getCurrentUtilisateur() {
        return currentUtilisateur;
    }

    public static User signIn(String email, String password) {
        User user = User.getUserByEmail(email);
        currentUtilisateur = user;
        System.out.println(currentUtilisateur);
        System.out.println("Password written : " + password);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if(user.isStatus()){
                System.out.println(SecurityService.getCurrentUtilisateur());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Bloqué");
                alert.setHeaderText("Votre compte a été bloqué par un admin");
                alert.showAndWait();
                SecurityService.logout();
                    System.out.println("Logout check : "+SecurityService.getCurrentUtilisateur());
                    return null;
                }else{
                    System.out.println(currentUtilisateur);
                System.out.println("Utilisateur Connecte");
                return currentUtilisateur;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Email ou mot de passe incorrecte");
                alert.setHeaderText("Veuillez vérifier vos informations");
                alert.showAndWait();
                System.out.println(currentUtilisateur);
                System.out.println("Utilisateur NonConnecte");
                return null;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email ou mot de passe incorrecte");
            alert.setHeaderText("Veuillez vérifier vos informations");
            alert.showAndWait();
            System.out.println(currentUtilisateur);
            System.out.println("Utilisateur NonConnecte");
            return null;
        }
    }

    public static boolean signUp(String lastname, String firstname, String email, String password, String phone, String role, String username) {

        if (User.getUserByEmail(email) == null) {
            User currentUtilisateur = new User();
            currentUtilisateur.setEmail(email);
            currentUtilisateur.setPassword(password);
            currentUtilisateur.setFirstname(firstname);
            currentUtilisateur.setLastname(lastname);
            currentUtilisateur.setPhone(phone);
            currentUtilisateur.addRole(role);
            currentUtilisateur.setStatus(false);
            currentUtilisateur.setNomUtilisateur(username);
            System.out.println(currentUtilisateur);
            CRUDUser cu = new CRUDUser();
            cu.ajouterUtilisateur2(currentUtilisateur);
            return true;
        } else {
            return false;
        }
    }

    public static void logout() {
        currentUtilisateur = null;
    }

    public static boolean changePassword(String email, String newPassword) {
        try {

            Connection conn = MyConnection.getInstance().getCnx();
            String query = "UPDATE user SET password=? WHERE email=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);

            pstmt.executeUpdate();

            System.out.println("Mot de passe modifié avec succès.");
            return true;
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            return false;
        }
    }


    public static boolean checkEmail(String email) {
        boolean exists = false;
        try {
            Connection conn = MyConnection.getInstance().getCnx();
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
                exists = true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return exists;
    }
}
