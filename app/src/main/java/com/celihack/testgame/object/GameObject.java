package com.celihack.testgame.object;

import android.graphics.Canvas;

/**
 * GameObject è una classe astratta che è in pratica la base da cui poi partono tutti gli oggetti del gioco
 */
public abstract class GameObject {

    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY){

        this.positionX = positionX;
        this.positionY = positionY;

    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

    protected double getPositionX() {
        return positionX;
    }
    protected double getPositionY() {
        return positionY;
    }

    protected static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(),2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(),2)
        );
    }
}
