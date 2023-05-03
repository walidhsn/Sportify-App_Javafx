/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import sportify.edu.entities.Equipment;
import sportify.edu.entities.Supplier;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sportify.edu.services.EquipmentCRUD;
import sportify.edu.services.SupplierCRUD;

/**
 * FXML Controller class
 *
 * @author MOLKA
 */
public class EquipmentAdd implements Initializable {

    @FXML
    private ChoiceBox<String> categoryField;
    private final String[] types = {"football", "handball", "basketball", "volleyball", "baseball", "tennis", "golf"};

    @FXML
    private ComboBox<String> SupplierCombo;

    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    private EquipmentCRUD academyCRUD = new EquipmentCRUD();
    @FXML
    private Button btnAdd;

    private EquipmentCRUD eqCRUD = new EquipmentCRUD();
    @FXML
    private Button btnBack;
    private File imageFile;
    @FXML
    private Button btnUpload;
    @FXML
    private ImageView eq_img;
    @FXML
    private TextField imageEventField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryField.getItems().addAll(types);
        SupplierCombo();

    }

    @FXML
    public void SupplierCombo() {
        List<Supplier> SupplierList = new SupplierCRUD().display();
        List<String> listP = new ArrayList<>();

        SupplierList.forEach((data) -> {
            listP.add(data.getName());
        });

        ObservableList listData = FXCollections.observableArrayList(listP);
        SupplierCombo.setItems(listData);
    }

    @FXML
    private void EqAdd(javafx.event.ActionEvent event) throws IOException {
        String name = nameField.getText();
        String category = categoryField.getValue();
        String img = imageEventField.getText();

        if (name == null || name.trim().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a name");
            alert.showAndWait();
        } else if (!name.matches("^[a-zA-Z ]+$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid name with only alphabetic characters and spaces");
            alert.showAndWait();
        } else if (eqCRUD.itemExists(name)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An equipment with this name already exists. Please enter a different name");
            alert.showAndWait();
        }

        if (category.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a category");
            alert.showAndWait();
            return;
        }

        if (!category.matches("^[a-zA-Z]*$")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid category with only alphabetic characters");
            alert.showAndWait();
            return;
        }

        String quantityText = quantityField.getText();
        if (quantityText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a quantity");
            alert.showAndWait();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid quantity");
            alert.showAndWait();
            return;
        }

        String priceText = priceField.getText();
        if (priceText.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a price");
            alert.showAndWait();
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid price");
            alert.showAndWait();
            return;
        }
        if (imageFile != null) {
            String destPath = "C:/Uploads/";
//                String destPath = "C:/Users/ramib/Desktop/";
            // String imageName = generateUniqueName(imageFile); // Generate a unique name for the image
            // File dest = new File(destPath + imageName); // Set the destination path for the image
            try {
                // Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Equipment eq = new Equipment(name, category, quantity, price, img);
                eqCRUD.addEntity(eq);
                System.out.println("Equipment added successfully");
                Parent equipmentListParent = FXMLLoader.load(getClass().getResource("../gui/EquipmentList.fxml"));
                Scene equipmentListScene = new Scene(equipmentListParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(equipmentListScene);
                window.show();
//                    redirect();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
           
            int id_supp = new SupplierCRUD().recupererIdSupplierParNom(SupplierCombo.getSelectionModel().getSelectedItem());
            String imageName = "nophoto.png";
            URL defaultImageUrl = getClass().getResource("../resources/icons/" + imageName);
            if (defaultImageUrl != null) {
                File defaultImageFile = new File(defaultImageUrl.getPath());
                String destPath = "C:/Uploads/";
                File dest = new File(destPath + imageName);
                try {
                    Files.copy(defaultImageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Equipment eq = new Equipment(name, category, quantity, price, img,id_supp);
                    eqCRUD.addEntity(eq);
                    System.out.println("Equipment added successfully");
                    Parent equipmentListParent = FXMLLoader.load(getClass().getResource("../gui/EquipmentList.fxml"));
                    Scene equipmentListScene = new Scene(equipmentListParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(equipmentListScene);
                    window.show();
//                    redirect();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("Unable to find default image");
            }
        }
//        Equipment eq = new Equipment(name, category, quantity, price,imageName);
//        eqCRUD.addEntity(eq);
//        System.out.println("Equipment added successfully");

    }

    @FXML
    private void upload_imageAction(javafx.event.ActionEvent event) throws FileNotFoundException, IOException {
        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\\\Users\\\\MOLKA\\\\OneDrive\\\\Documents\\\\NetBeansProjects\\\\Sportify_Desktop\\\\imageP\\\\" + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path = file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            eq_img.setImage(img);
            imageEventField.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();
        } else {
            System.out.println("error");
        }
//        FileChooser fileChooser = new FileChooser();
//        //Set extension filter
//        FileChooser.ExtensionFilter extFilterJPG
//                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
//        FileChooser.ExtensionFilter extFilterjpg
//                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
//        FileChooser.ExtensionFilter extFilterPNG
//                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
//        FileChooser.ExtensionFilter extFilterpng
//                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
//        FileChooser.ExtensionFilter extFilterJPEG
//                = new FileChooser.ExtensionFilter("JPEG files (*.JPEG)", "*.JPEG");
//        FileChooser.ExtensionFilter extFilterjpeg
//                = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");
//        fileChooser.getExtensionFilters()
//                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng, extFilterJPEG, extFilterjpeg);
//        //Show open file dialog
//        imageFile = fileChooser.showOpenDialog(null);
//        if (imageFile != null) {
//            String image_uri = imageFile.toURI().toString();
//            // Display the selected image in an ImageView
//            Image image = new Image(image_uri);
//            eq_img.setImage(image);
//        }

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
            Parent root = FXMLLoader.load(getClass().getResource("../gui/EquipmentList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void redirect() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/EquipmentList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void afficherEquipement(ActionEvent event) {

        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("../gui/afficherEvenement.fxml"));
            nameField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
