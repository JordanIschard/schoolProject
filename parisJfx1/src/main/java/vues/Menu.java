package vues;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pattern.Observateur;


public class Menu extends Presenter<GridPane> {

    @FXML
    public Hyperlink paris;

    @FXML
    public Hyperlink mesParis;

    @FXML
    public Hyperlink tousLesParis;

    @FXML
    public Button boutonDeconnexion;

    @FXML
    public Button boutonQuitter;

    @FXML
    public Label user;

    @Override
    protected boolean setOptions() {
        return true;
    }

    @Override
    public void show() {
        getStage().setTitle("Menu");
        if ((getMonControleur().getUtilisateur().isAdmin())) {
            user.setText("Bonjour cher admin "+getMonControleur().getUtilisateur().getLogin()+" !");
            tousLesParis.setVisible(true);
        } else {
            user.setText("Bonjour "+getMonControleur().getUtilisateur().getLogin()+" !");
            tousLesParis.setVisible(false);
        }
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        getStage().setResizable(true);
        getStage().show();
    }

    public void goToParisOuverts(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(paris)){
            getMonControleur().goToParisOuverts(getStage());
        }
    }

    public void goToMesParis(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(mesParis)){
            getMonControleur().goToMesParis(getStage());
        }
    }

    public void goToTousLesParis(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(tousLesParis)){
            getMonControleur().goToTousLesParis(getStage());
        }
    }

    public void deconnexion(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(boutonDeconnexion)){
            getMonControleur().deconnexion();
            getMonControleur().goToLogin(getStage());
        }
    }

    public void quitter(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(boutonQuitter)){
            getMonControleur().quitter(getStage());
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {

    }
}
