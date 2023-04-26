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
        Parent root = FXMLLoader.load(getClass().getResource("AcademyList.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("/Ressource/icons/logo.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Academy");
        stage.setResizable(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
