/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import entities.Academy;
import entities.Coach;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import services.AcademyCRUD;
import services.CoachCRUD;

/**
 * FXML Controller class
 *
 * @author ramib
 */
public class CoachAdd implements Initializable {

    @FXML
    private TextField nameField;
    
    @FXML
    private Button btnSubmit;
    @FXML
    private ImageView backIcon;
    private ImageView coach_image;
    
    private CoachCRUD coachCRUD = new CoachCRUD();
    @FXML
    private TextField nameField1;
    @FXML
    private TextField nameField2;
    @FXML
    private ChoiceBox<Academy> AcademyChoice;
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Academy> academies = academyCRUD.display();
        AcademyChoice.getItems().addAll(academies);
        AcademyChoice.setConverter(new StringConverter<Academy>() {
            @Override
            public String toString(Academy academy) {
                if (academy == null) {
                    return "";
                } else {
                    return academy.getName();
                }
            }
            @Override
            public Academy fromString(String string) {
                return null;
            }
        });
        
//        Image btn_icon = new Image("/resources/icons/back.png");
//        backIcon.setImage(btn_icon);

    }
    
    @FXML
    private void CoachAdd(javafx.event.ActionEvent event) throws IOException, GeoIp2Exception {
        String name = nameField.getText();
        String email = nameField1.getText();
        String phone = nameField2.getText();
        Academy academyChoice = AcademyChoice.getValue();
        String academy = AcademyChoice.getConverter().toString(academyChoice);
        
        if (name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()  ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            // Check if name or category field contains non-letter characters
        } else if (!name.matches("[a-zA-Z ]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Name Field Can Only Contain Letters");
            alert.setContentText("Please enter a valid name");
            alert.showAndWait();
        
        } else if (containsBadWords(name)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Coach Name");
            alert.setContentText("The name contains a bad word");
            alert.showAndWait();
            
        } else if (coachCRUD.coachExists(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Coach Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("A coach with the same name already exists");
            alert.showAndWait();
        } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Email Address");
            alert.setContentText("Please enter a valid email address");
            alert.showAndWait();
        }else if (!phone.matches("\\d{8}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Phone Number");
            alert.setContentText("Please enter a 8-digit phone number");
            alert.showAndWait();   
        } else {
        Coach coach = new Coach(name, email, phone, academy);          
        coachCRUD.addEntity(coach);
        System.out.println("Coach added successfully");
        redirect();
        }
         
    }
    
    
    
    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/CoachList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void redirect() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Gui/CoachList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
//Method that generate country code from IP
    private static final String DATABASE_PATH = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Api/GeoLite2-Country.mmdb";

    public static String getCountryCode() throws GeoIp2Exception {
        try {
            // Get the user's IP address
            InetAddress ip = InetAddress.getLocalHost();

            // Load the GeoIP2 database
            File database = new File(DATABASE_PATH);
            DatabaseReader reader = new DatabaseReader.Builder(database).build();

            // Look up the user's IP address and get the country code
            String countryCode = reader.country(ip).getCountry().getIsoCode();

            return countryCode;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    //Badwords filter
    private static final Set<String> BAD_WORDS = new HashSet<>();

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Badwords.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                BAD_WORDS.add(line.trim().toLowerCase());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean containsBadWords(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (BAD_WORDS.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    
    

}