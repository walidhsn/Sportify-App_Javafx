package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainFx extends Application {

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("AcademyList.fxml"));
//        primaryStage.setTitle("Academy");
//        primaryStage.setScene(new Scene(root, 800, 600));
//        primaryStage.show();
//    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Auth.fxml"));
        Scene scene = new Scene(root);
          // Set minimum and maximum sizes for the scene
//        scene.setMinWidth(800);
//        scene.setMinHeight(600);
//        scene.setMaxWidth(1200);
//        scene.setMaxHeight(800);

        // Set the minimum size for the stage
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Image icon = new Image("/Ressource/icons/logo.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Academy");
        stage.setResizable(true);
//        stage.setMinWidth(1024);
//        stage.setMinHeight(720);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
