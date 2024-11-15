/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Iservice.IReclamationService;
import dataSource.DataSource;
import entities.reclamation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Koussay
 */
public class ReclamationService implements IReclamationService<reclamation>{

    @Override
    public void ajouterReclamation(reclamation r) {
        try {
            String requete= "INSERT INTO reclamation ( dateRec,  description,  nom,  etat)"
                    + "VALUES (?,?,?,?)";
            
          PreparedStatement pst = DataSource.getInstance().getCnx() //envoie d'une requete a la base
                 .prepareStatement(requete);
           
          pst.setString(1,r.getDateRec());
            pst.setString(2, r.getDescription());
            pst.setString(3, r.getNom());
            pst.setBoolean(4,r.isEtat());
            
            pst.executeUpdate();   
            System.out.println("Reclamation inserée");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerReclamation(reclamation r) {
        try {
            String requete = "DELETE FROM reclamation where id=?";
            PreparedStatement pst = DataSource.getInstance().getCnx()
                    .prepareStatement(requete);
          
            pst.setInt(1, r.getId());
            pst.executeUpdate();
            System.out.println("Réponse supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    
    @Override
    public void modifierReclamation(reclamation r) {
         try {
            String requete = "UPDATE reclamation SET id=?,dateRec=?,description=?,nom=?,etat=? WHERE id =?";
            PreparedStatement pst = DataSource.getInstance().getCnx()
                    .prepareStatement(requete);
          
            pst.setInt(1,r.getId());
            pst.setString(2,r.getDateRec());
             pst.setString(3,r.getDescription());
            pst.setString(4, r.getNom());
             pst.setBoolean(5,r.isEtat());
              pst.setInt(6,r.getId());
            
            pst.executeUpdate();
            System.out.println("Réponse modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    @Override
    public List<reclamation> afficherReclamations() {
    
        List<reclamation> ReclamationsList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reclamation r ";
            Statement st = DataSource.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); //te5ou hajet f reclamation w thotohom f rs
           
            while(rs.next()){
                reclamation r = new reclamation();
                
                r.setId(rs.getInt("id"));
                r.setDateRec(rs.getString("dateRec"));
                r.setDescription(rs.getString("description"));
                r.setNom(rs.getString("nom"));
                r.setEtat(rs.getBoolean("etat")); 
                 System.out.println("les reclamations sont" +r.toString());
                
                ReclamationsList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ReclamationsList; 
        
    }
    
}
