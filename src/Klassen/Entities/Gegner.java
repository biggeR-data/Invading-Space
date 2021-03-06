package Klassen.Entities;

import javafx.scene.Group;

/**
 * Oberklasse aller Gegner
 * Festlegung von Maßen und Position
 * Bewegungsfunktionen und -beschränkungen
 * Schussfunktion
 */
public abstract class Gegner extends BeweglicheObjekte {

    private final double YBEWEGUNG = 50;

    private static final int PUNKTE = 10;
    private int punkte = PUNKTE;

    /**
     * Position festlegen
     *
     * @param xKoor Position in X Dimension
     * @param yKoor Position in Y Dimension
     * @param root  legt fest auf welcher GUI-Ebene das Objekt erstellt wird
     */
    protected Gegner(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
    }

    protected void setzePunkte(int punkte) {
        this.punkte = punkte;
    }

    public int erhaltePunkte() {
        return this.punkte;
    }

    /**
     * altes Gegner Objekt entfernen
     * Gegnerobjekt an neuer Position erschaffen
     */
    public void bewegenRechts() {
        entferneObjekt();
        this.xKoor = erhalteXKoor() + erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Links-Bewegung
     * altes Gegner Objekt entfernen
     * Gegnerobjekt an neuer Position erschaffen
     */
    public void bewegenLinks() {
        entferneObjekt();
        this.xKoor = erhalteXKoor() - erhalteXBewegung();
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Rechts-Bewegung
     * altes Gegner Objekt entfernen
     * Gegnerobjekt an neuer Position erschaffen
     */
    public void bewegenRunter() {
        entferneObjekt();
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Schuss an der Position des Gegners abfeuern
     *
     * @return Schuss Schuss des Gegners
     */
    public Schuss schiessen() {
        return new Schuss(erhalteXKoor() + (erhalteBreite() / 2), erhalteYKoor(), root);
    }

    /**
     * Überprüfen ob ein Gegner den unteren Rand (Raumschiff) trifft.
     *
     * @param zonenMarkierung bestimmt die Grenze zwischen der Gegnerzone und der eigenen Zone
     * @return boolean Wahr, falls die Gegner die eigene Reihe durchbrechen
     */
    public boolean pruefeKollisionUnten(double zonenMarkierung) {
        if (erhalteYKoor() + YBEWEGUNG > zonenMarkierung) {
            return true;
        }

        return false;
    }

}
