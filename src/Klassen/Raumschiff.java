package Klassen;

import javafx.scene.Group;
import javafx.scene.Scene;

import java.awt.*;

public class Raumschiff extends BeweglicheObjekte {
    // Konstruktor
    public Raumschiff(int xKoor, int yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(40);
        setzeHoehe(40);
    }

    // Raumschiff nach rechts und links bewegen
    //  Altes Raumschiff "entfernen"
    //  Neue Koordinaten berechnen
    //  "Neues" Raumschiff zeichnen

    public void bewegenRechts() {
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.xKoor =  erhalteXKoor() + erhalteXBewegung();
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    public void bewegenLinks() {
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.xKoor = erhalteXKoor() - erhalteXBewegung();
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    public void schiessen() {
        // Aufrufen der Klasse Schuss
        // X und Y Koordinaten setzten
        // Schuss in Liste hinzufügen
    }
}
