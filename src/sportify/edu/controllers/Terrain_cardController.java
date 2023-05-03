/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sportify.edu.entities.Terrain;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Terrain_cardController implements Initializable {

    @FXML
    private HBox terrain_Hbox;
    @FXML
    private ImageView terrain_image;
    @FXML
    private Label terrain_name;
    @FXML
    private Label sport_type;
    @FXML
    private Label address;
    @FXML
    private Label capacity;
    @FXML
    private Label rentPrice;
    @FXML
    private Button book_btn;

    private Terrain terrain;
    private int id_client;

    public void setData(Terrain t, int client_id) {
        String image_directory_path = "file:C:/Users/moata/PhpstormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/terrain/";
        String full_path;
        this.terrain = t;
        this.id_client = client_id;
        terrain_name.setText(t.getName().toUpperCase());
        sport_type.setText(t.getSportType());
        String address_txt = t.getCity() + ", " + t.getCountry() + ", " + t.getRoadName();
        address.setText(address_txt);
        capacity.setText(String.valueOf(t.getCapacity()));
        String price = String.valueOf(t.getRentPrice()) + " Dt";
        rentPrice.setText(price);
        if (t.getImageName() != null) {
            full_path = image_directory_path + t.getImageName();
            terrain_image.setImage(new Image(full_path));
        }
        terrain_Hbox.setStyle("-fx-background-color:#6c757d; -fx-background-radius: 20; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.3), 10, 0 , 0 ,10);");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.terrain = new Terrain();

    }

    @FXML
    private void redirectToReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/ADD_reservation.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            ADD_reservationController controller = loader.getController();
            controller.setData(this.terrain, this.id_client);
            //-----
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void redirectToTerrainDetails(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Details_Terrain_reservation.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            DETAILS_Terrain_reservationController controller = loader.getController();
            controller.setInformation_Terrain(this.terrain, this.id_client);
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
    private void redirectToTerrainDetails_2(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Details_Terrain_reservation.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            DETAILS_Terrain_reservationController controller = loader.getController();
            controller.setInformation_Terrain(this.terrain, this.id_client);
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
