package com.loneboat.spacesloth.main.game.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.game.Box2DSpriteObject;

/**
 * com.loneboat.spacesloth.main.game.handlers
 * Created by Dubpub on 8/21/2015.
 */
public class AsteroidsLevelListener implements ContactListener {

    private SpaceSloth game;

    public AsteroidsLevelListener(SpaceSloth game) {
        this.game = game;
        //testsetestsetsetest
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

        if(A.getUserData() instanceof Box2DSpriteObject && B.getUserData() instanceof  Box2DSpriteObject) {
            Box2DSpriteObject sA = (Box2DSpriteObject) A.getUserData();
            Box2DSpriteObject sB = (Box2DSpriteObject) B.getUserData();
            game.getLogger().info(sA.getGameObject().ObjLabel + " collided with " + sB.getGameObject().ObjLabel);
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
