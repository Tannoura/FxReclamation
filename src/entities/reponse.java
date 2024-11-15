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
public class reponse {
    private int idRep,id;
    private String dateRes,resolution;

    public reponse() {
    }

    public reponse(int idRep, int id, String dateRes, String resolution) {
        this.idRep = idRep;
        this.id = id;
        this.dateRes = dateRes;
        this.resolution = resolution;
    }

    public int getIdRep() {
        return idRep;
    }

    public int getId() {
        return id;
    }

    public String getDateRes() {
        return dateRes;
    }

    public String getResolution() {
        return resolution;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateRes(String dateRes) {
        this.dateRes = dateRes;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "reponse{" + "idRep=" + idRep + ", id=" + id + ", dateRes=" + dateRes + ", resolution=" + resolution + '}';
    }

   
  
    
}
