/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import louaypi.entities.CardItem;
import louaypi.tools.MyConnection;

/**
 *
 * @author louay
 */
public class CardItemCrud {
    public void ajouter_card_item(CardItem ci) {
    try {
        String requete = "INSERT INTO card_item (id, libelle, prix, quantity, card_id,owner_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, ci.getId());
        pst.setString(2, ci.getLibelle());
        pst.setFloat(3, ci.getPrix());
        pst.setInt(4, ci.getQuantite());
        pst.setInt(5, ci.getCard_id());
        pst.setInt(6, ci.getOwner_id());

        pst.executeUpdate();
        System.out.println("Article ajoutÃ© !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void modifier_card_item(CardItem ci) {
    try {
        String requete = "UPDATE card_item SET libelle = ?, prix = ?, quantity = ?, card_id = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, ci.getLibelle());
        pst.setFloat(2, ci.getPrix());
        pst.setInt(3, ci.getQuantite());
        pst.setInt(4, ci.getCard_id());
        pst.setInt(5, ci.getId());
        pst.executeUpdate();
        System.out.println("Article modifiÃ© !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void supprimer_card_item(int id) {
    try {
        String requete = "DELETE FROM card_item WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Article supprimÃ© !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public List<CardItem> lister_card_items() {
    List<CardItem> cardItems = new ArrayList<>();
    try {
        String requete = "SELECT * FROM card_item";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            CardItem ci = new CardItem(rs.getInt("id"), rs.getString("libelle"), rs.getFloat("prix"), rs.getInt("quantity"), rs.getInt("card_id"),rs.getInt("owner_id"));
            cardItems.add(ci);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return cardItems;
}
public List<CardItem> lister_card_items_Card_id(int id_card) {
    List<CardItem> cardItems = new ArrayList<>();
    try {
        String requete = "SELECT * FROM card_item WHERE card_id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id_card);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            CardItem ci = new CardItem(rs.getInt("id"), rs.getString("libelle"), rs.getFloat("prix"), rs.getInt("quantity"), rs.getInt("card_id"),rs.getInt("owner_id"));
            cardItems.add(ci);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return cardItems;
}

public CardItem trouver_card_item_par_id(int id) {
    CardItem ci = null;
    try {
        String requete = "SELECT * FROM card_item WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            ci = new CardItem(rs.getInt("id"), rs.getString("libelle"), rs.getFloat("prix"), rs.getInt("quantity"), rs.getInt("card_id"),rs.getInt("owner_id"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return ci;
}
public CardItem trouver_card_item_par_libelle(String lib) {
    CardItem ci = null;
    try {
        String requete = "SELECT * FROM card_item WHERE libelle = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, lib);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            ci = new CardItem(rs.getInt("id"), rs.getString("libelle"), rs.getFloat("prix"), rs.getInt("quantity"), rs.getInt("card_id"),rs.getInt("owner_id"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return ci;
}
}
