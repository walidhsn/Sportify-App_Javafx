/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import sportify.edu.services.SecurityService;

/**
 *
 * @author moata
 */
public class MapController implements Initializable {

    @FXML
    private Button fxsave;
    @FXML
    private WebView webView;

    private double currentLat = 0;
    private double currentLong = 0;
    private boolean adresseValide = false;
    private String adresse = "";


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        WebEngine webEngine = webView.getEngine();
        String apiKey = "AIzaSyBLOY-8VaGbh9cjzdivIpgQh8R8eG0ltA4";
        webEngine.loadContent("<iframe src='https://www.google.com/maps/embed/v1/place?q=Ariana&amp;key=AIzaSyBLOY-8VaGbh9cjzdivIpgQh8R8eG0ltA4'></iframe>");

        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.contains("@")) {
                String[] locationParts = newValue.split("@")[1].split(",");
                double latitude = Double.parseDouble(locationParts[0]);
                currentLat = Double.parseDouble(locationParts[0]);
                double longitude = Double.parseDouble(locationParts[1]);
                currentLong = longitude;
                System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
            }
            try {
                // Build the API request URL
                String url2 = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + URLEncoder.encode(currentLat + "," + currentLong, "UTF-8") + "&key=" + apiKey;
                System.out.println(url2);
                // Make the API request
                HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
                conn.setRequestMethod("GET");

                // Parse the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Extract the address information from the response
                JSONObject json = new JSONObject(response.toString());
                if (json.getString("status").equals("OK")) { 
                    adresseValide = true;
                    adresse = json.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    System.out.println("Address: " + adresse);
                }else{
                    System.out.println("Pas encore choisi...");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void saveAdress(ActionEvent event) {
        if(adresseValide){
            SecurityService.setCurrentAdresse(adresse);
            System.out.println(SecurityService.getCurrentAdresse());
            Stage stage = (Stage) fxsave.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Veuillez choisir une adresse valide");
            alert.setHeaderText("L'adresse que vous avez choisi n'est pas valide.");
            alert.showAndWait();
            return;
        }
    }

}
