package Klassen;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class BeweglicheObjekte {
    public double xKoor; // oben links
    public double yKoor; // oben links
    public Group root;
    // Konstanten für die Höhe und Breite des Objekts und die Bewegungseinheit für einen "Schritt"
    // todo: hoehe und breite sind beim Raumschiff und beim Monster unterschiedlich.
    //  reicht eine neue hoehen und breiten Var in der Raumhschiff-Klasse?
    protected final double hoehe = 40;
    protected final double breite = 40;
    protected final double XBEWEGUNG = 10;
    // Image

    // Konstruktor
    protected BeweglicheObjekte(double xKoor, double yKoor, Group root) {
        this.xKoor = xKoor;
        this.yKoor = yKoor;
        this.root = root;
        zeichneWeiss();
    }

    // X und Y Koordinaten
    public double erhalteXKoor() {
        return this.xKoor;
    }

    public double erhalteYKoor() {
        return this.yKoor;
    }

    // Höhe und Breite
    public double erhalteBreite() {
        return breite;
    }

    public double erhalteHoehe() {
        return hoehe;
    }

    // Group Root
    public Group erhalteGroup() {
        return this.root;
    }

    // Rechteck zeichnen schwarz und weiß
    //  todo: GraphicsContext (eine Group ?) muss übergeben werden --> Leon fragen
    //   Bereits im Konstruktor übergeben
    //   Group root = new Group();
    protected void zeichneSchwarz() {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(Color.BLACK);
        this.root.getChildren().add(objekt);
    }

    // todo: Diese Methode anpassen, so wie die zeichneSchwarz()-Methode.
    protected void zeichneWeiss() {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(Color.WHITE);
        this.root.getChildren().add(objekt);
    }

    // todo: Draw Statement --> nachsehen

    // Muss das Objekt zurückgegeben werden?

    public boolean pruefeKollisionRechts(double xRand) {
        if (erhalteXKoor() + erhalteBreite() + XBEWEGUNG > xRand) {
            return true;
        }
        return false;
    }

    public boolean pruefeKollisionLinks(double xRand) {
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
