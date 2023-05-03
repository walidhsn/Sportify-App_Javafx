/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.interfaces;

import sportify.edu.entities.Equipment;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MOLKA
 */
public interface EntityEquipmentCRUD<T> {
    public void addEntity(T t);
    public List<T> display();
    public Equipment getEntity(int equipmentId) throws SQLException;
    
}
