/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;
import sportify.edu.entities.Avis;
import sportify.edu.entities.Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author asus
 */
public class AvisService {
    
      Connection cnx;
    public Statement ste;
    public PreparedStatement pst;
    ObservableList<Avis>obListAvis = FXCollections.observableArrayList();

    
    
    public AvisService() {
        
        cnx = MyConnection.getInstance().getCnx();
    }
    
     public void ajouterAvis(Avis A) throws SQLException{
        EquipmentCRUD es = new EquipmentCRUD();
        String requete = "INSERT INTO `avis` (`idEvent`,`contenu`) VALUES(?,?) ;";

              try {
            pst = (PreparedStatement) cnx.prepareStatement(requete);
            pst.setInt(1, A.getIdEvent());
            pst.setString(2, A.getContenu());

            pst.executeUpdate();
            System.out.println("Donner nous votre avis sur notre equipment" + A.getIdEvent() + " ajouté avec succe");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     
     
      public void modifierAvis(Avis A) throws SQLException {
        String req = "UPDATE avis SET contenu = ? where idAvis = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, A.getContenu());
        ps.setInt(2, A.getIdAvis());
        
        ps.executeUpdate();
    }

      public void supprimerAvis(Avis A) throws SQLException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String req = "delete from avis where idAvis = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, A.getIdAvis());
        ps.executeUpdate();
        System.out.println("avis with id= " + A.getIdAvis() + "  is deleted successfully");
    }
      
      public List<Avis> recupererAvis() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        List<Avis> aviss = new ArrayList<>();
        String s = "select * from avis";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Avis A = new Avis();
            A.setIdEvent(rs.getInt("idEvent"));
            A.setContenu(rs.getString("contenu"));
           
            aviss.add(A);

        }
        return aviss;
    }
    
      
      
      public ObservableList<Avis> getAvisByEvent()throws SQLException {
        String sql ="select * from Avis a "
                + "JOIN evenement e ON e.id_event=a.idEvent";
        List<Avis> listeAvis = new ArrayList<>();

        try {
            Statement statement = cnx.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int avisid = result.getInt(1);
                int idEv = result.getInt(2);
                String contenueAvis = result.getString(3);
                

                Avis a = new Avis( avisid,idEv,contenueAvis);
                obListAvis.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return obListAvis;
    }
    
    
    
}
