package vues;

import facade.exceptions.OperationNonAuthoriseeException;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import modele.Pari;
import pattern.Observateur;
import java.time.format.DateTimeFormatter;

public class MesParis extends Presenter<BorderPane> {
    
    @FXML
    public Button boutonMenu;
    
    @FXML
    public Button boutonTousLesParis;
    
    @FXML
    public Button boutonParisOuverts;
    
    @FXML
    public Button boutonSupprimer;

    @FXML
    public TableView<Pari> tableauMesParis;

    @FXML
    public Label erreur;


    @Override
    protected boolean setOptions() {
        erreur.setVisible(false);
        buildTableau();
        return true;
    }

    private void buildTableau(){

        ObservableList<Pari> data = FXCollections.observableArrayList(getMonControleur().getMesParis());

        tableauMesParis = new TableView();

        tableauMesParis.itemsProperty().setValue(data);

        TableColumn<Pari,Number> idColumn = new TableColumn<>("Id");
        TableColumn<Pari,String> sport = new TableColumn<>("Sport");
        TableColumn<Pari,String> equipe1 = new TableColumn<>("Equipe 1");
        TableColumn<Pari,String> equipe2 = new TableColumn<>("Equipe 2");
        TableColumn<Pari,String> quand = new TableColumn<>("Quand");
        TableColumn<Pari,String> vainqueur_choisi = new TableColumn<>("Vainqueur choisi");
        TableColumn<Pari,Number> mise = new TableColumn<>("Mise");
        TableColumn<Pari,String> gain = new TableColumn<>("Gain");




        idColumn.setCellValueFactory((e) -> new ReadOnlyLongWrapper(e.getValue().getIdPari()).getReadOnlyProperty());
        sport.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getMatch().getSport()).getReadOnlyProperty());
        equipe1.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getMatch().getEquipe1()).getReadOnlyProperty());
        equipe2.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getMatch().getEquipe2()).getReadOnlyProperty());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

        quand.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getMatch().getQuand().format(formatter)).getReadOnlyProperty());
        vainqueur_choisi.setCellValueFactory((e) -> new ReadOnlyStringWrapper(e.getValue().getVainqueur()).getReadOnlyProperty());
        mise.setCellValueFactory((e) -> new ReadOnlyDoubleWrapper(e.getValue().getMontant()).getReadOnlyProperty());
        gain.setCellValueFactory((e) -> new ReadOnlyStringWrapper((e.getValue().getGain().isPresent())?
                String.valueOf(e.getValue().getGain().get()):
                ("Aucun résultat")
                ).getReadOnlyProperty());

        tableauMesParis.getColumns().addAll(idColumn, sport, equipe1, equipe2, quand, vainqueur_choisi, mise, gain);

        getRoot().setCenter(tableauMesParis);

    }


    @Override
    public void show() {
        getStage().setTitle("Mes paris");
        getStage().setScene(new Scene(getRoot(), getRoot().getMinWidth(), getRoot().getMinHeight()));
        getStage().setResizable(true);
        getStage().show();
    }

    public void goToMenu(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(boutonMenu)){
            getMonControleur().goToMenu(getStage());
        }
    }

    public void goToParisOuverts(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonParisOuverts)){
            getMonControleur().goToParisOuverts(getStage());
        }
    }

    public void goToTousLesParis(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonTousLesParis)){
            getMonControleur().goToTousLesParis(getStage());
        }
    }

    public void supprimer(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(boutonSupprimer)){
            try {
                erreur.setVisible(false);
                int idLine = tableauMesParis.getSelectionModel().getSelectedIndex();
                long idMatch = tableauMesParis.getItems().get(idLine).getIdPari();
                getMonControleur().supprimer(idMatch);
                tableauMesParis.getItems().remove(idLine);
            } catch (OperationNonAuthoriseeException e) {
                erreur.setVisible(true);
                erreur.setText("Opération non autorisée");
            } catch (Exception e){
                erreur.setVisible(true);
                erreur.setText("Vous n'avez pas choisi de match");
            }
        }
    }

    @Override
    public void maj(Observateur observateur, Object o) {

    }
}
