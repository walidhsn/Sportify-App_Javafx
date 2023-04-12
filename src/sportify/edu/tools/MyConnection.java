/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author WALID
 */
public class MyConnection {
    private String url="jdbc:mysql://localhost:3306/sportifyintegration";
    private String login="root";
    private String pwd="";
    Connection cnx;
    public static MyConnection Instance;
    private MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }
    public static MyConnection getInstance()
    {
        if(Instance ==null){
            Instance = new MyConnection();
        }
        return Instance;
    }
}
