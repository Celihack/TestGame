package com.celihack.testgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.celihack.testgame.object.Enemy;
import com.celihack.testgame.object.Player;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Enemy enemy;
    private Joystick joystick;
    private final Player player;
    private final GameLoop gameLoop;

    public Game(Context context) {
        super(context);

        //inizializzazione game loop
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder);

        // Inizializzazione joystick
        joystick = new Joystick(275,700,70,40);

        // Inizializziazione player
        player = new Player(getContext(),joystick, 500,500,30);

        //inizializzazione enemy
        enemy = new Enemy(context, player, 880,500,30);


        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;

        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawFPS(canvas);
        drawUPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        enemy.draw(canvas);
    }

    public void drawUPS(Canvas canvas){

        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(20);
        canvas.drawText("UPS" + averageUPS, 100, 100 , paint);

    }

    public void drawFPS(Canvas canvas){

        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(20);
        canvas.drawText("FPS" + averageFPS, 100, 200 , paint);

    }

    public void update() {
        joystick.update();
        player.update();
        enemy.update();

    }
}
