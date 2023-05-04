/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportify.edu.interfaces;

import sportify.edu.entities.Sponsor;

/**
 *
 * @author WALID
 */
public interface ISponsorEvent {
    public Sponsor find_sponsor(String name,String email,String phone);
    public Sponsor find_sponsor_update(int id,String name,String email,String phone);
}
