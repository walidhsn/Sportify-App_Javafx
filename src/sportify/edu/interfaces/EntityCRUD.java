/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.interfaces;

import java.util.List;

/**
 *
 * @author WALID
 * @param <T>
 */
public interface EntityCRUD <T>{
    public void addEntity(T t);
    public void updateEntity(T t);
    public void deleteEntity(int id);
    public List<T> displayEntity();
    public T diplay(int id);
}
