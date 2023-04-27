/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import sportify.edu.entities.User;
import sportify.edu.services.SecurityService;

/**
 *
 * @author moata
 */
public class ChangerMdpController implements Initializable {

    @FXML
    private PasswordField fxoldpassword;
    @FXML
    private PasswordField fxnewpassword;
    @FXML
    private Button fxchanger;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    public void changermdp(){
        String oldPassword = fxoldpassword.getText();
        String newPassword = fxnewpassword.getText();

        User u = SecurityService.getCurrentUtilisateur();

        if (u.getPassword().equals(oldPassword)) {
            boolean updateResult = SecurityService.changePassword(u.getEmail(), newPassword);
            if (updateResult) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Changement de mot de passe");
                alert.setHeaderText("Votre mot de passe a été modifié avec succès.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Une erreur s'est produite lors de la mise à jour du mot de passe.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("L'ancien mot de passe est incorrect.");
            alert.showAndWait();
        }
    }

}
