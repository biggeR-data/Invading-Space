package Klassen;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.awt.*;

public class Raumschiff extends BeweglicheObjekte {
    // Konstruktor
    public Raumschiff(int xKoor, int yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(40);
        setzeHoehe(40);
        Image img = new Image(getClass().getResource("../MileniumFalke3.png").toExternalForm());
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
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

    public Schuss schiessen() {
        //Schuss schuss = new Schuss(erhalteXKoor() + (erhalteBreite()/2), erhalteYKoor(), root);
        //schuss.schiessenHoch();
        return new Schuss(erhalteXKoor() + (erhalteBreite()/2), erhalteYKoor(), root);
    }
}
