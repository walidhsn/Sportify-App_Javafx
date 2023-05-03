/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import sportify.edu.entities.Card;
import sportify.edu.entities.CardItem;
import sportify.edu.entities.Commande;
import sportify.edu.entities.Historique;
import sportify.edu.entities.User;
import sportify.edu.services.CardCrud;
import sportify.edu.services.CardItemCrud;
import sportify.edu.services.CommandeCrud;
import sportify.edu.services.HistoriquesCrud;
import sportify.edu.services.SecurityService;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class form_commande implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField pre;
    @FXML
    private TextField email;
    @FXML
    private TextField tel;
    @FXML
    private TextField ville;
    @FXML
    private TextField adr;
    @FXML
    private Button btn_cree_commande;
    
    CardCrud cx = new CardCrud();
    private Card card ;
    @FXML
    private Button btn_retour;
    private User user;
    private CommandeCrud cc;
    public void setData(Card c){
        this.card = c;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       user = SecurityService.getCurrentUtilisateur();
       cc = new CommandeCrud();
    }    

    @FXML
    private void add_commande(ActionEvent event) throws DocumentException, FileNotFoundException, BadElementException, IOException {
        
        Commande c = new Commande();
        c.setTotal(card.getTotal());
        //Thot les valeur o controle de saisir
        String nomC = nom.getText();
        String pren = pre.getText();
        String mail = email.getText();
        String t = tel.getText();
        String v = ville.getText();
        String adre = adr.getText();
        if (nomC.isEmpty() && nomC.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Name");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (pren.isEmpty() && pren.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the lastName");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (mail.isEmpty() && mail.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the mail");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (t.isEmpty() && t.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the phone number");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (v.isEmpty() && v.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the ville");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (adre.isEmpty() && adre.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the adresse ");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
                c.setFirstname(nomC);
                c.setLastname(pren);
                c.setEmail(mail);
                c.setTel(t);
                c.setCity(v);
                c.setAdresse(adre);
                c.setCard_id(card.getId());
                c.setUser_id(card.getUser_id());
                String pdfpath = "C:/Users/moata/Desktop/Sportify-App_Javafx-master/" + c.getFirstname()+".pdf";
                
                cc.ajouter_commande(c);
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirmation");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Do you want to pay now?");

    ButtonType payNowButton = new ButtonType("Pay Now");
    ButtonType payLaterButton = new ButtonType("Pay Later");

    confirmationAlert.getButtonTypes().setAll(payNowButton, payLaterButton);
    final Commande finalC = c;
    confirmationAlert.showAndWait().ifPresent(buttonType -> {
        if (buttonType == payNowButton) {
            // User clicked "Pay Now"
            // Load another GUI
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/commande/Payment.fxml"));
                Parent paymentRoot = loader.load();
                PayController paymentController = loader.getController();
                
                paymentController.setData(finalC);

                Scene paymentScene = new Scene(paymentRoot);
    Stage stage = new Stage();
    stage.setScene(paymentScene);
    stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        } else if (buttonType == payLaterButton) {
            // User clicked "Pay Later"
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Command Created");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Command created successfully!");
            successAlert.showAndWait();
            try {
                cc.createPDF(finalC);
            } catch (DocumentException ex) {
                Logger.getLogger(form_commande.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(form_commande.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Open the PDF file
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            try {
                File file = new File(pdfpath);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        
        } else {
            // Desktop ou l'action OPEN n'est pas pris en charge sur la plate-forme actuelle
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("we have an error while we open the file try later");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
          }
            }
        });
    }
        c = cc.trouver_commande_par_card_id(card.getId());
        CardItemCrud cic = new CardItemCrud();
        HistoriquesCrud hc = new HistoriquesCrud();
        List<CardItem> list_items = cic.lister_card_items_Card_id(card.getId());
        if(!list_items.isEmpty()){
            
            for(CardItem item : list_items){
                Historique h = new Historique();
                h.setCommande_id(c.getId());
                h.setLibelle(item.getLibelle());
                h.setPrix(item.getPrix());
                h.setQuantite(item.getQuantite());
                h.setOwner_id(item.getOwner_id());
                hc.ajouter_historique(h);
                cic.supprimer_card_item(item.getId());
            }
            // cx.supprimer_card(card.getId());
        }
    
                                redirectToHomePage(event);

        }    
            

    @FXML
    private void retour(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/produit/panier.fxml"));
                Parent root = loader.load();
                //UPDATE The Controller with Data :
                panierController controller = loader.getController();
                controller.setData(this.card);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
    private void showErrorAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.setTitle("Problem");
    alert.setHeaderText(null);
    alert.showAndWait();
}
    private void redirectToHomePage(ActionEvent event) {
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
    
    
    
}}
  /*   Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error occurred while creating the command.");
            errorAlert.showAndWait();*/
        
    
                /*cc.ajouter_commande(c);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added .");
                alert.setHeaderText(null);
                alert.show();*/
                //redirectToListProduit();
                
            
       // cc.ajouter_commande(c);


           /* Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Command created successfully!");
            
            cc.createPDF(c);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            try {
                File file = new File(pdfpath);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                // GÃ©rez les erreurs liÃ©es Ã  l'ouverture du fichier PDF
            }*/