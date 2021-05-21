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
    private Label lbl_spielername;
    @FXML
    private Label lbl_aktuellerscore;
    @FXML
    private Label lbl_highscorename;
    @FXML
    private Label lbl_highscorepunkte;
    private Spieler spieler;
    private ScoreListe scoreListe = new ScoreListe("./res/spielerdaten.txt");
    private int mode;
    private Game spielthread;

    //Hier Spieler anstatt String empfangen
    public void aktiviereSpielfeld(ActionEvent e,Spieler pspieler, Parent wurzel,int mode) throws IOException {
        this.mode = mode;
        spieler = pspieler;
        root = new Group(wurzel);
        spielthread = new Game(this, mode,root);
        //new Thread(spielthread).start();
        spielthread.start();

        lbl_spielername.setText(spieler.erhalteName());
        lbl_highscorename.setText(scoreListe.spielerlisteIndexAusgabe(0).erhalteName());
        lbl_highscorepunkte.setText(String.valueOf(scoreListe.spielerlisteIndexAusgabe(0).erhaltePunkte()));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        //Hier zur scene die vollständige Group einfügen
        //raumschiff.erhalteGroup() anstelle root
        scene = new Scene(root);
        scene.setOnKeyPressed(event ->  {
            if(event.getCode().equals(KeyCode.D) ||event.getCode().equals(KeyCode.RIGHT) ){
                //raumschiffrechts();
                spielthread.keyRight();
                System.out.println(event.getCode());
            } else if(event.getCode().equals(KeyCode.A) ||event.getCode().equals(KeyCode.LEFT)){
                //raumschifflinks();
                //raumschiff.bewegenLinks();
                spielthread.keyLeft();
                System.out.println(event.getCode());
            } else if(event.getCode().equals(KeyCode.W) ||event.getCode().equals(KeyCode.UP)){
                spielthread.keyUp();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void wechselZuGameover() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Endbildschirm.fxml"));
            Parent root2 = loader.load();

            Endbildschirmcontroller endbildschirmcontroller = loader.getController();
            int aktuellerscore = Integer.parseInt(lbl_aktuellerscore.getText());
            //Spieler und Punktestand übergeben || Spieler mit punktestand füllen und übergeben
            spieler.setzePunkte(aktuellerscore);
            endbildschirmcontroller.aktiviereEndscreen(stage,spieler,root2, mode);
            spielthread.interrupt();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void wechselZuStartscreen(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Startbildschirm.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        spielthread.interrupt();
    }
    //Anbindung an die Gameklasse zur Erhöhung der Punktzeil
    public void setztePunktzahl(int punktzahl){
        lbl_aktuellerscore.setText(String.valueOf(punktzahl));
    }
    public int getPunktzahl(){
        return Integer.parseInt(lbl_aktuellerscore.getText());
    }

}
