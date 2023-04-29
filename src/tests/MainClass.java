/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;


import entities.Equipment;
import entities.Supplier;
import services.SupplierCRUD;
import services.EquipmentCRUD;
import tools.MyConnection;
//import services.EquipmentCRUD;
//import tools.MyConnection;
/**
 *
 * @author MOLKA
 */
public class MainClass {
  public static void main(String[] args) {
//        MyConnection mc = new MyConnection();
       Equipment e;
        Supplier s;
      s = new Supplier("Lara","Mahdia","58111458","lili"); 
       // e = new Equipment("Raquette", "Lara",55,21,"rami.PNG");
       // EquipmentCRUD pcd = new EquipmentCRUD();
       SupplierCRUD scd = new SupplierCRUD();
//           scd.addEntity(s);
   // pcd.addEntity(e);
       scd.addEntity2(s);
         
//         e.setName("Mouradi");
//        e.setCategory("Basket");
//       pcd.updateEntity(e);

    System.out.println(pcd.display());
    }  
}
