package com.loneboat.spacesloth.main.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.screens.GameLevel;
import com.loneboat.spacesloth.main.util.GameObjectTracker;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * com.loneboat.spacesloth.main.game
 * Created by Dubpub on 8/6/2015.
 */
public abstract class GameObject extends Actor implements GameObjectTracker {
    // Get our basics.
    public SpaceSloth game;
    public ContentHandler chandle;
    public Stage active_stage;

    // Name of Game Object.
    public String ObjLabel;

    // Get our Box2D world
    public World world;

    // Get the screen for GameScreen related methods.
    public GameLevel level;

    // Box2D objects
    public Body body;
    public BodyDef bdef;
    public Fixture fixture;

    // Utility objects.
    public Sprite sprite;

    public HashMap<String, AnimatedBox2DSprite> animatedSprites;
    public HashMap<String, Box2DSprite> sprites;

    // Local Animation storage.
    private TreeMap<String, Animation> animations;

    // Actives
    private ProjectileObject currentProjectile;

    // Tracker variables
    private float lives;
    private float health;
    private float maxHealth;
    private Vector2 MaxVelocity;
    private Vector2 CurVelocity;
    private int projectile_count = 0;
    private int total_projectile_count = 10;

    // Ignore delay
    private boolean ignoreDelay = false;

    /**
     * Creates a new game object that is animated but not a box2d object.
     * @param game - Game Object.
     * @param chandle - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public GameObject(SpaceSloth game, ContentHandler chandle, Stage active_stage, String ObjLabel) {
        this.game = game;
        this.chandle = chandle;
        this.active_stage = active_stage;
        this.ObjLabel = ObjLabel;

        sprites = new HashMap<String, Box2DSprite>();
        animatedSprites = new HashMap<String, AnimatedBox2DSprite>();
    }

    /**
     * Creates a new game object that is part of box2d.
     * @param game - Game Object.
     * @param chandle - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
    */
    public GameObject(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, String ObjLabel) {
        this.game = game;
        this.chandle = chandle;
        this.active_stage = active_stage;
        this.world = world;
        this.ObjLabel = ObjLabel;

        sprites = new HashMap<String, Box2DSprite>();
        animatedSprites = new HashMap<String, AnimatedBox2DSprite>();
    }

    /**
     * Sets the body for easy box2d handling.
     * @param body - The created body from the extended Gameobject class.
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Gets the box2d body.
     * @return - the body.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Gets the relative box2d body, if set, x position.
     * @return - The position on screen relative to the origin of the world.
     */
    public float getBodyX() {
        return getBody().getPosition().x;
    }

    /**
     * Gets the relative box2d body, if set, y position.
     * @return - The position on the screen relative to the origin of the world.
     */
    public float getBodyY() {
        return getBody().getPosition().y;
    }

    /**
     * Sets the screen object, Game Screen object, for this game object.
     * @param level - Upon creation, sets from the extended game object.
     */
    public void setLevel(GameLevel level) {
        this.level = level;
    }

    /**
     * Creates a basic sprite, without box2d capabilities.
     * @param filename - File handle for the texture of the sprite.
     */
    public void createBasicSprite(String filename) {
        Texture bsprite = chandle.getManager().get(filename, Texture.class);
        sprite = new Sprite(bsprite, bsprite.getWidth(), bsprite.getHeight());
    }

    /**
     * Draws the game object.
     * @param batch - the sprite batch.
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    /**
     * Gets the lives from this objects game tracker.
     * @return - float of the number of lives left for this object.
     */
    @Override
    public float getLives() {
        return lives;
    }

    /**
     * Sets the maximum number of lives for this game object in it's own personal game tracker.
     * @param lives - float of the new number of lives to be set for this game object.
     */
    @Override
    public void setLives(float lives) {
        this.lives = lives;
    }

    /**
     * Gets the health for this game object.
     * @return - amount of current health for this game object.
     */
    @Override
    public float getHealth() {
        return health;
    }

    /**
     * Sets the health for this game object.
     * @param health - Hard sets the amount of health, as a float, for this game object.
     */
    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * Gets the current velocity of this game object.
     * @return - The current velocity as a vector2.
     */
    @Override
    public Vector2 getCurVelocity() {
        return CurVelocity;
    }

    /**
     * Sets the current velocity of this game object.
     * @param CurVelocity - a vector2.
     */
    @Override
    public void setCurVelocity(Vector2 CurVelocity) {
        this.CurVelocity = CurVelocity;
    }

    @Override
    public void incCurVelocity(Vector2 incVel) {
        if(compareVelocity()) {
            CurVelocity.x += incVel.x;
            CurVelocity.y += incVel.y;
        } else
            setCurVelocity(getMaxVelocity());
    }

    @Override
    public void decCurVelocity(Vector2 decVel) {
        if(CurVelocity.isZero()) {
            CurVelocity.x -= decVel.x;
            CurVelocity.y -= decVel.y;
        }
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isDead() {
        return getHealth() <= 0;
    }

    @Override
    public boolean compareVelocity() {
        return (CurVelocity.x < MaxVelocity.x && CurVelocity.y < MaxVelocity.y);
    }

    @Override
    public Vector2 getMaxVelocity() {
        return MaxVelocity;
    }

    @Override
    public void setMaxVelocity(Vector2 MaxVelocity) {
        this.MaxVelocity = MaxVelocity;
    }

    @Override
    public void subtrackHealth(float health) {
        this.health -= health;
    }

    @Override
    public void addHealth(float health) {
        this.health += health;
    }

    @Override
    public void subtractLives(float lives) {
        this.lives -= lives;
    }

    @Override
    public void addLives(float lives) {
        this.lives += lives;
    }

    public void destroy() {
        if(world != null && body != null)
            world.destroyBody(body);
        remove();
    }

    public void queueDestroy() {
        if(level != null)
            level.queueDestroyObject(this);
        else
            game.getLogger().info("currentScreen is null, cannot safely destroy " + ObjLabel);
    }

    public int getProjectileCount() {
        return projectile_count;
    }

    public void incProjectileCount(int count) {
        this.projectile_count += count;
    }

    public void decProjectileCount(int count) {
        this.projectile_count -= count;
    }

    public int getMaxProjectileCount() {
        return total_projectile_count;
    }

    public void setMaxProjectileCount(int total_projectile_count) {
        this.total_projectile_count = total_projectile_count;
    }

    public boolean canFire() {
        if(ignoreDelay)
            return projectile_count < total_projectile_count;
        else
            return projectile_count < total_projectile_count && (level.delay <= 0);
    }

    public void setCurrentProjectile(ProjectileObject projectile) {
        this.currentProjectile = projectile;
    }

    public ProjectileObject getCurrentProjectile() {
        return currentProjectile;
    }

    public void fire() {
        if(currentProjectile != null) {
            projectile_count++;
            // Player can fire active projectile.
            currentProjectile.setDestroyTime();
            level.addProjectile(currentProjectile);
            level.delay += 0.2;
        }
    }

    public boolean isIgnoreDelay() {
        return ignoreDelay;
    }

    public void setIgnoreDelay(boolean ignoreDelay) {
        this.ignoreDelay = ignoreDelay;
    }

    public void replenishHealth() {
        this.health = this.maxHealth;
    }

    public float getMass() {
        if(body != null)
            return body.getMass();
        else
            return 0;
    }

    public AnimatedBox2DSprite createNewAnimatedBox2dObject(String tag, Animation anime) {
        if(!animatedSprites.containsKey(tag))
            animatedSprites.put(tag, new AnimatedBox2DSprite(new AnimatedSprite(anime)));
        return animatedSprites.get(tag);
    }

    public Box2DSprite createNewBox2dObject(String tag, Texture texture) {
        if(!sprites.containsKey(tag))
            sprites.put(tag, new Box2DSprite(texture));
        return sprites.get(tag);
    }

}