package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class Raumschiff extends BeweglicheObjekte {

    public Raumschiff(int xKoor, int yKoor, Group root) {
        // Position
        super(xKoor, yKoor, root);

        // Maße
        setzeBreite(40);
        setzeHoehe(40);

        // Aussehen
        Image img = erhalteBild("MileniumFalke3.png");
        setzeBild(img);

        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Bewegung nach rechts durch neues Objekt realisieren
     * altes Objekt entfernen
     */
    public void bewegenRechts() {
        entferneObjekt();
        this.xKoor = erhalteXKoor() + erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Bewegung nach rechts durch neues Objekt realisieren
     * altes Objekt entfernen
     */
    public void bewegenLinks() {
        entferneObjekt();
        this.xKoor = erhalteXKoor() - erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Schuss an momentaner Position
     */
    public Schuss schiessen() {
        return new Schuss(erhalteXKoor() + (erhalteBreite() / 2), erhalteYKoor(), root);
    }
}
