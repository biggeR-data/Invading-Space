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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * funktionale Logik des Startbildschirms
 */
public class Startbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Import des FMXL files
    @FXML
    private TextField txtNamensfeld;
    @FXML
    private Label lblHighscoreName;
    @FXML
    private Label lblHighscorePunkte;
    @FXML
    private Label lblPopup;

    // todo: Highscore auf Startbildschirm - Unterscheidung zwischen Normal und Hölle
    public void aktiviereStartbildschirm() {
        ScoreListe scoreListe = new ScoreListe("./res/Scores/spielerdaten_normal.txt");
        lblHighscoreName.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblHighscorePunkte.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte()));
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
     * @param modus
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
            lblPopup.setText(delEx.getMessage());
        } catch (EmptyException empEx) {
            lblPopup.setText(empEx.getMessage());
        }
    }

}
