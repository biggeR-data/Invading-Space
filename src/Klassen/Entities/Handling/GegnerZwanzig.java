package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class GegnerZwanzig extends Gegner {

    public GegnerZwanzig(double xKoor, double yKoor, Group root) {
        // Position & Punkte
        super(xKoor, yKoor, root);
        setzePunkte(20);

        // Aussehen
        Image bild = erhalteBild("HyenaClassBomber.png");
        setzeBild(bild);

        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

}
