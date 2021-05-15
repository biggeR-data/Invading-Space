package Klassen;

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
    }
}
