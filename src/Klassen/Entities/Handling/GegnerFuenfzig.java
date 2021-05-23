package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class GegnerFuenfzig extends Gegner {

    public GegnerFuenfzig(double xKoor, double yKoor, Group root) {
        // Position & Punkte
        super(xKoor, yKoor, root);
        setzePunkte(50);

        // Aussehen
        Image bild = erhalteBild("Großschiff_Separatisten.png");
        setzeBild(bild);

        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

}
