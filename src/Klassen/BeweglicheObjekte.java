package Klassen;

import javafx.application.Platform;
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
    private Rectangle zeichenObjekt;
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
    /**
     * Erhalte die aktuelle X-Koordinate des Objekts
     * @return Einen double-Wert
     */
    public double erhalteXKoor() {
        return this.xKoor;
    }

    /**
     * Erhalte die aktuelle Y-Koordinate des Objekts
     * @return Einen double-Wert
     */
    public double erhalteYKoor() {
        return this.yKoor;
    }

    // Erhalten Höhe und Breite

    /**
     * Erhalte die aktuelle Breite des Objekts
     * @return Einen double-Wert
     */
    public double erhalteBreite() {
        return breite;
    }

    /**
     * Erhalte die aktuelle Höhe des Objekts
     * @return Einen double-Wert
     */
    public double erhalteHoehe() {
        return hoehe;
    }

    // Setzte Höhe und Breite
    /**
     * Setze eine neue Höhe für das akuelle Objekt
     * @param hoehe Die neue Höhe
     */
    protected void setzeHoehe(double hoehe) {
        this.hoehe = hoehe;
    }

    /**
     * Setze eine neue Breite für das aktuelle Objekt
     * @param breite Die nee Breite
     */
    protected void setzeBreite(double breite) {
        this.breite = breite;
    }

    // xBewegung
    /**
     * Setze eine neue Größe für den Schritt der X-Achse entlang
     * @param xBewegung Die neue X-Bewegung
     */
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
        //Rectangle objekt  = new Rectangle(xKoor, yKoor, breite, hoehe);
        if (zeichenObjekt != null) {
            zeichenObjekt.setFill(Color.BLACK);
        }
        Platform.runLater(new Runnable() {
            @Override public void run() {
                root.getChildren().remove(zeichenObjekt);
            }
        });
        //this.root.getChildren().add(objekt);
    }

    protected void zeichneWeiss(double breite, double hoehe) {
        zeichenObjekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        zeichenObjekt.setFill(new ImagePattern(img));
        Platform.runLater(new Runnable() {
            @Override public void run() {
                if (root.getChildren() != null && root.getChildren().contains(zeichenObjekt)) {
                    return;
                }
                root.getChildren().add(zeichenObjekt);
            }
        });
        //this.root.getChildren().add(objekt);
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
