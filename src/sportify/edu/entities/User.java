/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author WALID
 */
public class User {
    private int id;
    private String email;
    private String nomUtilisateur;
    private String phone;
    private String password;
    private String firstname;
    private String lastname;
    private ArrayList<String> roles = new ArrayList<>();
    private boolean status;
    private List<Terrain> terrains;
    private List<Reservation> reservations;
    private String imageName;
    
    public User() {
        this.terrains= new ArrayList<>();
        this.reservations= new ArrayList<>();
    }
    
    public User(String email,ArrayList<String> roles,String imageName,String nomutilisateur,String phone,String firstname,String lastname,boolean status){
        this.email = email;
        this.firstname = firstname;
        this.roles = roles;
        this.lastname = lastname;
        this.status = status;
        this.phone = phone;
        this.imageName = imageName;
    }

    public User(String email,String nomutilisateur,String phone,String firstname,String lastname,boolean status){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.phone = phone;
    }

    public User(String email,String nomutilisateur,String phone,String firstname,String lastname){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }


    public User(int id, String email,String password, String nomUtilisateur, String phone, String firstname, String lastname, boolean status,ArrayList<String> roles) {
        this.id = id;
        this.email = email;
        this.nomUtilisateur = nomUtilisateur;
        this.password = password;
        this.phone = phone;
        this.roles = roles;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.terrains= new ArrayList<>();
        this.reservations= new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", nomUtilisateur=" + nomUtilisateur + ", phone=" + phone + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + ", roles=" + roles + ", status=" + status + ", imageName=" + imageName + '}';
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void addRole(String role){
        this.roles.add(role);
    }
    
    public static User getUserById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = new User();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportifyintegration", "root", "");
            stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                //user = new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3));
                user.setId(rs.getInt(1));
                System.out.println(rs.getString(1));
                user.setPassword(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setFirstname(rs.getString(4));
                user.setLastname(rs.getString(5));
                user.setPhone(rs.getString(6));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    
    
    
     public static User getUserByEmail(String email) {
        Connection conn;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = new User();
        try {
            conn = MyConnection.getInstance().getCnx();
            stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            System.out.println("result : "+rs.next());
                String json = rs.getString("roles");
                JSONArray jsonArray = new JSONArray(json);
                ArrayList<String> roles = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    String role = jsonArray.getString(i);
                    roles.add(role);
                }
            user.setEmail(rs.getString("email"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setId(rs.getInt("id"));
            user.setNomUtilisateur(rs.getString("nomutilisateur"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getString("phone"));
            user.setStatus(rs.getBoolean("status"));
            
//                String json = rs.getString("roles");
//                JSONArray jsonArray = new JSONArray(json);
//                ArrayList<String> roles = new ArrayList<>();
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    String role = jsonArray.getString(i);
//                    roles.add(role);
//                }

            user.setRoles(roles);
            System.out.println("user : "+user);
            return user;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
     
}
