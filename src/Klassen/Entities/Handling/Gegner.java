package Klassen.Entities.Handling;

import javafx.scene.Group;

public abstract class Gegner extends BeweglicheObjekte {
    private static final int PUNKTE = 10;
    private final double YBEWEGUNG = 50;
    private int punkte = PUNKTE;

    public Gegner(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        //Image img = new Image(getClass().getResource("/Testing/DarthVaderKopf.png").toExternalForm());
        //setzteBild(img);
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Eine neue Punktzahl für das Gegner-Objekt setzen.
     *
     * @param punkte Ein int-Wert
     */
    protected void setzePunkte(int punkte) {
        this.punkte = punkte;
    }

    /**
     * Erhalte die aktuelle Punktzahl des Gegner-Objekts
     *
     * @return
     */
    public int erhaltePunkte() {
        return this.punkte;
    }

    /**
     * Das Gegner-Objekt nach rechts bewegen.
     * Altes Objekt entfernen
     * Koordinaten neu berechnen
     * Neues Objekt zeichnen
     */
    public void bewegenRechts() {
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.xKoor = erhalteXKoor() + erhalteXBewegung();
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Das Gegner-Objekt nach links bewegen.
     * Altes Objekt entfernen
     * Koordinaten neu berechnen
     * Neues Objekt zeichnen
     */
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

    public Schuss schiessen() {
        return new Schuss(erhalteXKoor() + (erhalteBreite() / 2), erhalteYKoor(), root);
    }

    public boolean pruefeKollisionUnten(double yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }
        return false;
    }
}
