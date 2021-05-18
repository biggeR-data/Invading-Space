package GUI;

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
    private ScoreListe scoreListe = new ScoreListe(".res/spielerdaten.txt");



    public void aktiviereEndscreen(ActionEvent e,String spielername,int aktuellerpunktestand ,Parent wurzel) throws IOException{

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root = new Group(wurzel);
        scene = new Scene(root);
        Spieler aktuellerspieler = new Spieler(spielername,aktuellerpunktestand);
        scoreListe.spielerHinzufuegen(aktuellerspieler);
        //lbl_score.setText(aktuellerpunktestand+"");
        System.out.println(scoreListe.spielerlisteIndexAusgabe(0).getName());
        lbl_top1name.setText(scoreListe.spielerlisteIndexAusgabe(0).getName());
        lbl_top1score.setText(scoreListe.spielerlisteIndexAusgabe(0).getPunkte()+"");
        lbl_top2name.setText(scoreListe.spielerlisteIndexAusgabe(1).getName());
        lbl_top2score.setText(scoreListe.spielerlisteIndexAusgabe(1).getPunkte()+"");
        lbl_top3name.setText(scoreListe.spielerlisteIndexAusgabe(2).getName());
        lbl_top3score.setText(scoreListe.spielerlisteIndexAusgabe(2).getPunkte()+"");

        stage.setScene(scene);
        stage.show();

    }
}
