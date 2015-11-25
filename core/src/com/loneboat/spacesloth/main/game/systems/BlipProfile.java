package com.loneboat.spacesloth.main.game.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.loneboat.spacesloth.main.game.GameObject;

/**
 * com.loneboat.spacesloth.main.game.systems
 * Created by Dubpub on 11/24/2015.
 */
public class BlipProfile {

    private GameObject object;
    private float x;
    private float y;
    private Color color;
    private int level;

    public BlipProfile(GameObject object, Color color, int level) {
        this.object = object;
        this.color = color;
        this.level = level;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void updateLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void updateLocation(Vector2 location) {
        this.x = location.x;
        this.y = location.y;
    }

    public Vector2 getLocation() {
        return new Vector2(x, y);
    }

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public GameObject getGameObject() {
        return object;
    }

}
