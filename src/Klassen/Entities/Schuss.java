package Klassen.Entities;

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
    public void schiessenRaumschiff() {
        // Aussehen setzen
        Image bild = erhalteBild("Schuss_Blau.png");
        setzeBild(bild);

        // Bewegung
        entferneObjekt();
        this.yKoor = erhalteYKoor() - YBEWEGUNG;
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Schuss der Gegner
     */
    public void schiessenGegner() {
        // Aussehen setzen
        Image img = erhalteBild("Schuss_Rot.png");
        setzeBild(img);

        // Bewegung
        entferneObjekt();
        this.yKoor = erhalteYKoor() + YBEWEGUNG;
        erschaffeObjekt(erhalteBreite(), erhalteHoehe());
    }

    /**
     * Treffertest der Gegner
     *
     * @param yRand Höhenlimit
     * @return boolean Wahr, falls ein Treffer vorliegt
     */
    public boolean pruefeGegnerGetroffen(double yRand) {
        if (erhalteYKoor() - YBEWEGUNG < yRand) {
            return true;
        }

        return false;
    }

    /**
     * Treffertest des Raumschiffs
     *
     * @param yRand Höhenlimit
     * @return boolean Wahr, falls ein Treffer vorliegt
     */
    public boolean pruefeRaumschiffGetroffen(double yRand) {
        if (erhalteYKoor() + YBEWEGUNG > yRand) {
            return true;
        }

        return false;
    }

}
