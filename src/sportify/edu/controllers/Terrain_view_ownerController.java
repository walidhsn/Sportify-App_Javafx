/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sportify.edu.entities.Terrain;
import sportify.edu.services.TerrainService;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sportify.edu.services.SecurityService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Terrain_view_ownerController implements Initializable {

    @FXML
    private TableView<Terrain> table_terrain;
    @FXML
    private TableColumn<Terrain, String> name_col;
    @FXML
    private TableColumn<Terrain, Integer> capacity_col;
    @FXML
    private TableColumn<Terrain, String> sportType_col;
    @FXML
    private TableColumn<Terrain, Double> rentPrice_col;
    @FXML
    private TableColumn<Terrain, String> disponibility_col;
    @FXML
    private TableColumn<Terrain, Integer> postalCode_col;
    @FXML
    private TableColumn<Terrain, String> city_col;
    @FXML
    private TableColumn<Terrain, String> country_col;
    @FXML
    private TableColumn<Terrain, String> updatedAt_col;
    @FXML
    private TableColumn<Terrain, String> details_col;
    @FXML
    private TableColumn<Terrain, String> update_col;
    @FXML
    private TableColumn<Terrain, String> delete_col;
    @FXML
    private TextField search_text;
    @FXML
    private Button add_btn;
    @FXML
    private MenuButton fxmenu;

    private List<Terrain> list_terrain;
    private List<Terrain> list_terrain_search;
    private TerrainService ts;
    private ImageView icon_delete;
    private ImageView icon_update;
    private ImageView icon_view;
    @FXML
    private ImageView add_icon;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;

    private int id_user;
    
    public void setData(int id_user){
        this.id_user = id_user;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ts = new TerrainService();
        id_user=1; // this will be modified by the user id getted from the user instance 
        list_terrain = ts.myProprieties(id_user);
        table_terrain.setItems(FXCollections.observableArrayList(list_terrain));
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
    });
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        sportType_col.setCellValueFactory(new PropertyValueFactory<>("sportType"));
        rentPrice_col.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        disponibility_col.setCellValueFactory(new PropertyValueFactory<>("disponibility"));
        postalCode_col.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
        country_col.setCellValueFactory(new PropertyValueFactory<>("country"));
        updatedAt_col.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        Image icon = new Image("/resources/add.png");
        add_icon.setImage(icon);
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            list_terrain_search = list_terrain.stream()
                    .filter(terrain -> terrain.getName().toLowerCase().contains(newValue.toLowerCase())
                    || terrain.getCity().toLowerCase().contains(newValue.toLowerCase())
                    || terrain.getCountry().toLowerCase().contains(newValue.toLowerCase())
                    || terrain.getSportType().toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(terrain.getCapacity()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(terrain.getPostalCode()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(terrain.getRentPrice()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(terrain.getId()).toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            table_terrain.setItems(FXCollections.observableArrayList(list_terrain_search));
        });
        // Delete Button : 
        delete_col.setCellFactory(column -> {
            return new TableCell<Terrain, String>() {

                final Button deleteButton = new Button();

                // Set the button properties
                {
                    icon_delete = new ImageView(new Image("/resources/delete.png"));
                    icon_delete.setFitWidth(20);
                    icon_delete.setFitHeight(20);
                    deleteButton.setGraphic(icon_delete);
                    deleteButton.setText("Delete");
                    deleteButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Confirmation");
                            alert.setContentText("Do you really want to delete this?");
                            Optional<ButtonType> btn = alert.showAndWait();
                            Terrain data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                ts.deleteEntity(data.getId());
                                refresh();
                            }
                        });
                    }
                }
            };
        });

        //Update Button : 
        update_col.setCellFactory(column -> {
            return new TableCell<Terrain, String>() {

                final Button updateButton = new Button();

                // Set the button properties
                {
                    icon_update = new ImageView(new Image("/resources/update.png"));
                    icon_update.setFitWidth(20);
                    icon_update.setFitHeight(20);
                    updateButton.setGraphic(icon_update);
                    updateButton.setText("Update");
                    updateButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                        updateButton.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");

                            alert.setHeaderText("Update Confirmation");
                            alert.setContentText("Do you really want to update this?");

                            Optional<ButtonType> btn = alert.showAndWait();
                            Terrain data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());                           
                            if (btn.get() == ButtonType.OK) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/UPDATE_Terrain.fxml"));
                                    Parent root = loader.load();
                                    //UPDATE The Controller with Data :
                                    UPDATE_TerrainController controller = loader.getController();
                                    controller.setTerrain(data);
                                    //-----
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) table_terrain.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                }
            };
        });

        //View Button : 
        details_col.setCellFactory(column -> {
            return new TableCell<Terrain, String>() {

                final Button viewButton = new Button();

                // Set the button properties
                {
                    icon_view = new ImageView(new Image("/resources/eye.png"));
                    icon_view.setFitWidth(20);
                    icon_view.setFitHeight(20);
                    viewButton.setGraphic(icon_view);
                    viewButton.setText("View");
                    viewButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                        viewButton.setOnAction((ActionEvent event) -> {
                            try {
                                Terrain data = getTableView().getItems().get(getIndex());
                                System.out.println(data);
                                System.out.println("selectedData: " + data.getId()); 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/DETAILS_Terrain.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                DETAILS_TerrainController controller = loader.getController();
                                controller.setInformation_Terrain(data);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_terrain.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
            };
        });
    }

    private void refresh() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../gui/terrain/Terrain_view_owner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_terrain.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToAddTerrain(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/ADD_Terrain.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void returnHomePage(ActionEvent event) {
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
    
}
