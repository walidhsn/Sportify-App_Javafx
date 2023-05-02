/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sportify.edu.services.SecurityService;

/**
 * FXML Controller class
 *
 * @author moata
 */
public class sportify_homeController implements Initializable {

    @FXML
    private Button btn_role;
    private ToolBar toolbar_client;
    private ToolBar toolbar_owner;
    @FXML
    private AnchorPane home_page;
    @FXML
    private Label title_1;
    @FXML
    private Label title_2;
    @FXML
    private Label title_3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (SecurityService.getCurrentUtilisateur().getRoles().contains("ROLE_CLIENT")) {
            title_1.setText("Find Your Playground");
            title_2.setText("The Scene is set for your Epic match");
            title_3.setText("Book Sports Pitches and Courts");
            btn_role.setText("Explore now");

            try {
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("../gui/security/toolbar_client.fxml"));
                toolbar_client = fXMLLoader.load();
                toolbar_clientController controller = fXMLLoader.getController();
                // Set the position of the toolbar_client within the home_page
                AnchorPane.setTopAnchor(toolbar_client, 20.0);
                AnchorPane.setLeftAnchor(toolbar_client, 90.0);
                home_page.getChildren().add(toolbar_client);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            title_1.setText("Manage your estates");
            title_2.setText("Grow your business with us.");
            title_3.setText("Add your Sports pitches and courts");
            btn_role.setText("ADD now");
            try {
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("../gui/security/toolbar_owner.fxml"));
                toolbar_owner = fXMLLoader.load();
                toolbar_ownerController controller = fXMLLoader.getController();
                // Set the position of the toolbar_client within the home_page
                AnchorPane.setTopAnchor(toolbar_owner, 20.0);
                AnchorPane.setLeftAnchor(toolbar_owner, 90.0);
                home_page.getChildren().add(toolbar_owner);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    private void redirectByRole(ActionEvent event) {
        if (SecurityService.getCurrentUtilisateur().getRoles().contains("ROLE_CLIENT")) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/Terrain_view_client.fxml"));
//                Parent root = loader.load();
//                //UPDATE The Controller with Data :
//                Terrain_view_clientController controller = loader.getController();
//                controller.setData(SecurityService.getCurrentUtilisateur().getId());
//                Scene scene = new Scene(root);
//                Stage stage = (Stage) btn_role.getScene().getWindow();
//                stage.setScene(scene);
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/Terrain_view_owner.fxml"));
                Parent root = loader.load();
                //UPDATE The Controller with Data :
                Terrain_view_ownerController controller = loader.getController();
                controller.setData(SecurityService.getCurrentUtilisateur().getId());
                Scene scene = new Scene(root);
                Stage stage = (Stage) btn_role.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
