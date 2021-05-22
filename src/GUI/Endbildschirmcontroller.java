package GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import Klassen.ScoreListe;
import Klassen.Spieler;

public class Endbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label lblScore;
    @FXML
    private Label lblTop1Name;
    @FXML
    private Label lblTop2Name;
    @FXML
    private Label lblTop3Name;
    @FXML
    private Label lblTop1Score;
    @FXML
    private Label lblTop2Score;
    @FXML
    private Label lblTop3Score;

    private ScoreListe scoreListe = new ScoreListe("./res/spielerdaten_normal.txt");
    private Spieler spieler;
    private int modus;

    public void aktiviereEndbildschirm(Stage bühne, Spieler pSpieler, Parent wurzel, int modus) throws IOException {
        spieler = pSpieler;
        this.modus = modus;
        stage = bühne;
        root = new Group(wurzel);
        scene = new Scene(root);
        scoreListe.spielerHinzufuegen(spieler);
        lblScore.setText(spieler.erhaltePunkte() + "");
        System.out.println(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblTop1Name.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblTop1Score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte()));
        lblTop2Name.setText(scoreListe.spielerlisteIndexAusgabe(1).erhalteName());
        lblTop2Score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(1).erhaltePunkte()));
        lblTop3Name.setText(scoreListe.spielerlisteIndexAusgabe(2).erhalteName());
        lblTop3Score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(2).erhaltePunkte()));
        scoreListe.txtUpdaten();
        stage.setScene(scene);
        stage.show();
    }

    public void wechselZuStartbildschirm(ActionEvent acEv) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Startbildschirm.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) acEv.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Startbildschirmcontroller startbildschirmController = loader.getController();
        startbildschirmController.aktiviereStartbildschirm();
    }

    public void wechselZuSpielbildschirm(ActionEvent acEv) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielbildschirm.fxml"));
        root = loader.load();
        Spielbildschirmcontroller spielController = loader.getController();
        //ab hier soll der spielbildschirmcontroller übernehmen
        spielController.aktiviereSpielbildschirm(acEv, spieler, root, modus);
    }

}
