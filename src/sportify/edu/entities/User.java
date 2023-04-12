/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WALID
 */
public class User {
    private int id;
    private String email;
    private String nomUtilisateur;
    private String phone;
    private String firstname;
    private String lastname;
    private boolean status;
    private List<Terrain> terrains;
    private List<Reservation> reservations;
    
    public User() {
        this.terrains= new ArrayList<>();
        this.reservations= new ArrayList<>();
    }

    public User(int id, String email, String nomUtilisateur, String phone, String firstname, String lastname, boolean status) {
        this.id = id;
        this.email = email;
        this.nomUtilisateur = nomUtilisateur;
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.terrains= new ArrayList<>();
        this.reservations= new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", nomUtilisateur=" + nomUtilisateur + ", phone=" + phone + ", firstname=" + firstname + ", lastname=" + lastname + ", status=" + status + ", terrains=" + terrains + ", reservations=" + reservations + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    
    
}
