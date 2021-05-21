package Klassen;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class MonsterFuenfzig extends Monster {
    public MonsterFuenfzig(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzePunkte(50);
        Image img = new Image(getClass().getResource("/Großschiff_Separatisten.png").toExternalForm());
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}
