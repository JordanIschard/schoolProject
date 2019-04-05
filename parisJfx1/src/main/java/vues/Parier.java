package vues;

import facade.exceptions.MatchClosException;
import facade.exceptions.ResultatImpossibleException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import modele.Match;
import pattern.Observateur;

import java.time.LocalDate;

public class Parier extends Presenter<GridPane> {

    @FXML
    public TextField sportA;

    @FXML
    public TextField equipe1A;

    @FXML
    public TextField equipe2A;

    @FXML
    public Button boutonParier;

    @FXML
    public Button boutonQuitter;

    @FXML
    public Label erreur;

    @FXML
    public ChoiceBox<String> vainqueur;

    @FXML
    public DatePicker quandA;

    @FXML
    public TextField mise;


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
        vainqueur.getItems().addAll(getMonControleur().getVainqueurs(m));
    }

    @Override
    public void show() {
        getStage().setTitle("Parier");
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        setChamps();
        getStage().setResizable(true);
        getStage().show();
    }

    public void setId(long id){
        this.idMatch = id;
    }

    public void parier(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonParier)){
            try {
                getMonControleur().parier(idMatch,mise.getText(),vainqueur.getValue());
                getMonControleur().quitterParier(getStage());
            } catch (MatchClosException e) {
                erreur.setVisible(true);
                erreur.setText("Match clos !");
            } catch (ResultatImpossibleException e) {
                erreur.setVisible(true);
                erreur.setText("Résultat impossible !");
            } catch (NumberFormatException e) {
                erreur.setVisible(true);
                erreur.setText("Le format de la mise est erronée");
            }
        }
    }

    public void quitter(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonQuitter)){
            getMonControleur().quitterParier(getStage());
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {
        observateur.notifier(o);
    }
}
