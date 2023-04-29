/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Supplier;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MOLKA
 */
public interface EntityCRUD2<S> {
    public void addEntity(S s);
    public List<S> display();
      public Supplier getEntity(int supplierId) throws SQLException;
}
