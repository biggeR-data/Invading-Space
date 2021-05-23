package Klassen.Entities.Handling;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;

import java.io.FileInputStream;

/**
 * Grundklasse aller beweglichen Objekte
 * Festlegung von Maßen, Position, Bild
 * Bewegungfunktionalität Grundgerüst
 */
public abstract class BeweglicheObjekte {

    // Standard Attribute
    private static final double STANDARD_HOEHE = 25;
    protected static final double STANDARD_BREITE = 25;
    protected static final double STANDARD_XBEWEGUNG = 10;
    protected Image STANDART_BILD;

    // Position
    public double xKoor;
    public double yKoor;

    // Maße
    private double hoehe = STANDARD_HOEHE;
    private double breite = STANDARD_BREITE;

    private double xBewegung = STANDARD_XBEWEGUNG;

    // Aussehen
    private Rectangle zeichenObjekt;
    private Image bild;

    public Group root;

    /**
     * Position und Aussehen festlegen
     *
     * @param xKoor
     * @param yKoor
     * @param root
     */
    protected BeweglicheObjekte(double xKoor, double yKoor, Group root) {
        // Layout
        this.xKoor = xKoor;
        this.yKoor = yKoor;
        this.root = root;

        // Aussehen
        STANDART_BILD = erhalteBild("Transparent.png");
        bild = STANDART_BILD;
    }

    public double erhalteXKoor() {
        return xKoor;
    }

    public double erhalteYKoor() {
        return yKoor;
    }

    public double erhalteBreite() {
        return breite;
    }

    protected void setzeBreite(double breite) {
        this.breite = breite;
    }

    public double erhalteHoehe() {
        return hoehe;
    }

    protected void setzeHoehe(double hoehe) {
        this.hoehe = hoehe;
    }

    public double erhalteXBewegung() {
        return xBewegung;
    }

    protected void setzeBild(Image image) {
        bild = image;
    }

    public Group erhalteGroup() {
        return this.root;
    }

    /**
     * Enferne das entsprechende Objekt von der grafischen Oberfläche
     */
    public void entferneObjekt() {
        if (zeichenObjekt != null) {
            zeichenObjekt.setFill(Color.BLACK);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root.getChildren().remove(zeichenObjekt);
            }
        });
    }

    /**
     * Zeichne ein rechteckiges Objekt mit ausgewähltem Image auf der grafischen Oberfläche
     *
     * @param breite
     * @param hoehe
     */
    public void erschaffeObjekt(double breite, double hoehe) {
        zeichenObjekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        zeichenObjekt.setFill(new ImagePattern(bild));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (root.getChildren() != null && root.getChildren().contains(zeichenObjekt)) {
                    return;
                }
                root.getChildren().add(zeichenObjekt);
            }
        });
    }

    /**
     * Kollision von einem Objekt mit dem rechten Rand überprüfen
     *
     * @param xRand
     * @return boolean
     */
    public boolean pruefeKollisionRechts(double xRand) {
        if (erhalteXKoor() + erhalteBreite() + xBewegung > xRand) {
            return true;
        }

        return false;
    }

    /**
     * Kollision von einem Objekt mit dem linken Rand überprüfen
     *
     * @param xRand
     * @return boolean Wahr, wenn der linke Rand überschritten werden würde
     */
    public boolean pruefeKollisionLinks(double xRand) {
        if (erhalteXKoor() - xBewegung < xRand) {
            return true;
        }

        return false;
    }

    /**
     * Test, ob Schuss des Raumschiffs oder des Gegners den Gegenüber treffen
     *
     * @param pruefObjekt Objekt, welches auf Überlagerung getestet wird
     * @return boolean Wahr, falls zwei Objekte sich überlagern
     */
    public boolean pruefeGetroffen(BeweglicheObjekte pruefObjekt) {
        if (pruefObjekt.erhalteXKoor() + pruefObjekt.erhalteBreite() >= this.xKoor &&
                pruefObjekt.erhalteXKoor() <= this.xKoor + breite &&
                pruefObjekt.erhalteYKoor() + pruefObjekt.erhalteHoehe() >= this.yKoor &&
                pruefObjekt.erhalteYKoor() <= this.yKoor + hoehe) {
            return true;
        }

        return false;
    }

    /**
     * simplifizierter Zugriff um Grafiken Entitäten zuzuschreiben
     *
     * @param name Dateiname
     * @return Image Aussehen des Objekts anhand einer Grafik
     */
    protected static Image erhalteBild(String name) {
        try {
            return new Image(new FileInputStream("./res/Images/" + name));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
