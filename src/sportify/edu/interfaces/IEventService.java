/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.interfaces;


import java.time.LocalDateTime;
import java.util.List;
import sportify.edu.entities.Event;
import sportify.edu.entities.Sponsor;

/**
 *
 * @author WALID
 */
public interface IEventService {
    public List<Event> myEvents(int organisateur_id);
    public Event find_event(int organisateur_id,String nom,String terrainName,LocalDateTime dateEvent);
    public Event find_event_update(int id_event,int organisateur_id,String nom,String terrainName,LocalDateTime dateEvent);
    public List<Event> findConflictingEvents(Event event);
    public List<String> createEvent(Event r);
    public List<String> updateEvent(Event r);
    public void addEventSponsor(int sponsor_id,int event_id);
    public void updateEventSponsor(int sponsor_id,int event_id);
    public void deleteEventSponsor(int sponsor_id,int event_id);
    public void join_event(int id_event,int id_client);
    public void delete_join_event(int id_event,int id_client);
    public List<Event> myEvents_client(int id_client);
    public List<Sponsor> mySponsors(int event_id);
}
