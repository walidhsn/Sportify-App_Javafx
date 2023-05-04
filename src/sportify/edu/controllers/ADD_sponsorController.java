/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.entities.Sponsor;
import sportify.edu.services.SponsorService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class ADD_sponsorController implements Initializable {

    @FXML
    private Button sponsor_addBtn;
    @FXML
    private TextField sponsor_name;
    @FXML
    private TextField sponsor_email;
    @FXML
    private TextField sponsor_tel;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;

    private SponsorService ss;
    private int id_owner;

    public void setData(int id_owner) {
        this.id_owner = id_owner;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ss = new SponsorService();
    }

    @FXML
    private void add_sponsor(ActionEvent event) {
        Sponsor verif;
        String name = sponsor_name.getText();
        String email = sponsor_email.getText();
        String tel = sponsor_tel.getText();
        // Control :
        if (name.isEmpty() && name.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Sponsor 'Name'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            sponsor_name.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(sponsor_name).play();
        } else if (email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Sponsor 'Email'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            sponsor_email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(sponsor_email).play();
        } else if (!isValidEmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter a valid Email");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            sponsor_email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(sponsor_email).play();
        } else if (tel.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Sponsor 'Telephone'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            sponsor_tel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(sponsor_tel).play();
        } else if (!isValidPhoneNumber(tel)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter a valid phone Number");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            sponsor_tel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(sponsor_tel).play();
        } else {
            sponsor_name.setStyle(null);
            sponsor_email.setStyle(null);
            sponsor_tel.setStyle(null);
            verif = ss.find_sponsor(name, email, tel);
            if (verif == null) {
                Sponsor s = new Sponsor(name, email, Integer.parseInt(tel));
                ss.addEntity(s);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added .");
                alert.setHeaderText(null);
                alert.show();
                redirectToListSponsor();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Sponsor already exist.");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void returnToListSponsor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/Sponsor_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Sponsor_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static boolean isValidPhoneNumber(String input) {
        // Remove any non-digit characters from the input string
        String digits = input.replaceAll("[^\\d]", "");

        // Check if the remaining string contains exactly 8 digits
        return digits.length() == 8 && digits.matches("\\d{8}");
    }

    private boolean isValidEmail(String email) {
        // Trim the input string to remove any leading or trailing whitespace
        email = email.trim();
        // Regular expression pattern to match an email address
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the email address
        Matcher matcher = pattern.matcher(email);

        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }

    private void redirectToListSponsor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/sponsor/Sponsor_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Sponsor_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
