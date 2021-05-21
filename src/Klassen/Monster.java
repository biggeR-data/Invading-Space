package Klassen;

import javafx.scene.Group;
import javafx.scene.image.Image;

// todo: Abstrakte Klasse
public class Monster extends BeweglicheObjekte {
    private static final int PUNKTE = 10;
    private final double YBEWEGUNG = 50;
    private int punkte = PUNKTE;

    public Monster(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        Image img = new Image(getClass().getResource("/Testing/DarthVaderKopf.png").toExternalForm());
        setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    protected void setzePunkte(int punkte) {
        this.punkte = punkte;
    }

    public int erhaltePunkte() {
        return this.punkte;
    }

    // Monster nach rechts, links, oben und unten bewegen.
    //  Altes Monster "entfernen"
    //  Neue Koordinaten berechnen
    //  "Neues" Monster zeichnen
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

    public void bewegenRunter() {
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    public void schiessen() {
        // Aufrufen der Klasse Schuss
    }

    public boolean pruefeKollisionUnten(double yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }
        return false;
    }
}
