/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import louaypi.entities.Card;
import louaypi.entities.CardItem;
import louaypi.services.CardItemCrud;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class card_itemController implements Initializable {

    @FXML
    private Label libelle_txt;
    @FXML
    private Label prix_txt;
    @FXML
    private Spinner<Integer> quantite;
    @FXML
    private Button supprimer_btn;
    private CardItem card_item;
    private Card card;
    private panierController controller;
    public void setData(CardItem item, Card c) {
        CardItemCrud cic = new CardItemCrud();
        this.card = c;
        this.card_item = item;
        libelle_txt.setText(card_item.getLibelle());
        prix_txt.setText(String.valueOf(card_item.getPrix()));
        SpinnerValueFactory<Integer> valueFactory_q = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99999999, 1, 1);
        valueFactory_q.setValue(card_item.getQuantite());
        quantite.setValueFactory(valueFactory_q);
        quantite.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int value = Integer.parseInt(newValue);
                quantite.getValueFactory().setValue(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });
        // ChangeListener for value changes
        quantite.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                card_item.setQuantite(newValue);
                cic.modifier_card_item(card_item);
                controller.updateTotal();
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        card_item = new CardItem();
        card = new Card();
    }

    @FXML
    private void supprimer_card_item(ActionEvent event) {
        CardItemCrud cic = new CardItemCrud();

        cic.supprimer_card_item(card_item.getId());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/louaypi/gui/produit/panier.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            panierController controller = loader.getController();
            controller.setData(card);
            Scene scene = new Scene(root);
            Stage stage = (Stage) supprimer_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
      public void setPanierController(panierController controller) {
        this.controller = controller;
    }
}
