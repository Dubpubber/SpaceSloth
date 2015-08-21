package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.math.Vector2;
import com.loneboat.spacesloth.main.Globals;

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
        float ix = (float) Math.random() * (vector2.x) / Globals.PixelsPerMetre;
        float iy = (float) Math.random() * (vector2.y) / Globals.PixelsPerMetre;

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

        System.out.println("Returning Vector: " + ix + "  " + iy);

        return temp;
    }

}
