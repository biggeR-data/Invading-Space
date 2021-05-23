package Klassen.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import Klassen.Scores.Spieler;
import Klassen.Scores.ScoreListe;
import Klassen.Entities.Handling.Game;

/**
 * funktionale Logik während dem Spielen
 */
public class Spielbildschirmcontroller {

    // Zugriff auf GUI
    private Stage stage;
    private Scene scene;
    private Group root;
    private Font starwarsfontvoll = Font.loadFont("file:res/Styling/star_jedi/starjedi/Starjedi.ttf",18);
    private Font starwarsfontleer = Font.loadFont("file:res/Styling/star_jedi/starjedi/Starjhol.ttf",30);
    // Import des FMXL files
    @FXML private Label lblHighscore;
    @FXML private Label lblSpielername;
    @FXML private Label lblAktuellerScore;
    @FXML private Label lblHighscoreName;
    @FXML private Label lblHighscorePunkte;
    @FXML private Button btnWeltraumverlassen;


    private Label lblPopUp;

    private ScoreListe scoreListe;
    private String pfadZuSpielerdaten;
    private Spieler spieler;
    private int modus;
    private static Game spielThread;

    /**
     * Thread starten für Spiel
     * KeyEvents des Spielers verarbeiten
     *
     * @param acEv
     * @param pSpieler
     * @param pRoot
     * @param modus
     */
    public void aktiviereSpielbildschirm(ActionEvent acEv, Spieler pSpieler, Parent pRoot, int modus) {
        // Aufbau der Oberfläche
        this.modus = modus;
        spieler = pSpieler;
        root = new Group(pRoot);
        formatieren();
        // Zugriff auf Spielerdaten Textdatei abhängig von Modus
        // Modus spezifische Highscores
        switch (modus) {
            case 0:
                pfadZuSpielerdaten = "./res/Scores/spielerdaten_normal.txt";
                break;
            case 1:
                pfadZuSpielerdaten = "./res/Scores/spielerdaten_hölle.txt";
                break;
        }

        // Highscore Spieler
        scoreListe = new ScoreListe(pfadZuSpielerdaten);
        lblSpielername.setText(spieler.erhalteName());
        lblHighscoreName.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblHighscorePunkte.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte()));

        // Starten des Threads
        // KeyEvents des Spielablaufs verarbeiten
        spielThread = new Game(this, modus, root);
        spielThread.start();
        stage = (Stage) ((Node) acEv.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
                spielThread.keyRight();
            } else if (event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)) {
                spielThread.keyLeft();
            } else if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)) {
                spielThread.keyUp();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logik transferieren zum geladenen Endbildschirm
     */
    public void wechselZuEndbildschirm() {
        try {
            // Beenden des Spiels
            spielThread.stop();

            // Laden der Endbildschirmoberfläche
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Endbildschirm.fxml"));
            Parent root2 = loader.load();

            // Controller Erstellung
            // Spielergebnis übergeben
            Endbildschirmcontroller endbildschirmController = loader.getController();
            int aktuellerScore = Integer.parseInt(lblAktuellerScore.getText());
            spieler.setzePunkte(aktuellerScore);
            endbildschirmController.aktiviereEndbildschirm(stage, spieler, root2, modus);

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void setztePunktzahl(int punktzahl) {
        lblAktuellerScore.setText(String.valueOf(punktzahl));
    }

    /**
     * dynamisches Popup
     *
     * @param nachricht
     */
    public void setztePopup(String nachricht) {
        lblPopUp = new Label(nachricht);
        lblPopUp.setLayoutX(270);
        lblPopUp.setLayoutY(298);
        lblPopUp.setTextFill(javafx.scene.paint.Color.WHITE);
        lblPopUp.setFont(Font.font("System", 23));
        root.getChildren().add(lblPopUp);
        lblPopUp.setText(nachricht);
    }

    public void entfernePopup() {
        root.getChildren().remove(lblPopUp);
    }

    public static Game erhalteSpielThread() {
        return spielThread;
    }

    public void formatieren(){
        lblHighscore.setFont(starwarsfontvoll);
        lblSpielername.setFont(starwarsfontvoll);
        lblAktuellerScore.setFont(starwarsfontvoll);
        lblHighscoreName.setFont(starwarsfontvoll);
        lblHighscorePunkte.setFont(starwarsfontvoll);
        btnWeltraumverlassen.setFont(starwarsfontvoll);
    }

}
