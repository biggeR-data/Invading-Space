package Klassen;

import java.util.ArrayList;
import java.util.Collections;

public class Koordinator {
    private enum xBewegung {
        LINKS,
        RECHTS
    }
    private xBewegung richtung = xBewegung.RECHTS; //true = rechts; false = links
    private ArrayList<Monster> monsterListe;
    private ArrayList<Schuss> schuesseListe = new ArrayList<Schuss>();
    private final double RANDRECHTS = 590;
    private final double RANDLINKS = 15;
    private final double RANDUNTENMONSTER = 690;
    private final double RANDUNTENSCHUSS = 700;
    private final double RANDOBEN = 80;
    private int score = 0;

    public Koordinator() {}

    public int erhalteScore() {
        return score;
    }

    private void setzteRichtung(xBewegung richtung) {
        this.richtung = richtung;
    }

    public final double erhalteRandRechts() {
        return RANDRECHTS;
    }

    public final double erhalteRandLinks() {
        return RANDLINKS;
    }

    public void hinzufuegenSchuss(Schuss schuss) {
        schuesseListe.add(schuss);
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
                if (monster.pruefeKollisionLinks(RANDLINKS) == true) {
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
                        monster.zeichneWeiss(monster.erhalteBreite(), monster.erhalteHoehe());
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
                        monster.zeichneWeiss(monster.erhalteBreite(), monster.erhalteHoehe());
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

    public void ueberpruefenUndBewegenSchuss() {
        // Kollision mit Schuss überprüfen (für jedes Objekt)
        // Element aus der Datenstruktur nehmen (und damit auf den Bildschirm entfernen (ggf. zeichenSchwarz))
        ArrayList<Schuss> loescheSchuesse = new ArrayList<Schuss>();
        ArrayList<Monster> loescheMonster = new ArrayList<Monster>();

        for (Schuss schuss : schuesseListe) {
            schuss.schiessenHoch();
            for (Monster monster : this.monsterListe) {
                if (monster.pruefeKollision(schuss) == true) {
                    score += monster.erhaltePunkte();
                    // Monster zerstört und aus der ArrayList monsterListe entfernen
                    monster.zeichneSchwarz(monster.erhalteBreite(), monster.erhalteHoehe());
                    loescheMonster.add(monster);
                    // Schuss zerstören und aus der ArrayList schusseListe entfernen
                    schuss.zeichneSchwarz(schuss.erhalteBreite(), schuss.erhalteHoehe());
                    loescheSchuesse.add(schuss);
                }
            }
        }
        for (Schuss schuss : schuesseListe) {
            if (schuss.pruefeKollisionOben(RANDOBEN) == true) {
                schuss.zeichneSchwarz(schuss.erhalteBreite(), schuss.erhalteHoehe());
                loescheSchuesse.add(schuss);
            }
        }
        schuesseListe.removeAll(loescheSchuesse);
        this.monsterListe.removeAll(loescheMonster);
    }

    public boolean ueberprüfeRaumschiffGetroffen(Raumschiff raumschiff) {
        for (Schuss schuss : schuesseListe) {
            schuss.schiessenRunter();
            if (raumschiff.pruefeKollision(schuss)) {
                return true;
            }
        }
        return false;
    }

    public boolean neueMonsterListeNotwendig() {
        if (this.monsterListe.isEmpty()) {
            return true;
        }
        return false;
    }

    public void neueMonsterListeUebergeben(ArrayList<Monster> monster) {
        Collections.reverse(monster);
        this.monsterListe = monster;
        this.setzteRichtung(xBewegung.RECHTS);
    }

    public boolean gameOver() {
        for (Monster monster : this.monsterListe) {
            if (monster.pruefeKollisionUnten(RANDUNTENMONSTER) == true) {
                return true;
            }
        }
        return false;
    }
}
