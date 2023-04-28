/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ramib
 */
public class Coach {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String academy_name;

    public Coach() {
    }

    public Coach(int id, String name, String email, String phone,String academy_name) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.academy_name = academy_name;
        
    }
//    public Coach(int id, String name, String email, String phone, Academy academy) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.academy = academy;
//    }

    public Coach(String name, String email, String phone, String academy_name) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.academy_name = academy_name;
    }
    
    public Coach(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
//    public Coach(String name, String email, String phone, Academy academy) {
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.academy = academy;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAcademyName() {
        return academy_name;
    }

    public void setAcademyName(String academy_name) {
        this.academy_name = academy_name;
    }
    
//    public Academy getAcademy() {
//        return academy;
//    }
//
//    public void setAcademy(Academy academy) {
//        this.academy = academy;
//    }

    @Override
    public String toString() {
        return "Coach{" + "id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", academy_name=" + academy_name + '}';
    }
}
