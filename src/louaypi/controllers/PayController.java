/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.controllers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.stripe.exception.StripeException;
import static com.stripe.param.PaymentLinkCreateParams.ShippingAddressCollection.AllowedCountry.MM;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import louaypi.entities.Commande;
import louaypi.services.CommandeCrud;
import louaypi.services.Payement;

/**
 * FXML Controller class
 *
 * @author louay
 */
public class PayController implements Initializable {

    @FXML
    private Label total;
    @FXML
    private Button pay_btn;
    @FXML
    private TextField name;
    @FXML
    private Spinner<Integer> cvc;
    @FXML
    private Spinner<Integer> month;
    @FXML
    private Spinner<Integer> year;
    @FXML
    private TextField mail;
    @FXML
    private TextField num;
    @FXML
    private Button back_btn;
    private float total_pay;
    
    private TextField spinnerTextField;
    private Commande cmd;
    private CommandeCrud cc;
    /**
     * Initializes the controller class.
     */
    public void setData(Commande c) {
        this.cmd = c;
        cc = new CommandeCrud();
        total_pay = (c.getTotal());
        int mm = LocalDate.now().getMonthValue();
        int yy = LocalDate.now().getYear();
        SpinnerValueFactory<Integer> valueFactory_month = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, mm, 1);// (min,max,startvalue,incrementValue) 
        SpinnerValueFactory<Integer> valueFactory_year = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, yy, 1);// (min,max,startvalue,incrementValue) 
        SpinnerValueFactory<Integer> valueFactory_cvc = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1, 1);// (min,max,startvalue,incrementValue) 
        month.setValueFactory(valueFactory_month);
        year.setValueFactory(valueFactory_year);
        cvc.setValueFactory(valueFactory_cvc);
        String total_txt = "Total : " + String.valueOf(total_pay) + " Dt.";
        total.setText(total_txt);
        spinnerTextField= cvc.getEditor();
        spinnerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                cvc.getValueFactory().setValue(Integer.parseInt(newValue));
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void payment(ActionEvent event) throws DocumentException, BadElementException, IOException {
        String customerName = name.getText().trim();
        String customerEmail = mail.getText().trim();
        String cardNumber = num.getText().trim();
        int cardExpMonth = month.getValue();
        int cardExpYear = year.getValue();
        String cardCvc = cvc.getValue().toString();

        // Input validation
        if (customerName.isEmpty() || customerEmail.isEmpty() || cardNumber.isEmpty() || cardCvc.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You need to input all informations");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        try {
            boolean paymentSuccess = Payement.processPayment(customerName, customerEmail, total_pay, cardNumber, cardExpMonth, cardExpYear, cardCvc);
               String pdfpath = "C:/Users/louay/OneDrive/Documents/NetBeansProjects/louaypii/" + cmd.getFirstname()+".pdf";
            if (paymentSuccess) {
                cc.createPDFP(cmd);
                redirectToHomePage(event);
                
            //if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            try {
                File file = new File(pdfpath);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                // GÃ©rez les erreurs liÃ©es Ã  l'ouverture du fichier PDF
            }
        //} else {
            // Desktop ou l'action OPEN n'est pas pris en charge sur la plate-forme actuelle
           /* Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("we have an error while we open the file try later");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        }*/
            } else {
                // Payment failed
                // Handle the failure scenario, show an error message, etc.
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You payement failed");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            }
        } catch (StripeException e) {
            // Exception occurred during payment processing
            // Handle the exception, log the error, show an error message, etc.
            e.printStackTrace();
        }
    }
    @FXML
    private void redirectToListReservation(ActionEvent event) {
    }
    private void redirectToHomePage(ActionEvent event) {
    try {
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/louaypi/gui/produit/Produit_view_client.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    
    
    
}
}
