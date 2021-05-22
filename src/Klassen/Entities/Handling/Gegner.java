package Klassen.Entities.Handling;

import javafx.scene.Group;

/**
 * Oberklasse aller Gegner
 * Festlegung von Maßen und Position
 * Bewegungsfunktionen, Schussfunktion und Rückgabewert für die Kollisionsüberprüfung mit dem unteren Rand
 */
public abstract class Gegner extends BeweglicheObjekte {
    // Standart Attribute
    private static final int PUNKTE = 10;
    private final double YBEWEGUNG = 50;

    private int punkte = PUNKTE;

    /**
     * Position festlegen
     * @param xKoor
     * @param yKoor
     * @param root
     */
    public Gegner(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
    }

    protected void setzePunkte(int punkte) {
        this.punkte = punkte;
    }

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
        entferneObjekt();
        this.xKoor = erhalteXKoor() + erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Das Gegner-Objekt nach links bewegen.
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
     * Das Gegner-Objekt nach unten bewegen.
     * Altes Objekt entfernen
     * Koordinaten neu berechnen
     * Neues Objekt zeichnen
     */
    public void bewegenRunter() {
        entferneObjekt();
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Ein Objekt der Klasse Schuss erstellen
     * @return Schuss
     */
    public Schuss schiessen() {
        return new Schuss(erhalteXKoor() + (erhalteBreite() / 2), erhalteYKoor(), root);
    }

    /**
     * Überprüfen ob ein Gegner den unteren Rand (Raumschiff) trifft.
     * @param yRand
     * @return Boolescher Wert
     */
    public boolean pruefeKollisionUnten(double yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }
        return false;
    }
}
