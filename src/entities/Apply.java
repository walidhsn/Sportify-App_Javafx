/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ramib
 */
public class Apply {
    private int id;
    private String name;
    private int age;
    private String imageName;
    private String academy_name;
    
    public Apply() {
    }

    public Apply(int id, String name, int age, String imageName, String academy_name) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.imageName = imageName;
        this.academy_name = academy_name;
    }

    public Apply(String name, int age, String imageName, String academy_name) {
        this.name = name;
        this.age = age;
        this.imageName = imageName;
        this.academy_name = academy_name;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAcademy_name() {
        return academy_name;
    }

    public void setAcademy_name(String academy_name) {
        this.academy_name = academy_name;
    }

    @Override
    public String toString() {
        return "Apply{" + "id=" + id + ", name=" + name + ", age=" + age + ", imageName=" + imageName + ", academy_name=" + academy_name + '}';
    }

    
       
}
