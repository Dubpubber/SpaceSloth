package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.math.Vector2;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/7/2015.
 *
 * Trackers are subclasses for keeping track of basic information.
 */
public interface GameObjectTracker {

    float getLives();
    void setLives(float lives);

    float getHealth();
    void setHealth(float health);

    Vector2 getCurVelocity();
    void setCurVelocity(Vector2 CurVelocity);

    void incCurVelocity(Vector2 incVel);
    void decCurVelocity(Vector2 decVel);

    boolean compareVelocity();

    Vector2 getMaxVelocity();
    void setMaxVelocity(Vector2 MaxVelocity);

    // Adjusters
    void subtrackHealth(float health);
    void addHealth(float health);

    void subtractLives(float lives);
    void addLives(float lives);
}
