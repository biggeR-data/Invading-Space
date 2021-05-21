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
            System.out.println("Problem :(");
        }
    }

    private void absteigendSortieren() {
        this.spielerliste.sort((spieler1, spieler2) -> spieler2.getPunkte() - spieler1.getPunkte());
    }

    public void txtUpdaten() {
        // Spiel soll beendet werden -> sync Spielerdaten in .txt
        try {
            BufferedWriter meinWriter = Files.newBufferedWriter(this.dok_spielerdaten, StandardOpenOption.WRITE);

            // Iteration über Spieler Liste, pro Spieler Iteration über Treeset, Werte ber Kommas getrennt in .txt schreiben
            spielerliste.stream().forEach(spieler -> {
                try {
                    meinWriter.write(spieler + "\n");
                } catch (java.io.IOException e) {
                    System.out.println("problem :(");
                }
            });

            meinWriter.close();

        } catch (java.io.IOException e) {
            System.out.println("problem :(");
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
