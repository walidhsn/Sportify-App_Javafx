/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import sportify.edu.entities.Equipment;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.services.EquipmentCRUD;
import sportify.edu.tools.JavaMail;

/**
 * FXML Controller class
 *
 * @author MOLKA
 */
public class EquipmentDetails implements Initializable {

@FXML
private TextField txtId;
@FXML
private TextField txtName;
@FXML
private TextField txtCategory;
@FXML
private TextField txtQuantity;
@FXML
private TextField txtPrice;
@FXML
private Button btnEdit;
@FXML
private Button btnDel;

    
private EquipmentCRUD academyCRUD = new EquipmentCRUD();


private EquipmentCRUD eqCRUD = new EquipmentCRUD();
    @FXML
    private ImageView QrCode;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }  
    
    public void setEquipment(Equipment eq) throws IOException {
         txtId.setText(String.valueOf(eq.getId()));
         txtName.setText(eq.getName());
         txtCategory.setText(eq.getCategory());
         txtQuantity.setText(String.valueOf(eq.getQuantity()));
         txtPrice.setText(String.valueOf(eq.getPrice()));
         
//                 String filename = academyCRUD.GenerateQrEvent(eq);
//            System.out.println("filename lenaaa " + filename);
//            String path1="C:\\xampp\\htdocs\\xchangex\\imgQr\\qrcode"+filename;
//             File file1=new File(path1);
//              Image img1 = new Image(file1.toURI().toString());
//              //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
//            QrCode.setImage(img1);  
           
     }
    @FXML
   public void handleEditButtonClick(ActionEvent event) throws IOException, SQLException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EquipmentEdit.fxml"));
//        Parent root = loader.load();
//
//       // Get the controller of the AcademyEdit.fxml file
//        EquipmentEdit controller = loader.getController();
//
//        // Set the ID of the selected academy
//       controller.setSelectedEquipmentId(Integer.parseInt(txtId.getText()));
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//       stage.show();
//    }
   
     String idText = txtId.getText();
        if (idText.isEmpty()) {
            // Display an error message to the user
            Alert alert = new Alert(AlertType.ERROR, "Please enter an ID");
            alert.showAndWait();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            // Display an error message to the user
            Alert alert = new Alert(AlertType.ERROR, "Invalid ID: " + idText);
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EquipmentEdit.fxml"));
        Parent root = loader.load();

        // Get the controller of the AcademyEdit.fxml file
        EquipmentEdit controller = loader.getController();

        // Set the ID of the selected academy
        controller.setSelectedEquipmentId(id);

        Stage stage = (Stage) txtId.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }



    
    @FXML
    public void handleDeleteButtonClick(ActionEvent event) throws IOException { 
        int equipmentId = Integer.parseInt(txtId.getText());
         Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this equipment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            academyCRUD.deleteEntity(equipmentId);

            Alert alertDeleted = new Alert(AlertType.INFORMATION);
            alertDeleted.setTitle("Academy Deleted");
            alertDeleted.setHeaderText(null);
            alertDeleted.setContentText("The equipment has been deleted successfully.");
            alertDeleted.showAndWait();
        Stage stage = (Stage) btnDel.getScene().getWindow();
        stage.close();
//        try {
//                //send email to emailField.getText()
//                JavaMail.sendMail("mimousisou35@gmail.com");
//            } catch (Exception ex) {
//                Logger.getLogger(EquipmentDetails.class.getName()).log(Level.SEVERE, null, ex);
//            }
        Parent academyListParent = FXMLLoader.load(getClass().getResource("../gui/EquipmentList.fxml"));
        Scene academyListScene = new Scene(academyListParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(academyListScene);
        window.show();
        }
    }

    
}
