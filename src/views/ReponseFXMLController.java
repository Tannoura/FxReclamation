/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Service.ReponseService;
import dataSource.DataSource;
import entities.reponse;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import static views.ReclamationFXMLController.isLeapYear;

/**
 * FXML Controller class
 *
 * @author Koussay
 */
public class ReponseFXMLController implements Initializable {

   
    @FXML
    private TextArea idRep;
    @FXML
    private TextArea dateRep;
    @FXML
    private ComboBox<Integer> comboRec;
    @FXML
    private Button ajouterRep;
    @FXML
    private Button SupprimerRep;
    @FXML
    private Button modifierRep;
    @FXML
    private Button fermerRep;
    
    ObservableList<reponse>repList;
    Connection mc;
    PreparedStatement ste;
    
     
    
      public static boolean isValidDate(String dateString) {
    String[] dateParts = dateString.split("/");
    if (dateParts.length != 3) {
      return false;
    }
    
    int day;
    int month;
    int year;
    try {
      day = Integer.parseInt(dateParts[0]);
      month = Integer.parseInt(dateParts[1]);
      year = Integer.parseInt(dateParts[2]);
    } catch (NumberFormatException e) {
      return false;
    }
    
    if (month < 1 || month > 12) {
      return false;
    }
    if (year <2023){return false;}
    
    int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int maxDays = daysInMonth[month - 1];
    if (month == 2 && isLeapYear(year)) {
      maxDays = 29;
    }
    
    if (day < 1 || day > maxDays) {
      return false;
    }
    
    return true;
  }
    @FXML
    private ListView<reponse> listViewRep;
    @FXML
    private ComboBox<String> resou_comb;
    @FXML
    private TextArea rechtxt;
    @FXML
    private Button recherchebtn;
    @FXML
    private Button tribtn;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resou_comb.getItems().addAll("abouti", "non abouti");
        mc=DataSource.getInstance().getCnx();
       try {
           
             String requete = "SELECT id FROM reclamation r ";
            Statement st = DataSource.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            ObservableList<Integer> id = null;
            List<Integer> list = new ArrayList<>();
            while(rs.next()){
                
             list.add(rs.getInt("id"));
                
            }
            id = FXCollections
                    .observableArrayList(list);
            comboRec.setItems(id);
        } catch (SQLException ex) {
            Logger.getLogger(ReponseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        afficherReponses();
    }    
void afficherReponses()
      {
          mc=DataSource.getInstance().getCnx();
          repList = FXCollections.observableArrayList();
   
        
        try {
            String requete = "SELECT * FROM reponse r ";
            Statement st = DataSource.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                reponse r = new reponse();
                r.setIdRep(rs.getInt("idRep"));
                r.setId(rs.getInt("id"));
                r.setDateRes(rs.getString("dateRes"));
                r.setResolution(rs.getString("resolution"));
               
                repList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        listViewRep.setItems(repList); 
        listViewRep.setCellFactory(new Callback<ListView<reponse>, ListCell<reponse>>() {
    @Override
    public ListCell<reponse> call(ListView<reponse> listView) {
        return new ListCell<reponse>() {
            @Override
            protected void updateItem(reponse item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText("La date de res est " +item.getDateRes()+ "            La Reclamation est " + item.getId()+"      l'id reponse est:       "+item.getIdRep() + "   La reclmamation est :   "+item.getResolution());
                }
            }
        };
    }
});   
        
      }
    
public void refresh(){
        
         repList.clear();
       
          
          mc=DataSource.getInstance().getCnx();

        repList = FXCollections.observableArrayList();
        
        String sql="select * from reponse";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                reponse e = new reponse();
                
                e.setIdRep(rs.getInt("idRep"));
                e.setId(rs.getInt("id"));
                e.setDateRes(rs.getString("dateRes"));
             e.setResolution(rs.getString("resolution"));
  
   
                repList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
          listViewRep.setItems(repList); 
        listViewRep.setCellFactory(new Callback<ListView<reponse>, ListCell<reponse>>() {
    @Override
    public ListCell<reponse> call(ListView<reponse> listView) {
        return new ListCell<reponse>() {
            @Override
            protected void updateItem(reponse item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                   setText("La date de res est " +item.getDateRes()+ " La Reclamation est " + item.getId()+"     l'id reponse est:   "+item.getIdRep() + "   La reclmamation est :   "+item.getResolution());
                }
            }
        };
    }
});   
         
    }
    
    
    
    
  
    
   

    @FXML
    private void addRep(MouseEvent event) {
        
        String idRec = comboRec.getSelectionModel().getSelectedItem().toString();
         String date = dateRep.getText();
         String resolution = resou_comb.getValue();
        
      if (isValidDate(date)==false){
           Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Date non valide"); 
             alert.showAndWait();
       
      }else{
          reponse r=new reponse(1,Integer.parseInt(idRec),date,resolution);
             ReponseService rc = new ReponseService();      
             rc.ajouterReponseRec(r);
             refresh(); 
             
             comboRec.setValue(null);
             dateRep.setText(null);
    resou_comb.setValue(null);
    }}

    @FXML
    private void deleteRep(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Es-tu sûre de supprimer!");
        
        String id_rep =  idRep.getText();
        String idrec = comboRec.getSelectionModel().getSelectedItem().toString();
         String date = dateRep.getText();
          String resolution = resou_comb.getValue();          
        Optional<ButtonType>result =  alert.showAndWait(); 
        if(result.get() == ButtonType.OK){ 
         
         reponse r= new reponse(Integer.parseInt(id_rep),Integer.parseInt(idrec),date,resolution);
            
         ReponseService rc = new ReponseService();
          rc.supprimerReponseRec(r);
        
             refresh();
             comboRec.setValue(null);
             dateRep.setText(null);
             resou_comb.setValue(null);
        }
    }

    @FXML
    private void updateRep(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Es-tu sûre de supprimer!");
        
        String id_rep =  idRep.getText();
        String idrec = comboRec.getSelectionModel().getSelectedItem().toString();
         String date = dateRep.getText();
          String resolution = resou_comb.getValue().toString();
          
        Optional<ButtonType>result =  alert.showAndWait(); 
        if(result.get() == ButtonType.OK){ 
         
         reponse r= new reponse(Integer.parseInt(id_rep),Integer.parseInt(idrec),date,resolution);
            
         ReponseService rc = new ReponseService();
          rc.modifierReponseRec(r);
        
             refresh();
             comboRec.setValue(null);
             dateRep.setText(null);
             resou_comb.setValue(null);
        }
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) fermerRep.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void selectListRep(MouseEvent event) {
           reponse clicked = listViewRep.getSelectionModel().getSelectedItem();
   
           if(clicked!=null){
           comboRec.setValue(clicked.getId());
          idRep.setText(String.valueOf(clicked.getIdRep()));
        dateRep.setText(String.valueOf(clicked.getDateRes()));
        resou_comb.setValue(String.valueOf(clicked.getResolution()));
           }
    }

    @FXML
    private void recherchersignal(MouseEvent event) {
   
        String rech = rechtxt.getText();
if (rech.trim().isEmpty()) {
    refresh();
    return;
}
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       
try {
     dateFormat.setLenient(false);
        dateFormat.parse(rech);

    
   String requete = "SELECT * FROM Reponse WHERE DateRes LIKE '%" + rech + "%' ORDER BY DateRes ASC";
   Statement st = DataSource.getInstance().getCnx().createStatement();
   ResultSet rs = st.executeQuery(requete); 
   listViewRep.getItems().clear();
   while (rs.next()) {
        Integer idRep = rs.getInt("idRep");
        Integer id = rs.getInt("id");
        String dat = rs.getString("DateRes");
        String res = rs.getString("resolution");
        reponse rep = new reponse(idRep, id, dat, res);
        listViewRep.getItems().add(rep);
   }
   rs.close();
} catch (SQLException e) {
     Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Date non trouve"); 
             alert.showAndWait();
              Notifications.create()
                .title("Erreur")
                .text("Une erreur est survenue .").showError();
}       catch (ParseException ex) {  
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Date non trouve"); 
             alert.showAndWait();
              Notifications.create()
                .title("Erreur")
                .text("Une erreur est survenue .").showError();
        }  

        


    
    }

    @FXML
    private void trisignal(MouseEvent event) {
   
          mc=DataSource.getInstance().getCnx();
          repList = FXCollections.observableArrayList();
   
        
        try {
            String requete = "SELECT * FROM reponse r ORDER BY dateRes DESC;";
            Statement st = DataSource.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                reponse r = new reponse();
                r.setIdRep(rs.getInt("idRep"));
                r.setId(rs.getInt("id"));
                r.setDateRes(rs.getString("dateRes"));
                r.setResolution(rs.getString("resolution"));
               
                repList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        listViewRep.setItems(repList); 
        listViewRep.setCellFactory(new Callback<ListView<reponse>, ListCell<reponse>>() {
    @Override
    public ListCell<reponse> call(ListView<reponse> listView) {
        return new ListCell<reponse>() {
            @Override
            protected void updateItem(reponse item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText("La date de res est " +item.getDateRes()+ "            La Reclamation est " + item.getId()+"      l'id reponse est:       "+item.getIdRep() + "   La reclmamation est :   "+item.getResolution());
                }
            }
        };
    }
});   
      

        
}
    


}
