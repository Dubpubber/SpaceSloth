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
        }

        // Projectile collision, A is a world object and B is a projectile.
        // elseif - A is the projectile, B is the world object.
        if(A.getUserData() instanceof Box2DSpriteObject && B.getUserData() instanceof ProjectileObject) {
            GameObject oA = ((Box2DSpriteObject) A.getUserData()).getGameObject();
            ProjectileObject oB = (ProjectileObject) B.getUserData();

            game.getLogger().info(oA.ObjLabel + " collided with " + oB.ObjLabel);

            // First, check for projectiles for Object A.
            if(oA instanceof ProjectileObject) {
                // Object A is a projectile, what do you want to do?
                if(((ProjectileObject) oA).hasCollisionApplicableObject(oB))
                    oB.destroy();
            } else if(oB instanceof ProjectileObject) {
                // Object B is a projectile then!
                if(((ProjectileObject) oB).hasCollisionApplicableObject(oA))
                    oA.destroy();
            }
        } else if(A.getUserData() instanceof ProjectileObject && B.getUserData() instanceof Box2DSpriteObject) {

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
