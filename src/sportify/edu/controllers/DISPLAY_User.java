/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.controllers;

import com.jfoenix.controls.JFXSnackbar;
import java.io.IOException;
import sportify.edu.entities.*;
import sportify.edu.services.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 *
 * @author moata
 */
public class DISPLAY_User implements Initializable {

    @FXML
    private TableColumn<User, Integer> fxemail;
    @FXML
    private TableColumn<User, String> fxnom;
    @FXML
    private TableColumn<User, String> fxprenom;
    @FXML
    private TableColumn<User, String> fxusername;
    @FXML
    private TableColumn<User, String> fxnum;
    @FXML
    private TableView<User> fxtableutilisateur;
    @FXML
    private TextField fxrecherche;
    @FXML
    private TableColumn fxblock;
    @FXML
    private MenuButton fxmenu;
    @FXML
    ButtonBar verif_bar;
    @FXML
    Button btn_verif;
    @FXML
private AnchorPane rootPane;

    private List<User> utilisateurs;
    private List<User> utilisateursFiltres;
    private CRUDUser cu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verif_bar.setVisible(false);
        if(!SecurityService.getCurrentUtilisateur().isIsVerified()){
            verif_bar.setVisible(true);
        }
        fxmenu.setText(SecurityService.getCurrentUtilisateur().getNomUtilisateur());
        MenuItem menuItem1 = new MenuItem("My profile");
        MenuItem menuItem2 = new MenuItem("Logout");
        MenuItem menuItem3 = new MenuItem("Changer mot de passe");
        fxmenu.getItems().addAll(menuItem1, menuItem2,menuItem3);

        menuItem1.setOnAction((event) -> {
        loadFXML("../gui/security/gerercompte.fxml");
    });
        menuItem2.setOnAction(event -> redirectToFxml("../gui/security/login.fxml"));
        menuItem3.setOnAction((event) -> {
        loadFXML("../gui/security/changermdp.fxml");
    });// TODO : Generation username , numero , auth 3 fois , complexitté de mdp
        cu = new CRUDUser();
        
        utilisateurs = cu.afficherUtilisateur();
        fxtableutilisateur.setItems(FXCollections.observableArrayList(utilisateurs));

        fxemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        fxnom.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        fxprenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        fxusername.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
        fxnum.setCellValueFactory(new PropertyValueFactory<>("phone"));
        fxblock.setCellFactory(column -> new TableCell<User, Boolean>() {
            private final Button button = new Button();

            {
                button.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    cu.modifierUtilisateur(user.getId(), !user.isStatus());
                    utilisateurs = cu.afficherUtilisateur();
                    fxtableutilisateur.setItems(FXCollections.observableArrayList(utilisateurs));
//                    initialize(url,rb);
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    User user = getTableView().getItems().get(getIndex());
                    if (user.isStatus()) {
                        button.setText("Unblock");
                    } else {
                        button.setText("Block");    
                    }
                    setGraphic(button);
                    setText(null);
                    setGraphic(button);
                }
            }
        });
        fxrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            utilisateursFiltres = utilisateurs.stream()
                    .filter(utilisateur -> utilisateur.getEmail().toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            fxtableutilisateur.setItems(FXCollections.observableArrayList(utilisateursFiltres));
        });

    }
    private void loadFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToFxml(String fxml) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) fxmenu.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void verifyEmail(ActionEvent event) throws MessagingException{
        MailSender.sendMail(SecurityService.getCurrentUtilisateur().getEmail(), SecurityService.getCurrentUtilisateur().getVerifCode());
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Verification de l'email");
                dialog.setHeaderText("Un code a été envoyé à votre email");
                dialog.setContentText("Code:");
                Optional<String> result = dialog.showAndWait();
                while (result.isPresent()) {
                    String code = result.get();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (!code.equals(SecurityService.getCurrentUtilisateur().getVerifCode())) {
                        alert.setTitle("Code incorrecte");
                        alert.setHeaderText("Vérifier l'email envoyé");
                        alert.showAndWait();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/security/login.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                            alert.setTitle("Echec de vérification d'email");
                            alert.setHeaderText("Vous pouvez s'authentifier et vérifier plus tard");
                            alert.showAndWait();
                            break;
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }

                        break;
                    } else {
                        SecurityService.emailVerified(SecurityService.getCurrentUtilisateur().getEmail());
                        alert.setTitle("Résultat");
                        alert.setHeaderText("Email vérifié avec succès");
                        alert.showAndWait();    
                        verif_bar.setVisible(false);
                            break;
                        }
                    }
    
                
    }
    
}
