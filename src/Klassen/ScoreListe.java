package Klassen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ScoreListe {

    private LinkedList<Spieler> spielerliste = new LinkedList<>();
    private Path dokSpielerdaten;

    public ScoreListe(String pfad_zu_spielerdaten) {
        try {
            setDokSpielerdaten(pfad_zu_spielerdaten);
            txtAuslesen();
            absteigendSortieren();
        } catch (DelimException delEx) {
            System.out.println(delEx.getMessage());
        } catch (EmptyException empEx) {
            System.out.println(empEx.getMessage());
        }
    }

    private void setDokSpielerdaten(String pfadZuSpielerdaten) {
        dokSpielerdaten = Paths.get(pfadZuSpielerdaten);
    }

    /**
     * Spielerdaten Textdatei auslesen
     * Spielerliste anlegen
     *
     * @throws DelimException
     * @throws EmptyException
     */
    private void txtAuslesen() throws DelimException, EmptyException {
        try {
            // vorhandene Spielerdaten erfassen
            // Dokument einlesen
            BufferedReader dokLeser = Files.newBufferedReader(dokSpielerdaten);

            String momentaneZeile = dokLeser.readLine();

            // iterativ Spieler pro Zeile anlegen und zwischenspeichern
            while (momentaneZeile != null) {
                StringTokenizer st = new StringTokenizer(momentaneZeile, ",");

                // Spieler anlegen
                Spieler neuerSpieler = new Spieler(st.nextToken());
                neuerSpieler.setzePunkte(Integer.parseInt(st.nextToken()));

                // Spieler hinzufügen
                spielerliste.add(neuerSpieler);

                momentaneZeile = dokLeser.readLine();
            }

            dokLeser.close();

        } catch (java.io.IOException ioEx) {
            System.out.println("Es ist ein Fehler aufgetreten während die Spielerdaten.txt Datei ausgelesen wurde.");
        }
    }

    /**
     * Spielerliste sortieren durch InsertionSort Algorithmus
     */
    private void absteigendSortieren() {
        int laenge = spielerliste.size();

        // benötigt um zwei Elemente tauschen zu können
        Spieler temp;
        int positionstauschIndex;

        for (int indexzeiger = 1; indexzeiger < laenge; indexzeiger++) {
            if (spielerliste.get(indexzeiger).erhaltePunkte() > spielerliste.get(indexzeiger - 1).erhaltePunkte()) {
                // zwischenspeichern des Spielers mit mehr Punkten
                temp = spielerliste.get(indexzeiger);

                // Index zum tauschen zwischenspeichern
                positionstauschIndex = indexzeiger;

                // Tausch vollziehen
                while ((positionstauschIndex > 0) && (spielerliste.get(positionstauschIndex - 1).erhaltePunkte() < temp.erhaltePunkte())) {
                    spielerliste.set(positionstauschIndex, spielerliste.get(positionstauschIndex - 1));
                    positionstauschIndex -= 1;
                }

                // Einfügen des zwischengespeicherten Elements an vorgesehener Stelle
                spielerliste.set(positionstauschIndex, temp);
            }
        }
    }

    /**
     * geänderte Spielerliste über alte Spielerdaten Textdatei überschreiben
     */
    public void txtUpdaten() {
        try {
            BufferedWriter dokSchreiber = Files.newBufferedWriter(dokSpielerdaten, StandardOpenOption.WRITE);

            // Iteration über Spielerliste, pro Spieler Iteration über Linkedlist, Werte per Kommas getrennt in .txt schreiben
            spielerliste.stream().forEach(spieler -> {
                try {
                    dokSchreiber.write(spieler.zuString() + "\n");
                } catch (java.io.IOException ioEx) {
                    System.out.println("Es ist ein Problem aufgetreten beim iterativen abspeichern der Spieler.");
                }
            });

            dokSchreiber.close();

        } catch (java.io.IOException ioEx) {
            System.out.println("Es ist ein Problem aufgetreten bei der Ablage der Spielerdaten.");
        }
    }

    /**
     * sortiert einen neuen Spielereintrag hinzufügen
     *
     * @param neuerSpieler
     */
    public void spielerHinzufuegen(Spieler neuerSpieler) {
        spielerliste.add(neuerSpieler);
        absteigendSortieren();
    }

    /**
     * Zugriff für Highscore Abfrage
     *
     * @param punkteIndex
     * @return Spieler
     */
    public Spieler spielerlisteIndexAusgabe(int punkteIndex) {
        return spielerliste.get(punkteIndex);
    }

}
