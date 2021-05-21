package Klassen;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Schuss extends BeweglicheObjekte {
    private final double YBEWEGUNG = 1;

    protected Schuss(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(5);
        setzeHoehe(10);
    }

//todo Schuss als rechteck erstellen und über x und y Koordinaten bewegen

    @Override
    protected void zeichneWeiss(double breite, double hoehe) {
        Rectangle objekt = new Rectangle(xKoor, yKoor, breite, hoehe);
        objekt.setFill(Color.WHITE);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                root.getChildren().add(objekt);
            }
        });
        //this.root.getChildren().add(objekt);
    }

    public void schiessenHoch() {
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor =  erhalteYKoor() - YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    public void schiessenRunter() {
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }
}
