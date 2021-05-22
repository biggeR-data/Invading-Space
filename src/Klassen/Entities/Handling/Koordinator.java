package Klassen.Entities.Handling;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Der Koordinator koordiniert verschiede bewegliche Objekte
 * Überprüfungen von Kollisionen mit Rändern und anderen Objekten
 * Bearbeitung der Gegner- und Schussliste
 */
public class Koordinator {
    // Rechts-Links Bewegung
    private enum xBewegung {
        LINKS,
        RECHTS
    }

    // todo: Alles mögliche Monster in Gegner umwandeln
    private xBewegung richtung = xBewegung.RECHTS;

    // Anlegen von Listen
    private ArrayList<Gegner> gegnerListe;
    private ArrayList<Schuss> schuesseRaumschiff = new ArrayList<Schuss>();
    private ArrayList<Schuss> schuesseMonster = new ArrayList<Schuss>();

    // Koordinaten der Ränder
    private final double RANDRECHTS = 590;
    private final double RANDLINKS = 15;
    private final double RANDUNTENMONSTER = 690;
    private final double RANDUNTENSCHUSS = 670;
    private final double RANDOBEN = 80;

    // Startwert des Punktestands
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

    /**
     * Der Übergebene Schuss des Klasse Raumschiff in die entsprechende Array-List aufnehmen
     * @param schuss
     */
    public void hinzufuegenSchussRaumschiff(Schuss schuss) {
        schuesseRaumschiff.add(schuss);
    }

    /**
     * Ermittlung des Gegners, das dem Raumschiff am nächsten ist
     * Dieser Gegner feuert einen Schuss ab
     * @param xKoorRaumschiff
     */
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

    /**
     * Überprüfen, ob die Gegner an die Seitenränder gelangen
     * @return
     */
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

    /**
     * Die Gegner, je nach ihrer Position, bewegen
     */
    public void ueberprüfenUndBewegenMonster() {
        // Kollision mit Rand überprüfen, Gegner bewegen und ggf. Richtung wechseln
        switch (richtung) {
            case LINKS:
                if (prüfeKollisionRand() == true) {
                    for (Gegner gegner : this.gegnerListe) {
                        gegner.bewegenRunter();
                        gegner.erschaffeObjekt(gegner.erhalteBreite(), gegner.erhalteHoehe());
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
                        gegner.erschaffeObjekt(gegner.erhalteBreite(), gegner.erhalteHoehe());
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

    /**
     * Überprüfen, ob die abgefeuerten Schüsse des Raumschiffs ein Gegner treffen
     * Bei einem Treffer den Schuss aus der Schussliste und der grafischen Oberfläche entfernen
     * Bei einem Treffer den Gegner aus der Gegnerliste und der grafischen Oberfläche entfernen
     * Bei einem Treffer den aktuellen Punktestand um die Punkte des getroffenen Gegners erhöhen
     * Schüsse die keine Gegner treffen werden beim erreichen des oberen Rands entfernt
     */
    public void ueberpruefenMonsterUndBewegenSchuss() {
        // Kollision mit Schuss überprüfen
        // Element aus der Datenstruktur nehmen (und damit auf den Bildschirm entfernen (ggf. zeichenSchwarz))
        ArrayList<Schuss> loescheSchuesse = new ArrayList<Schuss>();
        ArrayList<Gegner> loescheGegner = new ArrayList<Gegner>();

        for (Schuss schuss : schuesseRaumschiff) {
            schuss.schiessenHoch();
            for (Gegner gegner : this.gegnerListe) {
                if (gegner.pruefeKollision(schuss) == true) {
                    score += gegner.erhaltePunkte();
                    // Gegner zerstört und aus der ArrayList gegnerListe entfernen
                    gegner.entferneObjekt();
                    loescheGegner.add(gegner);
                    // Schuss zerstören und aus der ArrayList schusseListe entfernen
                    schuss.entferneObjekt();
                    loescheSchuesse.add(schuss);
                }
            }
        }
        // Überprüfen, ob die Schüsse den oberen Rand erreichen
        for (Schuss schuss : schuesseRaumschiff) {
            if (schuss.pruefeTrefferOben(RANDOBEN) == true) {
                schuss.entferneObjekt();
                loescheSchuesse.add(schuss);
            }
        }
        //  Getroffene Schüsse und Gegner aus den Listen entfernen
        schuesseRaumschiff.removeAll(loescheSchuesse);
        this.gegnerListe.removeAll(loescheGegner);
    }

    /**
     * Überprüfen, ob die Schüsse der Gegner das Raumschiff treffen
     * Bei einem Treffer den Schuss aus der Schussliste entfernen
     * @param raumschiff
     * @return Ein boolescher Wert
     */
    public boolean ueberpruefenRaumschiffUndBewegeSchuss(Raumschiff raumschiff) {
        ArrayList<Schuss> loescheSchuesse = new ArrayList<Schuss>();
        for (Schuss schuss : schuesseMonster) {
            schuss.schiessenRunter();
            if (raumschiff.pruefeKollision(schuss)) {
                return true;
            }
        }
        for (Schuss schuss : schuesseMonster) {
            if (schuss.pruefeTrefferUnten(RANDUNTENSCHUSS) == true) {
                schuss.entferneObjekt();
                loescheSchuesse.add(schuss);
            }
        }
        schuesseMonster.removeAll(loescheSchuesse);
        return false;
    }

    /**
     * Rückgabewert sagt aus, ob die Gegnerliste leer ist und eine neue benötigt wird.
     * @return Ein boolescher Wert
     */
    public boolean neueMonsterListeNotwendig() {
        if (this.gegnerListe.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Neue Gegnerliste übergeben
     * @param gegner
     */
    public void neueMonsterListeUebergeben(ArrayList<Gegner> gegner) {
        Collections.reverse(gegner);
        this.gegnerListe = gegner;
        this.setzteRichtung(xBewegung.RECHTS);
    }

    /**
     * Rückgabewert sagt aus, ob das Spiel "Game Over" und damit verloren ist
     * @return
     */
    public boolean gameOver() {
        for (Gegner gegner : this.gegnerListe) {
            if (gegner.pruefeKollisionUnten(RANDUNTENMONSTER) == true) {
                return true;
            }
        }
        return false;
    }
}
