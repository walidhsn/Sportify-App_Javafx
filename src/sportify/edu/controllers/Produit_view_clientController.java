package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import sportify.edu.entities.Produit;
import sportify.edu.services.ProduitCRUD;
import sportify.edu.entities.Card;
import sportify.edu.services.CardCrud;

public class Produit_view_clientController implements Initializable {

    @FXML
    private TilePane prods_TilePane;

    private List<Produit> list_produits;
    private int id_client;
    private Card c;
    @FXML
    private Button panier_btn;
    @FXML
    private TextField search_text;
    @FXML
    private Button search_btn;

    public void setData(int id_client) {
        this.id_client = id_client;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadProducts();
    }

    private void loadProducts() {
        ProduitCRUD ps = new ProduitCRUD();
        list_produits = ps.listerProduits();
        id_client = 2;
        CardCrud cc = new CardCrud();
        c = cc.trouver_card_par_user_id(id_client);
        if (c == null) {
            c = new Card(id_client, 0.0f);
            cc.ajouter_card(c);
            c = cc.trouver_card_par_user_id(id_client);
        }

        if (!list_produits.isEmpty()) {
            for (Produit produit : list_produits) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../gui/produit/Produit_card.fxml"));
                    HBox produitCard;
                    produitCard = fxmlLoader.load();
                    Produit_cardController produitCardController = fxmlLoader.getController();
                    produitCardController.setData(produit, c);
                    prods_TilePane.getChildren().add(produitCard);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void redirectToPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/produit/panier.fxml"));
            Parent root = loader.load();
            // UPDATE The Controller with Data :
            panierController controller = loader.getController();
            controller.setData(c);
            Scene scene = new Scene(root);
            Stage stage = (Stage) panier_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
private void SearchProducts(ActionEvent event) {
    String searchEntry = search_text.getText();
    if (searchEntry.isEmpty()) {
        // If the search entry is empty, reload all products
        prods_TilePane.getChildren().clear();
        loadProducts();
                           /* FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("/louaypi/gui/produit/Produit_view_clientController.fxml"));*/
    } else {
        // Perform search and display only the relevant products
        ProduitCRUD ps = new ProduitCRUD();
        List<Produit> searchResults = ps.SearchProd(searchEntry);

        // Clear the tile pane
        prods_TilePane.getChildren().clear();

        if (!searchResults.isEmpty()) {
            for (Produit produit : searchResults) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../gui/produit/Produit_card.fxml"));
                    HBox produitCard;
                    produitCard = fxmlLoader.load();
                    Produit_cardController produitCardController = fxmlLoader.getController();
                    produitCardController.setData(produit, c);
                    prods_TilePane.getChildren().add(produitCard);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Show a prompt when no search results are found
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Results");
            alert.setHeaderText("No results found.");
            alert.setContentText("Click OK to display all products.");

            ButtonType okButton = new ButtonType("OK");
            ButtonType cancelButton = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == okButton) {
                    // If the OK button is clicked, reload all products
                    prods_TilePane.getChildren().clear();
                    loadProducts();
                }
            });
        }
    }
}

    @FXML
    private void redirectToOwner(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/produit/Produit_view_client.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}