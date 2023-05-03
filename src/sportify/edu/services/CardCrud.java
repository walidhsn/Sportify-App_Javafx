/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Card;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author louay
 */
public class CardCrud {
    public void ajouter_card(Card c) {
    try {
        String requete = "INSERT INTO card (id, user_id, total) VALUES (?, ?, ?)";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, c.getId());
        pst.setInt(2, c.getUser_id());
        pst.setFloat(3, c.getTotal());
        pst.executeUpdate();
        System.out.println("Panier ajoutÃ© !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void modifier_card(Card c) {
    try {
        String requete = "UPDATE card SET user_id = ?, total = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, c.getUser_id());
        pst.setFloat(2, c.getTotal());
        pst.setInt(3, c.getId());
        pst.executeUpdate();
        System.out.println("Panier modifiÃ© !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void supprimer_card(int id) {
    try {
        String requete = "DELETE FROM card WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Panier supprimÃ© !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public List<Card> lister_cards() {
    List<Card> cards = new ArrayList<>();
    try {
        String requete = "SELECT * FROM card";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Card c = new Card(rs.getInt("id"), rs.getInt("user_id"), rs.getFloat("total"));
            cards.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return cards;
}

public Card trouver_card_par_id(int id) {
    Card c = null;
    try {
        String requete = "SELECT * FROM card WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            c = new Card(rs.getInt("id"), rs.getInt("user_id"), rs.getFloat("total"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return c;
}
public Card trouver_card_par_user_id(int user_id) {
    Card card = null;
    try {
        String requete = "SELECT * FROM card WHERE user_id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, user_id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Card c = new Card(rs.getInt("id"), rs.getInt("user_id"), rs.getFloat("total"));
            card = c;
            
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return card;
}

}
