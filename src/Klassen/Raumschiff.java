package Klassen;

import javafx.scene.Group;
import javafx.scene.Scene;

public class Raumschiff extends BeweglicheObjekte {
    // Konstruktor
    public Raumschiff(int xKoor, int yKoor, Group root) {
        super(xKoor, yKoor, root);
    }

    // Raumschiff nach rechts und links bewegen
    //  Altes Raumschiff "entfernen"
    //  Neue Koordinaten berechnen
    //  "Neues" Raumschiff zeichnen

    public void bewegenRechts() {
        zeichneSchwarz();
        this.xKoor =  erhalteXKoor() + XBEWEGUNG;
        zeichneWeiss();
    }

    public void bewegenLinks() {
        zeichneSchwarz();
        this.xKoor = erhalteXKoor() - XBEWEGUNG;
        zeichneWeiss();
    }

    public void schiessen() {
        // Aufrufen der Klasse Schuss
        // X und Y Koordinaten setzten
        // Schuss in Liste hinzufügen
    }
}
