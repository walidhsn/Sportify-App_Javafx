/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.controllers;

//import sportify.edu.controller.EquipmentAdd;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sportify.edu.entities.Equipment;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sportify.edu.services.EquipmentCRUD;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.context.Context;

/**
 * *********
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Properties;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;
import java.io.*;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * FXML Controller class
 *
 * @author MOLKA
 */
public class EvenementController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prixLabel;
    @FXML
    private Label QuantiteLabel;
    @FXML
    private Button participerEventButton;
    @FXML
    private TextField ideventF;

    int idEvent;
    private EquipmentCRUD Ev = new EquipmentCRUD();
    @FXML
    private ImageView QrCode;
    @FXML
    private TextField iduserF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ideventF.setVisible(false);
        iduserF.setVisible(false);

    }

    private Equipment eve = new Equipment();

    public void setEvennement(Equipment e) throws IOException {
        // this.eve=e;
       nomLabel.setText("Nom d'équipement : " + e.getName());
    nomLabel.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 14; -fx-text-fill: #2c3e50;");
       prixLabel.setText("Prix : " +String.valueOf(e.getPrice()) + " DT");
    prixLabel.setStyle("-fx-font-size: 12;");
        QuantiteLabel.setText("Quantité Disponible: " +String.valueOf(e.getQuantity()));
        ideventF.setText(String.valueOf(e.getId()));
//        iduserF.setText(String.valueOf(1));
        String path = e.getImageName();
        File file = new File(path);
        Image img = new Image(file.toURI().toString());
        imageview.setImage(img);

        //lel qr code hedhi 
//                 String filename = Ev.GenerateQrEvent(e);
//            System.out.println("filename lenaaa " + filename);
//            String path1="C:\\xampp\\htdocs\\xchangex\\imgQr\\qrcode"+filename;
//             File file1=new File(path1);
//              Image img1 = new Image(file1.toURI().toString());
//              //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
//            QrCode.setImage(img1);  
    }

    public void setIdevent(int idevent) {
        this.idEvent = idevent;
    }

    public int getIdEvent() {
        return this.idEvent;
    }

    @FXML
    private void go_cod(MouseEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterAvis.fxml"));
            Parent root = loader.load();
            AjouterAvisController destController = loader.getController();
//          destController.prodredcu(ps.selectProduitById(ps.getProductIdByName(nomProduirLabel.getText())));

            destController.setIdevent(Integer.parseInt(ideventF.getText()));
            Scene sence = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(sence);
            stage.show();

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    @FXML
    private void participerEvent(MouseEvent event) {
//        
//        Equipment e;
//        e.setName(nomLabel.getText());
//        
//            LocalDate dateActuelle = LocalDate.now();
//        Date dateSQL = Date.valueOf(dateActuelle);
//        participation p=new participation(dateSQL,Integer.parseInt(iduserF.getText()),Integer.parseInt(ideventF.getText()));
//        
//        Ps.ajouterParticipation(p);
//
//        idPartField.setText(String.valueOf(27));
//        annulerButton.setVisible(true);
//        participerEventButton.setVisible(false);
//        
//        
//        
//        String filename = Ev.GenerateQrEvent(e);
//            System.out.println("filename lenaaa " + filename);
//            String path1="C:\\xampp\\htdocs\\xchangex\\imgQr\\qrcode"+filename;
//             File file1=new File(path1);
//              Image img1 = new Image(file1.toURI().toString());
//              //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
//            QrCode.setImage(img1);  
        //////////////      
        sendEmailNotif();

    }

    public static String saveImageToFile(ImageView image, String fileName) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image.getImage(), null);
            File file = new File(fileName);
            ImageIO.write(bufferedImage, "png", file);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ImageView QrGenerator(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int width = 300;
        int height = 300;
        String fileType = "png";

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            System.out.println("Success...");

        } catch (WriterException ex) {
            Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

        return qrView;

    }

    @FXML
    private static void sendEmailNotif() {

        //String msg = "veuillez confirmer votre achat du pass avec ce code " + code + " \n veuillez ne pas depasser 30 secondes";
        // Create all the needed properties
        Properties connectionProperties = new Properties();
        // SMTP host
        connectionProperties.put("mail.smtp.host", "smtp.gmail.com");//
        // Is authentication enabled
        connectionProperties.put("mail.smtp.auth", "true");//
        // Is TLS enabled
        connectionProperties.put("mail.smtp.starttls.enable", "true");//
        // SSL Port
        //connectionProperties.put("mail.smtp.socketFactory.port", "465");
        // SSL Socket Factory class
        //connectionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // SMTP port, the same as SSL port :)
        connectionProperties.put("mail.smtp.port", "587");

        System.out.print("Creating the session...");

        // Create the session
        Session session = Session.getDefaultInstance(connectionProperties,
                new javax.mail.Authenticator() {	// Define the authenticator
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("molka.ghammam@esprit.tn", "201JMT3867");
            }
        });

        System.out.println("done!");

        // Create and send the message
        try {
            // Create the message 
            Message message = new MimeMessage(session);
            // Set sender
            message.setFrom(new InternetAddress("molka.ghammam@esprit.tn"));
            // Set the recipients
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("molka.ghammam@esprit.tn"));
            // Set message subject
            message.setSubject("Detail Pass");

            // Lire le contenu HTML à partir du fichier
//            File file = new File(htmlFilePath);
//            FileReader reader = new FileReader(file);
//            char[] chars = new char[(int) file.length()];
//            reader.read(chars);
//            String htmlBody = new String(chars);
//            reader.close();
//            message.setContent(htmlBody, "text/html");
            /**
             * *******template*****************************
             */
            FileTemplateResolver resolver = new FileTemplateResolver();
            resolver.setTemplateMode("HTML");
            resolver.setPrefix("src/gui/");
            resolver.setSuffix(".html");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);

// Définir les variables à utiliser dans le template
            Map<String, Object> variables = new HashMap<>();
            variables.put("name", "Mme Mola");
            variables.put("message", "Vous Avez reserver un equipeent on vous remercis pour votre confiance amusez vous bien !");

            //String idE= EventIdLabelId.getText();
            Equipment eq;
            eq = new EquipmentCRUD().getEntity(1);

            String QrContent = "Vous Avez reserver un equipement";
            String fileNameQr = "Qrcode.png";

            System.out.println(QrContent);
            ImageView qrImage = QrGenerator(QrContent);//QrContent
            String qrCodePath = saveImageToFile(qrImage, fileNameQr);
            System.out.println(qrCodePath);
            variables.put("qrCodeWidth", "300");
            variables.put("qrCodeHeight", "300");
            variables.put("qrCode", "cid:qrCode");

            /**
             * *************
             */
            // Créer un MimeBodyPart pour l'image du QR code
            MimeBodyPart qrCodePart = new MimeBodyPart();
            qrCodePart.attachFile(qrCodePath);
            qrCodePart.setContentID("<qrCode>");
            qrCodePart.setDisposition(MimeBodyPart.INLINE);

// Créer un MimeMultipart pour contenir le texte du mail et l'image
//MimeMultipart multipart = new MimeMultipart();
//multipart.addBodyPart(textPart);
//multipart.addBodyPart(qrCodePart);
            /**
             * ************
             */
// Générer le contenu HTML à partir du template et des variables
            Context context = new Context(Locale.getDefault(), variables);
            String htmlBody = templateEngine.process("templateMail", context);

            System.out.println("2");

            // Créer un MimeMultipart pour contenir le contenu HTML et l'image
            MimeMultipart multipart = new MimeMultipart("related");

// Ajouter le contenu HTML au multipart
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            multipart.addBodyPart(htmlPart);

            // Ajouter l'image du QR code au multipart
            multipart.addBodyPart(qrCodePart);

// Ajouter le multipart au message
            message.setContent(multipart);

            System.out.println("3");

            /**
             * ***********template*************************
             */
            // Set message text
            // message.setText(msg);
            System.out.print("Sending message...");
            // Send the message
            Transport.send(message);

            System.out.println("done!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
