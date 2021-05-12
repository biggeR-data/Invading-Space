package Klassen;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Raumschiff extends BeweglicheObjekte {
    // Konstruktor
    public Raumschiff(int xKoor, int yKoor) {
        super(xKoor, yKoor);
    }

    // Raumschiff nach rechts und links bewegen
    //  Altes Raumschiff "entfernen"
    //  Neue Koordinaten berechnen
    //  "Neues" Raumschiff zeichnen

    public void bewegenRechts() {
        zeichneSchwarz();
        this.xKoor =  getXKoor() + BEWEGUNG;
        zeichneWeiss();
    }

    public void bewegenLinks() {
        zeichneSchwarz();
        this.xKoor = getXKoor() - BEWEGUNG;
        zeichneWeiss();
    }

    public void schiessen() {
        // Aufrufen der Klasse Schuss
    }
}
