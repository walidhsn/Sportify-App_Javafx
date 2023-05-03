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
public class Commande {
    //id	user_id	card_id	firstname	lastname	email	tel	city	adresse	total
    private  int id;
    private  int card_id;
    private  int user_id;
    private String firstname;
    private String lastname;
    private String email;
    private String tel;
    private String adresse;
    private String city;
    private float total;

    public Commande() {
    }

    public Commande(int id, int card_id, int user_id, String firstname, String lastname, String email, String tel, String adresse, String city, float total) {
        this.id = id;
        this.card_id = card_id;
        this.user_id = user_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.city = city;
        this.total = total;
    }

    public Commande(int card_id, int user_id, String firstname, String lastname, String email, String tel, String adresse, String city, float total) {
        this.card_id = card_id;
        this.user_id = user_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.city = city;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", card_id=" + card_id + ", user_id=" + user_id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", tel=" + tel + ", adresse=" + adresse + ", city=" + city + ", total=" + total + '}';
    }
    
    
}
