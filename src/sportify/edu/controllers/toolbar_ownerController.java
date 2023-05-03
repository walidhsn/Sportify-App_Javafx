/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author moata
 */
public class toolbar_ownerController implements Initializable {

    @FXML
    private ImageView img_sportify;
    @FXML
    private Button btn_home;
    @FXML
    private Button btn_events;
    @FXML
    private Button btn_products;
    @FXML
    private Button btn_equipment;
    @FXML
    private Button btn_academy;
    @FXML
    private Button btn_coach;
    @FXML
    private MenuButton fxmenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToHome(ActionEvent event) {
    }

    @FXML
    private void goToEventsOwner(ActionEvent event) {
    }

    @FXML
    private void goToEquipmentsOwner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/equipment/EquipmentList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(toolbar_clientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML

    private void goToAcademyOwner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/academy/AcademyList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(toolbar_clientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goToCoachOwner(ActionEvent event) {

    }

}
