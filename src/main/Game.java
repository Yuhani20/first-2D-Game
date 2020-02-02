package main;

import GameObjects.BasicEnemy;
import GameObjects.ID;
import GameObjects.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH=640,HEIGHT=WIDTH/12*9;
    public static final int WIDTH2=WIDTH-48,HEIGHT2=HEIGHT-70;

    private Thread thread;
    private boolean running=false;
    private Handler handler;
    private HUD hud;
    private Spawner spawner;
    private Effect effect;

    Random r;

    public Game(){
        handler=new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH,HEIGHT,"a Boring Game",this);
        hud=new HUD();
        spawner=new Spawner(handler,hud);
        effect=new Effect();

        r=new Random();

        handler.addObject(new Player(r.nextInt(WIDTH2),r.nextInt(HEIGHT2), ID.Player,handler));
        handler.addObject(new BasicEnemy(r.nextInt(WIDTH2),r.nextInt(HEIGHT2),ID.BasicEnemy,handler));

    }

    public synchronized void start() {
        thread =new Thread(this);
        thread.start();
        running =true;
    }

    public synchronized void stop() {
        try{
            thread.join();
            running=false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
        hud.tick();
        spawner.tick();
    }

    private void render(){
        BufferStrategy bs= this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g=bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);
        hud.render(g);
//        effect.render(g);

        g.dispose();
        bs.show();
    }

    public static int clamp(int val,int min,int max){
        if (val<=min) return val=min;
        else if (val>=max) return val=max;
        else return val;
    }

    public static void main(String[] args) {
        new Game();
    }

}

//test commit second time