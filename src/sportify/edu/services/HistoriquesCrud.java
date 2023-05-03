/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Historique;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author louay
 */
public class HistoriquesCrud {
     public void ajouter_historique(Historique h) {
        try {
            String requete = "INSERT INTO historique (commande_id, libelle, prix, quantity,owner_id) VALUES(?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, h.getCommande_id());
            pst.setString(2, h.getLibelle());
            pst.setFloat(3, h.getPrix());
            pst.setInt(4, h.getQuantite());
            pst.setInt(5, h.getOwner_id());

            pst.executeUpdate();
            System.out.println("Historique ajoutÃ© !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     
    // READ operations
     public List<Historique> trouver_tous_les_historiques() {
    List<Historique> historiques = new ArrayList<>();
    try {
        String requete = "SELECT * FROM historique";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Historique h = new Historique(rs.getInt("id"), rs.getInt("commande_id"), rs.getString("libelle"),
                                          rs.getFloat("prix"), rs.getInt("quantity"),rs.getInt("owner_id"));
            historiques.add(h);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return historiques;
}

    public List<Historique> trouver_historique_par_commande_id(int commande_id) {
        List<Historique> historiques = new ArrayList<>();
        try {
            String requete = "SELECT * FROM historique WHERE commande_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, commande_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Historique h = new Historique(rs.getInt("id"), rs.getInt("commande_id"), rs.getString("libelle"),
                                              rs.getFloat("prix"), rs.getInt("quantity"),rs.getInt("owner_id"));
                historiques.add(h);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historiques;
    }
    public Historique trouver_historique_par_commande_id2(int commande_id) {
        Historique historique = null;
        try {
            String requete = "SELECT * FROM historique WHERE commande_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, commande_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Historique h = new Historique(rs.getInt("id"), rs.getInt("commande_id"), rs.getString("libelle"),
                                              rs.getFloat("prix"), rs.getInt("quantity"),rs.getInt("owner_id"));
                historique = h;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historique;
    }
    
    public List<Historique> trouver_historique_par_userid(int owner_id) {
        List<Historique> historiques = new ArrayList<>();
        try {
            String requete = "SELECT * FROM historique WHERE owner_id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, owner_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Historique h = new Historique(rs.getInt("id"), rs.getInt("commande_id"), rs.getString("libelle"),
                                              rs.getFloat("prix"), rs.getInt("quantity"),rs.getInt("owner_id"));
                historiques.add(h);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return historiques;
    }
    // UPDATE operation
    public void modifier_historique(Historique h) {
        try {
            String requete = "UPDATE historique SET libelle = ?, prix = ?, quantity = ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, h.getLibelle());
            pst.setFloat(2, h.getPrix());
            pst.setInt(3, h.getQuantite());
            pst.setInt(4, h.getId());
            pst.executeUpdate();
            System.out.println("Historique modifiÃ© !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // DELETE operation
    public void supprimer_historique(int historique_id) {
        try {
            String requete = "DELETE FROM historique WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, historique_id);
            pst.executeUpdate();
            System.out.println("Historique supprimÃ© !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

