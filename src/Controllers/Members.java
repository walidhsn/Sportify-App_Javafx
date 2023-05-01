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
import entities.Apply;
import entities.Coach;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.geometry.Pos;
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
import services.AcademyCRUD;
import services.ApplyCRUD;
//import javax.swing.text.Document;
import services.CoachCRUD;

/**
 *
 * @author ramib
 */
public class Members implements Initializable {
    
    private Label label;
    @FXML
    private TableView<Apply> tvApply;
    private ObservableList<Apply> applyList;
    @FXML
    private TableColumn<Apply, Integer> colId;
    @FXML
    private TableColumn<Apply, String> colName;
    @FXML
    private TableColumn<Apply, Integer> colCategory;
    @FXML
    private TableColumn<Apply, String> colPhone;
    @FXML
    private TextField searchField;
    @FXML
    private Button btnPDF;
    @FXML
    private TextField academyField;
    
    private List<Apply> Apply_list_search;    
    private ApplyCRUD applyCRUD = new ApplyCRUD();
    private int selectedAcademyId;
    private Academy selectedAcademy;
    private AcademyCRUD academyCRUD;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        academyCRUD = new AcademyCRUD();
        membersTable(); 
    }
    
    public void setSelectedAcademyId(int academyId) {
        this.selectedAcademyId = academyId;
        try {
            this.selectedAcademy = academyCRUD.getEntity(academyId);
            academyField.setText(selectedAcademy.getName());
            String academyName = academyField.getText();
            applyList = FXCollections.observableArrayList(applyCRUD.findApplyByAcademyName(academyName));;
            tvApply.setItems(applyList);
        } catch (SQLException ex) {
            // handle the exception here, e.g. log it or display an error message
            ex.printStackTrace();
        }
    }

    private void membersTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("age"));
//        colAcademy.setCellValueFactory(new PropertyValueFactory<>("academy_name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("imageName"));
        colPhone.setCellFactory(column -> new TableCell<Apply, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);

                if (empty || imageName == null) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    // Load the image from the file system or a URL
                    Image image = new Image("file:C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/" + imageName);

                    // Set the image and adjust its size
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });
//        String academyName = academyField.getText();
//        System.out.println("Id :" + academyName);
//        applyList = FXCollections.observableArrayList(applyCRUD.findApplyByAcademyName(academyName));;
//        tvApply.setItems(applyList);

        // Set the selection mode to MULTIPLE
        tvApply.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                Apply_list_search = applyList.stream()
                        .filter(academy -> String.valueOf(academy.getName()).toLowerCase().contains(newValue.toLowerCase())
                        || String.valueOf(academy.getAge()).contains(newValue.toLowerCase())
                        || String.valueOf(academy.getAcademy_name()).toLowerCase().contains(newValue.toLowerCase())
                        )
                        .collect(Collectors.toList());
                tvApply.setItems(FXCollections.observableArrayList(Apply_list_search));
            });
    }
    
    
    @FXML
    private void handleSearchButtonClick(ActionEvent event) {
        String searchText = searchField.getText();
        ObservableList<Apply> filteredList = FXCollections.observableArrayList();
        for (Apply apply : applyList) {
            if (apply.getName().toLowerCase().contains(searchText.toLowerCase()) || apply.getAcademy_name().toLowerCase().contains(searchText.toLowerCase()) ) {
                filteredList.add(apply);
            }
        }
        tvApply.setItems(filteredList);
    }
    
    @FXML
    private void handleClearButtonClick(MouseEvent event) throws IOException {
        String searchText = "";
        ObservableList<Apply> filteredList = FXCollections.observableArrayList();
        for (Apply apply : applyList) {
            if (apply.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(apply);
            }
        }
        tvApply.setItems(filteredList);
        searchField.clear();
    }
    
    @FXML
    private void handlePDFButtonClick(ActionEvent event) {
        try {
            byte[] pdfBytes = generatePDF();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("membersList.pdf");
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
        
        Paragraph title = new Paragraph("Members List");
        title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 24, BaseColor.RED));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        // Add table headers
        PdfPTable table = new PdfPTable(4);
        table.addCell(new Phrase("ID", headerFont));
        table.addCell(new Phrase("Name", headerFont));
        table.addCell(new Phrase("Age", headerFont));
        table.addCell(new Phrase("Academy", headerFont));

        // Define font styles for table data
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        // Add table data
        for (Apply apply : applyList) {
            table.addCell(new Phrase(String.valueOf(apply.getId()), dataFont));
            table.addCell(new Phrase(apply.getName(), dataFont));
            table.addCell(new Phrase(String.valueOf(apply.getAge()), dataFont));
            table.addCell(new Phrase(apply.getAcademy_name(), dataFont));
        }

        // Add style to table
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        document.add(table);
        document.close();

        return baos.toByteArray();
    }
    
    public void generateCategoryChart(List<Apply> applyList) {
        if (applyList == null || applyList.isEmpty()) {
            throw new IllegalArgumentException("Apply list cannot be null or empty");
        }
        // Create a map to store the count of academies in each category
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Apply apply : applyList) {
            String category = apply.getAcademy_name();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String category : categoryCount.keySet()) {
            dataset.addValue(categoryCount.get(category), "Academies", category);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Applyes by Academy",
                "Academy",
                "Number of Applyes",
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
        JFrame frame = new JFrame("Applyes Chart");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

    
    @FXML
    private void handleChartButtonClick(ActionEvent event) {
        generateCategoryChart(applyList);
    }

    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/AcademyList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



    
}
