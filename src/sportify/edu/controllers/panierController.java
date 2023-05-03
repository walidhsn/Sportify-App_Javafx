/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sportify.edu.entities.Card;
import sportify.edu.entities.CardItem;
import sportify.edu.entities.User;
import sportify.edu.services.CardCrud;
import sportify.edu.services.CardItemCrud;
import sportify.edu.services.SecurityService;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class panierController implements Initializable {

    @FXML
    private VBox vbox_card;
    @FXML
    private Button commander_btn;
    @FXML
    private Button supprimerCard_btn;
    @FXML
    private TextField total;
    private Card card;
    private List<CardItem> list_card_items;
    @FXML
    private Button list;
    private float total_prix;
    private User user;
    
    public void setData(Card c) {
        this.card = c;
        set_total();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = new User();
        user= SecurityService.getCurrentUtilisateur();
        card = new Card();
        this.total_prix = 0;
    }

    @FXML
    private void RedirectToCommander(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/commande/formulaire_commande.fxml"));
                Parent root = loader.load();
                //UPDATE The Controller with Data :
                form_commande controller = loader.getController();
                controller.setData(this.card);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    @FXML
    private void supprimerCard(ActionEvent event) {
        CardItemCrud cic = new CardItemCrud();
        if (!list_card_items.isEmpty()) {
            for (CardItem item : list_card_items) {
                cic.supprimer_card_item(item.getId());
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/produit/panier.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            panierController controller = loader.getController();
            controller.setData(card);
            Scene scene = new Scene(root);
            Stage stage = (Stage) supprimerCard_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void oList(ActionEvent event) {
      try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/produit/Produit_view_client.fxml"));
                Parent root = loader.load();
                //UPDATE The Controller with Data :
                Produit_view_clientController controller = loader.getController();
                controller.setData(user.getId());
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void set_total() {
        CardCrud cc = new CardCrud();
        CardItemCrud cic = new CardItemCrud();
        list_card_items = cic.lister_card_items_Card_id(this.card.getId());
        if (!list_card_items.isEmpty()) {
            for (CardItem item : list_card_items) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../gui/produit/card_item.fxml"));
                    HBox Card_Item;
                    Card_Item = fxmlLoader.load();
                    card_itemController CardItemController = fxmlLoader.getController();
                    CardItemController.setData(item, this.card);
                    CardItemController.setPanierController(this);
                    vbox_card.getChildren().add(Card_Item);
                    total_prix += (item.getPrix() * item.getQuantite());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            String total_txt = String.valueOf(total_prix) + " Dt";
            total.setText(total_txt);
            card.setTotal(total_prix);
            cc.modifier_card(this.card);
        }
    }

    public void updateTotal() {
        total_prix=0;
        CardCrud cc = new CardCrud();
        CardItemCrud cic = new CardItemCrud();
        list_card_items = cic.lister_card_items_Card_id(this.card.getId());
        if (!list_card_items.isEmpty()) {
            for (CardItem item : list_card_items) {

                total_prix += (item.getPrix() * item.getQuantite());

            }
            String total_txt = String.valueOf(total_prix) + " Dt";
            total.setText(total_txt);
            card.setTotal(total_prix);
            cc.modifier_card(this.card);
        }
    }
}
