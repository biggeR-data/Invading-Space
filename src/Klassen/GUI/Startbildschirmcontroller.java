package Klassen.GUI;

import Klassen.Exceptions.DelimException;
import Klassen.Exceptions.EmptyException;
import Klassen.Scores.ScoreListe;
import Klassen.Scores.Spieler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * funktionale Logik des Startbildschirms
 */
public class Startbildschirmcontroller {

    // Zugriff auf GUI
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Bilder auf Startbildschirm der Gegner und des Raumschiffs
    FileInputStream stream = new FileInputStream("res/Images/Großschiff_Separatisten.png");
    Image grossschiff = new Image(stream);
    FileInputStream stream2 = new FileInputStream("res/Images/HyenaClassBomber.png");
    Image bomber = new Image(stream2);
    FileInputStream stream3 = new FileInputStream("res/Images/TIEFigtherFront.png");
    Image tiefighter = new Image(stream3);
    FileInputStream stream4 = new FileInputStream("res/Images/MileniumFalke3.png");
    Image milenium = new Image(stream4);

    // Import des FMXL files
    @FXML private Label lbl10;
    @FXML private Label lbl20;
    @FXML private Label lbl50;
    @FXML private Button btn_normal;
    @FXML private Button btn_hölle;
    @FXML private Label lblHighscore;
    @FXML private Label lblNormal;
    @FXML private Label lblHölle;
    @FXML private Label lblSpace;
    @FXML private Label lblInvader;
    @FXML private Label lblaberbesser;
    @FXML private TextField txtNamensfeld;
    @FXML private Label lblHighscoreNameNormal;
    @FXML private Label lblHighscorePunkteNormal;
    @FXML private Label lblHighscoreNameHölle;
    @FXML private Label lblHighscorePunkteHölle;
    @FXML private Label lblPopup;
    @FXML private ImageView bildGrossschiff;
    @FXML private ImageView bildBomber;
    @FXML private ImageView bildTiefighter;
    @FXML private ImageView bildMilenium;
    @FXML private ImageView bildGrossschiff2;
    @FXML private ImageView bildBomber2;
    @FXML private ImageView bildTiefighter2;

    // Benutzerdefinierte Schriftart
    //Font Quelle: https://www.dafont.com/de/star-jedi.font?psize=s
    private Font starwarsfontvoll = Font.loadFont("file:res/Styling/star_jedi/starjedi/Starjedi.ttf",18);
    private Font starwarsfontleer = Font.loadFont("file:res/Styling/star_jedi/starjedi/Starjhol.ttf",30);

    public Startbildschirmcontroller() throws FileNotFoundException {
    }

    public void aktiviereStartbildschirm() {
        ScoreListe scoreListeNormal = new ScoreListe("./res/Scores/spielerdaten_normal.txt");
        ScoreListe scoreListeHölle = new ScoreListe("./res/Scores/spielerdaten_hölle.txt");

        // Highscore pro Modus setzen
        lblHighscoreNameNormal.setText(scoreListeNormal.spielerlisteIndexAusgabe(0).erhalteName());
        lblHighscorePunkteNormal.setText(String.valueOf(scoreListeNormal.spielerlisteIndexAusgabe(0).erhaltePunkte()));
        lblHighscoreNameHölle.setText(scoreListeHölle.spielerlisteIndexAusgabe(0).erhalteName());
        lblHighscorePunkteHölle.setText(String.valueOf(scoreListeHölle.spielerlisteIndexAusgabe(0).erhaltePunkte()));

        formatieren();
    }

    public void normal(ActionEvent acEv) throws IOException {
        wechselZuSpielbildschirm(acEv, 0);
    }

    public void hölle(ActionEvent acEv) throws IOException {
        wechselZuSpielbildschirm(acEv, 1);
    }

    /**
     * Logik transferieren zum geladenen Spielbildschirm
     *
     * @param acEv
     * @param modus ausgewählter Modus (Normal = 0; Hölle = 1)
     * @throws IOException
     */
    public void wechselZuSpielbildschirm(ActionEvent acEv, int modus) throws IOException {
        try {
            Spieler spieler = new Spieler(txtNamensfeld.getText());

            // Bildschirm laden
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielbildschirm.fxml"));
            root = loader.load();

            // Erstellung & Aktivierung des Controllers
            Spielbildschirmcontroller spielController = loader.getController();
            spielController.aktiviereSpielbildschirm(acEv, spieler, root, modus);

            // Abfangen einer fehlerhaften Spielernamenseingabe
        } catch (DelimException delEx) {
            lblPopup.setFont(starwarsfontvoll);
            lblPopup.setText(delEx.getMessage());
        } catch (EmptyException empEx) {
            lblPopup.setFont(starwarsfontvoll);
            lblPopup.setText(empEx.getMessage());
        }
    }

    public void formatieren(){
        // Highscores
        lblHighscoreNameNormal.setFont(starwarsfontvoll);
        lblHighscorePunkteNormal.setFont(starwarsfontvoll);
        lblHighscoreNameHölle.setFont(starwarsfontvoll);
        lblHighscorePunkteHölle.setFont(starwarsfontvoll);
        lblHighscore.setFont(starwarsfontvoll);

        // Modus
        lblNormal.setFont(starwarsfontvoll);
        lblHölle.setFont(starwarsfontvoll);
        btn_hölle.setFont(starwarsfontvoll);
        btn_normal.setFont(starwarsfontvoll);

        // Titel
        lblSpace.setFont(starwarsfontleer);
        lblInvader.setFont(starwarsfontleer);
        lblaberbesser.setFont(starwarsfontvoll);

        // Titel Bilder
        bildGrossschiff.setImage(grossschiff);
        bildBomber.setImage(bomber);
        bildTiefighter.setImage(tiefighter);
        bildMilenium.setImage(milenium);

        // Namenseingabe
        txtNamensfeld.setFont(starwarsfontvoll);
        txtNamensfeld.setStyle("-fx-text-inner-color: #facc15;-fx-control-inner-background: #000000");

        // Punkte
        lbl10.setFont(starwarsfontvoll);
        lbl20.setFont(starwarsfontvoll);
        lbl50.setFont(starwarsfontvoll);

        // Bilder bei Punkten
        bildGrossschiff2.setImage(grossschiff);
        bildBomber2.setImage(bomber);
        bildTiefighter2.setImage(tiefighter);
    }

}
