package vues;

import facade.exceptions.UtilisateurNonAdminException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pattern.Observateur;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Ajouter extends Presenter<GridPane> {

    @FXML
    public TextField sportA;

    @FXML
    public TextField equipe1A;

    @FXML
    public TextField equipe2A;

    @FXML
    public Label erreur;

    @FXML
    public Label succes;

    @FXML
    public DatePicker quandA;

    @FXML
    public Button boutonAjouter;

    @FXML
    public Button boutonQuitter;

    @Override
    protected boolean setOptions() {
        return true;
    }



    @Override
    public void show() {
        getStage().setTitle("Ajouter");
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        erreur.setVisible(false);
        succes.setVisible(false);
        getStage().setResizable(true);
        getStage().show();
    }

    public void ajouter(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonAjouter)){
            if(champsRempli()){
                try {
                    LocalDateTime date = LocalDateTime.of(quandA.getValue(), LocalTime.now());
                    getMonControleur().ajouterMatch(sportA.getText(),equipe1A.getText(),equipe2A.getText(),date);
                    sportA.setText("");
                    equipe1A.setText("");
                    equipe2A.setText("");
                    erreur.setVisible(false);
                    succes.setVisible(true);
                    succes.setText("Ajout réussi !");
                    maj(getMonControleur(),this);
                } catch (UtilisateurNonAdminException e) {
                    succes.setVisible(false);
                    erreur.setVisible(true);
                    erreur.setText("Vous n'êtes pas admin !");
                }
            }else{
                succes.setVisible(false);
                erreur.setVisible(true);
                erreur.setText("Champs vide");
            }
        }
    }

    private boolean champsRempli(){
        return !(sportA.getText().isEmpty()
                || equipe1A.getText().isEmpty()
                || equipe2A.getText().isEmpty()
                || quandA.getValue().toString().isEmpty());
    }

    public void quitter(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonQuitter)){
            getMonControleur().quitterAjouter(getStage());
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {
        observateur.notifier(o);
    }
}
