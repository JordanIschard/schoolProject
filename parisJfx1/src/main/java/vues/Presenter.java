package vues;

import controleur.Controleur;
import exception.LoadOptionsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import path.Path;
import pattern.Sujet;

import java.io.IOException;
import java.net.URL;

public abstract class Presenter<T> implements Sujet {

    private Controleur monControleur;

    private Stage stage;

    @FXML
    private T root;

    Controleur getMonControleur() {
        return monControleur;
    }

    Stage getStage() {
        return stage;
    }

    T getRoot() { return root; }

    public static Presenter creerInstance(Controleur controleur, Stage stage, Path path) throws LoadOptionsException{
        URL location = Presenter.class.getResource(path.getPath());
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Presenter vue = fxmlLoader.getController();
        vue.root = root;
        vue.stage = stage;
        vue.monControleur = controleur;
        boolean load = vue.setOptions();

        if(load){
            return vue;
        }else{
            throw new LoadOptionsException();
        }
    }

    protected abstract boolean setOptions();

    public abstract void show();

}
