package com.celihack.testgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    private static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;

    private double averageUPS;
    private double averageFPS;

    private  Game game;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;




    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;

    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game Loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning){

            //Si prova ad aggiornare e renderizzare il gioco

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }

            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }




            // Si mette in pausa il loop per non eccedere UPS (updates/s)
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0){

                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Si saltano frame per stare al passo con gli UPS stabiliti

            while(sleepTime < 0 && updateCount < MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            // Calcolo degli UPS e FPS medi
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000){

                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = updateCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }

    }

}
