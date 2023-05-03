/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Equipment;
import sportify.edu.entities.Reservation;
import sportify.edu.services.EquipmentCRUD;
import sportify.edu.services.ReservationService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class UPDATE_ReservationController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private JFXDatePicker dateReservation;
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private Spinner<Integer> nbPerson;
    @FXML
    private Button update_btn;
    private Reservation reservation;
    private int client_id;
    @FXML
    private ListView<String> list_equipment;
    private EquipmentCRUD ec;
    private List<Equipment> list_equipments;
    private Map<Integer, String> map_equipment;
    private List<String> selectedItems;
    private List<String> unselectedItems;
    private List<Equipment> selectedEquipments;
    private ReservationService rs;

    public void setData(Reservation reservation, int id_client) {
        this.reservation = reservation;
        this.client_id = id_client;
        dateReservation.setValue(this.reservation.getDateReservation().toLocalDate());
        startTime.setValue(this.reservation.getStartTime().toLocalTime());
        endTime.setValue(this.reservation.getEndTime().toLocalTime());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1, 1);// (min,max,startvalue,incrementValue)
        valueFactory.setValue(this.reservation.getNbPerson());
        nbPerson.setValueFactory(valueFactory);

        selectedEquipments = rs.myEquipments(this.reservation.getId());
        if (!selectedEquipments.isEmpty()) {
            for (Equipment item : selectedEquipments) {
                selectedItems.add(item.getName());
            }
        }
        System.out.println("Selected Equipment : " + selectedItems);
        list_equipments = ec.display();
        if (!list_equipments.isEmpty()) {
            for (Equipment item : list_equipments) {
                map_equipment.put(item.getId(), item.getName());
            }
        }        // Set the ListView and get the selected data
        list_equipment.setEditable(true);
        for (String value : map_equipment.values()) {
            list_equipment.getItems().add(value);
        }

        // Create the custom ListCell with the checkbox event listener
        list_equipment.setCellFactory(param -> new ListCell<String>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    String item = getItem();
                    if (checkBox.isSelected()) {
                        selectedItems.add(item);
                        unselectedItems.remove(item);
                    } else {
                        selectedItems.remove(item);
                        unselectedItems.add(item);
                    }
                });
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    checkBox.setSelected(selectedItems.contains(item));
                    setGraphic(checkBox);
                }
            }
        });

        list_equipment.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list_equipment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Do nothing here
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.reservation = new Reservation();
        ec = new EquipmentCRUD();
        list_equipments = new ArrayList<>();
        map_equipment = new HashMap<>();
        selectedItems = new ArrayList<>();
        unselectedItems = new ArrayList<>();
        selectedEquipments = new ArrayList<>();
        rs = new ReservationService();
        Image btn_icon = new Image("/resources/back-arrow.png");
        backBtn_icon.setImage(btn_icon);
    }

    @FXML
    private void returnListReservation(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/reservation/reservation_view_client.fxml"));
            Parent root = fxmlLoader.load();
            //UPDATE The Controller with Data :
            Reservation_view_client controller = fxmlLoader.getController();
            controller.setData(this.client_id);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateReservation(ActionEvent event) {
        List<String> result;

        // Get the selected date
        LocalDate selectedDate = dateReservation.getValue();

        // Get the start and end times
        LocalTime selectedStartTime = startTime.getValue();
        LocalTime selectedEndTime = endTime.getValue();
        if (selectedDate != null && selectedStartTime != null && selectedEndTime != null) {

            // Combine the date and time values to create the start and end date/time objects
            LocalDateTime startDateTime = LocalDateTime.of(selectedDate, selectedStartTime);
            LocalDateTime endDateTime = LocalDateTime.of(selectedDate, selectedEndTime);

            // Get the number of people and equipment selection
            int numberOfPeople = nbPerson.getValue();
            if (startDateTime.isBefore(LocalDateTime.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Date Must be in the Future");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
                dateReservation.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                new animatefx.animation.Shake(dateReservation).play();
            } else {
                dateReservation.setStyle(null);
                // Create a new Reservation object with the selected values
                Reservation newReservation = new Reservation();
                //-----------------------------------------------------------------------------------------------
                newReservation.setId(this.reservation.getId());
                newReservation.setClient_id(this.client_id);
                newReservation.setTerrain_id(this.reservation.getTerrain_id());
                //-----------------------------------------------------------------------------------------------
                newReservation.setDateReservation(startDateTime);
                newReservation.setStartTime(startDateTime);
                newReservation.setEndTime(endDateTime);
                newReservation.setNbPerson(numberOfPeople);
                newReservation.setResStatus(this.reservation.isResStatus());
                newReservation.setUpdated_at(LocalDateTime.now());

                result = rs.reserver_update(newReservation);
                // Call the method to check for conflicts and add the reservation
                if (result.get(0).equals("ok")) {
                    for (String item : unselectedItems) {
                        for (Map.Entry<Integer, String> element : map_equipment.entrySet()) {
                            if (element.getValue().equals(item)) {
                                rs.delete_equipment_reservation(this.reservation.getId(),element.getKey());
                            }
                        }
                    }
                    for (String item : selectedItems) {
                        for (Map.Entry<Integer, String> element : map_equipment.entrySet()) {
                            if (element.getValue().equals(item)) {
                                 rs.add_equipment_reservation(reservation.getId(),element.getKey());
                            }
                        }
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText(result.get(1));
                    alert.setHeaderText(null);
                    alert.show();
                    redirectToListReservation();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(result.get(1));
                    alert.setTitle("Problem");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
        } else if (selectedDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter the missing information (Date) ");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            dateReservation.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(dateReservation).play();
        } else if (selectedStartTime == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter the missing information (Start Time)");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            startTime.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(startTime).play();
        } else if (selectedEndTime == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter the missing information (End Time)");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            endTime.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(endTime).play();
        }
    }

    private void redirectToListReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Reservation_view_client.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Reservation_view_client controller = loader.getController();
            controller.setData(this.client_id);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
