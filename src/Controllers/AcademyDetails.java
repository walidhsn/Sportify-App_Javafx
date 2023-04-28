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

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class AcademyDetails implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtCategory1;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private ImageView img;
    
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    private CoachCRUD coachCRUD = new CoachCRUD();
    @FXML
    private ImageView backIcon;
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setAcademy(Academy academy) {
        String image_path_directory = "file:C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
        String full_path;
        txtId.setText(String.valueOf(academy.getId()));
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
    public void handleEditButtonClick(ActionEvent event) throws IOException, SQLException {
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/AcademyEdit.fxml"));
        Parent root = loader.load();

        // Get the controller of the AcademyEdit.fxml file
        AcademyEdit controller = loader.getController();

        // Set the ID of the selected academy
        controller.setSelectedAcademyId(id);

        Stage stage = (Stage) txtId.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    
    @FXML
    public void handleDeleteButtonClick(ActionEvent event) throws IOException { 
        int academyId = Integer.parseInt(txtId.getText());

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the academy?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            academyCRUD.deleteEntity(academyId);

            Alert alertDeleted = new Alert(AlertType.INFORMATION);
            alertDeleted.setTitle("Academy Deleted");
            alertDeleted.setHeaderText(null);
            alertDeleted.setContentText("The academy has been deleted successfully.");
            alertDeleted.showAndWait();

            Stage stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyList.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            window.show();
        }
    }

    
    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
}
