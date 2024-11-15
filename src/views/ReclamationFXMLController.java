/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Service.ReclamationService;
import dataSource.DataSource;
import entities.reclamation;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Koussay
 */
public class ReclamationFXMLController implements Initializable {

   
    @FXML
    private TextArea dateRec;
    @FXML
    private TextArea idRec;
    @FXML
    private TextArea etatRec;
    @FXML
    private TextArea descRec;
    @FXML
    private TextArea nomRec;
    @FXML
    private Button ajouterRec;
    @FXML
    private Button supprimerRec;
    @FXML
    private Button modifierRec;
    @FXML
    private Button fermerRec;
    
    
    ObservableList<reclamation>recList;
    
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
  
  public static boolean isLeapYear(int year) {
    if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
      return true;
    }
    return false;
  }
    @FXML
    private TextArea rechercheRec;
    @FXML
    private ListView<reclamation> listViewRec;
    
     
    public static boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$";
    Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
}

 private boolean checkUserExistence(String nom, String prenom) {
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thnity", "root", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM reservation_bus WHERE nom='" + nom + "' AND prenom='" + prenom + "'");
        if (rs.next()) {
            return true;
        }
        conn.close();
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la vérification de l'existence de l'utilisateur : " + ex.getMessage());
    }
    return false;
}



    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          afficherReclamations();
    }    
    
     void afficherReclamations()
      {
          mc=DataSource.getInstance().getCnx();
          recList = FXCollections.observableArrayList();
   
        
        try {
            String requete = "SELECT * FROM reclamation r order by dateRec desc";
            Statement st = DataSource.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                reclamation r = new reclamation();
                r.setId(rs.getInt("id"));
                r.setDateRec(rs.getString("dateRec"));
                 r.setDescription(rs.getString("description"));
                r.setNom(rs.getString("nom"));
                r.setEtat(rs.getBoolean("etat"));
                
                
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
       listViewRec.setItems(recList);
    
    
    listViewRec.setCellFactory(new Callback<ListView<reclamation>, ListCell<reclamation>>() {
    @Override
    public ListCell<reclamation> call(ListView<reclamation> listView) {
        return new ListCell<reclamation>() {
            @Override
            protected void updateItem(reclamation item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getDateRec()+ "            " + item.getDescription()+"             "+item.getNom() +"          "+item.isEtat());
                }
            }
        };
    }
});

    search();
    }
        
      
    
    
    
    
    
    
     public void refresh(){
        
         recList.clear();
       
          
          mc=DataSource.getInstance().getCnx();

        recList = FXCollections.observableArrayList();
        
        String sql="select * from reclamation";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs=ste.executeQuery();
            while(rs.next()){
                reclamation e = new reclamation();
                e.setId(rs.getInt("id"));
                e.setDateRec(rs.getString("dateRec"));
                 e.setDescription(rs.getString("description"));
                e.setNom(rs.getString("nom"));
                e.setEtat(rs.getBoolean("etat"));
                
                
                
                recList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
         listViewRec.setItems(recList);   
      
       
         
    listViewRec.setCellFactory(new Callback<ListView<reclamation>, ListCell<reclamation>>() {
    @Override
    public ListCell<reclamation> call(ListView<reclamation> listView) {
        return new ListCell<reclamation>() {
            @Override
            protected void updateItem(reclamation item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getDateRec()+ "            " + item.getDescription()+"             "+item.getNom() +"          "+item.isEtat());
                }
            }
        };
    }
});   search();
       
    }
    

    @FXML
    private void addRec(MouseEvent event) {
       String date = dateRec.getText();// bch te5ou text mawjoud f label w thotou f variable  
        String description = descRec.getText();
         String nom = nomRec.getText();
        String etat = etatRec.getText();
      
        if (date.isEmpty() || description.isEmpty()|| nom.isEmpty()|| etat.isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Veuiller Remplir Les champs!!"); 
             alert.showAndWait();    
              Notifications.create()
                .title("Erreur")
                .text("Une erreur est survenue lors de l'ajout de la réclamation.")
                .showError();
         }else if (isValidDate(date)==false)
         {
         Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Date non valide"); 
             alert.showAndWait();
              Notifications.create()
                .title("Erreur")
                .text("Une erreur est survenue lors de l'ajout de la réclamation.")
                .showError();
         } 
         
      
         else{ 
        
         reclamation r=new reclamation(1,date,description,nom,Boolean.parseBoolean(etat));
             ReclamationService rc = new ReclamationService();
             
             //ResultSet rs=ste.executeQuery();        
             rc.ajouterReclamation(r);
              Notifications.create()
                .title("Réclamation ajoutée")
                .text("La réclamation a été ajoutée avec succès.")
                .showInformation();
             
             
             dateRec.setText(null);
             descRec.setText(null);
             nomRec.setText(null);
             etatRec.setText(null);
             
             
    
        refresh();
        }
    }

    @FXML
    private void deleteRec(MouseEvent event) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Es-tu sûre de supprimer!");
       
            
        String value1 = idRec.getText(); 
        String date = dateRec.getText();// bch te5ou text mawjoud f label w thotou f variable  
        String description = descRec.getText();
         String nom = nomRec.getText();
        String etat = etatRec.getText();
      
       
        Optional<ButtonType>result =  alert.showAndWait(); 
        if(result.get() == ButtonType.OK){ 
         
            reclamation r= new reclamation(Integer.parseInt(value1),date,description,nom,Boolean.parseBoolean(etat));
            
         ReclamationService rc = new ReclamationService();
             rc.supprimerReclamation(r);
        
             refresh();}
        
          dateRec.setText(null);
             descRec.setText(null);
             nomRec.setText(null);
             etatRec.setText(null);
        
    }

    @FXML
    private void updateRec(MouseEvent event) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Es-tu sûre de supprimer!");
       
            
        String value1 = idRec.getText(); 
        String date = dateRec.getText();// bch te5ou text mawjoud f label w thotou f variable  
        String description = descRec.getText();
         String nom = nomRec.getText();
        String etat = etatRec.getText();
      
       
        Optional<ButtonType>result =  alert.showAndWait(); 
        if(result.get() == ButtonType.OK){ 
         
            reclamation r= new reclamation(Integer.parseInt(value1),date,description,nom,Boolean.parseBoolean(etat));
            
         ReclamationService rc = new ReclamationService();
             rc.modifierReclamation(r);
         dateRec.setText(null);
             descRec.setText(null);
             nomRec.setText(null);
             etatRec.setText(null);
             refresh();}
        

    }

    @FXML
    private void closeRec(MouseEvent event) {
         Stage stage = (Stage) fermerRec.getScene().getWindow();
    stage.close();
    }
    
    
     private void search() {      
        
        FilteredList<reclamation>filteredData = new FilteredList<>(recList, b->true);
        rechercheRec.textProperty().addListener((observable, oldValue, newValue)->{
           
            filteredData.setPredicate(Reclamation->{
               
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
               
                String lowerCaseFilter = newValue.toLowerCase();
                 
                  if(String.valueOf(Reclamation.getDescription()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                  else if(String.valueOf(Reclamation.getDateRec()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else{
                return false;
                }
            });          
        });
        SortedList<reclamation>sortedData = new SortedList<>(filteredData);
    //  sortedData.comparatorProperty().bind(listViewRec.comparatorProperty());
        listViewRec.setItems(sortedData);
    }

    @FXML
    private void selectRecList(MouseEvent event) {
        
        
        reclamation clicked = listViewRec.getSelectionModel().getSelectedItem();
        if(clicked != null){
            
         idRec.setText(String.valueOf(clicked.getId()));
         dateRec.setText(clicked.getDateRec());
        descRec.setText(clicked.getDescription());
         nomRec.setText(clicked.getNom());
        etatRec.setText(Boolean.toString(clicked.isEtat()));
       
    }
    
}
}