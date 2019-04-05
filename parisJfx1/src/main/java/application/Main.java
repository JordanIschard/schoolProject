package application;

import controleur.Controleur;
import facade.FacadeParis;
import facade.FacadeParisStaticImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        FacadeParis facadeParisStatic = new FacadeParisStaticImpl();
        new Controleur(primaryStage,facadeParisStatic);
    }

    public static void main(String[] args) {
        launch(args);
    }
}