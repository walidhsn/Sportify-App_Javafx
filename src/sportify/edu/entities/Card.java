/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

/**
 *
 * @author louay
 */
public class Card {
    private  int id;
    private  int user_id;
    private  float total;

    public Card() {
    }

    public Card(int id, int user_id, float total) {
        this.id = id;
        this.user_id = user_id;
        this.total = total;
    }

    public Card(int user_id, float total) {
        this.user_id = user_id;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", user_id=" + user_id + ", total=" + total + '}';
    }
    
    
}
