package com.celihack.testgame.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.celihack.testgame.GameLoop;
import com.celihack.testgame.Joystick;
import com.celihack.testgame.R;


/**
* Classe del personaggio principale che l'utente può controllare attraverlo il joystick
* Questa classe è un'estensione di Circle, che a sua volta è un'estensione di GameObject
*/

public class Player extends Circle{

    public static final double SPEED_PIXEL_PER_SECOND = 400.00;
    public static final double MAX_SPEED = SPEED_PIXEL_PER_SECOND / GameLoop.MAX_UPS;

    private final Joystick joystick;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius){
        super(context,ContextCompat.getColor(context, R.color.player),positionX,positionY,radius);

        this.joystick = joystick;

    }

    public void update() {

        //Update della velocity in base all'actuator del joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        //Update della posizione
        positionX += velocityX;
        positionY += velocityY;
    }

}
