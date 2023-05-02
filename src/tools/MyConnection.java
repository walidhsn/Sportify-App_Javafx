/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ramib
 */
public class MyConnection {
//     private String url="jdbc:mysql://localhost:3306/javatest";
     private String url="jdbc:mysql://localhost:3306/sportifyintegration";
    private String login="root";
    private String pwd="";
    Connection cnx;
    public static MyConnection instance;

    private MyConnection() {
        try{
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connected to the database!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnection getInstance() {
            if(instance == null){
                instance = new MyConnection();
            }  
            return instance;
    }
    
}
