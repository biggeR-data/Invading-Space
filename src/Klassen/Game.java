package Klassen;

import GUI.Main;

import java.util.ArrayList;

// todo: Stages? GameOver? Score speichern?

public class Game extends Thread{
    // Attribute
    private long zeahlerTakt = 0;
    private int monsterGeschwindigkeit; // in millisekunden
    private int schussGeschwindigkeit; // in millisekunden

    private int score;
    private boolean gameover = false;

    private Main gui;
    private Spieler spieler;
    private ArrayList<Monster> listMonster = new ArrayList<Monster>();
    private ArrayList<Schuss> listSchuesse = new ArrayList<Schuss>();
    private Raumschiff schiff = new Raumschiff(0,0);

    private long lastSchussMillis = 0; // in millisekunden
    private long lastTickMillis = 0; // in millisekunden
    private int timePerTick = 100; // in millisekunden

    public Game(Main gui, Spieler spieler, int mode){
        this.spieler = spieler;
        this.gui = gui;
        switch(mode){
            case 0:
                // normal
                schussGeschwindigkeit = 10;
                monsterGeschwindigkeit = 5;
                break;
            case 1:
                schussGeschwindigkeit = 10;
                monsterGeschwindigkeit = 20;
                break;
        }
    }

    // run
    public void run(){
        // spiele bis gameover
        while(!gameover){

            // warte bis Zeit vergangen
            while((lastTickMillis + timePerTick) <= System.currentTimeMillis()){
                try {
                    this.sleep(0,100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // führe tick aus
            System.out.println("neuer tick");
            tick();
        }

        gameover();
    }

    // gametick
    private void tick(){
        zeahlerTakt ++;

        // jeder Takt
        bewegeSchuesse();
        checkMonsterGetroffen();

        // anzeigen aktualisieren

        // loese neuen Schuss

        if(zeahlerTakt % schussGeschwindigkeit == 0){
            loeseNeuenSchuss();
        }

        // nicht jeden Takt ausführen (bei Monster beschleunigung MonsterGeschwindigkeit ändern)
        if(zeahlerTakt % monsterGeschwindigkeit == 0){
            bewegeMonster();
        }

    }

    // private Operationen
    private void checkMonsterGetroffen(){
        System.out.println("checke Monster getroffen");
        // wenn Monster getroffen - entfernen
        // Score anpassen
    }

    private void bewegeSchuesse(){
        System.out.println("bewege Schüsse");

        for(Schuss schuss:listSchuesse) {
            // bewege
        }
        // entferne wenn ausserhalb
    }

    private void bewegeMonster(){
        System.out.println("bewege Monster");

        for(Monster monster:listMonster){
            // monster.bewegenLinks();
            // gameover -> true wenn Monster unten
        }
    }

    private void loeseNeuenSchuss() {
        System.out.println("schieße");
        if(System.currentTimeMillis() - lastSchussMillis >= schussGeschwindigkeit) {
            listSchuesse.add(schiff.schiessen()); // ich brauch den Schuss!
        }
    }

    private void gameover(){
        System.out.println("Game over!");
        // punktzahl übergeben -> Spieler
        // GUI
    }

    // key events
    // todo: keyevent
    public void keyUp(){
        loeseNeuenSchuss();
    }

    public void keyLeft(){
        schiff.bewegenLinks();
    }

    public void keyRight(){
        schiff.bewegenRechts();
    }

}
