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
import louaypi.entities.Categorie;
import louaypi.tools.MyConnection;

/**
 *
 * @author louay
 */
public class CategorieCrud {
    public void ajouter_categorie(Categorie c) {
    try {
        String requete = "INSERT INTO categorie (nom_cat) VALUES (?)";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, c.getNom_cat());
        pst.executeUpdate();
        System.out.println("CatÃ©gorie ajoutÃ©e !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    /*public void modifier_categorie(int id) {
    try {
        CategorieCrud cc = new CategorieCrud();
        Categorie c = cc.trouver_categorie_par_id(id);
        String requete = "UPDATE categorie SET nom_cat = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, cc.trouver_categorie_par_id(id).getNom_cat());
        System.out.println(cc.trouver_categorie_par_id(id).getNom_cat());
        
        pst.executeUpdate();
        System.out.println("CatÃ©gorie modifiÃ©e !");
                System.out.println(cc.trouver_categorie_par_id(id).getNom_cat());
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}*/
    public void modifier_categorie(int id, String newNom) {
    try {
        CategorieCrud cc = new CategorieCrud();
        Categorie c = cc.trouver_categorie_par_id(id);
        String requete = "UPDATE categorie SET nom_cat = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, newNom);
        pst.setInt(2, id);
        
        pst.executeUpdate();
        System.out.println("CatÃ©gorie modifiÃ©e !");
        System.out.println(c.getNom_cat());
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void supprimer_categorie(int id) {
    try {
        String requete = "DELETE FROM categorie WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("CatÃ©gorie supprimÃ©e !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public List<Categorie> lister_categories() {
    List<Categorie> categories = new ArrayList<>();
    try {
        String requete = "SELECT * FROM categorie";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Categorie c = new Categorie(rs.getInt("id"), rs.getString("nom_cat"));
            categories.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return categories;
}

public Categorie trouver_categorie_par_id(int id) {
    Categorie c = null;
    try {
        String requete = "SELECT * FROM categorie WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            c = new Categorie(rs.getInt("id"), rs.getString("nom_cat"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return c;
}
}
