package Klassen;

import java.util.ArrayList;

public class Kontrolloer {
    private boolean richtung = true; //true = rechts; false = links
    private ArrayList<Monster> monster;
    private int tempXKoor;
    private int tempYKoor;
    private int maxXKoor;
    private int minXKoor;
    // macht vielleicht ein Interface für diese Klasse und die Klasse "Bewegliche Objekte" Sinn?
    private final int XBEWEGUNG = 10;
    private final int YBEWEGUNG = 50;
    private final int RANDRECHTS = 545;
    private final int RANDLINKS = 30;

    // todo Datenstruktur ggf. abändern. Auch an die anderen Stellen denken!
    public Kontrolloer(ArrayList<Monster> monster) {
        this.monster = monster;
    }

    public boolean erhalteRichtung() {
        return this.richtung;
    }

    public void setzteRichtung(boolean richtung) {
        this.richtung = richtung;
    }

    private int berechneTempXKoor(int xKoor) {
        if (richtung == true) {
            tempXKoor = xKoor + XBEWEGUNG;
        } else {
            tempXKoor = xKoor - XBEWEGUNG;
        }
        return tempXKoor;
    }

    private int berechneTempYKoor(int yKoor) {
        tempYKoor = yKoor + YBEWEGUNG;
        return tempYKoor;
    }

    public void erhalteAußenXKoor() {
        maxXKoor = 0;
        minXKoor = 600;
        // Herausfinden welches Element (der aktuellen Richung) dem Rand am nächsten steht
        for (Monster monster : this.monster) {    // todo: WHY?
            int ueberpruefendeXKoor = monster.getXKoor();
            if (this.richtung == true) {
                if (ueberpruefendeXKoor > maxXKoor) {
                    maxXKoor = ueberpruefendeXKoor;
                }
            } else {
                if (ueberpruefendeXKoor < minXKoor) {
                    minXKoor = ueberpruefendeXKoor;
                }
            }

        }
    }

    public ArrayList<Monster> ueberprüfenUndBewegen() {
        // Kollision mit Rand überprüfen
            // Bei Kollision Monster runterbewegen und Richtung wechseln
            //  Dabei sollen auch die wirklichen neuen Koordinaten berechnet werden.
            // Keine Kollision einen Schritt weiter in die gemerkte Richtung
            //  Dabei sollen auch die wirklichen neuen Koordinaten berechnet werden.
        erhalteAußenXKoor();
        if (maxXKoor >= RANDRECHTS) {
            for (Monster monster : this.monster) {
                monster.bewegenRunter();
                setzteRichtung(false);
            }
        } else if (maxXKoor < RANDRECHTS && richtung == true) {
            for (Monster monster : this.monster) {
                monster.bewegenRechts();
            }
        }
        if (minXKoor <= RANDLINKS) {
            for (Monster monster : this.monster) {
                monster.bewegenRunter();
                setzteRichtung(true);
            }
        } else if (minXKoor > RANDLINKS && richtung == false) {
            for (Monster monster : this.monster) {
                monster.bewegenLinks();
            }
        }


        // Kollision mit Schuss überprüfen (für jedes Objekt)
            // Element aus der Datenstruktur nehmen (und damit auf den Bildschirm entfernen (ggf. zeichenSchwarz))
        return this.monster;
    }
}
