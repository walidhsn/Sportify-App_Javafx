/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entities.Academy;
import entities.Coach;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.AcademyCRUD;
import services.CoachCRUD;
import Ressource.Constants;

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class AcademyDetails_1 implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtCategory1;
    @FXML
    private ImageView img;
    
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    private CoachCRUD coachCRUD = new CoachCRUD();
    @FXML
    private ImageView backIcon;
    private String txtId;
    
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setAcademy(Academy academy) {
        String image_path_directory = "file:"+Constants.DEST_PATH;
        String full_path;
        txtId = String.valueOf(academy.getId());
        txtName.setText(academy.getName());
        txtCategory.setText(academy.getCategory());
        List<String> coachNames = coachCRUD.findCoachNamesByAcademyName(academy.getName());
        String coachNamesString = String.join(", ", coachNames);
        txtCategory1.setText(coachNamesString);
        System.out.println(academy.getImageName());
        if (academy.getImageName() != null) {
            full_path = image_path_directory + academy.getImageName();
            System.out.println(full_path);
            img.setImage(new Image(full_path));
        } else {
            System.out.println("No image");    
        }
    }
    
    @FXML
    public void handleApplyButtonClick(javafx.event.ActionEvent event) throws IOException, SQLException {
        if (txtId.isEmpty()) {
            // Display an error message to the user
            Alert alert = new Alert(AlertType.ERROR, "Please enter an ID");
            alert.showAndWait();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(txtId);
        } catch (NumberFormatException e) {
            // Display an error message to the user
            Alert alert = new Alert(AlertType.ERROR, "Invalid ID: " + txtId);
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Apply));
            Parent root = loader.load();

            // Get the controller of the AcademyEdit.fxml file
            ApplyController controller = loader.getController();

            // Set the ID of the selected academy
            controller.setSelectedAcademyId(id);

            Stage stage = (Stage) txtName.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    
    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
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

    
}
