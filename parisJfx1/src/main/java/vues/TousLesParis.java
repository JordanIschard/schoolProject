package vues;

import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import modele.Match;
import pattern.Observateur;

import java.time.format.DateTimeFormatter;

public class TousLesParis extends Presenter<BorderPane> {

    @FXML
    public TableView<Match> tableauTousLesParis;

    @FXML
    public Button boutonMenu;

    @FXML
    public Button boutonMesParis;

    @FXML
    public Button boutonParisOuverts;

    @FXML
    public Button boutonResultat;

    @FXML
    public Label erreur;

    private void buildTableau(){

        ObservableList<Match> data = FXCollections.observableArrayList(getMonControleur().getTousLesParis());

        tableauTousLesParis= new TableView();

        tableauTousLesParis.itemsProperty().setValue(data);

        TableColumn<Match,Number> idColumn = new TableColumn<>("Id");
        TableColumn<Match,String> sport = new TableColumn<>("Sport");
        TableColumn<Match,String> equipe1 = new TableColumn<>("Equipe 1");
        TableColumn<Match,String> equipe2 = new TableColumn<>("Equipe 2");
        TableColumn<Match,String> quand = new TableColumn<>("Quand");
        TableColumn<Match,String> resultat = new TableColumn<>("Resultat");


            idColumn.setCellValueFactory((e) -> new ReadOnlyLongWrapper(e.getValue().getIdMatch()).getReadOnlyProperty());
            sport.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getSport()).getReadOnlyProperty());
            equipe1.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getEquipe1()).getReadOnlyProperty());
            equipe2.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getEquipe2()).getReadOnlyProperty());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

            quand.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getQuand().format(formatter)).getReadOnlyProperty());

        resultat.setCellValueFactory((e) -> new ReadOnlyStringWrapper((e.getValue().getResultat().isPresent()?
                (e.getValue().getResultat().get()):
                "Pas encore donnée")).getReadOnlyProperty()
                );

            tableauTousLesParis.getColumns().addAll(idColumn,sport,equipe1,equipe2,quand,resultat);

        getRoot().setCenter(tableauTousLesParis);

    }

    @Override
    protected boolean setOptions() {
        erreur.setVisible(false);
        buildTableau();
        return true;
    }

    @Override
    public void show() {
        getStage().setTitle("Tous les matchs");
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        getStage().setResizable(true);
        getStage().show();
    }

    public void goToParisOuverts(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonParisOuverts)){
            getMonControleur().goToParisOuverts(getStage());
        }
    }

    public void goToMesParis(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonMesParis)){
            getMonControleur().goToMesParis(getStage());
        }
    }

    public void goToMenu(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonMenu)){
            getMonControleur().goToMenu(getStage());
        }
    }

    public void goToResultat(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonResultat)){
            try {
                int idLine = tableauTousLesParis.getSelectionModel().getSelectedIndex();
                long idMatch = tableauTousLesParis.getItems().get(idLine).getIdMatch();
                getMonControleur().goToResultat(idMatch);
                erreur.setVisible(false);
            }catch (Exception e){
                erreur.setVisible(true);
                erreur.setText("Vous n'avez pas choisi de match");
            }
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {
        buildTableau();
        getRoot().setCenter(tableauTousLesParis);
    }
}
