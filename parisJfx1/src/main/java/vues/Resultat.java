package vues;

import facade.exceptions.ResultatImpossibleException;
import facade.exceptions.UtilisateurNonAdminException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import modele.Match;
import pattern.Observateur;

import java.time.LocalDate;

public class Resultat extends Presenter<GridPane> {

    @FXML
    public TextField sportA;

    @FXML
    public TextField equipe1A;

    @FXML
    public TextField equipe2A;

    @FXML
    public Button boutonResultat;

    @FXML
    public Button boutonQuitter;

    @FXML
    public Label erreur;

    @FXML
    public ChoiceBox<String> resultat;

    @FXML
    public DatePicker quandA;

    private long idMatch;

    @Override
    protected boolean setOptions() {
        erreur.setVisible(false);
        return true;
    }

    private void setChamps(){
        Match m = getMonControleur().getMatch(idMatch);
        sportA.setText(m.getSport());
        equipe1A.setText(m.getEquipe1());
        equipe2A.setText(m.getEquipe2());
        quandA.setValue(LocalDate.from(m.getQuand()));
        resultat.getItems().addAll(getMonControleur().getVainqueurs(m));
    }


    @Override
    public void show() {
        getStage().setTitle("Résultat");
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        setChamps();
        getStage().setResizable(true);
        getStage().show();
    }

    @Override
    public void maj(Observateur observateur, Object o) {
        observateur.notifier(o);
    }

    public void quitter(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonQuitter)){
            getMonControleur().quitterResultat(getStage());
        }
    }

    public void donnerRes(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonResultat)){
            try {
                getMonControleur().donnerRes(idMatch,resultat.getValue());
                getMonControleur().quitterResultat(getStage());
                maj(getMonControleur(),this);
                erreur.setVisible(false);
            } catch (UtilisateurNonAdminException e) {
                erreur.setVisible(true);
                erreur.setText("Vous n'êtes pas admin !");
            } catch (ResultatImpossibleException e) {
                erreur.setVisible(true);
                erreur.setText("Résultat impossible !");
            }
        }
    }

    public void setId(long idMatch) {
        this.idMatch = idMatch;
    }
}
