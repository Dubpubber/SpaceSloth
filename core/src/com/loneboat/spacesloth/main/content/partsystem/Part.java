package com.loneboat.spacesloth.main.content.partsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.Box2DSpriteObject;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.util.ScreenUtil;

/**
 * com.loneboat.spacesloth.main.content.systems
 * Created by Dubpub on 8/30/2015.
 */
public abstract class Part {

    /**
     * The long awaited part system! WOOHOO!
     *
     *  - Parts are ordered and identified by rank and name.
     *  - Each part has different responsibilities.
     *
     *  - Current list of visible parts:
     *    [Double Mold] Wings
     *     - In charge of handing steering torque of the ship.
     *    [Single Mold] Cockpit
     *     - On screen HUD improvements.
     *    [Single Mold] GunMount
     *     - Fire rate.
     *    [Single Mold] Hull
     *     - Amount of gold your ship can hold.
     *    [Single Mold] Thrusters
     *     - Duration of thrust for your ship.
     *
     *  - Current List of ship systems parts:
     *    [Single Mold] Reactor
     *     - Base ship speed and how fast thrust recharges
     *    [Single Mold] Armory
     *     - Weapon type count ship can hold.
     *    [Single Mold] Tractor beam
     *     - Range on which your ship can pull gold in.
     *    [Single Mold] Refinery
     *     - Dictates your ships ability to harvest gold from ore.
     */

    // Rank of this part.
    /**
     * Rank Overview:
     *  [A-F]
     *      Tier 1 list of parts.
     *  [A1-F1]
     *      Tier 2 list of parts.
     *  [A2-F3]
     *      Tier 3 list of parts.
     *  [S-X]
     *      Tier 4 list of parts.
     *  [S1000-X1000 Series]
     *      Dev. list of parts.
     */
    private String RANK;

    // This parts cost. Used when purchasing and again when repairing.
    private double COST;

    // This parts health represented for the health of the total ship. Not this parts particular health.
    // However, repairing the ship will use each parts individual health.
    private float HEALTH;

    // This parts cost of repair factor. Formula: (COST * REPAIR_FACTOR)
    private double REPAIR_FACTOR;

    // Boolean that tells the system this part will be displayed on the ship itself.
    private boolean VISIBLE;

    // Localized name of part.
    private String LOCAL_NAME;

    // String used to look up sprite of part if
    private String NAME;

    // Box2d body of part.
    private GameObject gameObject;

    // Box2dSpriteObject of part.
    private Box2DSpriteObject box2DSpriteObject;

    // Mass of part as float
    private float MASS;

    /**
     * Creates a new part for the system.
     * @param RANK - rank of the part.
     * @param COST - cost of the part.
     * @param HEALTH - health of this part.
     * @param VISIBLE - is this part visible on the ship?
     * @param LOCAL_NAME - Name given to the part if it needs to be read out to console or on screen. "Thruster 1"
     * @param NAME - Name of the part used to query the Content Handlers getManager().get(NAME)
     */
    public Part(String RANK, double COST, float HEALTH, double REPAIR_FACTOR, boolean VISIBLE, String LOCAL_NAME, String NAME, GameObject gameObject) {
        setRank(RANK);
        setCost(COST);
        setHealth(HEALTH);
        setRepairFactor(REPAIR_FACTOR);
        setVisible(VISIBLE);
        setLocalizedName(LOCAL_NAME);
        setName(NAME);
        setGameObject(gameObject);
    }

    public boolean isVisible() {
        return VISIBLE;
    }

    public void setVisible(boolean VISIBLE) {
        this.VISIBLE = VISIBLE;
    }

    public double getCost() {
        return COST;
    }

    public void setCost(double COST) {
        this.COST = COST;
    }

    public float getHealth() {
        return HEALTH;
    }

    public void setHealth(float HEALTH) {
        this.HEALTH = HEALTH;
    }

    public String getLocalizedName() {
        return LOCAL_NAME;
    }

    public void setLocalizedName(String LOCAL_NAME) {
        this.LOCAL_NAME = LOCAL_NAME;
    }

    public String getName() {
        return NAME;
    }

    public void setName(String NAME) {
        this.NAME = NAME;
    }

    public String getRank() {
        return RANK;
    }

    public void setRank(String RANK) {
        this.RANK = RANK;
    }

    public double getRepairFactor() {
        return REPAIR_FACTOR;
    }

    public void setRepairFactor(double REPAIR_FACTOR) {
        this.REPAIR_FACTOR = REPAIR_FACTOR;
    }

    public double calculateRepairCost() {
        return getCost() * getRepairFactor();
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Box2DSpriteObject getBox2DSpriteObject() {
        return box2DSpriteObject;
    }

    public void setBox2DSpriteObject(Box2DSpriteObject box2DSpriteObject) {
        this.box2DSpriteObject = box2DSpriteObject;
    }

    public float getMass() {
        return MASS;
    }

    public void setMass(float MASS) {
        this.MASS = MASS;
    }

    public boolean createFixtureForPart(float x, float y) {
        if(gameObject.getBody() == null)
            return false;
        Texture texture = ContentHandler.manager.get(getName(), Texture.class);
        int width = texture.getWidth();
        int height = texture.getHeight();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                width / Globals.PixelsPerMetre,
                height / Globals.PixelsPerMetre,
                ScreenUtil.scaleVector(x, y), 0
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = getMass();

        Fixture fixture = gameObject.getBody().createFixture(fixtureDef);
        setBox2DSpriteObject(new Box2DSpriteObject(texture, gameObject));
        fixture.setUserData(getBox2DSpriteObject());
        shape.dispose();
        return true;
    }

}
