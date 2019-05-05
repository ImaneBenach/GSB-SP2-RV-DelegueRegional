/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gsb.rv.dr ;

import gsb.rv.dr.entites.RapportVisite;
import java.time.format.DateTimeFormatter;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 *
 * @author etudiant
 */
public class VueRapport extends Dialog<Pair<String,String>> {

    private RapportVisite leRapport;
    /**
     *
     * @param rv
     */
    public VueRapport(RapportVisite rv){

      

    }
}
