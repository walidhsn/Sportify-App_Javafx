/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import sportify.edu.entities.Equipment;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sportify.edu.services.EquipmentCRUD;



/**
 * FXML Controller class
 *
 * @author MOLKA
 */
public class EquipmentList implements Initializable {

@FXML
private TableView<Equipment> equipmentTable;
private ObservableList<Equipment> EqList;
@FXML
private TableColumn<Equipment, String> nameColumn;
@FXML
private TableColumn<Equipment, String> categoryColumn;
@FXML
private TableColumn<Equipment, Integer> quantityColumn;
@FXML
private TableColumn<Equipment, Double> priceColumn;

    @FXML
    private TableColumn<Equipment,String> SupplierColumn;

@FXML
private Button btnAdd;

  private EquipmentCRUD Ev = new EquipmentCRUD();


private EquipmentCRUD equipmentCRUD = new EquipmentCRUD();
    @FXML
    private TextField bar;
    @FXML
    private Button reherche;
    @FXML
    private Button Excel;
    @FXML
    private Button pdf;
 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        SupplierColumn.setCellValueFactory(new PropertyValueFactory<>("id_supp"));
        priceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment, Double>, ObservableValue<Double>>() {
    @Override
    public ObservableValue<Double> call(TableColumn.CellDataFeatures<Equipment, Double> param) {
        Equipment equipment = param.getValue();
        double exchangeRate = 0.36; // 1 Tunisian dinar = 0.36 US dollars
        double priceInDollars = equipment.getPriceInDollars(exchangeRate);
        String formattedPrice = String.format("%.2f", priceInDollars);
        return new SimpleDoubleProperty(priceInDollars).asObject();
    }
});

priceColumn.setCellFactory(tc -> new TableCell<Equipment, Double>() {
    @Override
    protected void updateItem(Double price, boolean empty) {
        super.updateItem(price, empty);
        if (empty) {
            setText(null);
        } else {
            setText(String.format("%.2f", price) + " $" );
        }
    }
});


        EqList = FXCollections.observableArrayList(equipmentCRUD.display());
        equipmentTable.setItems(EqList);
        equipmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    // Load the AcademyDetails FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/equipment/equipment/EquipmentDetails.fxml"));
                    Parent root = loader.load();

                    // Get a reference to the AcademyDetails controller
                    EquipmentDetails controller = loader.getController();

                    // Call a method on the controller to set the academy to display its details
                    controller.setEquipment(newSelection);

                    // Create a new stage to display the AcademyDetails scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    } 
    
    @FXML
    private void handleAddButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/equipment/EquipmentAdd.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void search(ActionEvent event) {
        String filter =bar.getText();
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        EqList = FXCollections.observableArrayList(equipmentCRUD.displayrecherche(filter));
        equipmentTable.setItems(EqList);
        equipmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    // Load the AcademyDetails FXML file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/equipment/EquipmentDetails.fxml"));
                    Parent root = loader.load();

                    // Get a reference to the AcademyDetails controller
                    EquipmentDetails controller = loader.getController();

                    // Call a method on the controller to set the academy to display its details
                    controller.setEquipment(newSelection);

                    // Create a new stage to display the AcademyDetails scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void AjouterExcel(ActionEvent event) {
                  
try{
String filename="C:\\xampp\\htdocs\\fichierExcelJava\\dataEvent.xls" ;
    HSSFWorkbook hwb=new HSSFWorkbook();
    HSSFSheet sheet =  hwb.createSheet("new sheet");
    HSSFRow rowhead=   sheet.createRow((short)0);
rowhead.createCell((short) 0).setCellValue("nom evenement");
rowhead.createCell((short) 1).setCellValue("type d'evenement");
rowhead.createCell((short) 2).setCellValue("description ");
List<Equipment> evenements = Ev.display();
  for (int i = 0; i < evenements.size(); i++) {          
HSSFRow row=   sheet.createRow((short)i);
row.createCell((short) 0).setCellValue(evenements.get(i).getName());
row.createCell((short) 1).setCellValue(evenements.get(i).getPrice());
row.createCell((short) 2).setCellValue(evenements.get(i).getQuantity());
//row.createCell((short) 3).setCellValue((evenements.get(i).getDate()));
i++;
            }
int i=1;
    FileOutputStream fileOut =  new FileOutputStream(filename);
hwb.write(fileOut);
fileOut.close();
System.out.println("Your excel file has been generated!");
 File file = new File(filename);
        if (file.exists()){ 
        if(Desktop.isDesktopSupported()){
            Desktop.getDesktop().open(file);
        }}       
} catch ( Exception ex ) {
    System.out.println(ex);
}
        
    }

//    @FXML
//    private void Ajouterpdf(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
//        
//        
//                  long millis = System.currentTimeMillis();
//        java.sql.Date DateRapport = new java.sql.Date(millis);
//
//        String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);//yyyyMMddHHmmss
//        System.out.println("Date d'aujourdhui : " + DateLyoum);
//
//        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//
////        try {
//            PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));//yyyy-MM-dd
//            document.open();
//            Paragraph ph1 = new Paragraph("Voici un rapport détaillé de la miste des equipment disponible dans votre  terrain. date  d'aujourdhui :" + DateRapport );
//            Paragraph ph2 = new Paragraph(".");
//            PdfPTable table = new PdfPTable(3);
//            //On créer l'objet cellule.
//            PdfPCell cell;
//            //contenu du tableau.
//            table.addCell("nom_event");
//            table.addCell("type_event");
//            table.addCell("description_event");
//            Equipment r = new Equipment();
//            Ev.display().forEach(e
//                    -> {
//                table.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(String.valueOf(e.getName()));
//                table.addCell(String.valueOf(e.getCategory()));
//                table.addCell(String.valueOf(e.getPrice()));    
//            }
//            );
//            
////            Image img = Image.getInstance("C:\\Users\\msi\\Desktop\\projet yocef\\reclamation\\src\\com\\img\\Exchange.png12.png");
////       img.scaleAbsoluteHeight(60);
////       img.scaleAbsoluteWidth(100);
////       img.setAlignment(Image.ALIGN_RIGHT);
////       document.add(img);
//            document.add(ph1);
//            document.add(ph2);
//            document.add(table);
////             } catch (Exception e) {
////            System.out.println(e);
////        }
//        document.close();
//
//        ///Open FilePdf
//        File file = new File(DateLyoum + ".pdf");
//        Desktop desktop = Desktop.getDesktop();
//        if (file.exists()) //checks file exists or not  
//        {
//            desktop.open(file); //opens the specified file   
//        }
//    }
//    @FXML
//private void Ajouterpdf(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
//        
//    long millis = System.currentTimeMillis();
//    java.sql.Date DateRapport = new java.sql.Date(millis);
//
//    String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);
//    System.out.println("Date d'aujourd'hui : " + DateLyoum);
//
//    com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//    PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));
//    document.open();
//    
//    // Add a title to the document
//    com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
//    Paragraph title = new Paragraph("Rapport détaillé des équipements disponibles sur votre terrain", titleFont);
//    title.setAlignment(Element.ALIGN_CENTER);
//    document.add(title);
//    
//    // Add a subtitle with the current date
//    com.itextpdf.text.Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
//    Paragraph subtitle = new Paragraph("Date d'aujourd'hui : " + DateRapport, subtitleFont);
//    subtitle.setAlignment(Element.ALIGN_CENTER);
//    document.add(subtitle);
//    
//    // Add a space between the title and the table
//    document.add(new Paragraph(" "));
//    
//    // Create a table with headers and content
//    PdfPTable table = new PdfPTable(3);
//    com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
//    table.addCell(new Phrase("Nom de l'équipement", headerFont));
//    table.addCell(new Phrase("Type d'équipement", headerFont));
//    table.addCell(new Phrase("Prix de l'équipement", headerFont));
//    
//    // Add data from the database to the table
//    Equipment r = new Equipment();
//    Ev.display().forEach(e -> {
//        table.addCell(String.valueOf(e.getName()));
//        table.addCell(String.valueOf(e.getCategory()));
//        table.addCell(String.valueOf(e.getPrice()));    
//    });
//    
//    // Add the table to the document
//    table.setHorizontalAlignment(Element.ALIGN_CENTER);
//    document.add(table);
//
//    document.close();
//
//    // Open the generated PDF file
//    File file = new File(DateLyoum + ".pdf");
//    Desktop desktop = Desktop.getDesktop();
//    if (file.exists()) {
//        desktop.open(file);
//    }
    @FXML
private void Ajouterpdf(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
        
    long millis = System.currentTimeMillis();
    java.sql.Date DateRapport = new java.sql.Date(millis);

    String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);
    System.out.println("Date d'aujourd'hui : " + DateLyoum);

    com.itextpdf.text.Document document = new com.itextpdf.text.Document();
    PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));
    document.open();
    
    // Define styles for the title and the subtitle
    com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
    com.itextpdf.text.Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
    
    // Add a title to the document
    Paragraph title = new Paragraph("Rapport détaillé des équipements disponibles sur votre terrain", titleFont);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);
    
    // Add a subtitle with the current date
    Paragraph subtitle = new Paragraph("Date d'aujourd'hui : " + DateRapport, subtitleFont);
    subtitle.setAlignment(Element.ALIGN_CENTER);
    document.add(subtitle);
    
    // Add a space between the title and the table
    document.add(new Paragraph(" "));
    
    // Create a table with headers and content
    PdfPTable table = new PdfPTable(3);
    
    // Set the border color and width of the table
    table.getDefaultCell().setBorderColor(BaseColor.GRAY);
    table.getDefaultCell().setBorderWidth(1f);
    
    // Set the background color of the headers
    table.setHeaderRows(1);

    
    // Define styles for the headers and the content
    com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
    com.itextpdf.text.Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
    
    // Add headers to the table
    table.addCell(new Phrase("Nom de l'équipement", headerFont));
    table.addCell(new Phrase("Type d'équipement", headerFont));
    table.addCell(new Phrase("Prix de l'équipement", headerFont));
    
    // Add data from the database to the table
    Equipment r = new Equipment();
    Ev.display().forEach(e -> {
        table.addCell(new Phrase(String.valueOf(e.getName()), contentFont));
        table.addCell(new Phrase(String.valueOf(e.getCategory()), contentFont));
        table.addCell(new Phrase(String.valueOf(e.getPrice()), contentFont));    
    });
    
    // Add the table to the document
    table.setHorizontalAlignment(Element.ALIGN_CENTER);
    document.add(table);

    document.close();

    // Open the generated PDF file
    File file = new File(DateLyoum + ".pdf");
    Desktop desktop = Desktop.getDesktop();
    if (file.exists()) {
        desktop.open(file);
    }
}

}


