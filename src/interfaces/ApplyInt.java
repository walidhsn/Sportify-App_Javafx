/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Apply;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ramib
 */
public interface ApplyInt<T> {
//    public void addEntity(String name, String category);
    public void addEntity(T t);
    public void applyDetails(int id);
    public void deleteEntity(int id);
//    public void updateEntity(T t);
    public Apply getEntity(String applyName) throws SQLException;
    public List<T> display();
}
    

