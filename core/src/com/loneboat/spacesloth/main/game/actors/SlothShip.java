package com.loneboat.spacesloth.main.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.content.partsystem.Part;
import com.loneboat.spacesloth.main.content.partsystem.PartFactory;
import com.loneboat.spacesloth.main.content.partsystem.PartType;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.game.systems.PlayerHUD;
import com.loneboat.spacesloth.main.game.systems.console.PlayerConsole;
import com.loneboat.spacesloth.main.game.worldobjects.ores.Ore;
import com.loneboat.spacesloth.main.game.worldobjects.weapons.BlueBlast;
import com.loneboat.spacesloth.main.screens.GameLevel;
import com.loneboat.spacesloth.main.util.PlayerInputListener;
import com.loneboat.spacesloth.main.util.ScreenUtil;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.math.BigDecimal;

/**
 * com.loneboat.spacesloth.main.game.actors
 * Created by Dubpub on 8/7/2015.
 */
public class SlothShip extends GameObject {

    // Used to call on for possible player input.
    private PlayerInputListener ip;

    // Holds the save/load profile for the player. All serializable information will be stored here.
    private Profile profile;

    // The player hud and systems. Radar, console and health/boost.
    private PlayerHUD hud;

    // The current amount of steer torque being applied to the player's spacecraft.
    private float steeringTorque = 0;

    /**
     * Interchangeable values.
     *  - These values are not final for a reason!!!
     */
    // Maximum boost for the spacecraft. Should be more than max velocity!
    private float boostCap;
    // Holds the current boost of the player's spacecraft.
    private float curBoost = 0;
    // Boolean to note if the player is boosting. (Cause it's illegal, ya know.)
    public boolean isBoosting = false;
    // Is the player currently within my very lazy and needs some desperate tuning in map boolean?
    public boolean inMap;

    // Holds the current level of which the player is currently playing.
    private GameLevel level;

    // Max boost velocity. I actually don't know the difference. Should probably though.
    private Vector2 MaxBoostVelocity;

    // Like in most Java naming parterns, the part factory is in charge of well, processing and gathering all the possible parts for the player's ship!
    private PartFactory pf;

    /**
     * Holds the player's individual profile.
     *  (for easy saving and loading.)
     */
    public class Profile {
        // This
        private SlothShip sloth;
        // Array of the current parts equiped to the player's ship.
        private Part[] currentParts;
        // Amount of money the player currently has.
        private BigDecimal money;

        // Count of parts. currentParts.length doesn't check for null-ness, so that's why this exists!
        private int equipedPartCount = 0;

        // The shield is it's own special thing, it gets a place in the GC as well.
        private Box2DSprite shield;

        /**
         * Constructor for creating a new game profile.
         * As mentioned this profile covers the entirety of saving and loading individual games.
         * Pretty freakin' important.
         * @param sloth - This!
         */
        public Profile(SlothShip sloth) {
            this.sloth = sloth;
            this.currentParts = new Part[20];
            this.money = new BigDecimal(100.0);
            //  TODO: remove when loading and saving is implemented.
            loadDefaultParts();
        }

        public SlothShip getPlayer() {
            return sloth;
        }

        /**
         * Called if no previous profile is created.
         */
        public void loadDefaultParts() {
            currentParts[0] = PartFactory.fetchPart("rankfcockpit");
            currentParts[1] = PartFactory.fetchPart("rankfgunmount");
            currentParts[2] = PartFactory.fetchPart("rankfhull");
            currentParts[3] = PartFactory.fetchPart("rankfthrusters");
            currentParts[4] = PartFactory.fetchPart("rankfwing1");
            currentParts[5] = PartFactory.fetchPart("rankfwing2");
            currentParts[6] = PartFactory.fetchPart("rankfshieldgenerator");
        }

        /**
         * Hopefully one day load a saved game.
         * @param savefile - the location of the save file.
         * @return - the loaded profile.
         */
        public Profile load(FileHandle savefile) {
            return null;
        }

        /**
         * Again, hopefully one day save a profile.
         * @param savefile - the save location.
         */
        public void save(FileHandle savefile) {}

        /**
         * Get part by type.
         * @param partType - the enum of the part needed.
         * @return - the part from type.
         */
        public Part getPart(PartType partType) {
            switch(partType) {
                case COCKPIT:
                    return currentParts[0];
                case GUNMOUNT:
                    return currentParts[1];
                case HULL:
                    return currentParts[2];
                case THRUSTERS:
                    return currentParts[3];
                case WING1:
                    return currentParts[4];
                case WING2:
                    return currentParts[5];
                case SGENERATOR:
                    return currentParts[6];
            }
            return currentParts[0];
        }

        public void setPart(Part part) {
            switch(part.getPartType()) {
                case COCKPIT:
                    currentParts[0] = part;
                    break;
                case GUNMOUNT:
                    currentParts[1] = part;
                    break;
                case HULL:
                    currentParts[2] = part;
                    break;
                case THRUSTERS:
                    currentParts[3] = part;
                    break;
                case WING1:
                    currentParts[4] = part;
                    break;
                case WING2:
                    currentParts[5] = part;
                    break;
                case SGENERATOR:
                    currentParts[6] = part;
                    break;
            }

        }

        public float getTorque() {
            return currentParts[4].getProperty("Torque").asFloat();
        }

        public void setShield(Box2DSprite sprite) {
            this.shield = sprite;
        }

        public Box2DSprite getShield() {
            return shield;
        }

        public void calculateTotalHealth() {
            float totalHealth = 0;
            for(Part part : currentParts) {
                if(part != null) {
                    totalHealth += part.getHealth();
                }
            }
            setMaxHealth(totalHealth);
        }

        public void calculateEquippedPartCount() {
            equipedPartCount = 0;
            for(Part part : currentParts) {
                if(part != null)
                    equipedPartCount += 1;
            }
        }

        public int getEquippedPartCount() {
            return equipedPartCount;
        }

        public void shuffleDefaultParts() {
            game.getLogger().info("--- BEGIN SHIP SHUFFLE ---");
            for(Part part : currentParts) {
                if(part != null) {
                    game.getLogger().info("Shuffling part " + part.getLocalName() + "!");
                    Part newPart = PartFactory.fetchRandomPart(part.getPartType());
                    game.getLogger().info("Found new part for " + part.getLocalName() + "! New part: " + newPart.getLocalName());
                    setPart(newPart);
                }
            }
            game.getLogger().info("--- END SHIP SHUFFLE ---");
        }

        public void rebuild() {
            calculateEquippedPartCount();
            calculateTotalHealth();
            replenishHealth();
            setBoostCap(100);
            replenishBoost();
        }

        /**
         * Gets this profiles total cash.
         * @return
         */
        public BigDecimal getMoney() {
            return money;
        }

        /**
         * Adds money to this profile.
         */
        public void addMoney(BigDecimal money) {
            money = money.add(money);
        }

        /**
         * Subtracts money from this profile.
         */
        public void subMoney(BigDecimal money) {
            money = money.subtract(money);
        }

    }

    private Box2DSprite gunMount_sprite;

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

        setBody(body);
        body.setUserData(this);
        rebuildShip();
        createRadarWarn();

        ip = new PlayerInputListener(game, chandle);

        setCurVelocity(new Vector2(0.0f, 0.0f));
        setMaxVelocity(new Vector2(0.075f, 0.075f));
        setMaxBoostVelocity(new Vector2(0.090f, 0.090f));

        getBody().setAngularDamping(2.5f);

        getBody().setAngularDamping(2.5f);

        setMaxProjectileCount(30);
    }

    /**
     * When called, this builds the entire ship over again. Usually on initial object creation and part shuffles/assigns.
     */
    public void rebuildShip() {
        destroyAllFixtures();
        PolygonShape shape = new PolygonShape();
        createCockpit(body, shape);
        createWings(body, shape);
        //createGunMount(body, shape);
        createHull(body, shape);
        createThrusters(body, shape);
        //createShield(body);
        shape.dispose();
        profile.rebuild();
    }

    /**
     * @param batch - the sprite batch.
     * @param parentAlpha - Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
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
                incCurVelocity(new Vector2(0.03f, 0.03f));
            } else {
                setCurVelocity(getMaxBoostVelocity());
                if(!hasBoost())
                    isBoosting = false;
            }
            getBody().setLinearDamping(0.0f);
            getBody().applyLinearImpulse(force.x, force.y, getBodyX(), getBodyY(), true);
        } else {
            setCurVelocity(new Vector2(0.0f, 0.0f));
            getBody().setLinearDamping(2.55f);
        }

        if(ip.shift && useBoost()) {
            isBoosting = true;
        }

        if (ip.a) {
            steeringTorque = (profile.getTorque() * getMass());
        }

        if(ip.d) {
            steeringTorque = (-profile.getTorque() * getMass());
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            profile.shuffleDefaultParts();
            rebuildShip();
        }

        if (!isBoosting)
            restoreBoosters();
        else
            depleteBoosters();

        getBody().applyTorque(steeringTorque, true);

    }

    /**
     * Gets the player's current profile.
     * @return - the profile.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Get's the input listener attached to the slothship object.
     * @return - the input listener.
     */
    public PlayerInputListener getPlayerInputListener() {
        return ip;
    }

    /**
     * Get's the box2dsprite object of this player object.
     * @return - the box2dsprite for the gun mount.
     */
    public Box2DSprite getGunMountSprite() {
        return gunMount_sprite;
    }

    /**
     * Get's the boost cap for the player.
     * @return - float of boost cap.
     */
    public float getBoostCap() {
        return boostCap;
    }

    /**
     * Sets the boost cap of player.
     * @param boostCap - the new boost cap.
     */
    public void setBoostCap(float boostCap) {
        this.boostCap = boostCap;
    }

    /**
     * Get the current boost as a float.
     * @return - boost.
     */
    public float getCurBoost() {
        return curBoost;
    }

    /**
     * Set's the current boost.
     * @param curBoost - new boost.
     */
    public void setCurBoost(float curBoost) {
        this.curBoost = curBoost;
    }

    /**
     * Replenish the boost.
     */
    public void replenishBoost() {
        this.curBoost = boostCap;
    }

    /**
     * Boolean to check if the player has any available boost.
     * @return - boolean of boost capacity.
     */
    public boolean hasBoost() {
        return curBoost > 0;
    }

    /**
     * Can use boost.
     * @return - boolean if the player can use boost or not.
     */
    public boolean useBoost() {
        return curBoost >= boostCap;
    }

    /**
     * Begin depleting boosters.
     */
    public void depleteBoosters() {
        if(hasBoost())
            curBoost -= 1.0f;
    }

    /**
     * Start restoring boosters.
     */
    public void restoreBoosters() {
        if(curBoost < boostCap)
            curBoost += 0.15f;
    }

    // End drug boosting. //

    /**
     * Gets the max boost velocity of the player.
     * @return
     */
    public Vector2 getMaxBoostVelocity() {
        return MaxBoostVelocity;
    }

    /**
     * Sets the current boost velocity of the player to the new vector given.
     * @param maxBoostVelocity - new max boost velocity.
     */
    public void setMaxBoostVelocity(Vector2 maxBoostVelocity) {
        MaxBoostVelocity = maxBoostVelocity;
    }

    // All of these are pretty self-explanatory...
    public void createCockpit(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
               62 / Globals.PixelsPerMetre, 62 / Globals.PixelsPerMetre, new Vector2(0,0), 0
        );
        FixtureDef cockpit = new FixtureDef();
        cockpit.shape = shape;
        cockpit.density = 5.0f;
        cockpit.filter.groupIndex = -1;
        Fixture cockpitFixture = body.createFixture(cockpit);

        Part c_part = profile.getPart(PartType.COCKPIT);
        Texture texture = chandle.getManager().get(c_part.getFileName());
        Box2DSprite sprite = createNewBox2dObject("cockpit", texture);
        if(!c_part.getRGB().equalsIgnoreCase("none"))
            sprite.setColor(c_part.getColor());
        cockpitFixture.setUserData(sprite);
    }

    public void createGunMount(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
                12 / Globals.PixelsPerMetre, 20 / Globals.PixelsPerMetre,
                ScreenUtil.divideVectorByPPM(new Vector2(0, 65)), 0
        );
        FixtureDef gunMount = new FixtureDef();
        gunMount.shape = shape;
        gunMount.density = 1.5f;
        gunMount.filter.groupIndex = -1;
        Fixture gunMountFixture = body.createFixture(gunMount);

        Part c_part = profile.getPart(PartType.GUNMOUNT);
        Texture texture = chandle.getManager().get(c_part.getFileName());
        Box2DSprite sprite = createNewBox2dObject("gunmount", texture);
        if(!c_part.getRGB().equalsIgnoreCase("none"))
            sprite.setColor(c_part.getColor());
        gunMountFixture.setUserData(sprite);
    }

    public void createHull(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
                80 / Globals.PixelsPerMetre, 176 / Globals.PixelsPerMetre,
                ScreenUtil.divideVectorByPPM(new Vector2(0, -206)), 0
        );
        FixtureDef hull = new FixtureDef();
        hull.shape = shape;
        hull.density = 10.0f;
        hull.filter.groupIndex = -1;
        Fixture hullFixture = body.createFixture(hull);

        Part c_part = profile.getPart(PartType.HULL);
        Texture texture = chandle.getManager().get(c_part.getFileName());
        Box2DSprite sprite = createNewBox2dObject("hull", texture);
        if(!c_part.getRGB().equalsIgnoreCase("none"))
            sprite.setColor(c_part.getColor());
        hullFixture.setUserData(sprite);
    }

    public void createThrusters(Body body, PolygonShape shape) {
        shape = new PolygonShape();
        shape.setAsBox(
                84 / Globals.PixelsPerMetre, 106 / Globals.PixelsPerMetre,
                ScreenUtil.divideVectorByPPM(new Vector2(0, -400)), 0
        );
        FixtureDef thruster = new FixtureDef();
        thruster.shape = shape;
        thruster.density = 2.0f;
        thruster.filter.groupIndex = -1;
        Fixture thrusterFixture = body.createFixture(thruster);

        Part c_part = profile.getPart(PartType.THRUSTERS);
        Texture texture = chandle.getManager().get(c_part.getFileName());
        Box2DSprite sprite = createNewBox2dObject("thrusters", texture);
        if(!c_part.getRGB().equalsIgnoreCase("none"))
            sprite.setColor(c_part.getColor());
        thrusterFixture.setUserData(sprite);
    }

    public void createWings(Body body, PolygonShape shape) {
        // Create the first wing.
        shape = new PolygonShape();
        shape.setAsBox(
                102 / Globals.PixelsPerMetre, 132 / Globals.PixelsPerMetre,
                ScreenUtil.divideVectorByPPM(new Vector2(120, -195)), 0
        );
        FixtureDef winga = new FixtureDef();
        winga.shape = shape;
        winga.density = 1.5f;
        winga.filter.groupIndex = -1;
        Fixture WingAFixture = body.createFixture(winga);

        Part c_part1 = profile.getPart(PartType.WING1);
        Texture texture1 = chandle.getManager().get(c_part1.getFileName());
        Box2DSprite sprite1 = createNewBox2dObject("winga", texture1);
        if(!c_part1.getRGB().equalsIgnoreCase("none"))
            sprite1.setColor(c_part1.getColor());
        WingAFixture.setUserData(sprite1);

        // Create the second wing.
        shape = new PolygonShape();
        shape.setAsBox(
                102 / Globals.PixelsPerMetre, 132 / Globals.PixelsPerMetre,
                ScreenUtil.divideVectorByPPM(new Vector2(-120, -195)), 0
        );
        FixtureDef wingb = new FixtureDef();
        wingb.shape = shape;
        wingb.density = 1.5f;
        wingb.filter.groupIndex = -1;
        Fixture WingBFixture = body.createFixture(wingb);

        Part c_part2 = profile.getPart(PartType.WING2);
        Texture texture2 = chandle.getManager().get(c_part2.getFileName());
        Box2DSprite sprite2 = createNewBox2dObject("wingb", texture2);
        if(!c_part2.getRGB().equalsIgnoreCase("none"))
            sprite2.setColor(c_part2.getColor());
        WingBFixture.setUserData(sprite2);
    }

    public void createShield(Body body) {
        PolygonShape shape = new PolygonShape();
        Vector2[] verts = {
                new Vector2(0, -1), new Vector2(1, 1), new Vector2(0, -1.50f), new Vector2(1, -1.75f),
                new Vector2(0, -1), new Vector2(-1, -1.75f), new Vector2(0, -2.56f), new Vector2(-1, 1)
        };
        shape.set(verts);


        FixtureDef shield_def = new FixtureDef();
        shield_def.shape = shape;
        shield_def.filter.groupIndex = -1;
        Fixture shieldFixture = body.createFixture(shield_def);

        Part c_part = profile.getPart(PartType.SGENERATOR);
        Texture texture = chandle.getManager().get(c_part.getFileName());
        Box2DSprite sprite = createNewBox2dObject("shield", texture);
        if(!c_part.getRGB().equalsIgnoreCase("none"))
            sprite.setColor(c_part.getColor());
        shieldFixture.setUserData(sprite);
        profile.setShield(sprite);


        shape.dispose();
    }

    /**
     * Process an ore contact.
     * @param ore - the ore that was just collected by the player.
     */
    public void processOreContact(Ore ore) {
        BigDecimal worth = ore.getOreWorth();
        // When the refinery is integrated, this is where we'd use its efficiency module.
        profile.addMoney(worth);
        String quarterMasterResponse = "...";
        switch(ore.getType()) {
            case ROCK:
                quarterMasterResponse = "Processed one really nice [GRAY]rock[]. Estimated worth: $" + ore.getOreWorth();
                break;
            case GOLD:
                quarterMasterResponse = "Processed one really big piece of [YELLOW]gold[]. Estimated worth: $" + ore.getOreWorth();
                break;
            case PLATINUM:
                quarterMasterResponse = "Nice find, Processed one [WHITE]platinum[]. Estimated worth: $" + ore.getOreWorth();
                break;
        }
        getPlayerHud().getConsole().writeFromCrew(PlayerConsole.CrewType.QUARTERMASTER, quarterMasterResponse);
    }

    /**
     * Sets the player's hud.
     * @param hud - the hud object.
     */
    public void setPlayerHud(PlayerHUD hud) {
        this.hud = hud;
    }

    /**
     * Get's the player's hud. Used for getting systems like radar and console.
     * @return - player hud.
     */
    public PlayerHUD getPlayerHud() {
        return hud;
    }

    /**
     * Radar warning generator.
     */
    public void createRadarWarn() {
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                getPlayerHud().getConsole().writeFromCrew(PlayerConsole.CrewType.FIRSTMATE, "Captain, radar's showin' [WHITE]" + getPlayerHud().getRadar().getBlips() + "[] objects!");
            }
        }, 0, 30);
    }

}
