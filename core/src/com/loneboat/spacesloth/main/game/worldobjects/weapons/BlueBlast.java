package com.loneboat.spacesloth.main.game.worldobjects.weapons;

import com.badlogic.gdx.graphics.Texture;
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

        // Set the blast to the tip of the gunmount sprite.
        //velocity.set(targetX - position.x, targetY - position.y).nor().scl(Math.min(position.dst(targetX, targetY), speedMax));
        bdef.position.set(player.getBody().getPosition());
        bdef.type = BodyDef.BodyType.KinematicBody;

        Body body = world.createBody(bdef);
        //getCurVelocity().set(player.getBodyX() - getBodyX(), player.getBodyY() - getBodyY()).nor().scl(Math.min(body.getPosition().dst(getBodyX(), getBodyY()), 10.0f));

        shape.setRadius(15 / Globals.PixelsPerMetre);
        bulletbody.shape = shape;
        bulletbody.density = 1.0f;

        Fixture bulletFixture = body.createFixture(bulletbody);

        Texture bulletTexture = chandle.getManager().get("Sprites/BlueBlast_A1.png", Texture.class);
        Box2DSpriteObject sprite = new Box2DSpriteObject(bulletTexture, this);
        bulletFixture.setUserData(sprite);

        setBody(body);
        setBox2DSprite(sprite);

    }

    public void fire() {
        isFiring = true;
    }

    public void dispose() {
        world.destroyBody(body);
        remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
