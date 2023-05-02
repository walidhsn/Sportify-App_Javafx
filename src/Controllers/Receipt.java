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
import services.ApplyCRUD;
import Ressource.Constants;

/**
 *
 * @author ramib
 */
public class Receipt implements Initializable {
    
    private String selectedApplyName;
    private Apply selectedApply;
    private ApplyCRUD applyCRUD;
    private int appId;
    private String appName;
    private String appAcademyName;
    private int appAge;
    @FXML
    private Button btnPdf;
    @FXML
    private ImageView backIcon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyCRUD = new ApplyCRUD();
    }
    
    public void setSelectedApplyName(String applyName) throws SQLException {
        this.selectedApplyName = applyName;
        this.selectedApply = applyCRUD.getEntity(applyName);
        appId = selectedApply.getId();
        appName = selectedApply.getName();
        appAge = selectedApply.getAge();
        appAcademyName = selectedApply.getAcademy_name();
        System.out.println(appId+","+appName+","+appAge+","+appAcademyName);
    }
    
    @FXML
    private void handlePDFButtonClick(ActionEvent event) {
        try {
            byte[] pdfBytes = generatePDF();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("receipt.pdf");
            File file = fileChooser.showSaveDialog(btnPdf.getScene().getWindow());
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
        
        Paragraph title = new Paragraph("Application receipt");
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
        
        table.addCell(new Phrase(String.valueOf(appId), dataFont));
        table.addCell(new Phrase(appName, dataFont));
        table.addCell(new Phrase(String.valueOf(appAge), dataFont));
        table.addCell(new Phrase(appAcademyName, dataFont));
        

        // Add style to table
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        document.add(table);
        document.close();

        return baos.toByteArray();
    }


    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(Constants.AcademyList_1));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
