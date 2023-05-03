/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

import java.util.List;
import javax.persistence.OneToMany;

/**
 *
 * @author ramib
 */
public class Academy {
    private int id;
    private String name;
    private String category;
    private String imageName;
    private List<Coach> coaches;

    public Academy() {
    }

    public Academy(int id, String name, String category, String imageName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageName = imageName;
    }

    public Academy(String name, String category, String imageName) {
        this.name = name;
        this.category = category;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    
    public String getImagePath() {
        String imageDirectory = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
        return imageDirectory + imageName;
    }

    @Override
    public String toString() {
        return "Academy{" + "id=" + id + ", name=" + name + ", category=" + category + ", imageName=" + imageName + '}';
    }
    
    @OneToMany(mappedBy = "academy")
    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }
}
