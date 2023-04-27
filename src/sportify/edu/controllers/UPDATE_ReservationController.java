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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Reservation;
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
    private ChoiceBox<?> equipments;
    @FXML
    private Button update_btn;
    private Reservation reservation;
    private int client_id;

    public void setData(Reservation reservation, int id_client) {
        this.reservation = reservation;
        this.client_id = id_client;
        dateReservation.setValue(this.reservation.getDateReservation().toLocalDate());
        startTime.setValue(this.reservation.getStartTime().toLocalTime());
        endTime.setValue(this.reservation.getEndTime().toLocalTime());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1, 1);// (min,max,startvalue,incrementValue)
        valueFactory.setValue(this.reservation.getNbPerson());
        nbPerson.setValueFactory(valueFactory);
              
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.reservation = new Reservation();
        Image btn_icon = new Image("/resources/back-arrow.png");
        backBtn_icon.setImage(btn_icon);
    }

    @FXML
    private void returnListReservation(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/reservation/reservation_view_client.fxml"));
            Parent root = fxmlLoader.load();
            //UPDATE The Controller with Data :
            Reservation_view_client controller = fxmlLoader.getController();
            controller.setData(this.client_id);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);           
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void updateReservation(ActionEvent event) {
        List<String> result;
        ReservationService rs = new ReservationService();
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
            } else {
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
                result = rs.reserver_update(newReservation);
                // Call the method to check for conflicts and add the reservation
                if (result.get(0).equals("ok")) {
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
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please Enter the missing informations");
                    alert.setTitle("Problem");
                    alert.setHeaderText(null);
                    alert.showAndWait();
        }
    }
    
    private void redirectToListReservation(){
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
