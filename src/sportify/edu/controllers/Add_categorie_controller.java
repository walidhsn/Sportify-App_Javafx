/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sportify.edu.entities.Categorie;
import sportify.edu.entities.Produit;
import sportify.edu.services.CategorieCrud;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class Add_categorie_controller implements Initializable {

    @FXML
    private TextField nom_cat;
    @FXML
    private Button add;
    @FXML
    private Button back_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCategorie(ActionEvent event) {
        CategorieCrud gc = new CategorieCrud();
        String lib = nom_cat.getText();
        if (lib.isEmpty() && lib.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Produit 'Name'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else {
                Categorie c = new Categorie(lib);
                gc.ajouter_categorie(c);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added .");
                alert.setHeaderText(null);
                alert.show();
                redirectToList(event);
            }
    }

    @FXML
    private void redirectToList(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/louaypi/gui/categorie/Categorie_view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
