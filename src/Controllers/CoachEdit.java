/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entities.Academy;
import entities.Coach;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import services.AcademyCRUD;
import services.CoachCRUD;
import Ressource.Constants;

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class CoachEdit implements Initializable {

    private int selectedCoachId;
    private Coach selectedCoach;
    private CoachCRUD coachCRUD;


    @FXML
    private TextField txtName;
    @FXML
    private TextField txtName1;
    @FXML
    private TextField txtName11;
    @FXML
    private Button btnSave;
    @FXML
    private ImageView backIcon;
    @FXML
    private ChoiceBox<Academy> AcademyChoice;
    private AcademyCRUD academyCRUD = new AcademyCRUD();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coachCRUD = new CoachCRUD();
        
        List<Academy> academies = academyCRUD.display();
        AcademyChoice.getItems().addAll(academies);
        AcademyChoice.setConverter(new StringConverter<Academy>() {
            @Override
            public String toString(Academy academy) {
                if (academy == null) {
                    return "";
                } else {
                    return academy.getName();
                }
            }
            @Override
            public Academy fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null; // or return a default Academy object
                } else {
                    // find the Academy object with the corresponding name
                    for (Academy academy : academies) {
                        if (academy.getName().equals(string)) {
                            return academy;
                        }
                    }
                    return null; // or return a default Academy object
                }
            }
        });
    }

    public void setSelectedCoachId(int coachId) throws SQLException {
        this.selectedCoachId = coachId;
        this.selectedCoach = coachCRUD.getEntity(coachId);
        txtName.setText(selectedCoach.getName());
        txtName1.setText(selectedCoach.getEmail());
        txtName11.setText(selectedCoach.getPhone());
//        AcademyChoice.setValue(selectedCoach.getAcademyName());
    }

    @FXML
    public void handleSaveButtonClick(ActionEvent event) throws IOException {
        // Retrieve the Coach data from the fields
        int id = selectedCoach.getId();
        String name = txtName.getText();
        String email = txtName1.getText();
        String phone = txtName11.getText();
        Academy academyChoice = AcademyChoice.getValue();
        String academy = AcademyChoice.getConverter().toString(academyChoice);

        if (name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()  ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

            // Check if name or category field contains non-letter characters
        } else if (!name.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Name Field Can Only Contain Letters");
            alert.setContentText("Please enter a valid name");
            alert.showAndWait();
        } else if (!name.equals(selectedCoach.getName()) && coachCRUD.coachExists(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coach Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("A coach with the same name already exists");
            alert.showAndWait();
        } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Email Address");
            alert.setContentText("Please enter a valid email address");
            alert.showAndWait();
        }else if (!phone.matches("\\d{8}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Please enter a 8-digit phone number");
            alert.showAndWait();   
        } else {
            CoachCRUD coachCRUD = new CoachCRUD();
            Coach coach = new Coach();
            coach.setId(id);
            coach.setName(name);
            coach.setEmail(email);
            coach.setPhone(phone);
            coach.setAcademyName(academy);
            
            coachCRUD.updateEntity(coach);

            // Close the window
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();
            Parent coachListParent = FXMLLoader.load(getClass().getResource(Constants.CoachList));
            Scene coachListScene = new Scene(coachListParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(coachListScene);
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