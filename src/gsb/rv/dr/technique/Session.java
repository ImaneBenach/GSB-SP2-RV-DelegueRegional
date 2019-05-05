/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsb.rv.dr.technique;

import gsb.rv.dr.entites.Visiteur;
import java.util.Objects;

/**
 *
 * @author etudiant
 */
public class Session {
    private static Session session = null ;
    private Visiteur leVisiteur ;

    public Session(Session session) {
        this.session = session;
    }

    public Session() {
    }

    public Session(Visiteur leVisiteur) {
        this.leVisiteur = leVisiteur;
    }

    public Session getSession() {
        return session;
    }

    public Visiteur getLeVisiteur() {
        return leVisiteur;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setLeVisiteur(Visiteur leVisiteur) {
        this.leVisiteur = leVisiteur;
    }
    
    public static void ouvrir(Visiteur leVisiteur){
        Session session = new Session(leVisiteur);
    }
    
    public static void fermer(){
        Session session = null ;
    }
    
     
    public static boolean estOuverte(){
       
        if(session != null){
            return true;
        }
        else {
            return false;
        }
    }
  

    @Override
    public String toString() {
        return "Session{" + "session=" + session + ", leVisiteur=" + leVisiteur + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Session other = (Session) obj;
        if (!Objects.equals(this.session, other.session)) {
            return false;
        }
        if (!Objects.equals(this.leVisiteur, other.leVisiteur)) {
            return false;
        }
        return true;
    }
    
    
}
