package Klassen;

import javafx.scene.Group;

// todo: Abstrakte Klasse
public class Monster extends BeweglicheObjekte {
    private static final int PUNKTE = 10;
    private final double YBEWEGUNG = 50;
    private int punkte = PUNKTE;

    public Monster(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
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
    // todo: Ablauf erstellen --- vielleicht im Main Program und nicht hier in der Klasse
    //  siehe Notizen auf IPad
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

    public boolean pruefeKollisionUnten(int yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }
        return false;
    }

    // Vielleicht müss die Zeichenfläche hier auch übergeben werden.
    //  Und vieleicht muss auch noch eine Fill Funktion genutzt werden, um das ganze Objekt weiß zu zeichnen.
}
