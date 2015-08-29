package com.loneboat.spacesloth.main.game.worldobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.Box2DSpriteObject;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.util.ScreenUtil;

/**
 * com.loneboat.spacesloth.main.game.worldobjects
 * Created by Dubpub on 8/20/2015.
 */
public class Asteroid extends GameObject {
    private int ID;
    /**
     * Creates a new game object that is part of box2d.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     * @param world
     */
    public Asteroid(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, int diameter, int maxDistance) {
        super(game, chandle, active_stage, world, "Asteroid");

        int size = MathUtils.random(12, diameter);

        BodyDef bdef = new BodyDef();
        FixtureDef asteroidBody = new FixtureDef();
        CircleShape shape = new CircleShape();
        Vector2 randPos = ScreenUtil.randomVector2(10, maxDistance);

        bdef.position.set(randPos.x, randPos.y);
        bdef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bdef);

        shape.setRadius(size / Globals.PixelsPerMetre);
        asteroidBody.shape = shape;
        asteroidBody.density = 0.2f * size;

        Fixture shipFixture = body.createFixture(asteroidBody);

        // Create the base ship model from the body def so far.
        Texture texture = chandle.getManager().get("Sprites/Asteroid_2_smallball.png", Texture.class);
        Box2DSpriteObject sprite = new Box2DSpriteObject(texture, this);
        shipFixture.setUserData(sprite);

        setBody(body);
        setBox2DSprite(sprite);
        ID = MathUtils.random(1000);
        setMaxHealth(body.getMass());
        replenishHealth();
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
