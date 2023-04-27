/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sportify.edu.services;

import sportify.edu.entities.User;


/**
 *
 * @author moata
 */
public interface UserInterface {
        public User getutilisateur(int id);
        public User getUserByEmail(String email);
}
