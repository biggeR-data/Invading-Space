package Klassen;

import javafx.scene.Group;

public class Monster extends BeweglicheObjekte {
    private int punkte;
    private final double YBEWEGUNG = 50;

    // todo: Einer der Konstruktoren muss vermutlich entfernt werden
    public Monster(double xKoor, double yKoor, Group root, int punkte) {
        super(xKoor, yKoor, root);
        this.punkte = punkte;
    }

    public Monster(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
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
        zeichneSchwarz();
        this.xKoor =  erhalteXKoor() + XBEWEGUNG;
        zeichneWeiss();
    }

    public void bewegenLinks() {
        zeichneSchwarz();
        this.xKoor = erhalteXKoor() - XBEWEGUNG;
        zeichneWeiss();
    }

    public void bewegenRunter() {
        zeichneSchwarz();
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        zeichneWeiss();
    }

    /*public void bewegenHoch() {
        zeichneSchwarz();
        this.yKoor = getYKoor() - BEWEGUNG;
        zeichneWeiss();
    }*/

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
