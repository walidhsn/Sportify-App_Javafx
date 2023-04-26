/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sportify.edu.entities.Reservation;
import sportify.edu.entities.Terrain;
import sportify.edu.services.ReservationService;
import sportify.edu.services.TerrainService;

/**
 * FXML Controller class
 *
 * @author WALID
 */
public class Reservation_view_client implements Initializable {

    @FXML
    private TableColumn<Reservation, String> date_col;
    @FXML
    private TableColumn<Reservation, String> startTime_col;
    @FXML
    private TableColumn<Reservation, String> endTime_col;
    @FXML
    private TableColumn<Reservation, Integer> nbPerson_col;
    @FXML
    private TableColumn<Reservation, Boolean> status_col;
    @FXML
    private TableColumn<Reservation, String> details_col;
    @FXML
    private TableColumn<Reservation, String> update_col;
    @FXML
    private TableColumn<Reservation, String> delete_col;
    @FXML
    private TableColumn<Reservation,Boolean> receipt_col;
    @FXML
    private TableColumn<Reservation, String> montant_total;
    @FXML
    private TextField search_text;

    private int client_id;

    @FXML
    private TableView<Reservation> table_reservation;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView backBtn_icon;
    private List<Reservation> reservation_list;
    private List<Reservation> reservation_list_search;
    private ImageView icon_delete, icon_update, icon_view, icon_card,icon_pdf;

    private ReservationService rs;

    public void setData(int client) {
        client_id = client;
        reservation_list = rs.myReservation(client_id);
        System.out.println(reservation_list);
        table_reservation.setItems(FXCollections.observableArrayList(reservation_list));
        date_col.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        nbPerson_col.setCellValueFactory(new PropertyValueFactory<>("nbPerson"));
        startTime_col.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime_col.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        receipt_col.setCellValueFactory(new PropertyValueFactory<>("resStatus"));
        montant_total.setCellValueFactory(cellData -> {
            Reservation reservation = cellData.getValue();
            TerrainService ts = new TerrainService();
            Terrain t = ts.diplay(reservation.getTerrain_id());
            double totalAmount = reservation.getNbPerson() * t.getRentPrice();
            String total_txt = String.format("%.2f", totalAmount) + " Dt";
            return new SimpleStringProperty(total_txt);
        });
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            reservation_list_search = reservation_list.stream()
                    .filter(reservation -> String.valueOf(reservation.getNbPerson()).contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getDateReservation()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.isResStatus()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getStartTime()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getEndTime()).toLowerCase().contains(newValue.toLowerCase())
                    || String.valueOf(reservation.getId()).toLowerCase().contains(newValue.toLowerCase())
                    )
                    .collect(Collectors.toList());
            table_reservation.setItems(FXCollections.observableArrayList(reservation_list_search));
        });
        //payment Button : 
        status_col.setCellFactory(column -> {
            return new TableCell<Reservation, Boolean>() {
                final Button paymentButton = new Button();

                // Set the button properties
                {
                    icon_card = new ImageView(new Image("/resources/card.png"));
                    icon_card.setFitWidth(30);
                    icon_card.setFitHeight(30);
                    paymentButton.setGraphic(icon_card);
                    paymentButton.setText("Pay");
                    paymentButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(Boolean resStatus, boolean empty) {
                    super.updateItem(resStatus, empty);
                    Label paidLabel = new Label("Paid");
                    // Set the label style
                    paidLabel.setStyle("-fx-text-fill: green;");
                    if (empty || resStatus == null) {
                        setGraphic(null);
                    } else if (resStatus) {
                        setGraphic(paidLabel);
                    } else {
                        setGraphic(paymentButton);
                        paymentButton.setOnAction((ActionEvent event) -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Payment.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                PaymentController controller = loader.getController();
                                Reservation data = getTableView().getItems().get(getIndex());
                                controller.setData(data);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_reservation.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
            };
        });
        // Export Receipt : 
        receipt_col.setCellFactory(column -> {
            return new TableCell<Reservation, Boolean>() {
                final Button exportButton = new Button();

                // Set the button properties
                {
                    icon_pdf = new ImageView(new Image("/resources/pdf.png"));
                    icon_pdf.setFitWidth(30);
                    icon_pdf.setFitHeight(30);
                    exportButton.setGraphic(icon_pdf);
                    exportButton.setText("Get Receipt");
                    exportButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(Boolean resStatus, boolean empty) {
                    super.updateItem(resStatus, empty);
                    Label unpaidLabel = new Label("UnPaid");
                    // Set the label style
                    unpaidLabel.setStyle("-fx-text-fill: red;");
                    if (empty || resStatus == null) {
                         setGraphic(null);
                    }else if(!resStatus){
                        setGraphic(unpaidLabel);
                    }else if (resStatus) {
                       setGraphic(exportButton);
                        exportButton.setOnAction((ActionEvent event) -> {
                            Reservation data = getTableView().getItems().get(getIndex());
                            generate_pdf(data);
                        });
                    } 
                }
            };
        });
        // Delete Button : 
        delete_col.setCellFactory(column -> {
            return new TableCell<Reservation, String>() {

                final Button deleteButton = new Button();

                // Set the button properties
                {
                    icon_delete = new ImageView(new Image("/resources/delete.png"));
                    icon_delete.setFitWidth(20);
                    icon_delete.setFitHeight(20);
                    deleteButton.setGraphic(icon_delete);
                    deleteButton.setText("Delete");
                    deleteButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Delete Confirmation");
                            alert.setContentText("Do you really want to delete this?");
                            Optional<ButtonType> btn = alert.showAndWait();
                            Reservation data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                rs.deleteEntity(data.getId());
                                refresh();
                            }
                        });
                    }
                }
            };
        });

        //Update Button : 
        update_col.setCellFactory(column -> {
            return new TableCell<Reservation, String>() {

                final Button updateButton = new Button();

                // Set the button properties
                {
                    icon_update = new ImageView(new Image("/resources/update.png"));
                    icon_update.setFitWidth(20);
                    icon_update.setFitHeight(20);
                    updateButton.setGraphic(icon_update);
                    updateButton.setText("Update");
                    updateButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                        updateButton.setOnAction((ActionEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");

                            alert.setHeaderText("Update Confirmation");
                            alert.setContentText("Do you really want to update this?");

                            Optional<ButtonType> btn = alert.showAndWait();
                            Reservation data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            System.out.println("selectedData: " + data.getId());
                            if (btn.get() == ButtonType.OK) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/UPDATE_Reservation.fxml"));
                                    Parent root = loader.load();
                                    //UPDATE The Controller with Data :
                                    UPDATE_ReservationController controller = loader.getController();
                                    controller.setData(data, client_id);
                                    //-----
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) table_reservation.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                }
            };
        });

        //View Button : 
        details_col.setCellFactory(column -> {
            return new TableCell<Reservation, String>() {

                final Button viewButton = new Button();

                // Set the button properties
                {
                    icon_view = new ImageView(new Image("/resources/eye.png"));
                    icon_view.setFitWidth(20);
                    icon_view.setFitHeight(20);
                    viewButton.setGraphic(icon_view);
                    viewButton.setText("View");
                    viewButton.setContentDisplay(ContentDisplay.LEFT);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                        viewButton.setOnAction((ActionEvent event) -> {
                            try {
                                Reservation data = getTableView().getItems().get(getIndex());
                                System.out.println(data);
                                System.out.println("selectedData: " + data.getId());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/DETAILS_Reservation.fxml"));
                                Parent root = loader.load();
                                //UPDATE The Controller with Data :
                                DETAILS_ReservationController controller = loader.getController();
                                int id_client = client_id;
                                controller.setData(data, id_client);
                                //-----
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) table_reservation.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                }
            };
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservation_list = new ArrayList<>();
        reservation_list_search = new ArrayList<>();
        rs = new ReservationService();

    }

    @FXML
    private void returnListTerrainClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Terrain/Terrain_view_client.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_reservation.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void refresh() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/reservation/Reservation_view_client.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            Reservation_view_client controller = loader.getController();
            controller.setData(this.client_id);
            Scene scene = new Scene(root);
            Stage stage = (Stage) table_reservation.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void export_pdf(String html, String fileName) throws IOException, DocumentException {
        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);

        // Create a document
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        // Export to PDF
        try (FileOutputStream os = new FileOutputStream(file)) {
            renderer.createPDF(os);
        }
    }
    private void generate_pdf(Reservation reservation){
        TerrainService ts = new TerrainService();
        Terrain terrain = ts.diplay(reservation.getTerrain_id());
        float total = (reservation.getNbPerson() * terrain.getRentPrice());
        DateTimeFormatter formatter_time = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter_date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date_res = formatter_date.format(reservation.getDateReservation());
        String start_time = formatter_time.format(reservation.getStartTime());
        String end_time = formatter_time.format(reservation.getEndTime());
        String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <title>Receipt</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            font-size: 17px;\n"
                + "        }\n"
                + "        .header {\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "        .header h1 {\n"
                + "            margin: 0;\n"
                + "            font-size: 24px;\n"
                + "        }\n"
                + "        .info {\n"
                + "            margin-top: 20px;\n"
                + "            margin-bottom: 20px;\n"
                + "            border: 1px solid #ee1e46;\n"
                + "            padding: 10px;\n"
                + "        }\n"
                + "        .items {\n"
                + "            margin-top: 20px;\n"
                + "            margin-bottom: 20px;\n"
                + "            border-collapse: collapse;\n"
                + "            width: 100%;\n"
                + "            font-size: 16px;\n"
                + "        }\n"
                + "        .items th, .items td {\n"
                + "            border: 1px solid #ccc;\n"
                + "            padding: 10px;\n"
                + "            text-align: left;\n"
                + "        }\n"
                + "        .items th {\n"
                + "            background-color: #eee;\n"
                + "            font-weight: bold;\n"
                + "        }\n"
                + "        .items td {\n"
                + "            vertical-align: middle;\n"
                + "        }\n"
                + "        .items tr:hover {\n"
                + "            background-color: #f5f5f5;\n"
                + "        }\n"
                + "        .total {\n"
                + "            text-align: right;\n"
                + "            font-weight: bold;\n"
                + "            font-size: 18px;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<header>\n"
                + " <img src=\"https://i.im.ge/2023/04/23/LxdZBT.icon.png\" alt=\"Company Logo\" width=\"240\" height=\"180\" />\n"
                + "  <h1>Receipt :</h1>\n"
                + "</header>\n"
                + "<body>\n"
                + "    \n"
                + "    <div class=\"info\">\n"
                + "        <p><strong>Client Information:</strong></p>\n"
                + "        <p><strong>Firstname:</strong> " + "client-name-To-Do" + "</p>\n"
                + "        <p><strong>Lastname:</strong> " + "client-lastname-To-Do" + "</p>\n"
                + "        <p><strong>Email:</strong> " + "client-email-To-Do" + "</p>\n"
                + "        <p><strong>Telephone:</strong> " + "client-telephone-To-Do" + "</p>\n"
                + "    </div>\n"
                + "    <table class=\"items\">\n"
                + "        <thead>\n"
                + "            <tr>\n"
                + "                <th>Date</th>\n"
                + "                <th>Start Time</th>\n"
                + "                <th>End Time</th>\n"
                + "                <th>Number of people</th>\n"
                + "                <th>Subtotal</th>\n"
                + "            </tr>\n"
                + "        </thead>\n"
                + "        <tbody>\n"
                + "            \n"
                + "            <tr>\n"
                + "                <td>" + date_res + "</td>\n"
                + "                <td>" + start_time + "</td>\n"
                + "                <td>" + end_time + "</td>\n"
                + "                <td>" + String.valueOf(reservation.getNbPerson()) + "</td>\n"
                + "                <td>" + String.valueOf(total) + ".DT</td>\n"
                + "            </tr>\n"
                + "            <tr>\n"
                + "                <td colspan=\"3\" align=\"right\">Total:</td>\n"
                + "                <td class=\"total\">" + String.valueOf(total) + ".DT</td>\n"
                + "            </tr>\n"
                + "        </tbody>\n"
                + "    </table>\n"
                + "    <br></br><br></br><br></br>\n"
                + "     <div>\n"
                + "                            <p>\n"
                + "                    © All rights reserved | This template is made with ♥ by Creative Crew \n"
                + "                            </p>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
        String txt= "Reservation-"+String.valueOf(reservation.getId())+".pdf";
        String fileName = txt;
        try {
            export_pdf(html, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
