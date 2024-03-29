/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import sportify.edu.entities.Terrain;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Terrain_view_clientController implements Initializable {

    @FXML
    private TilePane terrains_TilePane;
    @FXML
    private Button myReservation_btn;

    private List<Terrain> list_terrain;
    private List<Terrain> list_terrain_search;
    @FXML
    private TextField search_text;
    private int client_id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.client_id = 3;
        TerrainService ts = new TerrainService();
        list_terrain = ts.displayEntity();
        if (!list_terrain.isEmpty()) {
            for (Terrain terrain : list_terrain) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../gui/terrain/Terrain_card.fxml"));
                    HBox terrainCard = fxmlLoader.load();
                    Terrain_cardController terrainCardController = fxmlLoader.getController();
                    terrainCardController.setData(terrain,this.client_id);
                    terrains_TilePane.getChildren().add(terrainCard);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            search_text.textProperty().addListener((observable, oldValue, newValue) -> {
                terrains_TilePane.getChildren().clear(); // remove all existing HBox
                if (newValue.isEmpty()) {
                    // display the default list_terrain
                    for (Terrain terrain : list_terrain) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("../gui/terrain/Terrain_card.fxml"));
                            HBox terrainCard = fxmlLoader.load();
                            Terrain_cardController terrainCardController = fxmlLoader.getController();
                            terrainCardController.setData(terrain,this.client_id);
                            terrains_TilePane.getChildren().add(terrainCard);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    // display the filtered list_terrain_search
                    list_terrain_search = list_terrain.stream()
                            .filter(terrain -> terrain.getName().toLowerCase().contains(newValue.toLowerCase())
                            || terrain.getCity().toLowerCase().contains(newValue.toLowerCase())
                            || terrain.getCountry().toLowerCase().contains(newValue.toLowerCase())
                            || terrain.getSportType().toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(terrain.getCapacity()).toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(terrain.getPostalCode()).toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(terrain.getRentPrice()).toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());
                    for (Terrain terrain : list_terrain_search) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("../gui/terrain/Terrain_card.fxml"));
                            HBox terrainCard = fxmlLoader.load();
                            Terrain_cardController terrainCardController = fxmlLoader.getController();
                            terrainCardController.setData(terrain,this.client_id);
                            terrains_TilePane.getChildren().add(terrainCard);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        }
    }

    @FXML
    private void redirectToListReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Reservation_view_client.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Reservation_view_client controller = loader.getController();
            controller.setData(this.client_id);  
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
