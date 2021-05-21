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
    private Path dok_spielerdaten;

    public ScoreListe(String pfad_zu_spielerdaten) {
        try {
            setDok_spielerdaten(pfad_zu_spielerdaten);
            txtAuslesen();
            absteigendSortieren();
        } catch (DelimException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setDok_spielerdaten(String pfad_zu_spielerdaten) {
        this.dok_spielerdaten = Paths.get(pfad_zu_spielerdaten);
    }

    private void txtAuslesen() throws DelimException {
        try {
            // vorhandene Spielerdaten erfassen
            // Dokument einlesen
            BufferedReader dok_leser = Files.newBufferedReader(this.dok_spielerdaten);

            String momentane_zeile = dok_leser.readLine();

            // iterativ Spieler pro Zeile anlegen und zwischenspeichern
            while (momentane_zeile != null) {
                StringTokenizer st = new StringTokenizer(momentane_zeile, ",");

                // Spieler anlegen
                Spieler neuer_spieler = new Spieler(st.nextToken());
                neuer_spieler.setPunkte(Integer.parseInt(st.nextToken()));

                // Spieler hinzufügen
                this.spielerliste.add(neuer_spieler);

                momentane_zeile = dok_leser.readLine();
            }

            dok_leser.close();

        } catch (java.io.IOException e) {
            System.out.println("Es ist ein Fehler aufgetreten während die Spielerdaten.txt Datei ausgelesen wurde.");
        }
    }

    private void absteigendSortieren() {
        // InsertionSort
        int laenge = this.spielerliste.size();

        // benötigt um zwei Elemente tauschen zu können
        Spieler temp;
        int positionstausch_index;

        for (int indexzeiger = 1; indexzeiger < laenge; indexzeiger++) {
            if (this.spielerliste.get(indexzeiger).getPunkte() > this.spielerliste.get(indexzeiger - 1).getPunkte()) {
                // zwischenspeichern des Spielers mit mehr Punkten
                temp = this.spielerliste.get(indexzeiger);
                // Index zum tauschen zwischenspeichern
                positionstausch_index = indexzeiger;
                while ((positionstausch_index > 0) && (this.spielerliste.get(positionstausch_index - 1).getPunkte() < temp.getPunkte())) {
                    this.spielerliste.set(positionstausch_index, this.spielerliste.get(positionstausch_index - 1));
                    positionstausch_index -= 1;
                }
                // Einfügen des Elements an vorgesehener Stelle
                this.spielerliste.set(positionstausch_index, temp);
            }
        }
    }

    public void txtUpdaten() {
        // Spiel soll beendet werden -> sync Spielerdaten in .txt
        try {
            BufferedWriter dok_schreiber = Files.newBufferedWriter(this.dok_spielerdaten, StandardOpenOption.WRITE);

            // Iteration über Spielerliste, pro Spieler Iteration über Linkedlist, Werte per Kommas getrennt in .txt schreiben
            spielerliste.stream().forEach(spieler -> {
                try {
                    dok_schreiber.write(spieler + "\n");
                } catch (java.io.IOException e) {
                    System.out.println("Es ist ein Problem aufgetreten beim iterativen abspeichern der Spieler.");
                }
            });

            dok_schreiber.close();

        } catch (java.io.IOException e) {
            System.out.println("Es ist ein Problem aufgetreten bei der Ablage der Spielerdaten.");
        }
    }

    public void spielerHinzufuegen(Spieler neuer_spieler) {
        this.spielerliste.add(neuer_spieler);
        absteigendSortieren();
    }

    public Spieler spielerlisteIndexAusgabe(int punkte_index) {
        return this.spielerliste.get(punkte_index);
    }

}
