/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sportify.edu.entities.Reservation;
import sportify.edu.entities.Terrain;
import sportify.edu.services.ReservationService;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Terrain_chartController implements Initializable {

    @FXML
    private BorderPane borderPane;
    private int id_owner;

    public void setData(int id_owner) {
        this.id_owner = id_owner;
        barChart();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void backToListTerrain(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/terrain/Terrain_view_owner.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Terrain_view_ownerController controller = loader.getController();
            controller.setData(this.id_owner);
            Scene scene = new Scene(root);
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void showPieChart(ActionEvent event) {
        PieChart();
    }

    @FXML
    private void showBarChart(ActionEvent event) {
        barChart();
    }


    private void barChart() {
        TerrainService ts = new TerrainService();
        ReservationService rs = new ReservationService();
        Map<String, Double> data_map = new HashMap<>();
        List<Terrain> listTerrains = ts.myProprieties(id_owner);
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Stadiums");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Revenues in Dt");
        xAxis.setTickLabelFill(Paint.valueOf("#ffffff"));
        yAxis.setTickLabelFill(Paint.valueOf("#ffffff"));
        BarChart barChart = new BarChart(xAxis, yAxis);
        XYChart.Series data = new XYChart.Series();
        data.setName("Stadiums Revenues en Dt");
        // Prepare Data :
        for (Terrain terrain : listTerrains) {
            Double revenue = 0.0;
            Double app_profit = 0.0;
            List<Reservation> listReservations = rs.terrain_reservations(terrain.getId());
            for (Reservation reservation : listReservations) {
                revenue += (reservation.getNbPerson() * terrain.getRentPrice());
                //to Do adding the equipments that has been rented
            }
            app_profit = ((20 * revenue) / 100);
            revenue -= app_profit;
            data_map.put(terrain.getName(), revenue);
        }
        //provide Data : 
        for (Map.Entry<String, Double> element : data_map.entrySet()) {
            data.getData().add(new XYChart.Data(element.getKey(), element.getValue()));
        }
        barChart.getData().add(data);
        barChart.setTitle("Staduims Revenues - Bar-Chart");
        //add barChart to the Pane : 
        borderPane.setCenter(barChart);
    }

    private void PieChart() {
        TerrainService ts = new TerrainService();
        ReservationService rs = new ReservationService();
        Map<String, Double> data_map = new HashMap<>();
        List<Terrain> listTerrains = ts.myProprieties(id_owner);
        // create an empty ObservableList
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
        // Prepare Data :
        for (Terrain terrain : listTerrains) {
            Double revenue = 0.0;
            Double app_profit = 0.0;
            List<Reservation> listReservations = rs.terrain_reservations(terrain.getId());
            for (Reservation reservation : listReservations) {
                revenue += (reservation.getNbPerson() * terrain.getRentPrice());
                //to Do adding the equipments that has been rented
            }
            app_profit = ((20 * revenue) / 100);
            revenue -= app_profit;
            data_map.put(terrain.getName(), revenue);
        }
        //provide Data : 
        for (Map.Entry<String, Double> element : data_map.entrySet()) {
            piechartData.add(new PieChart.Data(element.getKey(), element.getValue()));
        }
        // create a PieChart with the data from the ObservableList :
        PieChart pieChart = new PieChart(piechartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Staduims Revenues - Pie-Chart");
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        pieChart.setLegendSide(Side.LEFT);
        //Set it to the Pane:
        borderPane.setCenter(pieChart);
    }
}
