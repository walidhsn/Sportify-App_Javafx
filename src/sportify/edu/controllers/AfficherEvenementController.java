/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import sportify.edu.entities.Equipment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sportify.edu.services.EquipmentCRUD;

/**
 * FXML Controller class
 *
 * @author MOLKA
 */
public class AfficherEvenementController implements Initializable {

    @FXML
    private GridPane gridEvent;
    @FXML
    private TextField chercherEventField;
    @FXML
    private Button ajouterEventBUTTON;
    
       private EquipmentCRUD Ev = new EquipmentCRUD();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherEvenement();
    }    
    
    
    
    public void afficherEvenement(){
         try {
            List<Equipment> evenements = Ev.display();
            gridEvent.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("evenement.fxml"));
                AnchorPane pane = loader.load();
               
                //passage de parametres
                EvenementController controller = loader.getController();
                controller.setEvennement(evenements.get(i));
                controller.setIdevent(evenements.get(i).getId());
                gridEvent.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
                
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void rechercherevenement(KeyEvent event) {
        
        
               try {
            List<Equipment> evenements = Ev.displayrecherche(chercherEventField.getText());
            gridEvent.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("evenement.fxml"));
                AnchorPane pane = loader.load();         
                //passage de parametres
                EvenementController controller = loader.getController();
                controller.setEvennement(evenements.get(i));
                controller.setIdevent(evenements.get(i).getId());
                gridEvent.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
              
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }

    @FXML
    private void ajouterEvenement(ActionEvent event) {
    }


    @FXML
    private void trierEvenement(ActionEvent event) throws SQLException {
        
        
               try {
            List<Equipment> evenements = Ev.trierEvent();
            gridEvent.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < evenements.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("evenement.fxml"));
                AnchorPane pane = loader.load();      
                //passage de parametres
                EvenementController controller = loader.getController();
                controller.setEvennement(evenements.get(i));
                controller.setIdevent(evenements.get(i).getId());
                gridEvent.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }
            
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
