package vues;

import facade.exceptions.InformationsSaisiesIncoherentesException;
import facade.exceptions.UtilisateurDejaConnecteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pattern.Observateur;

public class Login extends Presenter<GridPane> {

    @FXML
    public TextField user;

    @FXML
    public PasswordField pwd;

    @FXML
    public Button boutonConnexion;

    @FXML
    public Label erreur;

    @FXML
    public Button boutonQuitter;

    @Override
    protected boolean setOptions() {
        return true;
    }

    @Override
    public void show() {
        getStage().setTitle("Connexion");
        erreur.setVisible(false);
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        getStage().setResizable(true);
        getStage().show();
    }

    public void connexion(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonConnexion)){
            if(user.getText().isEmpty()
            || pwd.getText().isEmpty()){
                erreur.setText("Champs vide");
                erreur.setVisible(true);
            }else{
                try {
                    getMonControleur().connexion(user.getText(),pwd.getText());
                    getMonControleur().goToMenu(getStage());
                } catch (InformationsSaisiesIncoherentesException e) {
                    erreur.setText("Informations incohérentes");
                    erreur.setVisible(true);
                } catch (UtilisateurDejaConnecteException e) {
                    erreur.setText("Utilisateur déjà connecté");
                    erreur.setVisible(true);
                }
            }
        }
    }

    public void quitter(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonQuitter)){
            getMonControleur().quitter(getStage());
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {

    }
}
