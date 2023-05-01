/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Coach;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
//import javax.swing.text.Document;
import services.CoachCRUD;

/**
 *
 * @author ramib
 */
public class CoachList implements Initializable {
    
    private Label label;
    @FXML
    private TableView<Coach> tvCoach;
    private ObservableList<Coach> coachList;
    @FXML
    private TableColumn<Coach, Integer> colId;
    @FXML
    private TableColumn<Coach, String> colName;
    @FXML
    private TableColumn<Coach, String> colCategory;
    @FXML
    private TableColumn<Coach, String> colPhone;
    @FXML
    private TableColumn<Coach, String> colAcademy;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView imgSearch;
    @FXML
    private ImageView btnClear;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnPDF;
    private List<Coach> Coach_list_search;

    
    private CoachCRUD coachCRUD = new CoachCRUD();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChart;
    @FXML
    private Button btnAcademy;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAcademy.setCellValueFactory(new PropertyValueFactory<>("AcademyName"));
        coachList = FXCollections.observableArrayList(coachCRUD.display());
//        System.out.println(coachList);
        tvCoach.setItems(coachList);

        // Set the selection mode to MULTIPLE
        tvCoach.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Add listener for the selection of an coach in the table
        tvCoach.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Coach selectedCoach = tvCoach.getSelectionModel().getSelectedItem();
                if (selectedCoach != null) {
                    try {
                        // Load the CoachDetails FXML file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/CoachDetails.fxml"));
                        Parent root = loader.load();

                        // Get a reference to the CoachDetails controller
                        CoachDetails controller = loader.getController();

                        // Call a method on the controller to set the coach to display its details
                        controller.setCoach(selectedCoach);
                        
                        // Get a reference to the current stage
                        Stage stage = (Stage) tvCoach.getScene().getWindow();

                        // Create a new stage to display the CoachDetails scene
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                Coach_list_search = coachList.stream()
                        .filter(academy -> String.valueOf(academy.getName()).toLowerCase().contains(newValue.toLowerCase())
                        || String.valueOf(academy.getEmail()).toLowerCase().contains(newValue.toLowerCase())
                        || String.valueOf(academy.getPhone()).contains(newValue.toLowerCase())
                        || String.valueOf(academy.getAcademyName()).toLowerCase().contains(newValue.toLowerCase())
                        )
                        .collect(Collectors.toList());
                tvCoach.setItems(FXCollections.observableArrayList(Coach_list_search));
            });
    }
    
    @FXML
    private void handleSearchButtonClick(ActionEvent event) {
        String searchText = searchField.getText();
        ObservableList<Coach> filteredList = FXCollections.observableArrayList();
        for (Coach coach : coachList) {
            if (coach.getName().toLowerCase().contains(searchText.toLowerCase()) || coach.getAcademyName().toLowerCase().contains(searchText.toLowerCase()) ) {
                filteredList.add(coach);
            }
        }
        tvCoach.setItems(filteredList);
    }
//    @FXML
//    private void handleSearchButtonClick(ActionEvent event) {
//        String searchText = searchField.getText();
//        ObservableList<Coach> filteredList = FXCollections.observableArrayList();
//        for (Coach coach : coachList) {
//            if (coach.getName().toLowerCase().contains(searchText.toLowerCase())) {
//                filteredList.add(coach);
//            }
//        }
//        tvCoach.setItems(filteredList);
//    }
    
    @FXML
    private void handleAddButtonClick(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/CoachAdd.fxml"));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/CoachAdd.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void handleClearButtonClick(MouseEvent event) throws IOException {
        
        String searchText = "";
        ObservableList<Coach> filteredList = FXCollections.observableArrayList();
        for (Coach coach : coachList) {
            if (coach.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(coach);
            }
        }
        tvCoach.setItems(filteredList);
        searchField.clear();
    }
    
    @FXML
    private void handlePDFButtonClick(ActionEvent event) {
        try {
            byte[] pdfBytes = generatePDF();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("coachList.pdf");
            File file = fileChooser.showSaveDialog(btnPDF.getScene().getWindow());
            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfBytes);
                fos.close();
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
    private byte[] generatePDF() throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Define font styles
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        
        Paragraph title = new Paragraph("Coach List");
        title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 24, BaseColor.RED));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        // Add table headers
        PdfPTable table = new PdfPTable(5);
        table.addCell(new Phrase("ID", headerFont));
        table.addCell(new Phrase("Name", headerFont));
        table.addCell(new Phrase("Email", headerFont));
        table.addCell(new Phrase("Phone", headerFont));
        table.addCell(new Phrase("Academy", headerFont));

        // Define font styles for table data
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        // Add table data
        for (Coach coach : coachList) {
            table.addCell(new Phrase(String.valueOf(coach.getId()), dataFont));
            table.addCell(new Phrase(coach.getName(), dataFont));
            table.addCell(new Phrase(coach.getEmail(), dataFont));
            table.addCell(new Phrase(coach.getPhone(), dataFont));
            table.addCell(new Phrase(coach.getAcademyName(), dataFont));
        }

        // Add style to table
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
    
    public void generateCategoryChart(List<Coach> coachList) {
        if (coachList == null || coachList.isEmpty()) {
            throw new IllegalArgumentException("Coach list cannot be null or empty");
        }
        // Create a map to store the count of academies in each category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Coach coach : coachList) {
            String category = coach.getAcademyName();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String category : categoryCount.keySet()) {
            dataset.addValue(categoryCount.get(category), "Academies", category);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Coaches by Academy",
                "Academy",
                "Number of Coaches",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1156, 800));
        JFrame frame = new JFrame("Coaches Chart");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    
    @FXML
    private void handleChartButtonClick(ActionEvent event) {
        generateCategoryChart(coachList);
    }

    @FXML
    private void SwitchToAcademy(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    
}
