/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihem;

import Iservice.IReclamationService;
import Iservice.IReponseService;
import Service.ReclamationService;
import Service.ReponseService;
import entities.reclamation;
import entities.reponse;


/**
 *
 * @author Koussay
 */
public class Rihem {

    /**
     * @param args the command line arguments
     
     */
    public static void main(String[] args) {
        reclamation r = new reclamation(10,"l", "l", "l",true);
        IReclamationService rs = new ReclamationService();
        
       // rs.ajouterReclamation(r);
      //  rs.supprimerReclamation(r);
       rs.modifierReclamation(r);
       //rs.afficherReclamations();
       
     
        
    }
    
}
