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
        } catch (DelimException ex) {
            System.out.println(ex.getMessage());
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setDokSpielerdaten(String pfadZuSpielerdaten) {
        this.dokSpielerdaten = Paths.get(pfadZuSpielerdaten);
    }

    private void txtAuslesen() throws DelimException, EmptyException {
        try {
            // vorhandene Spielerdaten erfassen
            // Dokument einlesen
            BufferedReader dokLeser = Files.newBufferedReader(this.dokSpielerdaten);

            String momentaneZeile = dokLeser.readLine();

            // iterativ Spieler pro Zeile anlegen und zwischenspeichern
            while (momentaneZeile != null) {
                StringTokenizer st = new StringTokenizer(momentaneZeile, ",");

                // Spieler anlegen
                Spieler neuerSpieler = new Spieler(st.nextToken());
                neuerSpieler.setzePunkte(Integer.parseInt(st.nextToken()));

                // Spieler hinzufügen
                this.spielerliste.add(neuerSpieler);

                momentaneZeile = dokLeser.readLine();
            }

            dokLeser.close();

        } catch (java.io.IOException ex) {
            System.out.println("Es ist ein Fehler aufgetreten während die Spielerdaten.txt Datei ausgelesen wurde.");
        }
    }

    private void absteigendSortieren() {
        // InsertionSort
        int laenge = this.spielerliste.size();

        // benötigt um zwei Elemente tauschen zu können
        Spieler temp;
        int positionstauschIndex;

        for (int indexzeiger = 1; indexzeiger < laenge; indexzeiger++) {
            if (this.spielerliste.get(indexzeiger).erhaltePunkte() > this.spielerliste.get(indexzeiger - 1).erhaltePunkte()) {
                // zwischenspeichern des Spielers mit mehr Punkten
                temp = this.spielerliste.get(indexzeiger);
                // Index zum tauschen zwischenspeichern
                positionstauschIndex = indexzeiger;
                while ((positionstauschIndex > 0) && (this.spielerliste.get(positionstauschIndex - 1).erhaltePunkte() < temp.erhaltePunkte())) {
                    this.spielerliste.set(positionstauschIndex, this.spielerliste.get(positionstauschIndex - 1));
                    positionstauschIndex -= 1;
                }
                // Einfügen des zwischengespeicherten Elements an vorgesehener Stelle
                this.spielerliste.set(positionstauschIndex, temp);
            }
        }
    }

    public void txtUpdaten() {
        // Spiel soll beendet werden -> sync Spielerdaten in .txt
        try {
            BufferedWriter dokSchreiber = Files.newBufferedWriter(this.dokSpielerdaten, StandardOpenOption.WRITE);

            // Iteration über Spielerliste, pro Spieler Iteration über Linkedlist, Werte per Kommas getrennt in .txt schreiben
            spielerliste.stream().forEach(spieler -> {
                try {
                    dokSchreiber.write(spieler.zuString() + "\n");
                } catch (java.io.IOException ex) {
                    System.out.println("Es ist ein Problem aufgetreten beim iterativen abspeichern der Spieler.");
                }
            });

            dokSchreiber.close();

        } catch (java.io.IOException ex) {
            System.out.println("Es ist ein Problem aufgetreten bei der Ablage der Spielerdaten.");
        }
    }

    public void spielerHinzufuegen(Spieler neuerSpieler) {
        this.spielerliste.add(neuerSpieler);
        absteigendSortieren();
    }

    public Spieler spielerlisteIndexAusgabe(int punkteIndex) {
        return this.spielerliste.get(punkteIndex);
    }

}
