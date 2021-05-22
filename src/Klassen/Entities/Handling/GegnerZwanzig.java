package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class GegnerZwanzig extends Gegner {
    public GegnerZwanzig(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzePunkte(20);
        Image img = erhalteBild("HyenaClassBomber.png");
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}