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
import sportify.edu.entities.Commande;
import sportify.edu.services.CommandeCrud;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class Commande_view_ownerController implements Initializable {

    @FXML
    private TableColumn<Commande, String> name_col;
    @FXML
    private TableColumn<Commande, String> last_nameCol;
    @FXML
    private TableColumn<Commande, String> tel_col;
    @FXML
    private TableColumn<Commande, String> email_col;
    @FXML
    private TableColumn<Commande, String> city_col;
    @FXML
    private TableColumn<Commande, String> adr_col;
    @FXML
    private TableColumn<Commande, String> total_col;
    @FXML
    private TableColumn<Commande, String> delete_col;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TextField search_text;
    @FXML
    private TableView<Commande> table_com;
    
    private List<Commande> list_Com;
    private CommandeCrud cc;
    
    private ImageView icon_delete;
    private ImageView icon_view;
    @FXML
    private TableColumn<Commande, String> view_col;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         cc= new CommandeCrud();
       Commande p=new Commande();
       
        list_Com=cc.trouver_toutes_les_commandes();
        table_com.setItems(FXCollections.observableArrayList(list_Com));
        name_col.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        last_nameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        adr_col.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("tel"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        
        delete_col.setCellFactory(column -> {
            return new TableCell<Commande, String>() {

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
                            Commande data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                cc.supprimer_commande(data.getId());
                                //refresh();
                             }
                        });
                    }
                }
            };
        });
        //View Button : 
        view_col.setCellFactory(column -> {
            return new TableCell<Commande, String>() {

                final Button viewButton = new Button();

                // Set the button properties
                {
                    icon_view = new ImageView(new Image("/resources/eye.png"));
                    icon_view.setFitWidth(20);
                    icon_view.setFitHeight(20);
                    viewButton.setGraphic(icon_view);
                    viewButton.setText("View");
                    viewButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                        viewButton.setOnAction((ActionEvent event) -> {
                            try {
                                Commande data = getTableView().getItems().get(getIndex());
                                System.out.println(data.getId());
                                System.out.println("selectedData: " + data.getId()); 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/commande/commande_items_view.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                view_listController controller = loader.getController();
                                controller.setInformation_Commande(data);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_com.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
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
            Parent root = FXMLLoader.load(getClass().getResource("../gui/produit/Produit_view_owner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
