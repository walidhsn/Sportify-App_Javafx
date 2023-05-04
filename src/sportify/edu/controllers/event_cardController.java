/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sportify.edu.entities.Event;
import sportify.edu.services.EventService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class event_cardController implements Initializable {

    @FXML
    private ImageView event_image;
    @FXML
    private Label event_name;
    @FXML
    private Label terrain_name;
    @FXML
    private Label event_date;
    @FXML
    private Label star_end_time;
    @FXML
    private Label category;
    @FXML
    private Button join_btn;
    @FXML
    private HBox event_Hbox;
    private Event event;
    private int id_client;

    public void setData(Event e, int client_id) {
        String image_directory_path = "file:C:/Users/moata/PhpstormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/event/";
        String full_path;
        DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String time;
        this.event = e;
        this.id_client = client_id;
        event_name.setText(this.event.getNom().toUpperCase());
        terrain_name.setText(this.event.getTerrainName().toUpperCase());
        event_date.setText(formatter_date.format(this.event.getDateEvent()));
        time = "Start: " + formatter_time.format(this.event.getStartTime()) + " | " + " End :" + formatter_time.format(this.event.getEndTime());
        star_end_time.setText(time);
        category.setText(this.event.getCategory());
        if (this.event.getImageName() != null) {
            full_path = image_directory_path + this.event.getImageName();
            event_image.setImage(new Image(full_path));
        }
        event_Hbox.setStyle("-fx-background-color:#6c757d; -fx-background-radius: 20; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.3), 10, 0 , 0 ,10);");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.event = new Event();
    }

    @FXML
    private void redirectToEventDetails_2(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/DETAILS_event_join.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            DETAILS_event_joinController controller = loader.getController();
            controller.setData(this.event, this.id_client);
            //-----
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToEventDetails(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/DETAILS_event_join.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            DETAILS_event_joinController controller = loader.getController();
            controller.setData(this.event, this.id_client);
            //-----
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void joinEvent(ActionEvent event) {
        EventService es = new EventService();
        es.join_event(this.event.getId(), id_client);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("You Have Joined this Event.");
        alert.setHeaderText(null);
        alert.showAndWait();

    }

}
