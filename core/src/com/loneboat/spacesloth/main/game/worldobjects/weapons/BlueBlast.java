package com.loneboat.spacesloth.main.game.worldobjects.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.ProjectileObject;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.util.ScreenUtil;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.game.worldobjects.weapons
 * Created by Dubpub on 8/21/2015.
 */
public class BlueBlast extends ProjectileObject {
    private SlothShip player;
    /**
     * Creates a new game object that is animated but not a box2d object.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public BlueBlast(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, SlothShip player) {
        super(game, chandle, active_stage, world, "BlueBlast", player, 5);
        this.player = player;

        BodyDef bdef = new BodyDef();
        FixtureDef bulletbody = new FixtureDef();
        CircleShape shape = new CircleShape();

        Vector2 impulse = ScreenUtil.getAngleOffset(player, 1.2f);

        // Set the blast to the tip of the gunmount sprite.
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(impulse);

        Body body = world.createBody(bdef);
        body.setUserData(this);

        shape.setRadius(12 / Globals.PixelsPerMetre);
        bulletbody.shape = shape;
        bulletbody.density = 1.0f;
        bulletbody.filter.groupIndex = -1;

        Fixture bulletFixture = body.createFixture(bulletbody);

        Texture bulletTexture = chandle.getManager().get("Sprites/BlueBlast_A1.png", Texture.class);
        Box2DSprite sprite = createNewBox2dObject("blueblast", bulletTexture);
        bulletFixture.setUserData(sprite);

        body.setBullet(true);

        setBody(body);
        setSpeed(50f);

        Vector2 forceOffset = ScreenUtil.getFiringAngle(player.getPosition(), this.getPosition(), 1.0f);

        // Define what can collide with this projectile.
        addCollisionApplicableObject("Asteroid");
        addCollisionApplicableObject("AsteroidBomb");

        body.setLinearVelocity(forceOffset.scl(getSpeed()));

        setSplitter(false);
        setDamage(10);
        shape.dispose();
    }

}
