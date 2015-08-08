package com.loneboat.spacesloth.main.util;

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

    float getMaxVelocity();
    void setMaxVeolicity(float velocity);

    // Adjusters
    void subtrackHealth(float health);
    void addHealth(float health);

    void subtractLives(float lives);
    void addLives(float lives);
}
