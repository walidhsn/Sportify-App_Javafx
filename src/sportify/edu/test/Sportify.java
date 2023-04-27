/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



/**
 *
 * @author WALID
 */
public class Sportify extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/Home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../gui/security/login.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("/resources/icon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Sportify");
        stage.setResizable(true);
        stage.show();
    }
    
}
