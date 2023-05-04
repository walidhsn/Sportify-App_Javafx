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
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import sportify.edu.entities.Sponsor;
import sportify.edu.services.SponsorService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Sponsor_view_ownerController implements Initializable {

    @FXML
    private TableView<Sponsor> table_sponsor;
    @FXML
    private TableColumn<Sponsor, String> name_col;
    @FXML
    private TableColumn<Sponsor, String> email_col;
    @FXML
    private TableColumn<Sponsor, String> tel_col;
    @FXML
    private TableColumn<Sponsor, String> details_col;
    @FXML
    private TableColumn<Sponsor, String> update_col;
    @FXML
    private TableColumn<Sponsor, String> delete_col;
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
    private int id_owner;
    private SponsorService ss;
    private List<Sponsor> list_sponsor;
    private List<Sponsor> list_sponsor_search;
    private ImageView icon_delete;
    private ImageView icon_update;
    private ImageView icon_view;

    public void setData(int id_owner) {
        this.id_owner = id_owner;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ss = new SponsorService();
        list_sponsor = ss.displayEntity();
        table_sponsor.setItems(FXCollections.observableArrayList(list_sponsor));

        name_col.setCellValueFactory(new PropertyValueFactory<>("nomSponsor"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("emailSponsor"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("telSponsor"));
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            list_sponsor_search = list_sponsor.stream()
                    .filter(event -> event.getNomSponsor().toLowerCase().contains(newValue.toLowerCase())
                    || event.getEmailSponsor().toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(event.getTelSponsor()).toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            table_sponsor.setItems(FXCollections.observableArrayList(list_sponsor_search));
        });
        // Delete Button : 
        delete_col.setCellFactory(column -> {
            return new TableCell<Sponsor, String>() {

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
                            Sponsor data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                ss.deleteEntity(data.getId());
                                refresh();
                            }
                        });
                    }
                }
            };
        });

        //Update Button : 
        update_col.setCellFactory(column -> {
            return new TableCell<Sponsor, String>() {

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
                            Sponsor data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/UPDATE_sponsor.fxml"));
                                    Parent root = loader.load();
                                    //UPDATE The Controller with Data :
                                    UPDATE_sponsorController controller = loader.getController();
                                    controller.setData(data,id_owner);
                                    //-----
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) table_sponsor.getScene().getWindow();
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

        //View Button : 
        details_col.setCellFactory(column -> {
            return new TableCell<Sponsor, String>() {

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
                                Sponsor data = getTableView().getItems().get(getIndex());
                                System.out.println(data);
                                System.out.println("selectedData: " + data.getId());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/DETAILS_sponsor.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                DETAILS_sponsorController controller = loader.getController();
                                controller.setData(data,id_owner);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_sponsor.getScene().getWindow();
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
    private void returnToEventList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/Event_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Event_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_sponsor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToAddSponsor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/ADD_sponsor.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            ADD_sponsorController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_sponsor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void refresh() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/Sponsor_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Sponsor_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
