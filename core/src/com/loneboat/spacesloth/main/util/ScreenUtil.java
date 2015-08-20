package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.math.Vector2;
import com.loneboat.spacesloth.main.Globals;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/20/2015.
 */
public class ScreenUtil {

    public static Vector2 getRandomPositionAroundVector(Vector2 vector2, int range) {
        Vector2 temp = new Vector2(0, 0);
        float ix = (float) (Math.random() * (vector2.x + range)) / Globals.PixelsPerMetre;
        float iy = (float) (Math.random() * (vector2.y + range)) / Globals.PixelsPerMetre;
        temp.set(ix, iy);
        return temp;
    }

}
