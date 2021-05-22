package Klassen;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class MonsterZehn extends Monster {

    public MonsterZehn(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzePunkte(10);
        Image img = erhalteBild("TIEFigtherFront.png");
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}
