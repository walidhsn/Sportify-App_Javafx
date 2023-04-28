/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entities.Academy;
import entities.Coach;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import services.AcademyCRUD;
import services.CoachCRUD;

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class CoachAdd implements Initializable {

    @FXML
    private TextField nameField;
    
    @FXML
    private Button btnSubmit;
    @FXML
    private ImageView backIcon;
    private ImageView coach_image;
    
    private CoachCRUD coachCRUD = new CoachCRUD();
    @FXML
    private TextField nameField1;
    @FXML
    private TextField nameField2;
    @FXML
    private ChoiceBox<Academy> AcademyChoice;
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                return null;
            }
        });
        
//        Image btn_icon = new Image("/resources/icons/back.png");
//        backIcon.setImage(btn_icon);

    }
    
    @FXML
    private void CoachAdd(javafx.event.ActionEvent event) throws IOException {
        String name = nameField.getText();
        String email = nameField1.getText();
        String phone = nameField2.getText();
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
        } else if (coachCRUD.coachExists(name)) {
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
        Coach coach = new Coach(name, email, phone, academy);          
        coachCRUD.addEntity(coach);
        System.out.println("Coach added successfully");
        redirect();
        }
         
    }
    
    
    
    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/CoachList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void redirect() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Gui/CoachList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}