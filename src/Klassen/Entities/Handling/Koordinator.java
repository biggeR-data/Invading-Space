package Klassen.Entities.Handling;

import java.util.ArrayList;
import java.util.Collections;

public class Koordinator {
    private enum xBewegung {
        LINKS,
        RECHTS
    }

    private xBewegung richtung = xBewegung.RECHTS; //true = rechts; false = links
    private ArrayList<Gegner> gegnerListe;
    private ArrayList<Schuss> schuesseRaumschiff = new ArrayList<Schuss>();
    private ArrayList<Schuss> schuesseMonster = new ArrayList<Schuss>();
    private final double RANDRECHTS = 590;
    private final double RANDLINKS = 15;
    private final double RANDUNTENMONSTER = 690;
    private final double RANDUNTENSCHUSS = 670;
    private final double RANDOBEN = 80;
    private int score = 0;

    public Koordinator() {
    }

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

    public void hinzufuegenSchussRaumschiff(Schuss schuss) {
        schuesseRaumschiff.add(schuss);
    }

    public void schiessenMonster(double xKoorRaumschiff) {
        ArrayList<Gegner> naechsteGegner = new ArrayList<Gegner>();
        for (Gegner gegner : this.gegnerListe) {
            if (gegner.erhalteXKoor() <= xKoorRaumschiff + 10 && gegner.erhalteXKoor() >= xKoorRaumschiff - 10) {
                naechsteGegner.add(gegner);
            }
        }
        double yKoorMonsterMax = 0;
        for (Gegner gegner : naechsteGegner) {
            if (gegner.erhalteYKoor() >= yKoorMonsterMax) {
                yKoorMonsterMax = gegner.erhalteYKoor();
            }
        }
        for (Gegner gegner : naechsteGegner) {
            if (gegner.erhalteYKoor() == yKoorMonsterMax) {
                schuesseMonster.add(gegner.schiessen());
            }
        }

    }

    private boolean prüfeKollisionRand() {
        if (richtung == xBewegung.RECHTS) {
            for (Gegner gegner : this.gegnerListe) {
                if (gegner.pruefeKollisionRechts(RANDRECHTS) == true) {
                    return true;
                }
            }
        } else {
            for (Gegner gegner : this.gegnerListe) {
                if (gegner.pruefeKollisionLinks(RANDLINKS) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public void ueberprüfenUndBewegenMonster() {
        // Kollision mit Rand überprüfen, Gegner bewegen und ggf. Richtung wechseln
        switch (richtung) {
            case LINKS:
                if (prüfeKollisionRand() == true) {
                    for (Gegner gegner : this.gegnerListe) {
                        gegner.bewegenRunter();
                        gegner.zeichneWeiss(gegner.erhalteBreite(), gegner.erhalteHoehe());
                    }
                    setzteRichtung(xBewegung.RECHTS);
                } else {
                    for (Gegner gegner : this.gegnerListe) {
                        gegner.bewegenLinks();
                    }
                }
                break;

            case RECHTS:
                if (prüfeKollisionRand() == true) {
                    for (Gegner gegner : this.gegnerListe) {
                        gegner.bewegenRunter();
                        gegner.zeichneWeiss(gegner.erhalteBreite(), gegner.erhalteHoehe());
                    }
                    setzteRichtung(xBewegung.LINKS);
                } else {
                    for (Gegner gegner : this.gegnerListe) {
                        gegner.bewegenRechts();
                    }
                }
                break;
        }
    }

    public void ueberpruefenMonsterUndBewegenSchuss() {
        // Kollision mit Schuss überprüfen (für jedes Objekt)
        // Element aus der Datenstruktur nehmen (und damit auf den Bildschirm entfernen (ggf. zeichenSchwarz))
        ArrayList<Schuss> loescheSchuesse = new ArrayList<Schuss>();
        ArrayList<Gegner> loescheGegner = new ArrayList<Gegner>();

        for (Schuss schuss : schuesseRaumschiff) {
            schuss.schiessenHoch();
            for (Gegner gegner : this.gegnerListe) {
                if (gegner.pruefeKollision(schuss) == true) {
                    score += gegner.erhaltePunkte();
                    // Gegner zerstört und aus der ArrayList gegnerListe entfernen
                    gegner.zeichneSchwarz(gegner.erhalteBreite(), gegner.erhalteHoehe());
                    loescheGegner.add(gegner);
                    // Schuss zerstören und aus der ArrayList schusseListe entfernen
                    schuss.zeichneSchwarz(schuss.erhalteBreite(), schuss.erhalteHoehe());
                    loescheSchuesse.add(schuss);
                }
            }
        }
        for (Schuss schuss : schuesseRaumschiff) {
            if (schuss.pruefeKollisionOben(RANDOBEN) == true) {
                schuss.zeichneSchwarz(schuss.erhalteBreite(), schuss.erhalteHoehe());
                loescheSchuesse.add(schuss);
            }
        }
        schuesseRaumschiff.removeAll(loescheSchuesse);
        this.gegnerListe.removeAll(loescheGegner);
    }

    public boolean ueberpruefenRaumschiffUndBewegeSchuss(Raumschiff raumschiff) {
        ArrayList<Schuss> loescheSchuesse = new ArrayList<Schuss>();
        for (Schuss schuss : schuesseMonster) {
            schuss.schiessenRunter();
            if (raumschiff.pruefeKollision(schuss)) {
                return true;
            }
        }
        for (Schuss schuss : schuesseMonster) {
            if (schuss.pruefeKollisionUnten(RANDUNTENSCHUSS) == true) {
                schuss.zeichneSchwarz(schuss.erhalteBreite(), schuss.erhalteHoehe());
                loescheSchuesse.add(schuss);
            }
        }
        schuesseMonster.removeAll(loescheSchuesse);
        return false;
    }

    public boolean neueMonsterListeNotwendig() {
        if (this.gegnerListe.isEmpty()) {
            return true;
        }
        return false;
    }

    public void neueMonsterListeUebergeben(ArrayList<Gegner> gegner) {
        Collections.reverse(gegner);
        this.gegnerListe = gegner;
        this.setzteRichtung(xBewegung.RECHTS);
    }

    public boolean gameOver() {
        for (Gegner gegner : this.gegnerListe) {
            if (gegner.pruefeKollisionUnten(RANDUNTENMONSTER) == true) {
                return true;
            }
        }
        return false;
    }
}
