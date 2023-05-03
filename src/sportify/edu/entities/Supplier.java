/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

/**
 *
 * @author MOLKA
 */
public class Supplier {
    private int id;
    private String name;
     private String adresse;
    private String phone;
     private String Email;

     
     
     
     public Supplier() {
    }

    public Supplier(int id, String name, String adresse,String phone,String Email) {
        this.id = id;
        this.name = name;
       this.adresse = adresse ;
       this.phone = phone;
        this.Email= Email;

        
    }
    
       public Supplier(String name, String adresse ,String phone,String Email){
      
        this.name = name;
        this.adresse = adresse ;
         this. phone= phone;
         this.Email= Email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return Email;
    }
   
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    

    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", name=" + name + ", adresse=" + adresse + ", phone=" + phone + ", Email=" + Email + '}';
    }

  

   
   
}
