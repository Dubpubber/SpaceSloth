package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.math.Vector2;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.game.GameObject;

import java.util.Random;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/20/2015.
 */
public class ScreenUtil {

    private static Random rand = new Random();

    public static Vector2 getRandomPositionAroundVector(Vector2 vector2, int minDistance) {
        Vector2 temp = new Vector2(0, 0);
        int quadrant = rand.nextInt(4);
        float ix = (float) Math.random() * (vector2.x * 10);
        float iy = (float) Math.random() * (vector2.y * 10);

        switch(quadrant) {
            case 0:
                // Quadrant 1, (+, -)
                ix += minDistance;
                iy = -iy - minDistance;
                break;
            case 1:
                // Quadrant 2, (+, +)
                ix += minDistance;
                iy += minDistance;
                break;
            case 2:
                // Quadrant 3, (-, +)
                ix = -ix - minDistance;
                iy += minDistance;
                break;
            case 3:
                // Quadrant 4, (-, -)
                ix = -ix - minDistance;
                iy = -iy - minDistance;
                break;
        }

        temp.set(ix, iy);

        return temp;
    }

    public static boolean isWithinDistance(Vector2 p1, Vector2 p2, float distance) {
        return p1.dst(p2) <= distance;
    }

    public static int calculateQuadrant(Vector2 position) {
        // Check quadrant 1, (+, -)
        if(position.x > 0 && position.y < 0) {
            // Quadrant 1, return 0.
            return 0;
        } else if(position.x > 0 && position.y > 0) { // Check quadrant 2, (+, +)
            // Quadrant 2, return 1.
            return 1;
        } else if(position.x < 0 && position.y > 0) { // Check quadrant 3, (-, +)
            // Quadrant 3, return 2.
            return 2;
        } else {
            // Quadrant 4, return 3.
            return 3;
        }
    }

    /**
     * Calculates the very most front of any game object based on it's angle.
     * @param object - The game object in which the calculate will be performed.
     * @return - The position of the very most front of the object.
     */
    public static Vector2 calculateFrontOfGameObject(GameObject object, float frontx, float fronty) {
        if(object.getBody() == null) return new Vector2(0,0);
        frontx = frontx / Globals.PixelsPerMetre;
        fronty = fronty / Globals.PixelsPerMetre;
        Vector2 front = object.getBody().getPosition();
        switch(calculateQuadrant(object.getBody().getPosition())) {
            case 0:
                // Quadrant 1, (+, -)
                front.set(front.x + frontx, front.y - fronty);
                break;
            case 1:
                // Quadrant 2, (+, +)
                front.set(front.x + frontx, front.y + fronty);
                break;
            case 2:
                // Quadrant 3, (-, +)
                front.set(front.x - frontx, front.y + fronty);
                break;
            case 3:
                // Quadrant 4, (-, -)
                front.set(front.x - frontx, front.y - fronty);
                break;
        }
        System.out.println("Front Vector: " + front.x + " " + front.y);
        return front;
    }

}
