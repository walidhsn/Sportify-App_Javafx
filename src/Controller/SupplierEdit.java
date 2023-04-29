/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import entities.Supplier;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import services.EquipmentCRUD;
import services.SupplierCRUD;

/**
 *
 * @author MOLKA
 */
class SupplierEdit implements Initializable {
     private int selectedSupplierId;
    private Supplier selectedSupplier;
    private SupplierCRUD spCRUD;
    
   
    @FXML
private TextField nameField;
@FXML
private TextField adresseField;
@FXML
private TextField phoneField;
@FXML
private TextField EmailField;
    @FXML
    private Button btnSave;
    
//    private EquipmentCRUD eqCRUD = new EquipmentCRUD();
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spCRUD = new SupplierCRUD();
           

        }    
        
        
    public void setSelectedSupplierId(int supplierId) throws SQLException {
        this.selectedSupplierId = supplierId;
        this.selectedSupplier = spCRUD.getEntity(supplierId);
        txtName.setText(selectedEquipment.getName());
    }
    }
    
}
