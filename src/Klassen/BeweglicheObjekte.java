package Klassen;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class BeweglicheObjekte {
    public int xKoor; // oben links
    public int yKoor; // oben links

    // Konstanten für die Höhe und Breite des Objekts und die Bewegungseinheit für einen "Schritt"
    protected final int hoehe = 25;
    protected final int breite = 25;
    protected final int XBEWEGUNG = 10;
    // Image

    // Konstruktor
    protected BeweglicheObjekte(int xKoor, int yKoor) {
        this.xKoor = xKoor;
        this.yKoor = yKoor;
    }

    // X und Y Koordinaten
    protected int getXKoor() {
        return this.xKoor;
    }

    protected int getYKoor() {
        return this.yKoor;
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
}
