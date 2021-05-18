package Klassen;

import javafx.scene.Group;

public class Schuss extends BeweglicheObjekte {
    private final double YBEWEGUNG = 1;

    protected Schuss(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(5);
        setzeHoehe(10);
    }

//todo Schuss als rechteck erstellen und über x und y Koordinaten bewegen

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
