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
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.web.WebEngine;
import org.mindrot.jbcrypt.BCrypt;
import sportify.edu.entities.User;
import sportify.edu.services.CRUDUser;
import sportify.edu.services.MailSender;

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
    @FXML
    private Button fxadresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        sprenom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !snom.getText().isEmpty()) {
                Random random = new Random();
                susername.setText(sprenom.getText() + "." + snom.getText() + random.nextInt(999));
            }
        });
        snom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && !sprenom.getText().isEmpty()) {
                Random random = new Random();
                susername.setText(sprenom.getText() + "." + snom.getText() + random.nextInt(999));
            }
        });

    }

    public void handleRadioButtonAction(ActionEvent event) {

    }

    @FXML
    private void signup(ActionEvent event) throws Exception {
        String role = "ROLE_CLIENT";
        RadioButton selectedRadioButton = (RadioButton) srole.getSelectedToggle();
        if (selectedRadioButton.isSelected()) {
            String selectedValue = selectedRadioButton.getText();
            System.out.println(selectedValue);
            System.out.println(selectedRadioButton.isSelected());
            System.out.println("Client".equals(selectedValue));
            if (!"Client".equals(selectedValue)) {
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
            if (email.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty() || phone.isEmpty() || username.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }

            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("L'adresse e-mail est invalide.");
                alert.showAndWait();
                return;
            }

            if (!phone.matches("[0-9]{8}")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Le numéro de téléphone est invalide.");
                alert.showAndWait();
                return;
            }
            if (SecurityService.checkEmail(email)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("E-mail existant");
                alert.setHeaderText("Un compte avec cet e-mail existe déjà.");
                alert.showAndWait();
                return;
            }

            if (!password.equals(passwordConfirm)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Les deux mots de passe ne sont pas identiques");
                alert.showAndWait();
                return;
            }
            String adresse = SecurityService.getCurrentAdresse();
            if (adresse.equals("")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Veuillez choisir votre adresse");
                alert.showAndWait();
                return;
            }
            
            String hashedPassword = hashPassword(password);
            String verifCode = SecurityService.generateCode();

            boolean ConnexionResultat = SecurityService.signUp(nom, prenom, email, hashedPassword, phone, roles, username, verifCode);
            MailSender.sendMail(email, verifCode);
            if (ConnexionResultat == true) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Verification de l'email");
                dialog.setHeaderText("Un code a été envoyé à votre email");
                dialog.setContentText("Code:");
                Optional<String> result = dialog.showAndWait();
                while (result.isPresent()) {
                    String code = result.get();
                    Alert alert = new Alert(AlertType.ERROR);
                    if (!code.equals(verifCode)) {
                        alert.setTitle("Code incorrecte");
                        alert.setHeaderText("Vérifier l'email envoyé");
                        alert.showAndWait();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/login.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                            alert.setTitle("Echec de vérification d'email");
                            alert.setHeaderText("Vous pouvez s'authentifier et vérifier plus tard");
                            alert.showAndWait();
                            break;
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }

                        break;
                    } else {
                        SecurityService.emailVerified(email);
                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setTitle("Résultat");
                        alert.setHeaderText("Email vérifié avec succès");
                        alert.showAndWait();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/login.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                            break;
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

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

    private String hashPassword(String plainPassword) {
        String salt = BCrypt.gensalt(13);
        return BCrypt.hashpw(plainPassword, salt);
    }

    @FXML
    public void generateUsername(ActionEvent event) {

        String firstname = sprenom.getText();
        String lastname = snom.getText();
        Random random = new Random();
        susername.setText(firstname + "." + lastname + random.nextInt());
    }

    @FXML
    public void openMap(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/map.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
