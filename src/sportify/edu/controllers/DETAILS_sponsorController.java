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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Sponsor;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class DETAILS_sponsorController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private TextField Sponsor_tel;
    @FXML
    private TextField Sponsor_name;
    @FXML
    private TextField Sponsor_email;
    private int id_owner;
    private Sponsor sponsor;
    public void setData(Sponsor s,int id_owner){
        this.id_owner = id_owner;
        this.sponsor = s;
        Sponsor_name.setText(this.sponsor.getNomSponsor());
        Sponsor_email.setText(this.sponsor.getEmailSponsor());
        Sponsor_tel.setText(String.valueOf(this.sponsor.getTelSponsor()));
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.sponsor = new Sponsor();
        
    }    

    @FXML
    private void returnToListSponsor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/Sponsor_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Sponsor_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
