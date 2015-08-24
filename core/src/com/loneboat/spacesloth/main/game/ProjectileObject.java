package com.loneboat.spacesloth.main.game;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * com.loneboat.spacesloth.main.game.worldobjects
 * Created by Dubpub on 8/23/2015.
 */
public class ProjectileObject extends GameObject {
    // Shooter of projectile
    private GameObject shooter;

    private float lifespan = 10;

    // Damage of bullet on impact.
    private float damage = 0;

    // Is active boolean
    private boolean isActive = true;

    /**
     * Creates a standalone projectile GameObject.
     *  Awesome for creating projectiles on the fly. GET IT?
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public ProjectileObject(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, String ObjLabel, float lifespan, GameObject shooter) {
        super(game, chandle, active_stage, world, "proj_" + ObjLabel);
        TimerTask lifetime = new TimerTask() {
            @Override
            public void run() {
                setIsActive(false);
            }
        };
        new Timer().schedule(lifetime, 5000);
        this.shooter = shooter;
        this.lifespan = lifespan;
        this.setZIndex(1);
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

    public void update(float delta) {
        //body.getPosition().set(shooter.getBodyX() + 1, shooter.getBodyY() + 1);
    }

    public void dispose() {
        world.destroyBody(body);
        remove();
    }

}