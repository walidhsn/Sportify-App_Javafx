/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import sportify.edu.entities.Academy;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
import sportify.edu.services.AcademyCRUD;
import sportify.edu.Ressource.Constants;



/**
 * FXML Controller class
 *
 * @author ramib
 */
public class AcademyAdd implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> categoryField;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView backIcon;
    @FXML
    private Button btnUpload;
    @FXML
    private ImageView imgUpload;
    @FXML
    private ImageView academy_image;
    
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    
    private File imageFile;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryField.getItems().addAll(Constants.categories);
//        Image btn_icon = new Image("/resources/icons/back.png");
//        backIcon.setImage(btn_icon);

    }
    
    @FXML
    private void AcademyAdd(javafx.event.ActionEvent event) throws IOException {
        String name = nameField.getText();
        String category = categoryField.getValue();

        if (name.trim().isEmpty() || category == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else if (!name.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Name Field Can Only Contain Letters");
            alert.setContentText("Please enter a valid name");
            alert.showAndWait();
        
        } else if (containsBadWords(name)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Academy Name");
            alert.setContentText("The name contains a bad word");
            alert.showAndWait();

        } else if (academyCRUD.academyExists(name)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Academy Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("An academy with the same name already exists");
            alert.showAndWait();
        } else {
            if (imageFile != null) {
//                String destPath = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
                String destPath = Constants.DEST_PATH;
//                String destPath = "C:/Users/ramib/Desktop/";
                String imageName = generateUniqueName(imageFile); // Generate a unique name for the image
                File dest = new File(destPath + imageName); // Set the destination path for the image
                try {
                    Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Academy academy = new Academy(name, category, imageName);
                    academy.setImageName(imageName);
                    academyCRUD.addEntity(academy);
                    System.out.println("Academy added successfully");
                    redirect();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                String imageName = "nophoto.png";
                URL defaultImageUrl = getClass().getResource(Constants.Icons + imageName);
                if (defaultImageUrl != null) {
                    File defaultImageFile = new File(defaultImageUrl.getPath());
//                    String destPath = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
                    String destPath = Constants.DEST_PATH;
                    File dest = new File(destPath + imageName); 
                    try {
                        Files.copy(defaultImageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Academy academy = new Academy(name, category, imageName);
                        academyCRUD.addEntity(academy);
                        System.out.println("Academy added successfully");
                        redirect();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    System.out.println("Unable to find default image");
                }
            }
        }
    }
    
    @FXML
    private void upload_imageAction(javafx.event.ActionEvent event) {
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
            Parent root = FXMLLoader.load(getClass().getResource(Constants.AcademyList));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void redirect() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Constants.AcademyList));
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    //Badwords filter
    private static final Set<String> BAD_WORDS = new HashSet<>();

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Constants.Badwords));
            String line;
            while ((line = reader.readLine()) != null) {
                BAD_WORDS.add(line.trim().toLowerCase());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean containsBadWords(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (BAD_WORDS.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    

}