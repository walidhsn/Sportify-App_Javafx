/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Ressource.Constants;

/**
 *
 * @author ramib
 */
public class Auth {

    @FXML
    private Button btnClient;
    @FXML
    private Button btnOwner;

    @FXML
    private void Client(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.AcademyList_1));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Owner(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.AcademyList));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
