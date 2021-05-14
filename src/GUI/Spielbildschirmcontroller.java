package GUI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

public class Spielbildschirmcontroller {
    @FXML
    private Polygon raumschiff;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label lbl_spielername;

    public void setLbl_spielername(String spielername){
        lbl_spielername.setText(spielername);
    }

    public void raumschifflinks(ActionEvent e){
        raumschiff.setLayoutX(raumschiff.getLayoutX()-10);
    }
    public void raumschiffrechts(ActionEvent e){
        raumschiff.setLayoutX(raumschiff.getLayoutX()+10);
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
