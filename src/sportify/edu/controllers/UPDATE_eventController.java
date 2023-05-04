/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sportify.edu.entities.Event;
import sportify.edu.entities.Sponsor;
import sportify.edu.entities.Terrain;
import sportify.edu.services.EventService;
import sportify.edu.services.SponsorService;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class UPDATE_eventController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private JFXDatePicker dateEvent;
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private ListView<String> sponsors;
    @FXML
    private TextField event_category;
    @FXML
    private TextField event_name;
    @FXML
    private ChoiceBox<String> terrains;
    @FXML
    private Button update_btn;
    @FXML
    private ImageView event_image;
    @FXML
    private Button upload_image;
    @FXML
    private ImageView upload_img_icon;
    private File file;
    private int id_owner;
    private SponsorService ss;
    private EventService es;
    private TerrainService ts;
    private List<Sponsor> list_sponsor;
    private Map<Integer, String> map_sponsor;
    private List<Terrain> list_terrain;
    private List<String> list_terrain_names;
    private List<String> selectedItems;
    private List<String> unselectedItems;
    private Event event;
    private List<Sponsor> selectedSponsors;
    private Image image;

    public void setData(int id_owner, Event e) {
        this.id_owner = id_owner;
        this.event = e;
                            
        String url_imageDirectory = "file:C:/Users/moata/PhpstormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/event/";
        if (this.event.getImageName() != null) {
            String url_image = url_imageDirectory + this.event.getImageName();
            System.out.println(url_image);
            image = new Image(url_image);
            event_image.setImage(image);
        }
        list_terrain = ts.myProprieties(id_owner);
        if (list_terrain != null) {
            for (Terrain terrain : list_terrain) {
                list_terrain_names.add(terrain.getName());
            }
        }
        selectedSponsors = es.mySponsors(this.event.getId());
        if (selectedSponsors != null) {
            for (Sponsor item : selectedSponsors) {
                selectedItems.add(item.getNomSponsor());
            }
        }
        System.out.println("Selected Sponsors : " + selectedItems);
        list_sponsor = ss.displayEntity();
        if (list_sponsor != null) {
            for (Sponsor sponsor : list_sponsor) {
                map_sponsor.put(sponsor.getId(), sponsor.getNomSponsor());
            }
        }

        // Set the ListView and get the selected data
        sponsors.setEditable(true);
        for (String value : map_sponsor.values()) {
            sponsors.getItems().add(value);
        }

        // Create the custom ListCell with the checkbox event listener
        sponsors.setCellFactory(param -> new ListCell<String>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    String item = getItem();
                    if (checkBox.isSelected()) {
                        selectedItems.add(item);
                        unselectedItems.remove(item);
                    } else {
                        unselectedItems.add(item);
                        selectedItems.remove(item);
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

        sponsors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sponsors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Do nothing here
        });
        //set terrain names :
        terrains.getItems().addAll(list_terrain_names);
        // set Data : 
        event_name.setText(event.getNom());
        event_category.setText(event.getCategory());
        terrains.setValue(this.event.getTerrainName());
        dateEvent.setValue(this.event.getDateEvent().toLocalDate());
        startTime.setValue(this.event.getStartTime().toLocalTime());
        endTime.setValue(this.event.getEndTime().toLocalTime());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        es = new EventService();
        ts = new TerrainService();
        ss = new SponsorService();
        map_sponsor = new HashMap<>();
        list_terrain = new ArrayList<>();
        list_terrain_names = new ArrayList<>();
        selectedItems = new ArrayList<>();
        unselectedItems = new ArrayList<>();
        selectedSponsors = new ArrayList<>();
    }

    @FXML
    private void returnListEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/Event_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Event_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        List<String> result;
        Event verif;
        String name = event_name.getText();
        String category = event_category.getText();
        String terrain_name = "";
        // Get the selected date
        LocalDate selectedDate = dateEvent.getValue();
        // Get the start and end times
        LocalTime selectedStartTime = startTime.getValue();
        LocalTime selectedEndTime = endTime.getValue();
        if (terrains.getValue() != null) {
            terrain_name = terrains.getValue();
        }
        //Control :
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please input the Event Name.");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            event_name.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(event_name).play();
        } else if (category.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please input the Event Category.");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            event_category.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(event_category).play();
        } else if (selectedDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter the missing information (Date)");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            dateEvent.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(dateEvent).play();
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
        } else if (selectedItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select Sponsor(s).");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            sponsors.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(sponsors).play();
        } else if (terrain_name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select a Stadium for The event.");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            terrains.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(terrains).play();
        } else {
            // Combine the date and time values to create the start and end date/time objects
            LocalDateTime startDateTime = LocalDateTime.of(selectedDate, selectedStartTime);
            LocalDateTime endDateTime = LocalDateTime.of(selectedDate, selectedEndTime);

            verif = es.find_event_update(this.event.getId(), id_owner, name, terrain_name, startDateTime);
            event_name.setStyle(null);
            event_category.setStyle(null);
            dateEvent.setStyle(null);
            startTime.setStyle(null);
            endTime.setStyle(null);
            sponsors.setStyle(null);
            terrains.setStyle(null);

            if (verif == null) {

                if (file != null) {
                    String destPath = "C:/Users/moata/PhpstormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/event/";
                    String imageName = generateUniqueName(file); // Generate a unique name for the image
                    String oldImageName = this.event.getImageName();
                    String oldImagePath;
                    if (oldImageName != null) {
                        oldImagePath = destPath + oldImageName;
                        File oldImageFile = new File(oldImagePath);
                        if (oldImageFile.exists()) { // Check if the old image file exists
                            try {
                                Files.delete(oldImageFile.toPath()); // Delete the old image file
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                    File dest = new File(destPath + imageName); // Set the destination path for the image
                    try {
                        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING); // Copy the image to the destination folder

                        Event e = new Event(this.event.getId(), id_owner, name, category, startDateTime, startDateTime, endDateTime, imageName, terrain_name, LocalDateTime.now());
                        result = es.updateEvent(e);
                        if (result.get(0).equals("ok")) {
                            System.out.println("unselected aaa !  :" + unselectedItems);
                            verif = es.find_event(id_owner, name, terrain_name, startDateTime);
                            for (String item : unselectedItems) {
                                for (Map.Entry<Integer, String> element : map_sponsor.entrySet()) {
                                    if (element.getValue().equals(item)) {
                                        es.deleteEventSponsor(element.getKey(), verif.getId());

                                    }
                                }
                            }
                            for (String item : selectedItems) {
                                for (Map.Entry<Integer, String> element : map_sponsor.entrySet()) {
                                    if (element.getValue().equals(item)) {
                                        es.addEventSponsor(element.getKey(), verif.getId());
                                    }
                                }
                            }

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setContentText(result.get(1));
                            alert.setHeaderText(null);
                            alert.show();

                            redirectToListEvent();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(result.get(1));
                            alert.setTitle("Problem");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    Event e = new Event(this.event.getId(), id_owner, name, category, startDateTime, startDateTime, endDateTime, this.event.getImageName(), terrain_name, LocalDateTime.now());
                    result = es.updateEvent(e);
                    if (result.get(0).equals("ok")) {
                        verif = es.find_event(id_owner, name, terrain_name, startDateTime);
                        for (String item : unselectedItems) {
                            for (Map.Entry<Integer, String> element : map_sponsor.entrySet()) {
                                if (element.getValue().equals(item)) {
                                    es.deleteEventSponsor(element.getKey(), verif.getId());

                                }
                            }
                        }
                        for (String item : selectedItems) {
                            for (Map.Entry<Integer, String> element : map_sponsor.entrySet()) {
                                if (element.getValue().equals(item)) {
                                    es.addEventSponsor(element.getKey(), verif.getId());
                                }
                            }
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText(result.get(1));
                        alert.setHeaderText(null);
                        alert.show();

                        redirectToListEvent();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(result.get(1));
                        alert.setTitle("Problem");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Event already Exist.");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void upload_imageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterJPEG
                = new FileChooser.ExtensionFilter("JPEG files (*.JPEG)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterjpeg
                = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng, extFilterJPEG, extFilterjpeg);
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String image_uri = file.toURI().toString();
            // Display the selected image in an ImageView
            Image image = new Image(image_uri);
            event_image.setImage(image);
        }
    }

    private String generateUniqueName(File file) {
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".")).toLowerCase(); // Get the file extension
        String imageName = UUID.randomUUID().toString() + extension; // Generate a unique name with the same extension
        return imageName;
    }

    private void redirectToListEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/Event_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Event_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
