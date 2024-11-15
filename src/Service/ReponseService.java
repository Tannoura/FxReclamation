/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Iservice.IReponseService;
import dataSource.DataSource;
import entities.reponse;
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
public class ReponseService implements IReponseService<reponse>{

    @Override
    public void ajouterReponseRec(reponse r) {
       try{
        String requete= "INSERT INTO reponse (id,dateRes,resolution)"
                    + "VALUES (?,?,?)";
            
          PreparedStatement pst = DataSource.getInstance().getCnx() 
                 .prepareStatement(requete);
          pst.setInt(1,r.getId());
          pst.setString(2,r.getDateRes());
          pst.setString(3,r.getResolution());
            pst.executeUpdate();   
            System.out.println("Réponse inserée");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerReponseRec(reponse r) {
        try {
            String requete = "DELETE FROM reponse where idRep=?";
            PreparedStatement pst = DataSource.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, r.getIdRep());
            pst.executeUpdate();
            System.out.println("Réponse supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public void modifierReponseRec(reponse r) {
         try {
            String requete = "UPDATE reponse SET idRep=?,id=?,dateRes=?,resolution=? WHERE idRep=?";
            PreparedStatement pst = DataSource.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1,r.getIdRep());
            pst.setInt(2,r.getId());
             pst.setString(3,r.getDateRes());
              pst.setString(4,r.getResolution());
              pst.setInt(5,r.getIdRep());
            
            
            pst.executeUpdate();
            System.out.println("Réponse modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<reponse> afficherReponseRec() {
        List<reponse> ReponseRecList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reponse r ";
            Statement st = DataSource.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); //te5ou hajet f reclamation w thotohom f rs
            while(rs.next()){
                reponse r = new reponse();
                
                
                
                r.setIdRep(rs.getInt("idRep"));
                r.setId(rs.getInt("id"));
                r.setDateRes(rs.getString("dateRes"));
                r.setDateRes(rs.getString("resolution"));
                
                ReponseRecList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ReponseRecList;
    }
    
}
