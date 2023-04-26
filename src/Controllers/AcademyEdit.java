/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entities.Academy;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
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
import javax.swing.JOptionPane;
import services.AcademyCRUD;

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class AcademyEdit implements Initializable {

    private int selectedAcademyId;
    private Academy selectedAcademy;
    private AcademyCRUD academyCRUD;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;
    
    @FXML
    private ImageView academy_image;
    
    @FXML
    private Button btnUpload;

    @FXML
    private ChoiceBox<String> categoryField;
    private final String[] types = {"football", "handball", "basketball", "volleyball", "baseball", "tennis", "golf"};
    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        academyCRUD = new AcademyCRUD();
        categoryField.getItems().addAll(types);
    }

    public void setSelectedAcademyId(int academyId) throws SQLException {
        this.selectedAcademyId = academyId;
        this.selectedAcademy = academyCRUD.getEntity(academyId);
        txtName.setText(selectedAcademy.getName());
    }

    public void handleSaveButtonClick(ActionEvent event) throws IOException {
        // Retrieve the Academy data from the fields
        int id = selectedAcademy.getId();
        String name = txtName.getText();
        String category = categoryField.getValue();

        if (name.trim().isEmpty() || category == null) {
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

        } else if (academyCRUD.academyExists(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Academy Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("An academy with the same name already exists");
            alert.showAndWait();
        } else {
            AcademyCRUD academyCRUD = new AcademyCRUD();
            Academy academy = new Academy();
            academy.setId(id);
            academy.setName(name);
            academy.setCategory(category);

            // Update the image if a new one is selected
            if (imageFile != null) {
                String destPath = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
                String imageName = generateUniqueName(imageFile); // Generate a unique name for the image
                File dest = new File(destPath + imageName); // Set the destination path for the image
                try {
                    Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    academy.setImageName(imageName);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                academy.setImageName("tester.jpg");
            }

            academyCRUD.updateEntity(academy);

            // Close the window
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();
            Parent academyListParent = FXMLLoader.load(getClass().getResource("../Gui/AcademyList.fxml"));
            Scene academyListScene = new Scene(academyListParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(academyListScene);
            window.show();
        }
    }
    
    @FXML
    private void upload_imageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterJPEG
                = new FileChooser.ExtensionFilter("JPEG files (*.JPEG)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterjpeg
                = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng, extFilterJPEG, extFilterjpeg);
        //Show open file dialog
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            String image_uri = imageFile.toURI().toString();
            // Display the selected image in an ImageView
            Image image = new Image(image_uri);
            academy_image.setImage(image);
        }
    }
    
    private String generateUniqueName(File imageFile) {
        String name = imageFile.getName();
        String extension = name.substring(name.lastIndexOf(".")).toLowerCase(); // Get the file extension
        String imageName = UUID.randomUUID().toString() + extension; // Generate a unique name with the same extension
        return imageName;
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