/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Button filterByYearButton;
    private Button filterByMonthButton;
    private Button filterByWeekButton;
    private Button defaultButton;
    private VBox buttonBox;
    private String currentChartType; // instance variable to track the current chart type
    private TerrainService ts;
    private ReservationService rs;
    private Label status;
    public void setData(int id_owner) {
        this.id_owner = id_owner;
        barChart();
        currentChartType = "bar";
        status.setText("Bar Chart - All : ");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ts = new TerrainService();
        rs = new ReservationService();
        status = new Label();
        filterByYearButton = new Button("Filter by Year");
        filterByMonthButton = new Button("Filter by Month");
        filterByWeekButton = new Button("Filter by Week");
        defaultButton = new Button("All");
        buttonBox = new VBox(filterByYearButton, filterByMonthButton, filterByWeekButton, defaultButton);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));
        borderPane.setRight(buttonBox);
        status.setPadding(new Insets(10));
        borderPane.setLeft(status);
        // add event handlers to the buttons to filter the chart data
        filterByYearButton.setOnAction(e -> {
            if (currentChartType.equals("pie")) {
                filterPieChartByYear();
                status.setText("Pie Chart - By Year: ");
            } else if (currentChartType.equals("bar")) {
                filterBarChartByYear();
                status.setText("Bar Chart - By Year: ");
            }
        });

        filterByMonthButton.setOnAction(e -> {
            if (currentChartType.equals("pie")) {
                filterPieChartByMonth();
                status.setText("Pie Chart - By Month: ");
            } else if (currentChartType.equals("bar")) {
                filterBarChartByMonth();
                status.setText("Bar Chart - By Month : ");
            }
        });

        filterByWeekButton.setOnAction(e -> {
            if (currentChartType.equals("pie")) {
                filterPieChartByWeek();
                status.setText("Pie Chart - By Week : ");
            } else if (currentChartType.equals("bar")) {
                filterBarChartByWeek();
                status.setText("Bar Chart - By Week : ");
            }
        });
        defaultButton.setOnAction(e -> {
            if (currentChartType.equals("pie")) {
                PieChart();
                status.setText("Pie Chart - All : ");
            } else if (currentChartType.equals("bar")) {
                barChart();
                status.setText("Bar Chart - All : ");
            }
        });
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
        currentChartType = "pie";
        PieChart();
        status.setText("Pie Chart - All : ");
    }

    @FXML
    private void showBarChart(ActionEvent event) {
        currentChartType = "bar";
        barChart();
        status.setText("Bar Chart - All : ");
    }

    private void barChart() {
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
        final Label caption = new Label("");

        // create a PieChart with the data from the ObservableList :
        PieChart pieChart = new PieChart(piechartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Staduims Revenues - Pie-Chart");
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        pieChart.setLegendSide(Side.LEFT);

        // create a StackPane to hold the pie chart and the caption label
        StackPane pieChartPane = new StackPane();
        pieChartPane.getChildren().addAll(pieChart, caption);
        borderPane.setCenter(pieChartPane);

        // add the event handler to each pie slice
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getX());
                    caption.setTranslateY(e.getY());
                    caption.setText(String.valueOf(data.getPieValue()) + " Dt");
                    caption.setTextFill(Paint.valueOf("#ffffff"));
                    caption.setStyle("-fx-background-color: black; -fx-padding: 5px;-fx-font-size: 18px;");
                    caption.setVisible(true);
                }
            });
        }

        // hide the caption label when the mouse is released
        pieChartPane.setOnMouseReleased(e -> caption.setVisible(false));

    }
    // filter methods for the pie chart : -------------------------

    private void filterPieChartByYear() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        Map<String, Double> data_map = new HashMap<>();
        List<Terrain> listTerrains = ts.myProprieties(id_owner);
        // create an empty ObservableList
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
        // Prepare Data :
        for (Terrain terrain : listTerrains) {
            Double revenue = 0.0;
            Double app_profit = 0.0;
            List<Reservation> listReservations = rs.terrain_reservations_by_year(terrain.getId(), year);
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
        final Label caption = new Label("");

        // create a PieChart with the data from the ObservableList :
        PieChart pieChart = new PieChart(piechartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Staduims Revenues - Pie-Chart");
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        pieChart.setLegendSide(Side.LEFT);

        // create a StackPane to hold the pie chart and the caption label
        StackPane pieChartPane = new StackPane();
        pieChartPane.getChildren().addAll(pieChart, caption);
        borderPane.setCenter(pieChartPane);

        // add the event handler to each pie slice
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getX());
                    caption.setTranslateY(e.getY());
                    caption.setText(String.valueOf(data.getPieValue()) + " Dt");
                    caption.setTextFill(Paint.valueOf("#ffffff"));
                    caption.setStyle("-fx-background-color: black; -fx-padding: 5px;-fx-font-size: 18px;");
                    caption.setVisible(true);
                }
            });
        }

        // hide the caption label when the mouse is released
        pieChartPane.setOnMouseReleased(e -> caption.setVisible(false));
    }

    private void filterPieChartByMonth() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        Map<String, Double> data_map = new HashMap<>();
        List<Terrain> listTerrains = ts.myProprieties(id_owner);
        // create an empty ObservableList
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
        // Prepare Data :
        for (Terrain terrain : listTerrains) {
            Double revenue = 0.0;
            Double app_profit = 0.0;
            List<Reservation> listReservations = rs.terrain_reservations_by_month(terrain.getId(), year, month);
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
        final Label caption = new Label("");

        // create a PieChart with the data from the ObservableList :
        PieChart pieChart = new PieChart(piechartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Staduims Revenues - Pie-Chart");
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        pieChart.setLegendSide(Side.LEFT);

        // create a StackPane to hold the pie chart and the caption label
        StackPane pieChartPane = new StackPane();
        pieChartPane.getChildren().addAll(pieChart, caption);
        borderPane.setCenter(pieChartPane);

        // add the event handler to each pie slice
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getX());
                    caption.setTranslateY(e.getY());
                    caption.setText(String.valueOf(data.getPieValue()) + " Dt");
                    caption.setTextFill(Paint.valueOf("#ffffff"));
                    caption.setStyle("-fx-background-color: black; -fx-padding: 5px;-fx-font-size: 18px;");
                    caption.setVisible(true);
                }
            });
        }

        // hide the caption label when the mouse is released
        pieChartPane.setOnMouseReleased(e -> caption.setVisible(false));
    }

    private void filterPieChartByWeek() {
        LocalDate date = LocalDate.now();
        Map<String, Double> data_map = new HashMap<>();
        List<Terrain> listTerrains = ts.myProprieties(id_owner);
        // create an empty ObservableList
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
        // Prepare Data :
        for (Terrain terrain : listTerrains) {
            Double revenue = 0.0;
            Double app_profit = 0.0;
            List<Reservation> listReservations = rs.terrain_reservations_by_week(terrain.getId(), date);
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
        final Label caption = new Label("");

        // create a PieChart with the data from the ObservableList :
        PieChart pieChart = new PieChart(piechartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Staduims Revenues - Pie-Chart");
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        pieChart.setLegendSide(Side.LEFT);

        // create a StackPane to hold the pie chart and the caption label
        StackPane pieChartPane = new StackPane();
        pieChartPane.getChildren().addAll(pieChart, caption);
        borderPane.setCenter(pieChartPane);

        // add the event handler to each pie slice
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getX());
                    caption.setTranslateY(e.getY());
                    caption.setText(String.valueOf(data.getPieValue()) + " Dt");
                    caption.setTextFill(Paint.valueOf("#ffffff"));
                    caption.setStyle("-fx-background-color: black; -fx-padding: 5px;-fx-font-size: 18px;");
                    caption.setVisible(true);
                }
            });
        }

        // hide the caption label when the mouse is released
        pieChartPane.setOnMouseReleased(e -> caption.setVisible(false));
    }

    // filter methods for the bar chart : -------------------------
    private void filterBarChartByYear() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
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
            List<Reservation> listReservations = rs.terrain_reservations_by_year(terrain.getId(), year);
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

    private void filterBarChartByMonth() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
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
            List<Reservation> listReservations = rs.terrain_reservations_by_month(terrain.getId(), year, month);
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

    private void filterBarChartByWeek() {
        LocalDate date = LocalDate.now();
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
            List<Reservation> listReservations = rs.terrain_reservations_by_week(terrain.getId(), date);
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
}
