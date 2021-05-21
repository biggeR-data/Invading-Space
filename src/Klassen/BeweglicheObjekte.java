package Klassen;

import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

//import java.awt.*;
import javafx.scene.image.Image;

public abstract class BeweglicheObjekte {
    // Konstanten für die Höhe und Breite des Objekts und die Bewegungseinheit für einen "Schritt"
    private static final double STANDARD_HOEHE = 25;
    protected static final double STANDARD_BREITE= 25;
    protected static final double STANDARD_XBEWEGUNG = 10;
    protected final Image STANDART_BILD = new Image(getClass().getResource("../Transparent.png").toExternalForm());
    public double xKoor; // von oben links
    public double yKoor; // von oben links
    private double hoehe = STANDARD_HOEHE;
    private double breite = STANDARD_BREITE;
    private double xBewegung = STANDARD_XBEWEGUNG;
    public Group root;
    private Image img = STANDART_BILD;

    // Konstruktor
    protected BeweglicheObjekte(double xKoor, double yKoor, Group root) {
        this.xKoor = xKoor;
        this.yKoor = yKoor;
        this.root = root;
        //zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    // X und Y Koordinaten
    public double erhalteXKoor() {
        return this.xKoor;
    }

    public double erhalteYKoor() {
        return this.yKoor;
    }

    // Erhalten Höhe und Breite
    public double erhalteBreite() {
        return breite;
    }

    public double erhalteHoehe() {
        return hoehe;
    }

    // Setzte Höhe und Breite
    protected void setzeHoehe(double hoehe) {
        this.hoehe = hoehe;
    }

    protected void setzeBreite(double breite) {
        this.breite = breite;
    }

    // xBewegung
    protected void setzeXBewegung(double xBewegung) {
        this.xBewegung = xBewegung;
    }

    public double erhalteXBewegung() {
        return xBewegung;
    }

    // Setzte Bild
    protected void setzteBild(Image image) {
        img = image;
    }

    // Group Root
    public Group erhalteGroup() {
        return this.root;
    }

    // Rechteck zeichnen schwarz und weiß
    protected void zeichneSchwarz(double breite, double hoehe) {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(Color.BLACK);
        this.root.getChildren().add(objekt);
    }

    protected void zeichneWeiss(double breite, double hoehe) {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(new ImagePattern(img));
        this.root.getChildren().add(objekt);
    }

    // Kollisionen von Objekten mit dem rechten Spielrand überprüfen
    public boolean pruefeKollisionRechts(double xRand) {
        if (erhalteXKoor() + erhalteBreite() + xBewegung > xRand) {
            return true;
        }
        return false;
    }

    // Kollisionen von Objekten mit dem linken Spielrand überprüfen
    public boolean pruefeKollisionLinks(double xRand) {
        if (erhalteXKoor() - xBewegung < xRand) {
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
            pruefObjekt.erhalteYKoor() <= this.yKoor + hoehe) {
            return true;
        }
        return false;
    }

}
