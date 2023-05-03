/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Equipment;
import sportify.edu.entities.Reservation;
import sportify.edu.entities.Terrain;
import sportify.edu.services.ReservationService;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class DETAILS_ReservationController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private TextField nbPerson;
    @FXML
    private TextField status;
    @FXML
    private TextField dateReservation;
    @FXML
    private TextField stadiumName;

    private Reservation reservation;
    private int client_id;
    @FXML
    private TextField terrain_type;
    @FXML
    private ImageView terrain_image;
    @FXML
    private TextField total_price;

    public void setData(Reservation reservation, int client_id) {
        this.reservation = reservation;
        this.client_id = client_id;
        ReservationService rs = new ReservationService();
        List<Equipment> list_eq = rs.myEquipments(this.reservation.getId());

        TerrainService ts = new TerrainService();
        Terrain t = ts.diplay(reservation.getTerrain_id());
        DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        startTime.setText(formatter_time.format(reservation.getStartTime()));
        endTime.setText(formatter_time.format(reservation.getEndTime()));
        nbPerson.setText(String.valueOf(reservation.getNbPerson()));
        if (reservation.isResStatus()) {
            status.setText("Paid");
        } else {
            status.setText("Unpaid");
        }
        dateReservation.setText(formatter_date.format(reservation.getDateReservation()));
        stadiumName.setText(t.getName());
        terrain_type.setText(t.getSportType());
        String path = "file:C:/Users/moata/PhpstormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/terrain/";
        if (t.getImageName() != null) {
            path += t.getImageName();
            Image img = new Image(path);
            terrain_image.setImage(img);
        }
        float total = (reservation.getNbPerson() * t.getRentPrice());
        for (Equipment item : list_eq) {
            total += item.getPrice();
        }
        String total_txt = String.valueOf(total) + " Dt";
        total_price.setText(total_txt);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.reservation = new Reservation();
    }

    @FXML
    private void returnToListReservation(ActionEvent event) {
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
