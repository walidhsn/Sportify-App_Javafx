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
public class Event {

    int id;
    int organisateur_id;
    String nom;
    String category;
    LocalDateTime dateEvent;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String imageName;
    String terrainName;
    LocalDateTime updatedAt;
    public Event() {
    }

    public Event(int id, int organisateur_id, String nom, String category, LocalDateTime dateEvent, LocalDateTime startTime, LocalDateTime endTime, String imageName, String terrainName,LocalDateTime updatedAt) {
        this.id = id;
        this.organisateur_id = organisateur_id;
        this.nom = nom;
        this.category = category;
        this.dateEvent = dateEvent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageName = imageName;
        this.terrainName = terrainName;
        this.updatedAt = updatedAt;
    }

    public Event(int organisateur_id, String nom, String category, LocalDateTime dateEvent, LocalDateTime startTime, LocalDateTime endTime, String imageName, String terrainName,LocalDateTime updatedAt) {
        this.organisateur_id = organisateur_id;
        this.nom = nom;
        this.category = category;
        this.dateEvent = dateEvent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageName = imageName;
        this.terrainName = terrainName;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganisateur_id() {
        return organisateur_id;
    }

    public void setOrganisateur_id(int organisateur_id) {
        this.organisateur_id = organisateur_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTerrainName() {
        return terrainName;
    }

    public void setTerrainName(String terrainName) {
        this.terrainName = terrainName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", organisateur_id=" + organisateur_id + ", nom=" + nom + ", category=" + category + ", dateEvent=" + dateEvent + ", startTime=" + startTime + ", endTime=" + endTime + ", imageName=" + imageName + ", terrainName=" + terrainName + ", updatedAt=" + updatedAt + '}';
    }

  

 

  
}
