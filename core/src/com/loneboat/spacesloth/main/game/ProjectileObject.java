package com.loneboat.spacesloth.main.game;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;

import java.util.ArrayList;

/**
 * com.loneboat.spacesloth.main.game.worldobjects
 * Created by Dubpub on 8/23/2015.
 */
public class ProjectileObject extends GameObject {
    // Shooter of projectile
    private GameObject shooter;

    // Damage of bullet on impact.
    private float damage = 0;

    // Is active boolean
    private boolean isActive = true;

    // Splits through objects.
    private boolean splitter = false;

    // Active list
    private ArrayList<String> affectedObjects;

    // Active speed
    private float speed;

    // Will be passed down.
    private float time;

    // Destroy time
    private float destroyTime;

    /**
     * Creates a standalone projectile GameObject.
     *  Awesome for creating projectiles on the fly. GET IT?
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public ProjectileObject(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, String ObjLabel, GameObject shooter, int time) {
        super(game, chandle, active_stage, world, "proj_" + ObjLabel);
        this.shooter = shooter;
        this.time = time;
        affectedObjects = new ArrayList<String>();
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public GameObject getShooter() {
        return shooter;
    }

    public void addCollisionApplicableObject(GameObject gameObject) {
        if(!affectedObjects.contains(gameObject.ObjLabel))
            affectedObjects.add(gameObject.ObjLabel);
    }

    public void addCollisionApplicableObject(String label) {
        if(!affectedObjects.contains(label))
            affectedObjects.add(label);
    }

    public boolean hasCollisionApplicableObject(GameObject gameObject) {
        return gameObject != null && affectedObjects.contains(gameObject.ObjLabel);
    }

    public boolean hasCollisionApplicableObject(String label) {
        return affectedObjects.contains(label);
    }

    public void update(float delta) {
        if(Math.round(level.timer) == destroyTime)
            setIsActive(false);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime() {
        game.getLogger().info("Level: " + level.getPlayer().inMap);
        destroyTime = Math.round(level.timer) + time;
    }

    public boolean isSplitter() {
        return splitter;
    }

    public void setSplitter(boolean splitter) {
        this.splitter = splitter;
    }

}