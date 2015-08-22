package com.loneboat.spacesloth.main.game.worldobjects.weapons;

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
import com.loneboat.spacesloth.main.game.actors.SlothShip;

/**
 * com.loneboat.spacesloth.main.game.worldobjects.weapons
 * Created by Dubpub on 8/21/2015.
 */
public class BlueBlast extends GameObject {
    private SlothShip player;
    private boolean isFiring = false;
    /**
     * Creates a new game object that is animated but not a box2d object.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public BlueBlast(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, SlothShip player) {
        super(game, chandle, active_stage, world, "BlueBlast");
        this.player = player;

        BodyDef bdef = new BodyDef();
        FixtureDef bulletbody = new FixtureDef();
        CircleShape shape = new CircleShape();

        // Get the gun mount fixture from the player.
        GameObject playerGunMount = player.getGunMountSprite().getGameObject();
        Vector2 playerAngle = new Vector2(-(MathUtils.sin(player.getBody().getAngle()) * 1f), (MathUtils.cos(player.getBody().getAngle()) * 1f));

        // Set the blast to the tip of the gunmount sprite.
        bdef.position.set(
                player.getBodyX(),
                player.getBodyY() * playerAngle.y
        );

        bdef.type = BodyDef.BodyType.KinematicBody;

        Body body = world.createBody(bdef);

        shape.setRadius(15 / Globals.PixelsPerMetre);
        bulletbody.shape = shape;
        bulletbody.density = 1.0f;

        Fixture bulletFixture = body.createFixture(bulletbody);

        Texture bulletTexture = chandle.getManager().get("Sprites/BlueBlast_A1.png", Texture.class);
        Box2DSpriteObject sprite = new Box2DSpriteObject(bulletTexture, this);
        bulletFixture.setUserData(sprite);

        setBody(body);
        setBox2DSprite(sprite);

        body.setLinearVelocity(playerAngle.x * 10, playerAngle.y * 10);

    }

    public void fire() {
        isFiring = true;
    }

    public void dispose() {
        world.destroyBody(body);
        remove();
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
