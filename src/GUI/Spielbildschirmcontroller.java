package GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.io.IOException;
import Klassen.Raumschiff;
import Klassen.Monster;
import Klassen.Spieler;

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
    private Spieler spieler;

    //Hier Spieler anstatt String empfangen
    public void aktiviereSpielfeld(ActionEvent e,Spieler pspieler, Parent wurzel) throws IOException {
        spieler = pspieler;
        root = new Group(wurzel);
        Raumschiff raumschiff = new Raumschiff(280, 638, root);
        Monster monster1 = new Monster(30, 100, root);
        lbl_spielername.setText(spieler.getName());
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(raumschiff.erhalteGroup());
        scene.setOnKeyPressed(event ->  {
            if(event.getCode().equals(KeyCode.D) ||event.getCode().equals(KeyCode.RIGHT) ){
                //raumschiffrechts();
                raumschiff.bewegenRechts();
                System.out.println(event.getCode());
            } else if(event.getCode().equals(KeyCode.A) ||event.getCode().equals(KeyCode.LEFT)){
                //raumschifflinks();
                raumschiff.bewegenLinks();
                System.out.println(event.getCode());
            } else if(event.getCode().equals(KeyCode.SPACE)){
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void wechselZuGameover(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Endbildschirm.fxml"));
        Parent root2 = loader.load();

        Endbildschirmcontroller endbildschirmcontroller = loader.getController();
        int aktuellerscore = Integer.parseInt(lbl_aktuellerscore.getText());
        //Spieler und Punktestand übergeben || Spieler mit punktestand füllen und übergeben
        spieler.setPunkte(aktuellerscore);
        endbildschirmcontroller.aktiviereEndscreen(e,spieler,root2);
    }
    public void wechselZuStartscreen(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Startbildschirm.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Anbindung an die Gameklasse zur Erhöhung der Punktzeil
    public void setztePunktzahl(int punktzahl){
        lbl_aktuellerscore.setText(String.valueOf(punktzahl));
    }
    public int getPunktzahl(){
        return Integer.parseInt(lbl_aktuellerscore.getText());
    }

}
