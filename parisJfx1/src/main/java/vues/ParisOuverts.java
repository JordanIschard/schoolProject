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

public class ParisOuverts extends Presenter<BorderPane> {

    @FXML
    public Button boutonAjouter;

    @FXML
    public Label erreur;

    @FXML
    public Button boutonParier;

    @FXML
    public Button boutonTousLesParis;

    @FXML
    public Button boutonMenu;
    
    @FXML
    public Button boutonMesParis;

    @FXML
    private TableView<Match> tableauParis;

    @Override
    protected boolean setOptions() {
        erreur.setVisible(false);
        buildTableau();
        return true;
    }

    private void buildTableau(){

        ObservableList<Match> data = FXCollections.observableArrayList(getMonControleur().getParisOuverts());

        tableauParis = new TableView();

        tableauParis.itemsProperty().setValue(data);

        TableColumn<Match,Number> idColumn = new TableColumn<>("Id");
        TableColumn<Match,String> sport = new TableColumn<>("Sport");
        TableColumn<Match,String> equipe1 = new TableColumn<>("Equipe 1");
        TableColumn<Match,String> equipe2 = new TableColumn<>("Equipe 2");
        TableColumn<Match,String> quand = new TableColumn<>("Quand");

        idColumn.setCellValueFactory((e) -> new ReadOnlyLongWrapper(e.getValue().getIdMatch()).getReadOnlyProperty());
        sport.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getSport()).getReadOnlyProperty());
        equipe1.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getEquipe1()).getReadOnlyProperty());
        equipe2.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getEquipe2()).getReadOnlyProperty());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

        quand.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getQuand().format(formatter)).getReadOnlyProperty());

        tableauParis.getColumns().addAll(idColumn,sport,equipe1,equipe2,quand);

        getRoot().setCenter(tableauParis);

    }

    @Override
    public void show() {
        getStage().setTitle("Paris ouverts");
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        getStage().setResizable(true);
        getStage().show();
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

    public void goToTousLesParis(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonTousLesParis)){
            getMonControleur().goToTousLesParis(getStage());
        }
    }

    public void goToAjouter(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonAjouter)){
            getMonControleur().goToAjouter();
        }
    }

    public void goToParier(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonParier)){
            try {
                int idLine = tableauParis.getSelectionModel().getSelectedIndex();
                long idMatch = tableauParis.getItems().get(idLine).getIdMatch();
                getMonControleur().goToParier(idMatch);
                erreur.setVisible(false);
            }catch (Exception e){
                erreur.setVisible(true);
                erreur.setText("Vous n'avez pas choisi de match où parier");
            }
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {
        buildTableau();
        getRoot().setCenter(tableauParis);
    }
}
