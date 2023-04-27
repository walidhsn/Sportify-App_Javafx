/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

//import models.MailSender;
import sportify.edu.entities.User;
import sportify.edu.services.SecurityService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author moata
 */
public class REGISTER_Controller implements Initializable {

    @FXML
    private TextField fxemail;
    @FXML
    private PasswordField fxpassword;
    @FXML
    private Button fxlogin;
    @FXML
    private AnchorPane creeruncompte;
    @FXML
    private Button stosignup;
    @FXML
    private Label resetpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(SecurityService.getCurrentUtilisateur());
        SecurityService.logout();
        System.out.println(SecurityService.getCurrentUtilisateur());
    }

    @FXML
    private void login(ActionEvent event) {

        String email = fxemail.getText();
        String password = fxpassword.getText();
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            // afficher le message d'erreur dans une alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("L'adresse e-mail est invalide.");
            alert.showAndWait();
            return;
        }
        
        User currentUtilisateur = SecurityService.signIn(email, password);
            if (currentUtilisateur != null) {
                if (currentUtilisateur.getRoles().contains("ROLE_ADMIN")) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../gui/security/afficherlisteutilisateur.fxml")); 
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                } else if (currentUtilisateur.getRoles().contains("ROLE_OWNER")) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/Terrain_view_owner.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }else {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/Terrain_view_client.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
            
    }

    @FXML
    private void redirectToSignUp(ActionEvent event) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/security/signup.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    @FXML
//    private void resetpassword(MouseEvent event) {
//        
//        try {
//            MailSender.sendMail("dhiasaibi@yahoo.com", "Dhia", "s");
//        } catch (Exception ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
}



        