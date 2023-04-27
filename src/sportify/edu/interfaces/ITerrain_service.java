/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.interfaces;

import java.util.List;
import java.util.Set;
import sportify.edu.entities.Terrain;

/**
 *
 * @author WALID
 */
public interface ITerrain_service {
    public List<Terrain> myProprieties(int owner_id);
    public List<Terrain> filterTerrain(String location,String sport_type,String rent_price);
    public List<Terrain> searchTerrain(String search_term);
    public void add_autoCompleteWord(String word);
    public Set<String> get_autoCompleteWords();
}
