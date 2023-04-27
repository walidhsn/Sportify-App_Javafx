/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

import java.time.LocalDateTime;

/**
 *
 * @author WALID
 */
public class Terrain {

    private int id;
    private int owner_id;
    private String name;
    private int capacity;
    private String sportType;
    private float rentPrice;
    private boolean disponibility;
    private int postalCode;
    private String roadName;
    private int roadNumber;
    private String city;
    private String country;
    private String imageName;
    private LocalDateTime updatedAt;

    public Terrain() {

    }

    public Terrain(int id, int owner_id, String name, int capacity, String sportType, float rentPrice, boolean disponibility, int postalCode, String roadName, int roadNumber, String city, String country, String imageName, LocalDateTime updatedAt) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.capacity = capacity;
        this.sportType = sportType;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;
        this.postalCode = postalCode;
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.city = city;
        this.country = country;
        this.imageName = imageName;
        this.updatedAt = updatedAt;
    }

    public Terrain(int owner_id, String name, int capacity, String sportType, float rentPrice, boolean disponibility, int postalCode, String roadName, int roadNumber, String city, String country, String imageName, LocalDateTime updatedAt) {
        this.owner_id = owner_id;
        this.name = name;
        this.capacity = capacity;
        this.sportType = sportType;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;
        this.postalCode = postalCode;
        this.roadName = roadName;
        this.roadNumber = roadNumber;
        this.city = city;
        this.country = country;
        this.imageName = imageName;
        this.updatedAt = updatedAt;
    }

    public Terrain(Terrain other) {
        this.id = other.id;
        this.owner_id = other.owner_id;
        this.name = other.name;
        this.capacity = other.capacity;
        this.sportType = other.sportType;
        this.rentPrice = other.rentPrice;
        this.disponibility = other.disponibility;
        this.postalCode = other.postalCode;
        this.roadName = other.roadName;
        this.roadNumber = other.roadNumber;
        this.city = other.city;
        this.country = other.country;
        this.imageName = other.imageName;
        this.updatedAt = other.updatedAt;
    }

    @Override
    public String toString() {
        return "Terrain{" + "id=" + id + ", owner_id=" + owner_id + ", name=" + name + ", capacity=" + capacity + ", sportType=" + sportType + ", rentPrice=" + rentPrice + ", disponibility=" + disponibility + ", postalCode=" + postalCode + ", roadName=" + roadName + ", roadNumber=" + roadNumber + ", city=" + city + ", country=" + country + ", imageName=" + imageName + ", updatedAt=" + updatedAt + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public float getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(float rentPrice) {
        this.rentPrice = rentPrice;
    }

    public boolean isDisponibility() {
        return disponibility;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public int getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(int roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
