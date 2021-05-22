package Klassen.Entities.Handling;

import javafx.scene.Group;
import javafx.scene.image.Image;

/**
 * wird genutzt um einen Kampf zu simulieren
 */
public class Schuss extends BeweglicheObjekte {

    private final double YBEWEGUNG = 10;

    protected Schuss(double xKoor, double yKoor, Group root) {
        super(xKoor, yKoor, root);
        setzeBreite(5);
        setzeHoehe(10);
    }

    /**
     * Schuss des Raumschiffs
     */
    public void schiessenHoch() {
        Image bild = erhalteBild("Schuss_Blau.png");
        setzteBild(bild);
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor = erhalteYKoor() - YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Schuss der Gegner
     */
    public void schiessenRunter() {
        Image img = erhalteBild("Schuss_Rot.png");
        setzteBild(img);
        zeichneSchwarz(erhalteBreite(), erhalteHoehe());
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        zeichneWeiss(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Treffertest der Gegner
     * @param yRand
     * @return boolean
     */
    public boolean pruefeKollisionOben(double yRand) {
        if (erhalteYKoor() - YBEWEGUNG < yRand) {
            return true;
        }
        return false;
    }

    /**
     * Treffertest des Raumschiffs
     * @param yRand
     * @return boolean
     */
    public boolean pruefeKollisionUnten(double yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }
        return false;
    }
}
