package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.game.GameObject;

import java.util.Random;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/20/2015.
 */
public class ScreenUtil {

    /* Grimace's pool status:
    *  1. Sucks
    *  2. Alright
    *  3. Sucks
    *
    *  Grimace sucks by majority.
    */

    private static Random rand = new Random();

    @Deprecated
    public static Vector2 getRandomPositionAroundVector(Vector2 vector2, int minDistance) {
        int side = rand.nextInt(7);
        float ix = (float) Math.random() * (vector2.x * 5);
        float iy = (float) Math.random() * (vector2.y * 5);

        switch(side) {
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
            case 4:
                // X Axis Positive
                ix += minDistance;
                break;
            case 5:
                // x Axis Negative
                ix = -ix - minDistance;
                break;
            case 6:
                // Y Axis Positive
                iy += minDistance;
                break;
            case 7:
                // Y Axis Negative
                iy = -iy - minDistance;
                break;
        }

        return new Vector2(ix, iy);
    }

    public static Vector2 randomVector2(int min, int max) {
        float theta = MathUtils.random(0f, MathUtils.PI2);
        float sqrt = (float) Math.sqrt(MathUtils.random(min, max));
        return new Vector2(sqrt * MathUtils.cos(theta), sqrt * MathUtils.sin(theta));
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

    public static float calculateNormalAngleOfObject(GameObject object) {
        float angle = MathUtils.radiansToDegrees * object.getBody().getAngle();
        while (angle <= 0){
            angle += 360;
        }
        while (angle > 360){
            angle -= 360;
        }
        return angle;
    }

    public static Vector2 getAngleOffset(GameObject object, float radius) {
        float angleCos = -MathUtils.cos(object.getBody().getAngle() - (MathUtils.PI / 2));
        float angleSin = MathUtils.sin(object.getBody().getAngle() + (MathUtils.PI / 2));
        float x = object.getBodyX() + (radius * angleCos);
        float y = object.getBodyY() + (radius * angleSin);
        return new Vector2(x, y);
    }

    public static Vector2 getPositionOffset(Vector2 v1, Vector2 v2, float scale) {
        return new Vector2(
                v1.x - v2.x,
                v1.y - v2.y
        ).scl(scale);
    }

    public static Vector2 scaleVector(Vector2 vector2) {
        return new Vector2(vector2.x / Globals.PixelsPerMetre, vector2.y / Globals.PixelsPerMetre);
    }

    public static Vector2 scaleVector(float x, float y) {
        return new Vector2(x / Globals.PixelsPerMetre, y / Globals.PixelsPerMetre);
    }

}
