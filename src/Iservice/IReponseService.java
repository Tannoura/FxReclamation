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
 * @param <reponse>
 */
public interface IReponseService<reponse> {
      public void ajouterReponseRec(reponse r);
    public void supprimerReponseRec(reponse r);
    public void modifierReponseRec(reponse r);
    public List<reponse> afficherReponseRec() ;
    
}
