package Testing;

import Klassen.Spieler;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class TestSpieler {

    public static void main(String[] args) {
        // todo Exception wenn ein Komma im Namen ist
        LinkedList<Spieler> spielerliste = new LinkedList<>();
        List<Spieler> highscoreliste;
        Path dok_spielerdaten = Paths.get("res/spielerdaten.txt");
        try {
            // vorhandene Spielerdaten erfassen
            // Dokument einlesen
            BufferedReader dok_leser = Files.newBufferedReader(dok_spielerdaten);

            String momentane_zeile = dok_leser.readLine();

            // iterativ Spieler pro Zeile anlegen und zwischenspeichern
            while (momentane_zeile != null) {
                StringTokenizer st = new StringTokenizer(momentane_zeile, ",");

                // Spieler anlegen
                Spieler neuer_spieler = new Spieler(st.nextToken());
                neuer_spieler.setPunkte(Integer.parseInt(st.nextToken()));

                // Spieler hinzufügen
                spielerliste.add(neuer_spieler);

                momentane_zeile = dok_leser.readLine();
            }

            dok_leser.close();

        } catch (java.io.IOException e) {
            System.out.println("Problem :(");
        }

        // Output von Reihenfolge gespeichert in .txt
        System.out.println(spielerliste);

        // sortieren
        absteigendSortieren(spielerliste);
        highscoreliste = spielerliste.subList(0,3);
        System.out.println("Highscore Liste: " + highscoreliste);

        // gefixte Reihenfolge ausgeben
        System.out.println(spielerliste);

        // spielen -> Daten zu Struktur aus .txt hinzufügen
        while (true) {
            String name = JOptionPane.showInputDialog("Spielername:");
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

            if ("Ende".equals(name)) {
                break;
            }

            Spieler sp1 = new Spieler(name);
            int punktzahl = Integer.parseInt(JOptionPane.showInputDialog("Punktzahl:"));
            sp1.setPunkte(punktzahl);

            System.out.println("neuer Eintrag: " + sp1);

            spielerliste.add(sp1);
            absteigendSortieren(spielerliste);

            // Highscore Liste muss jedes mal geupdatet werden, wenn die Top 3 sich verändert
            highscoreliste = spielerliste.subList(0,3);
            System.out.println("Highscore Liste: " + highscoreliste);
        }

        System.out.println(spielerliste);

        // Spiel soll beendet werden -> sync Spielerdaten in .txt
        try {
            BufferedWriter meinWriter = Files.newBufferedWriter(dok_spielerdaten, StandardOpenOption.WRITE);

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

    public static void absteigendSortieren (LinkedList<Spieler> spielerliste) {
        spielerliste.sort((spieler1, spieler2) -> spieler2.getPunkte() - spieler1.getPunkte());
    }
}
