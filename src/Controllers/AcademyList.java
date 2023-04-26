/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entities.Academy;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AcademyCRUD;

/**
 *
 * @author ramib
 */
public class AcademyList implements Initializable {
    
    private Label label;
    @FXML
    private TableView<Academy> tvAcademy;
    private ObservableList<Academy> academyList;
    @FXML
    private TableColumn<Academy, Integer> colId;
    @FXML
    private TableColumn<Academy, String> colName;
    @FXML
    private TableColumn<Academy, String> colCategory;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView imgSearch;
    @FXML
    private ImageView btnClear;
    @FXML
    private Button btnSearch;
    
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    @FXML
    private Button btnAdd;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        academyList = FXCollections.observableArrayList(academyCRUD.display());
        tvAcademy.setItems(academyList);

        // Set the selection mode to MULTIPLE
        tvAcademy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Add listener for the selection of an academy in the table
        tvAcademy.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Academy selectedAcademy = tvAcademy.getSelectionModel().getSelectedItem();
                if (selectedAcademy != null) {
                    try {
                        // Load the AcademyDetails FXML file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/AcademyDetails.fxml"));
                        Parent root = loader.load();

                        // Get a reference to the AcademyDetails controller
                        AcademyDetails controller = loader.getController();

                        // Call a method on the controller to set the academy to display its details
                        controller.setAcademy(selectedAcademy);
                        
                        // Get a reference to the current stage
                        Stage stage = (Stage) tvAcademy.getScene().getWindow();

                        // Create a new stage to display the AcademyDetails scene
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    
    @FXML
    private void handleSearchButtonClick(ActionEvent event) {
        String searchText = searchField.getText();
        ObservableList<Academy> filteredList = FXCollections.observableArrayList();
        for (Academy academy : academyList) {
            if (academy.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(academy);
            }
        }
        tvAcademy.setItems(filteredList);
    }
    
    @FXML
    private void handleAddButtonClick(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/AcademyAdd.fxml"));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyAdd.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void handleClearButtonClick(MouseEvent event) throws IOException {
        String searchText = "";
        ObservableList<Academy> filteredList = FXCollections.observableArrayList();
        for (Academy academy : academyList) {
            if (academy.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(academy);
            }
        }
        tvAcademy.setItems(filteredList);
    }

    
}
