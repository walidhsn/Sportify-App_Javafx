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
import sportify.edu.entities.Produit;
import sportify.edu.services.CategorieCrud;
import sportify.edu.services.ProduitCRUD;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class Produit_view_ownerController implements Initializable {

    @FXML
    private TableColumn<Produit, String> name_col;
    @FXML
    private TableColumn<Produit, String> updatedAt_col;
    @FXML
    private TableColumn<Produit, String> update_col;
    @FXML
    private TableColumn<Produit, String> delete_col;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private Button add_btn;
    @FXML
    private ImageView add_icon;
    @FXML
    private TableView<Produit> table_prod;
    @FXML
    private TableColumn<Produit, String> cat_col;
    @FXML
    private TableColumn<Produit, Double> prix_col;
    @FXML
    private TableColumn<Produit, Integer> sto_col;
    @FXML
    private TableColumn<Produit, String> ref_col1;
    
    private List<Produit> list_prod;
    private ProduitCRUD pc;
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
     
        pc= new ProduitCRUD();
       Produit p=new Produit();
       
        list_prod=pc.listerProduits();
        table_prod.setItems(FXCollections.observableArrayList(list_prod));
        name_col.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
        sto_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        ref_col1.setCellValueFactory(new PropertyValueFactory<>("refernce"));
        updatedAt_col.setCellValueFactory(new PropertyValueFactory<>("updated_at"));
        sto_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        
        Image icon = new Image("/resources/add.png");
        add_icon.setImage(icon);
        delete_col.setCellFactory(column -> {
            return new TableCell<Produit, String>() {

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
                            Produit data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                pc.supprimer_produit(data);
                                refresh();
                             }
                        });
                    }
                }
            };
        });

        //Update Button : 
        update_col.setCellFactory(column -> {
            return new TableCell<Produit, String>() {

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
                            Produit data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());                           
                            if (btn.get() == ButtonType.OK) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/louaypi/gui/produit/UPDATE_produit.fxml"));
                                    Parent root = loader.load();
                                    //UPDATE The Controller with Data :
                                    UPDATE_produitController controller = loader.getController();
                                    controller.setProduit(data);
                                    //-----
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) table_prod.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
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
    private void returnListProduitClient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/Produit_view_client.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToAddProduit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/Add_produit.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/Produit_view_owner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_prod.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToChart(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/stat.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
