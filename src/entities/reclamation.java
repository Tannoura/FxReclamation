/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;





/**
 *
 * @author Koussay
 */
public class reclamation {
   private int id;
   private String dateRec;
   private String description,nom;
   private boolean etat;

    public reclamation(int id, String dateRec, String description, String nom, boolean etat) {
        this.id = id;
        this.dateRec = dateRec;
        this.description = description;
        this.nom = nom;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public String getDateRec() {
        return dateRec;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateRec(String dateRec) {
        this.dateRec = dateRec;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "reclamation{" + "id=" + id + ", dateRec=" + dateRec + ", description=" + description + ", nom=" + nom + ", etat=" + etat + '}';
    }

    public reclamation() {
    }

 

  
  

   
   
}
