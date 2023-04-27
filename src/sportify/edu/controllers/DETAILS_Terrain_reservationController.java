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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Terrain;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class DETAILS_Terrain_reservationController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private TextField terrain_name;
    @FXML
    private TextField terrain_roadName;
    @FXML
    private TextField terrain_city;
    @FXML
    private TextField terrain_country;
    @FXML
    private TextField terrain_ownername;
    @FXML
    private TextField terrain_capacity;
    @FXML
    private TextField terrain_sportType;
    @FXML
    private TextField terrain_disponibility;
    @FXML
    private TextField terrain_postalCode;
    @FXML
    private TextField terrain_roadNumber;
    @FXML
    private TextField terrain_rentPrice;
    @FXML
    private ImageView terrain_image;
    @FXML
    private ImageView backBtn_icon;
    private Terrain terrain;
    private int id_client;
    @FXML
    private Button book_btn;

    public void setInformation_Terrain(Terrain terrain,int client_id) {
        this.id_client = client_id;
        String image_path_directory = "file:C:/Users/WALID/Desktop/WEBPI/WEBPI/public/uploads/terrain/";
        String full_path;
        this.terrain = terrain;
        terrain_name.setText(this.terrain.getName());
        terrain_capacity.setText(String.valueOf(this.terrain.getCapacity()));
        terrain_sportType.setText(this.terrain.getSportType());
        terrain_rentPrice.setText(String.valueOf(this.terrain.getRentPrice()) + " Dt");
        if (this.terrain.isDisponibility()) {
            terrain_disponibility.setText("Yes");
        } else {
            terrain_disponibility.setText("No");
        }
        terrain_postalCode.setText(String.valueOf(this.terrain.getPostalCode()));
        terrain_roadName.setText(this.terrain.getRoadName());
        terrain_roadNumber.setText(String.valueOf(this.terrain.getRoadNumber()));
        terrain_city.setText(this.terrain.getCity());
        terrain_country.setText(this.terrain.getCountry());
        terrain_ownername.setText("");// to do with a function in the userService
        if (this.terrain.getImageName() != null) {
            full_path = image_path_directory + this.terrain.getImageName();
            terrain_image.setImage(new Image(full_path));
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        terrain = new Terrain();
        Image btn_icon = new Image("/resources/back-arrow.png");
        backBtn_icon.setImage(btn_icon);
    }

    @FXML
    private void returnToListTerrain(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/Terrain_view_client.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void reserverTerrain(ActionEvent event) {
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

}
