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

    // laden der FXML
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

    // Score relevant Attribute
    private ScoreListe scoreListe = new ScoreListe("./res/spielerdaten_normal.txt");
    private Spieler spieler;
    private int modus;

    /**
     * Präsentation des Spielergebnisses
     * Ausgabe der Top 3 Highscores
     *
     * @param pStage
     * @param pSpieler
     * @param pRoot
     * @param modus
     * @throws IOException
     */
    public void aktiviereEndbildschirm(Stage pStage, Spieler pSpieler, Parent pRoot, int modus) throws IOException {
        // Aufbau
        spieler = pSpieler;
        this.modus = modus;
        stage = pStage;
        root = new Group(pRoot);
        scene = new Scene(root);

        // Punktzahl Verarbeitung
        scoreListe.spielerHinzufuegen(spieler);
        lblScore.setText(String.valueOf(spieler.erhaltePunkte()));

        lblTop1Name.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblTop1Score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte()));
        lblTop2Name.setText(scoreListe.spielerlisteIndexAusgabe(1).erhalteName());
        lblTop2Score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(1).erhaltePunkte()));
        lblTop3Name.setText(scoreListe.spielerlisteIndexAusgabe(2).erhalteName());
        lblTop3Score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(2).erhaltePunkte()));

        // Endstand der Highscores speichern
        scoreListe.txtUpdaten();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logik transferieren zum geladenen Startbildschirm
     *
     * @param acEv
     * @throws IOException
     */
    public void wechselZuStartbildschirm(ActionEvent acEv) throws IOException {
        // Laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Startbildschirm.fxml"));
        root = loader.load();

        // Verarbeitung
        stage = (Stage) ((Node) acEv.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Übergabe
        Startbildschirmcontroller startbildschirmController = loader.getController();
        startbildschirmController.aktiviereStartbildschirm();
    }

    /**
     * Starten einer neuen Runde mit selbem Spieler
     *
     * @param acEv
     * @throws IOException
     */
    public void wechselZuSpielbildschirm(ActionEvent acEv) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielbildschirm.fxml"));

        root = loader.load();
        Spielbildschirmcontroller spielController = loader.getController();

        spielController.aktiviereSpielbildschirm(acEv, spieler, root, modus);
    }

}
