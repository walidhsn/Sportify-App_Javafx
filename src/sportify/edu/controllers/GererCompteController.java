/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sportify.edu.services.SecurityService;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author moata
 */
public class GererCompteController implements Initializable{
    
     @FXML
    private TextField fxlastname;
    @FXML
    private TextField fxphone;
    @FXML
    private Button btnEnregistrer;
    @FXML
    private TextField fxfirstname;
    @FXML
    private TextField fxusername;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            Connection conn = MyConnection.getInstance().getCnx();
            PreparedStatement stmt = conn.prepareStatement("SELECT lastname, firstname, nomutilisateur, phone FROM user WHERE id = ?");
            stmt.setInt(1, SecurityService.getCurrentUtilisateur().getId()); 
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                fxlastname.setText(rs.getString("Lastname"));
                fxfirstname.setText(rs.getString("Firstname"));
                fxusername.setText(rs.getString("nomutilisateur"));
                fxphone.setText(rs.getString("phone"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }    

    @FXML
    private void enregistrer(ActionEvent event) {
        
        try {
        Connection conn = MyConnection.getInstance().getCnx();
        PreparedStatement stmt = conn.prepareStatement("UPDATE user SET lastname = ?, firstname = ?, nomutilisateur = ?, phone = ? WHERE id = ?");
        stmt.setString(1, fxlastname.getText());
        stmt.setString(2, fxfirstname.getText());
        stmt.setString(3, fxusername.getText());
        stmt.setString(4, fxphone.getText());
        stmt.setInt(5, SecurityService.getCurrentUtilisateur().getId()); 
        int result = stmt.executeUpdate();
        if (result > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Les modifications ont été enregistrées avec succès !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Echec");
            alert.setHeaderText(null);
            alert.setContentText("Aucune modification n'a été enregistrée.");
            alert.showAndWait();
        }
    } catch (SQLException ex) {
            System.out.println(ex.getMessage());
    }
    }
}
