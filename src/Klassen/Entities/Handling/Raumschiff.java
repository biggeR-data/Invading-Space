package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class Raumschiff extends BeweglicheObjekte {
    // Konstruktor
    public Raumschiff(int xKoor, int yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(40);
        setzeHoehe(40);
        Image img = erhalteBild("MileniumFalke3.png");
        setzeBild(img);
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Das Raumschiff-Objekt nach rechts bewegen.
     * Altes Objekt entfernen
     * Koordinaten neu berechnen
     * Neues Objekt zeichnen
     */
    public void bewegenRechts() {
        entferneObjekt();
        this.xKoor = erhalteXKoor() + erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Das Raumschiff-Objekt nach links bewegen.
     * Altes Objekt entfernen
     * Koordinaten neu berechnen
     * Neues Objekt zeichnen
     */
    public void bewegenLinks() {
        entferneObjekt();
        this.xKoor = erhalteXKoor() - erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Das Raumschiff-Objekt schießen lassen
     */
    public Schuss schiessen() {
        return new Schuss(erhalteXKoor() + (erhalteBreite() / 2), erhalteYKoor(), root);
    }
}
