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
import entities.Academy;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
import javafx.geometry.Pos;
import services.AcademyCRUD;
import Ressource.Constants;

/**
 *
 * @author ramib
 */
public class AcademyList implements Initializable {
    
    private Label label;
    @FXML
    private TableView<Academy> tvAcademy;
    private ObservableList<Academy> academyList;
    @FXML
    private TableColumn<Academy, Integer> colId;
    @FXML
    private TableColumn<Academy, String> colName;
    @FXML
    private TableColumn<Academy, String> colCategory;
    @FXML
    private TableColumn<Academy, String> colCategory1;
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
    private List<Academy> academy_list_search;
    
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnChart;
    @FXML
    private Button btnCoach;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCategory1.setCellValueFactory(new PropertyValueFactory<>("ImageName"));
        colCategory1.setCellFactory(column -> new TableCell<Academy, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);

                if (empty || imageName == null) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    // Load the image from the file system or a URL
                    Image image = new Image("file:"+Constants.DEST_PATH + imageName);

                    // Set the image and adjust its size
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        academyList = FXCollections.observableArrayList(academyCRUD.display());
        tvAcademy.setItems(academyList);
        
        // Set the selection mode to MULTIPLE
        tvAcademy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Add listener for the selection of an academy in the table
        tvAcademy.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Academy selectedAcademy = tvAcademy.getSelectionModel().getSelectedItem();
                if (selectedAcademy != null) {
                    try {
                        // Load the AcademyDetails FXML file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.AcademyDetails));
                        Parent root = loader.load();

                        // Get a reference to the AcademyDetails controller
                        AcademyDetails controller = loader.getController();

                        // Call a method on the controller to set the academy to display its details
                        controller.setAcademy(selectedAcademy);
                        
                        // Get a reference to the current stage
                        Stage stage = (Stage) tvAcademy.getScene().getWindow();

                        // Create a new stage to display the AcademyDetails scene
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
                academy_list_search = academyList.stream()
                        .filter(academy -> String.valueOf(academy.getName()).toLowerCase().contains(newValue.toLowerCase())
                        || String.valueOf(academy.getCategory()).toLowerCase().contains(newValue.toLowerCase())
                        )
                        .collect(Collectors.toList());
                tvAcademy.setItems(FXCollections.observableArrayList(academy_list_search));
            });
    }
    
    @FXML
    private void handleSearchButtonClick(ActionEvent event) {
        String searchText = searchField.getText();
        ObservableList<Academy> filteredList = FXCollections.observableArrayList();
        for (Academy academy : academyList) {
            if (academy.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(academy);
            }
        }
        tvAcademy.setItems(filteredList);
    }
    
    @FXML
    private void handleAddButtonClick(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/AcademyAdd.fxml"));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.AcademyAdd));
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
        ObservableList<Academy> filteredList = FXCollections.observableArrayList();
        for (Academy academy : academyList) {
            if (academy.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(academy);
            }
        }
        tvAcademy.setItems(filteredList);
        searchField.clear();
    }
    
    @FXML
    private void handlePDFButtonClick(ActionEvent event) {
        try {
            byte[] pdfBytes = generatePDF();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("academyList.pdf");
            File file = fileChooser.showSaveDialog(btnPDF.getScene().getWindow());
            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfBytes);
                fos.close();
                // open the PDF file
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
        
        Paragraph title = new Paragraph("Academy List");
        title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 24, BaseColor.RED));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        // Add table headers
        PdfPTable table = new PdfPTable(3);
        table.addCell(new Phrase("ID", headerFont));
        table.addCell(new Phrase("Name", headerFont));
        table.addCell(new Phrase("Category", headerFont));

        // Define font styles for table data
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        // Add table data
        for (Academy academy : academyList) {
            table.addCell(new Phrase(String.valueOf(academy.getId()), dataFont));
            table.addCell(new Phrase(academy.getName(), dataFont));
            table.addCell(new Phrase(academy.getCategory(), dataFont));
        }

        // Add style to table
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
    
    public void generateCategoryChart(List<Academy> academyList) {
        if (academyList == null || academyList.isEmpty()) {
            throw new IllegalArgumentException("Academy list cannot be null or empty");
        }
        // Create a map to store the count of academies in each category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Academy academy : academyList) {
            String category = academy.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String category : categoryCount.keySet()) {
            dataset.addValue(categoryCount.get(category), "Academies", category);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Academies by Category",
                "Category",
                "Number of Academies",
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
        JFrame frame = new JFrame("Category Chart");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    
    @FXML
    private void handleChartButtonClick(ActionEvent event) {
        generateCategoryChart(academyList);
    }

    @FXML
    private void SwitchToCoach(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Constants.CoachList));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void Auth(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.Auth));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
