/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

/**
 *
 * @author WALID
 */
public class Sponsor {
    int id;
    String nomSponsor;
    String emailSponsor;
    int telSponsor;

    public Sponsor() {
    }

    public Sponsor(int id, String nomSponsor, String emailSponsor, int telSponsor) {
        this.id = id;
        this.nomSponsor = nomSponsor;
        this.emailSponsor = emailSponsor;
        this.telSponsor = telSponsor;
    }

    public Sponsor(String nomSponsor, String emailSponsor, int telSponsor) {
        this.nomSponsor = nomSponsor;
        this.emailSponsor = emailSponsor;
        this.telSponsor = telSponsor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomSponsor() {
        return nomSponsor;
    }

    public void setNomSponsor(String nomSponsor) {
        this.nomSponsor = nomSponsor;
    }

    public String getEmailSponsor() {
        return emailSponsor;
    }

    public void setEmailSponsor(String emailSponsor) {
        this.emailSponsor = emailSponsor;
    }

    public int getTelSponsor() {
        return telSponsor;
    }

    public void setTelSponsor(int telSponsor) {
        this.telSponsor = telSponsor;
    }

    @Override
    public String toString() {
        return "Sponsor{" + "id=" + id + ", nomSponsor=" + nomSponsor + ", emailSponsor=" + emailSponsor + ", telSponsor=" + telSponsor + '}';
    }
    
}
