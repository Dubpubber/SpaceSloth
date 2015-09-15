package com.loneboat.spacesloth.main.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.game.ProjectileObject;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.game.worldobjects.ores.Ore;

/**
 * com.loneboat.spacesloth.main.game.handlers
 * Created by Dubpub on 8/21/2015.
 */
public class AsteroidsLevelListener implements ContactListener {

    private SpaceSloth game;

    public AsteroidsLevelListener(SpaceSloth game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        GameObject A = (GameObject) contact.getFixtureA().getBody().getUserData();
        GameObject B = (GameObject) contact.getFixtureB().getBody().getUserData();

        // Check collision with projectiles!
        if (A instanceof ProjectileObject || B instanceof ProjectileObject) {
            if (A instanceof ProjectileObject) {
                ProjectileObject pO1 = (ProjectileObject) A;
                if (pO1.hasCollisionApplicableObject(A)) {
                    B.subtrackHealth(pO1.getDamage());
                    if (B.isDead()) {
                        B.queueDestroy();
                    }
                    if (!((ProjectileObject) A).isSplitter()) {
                        B.queueDestroy();
                    }
                }
            } else {
                ProjectileObject pO1 = (ProjectileObject) B;
                if (pO1.hasCollisionApplicableObject(A)) {
                    A.subtrackHealth(pO1.getDamage());
                    if (A.isDead()) {
                        A.queueDestroy();
                    }
                    if (!((ProjectileObject) B).isSplitter()) {
                        B.queueDestroy();
                    }
                }
            }
        }

        // Check player collisions, check if either object is a player.
        if(A instanceof SlothShip || B instanceof SlothShip) {
            SlothShip player = null;
            boolean ab = false;
            // If so, find which one is a player.
            if(A instanceof SlothShip) {
                player = (SlothShip) A;
                ab = true;
            } else {
                player = (SlothShip) B;
                ab = false;
            }

            // Now, check for player applicable collision.
            // Ores first! //
            if(ab && B instanceof Ore) {
                B.queueDestroy();
            } else if(!ab && A instanceof Ore) {
                A.queueDestroy();
            }

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
