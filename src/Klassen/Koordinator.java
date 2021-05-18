package Klassen;

import java.util.ArrayList;

public class Koordinator {
    private enum xBewegung {
        LINKS,
        RECHTS
    }
    private xBewegung richtung = xBewegung.RECHTS; //true = rechts; false = links
    private ArrayList<Monster> monsterListe;
    private final double RANDRECHTS = 545;
    private final double RANDLINKS = 30;
    private final double RANDUNTEN = 640;

    // todo Datenstruktur ggf. abändern. Auch an die anderen Stellen denken!
    public Koordinator(ArrayList<Monster> monster) {
        this.monsterListe = monster;
    }

    private void setzteRichtung(xBewegung richtung) {
        this.richtung = richtung;
    }


    private boolean prüfeKollisionRand() {
        if (richtung == xBewegung.RECHTS) {
            for (Monster monster : this.monsterListe) {
                if (monster.pruefeKollisionRechts(RANDRECHTS) == true) {
                    return true;
                }
            }
        } else {
            for (Monster monster : this.monsterListe) {
                if (monster.pruefeKollisionRechts(RANDLINKS) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public void ueberprüfenUndBewegenMonster() {
        // Kollision mit Rand überprüfen, Monster bewegen und ggf. Richtung wechseln
        switch (richtung) {
            case LINKS:
                if (prüfeKollisionRand() == true) {
                    for (Monster monster : this.monsterListe) {
                        monster.bewegenRunter();
                    }
                    setzteRichtung(xBewegung.RECHTS);
                } else {
                    for (Monster monster : this.monsterListe) {
                        monster.bewegenLinks();
                    }
                }
                break;

            case RECHTS:
                if (prüfeKollisionRand() == true) {
                    for (Monster monster : this.monsterListe) {
                        monster.bewegenRunter();
                    }
                    setzteRichtung(xBewegung.LINKS);
                } else {
                    for (Monster monster : this.monsterListe) {
                        monster.bewegenRechts();
                    }
                }
                break;
        }
    }

    /*public ArrayList<Schuss> ueberpruefenUndBewegenSchuss(ArrayList<Schuss> schuesseListe) {
        // Kollision mit Schuss überprüfen (für jedes Objekt)
        // Element aus der Datenstruktur nehmen (und damit auf den Bildschirm entfernen (ggf. zeichenSchwarz))
        for (Schuss schuss : schuesseListe) {
            schuss.bewegeHoch();
            for (Monster monster : this.monsterListe) {
                if (monster.pruefeKollision(schuss) == true) {
                    // Monster zerstört und aus der ArrayList monsterListe entfernen
                    monster.zeichneSchwarz();
                    monsterListe.remove(monster);
                    // Schuss zerstören und aus der ArrayList schusseListe entfernen
                    schuss.zeichneSchwarz();
                    schuesseListe.remove(schuss);
                }
            }
        }
        return schuesseListe;
    }*/

    /*public boolean gameOver() {
        if (this.monsterListe.isEmpty()) {
            return true;
        }
        // todo: Prüfen ob Monster mit Raumschiff kollidiert bzw. eine bestimmte Höhe erreicht.
        for (Monster monster : this.monsterListe) {
            if (monster.pruefeKollisionUnten(RANDUNTEN) == true) {
                return true;
            }
        }
        return false;
    }*/
}