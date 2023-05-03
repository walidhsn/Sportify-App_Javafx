/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import sportify.edu.entities.Coach;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.services.CoachCRUD;
import sportify.edu.Ressource.Constants;

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class CoachDetails implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCategory;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    
    private CoachCRUD coachCRUD = new CoachCRUD();
    @FXML
    private TextField txtCategory1;
    @FXML
    private ImageView backIcon;
    @FXML
    private TextField txtCategory11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setCoach(Coach coach) {
        txtId.setText(String.valueOf(coach.getId()));
        txtName.setText(coach.getName());
        txtCategory.setText(coach.getEmail());
        txtCategory1.setText(coach.getPhone());
        txtCategory11.setText(coach.getAcademyName());
        
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.CoachEdit));
        Parent root = loader.load();

        // Get the controller of the CoachEdit.fxml file
        CoachEdit controller = loader.getController();

        // Set the ID of the selected coach
        controller.setSelectedCoachId(id);

        Stage stage = (Stage) txtId.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    
    @FXML
    public void handleDeleteButtonClick(ActionEvent event) throws IOException { 
        int coachId = Integer.parseInt(txtId.getText());

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the coach?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            coachCRUD.deleteEntity(coachId);

            Alert alertDeleted = new Alert(AlertType.INFORMATION);
            alertDeleted.setTitle("Coach Deleted");
            alertDeleted.setHeaderText(null);
            alertDeleted.setContentText("The coach has been deleted successfully.");
            alertDeleted.showAndWait();

            Stage stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource(Constants.CoachList));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            window.show();
        }
    }

    
    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.CoachList));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
}
