package com.loneboat.spacesloth.main.game.worldobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.util.ScreenUtil;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.game.worldobjects
 * Created by Dubpub on 8/20/2015.
 */
public class Asteroid extends GameObject {


    /**
     * Creates a new game object that is part of box2d.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     * @param world
     */
    public Asteroid(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world) {
        super(game, chandle, active_stage, world, "Asteroid");

        BodyDef bdef = new BodyDef();
        FixtureDef asteroidBody = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2 randPos = ScreenUtil.getRandomPositionAroundVector(new Vector2(0,0), 1000);

        game.getLogger().info("RandPos: " + randPos.x + " " + randPos.y);

        bdef.position.set(randPos.x, randPos.y);
        bdef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bdef);

        shape.setAsBox(64 / Globals.PixelsPerMetre, 64 / Globals.PixelsPerMetre);
        asteroidBody.shape = shape;
        asteroidBody.density = 2.5f;

        Fixture shipFixture = body.createFixture(asteroidBody);

        // Create the base ship model from the body def so far.
        Texture texture = chandle.getManager().get("Sprites/Asteroid_2_smallball.png", Texture.class);
        Box2DSprite sprite = new Box2DSprite(texture);
        shipFixture.setUserData(sprite);

    }

    /**
     * @param batch       - the sprite batch.
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
