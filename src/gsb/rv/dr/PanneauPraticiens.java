/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr;

import gsb.rv.dr.entites.Praticien;
import gsb.rv.dr.modeles.ModeleGsbRv;
import gsb.rv.dr.technique.ConnexionException;
import gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import gsb.rv.dr.utilitaires.ComparateurDateVisite;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 *
 * @author etudiant
 */
public class PanneauPraticiens extends Pane {
    
    public static int CRITERE_COEF_CONFIANCE = 1 ;
    public static int CRITERE_COEF_NOTORIETE = 2 ;
    public static int CRITERE_DATE_VISITE = 3 ; 
    
    private int critereTri = CRITERE_COEF_CONFIANCE ;
    private RadioButton rbCoefConfiance = new RadioButton("Confiance") ;
    private RadioButton rbCoefNotoriete = new RadioButton("Notoriete")  ;
    private RadioButton rbDateVisite = new RadioButton("Date Visite") ;
    
    private TableView<Praticien> tabPraticiens = new TableView() ;

    public PanneauPraticiens() {
        super() ;
        // Boite verticale (VBox) avec le Label
        this.setStyle("-fx-background-color: white");
        VBox root = new VBox() ;
        Label selectionTri = new Label ("Sélectionner un critère de tri :");
        selectionTri.setStyle("-fx-font-weight: bold");
        root.getChildren().add(selectionTri) ;
        this.getChildren().add( root ) ;

        
       // La grille (GridPane) avec les radios boutons.     
       
        GridPane gp = new GridPane();
       
        ToggleGroup tg = new ToggleGroup();
        
        rbCoefConfiance.setToggleGroup(tg);
        rbCoefConfiance.setSelected(true);
        rbCoefNotoriete.setToggleGroup(tg);
        rbDateVisite.setToggleGroup(tg);
        
        HBox box = new HBox(20, rbCoefConfiance,rbCoefNotoriete,rbDateVisite);
        
        gp.add(box,10,10);        
        root.getChildren().add(gp);
       
        // Création des colonnes de la tb
        TableColumn<Praticien, Integer> numero = new TableColumn<Praticien,Integer>("Numéro"); 
        TableColumn<Praticien, String> nom = new TableColumn<Praticien,String>("Nom"); 
        TableColumn<Praticien, String> ville = new TableColumn<Praticien,String>("Ville"); 
        
        tabPraticiens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        
        tabPraticiens.getColumns().addAll(numero,nom,ville);
        root.getChildren().add(tabPraticiens);

        // Ecouteurs d'évènements
        rbCoefConfiance.setOnAction(
        new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    // Mémorisation du critère de tri selectionné
                    critereTri = CRITERE_COEF_CONFIANCE ;
                    //Rafraichissement de la table
                    rafraichir();
                   
                }
        } 
   ) ;       
        
      rbCoefNotoriete.setOnAction(
      new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    // Mémorisation du critère de tri selectionné
                    critereTri = CRITERE_COEF_NOTORIETE ;
                    //Rafraichissement de la table
                    rafraichir();
                   
                }
        } 
   ) ;    
      
       rbDateVisite.setOnAction(
        new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {               
                    // Mémorisation du critère de tri selectionné
                     critereTri = CRITERE_DATE_VISITE ;
                    //Rafraichissement de la table
                    rafraichir();
                }
        } 
   ) ;           
}
      public void rafraichir(){
        try {
            // Obtenir la liste des praticiens 
            List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants() ;
            
            // Traitements spécifiques aux tris.
            if(critereTri == CRITERE_COEF_CONFIANCE){
                Collections.sort( praticiens, new ComparateurCoefConfiance() );
            }
            
            else if (critereTri == CRITERE_COEF_NOTORIETE){
                Collections.sort( praticiens, new ComparateurCoefNotoriete() );
                Collections.reverse(praticiens);
            }
            
            else {
                Collections.sort( praticiens, new ComparateurDateVisite() );
                Collections.reverse(praticiens);
            }
            
           //Convertir la liste des praticiens hésitants en une liste observable
            ObservableList<Praticien> ob = FXCollections.observableArrayList(praticiens) ; 
            tabPraticiens.getItems().clear(); 
            tabPraticiens.getItems().addAll(ob) ;
            
        } catch (ConnexionException ex) {
            Logger.getLogger(PanneauPraticiens.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCritereTri() {
        return critereTri;
    }

    public void setCritereTri(int critereTri) {
        this.critereTri = critereTri;
    }
    
  
    
}
