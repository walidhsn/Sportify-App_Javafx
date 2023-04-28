/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entities.Academy;
import java.util.List;
import services.AcademyCRUD;
import services.CoachCRUD;

/**
 *
 * @author ramib
 */
public class MainClass {
    public static void main(String[] args) {
//        MyConnection mc = new MyConnection();
//        Academy p = new Academy("Molka","rami");
        AcademyCRUD pcd = new AcademyCRUD();
        CoachCRUD cd = new CoachCRUD();
//        List<String> coachNames = cd.findCoachNamesByAcademyName("Rami");
//        System.out.println(coachNames);
//        Academy p = new Academy();
//        p.setId(15);
//        p.setName("Hamma");
//        p.setCategory("Basket");
//        pcd.updateEntity(p);
//        Academy academy = new Academy("Test Academy", "Test Category", "test-image.jpg");
//        String imageName = academy.getImageName();
//        System.out.println(imageName);


//        pcd.addEntity(p);
//        pcd.addEntity2();
//        pcd.deleteEntity(1); // Delete academy with ID 1

        System.out.println(cd.display());
//        System.out.println(pcd.academyDetails(2));
    }
    
}
