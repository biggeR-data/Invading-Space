package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

import Klassen.Spieler;
import Klassen.ScoreListe;
import Klassen.Game;

public class Spielbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    //private Parent root;
    private Group root;
    //Import des FMXL files und erstellung von Objekten der Gui elemente
    @FXML
    private Label lblSpielername;
    @FXML
    private Label lblAktuellerScore;
    @FXML
    private Label lblHighscoreName;
    @FXML
    private Label lblHighscorePunkte;
    @FXML
    private Label lblPopup;
    private Spieler spieler;
    private ScoreListe scoreListe = new ScoreListe("./res/spielerdaten_normal.txt");
    private int modus;
    private static Game spielThread;

    //Hier Spieler anstatt String empfangen
    public void aktiviereSpielbildschirm(ActionEvent acEv, Spieler pSpieler, Parent wurzel, int modus) throws IOException {
        this.modus = modus;
        spieler = pSpieler;
        root = new Group(wurzel);
        spielThread = new Game(this, modus, root);
        spielThread.start();

        lblSpielername.setText(spieler.erhalteName());
        lblHighscoreName.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblHighscorePunkte.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte()));
        stage = (Stage) ((Node) acEv.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.D) || event.getCode().equals(KeyCode.RIGHT)) {
                spielThread.keyRight();
                System.out.println(event.getCode());
            } else if (event.getCode().equals(KeyCode.A) || event.getCode().equals(KeyCode.LEFT)) {
                spielThread.keyLeft();
                System.out.println(event.getCode());
            } else if (event.getCode().equals(KeyCode.W) || event.getCode().equals(KeyCode.UP)) {
                spielThread.keyUp();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void wechselZuEndbildschirm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Endbildschirm.fxml"));
            Parent root2 = loader.load();

            Endbildschirmcontroller endbildschirmController = loader.getController();
            int aktuellerScore = Integer.parseInt(lblAktuellerScore.getText());
            spieler.setzePunkte(aktuellerScore);
            //Stage wird übergeben, da kein acEv besteht aus der später die stage generiert werden könnte
            endbildschirmController.aktiviereEndbildschirm(stage, spieler, root2, modus);
            spielThread.stop();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void wechselZuStartbildschirm(ActionEvent acEv) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Startbildschirm.fxml"));
        stage = (Stage) ((Node) acEv.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        spielThread.stop();
    }

    //Anbindung an die Gameklasse zur Erhöhung der Punktzeil
    public void setztePunktzahl(int punktzahl) {
        lblAktuellerScore.setText(String.valueOf(punktzahl));
    }

    public int getPunktzahl() {
        return Integer.parseInt(lblAktuellerScore.getText());
    }

    public void setztePopup(String m) {
        lblPopup.setText(m);
    }

    public static Game getSpielThread() {
        return spielThread;
    }

}
