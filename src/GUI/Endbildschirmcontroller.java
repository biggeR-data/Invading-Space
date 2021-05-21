package GUI;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OVERPeer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.io.IOException;
import Klassen.ScoreListe;
import Klassen.Spieler;

public class Endbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label lbl_score;
    @FXML
    private Label lbl_top1name;
    @FXML
    private Label lbl_top2name;
    @FXML
    private Label lbl_top3name;
    @FXML
    private Label lbl_top1score;
    @FXML
    private Label lbl_top2score;
    @FXML
    private Label lbl_top3score;
    private ScoreListe scoreListe = new ScoreListe("./res/spielerdaten.txt");
    private Spieler spieler;


    //Spieler empfangen anstatt string und punktestand
    public void aktiviereEndscreen(Stage bühne,Spieler pspieler,Parent wurzel) throws IOException{
        spieler = pspieler;
        //stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage = bühne;
        root = new Group(wurzel);
        scene = new Scene(root);
        scoreListe.spielerHinzufuegen(spieler);
        lbl_score.setText(spieler.getPunkte()+"");
        System.out.println(scoreListe.spielerlisteIndexAusgabe(0).getName());
        lbl_top1name.setText(scoreListe.spielerlisteIndexAusgabe(0).getName());
        lbl_top1score.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).getPunkte()));
        lbl_top2name.setText(scoreListe.spielerlisteIndexAusgabe(1).getName());
        lbl_top2score.setText(scoreListe.spielerlisteIndexAusgabe(1).getPunkte()+"");
        lbl_top3name.setText(scoreListe.spielerlisteIndexAusgabe(2).getName());
        lbl_top3score.setText(scoreListe.spielerlisteIndexAusgabe(2).getPunkte()+"");
        scoreListe.txtUpdaten();
        stage.setScene(scene);
        stage.show();

    }
    public void wechselZuStartscreen(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Startbildschirm.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Startbildschirmcontroller startbildschirmcontroller = loader.getController();
        startbildschirmcontroller.setzeHighscorespieler();
    }

    public void wechselZuGamescreen(ActionEvent e) throws IOException {
        //Main klasse des Spielbildschirms
        //TODO: Überprüfung text leer und kein komma

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielbildschirm.fxml"));
        root = loader.load();
        Spielbildschirmcontroller spielcontroller = loader.getController();
        //ab hier soll der spielbildschirmcontroller übernehmen
        spielcontroller.aktiviereSpielfeld(e,spieler, root);
    }
}
