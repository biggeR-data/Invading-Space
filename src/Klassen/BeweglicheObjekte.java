package Klassen;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

//import java.awt.*;
import javafx.scene.image.Image;

import java.io.FileInputStream;

public abstract class BeweglicheObjekte {
    // Konstanten für die Höhe und Breite des Objekts und die Bewegungseinheit für einen "Schritt"
    private static final double STANDARD_HOEHE = 25;
    protected static final double STANDARD_BREITE= 25;
    protected static final double STANDARD_XBEWEGUNG = 10;
    protected Image STANDART_BILD;
    public double xKoor; // von oben links
    public double yKoor; // von oben links
    private double hoehe = STANDARD_HOEHE;
    private double breite = STANDARD_BREITE;
    private double xBewegung = STANDARD_XBEWEGUNG;
    private Rectangle zeichenObjekt;
    public Group root;
    private Image img;

    // Konstruktor
    protected BeweglicheObjekte(double xKoor, double yKoor, Group root) {
        this.xKoor = xKoor;
        this.yKoor = yKoor;
        this.root = root;
        STANDART_BILD = erhalteBild("Transparent.png");
        img = STANDART_BILD;
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
     * @param breite Die neue Breite
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

    /**
     * Erhalte die Größe für den Schritt der X-Achse entlang
     * @return Einen double-Wert
     */
    public double erhalteXBewegung() {
        return xBewegung;
    }

    /**
     * Setze ein neues Bild für die Objekte
     * @param image Das neue Bild der JavaFX-Klasse Image
     */
    protected void setzteBild(Image image) {
        img = image;
    }

    /**
     * Erhalte die aktuelle Group
     * @return Eine Group
     */
    public Group erhalteGroup() {
        return this.root;
    }

    /**
     * Enferne das entsprechende Objekt von grafischen Oberfläche
     * @param breite Ein double-Wert
     * @param hoehe Ein double-Wert
     */
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
    // todo: refactor zeichneSchwarz zu entferneObjekt oder so
    //  und zeichneWeiss zu zeichneObjekt oder so

    /**
     * Zeichne ein rechteckiges Objekt mit ausgewähltem Image auf der grafischen Oberfläche
     * @param breite Ein double-Wert
     * @param hoehe Ein double-Wert
     */
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

    /**
     * Kollision von einem Objekt mit dem rechten Rand überprüfen
     * @param xRand Ein double-Wert
     * @return Ein boolescher Wert
     */
    public boolean pruefeKollisionRechts(double xRand) {
        if (erhalteXKoor() + erhalteBreite() + xBewegung > xRand) {
            return true;
        }
        return false;
    }

    /**
     * Kollision von einem Objekt mit dem linken Rand überprüfen
     * @param xRand Ein double-Wert
     * @return Ein boolescher Wert
     */
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

    protected static Image erhalteBild(String name) {
        try {
            return new Image(new FileInputStream("./res/Images/" + name));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
