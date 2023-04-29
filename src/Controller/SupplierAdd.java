/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import entities.Supplier;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import services.SupplierCRUD;


/**
 *
 * @author MOLKA
 */
public class SupplierAdd implements Initializable  {
    
@FXML
private TextField nameField;
@FXML
private TextField adresseField;
@FXML
private TextField phoneField;
@FXML
private TextField EmailField;
private SupplierCRUD supplierCRUD = new SupplierCRUD();
@FXML
private Button btnAdd;

private SupplierCRUD spCRUD = new SupplierCRUD();


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    @FXML
private void SpAdd(javafx.event.ActionEvent event) throws IOException {
    String name = nameField.getText();
    String adresse = adresseField.getText();
    String phone = phoneField.getText();
    String Email = EmailField.getText();

    if (name.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a name");
        alert.showAndWait();
        return;
    }

    if (!name.matches("^[a-zA-Z]*$")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid name with only alphabetic characters");
        alert.showAndWait();
        return;
    }
  

    if (adresse.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a category");
        alert.showAndWait();
        return;
    }

    if (!adresse.matches("^[a-zA-Z0-9]*$")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid category with only alphabetic characters");
        alert.showAndWait();
        return;
    }

 
    if (phone.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a phone number");
        alert.showAndWait();
        return;
    }
    if (!phone.matches("^[0-9]*$")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid name with only alphabetic characters");
        alert.showAndWait();
        return;
    }

 
    if (Email.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter an email address");
        alert.showAndWait();
        return;
    }

    if (!Email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid email address");
        alert.showAndWait();
        return;
    }


    Supplier sp = new Supplier(name, adresse, phone, Email);
    spCRUD.addEntity(sp);
    System.out.println("Supplier added successfully");
    Parent supplierListParent = FXMLLoader.load(getClass().getResource("../gui/SupplierList.fxml"));
    Scene supplierListScene = new Scene(supplierListParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(supplierListScene);
    window.show();
}



    
}

