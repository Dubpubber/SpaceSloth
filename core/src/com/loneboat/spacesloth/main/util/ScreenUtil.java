package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/20/2015.
 */
public class ScreenUtil {

    private static Random rand = new Random();

    /**
     * Calculates a random quadrant based vector from a given vector. Also applies a minimum distance from the supplied vector.
     * @param position - position of 'origin' object.
     * @param minDistance - minimum distance from 'position'
     * @return - Random Vector2 based on given position.
     */
    public static Vector2 getRandomPositionAroundVector(Vector2 position, int minDistance) {
        Vector2 temp = new Vector2(0, 0);
        int quadrant = rand.nextInt(4);
        float ix = (float) Math.random() * (position.x) * 10;
        float iy = (float) Math.random() * (position.y) * 10;

        switch(quadrant) {
            case 0:
                // Quadrant 1, (-, +)
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

    /**
     * Simply calculates the distance from pointA to pointB and returns a boolean if it's within the given distance.
     * @param pointA - The distance being compared.
     * @param pointB - The position of the object of which is being compared.
     * @param distance - Distance of which the dst() must be in order to return true.
     * @return - true or false based on if the compared vector is less than or equal to the distance.
     */
    public static boolean isWithinDistance(Vector2 pointA, Vector2 pointB, float distance) {
        return pointA.dst(pointB) <= distance;
    }

}
