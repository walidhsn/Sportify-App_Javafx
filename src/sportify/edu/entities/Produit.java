/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author ramib
 */
public class Produit {
   private  int id;
    private String refernce;
    private String libelle;
    private float prix;
    private String image;
    private int quantite;
    private int categorie;
    private int id_owner;
    private LocalDateTime updated_at;
    

    public Produit() {
    }

    public Produit(int id, String refernce, String libelle, float prix, String image, int quantite, int categorie,LocalDateTime updated_at) {
        this.id = id;
        this.refernce = refernce;
        this.libelle = libelle;
        this.prix = prix;
        this.image = image;
        this.quantite = quantite;
        this.categorie = categorie;
        this.updated_at = updated_at;
       
        
    }

    
    
    /*public Produit(int id, String refernce, String libelle, float prix, String image, int quantite, String categorie, int id_owner) {
        this.id = id;
        this.refernce = refernce;
        this.libelle = libelle;
        this.prix = prix;
        this.image = image;
        this.quantite = quantite;
        this.categorie = categorie;
        this.id_owner = id_owner;
        
    }*/

    public Produit(String refernce, String libelle, float prix, String image, int quantite, int categorie, int id_owner,LocalDateTime updated_at) {
        this.refernce = refernce;
        this.libelle = libelle;
        this.prix = prix;
        this.image = image;
        this.quantite = quantite;
        this.categorie = categorie;
        this.id_owner = id_owner;
        this.updated_at = updated_at;
    }

    public Produit(String refernce, String libelle, float prix, String image, int quantite, int categorie,LocalDateTime updated_at) {
        this.refernce = refernce;
        this.libelle = libelle;
        this.prix = prix;
        this.image = image;
        this.quantite = quantite;
        this.categorie = categorie;
        this.updated_at = updated_at;
    }

    public Produit(String refernce, String libelle, float prix, int quantite, int categorie,LocalDateTime updated_at) {
        this.refernce = refernce;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
        this.updated_at = updated_at;
       
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    

    public String getRefernce() {
        return refernce;
    }

    public void setRefernce(String refernce) {
        this.refernce = refernce;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public int getId_owner() {
        return id_owner;
    }

    public void setId_owner(int id_owner) {
        this.id_owner = id_owner;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", refernce=" + refernce + ", libelle=" + libelle + ", prix=" + prix + ", image=" + image + ", quantite=" + quantite + ", categorie=" + categorie + ", id_owner=" + id_owner + ", updated_at=" + updated_at + '}';
    }
    
    
   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.refernce);
        hash = 79 * hash + this.id_owner;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.id_owner != other.id_owner ) {
            return false;
        }
        if (!Objects.equals(this.refernce, other.refernce)) {
            return false;
        }
        return true;
    }
    

    
    
}

