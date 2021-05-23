package Klassen.Entities;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class GegnerZehn extends Gegner {

    public GegnerZehn(double xKoor, double yKoor, Group root) {
        // Position & Punkte
        super(xKoor, yKoor, root);
        setzePunkte(10);

        // Aussehen
        Image bild = erhalteBild("TIEFigtherFront.png");
        setzeBild(bild);

        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

}
