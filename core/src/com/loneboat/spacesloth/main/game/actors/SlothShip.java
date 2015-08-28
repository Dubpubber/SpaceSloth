package com.loneboat.spacesloth.main.game.actors;

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
import com.loneboat.spacesloth.main.game.worldobjects.weapons.BlueBlast;
import com.loneboat.spacesloth.main.util.PlayerInputListener;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.game.actors
 * Created by Dubpub on 8/7/2015.
 */
public class SlothShip extends GameObject {

    private PlayerInputListener ip;
    private Profile profile;

    private float steeringTorque = 0;

    private float boostCap;
    private float curBoost = 0;

    /**
     * Holds the player's individual profile.
     *  (for easy saving and loading.)
     */
    public class Profile {
        private SlothShip sloth;

        public Profile(SlothShip sloth) {
            this.sloth = sloth;
        }

        public SlothShip getPlayer() {
            return sloth;
        }

        public boolean load() {
            return false;
        }

        public boolean save() {
            return false;
        }
    }

    private Box2DSpriteObject gunMount_sprite;

    /**
     * Creates a new game object that is animated but not a box2d object.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public SlothShip(SpaceSloth game, final ContentHandler chandle, Stage active_stage, World world) {
        super(game, chandle, active_stage, world, "SlothShip");

        profile = new Profile(this);

        BodyDef bdef = new BodyDef();
        FixtureDef shipBody = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create player
        bdef.position.set(35 / Globals.PixelsPerMetre, 35 / Globals.PixelsPerMetre);
        bdef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bdef);

        shape.setAsBox(48 / Globals.PixelsPerMetre, 42 / Globals.PixelsPerMetre);
        shipBody.shape = shape;
        shipBody.density = 0.1f;

        Fixture shipFixture = body.createFixture(shipBody);

        // Create the base ship model from the body def so far.
        Texture texture = chandle.getManager().get("Sprites/Ship_A1.png", Texture.class);
        Box2DSpriteObject sprite = new Box2DSpriteObject(texture, this);
        shipFixture.setUserData(sprite);

        // Dispose of the shape used for the sloth ship, then create a new one for the thrusters.
        // - Create the first thruster.
        shape = new PolygonShape();
        shape.setAsBox(10 / Globals.PixelsPerMetre, 42 / Globals.PixelsPerMetre, new Vector2(-60 / Globals.PixelsPerMetre, -25 / Globals.PixelsPerMetre), 0);

        FixtureDef thruster1 = new FixtureDef();
        thruster1.shape = shape;

        Fixture thrusterFixture = body.createFixture(thruster1);

        // Assign a new box2d sprite to the thruster polygon.
        Texture thruster_texture = chandle.getManager().get("Sprites/Thruster_A1.png", Texture.class);
        Box2DSprite thruster_sprite = new Box2DSpriteObject(thruster_texture, this);
        thrusterFixture.setUserData(thruster_sprite);

        // - Create the second thruster.
        shape.setAsBox(10 / Globals.PixelsPerMetre, 42 / Globals.PixelsPerMetre, new Vector2(60 / Globals.PixelsPerMetre, -25 / Globals.PixelsPerMetre), 0);

        FixtureDef thruster2 = new FixtureDef();
        thruster2.shape = shape;

        Fixture thruster2Fixture = body.createFixture(thruster2);

        // Assign a new box2d sprite to the thruster polygon.
        Box2DSprite thruster2_sprite = new Box2DSpriteObject(thruster_texture, this);
        thruster2Fixture.setUserData(thruster2_sprite);

        // Finally, create the gun mount for the sloth ship. This piece will rotate with the mouse.
        shape = new PolygonShape();
        shape.setAsBox(10 / Globals.PixelsPerMetre, 35 / Globals.PixelsPerMetre, new Vector2(0, 60 / Globals.PixelsPerMetre), 0);

        FixtureDef gunMount_fdef = new FixtureDef();
        gunMount_fdef.shape = shape;

        Fixture gunMount_Fixture = body.createFixture(gunMount_fdef);

        // Assign the gun mount
        Texture gunMount_texture = chandle.getManager().get("Sprites/GunMount_A1.png", Texture.class);
        gunMount_sprite = new Box2DSpriteObject(gunMount_texture, this);
        gunMount_Fixture.setUserData(gunMount_sprite);

        // Last, dispose the shape.
        //shape.dispose();

        // Set the objects data.
        setBody(body);
        setBox2DSprite(sprite);

        ip = new PlayerInputListener(game, chandle);
        setOrigin(getWidth() / 2, getHeight() / 2);

        setCurVelocity(new Vector2(0.0f, 0.0f));
        setMaxVelocity(new Vector2(0.075f, 0.075f));
        setHealth(100.0f);

        getBody().setAngularDamping(2.5f);

        getBody().setAngularDamping(2.5f);

        shape.dispose();

        setMaxProjectileCount(30);
        setMaxHealth(1000);
        replenishHealth();
        setBoostCap(100);
        replenishBoost();
    }

    /**
     * @param batch       - the sprite batch.
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Vector2 force = new Vector2(
                -(MathUtils.sin(getBody().getAngle())),
                (MathUtils.cos(getBody().getAngle()))
        );
        force.scl(getCurVelocity());

        if(ip.w) {
            if(ip.shift && hasBoost()) {
                incCurVelocity(new Vector2(0.15f, 0.15f));
                getBody().setLinearDamping(0.0f);
                getBody().applyLinearImpulse(force.x, force.y, getBodyX(), getBodyY(), true);
                depleatBoosters();
            } else {
                incCurVelocity(new Vector2(0.01f, 0.01f));
                getBody().setLinearDamping(0.0f);
                getBody().applyLinearImpulse(force.x, force.y, getBodyX(), getBodyY(), true);
            }
        } else {
            setCurVelocity(new Vector2(0.0f, 0.0f));
            getBody().setLinearDamping(0.75f);
            restoreBoosters();
        }

        if (ip.a) {
            steeringTorque = 0.25f;
        }

        if(ip.d) {
            steeringTorque = -0.25f;
        }

        if(!ip.a && !ip.d) {
            steeringTorque = 0;
        }

        if (ip.s) {
            getBody().setLinearDamping(2.5f);
            getBody().setAngularDamping(2.5f);
        }

        if(ip.space && canFire()) {
            BlueBlast bb = new BlueBlast(game, chandle, active_stage, world, this);
            bb.setCurrentScreen(currentScreen);
            setCurrentProjectile(bb);
            fire();
        }

        getBody().applyTorque(steeringTorque, true);

    }

    public Profile getProfile() {
        return profile;
    }

    public PlayerInputListener getPlayerInputListener() {
        return ip;
    }

    public Box2DSpriteObject getGunMountSprite() {
        return gunMount_sprite;
    }


    public float getBoostCap() {
        return boostCap;
    }

    public void setBoostCap(float boostCap) {
        this.boostCap = boostCap;
    }

    public float getCurBoost() {
        return curBoost;
    }

    public void setCurBoost(float curBoost) {
        this.curBoost = curBoost;
    }

    public void replenishBoost() {
        this.curBoost = 0;
    }

    public boolean hasBoost() {
        return curBoost > 0;
    }

    public void depleatBoosters() {
        if(hasBoost())
            curBoost -= 1.0f;
    }

    public void restoreBoosters() {
        if(curBoost < boostCap)
            curBoost += 1.0f;
    }

}
