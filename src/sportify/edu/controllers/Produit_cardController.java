package sportify.edu.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sportify.edu.entities.Card;
import sportify.edu.entities.CardItem;
import sportify.edu.entities.Produit;
import sportify.edu.services.CardCrud;
import sportify.edu.services.CardItemCrud;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class Produit_cardController implements Initializable {

    @FXML
    private HBox produit_Hbox;
    @FXML
    private ImageView prod_image;
    @FXML
    private Label lib;
    @FXML
    private Label prix;
    @FXML
    private Label sto;
    @FXML
    private Label ref;

    private Produit produit;
    @FXML
    private Button btn_addcart;
    private Card card;
    private Produit prod;

    public void setData(Produit p, Card c) {
        this.prod = p;
        this.card = c;
        String image_directory_path = "file:C:/Users/moata/PhpStormProjects/WEBPI(finale)/WEBPI(finale)/public/uploads/produit/";
        String full_path;
        this.produit = p;
        lib.setText(p.getLibelle().toUpperCase());
        prix.setText(String.valueOf(p.getPrix()) + " DT");
        sto.setText(String.valueOf(p.getQuantite()));
        ref.setText(p.getRefernce());
        System.out.println(p.getImage());
        produit_Hbox.setStyle("-fx-background-color:#6c757d; -fx-background-radius: 20; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.3), 10, 0 , 0 ,10);");
        if (p.getImage() != null) {
            full_path = image_directory_path + p.getImage();
            Image img = new Image(full_path);
            System.out.println(full_path);
            System.out.println("Image width: " + img.getWidth() + ", height: " + img.getHeight());
            prod_image.setImage(img);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.produit = new Produit();
    }

    @FXML
    private void addToCart(ActionEvent event) {
        CardItem c = null;
        CardItemCrud cic = new CardItemCrud();
        c = cic.trouver_card_item_par_libelle(prod.getLibelle());
        if (c != null) {
            int quantity = c.getQuantite();
            quantity += 1;
            c.setQuantite(quantity);
            cic.modifier_card_item(c);
        } else {
            c = new CardItem();
            c.setCard_id(card.getId());
            c.setLibelle(prod.getLibelle());
            c.setPrix(prod.getPrix());
            c.setQuantite(1);
            c.setOwner_id(prod.getId_owner());
            cic.ajouter_card_item(c);      
        }

    }

}
