package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.loneboat.spacesloth.main.content.ContentHandler;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/17/2015.
 */
public class MouseUtil {

    public static float getMouseAngleRelativeToScreen() {
        float hw = ContentHandler.GAMEWIDTH / 2;
        float hh = ContentHandler.GAMEHEIGHT / 2;

        float angle = MathUtils.atan2(
                Gdx.input.getY() - hh,
                Gdx.input.getX() - hw
        );

        angle = (float)(180 / Math.PI) * angle;

        if(angle < 0) {
            angle = 360 - (-angle);
        }

        angle += 90;
        angle = 360 - angle;

        return angle;
    }

}
