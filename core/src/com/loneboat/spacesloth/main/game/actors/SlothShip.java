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
import com.loneboat.spacesloth.main.util.ScreenUtil;

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
    public boolean isBoosting = false;

    private Vector2 MaxBoostVelocity;

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

        // Create the body of the player.
        BodyDef bdef = new BodyDef();
        bdef.position.set(35 / Globals.PixelsPerMetre, 35 / Globals.PixelsPerMetre);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();

        createCockpit(body, shape);
        createGunMount(body, shape);
        createHull(body, shape);
        createThrusters(body, shape);
        createWings('F', body, shape);

        shape.dispose();

        setBody(body);

        ip = new PlayerInputListener(game, chandle);
        setOrigin(getWidth() / 2, getHeight() / 2);

        setCurVelocity(new Vector2(0.0f, 0.0f));
        setMaxVelocity(new Vector2(0.075f, 0.075f));
        setMaxBoostVelocity(new Vector2(0.090f, 0.090f));
        setHealth(100.0f);

        getBody().setAngularDamping(2.5f);

        getBody().setAngularDamping(2.5f);

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
        ).scl(new Vector2(getCurVelocity().x * getMass(), getCurVelocity().y * getMass()));

        if(ip.w) {
            if(!isBoosting) {
                incCurVelocity(new Vector2(0.01f, 0.01f));
            } else {
                setCurVelocity(getMaxBoostVelocity());
                if(!hasBoost())
                    isBoosting = false;
            }
            getBody().setLinearDamping(0.0f);
            getBody().applyLinearImpulse(force.x, force.y, getBodyX(), getBodyY(), true);
        } else {
            setCurVelocity(new Vector2(0.0f, 0.0f));
            getBody().setLinearDamping(0.75f);
        }

        if(ip.shift && useBoost()) {
            isBoosting = true;
        }

        if (ip.a) {
            steeringTorque = 1.75f * getMass();
        }

        if(ip.d) {
            steeringTorque = -1.75f * getMass();
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
            bb.setLevel(level);
            setCurrentProjectile(bb);
            fire();
        }

        if(!isBoosting)
            restoreBoosters();
        else
            depleatBoosters();

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
        this.curBoost = boostCap;
    }

    public boolean hasBoost() {
        return curBoost > 0;
    }

    public boolean useBoost() {
        return curBoost >= boostCap;
    }

    public void depleatBoosters() {
        if(hasBoost())
            curBoost -= 1.0f;
    }

    public void restoreBoosters() {
        if(curBoost < boostCap)
            curBoost += 0.15f;
    }

    public Vector2 getMaxBoostVelocity() {
        return MaxBoostVelocity;
    }

    public void setMaxBoostVelocity(Vector2 maxBoostVelocity) {
        MaxBoostVelocity = maxBoostVelocity;
    }

    public void createCockpit(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
               41 / Globals.PixelsPerMetre, 40 / Globals.PixelsPerMetre, new Vector2(0,0), 0
        );
        FixtureDef cockpit = new FixtureDef();
        cockpit.shape = shape;
        cockpit.density = 5.0f;
        Fixture cockpitFixture = body.createFixture(cockpit);
        Texture texture = chandle.getManager().get("Bodies/Ship_1/Ship_1_Cockpit.png");
        Box2DSpriteObject spriteObject = new Box2DSpriteObject(texture, this);
        cockpitFixture.setUserData(spriteObject);
    }

    public void createGunMount(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
                12 / Globals.PixelsPerMetre, 20 / Globals.PixelsPerMetre,
                ScreenUtil.scaleVector(new Vector2(0, 65)), 0
        );
        FixtureDef gunMount = new FixtureDef();
        gunMount.shape = shape;
        gunMount.density = 1.5f;
        Fixture gunMountFixture = body.createFixture(gunMount);
        Texture texture = chandle.getManager().get("Bodies/Ship_1/Ship_1_GunMount.png");
        Box2DSpriteObject spriteObject = new Box2DSpriteObject(texture, this);
        gunMountFixture.setUserData(spriteObject);
    }

    public void createHull(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
                40 / Globals.PixelsPerMetre, 81 / Globals.PixelsPerMetre,
                ScreenUtil.scaleVector(new Vector2(0, -127)), 0
        );
        FixtureDef hull = new FixtureDef();
        hull.shape = shape;
        hull.density = 10.0f;
        Fixture hullFixture = body.createFixture(hull);
        Texture texture = chandle.getManager().get("Bodies/Ship_1/Ship_1_Hull.png");
        Box2DSpriteObject spriteObject = new Box2DSpriteObject(texture, this);
        hullFixture.setUserData(spriteObject);
    }

    public void createThrusters(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
                42 / Globals.PixelsPerMetre, 16 / Globals.PixelsPerMetre,
                ScreenUtil.scaleVector(new Vector2(0, -225)), 0
        );
        FixtureDef thruster = new FixtureDef();
        thruster.shape = shape;
        thruster.density = 2.0f;
        Fixture thrusterFixture = body.createFixture(thruster);
        Texture texture = chandle.getManager().get("Bodies/Ship_1/Ship_1_Thrusters.png");
        Box2DSpriteObject spriteObject = new Box2DSpriteObject(texture, this);
        thrusterFixture.setUserData(spriteObject);
    }

    public void createWings(char rank, Body body, PolygonShape shape) {
        // Create the first wing.
        shape = new PolygonShape();
        shape.setAsBox(
                37 / Globals.PixelsPerMetre, 38 / Globals.PixelsPerMetre,
                ScreenUtil.scaleVector(new Vector2(60, -150)), 0
        );
        FixtureDef winga = new FixtureDef();
        winga.shape = shape;
        winga.density = 1.5f;
        Fixture WingAFixture = body.createFixture(winga);
        Texture texture1 = chandle.getManager().get("Bodies/Ship_1/Ship_1_1" + rank + "_Wing.png");
        Box2DSpriteObject spriteObject1 = new Box2DSpriteObject(texture1, this);
        WingAFixture.setUserData(spriteObject1);

        // Create the second wing.
        shape = new PolygonShape();
        shape.setAsBox(
                37 / Globals.PixelsPerMetre, 38 / Globals.PixelsPerMetre,
                ScreenUtil.scaleVector(new Vector2(-60, -150)), 0
        );
        FixtureDef wingb = new FixtureDef();
        wingb.shape = shape;
        wingb.density = 1.5f;
        Fixture WingBFixture = body.createFixture(wingb);
        Texture texture2 = chandle.getManager().get("Bodies/Ship_1/Ship_1_2" + rank + "_Wing.png");
        Box2DSpriteObject spriteObject2 = new Box2DSpriteObject(texture2, this);
        WingBFixture.setUserData(spriteObject2);
    }

}
