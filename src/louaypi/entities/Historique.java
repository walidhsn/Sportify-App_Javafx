/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.entities;

/**
 *
 * @author louay
 */
public class Historique {
    private  int id;
    private  int commande_id;
    private String libelle;
    private float prix;
    private int quantite;
    private int owner_id;
    
    public Historique() {
    }

    public Historique(int id, int commande_id, String libelle, float prix, int quantite,int owner_id) {
        this.id = id;
        this.commande_id = commande_id;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.owner_id = owner_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(int commande_id) {
        this.commande_id = commande_id;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
    
    
}
