/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import sportify.edu.entities.Country;
import sportify.edu.entities.Terrain;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class ADD_terrainController implements Initializable {

    @FXML
    private Button terrain_addBtn;
    @FXML
    private CheckBox terrain_disponibility;
    @FXML
    private ChoiceBox<String> terrain_sportType;
    @FXML
    private TextField terrain_name;
    @FXML
    private Spinner<Integer> terrain_capacity;
    @FXML
    private Spinner<Double> terrain_rentPrice;
    @FXML
    private Spinner<Integer> terrain_postalCode;
    @FXML
    private TextField terrain_roadName;
    @FXML
    private Spinner<Integer> terrain_roadNumber;
    @FXML
    private TextField terrain_city;
    @FXML
    private ComboBox<Country> terrain_country;

    private final String[] types = {"football", "handball", "basketball", "volleyball", "baseball", "tennis", "golf"};
    @FXML
    private ImageView terrain_image;
    @FXML
    private Button upload_image;

    private File file;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    @FXML
    private ImageView upload_img_icon;

    private AutoCompletionBinding<String> autoCompletionBinding;

    private TerrainService ts;
    private Set<String> possible_suggestions;
    private int id_owner;

    public void setData(int id_owner) {
        this.id_owner = id_owner;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ts = new TerrainService();
        possible_suggestions = ts.get_autoCompleteWords();
        autoCompletionBinding = TextFields.bindAutoCompletion(terrain_city, possible_suggestions);
        terrain_city.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        autoCompleteLearn(terrain_city.getText().trim());
                        break;
                    default:
                        break;
                }
            }

        });
        terrain_sportType.getItems().addAll(types);
        SpinnerValueFactory<Integer> valueFactory_c = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1, 1);// (min,max,startvalue,incrementValue)
        SpinnerValueFactory<Integer> valueFactory_pc = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999999, 0, 1);
        SpinnerValueFactory<Integer> valueFactory_rn = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999999, 0, 1);
        terrain_capacity.setValueFactory(valueFactory_c);
        terrain_postalCode.setValueFactory(valueFactory_pc);
        terrain_roadNumber.setValueFactory(valueFactory_rn);
        SpinnerValueFactory<Double> valueFactory_f = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 9999999.0, 1, 0.25);
        terrain_rentPrice.setValueFactory(valueFactory_f);
        Image btn_icon = new Image("/resources/back-arrow.png");
        Image btn_upload_img_icon = new Image("/resources/upload-image-icon.png");
        backBtn_icon.setImage(btn_icon);
        upload_img_icon.setImage(btn_upload_img_icon);
        terrain_country.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country == null ? null : country.getName();
            }

            @Override
            public Country fromString(String string) {
                return null;
            }
        });
        // Populate the choice box with countries
        ArrayList<Country> countries = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (int i = 0; i < countryCodes.length; i++) {
            String countryCode = countryCodes[i];
            if (countryCode.equals("IL")) { // Deleting israel from the list of countries
                continue; // skip
            }
            Locale locale = new Locale("en", countryCode);
            String countryName = locale.getDisplayCountry();
            Country country = new Country(countryCode, countryName);
            countries.add(country);
        }

        terrain_country.setItems(FXCollections.observableArrayList(countries));
        terrain_country.setCellFactory(param -> new CountryListCell());

    }

    @FXML
    private void add_terrain(ActionEvent event) {
        Terrain verif;
        int id_user = 1; // Just For Test will be replaced
        //Getting Values :        
        String name = terrain_name.getText();
        Integer capacity = terrain_capacity.getValue();
        String sport_type = terrain_sportType.getValue();
        Double rent_price = terrain_rentPrice.getValue();
        boolean disponibility = terrain_disponibility.isSelected();
        Integer postalCode = terrain_postalCode.getValue();
        String roadName = terrain_roadName.getText();
        Integer roadNumber = terrain_roadNumber.getValue();
        String city = terrain_city.getText();
        String country = terrain_country.getValue().getName();

        // Control :
        if (name.isEmpty() && name.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Terrain 'Name'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (sport_type.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("'Sport Type' must be selected");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (roadName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Terrain 'Road Name'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (city.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Terrain 'City'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (country.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must input the Terrain 'Country'");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            verif = ts.find_terrain(name, city, country);
            if (verif == null) {
                if (file != null) {
                    String destPath = "C:/Users/WALID/Desktop/WEBPI/WEBPI/public/uploads/terrain/";
                    String imageName = generateUniqueName(file); // Generate a unique name for the image
                    File dest = new File(destPath + imageName); // Set the destination path for the image
                    try {
                        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING); // Copy the image to the destination folder

                        Terrain t = new Terrain(id_user, name, capacity, sport_type, rent_price.floatValue(), disponibility, postalCode, roadName, roadNumber, city, country, imageName, LocalDateTime.now());
                        ts.addEntity(t);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Added .");
                        alert.setHeaderText(null);
                        alert.show();
                        redirectToListTerrain();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } else {
                    Terrain t = new Terrain(id_user, name, capacity, sport_type, rent_price.floatValue(), disponibility, postalCode, roadName, roadNumber, city, country, null, LocalDateTime.now());
                    ts.addEntity(t);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Added .");
                    alert.setHeaderText(null);
                    alert.show();
                    redirectToListTerrain();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Stadium Already Exist,try another one.");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void upload_imageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterJPEG
                = new FileChooser.ExtensionFilter("JPEG files (*.JPEG)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterjpeg
                = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng, extFilterJPEG, extFilterjpeg);
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String image_uri = file.toURI().toString();
            // Display the selected image in an ImageView
            Image image = new Image(image_uri);
            terrain_image.setImage(image);
        }
    }

    private String generateUniqueName(File file) {
        String name = file.getName();
        String extension = name.substring(name.lastIndexOf(".")).toLowerCase(); // Get the file extension
        String imageName = UUID.randomUUID().toString() + extension; // Generate a unique name with the same extension
        return imageName;
    }

    @FXML
    private void returnToListTerrain(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/Terrain_view_owner.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void redirectToListTerrain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/Terrain_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Terrain_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static class CountryListCell extends ListCell<Country> {

        private final ImageView imageView = new ImageView();

        @Override
        protected void updateItem(Country item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());
                Image img = new Image("file:C:/Users/WALID/Desktop/Sportify_java/Sportify/src/resources/countries/" + item.getCode().toLowerCase() + ".png");
                imageView.setImage(img);
                imageView.setFitWidth(35);
                imageView.setFitHeight(30);
                setGraphic(imageView);
            }
        }
    }

    private void autoCompleteLearn(String word) {
        if (!word.isEmpty()) {
            ts.add_autoCompleteWord(word);
            possible_suggestions = ts.get_autoCompleteWords();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(terrain_city, possible_suggestions);
        }
    }
}
