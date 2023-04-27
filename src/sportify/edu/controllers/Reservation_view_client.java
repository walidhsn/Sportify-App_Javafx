/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Reservation;
import sportify.edu.entities.Terrain;
import sportify.edu.services.ReservationService;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Reservation_view_client implements Initializable {

    @FXML
    private TableColumn<Reservation, String> date_col;
    @FXML
    private TableColumn<Reservation, String> startTime_col;
    @FXML
    private TableColumn<Reservation, String> endTime_col;
    @FXML
    private TableColumn<Reservation, Integer> nbPerson_col;
    @FXML
    private TableColumn<Reservation, Boolean> status_col;
    @FXML
    private TableColumn<Reservation, String> details_col;
    @FXML
    private TableColumn<Reservation, String> update_col;
    @FXML
    private TableColumn<Reservation, String> delete_col;
    @FXML
    private TableColumn<Reservation, String> montant_total;
    @FXML
    private TextField search_text;

    private int client_id;

    @FXML
    private TableView<Reservation> table_reservation;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    private List<Reservation> reservation_list;
    private List<Reservation> reservation_list_search;
    private ImageView icon_delete, icon_update, icon_view, icon_card;
    
    private ReservationService rs;

    public void setData(int client) {
        client_id = client;
        reservation_list = rs.myReservation(client_id);
        System.out.println(reservation_list);
        table_reservation.setItems(FXCollections.observableArrayList(reservation_list));
        date_col.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        nbPerson_col.setCellValueFactory(new PropertyValueFactory<>("nbPerson"));
        startTime_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        montant_total.setCellValueFactory(cellData -> {
            Reservation reservation = cellData.getValue();
            TerrainService ts= new TerrainService();
            Terrain t = ts.diplay(reservation.getTerrain_id());
            double totalAmount = reservation.getNbPerson() * t.getRentPrice();
            String total_txt = String.format("%.2f", totalAmount)+" Dt";
            return new SimpleStringProperty(total_txt);
        });
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            reservation_list_search = reservation_list.stream()
                    .filter(reservation -> String.valueOf(reservation.getNbPerson()).contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getDateReservation()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.isResStatus()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getStartTime()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getEndTime()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getId()).toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            table_reservation.setItems(FXCollections.observableArrayList(reservation_list_search));
        });
        //payment Button : 
        status_col.setCellFactory(column -> {
            return new TableCell<Reservation, Boolean>() {
                final Button paymentButton = new Button();

                // Set the button properties
                {
                    icon_card = new ImageView(new Image("/resources/card.png"));
                    icon_card.setFitWidth(30);
                    icon_card.setFitHeight(30);
                    paymentButton.setGraphic(icon_card);
                    paymentButton.setText("Pay");
                    paymentButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(Boolean resStatus, boolean empty) {
                    super.updateItem(resStatus, empty);
                    Label paidLabel = new Label("Paid");
                    // Set the label style
                    paidLabel.setStyle("-fx-text-fill: green;");
                    if (empty || resStatus == null) {
                        setGraphic(null);
                    } else if (resStatus) {
                        setGraphic(paidLabel);
                    } else {
                        setGraphic(paymentButton);
                        paymentButton.setOnAction((ActionEvent event) -> {
                        });
                    }
                }
            };
        });
        // Delete Button : 
        delete_col.setCellFactory(column -> {
            return new TableCell<Reservation, String>() {

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
                            Reservation data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                rs.deleteEntity(data.getId());
                                refresh();
                            }
                        });
                    }
                }
            };
        });

        //Update Button : 
        update_col.setCellFactory(column -> {
            return new TableCell<Reservation, String>() {

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
                            Reservation data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/UPDATE_Reservation.fxml"));
                                    Parent root = loader.load();
                                    //UPDATE The Controller with Data :
                                    UPDATE_ReservationController controller = loader.getController();
                                    controller.setData(data,client_id);
                                    //-----
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) table_reservation.getScene().getWindow();
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
            return new TableCell<Reservation, String>() {

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
                                Reservation data = getTableView().getItems().get(getIndex());
                                System.out.println(data);
                                System.out.println("selectedData: " + data.getId());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/DETAILS_Reservation.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                DETAILS_ReservationController controller = loader.getController();
                                int id_client = client_id;
                                controller.setData(data, id_client);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_reservation.getScene().getWindow();
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservation_list = new ArrayList<>();
        reservation_list_search = new ArrayList<>();
        rs = new ReservationService();
        
    }

    @FXML
    private void returnListTerrainClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Terrain/Terrain_view_client.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_reservation.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void refresh() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Reservation_view_client.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Reservation_view_client controller = loader.getController();
            controller.setData(this.client_id);
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_reservation.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
