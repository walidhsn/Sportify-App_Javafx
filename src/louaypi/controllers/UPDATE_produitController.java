/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import louaypi.entities.Categorie;
import louaypi.entities.Produit;
import louaypi.services.CategorieCrud;
import louaypi.services.ProduitCRUD;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class UPDATE_produitController implements Initializable {

    @FXML
    private TextField libelle;
    @FXML
    private TextField reference;
    @FXML
    private Button upload_image_btn;
    @FXML
    private Spinner<Double> prix;
    @FXML
    private Spinner<Integer> stock;
    @FXML
    private ChoiceBox<String> categorie_choice;
    @FXML
    private ImageView image_produit;
    @FXML
    private Button add_btn;
    @FXML
    private Button back_btn;
    
    private File file;
    private Produit produit;
    private CategorieCrud cg;
    private List<Categorie> list_categorie;
    
    /**
     * Initializes the controller class.
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
        Image image;
        String url_imageDirectory = "file:C:/Users/louay/OneDrive/Bureau/WEBPI(finale)/WEBPI(finale)/public/uploads/produit/";            

        libelle.setText(this.produit.getLibelle());
        reference.setText(this.produit.getRefernce());
        
        
        if (this.produit.getImage() != null) {
            String url_image=url_imageDirectory + this.produit.getImage();
            System.out.println(url_image);
            image = new Image(url_image);
            image_produit.setImage(image);
        }
        int id_cat = this.produit.getCategorie();
        Categorie categorie = cg.trouver_categorie_par_id(id_cat);
        
        categorie_choice.setValue(categorie.getNom_cat());
       
        SpinnerValueFactory<Integer> valueFactory_s = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1, 1);// (min,max,startvalue,incrementValue)
        valueFactory_s.setValue(this.produit.getQuantite());
        SpinnerValueFactory<Double> valueFactory_p = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 9999999.0, 1, 0.25);
        valueFactory_p.setValue((double) this.produit.getPrix());
        prix.setValueFactory(valueFactory_p);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         cg = new CategorieCrud();
        list_categorie = cg.lister_categories();
        List<String> types = new ArrayList<>();
        for (Categorie cat : list_categorie) {
            types.add(cat.getNom_cat());
        }
        SpinnerValueFactory<Double> valueFactory_p = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 99999999.0, 1, 0.25);
        SpinnerValueFactory<Integer> valueFactory_s = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999999, 1, 1);
        prix.setValueFactory(valueFactory_p);
        stock.setValueFactory(valueFactory_s);
        categorie_choice.getItems().addAll(types);
    }    

    @FXML
    private void upload_image(ActionEvent event) {
     FileChooser fileChooser = new FileChooser();
        // Set extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg")
        );
        // Show open file dialog
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imageUri = file.toURI().toString();
            // Display the selected image in an ImageView
            Image image = new Image(imageUri);
            image_produit.setImage(image);
        }
    }


    @FXML
    private void redirectToListProduit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/Produit_view_owner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void update_produit(ActionEvent event) {
        ProduitCRUD pc = new ProduitCRUD();
        String lib = libelle.getText();
        Double pri = prix.getValue();
        Integer sto = stock.getValue();
        String ref = reference.getText();
        int id_cat = -1;
        String categorie = categorie_choice.getValue();
        for (Categorie cat : list_categorie) {
            if (categorie.equals(cat.getNom_cat())) {
                id_cat = cat.getId();
                break;
            }
        }
        if (lib.isEmpty() && lib.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Produit 'Name'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (categorie.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Categorie' must be selected");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (ref.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Reference' must be inputed");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            if (file != null) {
                String destPath = "C:/Users/louay/OneDrive/Bureau/WEBPI(finale)/WEBPI(finale)/public/uploads/produit/";
                String imageName = generateUniqueName(file); // Generate a unique name for the image
                File dest = new File(destPath + imageName); // Set the destination path for the image
                try {
                    Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING); // Copy the image to the destination folder

                    Produit p = new Produit(ref, lib, pri.floatValue(), imageName, sto, id_cat, LocalDateTime.now());
                    pc.ajouter_produit(p);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Added .");
                    alert.setHeaderText(null);
                    alert.show();
                    //redirectToListProduit();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            } else {
               this.produit.setLibelle(lib);
this.produit.setPrix(pri.floatValue());
this.produit.setQuantite(sto);
this.produit.setRefernce(ref);
this.produit.setCategorie(id_cat); 
             //Produit p = new Produit(ref, lib, pri.floatValue(), sto, id_cat, LocalDateTime.now());
            pc.modifier_produit(this.produit);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Updated .");
            alert.setHeaderText(null);
            alert.show();
            redirectToListProduit(event);
        }
    }
                /*Produit p = new Produit(ref, lib, pri.floatValue(), sto, id_cat, LocalDateTime.now());
                pc.ajouter_produit(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added .");
                alert.setHeaderText(null);
                alert.show();
                redirectToListProduit(event);
            }
        }*/
        
    }
    private String generateUniqueName(File file) {
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".")).toLowerCase(); // Get the file extension
        String imageName = UUID.randomUUID().toString() + extension; // Generate a unique name with the same extension
        return imageName;
    }
    
}
