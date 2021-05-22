package GUI;

import Klassen.DelimException;
import Klassen.EmptyException;
import Klassen.ScoreListe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import Klassen.Spieler;

public class Startbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField txtNamensfeld;
    @FXML
    private Label lblHighscorename;
    @FXML
    private Label lblHighscorepunkte;
    @FXML
    private Label lblPopup;


    public void normal(ActionEvent acEv) throws IOException {
        System.out.println("Spielmodus: Normal");
        wechselZuGamescreen(acEv, 0);
    }

    public void hölle(ActionEvent acEv) throws IOException {
        System.out.println("Spielmodus: HÖLLE");
        wechselZuGamescreen(acEv, 1);
    }

    public void wechselZuGamescreen(ActionEvent acEv, int modus) throws IOException {
        try {
            Spieler spieler = new Spieler(txtNamensfeld.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielbildschirm.fxml"));
            root = loader.load();
            Spielbildschirmcontroller spielcontroller = loader.getController();
            //ab hier soll der spielbildschirmcontroller übernehmen
            spielcontroller.aktiviereSpielfeld(acEv, spieler, root, modus);
        } catch (DelimException ex) {
            lblPopup.setText(ex.getMessage());
        } catch (EmptyException ex) {
            lblPopup.setText(ex.getMessage());
        }
    }

    public void setzeHighscorespieler() throws IOException {
        ScoreListe scoreListe = new ScoreListe("./res/spielerdaten_normal.txt");
        lblHighscorename.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lblHighscorepunkte.setText(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte() + "");
    }

}
