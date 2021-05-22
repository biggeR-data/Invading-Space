package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class GegnerZehn extends Gegner {

    public GegnerZehn(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzePunkte(10);
        Image img = erhalteBild("TIEFigtherFront.png");
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}
