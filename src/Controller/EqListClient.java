/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
 
import entities.Equipment;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author MOLKA
 */
public class EqListClient implements Initializable {
    
    @FXML
    private GridPane equipmentGrid;
    
    @FXML
    private TextField searchField;
    
    private List<Equipment> EqListClient = new ArrayList<>(); // populate this list with your equipment data
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int row = 0;
        int col = 0;
        
        for (Equipment equipment : EqListClient) {
            ImageView imageView = new ImageView(equipment.getImage());
            Button likeButton = new Button("Like");
            Button dislikeButton = new Button("Dislike");
            TextField commentField = new TextField();
            Button detailsButton = new Button("Details");
            
            detailsButton.setOnAction(event -> {
                // display QR code for equipment
            });
            
            VBox equipmentBox = new VBox();
            equipmentBox.getChildren().addAll(imageView, likeButton, dislikeButton, commentField, detailsButton);
            
            equipmentGrid.add(equipmentBox, col, row);
            
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
    }
    
    @FXML
    private void search(ActionEvent event) {
        String searchQuery = searchField.getText();
        // perform search logic and update equipmentGrid

    
    }}
