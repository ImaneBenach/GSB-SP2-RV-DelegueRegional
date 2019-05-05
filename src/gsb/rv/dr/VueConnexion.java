/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author etudiant
 */
public class VueConnexion extends Dialog <Pair<String,String>>{
    
    private String matricule ;
    private String mdp ;

    public VueConnexion() {
               
        // Initialiser le titre de la boite de dialogue ainsi que son en-tête
        this.setTitle("Authentification");
        this.setHeaderText("Saisir vos données de connexion");
        
        // Création du formulaire
        final GridPane gridPane = new GridPane(); 
        gridPane.setHgap(6); 
        gridPane.setVgap(6); 
        gridPane.setPadding(new Insets(10,10,10,10)); 
        
        final TextField matriculeTF = new TextField(); 
        matriculeTF.setPromptText("Matricule"); 
        final PasswordField mdpTF = new PasswordField(); 
        mdpTF.setPromptText("Mot de passe"); 
        
        gridPane.add(new Label("Matricule :"), 0, 0); 
        gridPane.add(matriculeTF, 1, 0); 
        gridPane.add(new Label("Mot de passe :"), 0, 1); 
        gridPane.add(mdpTF, 1, 1);  
        
        this.getDialogPane().setContent(gridPane);
 
        //Création des deux boutons
        final ButtonType connexionButtonType = new ButtonType("Se connecter", ButtonData.OK_DONE); 
        final ButtonType annulerButtonType = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE); 
        this.getDialogPane().getButtonTypes().addAll(connexionButtonType, annulerButtonType);
       
        // Ecouteur d'évènements
        
        setResultConverter(
                new Callback<ButtonType, Pair <String,String>>() {
                    @Override
                    public Pair<String, String> call(ButtonType typeButon) {

                        if(typeButon == connexionButtonType){
                            String matricule = matriculeTF.getText() ;
                            String mdp = mdpTF.getText() ;       
                            return new  Pair <String,String> (matricule,mdp);
                        }
                        return null ;

                    }         
                }
        );
        
    }

    
    
}
