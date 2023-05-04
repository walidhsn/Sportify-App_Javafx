/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sportify.edu.entities.Sponsor;
import sportify.edu.interfaces.EntityCRUD;
import sportify.edu.interfaces.ISponsorEvent;
import sportify.edu.tools.MyConnection;

/**
 *
 * @author WALID
 */
public class SponsorService implements EntityCRUD<Sponsor>,ISponsorEvent {

    @Override
    public void addEntity(Sponsor t) {
        try {
            String rq = "INSERT INTO sponsor_e ( nom_sponsor,email_sponsor, tel_sponsor) VALUES (?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setString(1, t.getNomSponsor());
            pst.setString(2, t.getEmailSponsor());
            pst.setString(3, String.valueOf(t.getTelSponsor()).trim());

            pst.executeUpdate();
            System.out.println("Sponsor has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateEntity(Sponsor t) {
        try {
            String rq = "UPDATE sponsor_e SET nom_sponsor = ? ,email_sponsor = ? ,tel_sponsor = ? WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);

            pst.setString(1, t.getNomSponsor());
            pst.setString(2, t.getEmailSponsor());
            pst.setString(3, String.valueOf(t.getTelSponsor()).trim());
            pst.setInt(4, t.getId());

            pst.executeUpdate();
            System.out.println("Sponsor has been updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM sponsor_e where id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Sponsor has been deleted.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Sponsor> displayEntity() {
        List<Sponsor> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM sponsor_e";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                Sponsor r = new Sponsor();

                r.setId(rs.getInt("id"));
                r.setNomSponsor(rs.getString("nom_sponsor"));
                r.setEmailSponsor(rs.getString("email_sponsor"));
                r.setTelSponsor(Integer.parseInt(rs.getString("tel_sponsor")));

                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Sponsor diplay(int id) {
        Sponsor r = new Sponsor();
        try {
            String rq = "SELECT * FROM sponsor_e WHERE id = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r.setId(rs.getInt("id"));
                r.setNomSponsor(rs.getString("nom_sponsor"));
                r.setEmailSponsor(rs.getString("email_sponsor"));
                r.setTelSponsor(Integer.parseInt(rs.getString("tel_sponsor")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public Sponsor find_sponsor(String name, String email, String phone) {
        Sponsor r = null;
        try {
            String rq = "SELECT * FROM sponsor_e WHERE nom_sponsor = ? OR email_sponsor = ? OR tel_sponsor = ? ";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r=new Sponsor();
                r.setId(rs.getInt("id"));
                r.setNomSponsor(rs.getString("nom_sponsor"));
                r.setEmailSponsor(rs.getString("email_sponsor"));
                r.setTelSponsor(Integer.parseInt(rs.getString("tel_sponsor")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

    @Override
    public Sponsor find_sponsor_update(int id, String name, String email, String phone) {
         Sponsor r = null;
        try {
            String rq = "SELECT * FROM sponsor_e WHERE (nom_sponsor = ? OR email_sponsor = ? OR tel_sponsor = ?) AND id <> ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(rq);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setInt(4, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                 r=new Sponsor();
                r.setId(rs.getInt("id"));
                r.setNomSponsor(rs.getString("nom_sponsor"));
                r.setEmailSponsor(rs.getString("email_sponsor"));
                r.setTelSponsor(Integer.parseInt(rs.getString("tel_sponsor")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }

}
