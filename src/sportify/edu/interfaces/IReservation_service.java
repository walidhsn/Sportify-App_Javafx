/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.interfaces;

import java.util.List;
import sportify.edu.entities.Reservation;

/**
 *
 * @author WALID
 */
public interface IReservation_service {
   public List<String> reserver(Reservation r);
   public List<String> reserver_update(Reservation r);
   public List<Reservation> myReservation(int client_id);
   public List<Reservation> terrain_reservations(int terrain_id);
   public void add_equipment_reservation(int id_reservation,int id_equipment);
   public void delete_equipment_reservation(int id_reservation);
   public void update_equipment_reservation(int id_reservation);
   
}
