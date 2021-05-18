package Klassen;

import javafx.scene.Group;

public class Schuss extends BeweglicheObjekte {
    private final double YBEWEGUNG = 1;

    protected Schuss(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
    }
}
//todo Schuss als rechteck erstellen und über x und y Koordinaten bewegen

    public void schiessenHoch() {
        zeichneSchwarz();
        this.yKoor =  erhalteYKoor() - YBEWEGUNG;
        zeichneWeiss();
    }

    public void schiessenRunter() {
        zeichneSchwarz();
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        zeichneWeiss();
    }