package controleur;

import exception.LoadOptionsException;
import facade.FacadeParis;
import facade.exceptions.*;
import javafx.stage.Stage;
import modele.Match;
import modele.Pari;
import modele.Utilisateur;
import path.Path;
import pattern.Observateur;
import vues.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controleur extends Observateur {

    private Presenter fenetre;

    private Presenter ajouter;

    private Presenter parier;

    private Presenter resultat;

    private FacadeParis facade;

    private Utilisateur utilisateur;

    public Controleur(Stage primaryStage, FacadeParis facadeParis) {
        facade = facadeParis;
        try {
            fenetre = Login.creerInstance(this,primaryStage,Path.LOGIN);
            addSujet(fenetre);
            fenetre.show();
        } catch (LoadOptionsException e) {
            Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page login ont subi un problème");
        }

    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Collection<Match> getParisOuverts(){
        return  facade.getMatchsPasCommences();
    }

    public Collection<Pari> getMesParis(){
        return facade.getMesParis(utilisateur.getLogin());
    }

    public Match getMatch(long idMatch) {
        Match m = facade.getMatch(idMatch);
        System.out.println(m);
        return m;
    }

    public Collection<Match> getTousLesParis() { return facade.getAllMatchs(); }

    public Collection<String> getVainqueurs(Match m) {
        Collection<String> c = new ArrayList<>();
        c.add("nul");
        c.add(m.getEquipe1());
        c.add(m.getEquipe2());

        return c;
    }

    public void goToLogin(Stage stage) {
        try {
            deleteSujet(fenetre);
            fenetre = Login.creerInstance(this,stage,Path.LOGIN);
            addSujet(fenetre);
            fenetre.show();
        } catch (LoadOptionsException e) {Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page login ont subi un problème"); }
    }

    public void goToMenu(Stage stage) {
        try {
            deleteSujet(fenetre);
            fenetre = Menu.creerInstance(this,stage,Path.MENU);
            addSujet(fenetre);
            fenetre.show();
        } catch (LoadOptionsException e) {Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page menu ont subi un problème"); }
    }

    public void goToParisOuverts(Stage stage){
        try{
            deleteSujet(fenetre);
            if(utilisateur.isAdmin){
                fenetre = ParisOuverts.creerInstance(this,stage,Path.PARIS_OUVERTS_ADMIN);
            }else{
                fenetre = ParisOuverts.creerInstance(this,stage,Path.PARIS_OUVERTS);
            }
            addSujet(fenetre);
            fenetre.show();
        } catch (LoadOptionsException e) { Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page paris ouverts ont subi un problème"); }
    }

    public void goToMesParis(Stage stage) {
        try{
            deleteSujet(fenetre);
            if(utilisateur.isAdmin){
                fenetre = MesParis.creerInstance(this,stage,Path.MES_PARIS_ADMIN);
            }else{
                fenetre = MesParis.creerInstance(this,stage,Path.MES_PARIS);
            }
            addSujet(fenetre);
            fenetre.show();
        } catch (LoadOptionsException e) { Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page mes paris ont subi un problème"); }
    }

    public void goToTousLesParis(Stage stage) {
        try{
            deleteSujet(fenetre);
            fenetre = TousLesParis.creerInstance(this,stage,Path.TOUS_LES_PARIS);
            addSujet(fenetre);
            fenetre.show();
        } catch (LoadOptionsException e) { Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page tous les paris ont subi un problème"); }
    }

    public void connexion(String user, String pwd) throws InformationsSaisiesIncoherentesException, UtilisateurDejaConnecteException {
        utilisateur = facade.connexion(user, pwd);
    }

    public void goToAjouter() {
        try{
            ajouter = Ajouter.creerInstance(this,new Stage(),Path.AJOUTER_ADMIN);
            addSujet(ajouter);
            ajouter.show();
        } catch (LoadOptionsException e) { Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page tous les paris ont subi un problème"); }
    }

    public void goToParier(long idMatch) {
        try{
            parier = Parier.creerInstance(this,new Stage(),Path.PARIER);
            ((Parier)parier).setId(idMatch);
            addSujet(parier);
            parier.show();
        } catch (LoadOptionsException e) { Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page tous les paris ont subi un problème"); }
    }

    public void goToResultat(long idMatch) {
        try{
             resultat = Resultat.creerInstance(this,new Stage(),Path.RESULTAT);
            ((Resultat)resultat).setId(idMatch);
            addSujet(resultat);
            resultat.show();
        } catch (LoadOptionsException e) { Logger.getLogger(String.valueOf(Level.INFO),"Les options à charger pour la page tous les paris ont subi un problème"); }
    }

    public void deconnexion() {
        facade.deconnexion(utilisateur.getLogin());
    }

    public void quitter(Stage stage) {
        deleteAllSujet();
        stage.close();
    }

    public void quitterAjouter(Stage stage) {
        deleteSujet(ajouter);
        stage.close();
    }

    public void quitterParier(Stage stage) {
        deleteSujet(parier);
        stage.close();
    }

    public void quitterResultat(Stage stage) {
        deleteSujet(resultat);
        stage.close();
    }

    public void ajouterMatch(String sport, String equipe1, String equipe2, LocalDateTime date) throws UtilisateurNonAdminException {
        facade.ajouterMatch(utilisateur.getLogin(),sport,equipe1,equipe2,date);
    }

    public void parier(long idMatch, String mise, String vainqueur) throws MatchClosException, ResultatImpossibleException, NumberFormatException {
        facade.parier(utilisateur.getLogin(),idMatch,vainqueur,Double.valueOf(mise));
    }

    public void donnerRes(long idMatch, String value) throws UtilisateurNonAdminException, ResultatImpossibleException {
        facade.resultatMatch(utilisateur.getLogin(),idMatch,value);
    }

    public void supprimer(long idMatch) throws OperationNonAuthoriseeException {
        facade.annulerPari(utilisateur.getLogin(),idMatch);
    }
}
