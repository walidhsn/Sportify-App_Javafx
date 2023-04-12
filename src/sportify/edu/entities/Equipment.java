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
public class Equipment {
    int id,suppliers_id,categorie_id;
    String name,address,type,image;
    float price;
    int quantity,like,dislike;

    public Equipment() {
    }

    public Equipment(int id, int suppliers_id, int categorie_id, String name, String address, String type, String image, float price, int quantity, int like, int dislike) {
        this.id = id;
        this.suppliers_id = suppliers_id;
        this.categorie_id = categorie_id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.like = like;
        this.dislike = dislike;
    }

    public Equipment(String name, String address, String type, String image, float price, int quantity, int like, int dislike) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.like = like;
        this.dislike = dislike;
    }

    public Equipment(int suppliers_id, int categorie_id, String name, String address, String type, String image, float price, int quantity, int like, int dislike) {
        this.suppliers_id = suppliers_id;
        this.categorie_id = categorie_id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.like = like;
        this.dislike = dislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuppliers_id() {
        return suppliers_id;
    }

    public void setSuppliers_id(int suppliers_id) {
        this.suppliers_id = suppliers_id;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", suppliers_id=" + suppliers_id + ", categorie_id=" + categorie_id + ", name=" + name + ", address=" + address + ", type=" + type + ", image=" + image + ", price=" + price + ", quantity=" + quantity + ", like=" + like + ", dislike=" + dislike + '}';
    }
    
}
