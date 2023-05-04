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
import sportify.edu.entities.Event;
import sportify.edu.services.EventService;


/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Event_view_clientController implements Initializable {

    @FXML
    private TilePane events_TilePane;
    @FXML
    private Button myEvents_btn;
    @FXML
    private TextField search_text;
    private int client_id;
    private List<Event> list_event;
    private List<Event> list_event_search;
    @FXML
    private Button back_btn;
    
    public void setData(int id_client){
        this.client_id = id_client;
        EventService es = new EventService();
        list_event = es.displayEntity();
        if (!list_event.isEmpty()) {
            for (Event event : list_event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../gui/event/event_card.fxml"));
                    HBox eventCard = fxmlLoader.load();
                    event_cardController eventCardController = fxmlLoader.getController();
                    eventCardController.setData(event,this.client_id);
                    events_TilePane.getChildren().add(eventCard);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            search_text.textProperty().addListener((observable, oldValue, newValue) -> {
                events_TilePane.getChildren().clear(); // remove all existing HBox
                if (newValue.isEmpty()) {
                    // display the default list_terrain
                    for (Event event : list_event) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("../gui/event/event_card.fxml"));
                            HBox eventCard = fxmlLoader.load();
                            event_cardController eventCardController = fxmlLoader.getController();
                            eventCardController.setData(event,this.client_id);
                            events_TilePane.getChildren().add(eventCard);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    // display the filtered list_terrain_search
                    list_event_search = list_event.stream()
                            .filter(event -> event.getNom().toLowerCase().contains(newValue.toLowerCase())
                            || event.getCategory().toLowerCase().contains(newValue.toLowerCase())
                            || event.getTerrainName().toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(event.getDateEvent()).toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(event.getStartTime()).toLowerCase().contains(newValue.toLowerCase())
                            || String.valueOf(event.getEndTime()).toLowerCase().contains(newValue.toLowerCase()))
                            .collect(Collectors.toList());
                    for (Event event : list_event_search) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("../gui/event/event_card.fxml"));
                            HBox eventCard = fxmlLoader.load();
                            event_cardController terrainCardController = fxmlLoader.getController();
                            terrainCardController.setData(event,this.client_id);
                            events_TilePane.getChildren().add(eventCard);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void redirectToMyEvents(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/myEvents.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            myEventsController controller = loader.getController();
            controller.setData(this.client_id);           
            Scene scene = new Scene(root);
            Stage stage = (Stage) myEvents_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void back_home(ActionEvent event) {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/sportify_home.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
