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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import Klassen.Scores.ScoreListe;
import Klassen.Scores.Spieler;

/**
 * funktionale Logik des Endbildschirms
 */
public class Endbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    FileInputStream stream = new FileInputStream("res/Images/Großschiff_Separatisten.png");
    Image grossschiff = new Image(stream);
    FileInputStream stream2 = new FileInputStream("res/Images/HyenaClassBomber.png");
    Image bomber = new Image(stream2);
    FileInputStream stream3 = new FileInputStream("res/Images/TIEFigtherFront.png");
    Image tiefighter = new Image(stream3);
    FileInputStream stream4 = new FileInputStream("res/Images/MileniumFalke3.png");
    Image milenium = new Image(stream4);
    private Font starwarsfontvoll = Font.loadFont("file:res/Styling/star_jedi/starjedi/Starjedi.ttf",18);
    private Font starwarsfontleer = Font.loadFont("file:res/Styling/star_jedi/starjedi/Starjhol.ttf",30);

    // laden der FXML
    @FXML private ImageView imgvGrossschiff;
    @FXML private ImageView imgvBomber;
    @FXML private ImageView imgvTiefighter;
    @FXML private ImageView imgvMilenium;
    @FXML private Label lblGame;
    @FXML private Label lblOver;
    @FXML private Label lblPunktzahl;
    @FXML private Label lblScore;
    @FXML private Label lblTop3Spieler;
    @FXML private Label lblTop1Name;
    @FXML private Label lblTop2Name;
    @FXML private Label lblTop3Name;
    @FXML private Label lblTop1Score;
    @FXML private Label lblTop2Score;
    @FXML private Label lblTop3Score;
    @FXML private Button btnErneutspielen;
    @FXML private Button btnStartseite;

    // Score relevant Attribute
    private ScoreListe scoreListe;
    private String pfadZuSpielerdaten;
    private Spieler spieler;
    private int modus;

    public Endbildschirmcontroller() throws FileNotFoundException {
    }

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

        // Punktzahl Verarbeitung
        scoreListe = new ScoreListe(pfadZuSpielerdaten);
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

    public void formatieren(){
        lblGame.setFont(starwarsfontleer);
        lblOver.setFont(starwarsfontleer);
        lblPunktzahl.setFont(starwarsfontvoll);
        lblScore.setFont(starwarsfontvoll);
        lblTop3Spieler.setFont(starwarsfontvoll);
        lblTop1Name.setFont(starwarsfontvoll);
        lblTop2Name.setFont(starwarsfontvoll);
        lblTop3Name.setFont(starwarsfontvoll);
        lblTop1Score.setFont(starwarsfontvoll);
        lblTop2Score.setFont(starwarsfontvoll);
        lblTop3Score.setFont(starwarsfontvoll);
        btnErneutspielen.setFont(starwarsfontvoll);
        btnStartseite.setFont(starwarsfontvoll);
        imgvGrossschiff.setImage(grossschiff);
        imgvBomber.setImage(bomber);
        imgvTiefighter.setImage(tiefighter);
        imgvMilenium.setImage(milenium);
    }

}
