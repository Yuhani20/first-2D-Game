package main;

import GameObjects.BasicEnemy;
import GameObjects.FasterEnemy;
import GameObjects.ID;
import GameObjects.SmartEnemy;

import java.util.Random;

public class Spawner {
    private Handler handler;
    private HUD hud;

    private int scoreKeep=0;

    private Random r=new Random();

    public Spawner(Handler handler, HUD hud){
        this.handler=handler;
        this.hud=hud;
    }

    public void tick(){
        scoreKeep+=3;
        if (scoreKeep>=1000){

            scoreKeep=0;
            hud.setLevel(hud.getLevel()+1);

            if (hud.getLevel()<=2) {
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH2), r.nextInt(Game.HEIGHT2), ID.BasicEnemy, handler));
            }

            if (hud.getLevel()==4){
                handler.addObject(new FasterEnemy(r.nextInt(Game.WIDTH2),r.nextInt(Game.HEIGHT2),ID.FasterEnemy,handler));
            }

            if (hud.getLevel()==5){
                handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH2),r.nextInt(Game.HEIGHT2),ID.FasterEnemy,handler));
            }

        }

    }

}
