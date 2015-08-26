package com.loneboat.spacesloth.main.game.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.game.Box2DSpriteObject;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.game.ProjectileObject;

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
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

        // World object collision.
        if(A.getUserData() instanceof Box2DSpriteObject && B.getUserData() instanceof  Box2DSpriteObject) {
            Box2DSpriteObject sA = (Box2DSpriteObject) A.getUserData();
            Box2DSpriteObject sB = (Box2DSpriteObject) B.getUserData();
            GameObject oA = sA.getGameObject();
            GameObject oB = sB.getGameObject();

            // Projectile check.
            if(oA instanceof ProjectileObject) {
                ProjectileObject pO1 = (ProjectileObject) oA;
                // Check if collision is applicable with object.
                if(pO1.hasCollisionApplicableObject(oB))
                    oB.queueDestroy();
            }

            if(oB instanceof ProjectileObject) {
                ProjectileObject pO1 = (ProjectileObject) oB;
                // Check if collision is applicable with object.
                if(pO1.hasCollisionApplicableObject(oA)) {
                    oA.queueDestroy();
                    if(!((ProjectileObject) oB).isSplitter())
                        oB.queueDestroy();
                }
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
