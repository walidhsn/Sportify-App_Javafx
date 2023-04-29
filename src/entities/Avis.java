/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class Avis {
    
    private int idAvis;
    private int idEvent;
    private String contenu;
    Equipment evenement;    

    public Avis() {
    }

    public Avis(int idAvis, int idEvent, String contenu) {
        this.idAvis = idAvis;
        this.idEvent = idEvent;
        this.contenu = contenu;
    }
    public Avis(int idEvent, String contenu) {
        this.idEvent = idEvent;
        this.contenu = contenu;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }   
    
    public Equipment getEvenement() {
        return evenement;
    }

    public void setEvenement(Equipment evenement) {
        this.evenement = evenement;
    }
    
}
