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
public class CardItem {
    private  int id;
    private String libelle;
    private float prix;
    private int quantite;
    private  int card_id;
    private int owner_id;
    public CardItem() {
    }

    public CardItem(int id, String libelle, float prix, int quantite, int card_id,int owner_id) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.card_id = card_id;
        this.owner_id = owner_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    @Override
    public String toString() {
        return "CardItem{" + "id=" + id + ", libelle=" + libelle + ", prix=" + prix + ", quantite=" + quantite + ", card_id=" + card_id + ", owner_id=" + owner_id + '}';
    }


    
}
