/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Categorie;
import sportify.edu.services.CategorieCrud;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class categorie_view_controller implements Initializable {

    @FXML
    private TableColumn<Categorie, Integer> id_col;
    @FXML
    private TableColumn<Categorie, String> name_col;
    @FXML
    private TableColumn<Categorie, String> update_col;
    @FXML
    private TableColumn<Categorie, String> delete_col;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TextField search_text;
    @FXML
    private Button add_btn;
    @FXML
    private ImageView add_icon;
    @FXML
    private TableView<Categorie> table_cat;
    
    private List<Categorie> list_cat;
    private CategorieCrud cc;
    private ImageView icon_delete;
    private ImageView icon_update;
    private ImageView icon_view;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         cc= new CategorieCrud();
         Categorie c=new Categorie();
         list_cat=cc.lister_categories();
         table_cat.setItems(FXCollections.observableArrayList(list_cat));
         id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
         name_col.setCellValueFactory(new PropertyValueFactory<>("nom_cat"));
         Image icon = new Image("/resources/add.png");
        add_icon.setImage(icon);
        delete_col.setCellFactory(column -> {
            return new TableCell<Categorie, String>() {

                final Button deleteButton = new Button();

                // Set the button properties
                 {
                    icon_delete = new ImageView(new Image("/resources/delete.png"));
                    icon_delete.setFitWidth(20);
                    icon_delete.setFitHeight(20);
                    deleteButton.setGraphic(icon_delete);
                    deleteButton.setText("Delete");
                    deleteButton.setContentDisplay(ContentDisplay.LEFT);
                }
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Confirmation");
                            alert.setContentText("Do you really want to delete this?");
                            Optional<ButtonType> btn = alert.showAndWait();
                            Categorie data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                cc.supprimer_categorie(data.getId());
                                refresh();
                             }
                        });
                    }
                }
            };
        });

        //Update Button : 
        update_col.setCellFactory(column -> {
            return new TableCell<Categorie, String>() {

                final Button updateButton = new Button();

                // Set the button properties
                {
                    icon_update = new ImageView(new Image("/resources/update.png"));
                    icon_update.setFitWidth(20);
                    icon_update.setFitHeight(20);
                    updateButton.setGraphic(icon_update);
                    updateButton.setText("Update");
                    updateButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    Categorie cat = table_cat.getSelectionModel().getSelectedItem();
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                        updateButton.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");

                            alert.setHeaderText("Update Confirmation");
                            alert.setContentText("Do you really want to update this?");

                            Optional<ButtonType> btn = alert.showAndWait();
                            Categorie data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());                           
                            if (btn.get() == ButtonType.OK) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/categorie/Update_Categorie.fxml"));
                                    Parent root = loader.load();
                                    //UPDATE The Controller with Data :
                                    Update_categorieController controller = loader.getController();
                                    controller.setCategorie(data);
                                    //-----
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) table_cat.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                    
                                   //
                                    
                                    
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                }
            };
        });
        
    
    }    

    @FXML
    private void returnListCategorie(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/produit/Produit_view_owner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToAddCategorie(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/categorie/ADD_categorie.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void refresh() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../gui/Produit/Categorie_view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_cat.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
