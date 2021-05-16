package Klassen;

import javafx.scene.Group;

import java.util.ArrayList;

// todo: Stages? GameOver? Score speichern?

public class Game extends Thread{
    // Attribute
    private long zeahlerTakt = 0;
    private int monsterGeschwindigkeit = 20;
    private int schussGeschwindigkeit = 10;

    private int score;
    private boolean gameover = false;

    private ArrayList<Monster> listMonster = new ArrayList<Monster>();
    private ArrayList<Schuss> listSchuesse = new ArrayList<Schuss>();

    private long lastTickMillis = 0;
    private int timePerTick = 100; // in milliseconds

    // Von Jasmin
    private Group root;

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

            // mache einen neuen tick
            System.out.println("neuer tick");
            tick();
        }

        // gameover function GUI?
        // save score?
    }

    // gametick
    private void tick(){
        zeahlerTakt ++;

        // jeder Takt
        bewegeSchuesse();
        checkMonsterGetroffen();

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
            //bewege Monster
            // gameover -> true wenn Monster unten
        }
    }

    private void loeseNeuenSchuss() {
        System.out.println("schieße");
        //5 hier eingesetzt von Leon um mein program wieder lauffähig zu machen
        listSchuesse.add(new Schuss(5,5, root));
    }

    private void gameover(){
        System.out.println("Game over!");
        //punktzahl übergeben
    }

}
