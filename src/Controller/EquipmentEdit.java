/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import entities.Equipment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.EquipmentCRUD;

/**
 * FXML Controller class
 *
 * @author MOLKA
 */
public class EquipmentEdit implements Initializable {
    
    private int selectedEquipmentId;
    private Equipment selectedEquipment;
    private EquipmentCRUD eqCRUD;
    
    @FXML
    private ChoiceBox<String> categoryField;
    private final String[] types = {"football", "handball", "basketball", "volleyball", "baseball", "tennis", "golf"};
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
//    @FXML
//    private TextField txtCategory;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnSave;
    
//    private EquipmentCRUD eqCRUD = new EquipmentCRUD();


        @Override
        public void initialize(URL url, ResourceBundle rb) {
            eqCRUD = new EquipmentCRUD();
            categoryField.getItems().addAll(types);

        }    
        
        
    public void setSelectedEquipmentId(int equipmentId) throws SQLException {
        this.selectedEquipmentId = equipmentId;
        this.selectedEquipment = eqCRUD.getEntity(equipmentId);
        txtName.setText(selectedEquipment.getName());
    }
    
//    public void loadAcademyData(Equipment eq) {
//        txtId.setText(String.valueOf(eq.getId()));
//        txtName.setText(eq.getName());
//        txtCategory.setText(eq.getCategory());
//        txtQuantity.setText(String.valueOf(eq.getQuantity()));
//        txtPrice.setText(String.valueOf(eq.getPrice()));
//    }
    
    public void handleSaveButtonClick(ActionEvent event) {
        // Retrieve the Academy data from the fields
        int id = selectedEquipment.getId();        
        String name = txtName.getText();
        String category = categoryField.getValue();
        int quantity = Integer.parseInt(txtQuantity.getText());    
        int price = Integer.parseInt(txtPrice.getText());
        
        // Update the Academy in the database
        EquipmentCRUD eqCRUD = new EquipmentCRUD();
        Equipment molka = new Equipment();
        molka.setId(id);
        molka.setName(name);
        molka.setCategory(category);
        molka.setQuantity(quantity);
        molka.setPrice(price);
        eqCRUD.updateEntity(molka);
        
        // Close the window
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }
}
