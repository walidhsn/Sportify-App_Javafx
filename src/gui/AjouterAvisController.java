/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Avis;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import services.AvisService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjouterAvisController implements Initializable {

    @FXML
    private TextArea contenuField;
    @FXML
    private TextField idEvField;
    
    AvisService AS=new AvisService();
    @FXML
    private ComboBox<?> nameEventCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterAvis(ActionEvent event) {

       // nameEventCombo.setValue(AS);
        Avis a = new Avis();
        a.setContenu(contenuField.getText());
        a.setIdEvent(Integer.valueOf(idEvField.getText()));
         
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("avis add");
            alert.setContentText("avis added successfully!");
            alert.showAndWait();      
        try {
            AS.ajouterAvis(a);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }      
    }
    
    
    public void setIdevent(int idevent){
        idEvField.setText(String.valueOf(idevent));
    }
    
    
    
    
}
