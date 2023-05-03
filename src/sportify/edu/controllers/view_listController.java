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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Commande;
import sportify.edu.entities.Historique;
import sportify.edu.services.HistoriquesCrud;
import sportify.edu.services.SecurityService;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class view_listController implements Initializable {

    @FXML
    private TableView<Historique> table_com;
    @FXML
    private TableColumn<Historique, String> name_col;
    @FXML
    private TableColumn<Historique, String> delete_col;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TableColumn<Historique, String> q_col;
    @FXML
    private TableColumn<Historique, String> pr_col;
    
    private List<Historique> list_Com;
    private HistoriquesCrud cc;
    Historique hist= new Historique();
    Commande c;
    private ImageView icon_delete;
    public void setInformation_Commande(Commande c) {
         HistoriquesCrud cc = new HistoriquesCrud();
        //list_Com=cc.trouver_historique_par_commande_id(c.getId());
         hist=cc.trouver_historique_par_commande_id2(c.getId());
        System.out.println(c.getId());
        list_Com=cc.trouver_historique_par_userid(SecurityService.getCurrentUtilisateur().getId());
        table_com.setItems(FXCollections.observableArrayList(list_Com));
        name_col.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        pr_col.setCellValueFactory(new PropertyValueFactory<>(String.valueOf("prix")));
        q_col.setCellValueFactory(new PropertyValueFactory<>(String.valueOf("quantite")));
        
        delete_col.setCellFactory(column -> {
            return new TableCell<Historique, String>() {

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
                            Historique data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                cc.supprimer_historique(data.getId());
                                //refresh();
                             }
                        });
                    }
                }
            };
        });
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Historique hist =new Historique();
        Image btn_icon = new Image("/resources/back-arrow.png");
        backBtn_icon.setImage(btn_icon);
      /*  HistoriquesCrud cc = new HistoriquesCrud();
        System.out.println(id_c);
       
        list_Com=  cc.trouver_historique_par_commande_id(id_c);
        hist=cc.trouver_historique_par_commande_id2(id_c);
        
        table_com.setItems(FXCollections.observableArrayList(hist));
        name_col.setCellValueFactory(new PropertyValueFactory<>(hist.getLibelle()));
        pr_col.setCellValueFactory(new PropertyValueFactory<>(String.valueOf(hist.getPrix())));
        q_col.setCellValueFactory(new PropertyValueFactory<>(String.valueOf(hist.getQuantite())));
        
        delete_col.setCellFactory(column -> {
            return new TableCell<Historique, String>() {

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
                            Historique data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                cc.supprimer_historique(data.getId());
                                //refresh();
                             }
                        });
                    }
                }
            };
        });
*/
        
    }    

    @FXML
    private void returnListCommande(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("../gui/commande/commande_view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
