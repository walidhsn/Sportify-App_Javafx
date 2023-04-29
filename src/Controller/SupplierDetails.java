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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.stage.Stage;
import services.SupplierCRUD;

/**
 *
 * @author MOLKA
 */
public class SupplierDetails implements Initializable {
    @FXML
private TextField txtId;
@FXML
private TextField txtName;
@FXML
private TextField txtadresse;
@FXML
private TextField txtphone;
@FXML
private TextField txtEmail;
@FXML
private Button btnEdit;
@FXML
private Button btnDel;
    
private SupplierCRUD supplierCRUD = new SupplierCRUD();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
     public void setSupplier(Supplier sp) {
         txtId.setText(String.valueOf(sp.getId()));
         txtName.setText(sp.getName());
         txtadresse.setText(sp.getAdresse());
         txtphone.setText(sp.getPhone());
          txtEmail.setText(sp.getEmail());
     }
   public void handleEditButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/SupplierEdit.fxml"));
        Parent root = loader.load();

       // Get the controller of the AcademyEdit.fxml file
        SupplierEdit controller = loader.getController();

        // Set the ID of the selected academy
       controller.setSelectedSupplierId(Integer.parseInt(txtId.getText()));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
       stage.show();
    }


    
    public void handleDeleteButtonClick(ActionEvent event) throws IOException { 
        int supplierId = Integer.parseInt(txtId.getText());
         supplierCRUD.deleteEntity(supplierId);
        Stage stage = (Stage) btnDel.getScene().getWindow();
        stage.close();
        
        Parent supplierListParent = FXMLLoader.load(getClass().getResource("../gui/SupplierList.fxml"));
        Scene academyListScene = new Scene(supplierListParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(academyListScene);
        window.show();
        
    }

    
}
