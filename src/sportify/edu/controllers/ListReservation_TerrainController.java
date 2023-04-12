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
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class ListReservation_TerrainController implements Initializable {

    @FXML
    private TableView<Reservation> table_reservation;
    @FXML
    private TableColumn<Reservation, String> client_col;
    @FXML
    private TableColumn<Reservation, String> dateReservation_col;
    @FXML
    private TableColumn<Reservation, String> startTime_col;
    @FXML
    private TableColumn<Reservation, String> endTime_col;
    @FXML
    private TableColumn<Reservation, Boolean> status_col;
    @FXML
    private TableColumn<Reservation, Integer> nbPerson_col;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    private List<Reservation> reservation_list;
    private List<Reservation> reservation_list_search;
    private Terrain terrain;
    @FXML
    private TextField search_text;

    public void setData(Terrain terrain) {
        this.terrain = terrain;
        ReservationService rs = new ReservationService();
        reservation_list = rs.terrain_reservations(terrain.getId());
        System.out.println(reservation_list);
        if (!reservation_list.isEmpty()) {
            table_reservation.setItems(FXCollections.observableArrayList(reservation_list));
            dateReservation_col.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
            startTime_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endTime_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            status_col.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
            nbPerson_col.setCellValueFactory(new PropertyValueFactory<>("nbPerson"));
            /*client_col.setCellFactory(column -> {
                return new TableCell<Reservation, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText("To-DO");
                        }
                    }
                };
            });*/
            status_col.setCellFactory(column -> {
                return new TableCell<Reservation, Boolean>() {

                    @Override
                    protected void updateItem(Boolean resStatus, boolean empty) {
                        super.updateItem(resStatus, empty);
                        Label paidLabel = new Label("Paid");
                        Label unpaidLabel = new Label("UnPaid");
                        // Set the label style
                        paidLabel.setStyle("-fx-text-fill: green;");
                        unpaidLabel.setStyle("-fx-text-fill: red;");
                        if (empty || resStatus == null) {
                            setGraphic(null);
                        } else if (resStatus) {
                            setGraphic(paidLabel);
                        } else {
                            setGraphic(unpaidLabel);
                        }
                    }
                };
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
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservation_list = new ArrayList<>();
        terrain = new Terrain();
        Image btn_icon = new Image("/resources/back-arrow.png");
        backBtn_icon.setImage(btn_icon);

    }

    @FXML
    private void returnToDetailsTerrain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/DETAILS_Terrain.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            DETAILS_TerrainController controller = loader.getController();
            controller.setInformation_Terrain(this.terrain);
            //-----
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
