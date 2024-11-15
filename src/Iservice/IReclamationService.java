/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservice;

import java.util.List;

/**
 *
 * @author Koussay
 * @param <reclamation>
 */
public interface IReclamationService<reclamation> {
    
     public void ajouterReclamation(reclamation r);
    public void supprimerReclamation(reclamation r);
    public void modifierReclamation(reclamation r);
    public List<reclamation> afficherReclamations();
    
}
