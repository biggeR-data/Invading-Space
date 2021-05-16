package GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

public class Spielbildschirmcontroller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    //Import des FMXL files und erstellung von Objekten der Gui elemente
    @FXML
    private Label lbl_spielername;
    @FXML
    private Polygon raumschiff;


    public void raumschifflinks(){
        raumschiff.setLayoutX(raumschiff.getLayoutX()-10);
    }
    public void raumschiffrechts(){
        raumschiff.setLayoutX(raumschiff.getLayoutX()+10);
    }

    public void aktiviereSpielfeld(ActionEvent e,String spielername, Parent wurzel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Spielbildschirm.fxml"));
        root = wurzel;
        lbl_spielername.setText(spielername);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setOnKeyPressed(event ->  {
            if(event.getCode().equals(KeyCode.D)){
                raumschiffrechts();
                System.out.println(event.getCode());
            } else if(event.getCode().equals(KeyCode.A)){
                raumschifflinks();
                System.out.println(event.getCode());
            } else if(event.getCode().equals(KeyCode.SPACE)){

            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void wechselZuGameover(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Endbildschirm.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void wechselZuStartscreen(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Startbildschirm.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
