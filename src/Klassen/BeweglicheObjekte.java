package Klassen;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class BeweglicheObjekte {
    public int xKoor; // oben links
    public int yKoor; // oben links

    // Konstanten für die Höhe und Breite des Objekts und die Bewegungseinheit für einen "Schritt"
    // todo: hoehe und breite sind beim Raumschiff und beim Monster unterschiedlich.
    //  reicht eine neue hoehen und breiten Var in der Raumhschiff-Klasse?
    protected final int hoehe = 25;
    protected final int breite = 25;
    protected final int XBEWEGUNG = 10;
    // Image

    // Konstruktor
    protected BeweglicheObjekte(int xKoor, int yKoor) {
        this.xKoor = xKoor;
        this.yKoor = yKoor;
        zeichneWeiss();
    }

    // X und Y Koordinaten
    public int erhalteXKoor() {
        return this.xKoor;
    }

    public int erhalteYKoor() {
        return this.yKoor;
    }

    public int erhalteBreite() {
        return breite;
    }

    public int erhalteHoehe() {
        return hoehe;
    }

    // Rechteck zeichnen schwarz und weiß
    //  todo: GraphicsContext (eine Group ?) muss übergeben werden --> Leon fragen
    //   Bereits im Konstruktor übergeben
    //   Group root = new Group();
    protected void zeichneSchwarz() {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(Color.BLACK);
        //object.fillRect(xKoor, yKoor, breite, hoehe);
        //root.getChildren().add(objekt)
    }

    // todo: Diese Methode anpassen, so wie die zeichneSchwarz()-Methode.
    protected void zeichneWeiss() {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(Color.WHITE);
    }

    // todo: Draw Statement --> nachsehen

    // Muss das Objekt zurückgegeben werden?

    public boolean pruefeKollisionRechts(int xRand) {
        if (erhalteXKoor() + erhalteBreite() + XBEWEGUNG > xRand) {
            return true;
        }
        return false;
    }

    public boolean pruefeKollisionLinks(int xRand) {
        if (erhalteXKoor() - XBEWEGUNG < xRand) {
            return true;
        }
        return false;
    }


    /**
     * Prüft, ob eine Kollision zwischen zwei beweglichenObjekten stattfindet.
     * Die Prüfung erfolgt indem die Flächen der beiden Objekte auf Überlagerung geprüft wird.
     * @param pruefObjekt Das Objekt welches auf Kollision geprüft werden soll
     * @return TRUE wenn eine Kollision stattfindet
     */
    public boolean pruefeKollision(BeweglicheObjekte pruefObjekt) {
        if (pruefObjekt.erhalteXKoor() + pruefObjekt.erhalteBreite() >= this.xKoor &&
            pruefObjekt.erhalteXKoor() <= this.xKoor + breite &&
            pruefObjekt.erhalteYKoor() + pruefObjekt.erhalteHoehe() >= this.yKoor &&
            pruefObjekt.erhalteYKoor() <= this.yKoor + hoehe ) {
            return true;
        }
        return false;
    }
}
