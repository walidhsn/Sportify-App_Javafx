/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Commande;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author louay
 */
public class CommandeCrud {
    public void ajouter_commande(Commande c) {
        try {
            String requete = "INSERT INTO commande (card_id, user_id, firstname, lastname, email, tel, adresse, city, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, c.getCard_id());
            pst.setInt(2, c.getUser_id());
            pst.setString(3, c.getFirstname());
            pst.setString(4, c.getLastname());
            pst.setString(5, c.getEmail());
            pst.setString(6, c.getTel());
            pst.setString(7, c.getAdresse());
            pst.setString(8, c.getCity());
            pst.setFloat(9, c.getTotal());
            pst.executeUpdate();
            System.out.println("Commande ajoutÃ©e !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modifier_commande(Commande c) {
        try {
            String requete = "UPDATE commande SET card_id = ?, user_id = ?, firstname = ?, lastname = ?, email = ?, tel = ?, adresse = ?, city = ?, total = ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, c.getCard_id());
            pst.setInt(2, c.getUser_id());
            pst.setString(3, c.getFirstname());
            pst.setString(4, c.getLastname());
            pst.setString(5, c.getEmail());
            pst.setString(6, c.getTel());
            pst.setString(7, c.getAdresse());
            pst.setString(8, c.getCity());
            pst.setFloat(9, c.getTotal());
            pst.setInt(10, c.getId());
            pst.executeUpdate();
            System.out.println("Commande modifiÃ©e !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimer_commande(int id) {
        try {
            String requete = "DELETE FROM commande WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Commande supprimÃ©e !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Commande> trouver_toutes_les_commandes() {
        List<Commande> commandes = new ArrayList<>();
        try {
            String requete = "SELECT * FROM commande";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Commande c = new Commande(rs.getInt("id"), rs.getInt("card_id"), rs.getInt("user_id"),
                                          rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                                          rs.getString("tel"), rs.getString("adresse"), rs.getString("city"),
                                          rs.getFloat("total"));
                commandes.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commandes;
    }
    
    public Commande trouver_commande_par_id(int id) {
    Commande c = null;
    try {
        String requete = "SELECT * FROM commande WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            c = new Commande(rs.getInt("id"), rs.getInt("card_id"), rs.getInt("user_id"),
                              rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                              rs.getString("tel"), rs.getString("adresse"), rs.getString("city"),
                              rs.getFloat("total"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return c;
}
    public Commande trouver_commande_par_card_id(int id_card) {
    Commande c = null;
    try {
        String requete = "SELECT * FROM commande WHERE card_id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id_card);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            c = new Commande(rs.getInt("id"), rs.getInt("card_id"), rs.getInt("user_id"),
                              rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"),
                              rs.getString("tel"), rs.getString("adresse"), rs.getString("city"),
                              rs.getFloat("total"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return c;
}
    public Document createPDF(Commande s) throws DocumentException, FileNotFoundException, BadElementException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(s.getFirstname() + ".pdf"));

        // Set custom page size and margins
        Rectangle pageSize = new Rectangle(PageSize.A4.rotate());
        document.setPageSize(pageSize);
        document.setMargins(40, 40, 40, 40);

        // Define fonts
        Font titleFont = FontFactory.getFont("fonts/Roboto-Bold.ttf", BaseFont.IDENTITY_H, 24, Font.UNDERLINE);
        Font tableHeaderFont = FontFactory.getFont("fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, 12, Font.NORMAL);
        Font tableContentFont = FontFactory.getFont("fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, 12, Font.NORMAL);

        // Header and Footer
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);

        document.open();

        // Title
        Paragraph title = new Paragraph("Commande Details", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Image
        Image logo = Image.getInstance("file:C:/Users/louay/OneDrive/Documents/NetBeansProjects/louayPi/src/resources/icon_1.png");
        logo.setAbsolutePosition(document.left(),document.bottomMargin());
        logo.scalePercent(10);
        document.add(logo);

        // Table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setSpacingAfter(30);
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.addCell(createCell("Nom:", tableHeaderFont));
        table.addCell(createCell(s.getFirstname(), tableContentFont));
        table.addCell(createCell("Prenom:", tableHeaderFont));
        table.addCell(createCell(s.getLastname(), tableContentFont));
        table.addCell(createCell("Ville:", tableHeaderFont));
        table.addCell(createCell(s.getCity(), tableContentFont));
        table.addCell(createCell("Adresse:", tableHeaderFont));
        table.addCell(createCell(s.getAdresse(), tableContentFont));
        table.addCell(createCell("Tel:", tableHeaderFont));
        table.addCell(createCell(s.getTel(), tableContentFont));
        table.addCell(createCell("Total Price:", tableHeaderFont));
        table.addCell(createCell(Float.toString(s.getTotal()), tableContentFont));
        
        document.add(table);
        document.close();

        return document;
    }
    public Document createPDFP(Commande s) throws DocumentException, FileNotFoundException, BadElementException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(s.getFirstname() + ".pdf"));

        // Set custom page size and margins
        Rectangle pageSize = new Rectangle(PageSize.A4.rotate());
        document.setPageSize(pageSize);
        document.setMargins(40, 40, 40, 40);

        // Define fonts
        Font titleFont = FontFactory.getFont("fonts/Roboto-Bold.ttf", BaseFont.IDENTITY_H, 24, Font.UNDERLINE);
        Font tableHeaderFont = FontFactory.getFont("fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, 12, Font.NORMAL);
        Font tableContentFont = FontFactory.getFont("fonts/Roboto-Regular.ttf", BaseFont.IDENTITY_H, 12, Font.NORMAL);

        // Header and Footer
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);

        document.open();

        // Title
        Paragraph title = new Paragraph("Commande Details", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        Paragraph text1 = new Paragraph("This commande is Payed", titleFont);
        text1.setAlignment(Element.ALIGN_RIGHT);
        text1.setSpacingAfter(20);
        document.add(text1);
        // Image
        Image logo = Image.getInstance("file:C:/Users/louay/OneDrive/Documents/NetBeansProjects/louayPi/src/resources/icon_1.png");
        logo.setAbsolutePosition(document.left(),document.bottomMargin());
        logo.scalePercent(10);
        document.add(logo);

        // Table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setSpacingAfter(30);
        table.getDefaultCell().setBorder(Rectangle.BOX);
        table.addCell(createCell("Nom:", tableHeaderFont));
        table.addCell(createCell(s.getFirstname(), tableContentFont));
        table.addCell(createCell("Prenom:", tableHeaderFont));
        table.addCell(createCell(s.getLastname(), tableContentFont));
        table.addCell(createCell("Ville:", tableHeaderFont));
        table.addCell(createCell(s.getCity(), tableContentFont));
        table.addCell(createCell("Adresse:", tableHeaderFont));
        table.addCell(createCell(s.getAdresse(), tableContentFont));
        table.addCell(createCell("Tel:", tableHeaderFont));
        table.addCell(createCell(s.getTel(), tableContentFont));
        table.addCell(createCell("Total Price:", tableHeaderFont));
        table.addCell(createCell(Float.toString(s.getTotal()), tableContentFont));
        
        document.add(table);
        document.close();

        return document;
    }
    private PdfPCell createCell(String content, Font font) {
    PdfPCell cell = new PdfPCell(new Phrase(content, font));
    cell.setBorder(Rectangle.BOX);
    cell.setBorderColor(BaseColor.BLACK);
    cell.setPadding(5);
    return cell;
}

    private class HeaderFooterPageEvent extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle pageSize = document.getPageSize();
            PdfContentByte canvas = writer.getDirectContent();

            // Footer
            Phrase footer = new Phrase("Page " + writer.getPageNumber(), FontFactory.getFont("fonts/Roboto-LightItalic.ttf", BaseFont.IDENTITY_H, 10, Font.NORMAL));
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, footer, (pageSize.getLeft() + pageSize.getRight()) / 2, pageSize.getBottom() + 20, 0);
        }
    }
 
}
