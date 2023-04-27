/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

//import models.MailSender;
import sportify.edu.services.SecurityService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sportify.edu.entities.User;
import sportify.edu.services.CRUDUser;

/**
 * FXML Controller class
 *
 * @author moata
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField semail;
    @FXML
    private PasswordField spassword;
    @FXML
    private Button ssignup;
    @FXML
    private TextField snom;
    @FXML
    private TextField sprenom;
    @FXML
    private TextField sphone;
    @FXML
    private TextField snumero;
    private Node captcha;
    @FXML
    private Button stolign;
    @FXML
    private PasswordField spasswordConfirm;
    @FXML
    private TextField susername;
    @FXML
    private ToggleGroup srole;
    @FXML
    private RadioButton sowner;
    @FXML
    private RadioButton sclient;


    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void handleRadioButtonAction(ActionEvent event) {
       
}
    @FXML
    private void signup(ActionEvent event) throws Exception {
        String role = "ROLE_CLIENT";
        RadioButton selectedRadioButton = (RadioButton) srole.getSelectedToggle();
        if(selectedRadioButton.isSelected()){
            String selectedValue = selectedRadioButton.getText();
            System.out.println(selectedValue);
            System.out.println(selectedRadioButton.isSelected());
            System.out.println("Client".equals(selectedValue));
            if(!"Client".equals(selectedValue)){
                role = "ROLE_OWNER";
        }
        
        String email = semail.getText();
        String password = spassword.getText();
        String nom = snom.getText();
        String prenom = sprenom.getText();
        String roles = role;
        String phone = sphone.getText();
        String passwordConfirm = spasswordConfirm.getText();
        String username = susername.getText();
// Vérifier si tous les champs sont remplis
        if (email.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty() || phone.isEmpty() || username.isEmpty()) {
            // afficher le message d'erreur dans une alert box
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }

// Vérifier si le format de l'email est valide
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            // afficher le message d'erreur dans une alert box
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("L'adresse e-mail est invalide.");
            alert.showAndWait();
            return;
        }

// Vérifier si le format du numéro de téléphone est valide
        if (!phone.matches("[0-9]{8}")) {
            // afficher le message d'erreur dans une alert box
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Le numéro de téléphone est invalide.");
            alert.showAndWait();
            return;
        }
        if (SecurityService.checkEmail(email)) {
            // afficher un message d'erreur dans une alert box
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("E-mail existant");
            alert.setHeaderText("Un compte avec cet e-mail existe déjà.");
            alert.showAndWait();
            return;
        }

        // Vérifier si les deux mots de passe sont identiques
    if (!password.equals(passwordConfirm)) {
        // afficher le message d'erreur dans une alert box
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Les deux mots de passe ne sont pas identiques");
        alert.showAndWait();
        return;
    }
    
   

//        
//            User currentUtilisateur = new User();
//            currentUtilisateur.setEmail(email);
//            currentUtilisateur.setPassword(password);
//            currentUtilisateur.setFirstname(prenom);
//            currentUtilisateur.setLastname(nom);
//            currentUtilisateur.setPhone(phone);
//            currentUtilisateur.addRole(role);
//            currentUtilisateur.setStatus(false);
//            System.out.println(currentUtilisateur);
//            CRUDUser cu=new CRUDUser();
//            cu.ajouterUtilisateur2(currentUtilisateur);
//        
        boolean ConnexionResultat = SecurityService.signUp(nom, prenom, email, password, phone, roles,username);
//        MailSender.sendMail("dhiasaibi@yahoo.com", nom, prenom);
        if (ConnexionResultat == true) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    }

    @FXML
    private void redirectToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/security/login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}