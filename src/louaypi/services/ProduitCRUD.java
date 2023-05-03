/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.services;

import java.sql.Connection;
import louaypi.entities.Produit;
import louaypi.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.activation.DataSource;

/**
 *
 * @author ramib
 */
public class ProduitCRUD {
    
    public void ajouter_produit(Produit r) {
        try {
            String requete1 = "INSERT INTO produit (id,ref,libelle,prix,image_name,stock,categorie_id,updated_at,owner_id) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete1);
            pst.setInt(1, r.getId());
            pst.setString(2, r.getRefernce());
            pst.setString(3, r.getLibelle());
            pst.setFloat(4, r.getPrix());
            pst.setString(5, r.getImage());
            pst.setInt(6, r.getQuantite());
            pst.setInt(7, r.getCategorie());
            pst.setTimestamp(8, Timestamp.valueOf(r.getUpdated_at()));
            pst.setInt(8, r.getId_owner());
            
            pst.executeUpdate();
            System.out.println("produit ajoutÃ© !");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ObservableList<Produit> listerProduits() {
        ObservableList<Produit> myList = FXCollections.observableArrayList();
        try {
            
            String requete2 = "Select * FROM produit";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete2);
            while (rs.next()) {
                Produit rec = new Produit();
                
                rec.setId(rs.getInt("id"));
                rec.setRefernce(rs.getString("ref"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setPrix(rs.getFloat("prix"));
                rec.setImage(rs.getString("image_name"));
                rec.setQuantite(rs.getInt("stock"));
                rec.setCategorie(rs.getInt("categorie_id"));
                rec.setId_owner(rs.getInt("owner_id"));
                System.out.println("Rec: " + rec);

                myList.add(rec);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        return myList;
    }
    
    public void supprimer_produit(Produit R) {
        
        try {
            String requete3 = "DELETE FROM produit WHERE id=" + R.getId();
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            
            st.executeUpdate(requete3);
            System.out.println("Produit supprimÃ© !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    public ObservableList<Produit> SearchProd(String entry) {
        ObservableList<Produit> myList = FXCollections.observableArrayList();
        try {
            
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement("SELECT * FROM produit WHERE ref LIKE ? or libelle LIKE ? or prix LIKE ? or image_name LIKE ? or stock LIKE ? or categorie_id LIKE ?");
            ps.setString(1, "%" + entry + "%");
            ps.setString(2, "%" + entry + "%");
            ps.setString(3, "%" + entry + "%");
            ps.setString(4, "%" + entry + "%");
            ps.setString(5, "%" + entry + "%");
            ps.setString(6, "%" + entry + "%");
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Produit rec = new Produit();
                rec.setId(rs.getInt("id"));
                rec.setRefernce(rs.getString("ref"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setPrix(rs.getFloat("prix"));
                rec.setImage(rs.getString("image_name"));
                rec.setQuantite(rs.getInt("stock"));
                rec.setCategorie(rs.getInt("categorie_id"));
                myList.add(rec);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void modifier_produit(Produit r, String reference, String libelle, float prix, String image, int quantite, int categorie) {
        try {
            String requete4 = "UPDATE produit SET ref=?, libelle=?, prix=?, image_name=?, stock=?, categorie_id=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, reference);
            pst.setString(2, libelle);
            pst.setFloat(3, prix);
            pst.setString(4, image);
            pst.setInt(5, quantite);
            pst.setInt(6, categorie);
            pst.setInt(7, r.getId());
            pst.executeUpdate();
            System.out.println("Produit modifiÃ© !");
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ObservableList<Produit> getByCategorie(int categorie_id) {
        ObservableList<Produit> myList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement("SELECT * FROM produit WHERE categorie_id = ?");
            ps.setInt(1, categorie_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit rec = new Produit();
                rec.setId(rs.getInt("id"));
                rec.setRefernce(rs.getString("ref"));
                rec.setLibelle(rs.getString("libelle"));
                rec.setPrix(rs.getFloat("prix"));
                rec.setImage(rs.getString("image_name"));
                rec.setQuantite(rs.getInt("stock"));
                rec.setCategorie(rs.getInt("categorie_id"));
                myList.add(rec);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    public void modifier_produit(Produit r) {
        try {
            String requete4 = "UPDATE produit SET ref=?,libelle=?,prix=?,image_name=?,stock=?,categorie_id=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete4);
            pst.setString(1, r.getRefernce());
            pst.setString(2, r.getLibelle());
            pst.setFloat(3, r.getPrix());
            pst.setString(4, r.getImage());
            pst.setInt(5, r.getQuantite());
            pst.setInt(6, r.getCategorie());
            pst.setInt(7, r.getId());
            pst.executeUpdate();
            System.out.println("Produit modifiÃ© !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public int prodparcat(int id){
        int n=0;
        try {
                    String sql = "SELECT COUNT(*) FROM produit  INNER JOIN categorie ON produit.categorie_id = categorie.id WHERE categorie.id = "+id;
                            
                    //PreparedStatement ste = MyConnection.getInsatance().getCnx().prepareStatement(sql);
                                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(sql);

                    ResultSet s = pst.executeQuery(sql);

                    while (s.next()) {
                    n=s.getInt(1);
                }



                    
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                } 
        
        
        return n;
        
    }
}
