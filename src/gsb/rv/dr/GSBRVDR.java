/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import gsb.rv.dr.entites.Praticien;
import gsb.rv.dr.entites.RapportVisite;
import gsb.rv.dr.entites.Visiteur;
import gsb.rv.dr.modeles.ModeleGsbRv;
import gsb.rv.dr.technique.ConnexionBD;
import gsb.rv.dr.technique.ConnexionException;
import gsb.rv.dr.technique.Session;
import gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import gsb.rv.dr.utilitaires.ComparateurDateVisite;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author etudiant
 */
public class GSBRVDR extends Application {
    
    PanneauAccueil vueAccueil ;
    PanneauRapports vueRapports ;
    PanneauPraticiens vuePraticiens ;

    @Override
    public void start(Stage primaryStage) {

        //primaryStage est la fenêtre
        // Créer le conteneur BorderPane(noeud racine du graphe de scène).     
        BorderPane borderPane = new BorderPane();

        // Créer la scène et y associer le noeud racine du graphe de scène.
        Scene scene = new Scene(borderPane, 500, 300);

        // Modifier le titre de la fenêtre
        primaryStage.setTitle("GSB-RV-DR");

        //Associer la scène à la fenêtre
        primaryStage.setScene(scene);

        //Afficher la fenêtre
        primaryStage.show();

        // Création de la barre de menus
        MenuBar barreMenus = new MenuBar();

        // Création du menu
        Menu menuFichier = new Menu("Fichier");
        Menu menuRapports = new Menu("Rapports");
        Menu menuPraticiens = new Menu("Praticiens");

        menuRapports.setDisable(true); // La visibilité
        menuPraticiens.setDisable(true); // La visibilité

        // Création de l'item de menu
        // Pour le menu Fichier
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDéconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");

        itemSeDéconnecter.setDisable(true); // La visibilité
        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN)); // Pour le controle X

        //Pour le menu Rapports
        MenuItem itemConsulter = new MenuItem("Consulter");
        //Pour le menu Praticiens
        MenuItem itemHésitants = new MenuItem("Hésitants");

        // Ajout de l'item de menu dans le menu
        //Pour le menu Fichier
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDéconnecter);
        menuFichier.getItems().add(itemQuitter);
        //Pour le menu Rapports
        menuRapports.getItems().add(itemConsulter);
        //Pour le menu Praticiens
        menuPraticiens.getItems().add(itemHésitants);

        // Ajout du menu dans la barre de menus
        barreMenus.getMenus().add(menuFichier);
        barreMenus.getMenus().add(menuRapports);
        barreMenus.getMenus().add(menuPraticiens);

        // Positionner la barre de menu
        borderPane.setTop(barreMenus);

        // Associer un écouteur d'évènements à chaque menu & item de menu
        menuFichier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        }
        );
        
        ///////////////////////////////////////////////////////////////
        
          menuRapports.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            vueRapports.toFront();

            }
        }
        );
         
       /////////////////////////////////////////////////////////////
       
         menuPraticiens.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            vuePraticiens.toFront();

            }
        }
        );

        //////////////////////////////////////////////////////////
        itemSeConnecter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("Se connecter");
                             
                // Afficher la Vue connexion
                
               VueConnexion vue = new VueConnexion();
               Optional<Pair<String, String>> response = vue.showAndWait();
               if (response.isPresent()) {
                    System.out.println("ok");

                    String matricule = response.get().getKey();
                    String mdp = response.get().getValue();   
                    
                    System.out.println( "> " + matricule ) ;
                    System.out.println( "> " + mdp ) ;
                    
                    try {
                        Visiteur leVisiteur = ModeleGsbRv.seConnecter(matricule, mdp) ;
                        System.out.println( "> " + leVisiteur ) ;
                        if(leVisiteur != null){
                            Session.ouvrir(leVisiteur);
                            menuFichier.setDisable(false);
                            itemSeConnecter.setDisable(true);
                            itemSeDéconnecter.setDisable(false);
                            itemQuitter.setDisable(false);
                            menuRapports.setDisable(false);
                            menuPraticiens.setDisable(false);
                            
                            primaryStage.setTitle(leVisiteur.getNom() + " - " + "GSB-RV-DR" );
                                 
                            
                        }else {
                            System.out.println("echec");
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Echec de connexion");
                            alert.setHeaderText("Erreur sur le matricule ou le mdp");
                            alert.setContentText("Ooops, il y a une erreur!");
                            alert.showAndWait();
                        }
                        
                        
                        
                    } catch (ConnexionException ex) {
                        Logger.getLogger(GSBRVDR.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("echec");
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Crash BD");
                        alert.setHeaderText("");
                        alert.setContentText("");
                        alert.showAndWait();
                    }
                    
                    
                }
               
            }
        }
        );
        ////////////////////////////////////////////////////////////

        itemSeDéconnecter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Se déconnecter"); 
                
                menuFichier.setDisable(false);
                itemSeConnecter.setDisable(false);
                itemSeDéconnecter.setDisable(true);
                itemQuitter.setDisable(false);
                menuRapports.setDisable(true);
                menuPraticiens.setDisable(true);
                primaryStage.setTitle("GSB-RV-DR");
                
                vueAccueil.toFront();
                            

            }
        }
        );

        /////////////////////////////////////////////////////////////
        itemQuitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Quitter");
                //Création de l'alerte : boite de dialogue de confirmation
                Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                alertQuitter.setTitle("Quitter");
                alertQuitter.setHeaderText("Demande de confirmation");
                alertQuitter.setContentText("Voulez-vous quitter l'application?");
                //Création des boutons
                ButtonType btnOui = new ButtonType("Oui");
                ButtonType btnNon = new ButtonType("Non");
                alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
                //Traiter la réponse retournée par la boite de dialogue
                Optional<ButtonType> result = alertQuitter.showAndWait();
                if (result.get() == btnOui) {
                    primaryStage.close();
                } else {
                    alertQuitter.close();
                }
            }
        }
        );

        //////////////////////////////////////////////////////////////
        itemConsulter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("[Rapports] + Oumayma Bellili"); 
                 vueRapports.toFront();

            }
        }
        );

        //////////////////////////////////////////////////////////////////
        itemHésitants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("[Rapports] + Oumayma Bellili"); 
                vuePraticiens.rafraichir();
                vuePraticiens.toFront();

            }
        }
        );
        
        // Création  de la pile de panneaux
         
       vueAccueil = new PanneauAccueil();
       vueRapports = new PanneauRapports();
       vuePraticiens = new PanneauPraticiens();
       
       StackPane stackPane = new StackPane ();
       stackPane.getChildren().addAll(vueAccueil,vueRapports,vuePraticiens);
       vueAccueil.toFront();
       
       borderPane.setCenter(stackPane);
 
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ConnexionException {

        //System.out.println( "> " + ConnexionBD.getConnexion() ) ;
        //System.out.println( ModeleGsbRv.seConnecter("b25", "azerty") ) ;
       
        
        //test de la methode getPraticiensHesitants de la classe ModelGsbRv
        
        List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants() ;
       
        for( Praticien unPraticien : praticiens) {
            System.out.println( unPraticien);
        }
        /*
        // tester ComparateurCoefConfiance
        
        Collections.sort( praticiens, new ComparateurCoefConfiance() );
               
        for( Praticien unPraticien : praticiens ) {
            System.out.println( unPraticien );
        }
        
       // tester ComparateurCoefNotoriete
      
       Collections.sort( praticiens, new ComparateurCoefNotoriete() );
               
        for( Praticien unPraticien : praticiens ) {
            System.out.println( unPraticien );
        }
        
       
     // tester ComparateurDateVisite
       
       Collections.sort( praticiens, new ComparateurDateVisite() );
               
        for( Praticien unPraticien : praticiens ) {
            System.out.println( unPraticien );
        }
           

         //test de la methode getVisiteurs de la classe ModelGsbRv
        List<Visiteur> visiteurs = ModeleGsbRv.getVisiteurs() ;
       
        for( Visiteur unVisiteur : visiteurs) {
            System.out.println(unVisiteur);
        }

        //test de la methode getRapportsVisite de la classe ModelGsbRv
        List<RapportVisite> rapportsVisite = ModeleGsbRv.getRapportsVisite("c14", 3, 2018) ;
       
        for( RapportVisite unRapportVisite : rapportsVisite) {
            System.out.println(unRapportVisite);
        }
       
       
         //test de la methode getRapportsVisite de la classe ModelGsbRv
        List<RapportVisite> rapportsVisiteLu = ModeleGsbRv.setRapportVisiteLu("c14", 1) ;
       
        for( RapportVisite unRapportVisite : rapportsVisiteLu) {
            System.out.println(unRapportVisite);
        }
        
        */
        
         launch(args);

    }

}
