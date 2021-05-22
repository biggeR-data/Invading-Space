package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class GegnerFuenfzig extends Gegner {
    public GegnerFuenfzig(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzePunkte(50);
        Image img = erhalteBild("Großschiff_Separatisten.png");
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}