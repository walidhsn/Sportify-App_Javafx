/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SpinnerValueFactory;
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
import services.ApplyCRUD;
import services.CoachCRUD;
import entities.Apply;
import javafx.scene.control.Spinner;


/**
 * FXML Controller class
 *
 * @author ramib
 */
public class ApplyController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> nameField1;
    @FXML
    private Button btnSubmit;
    @FXML
    private ImageView backIcon;
    @FXML
    private ImageView apply_image;
    @FXML
    private Button btnUpload;
    @FXML
    private ImageView imgUpload;
    private File imageFile;
    
    private ApplyCRUD applyCRUD = new ApplyCRUD();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1, 1);
        nameField1.setValueFactory(spinner);
        
    }
    
    @FXML
    private void Apply(javafx.event.ActionEvent event) throws IOException {
        String name = nameField.getText();
        Integer age = nameField1.getValue();

        if (name.trim().isEmpty()) {
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
            
        } else {
            if (imageFile != null) {
                String destPath = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
//                String destPath = "C:/Users/ramib/Desktop/";
                String imageName = generateUniqueName(imageFile); // Generate a unique name for the image
                File dest = new File(destPath + imageName); // Set the destination path for the image
                try {
                    Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Apply apply = new Apply(name, age, imageName);
                    apply.setImageName(imageName);
                    applyCRUD.addEntity(apply);
                    System.out.println("Application added successfully");
                    redirect();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                String imageName = "nophoto.png";
                URL defaultImageUrl = getClass().getResource("../Ressource/icons/" + imageName);
                if (defaultImageUrl != null) {
                    File defaultImageFile = new File(defaultImageUrl.getPath());
                    String destPath = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
                    File dest = new File(destPath + imageName); 
                    try {
                        Files.copy(defaultImageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Apply academy = new Apply(name, age, imageName);
                        applyCRUD.addEntity(academy);
                        System.out.println("Apply added successfully");
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
            apply_image.setImage(image);
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
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyList_1.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void redirect() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyList_1.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    //Badwords filter
    private static final Set<String> BAD_WORDS = new HashSet<>();

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Badwords.txt"));
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