/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Event;
import sportify.edu.services.EventService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class myEventsController implements Initializable {

    @FXML
    private TableView<Event> table_event;
    @FXML
    private TableColumn<Event, String> name_col;
    @FXML
    private TableColumn<Event, String> category_col;
    @FXML
    private TableColumn<Event, String> date_col;
    @FXML
    private TableColumn<Event, String> startTime_col;
    @FXML
    private TableColumn<Event, String> endTime_col;
    @FXML
    private TableColumn<Event, String> terrainName_col;
    @FXML
    private TableColumn<Event, String> updatedAt_col;
    @FXML
    private TableColumn<Event, String> details_col;
    @FXML
    private TableColumn<Event, String> delete_col;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TextField search_text;
    private int client_id;
    private ImageView icon_delete;  
    private ImageView icon_view;
    private EventService es;
    private List<Event> list_event;
    private List<Event> list_event_search;
    
    public void setData(int client){
        this.client_id = client;
        list_event = es.myEvents_client(this.client_id);
        table_event.setItems(FXCollections.observableArrayList(list_event));

        name_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        startTime_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        terrainName_col.setCellValueFactory(new PropertyValueFactory<>("terrainName"));
        updatedAt_col.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            list_event_search = list_event.stream()
                    .filter(event -> event.getNom().toLowerCase().contains(newValue.toLowerCase())
                    || event.getCategory().toLowerCase().contains(newValue.toLowerCase())
                    || event.getTerrainName().toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(event.getDateEvent()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(event.getStartTime()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(event.getEndTime()).toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            table_event.setItems(FXCollections.observableArrayList(list_event_search));
        });
        // Delete Button : 
        delete_col.setCellFactory(column -> {
            return new TableCell<Event, String>() {

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
                            Event data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                es.delete_join_event(data.getId(),client_id);
                                refresh();
                            }
                        });
                    }
                }
            };
        });

        //View Button : 
        details_col.setCellFactory(column -> {
            return new TableCell<Event, String>() {

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
                                Event data = getTableView().getItems().get(getIndex());
                                System.out.println(data);
                                System.out.println("selectedData: " + data.getId());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/DETAILS_event_client.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                DETAILS_event_clientController controller = loader.getController();
                                controller.setData(data,client_id);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_event.getScene().getWindow();
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
        es = new EventService();
    }    

    @FXML
    private void returnToListEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/Event_view_client.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Event_view_clientController controller = loader.getController();
            controller.setData(this.client_id);           
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_event.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void refresh() {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/myEvents.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            myEventsController controller = loader.getController();
            controller.setData(this.client_id);           
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_event.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
