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
public class Reservation {

    private int id;
    private int terrain_id;
    private int client_id;
    private LocalDateTime dateReservation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean resStatus;
    private int nbPerson;
    private LocalDateTime updated_at;

    public Reservation() {

    }

    public Reservation(int id, int terrain_id, int client_id, LocalDateTime dateReservation, LocalDateTime startTime, LocalDateTime endTime, boolean resStatus, int nbPerson,LocalDateTime updated_at) {
        this.id = id;
        this.terrain_id = terrain_id;
        this.client_id = client_id;
        this.dateReservation = dateReservation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resStatus = resStatus;
        this.nbPerson = nbPerson;
        this.updated_at = updated_at;
    }

    public Reservation(int terrain_id, int client_id, LocalDateTime dateReservation, LocalDateTime startTime, LocalDateTime endTime, boolean resStatus, int nbPerson,LocalDateTime updated_at) {
        this.terrain_id = terrain_id;
        this.client_id = client_id;
        this.dateReservation = dateReservation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resStatus = resStatus;
        this.nbPerson = nbPerson;
        this.updated_at = updated_at;
    }

    public Reservation(Reservation other) {
        this.id = other.id;
        this.terrain_id = other.terrain_id;
        this.client_id = other.client_id;
        this.dateReservation = other.dateReservation;
        this.startTime = other.startTime;
        this.endTime = other.endTime;
        this.resStatus = other.resStatus;
        this.nbPerson = other.nbPerson;
        this.updated_at = other.updated_at;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", terrain_id=" + terrain_id + ", client_id=" + client_id + ", dateReservation=" + dateReservation + ", startTime=" + startTime + ", endTime=" + endTime + ", resStatus=" + resStatus + ", nbPerson=" + nbPerson + ", updated_at=" + updated_at + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerrain_id() {
        return terrain_id;
    }

    public void setTerrain_id(int terrain_id) {
        this.terrain_id = terrain_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
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

    public boolean isResStatus() {
        return resStatus;
    }

    public void setResStatus(boolean resStatus) {
        this.resStatus = resStatus;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

}
