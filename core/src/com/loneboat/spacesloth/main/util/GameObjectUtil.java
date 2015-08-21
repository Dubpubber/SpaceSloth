package com.loneboat.spacesloth.main.util;

import com.loneboat.spacesloth.main.game.GameObject;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/21/2015.
 */
public class GameObjectUtil {

    public static float calculateDamageGiven(GameObject object) {
        float damage;
        float velocity = object.getBody().getAngularVelocity();
        float mass = object.getBody().getMass();
        if(mass > 0 && velocity > 1) {
            damage = (mass * velocity); // divided by object armor.
        } else
            damage = mass;
        if(damage < 0) {
            damage = -damage;
        }
        return damage;
    }

}
