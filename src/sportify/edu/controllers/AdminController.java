/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import sportify.edu.services.SecurityService;

/**
 *
 * @author moata
 */
public class AdminController implements Initializable {

    @FXML
    private Button fxmodifierUtilisateur;
    @FXML
    private Button fxsupprimerutilisateur;
    @FXML
    private Button fxafficherlisteutilisateur;
    @FXML
    private MenuButton fxmenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxmenu.setText(SecurityService.getCurrentUtilisateur().getNomUtilisateur());

        MenuItem menuItem1 = new MenuItem("My profile");
        MenuItem menuItem2 = new MenuItem("Logout");
        fxmenu.getItems().addAll(menuItem1, menuItem2);

        menuItem1.setOnAction((event) -> {
        loadFXML("../gui/security/gerercompte.fxml");
    });
        menuItem2.setOnAction(event -> redirectToFxml("../gui/security/login.fxml"));
    }

    @FXML
    private void modifierUtilisateur(ActionEvent event) {
    }

    @FXML
    private void fxsupprimerUtilisateur(ActionEvent event) {
    }

    @FXML
    private void fxafficherlisteutilisateur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/afficherlisteutilisateur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
    }
    private void loadFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToFxml(String fxml) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) fxmenu.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
