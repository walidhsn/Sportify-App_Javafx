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
import sportify.edu.entities.Event;
import sportify.edu.entities.Sponsor;
import sportify.edu.entities.User;
import sportify.edu.services.CRUDUser;
import sportify.edu.services.EventService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class DETAILS_event_clientController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TextField event_name;
    @FXML
    private TextField sponsors;
    @FXML
    private TextField ownerName;
    @FXML
    private TextField event_category;
    @FXML
    private TextField event_date;
    @FXML
    private TextField event_endTime;
    @FXML
    private TextField terrain_name;
    @FXML
    private TextField event_startTime;
    @FXML
    private ImageView event_image;
    private Image image;
    private Event event;
    private int id_client;
    public void setData(Event e, int id_client) {
        this.event = e;
        this.id_client = id_client;
        CRUDUser Cu = new CRUDUser();
        User u = Cu.getUser(e.getOrganisateur_id());
        String name_owner = u.getFirstname() +"  "+u.getLastname();
        EventService es = new EventService();
        DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Sponsor> mysponsors = es.mySponsors(e.getId());
        String sponsors_txt = "";
        if (mysponsors != null) {
            for (Sponsor item : mysponsors) {
                sponsors_txt += item.getNomSponsor() +" ,  ";
            }
        }
        String url_imageDirectory = "file:C:/Users/moata/PhpstormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/event/";
        if (this.event.getImageName() != null) {
            String url_image = url_imageDirectory + this.event.getImageName();
            image = new Image(url_image);
            event_image.setImage(image);
        }
        event_name.setText(this.event.getNom());
        ownerName.setText(name_owner);
        event_category.setText(this.event.getCategory());
        event_date.setText(formatter_date.format(this.event.getDateEvent()));
        event_startTime.setText(formatter_time.format(this.event.getStartTime()));
        event_endTime.setText(formatter_time.format(this.event.getEndTime()));
        terrain_name.setText(this.event.getTerrainName());
        sponsors.setText(sponsors_txt);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.event = new Event();
    }    

    @FXML
    private void returnToMyEvents(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/event/myEvents.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            myEventsController controller = loader.getController();
            controller.setData(this.id_client);           
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
