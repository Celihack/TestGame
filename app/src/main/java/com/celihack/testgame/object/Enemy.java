package com.celihack.testgame.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.celihack.testgame.GameLoop;
import com.celihack.testgame.R;

/**
 * Enemy è un personaggio che si muove sempre nella direzione del player
 * La classe Enemy è un'estensione di Circle, che è a sua volta un'estensione di GameObject
 */

public class Enemy extends Circle{

    private static final double SPEED_PIXEL_PER_SECOND = Player.SPEED_PIXEL_PER_SECOND*0.6;
    private static final double MAX_SPEED = SPEED_PIXEL_PER_SECOND / GameLoop.MAX_UPS;

    private static final double SPAWNS_PER_MINUTE = 20.0;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;

    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);

        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(context,
                ContextCompat.getColor(context, R.color.enemy),
                Math.random()*1000,
                Math.random()*1000,
                30
        );

        this.player = player;
    }

    /**
     * readyToSpawn controlla se un nemico deve spoawnare, in accordo con il numero di spawn al minuto (SPAWNS_PER_MINUTE)
     * @return
     */
    public static boolean readyToSpawn() {

        if(updatesUntilNextSpawn <= 0){
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }

    }

    @Override
    public void update() {

        // Si fa l'update della velocity del nemico in modo tale che vada in direzione del player
        //====================================================================


        //Calcolo del vettore dall'enemy al player (x y)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //Calcolo della distanza assoluta tra l'enemy e il player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this,player);

        //Calcolo della direzione dall'enemy al player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        //Set della velocity in direzione del player
        if(distanceToPlayer > 0){
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;

        }else{
            velocityX = 0;
            velocityY = 0;
        }

        //Update della posizione dell'enemy
        positionX += velocityX;
        positionY += velocityY;

    }
}
