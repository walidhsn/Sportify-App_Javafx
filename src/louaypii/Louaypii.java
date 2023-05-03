/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypii;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author louay
 */
public class Louaypii extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       Parent root=FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/Produit_view_client.fxml"));
     // Parent root=FXMLLoader.load(getClass().getResource("gui/commande/commande_view.fxml"));
       //Parent root=FXMLLoader.load(getClass().getResource("gui/categorie/ADD_categorie.fxml"));
//            Parent root=FXMLLoader.load(getClass().getResource("gui/produit/Produit_card.fxml"));
//Parent root=FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/stat.fxml"));
       // Parent root=FXMLLoader.load(getClass().getResource("gui/produit/Produit_view_owner.fxml"));
         //Parent root = FXMLLoader.load(getClass().getResource("gui/produit/Add_produit.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Sportify");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
