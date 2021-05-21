package Klassen;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class MonsterZwanzig extends Monster {
    public MonsterZwanzig(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzePunkte(20);
        Image img = new Image(getClass().getResource("../HyenaClassBomber.png").toExternalForm());
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}
