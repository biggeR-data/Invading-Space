package Klassen;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

    /***************************
        Unterterklasse erstellen
    ***************************/

public class Schuss extends BeweglicheObjekte {
    private final double YBEWEGUNG = 10;

    protected Schuss(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(5);
        setzeHoehe(10);
    }

    /***************************
        Schuss erstellen und Bild zuweisen
        Schuss "fliegt nach oben"
     ***************************/

    public void schiessenHoch() {
        Image img = new Image(getClass().getResource("../Schuss_Blau.png").toExternalForm());
        setzteBild(img);
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor =  erhalteYKoor() - YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    /***************************
        Schuss erstellen und Bild zuweisen
        Schuss "fliegt nach unten"
     ***************************/


    public void schiessenRunter() {
        Image img = new Image(getClass().getResource("../Schuss_Rot.png").toExternalForm());
        setzteBild(img);
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    /***************************
        Prüfen, ob der Schuss am Bildrand angekommen ist
        Obere Rand des Feldes
     ***************************/

    public boolean pruefeKollisionOben(double yRand) {
        if (erhalteYKoor() - YBEWEGUNG < yRand) {
            return true;
        }
        return false;
    }

    /***************************
         Prüfen, ob der Schuss am Bildrand angekommen ist
         Untere Rand des Feldes
     ***************************/

    public boolean pruefeKollisionUnten(double yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }
        return false;
    }
}
