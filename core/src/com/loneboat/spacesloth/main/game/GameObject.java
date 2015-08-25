package com.loneboat.spacesloth.main.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.screens.GameScreen;
import com.loneboat.spacesloth.main.util.GameObjectTracker;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

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
    public GameScreen currentScreen;

    // Box2D objects
    public Body body;
    public BodyDef bdef;
    public Fixture fixture;

    // Utility objects.
    public Sprite sprite;
    public AnimatedSprite asprite;
    public AnimatedBox2DSprite animatedBox2DSprite;
    public Box2DSprite box2DSprite;

    // Local Animation storage.
    private TreeMap<String, Animation> animations;

    // Tracker variables
    private float lives;
    private float health;
    private Vector2 MaxVelocity;
    private Vector2 CurVelocity;

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
    }

    /**
     * Creates an animation from an atlas.
     * @param key - Name of the animation for reference in the TreeMap.
     * @param regions - String array of the names of the textures of the atlas.
     * @param atlas - Atlas the method will be retrieving the textures from.
     * @param speed - Speed of the animation.
     */
    public void addAnimation(String key, String[] regions, TextureAtlas atlas, float speed) {
        TextureRegion[] tregion = new TextureRegion[regions.length];
        for(int i = 0; i < regions.length; i++) {
            tregion[i] = atlas.findRegion(regions[i]);
        }
        animations.put(key, new Animation(speed, tregion));
        if(sprite == null && animatedBox2DSprite == null) {
            // If our sprites are null, create them.
            sprite = new AnimatedSprite(animations.get(key));
            if (world != null) {
                // It's a box2d object!
                animatedBox2DSprite = new AnimatedBox2DSprite(asprite);
            }
        }
    }

    public void setBox2DSprite(Box2DSprite sprite) {
        this.box2DSprite = sprite;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public float getBodyX() {
        return getBody().getPosition().x;
    }

    public float getBodyY() {
        return getBody().getPosition().y;
    }

    public void setCurrentScreen(GameScreen screen) {
        this.currentScreen = screen;
    }

    public void createBasicSprite(String filename) {
        Texture bsprite = chandle.getManager().get(filename, Texture.class);
        sprite = new Sprite(bsprite, bsprite.getWidth(), bsprite.getHeight());
    }

    /**
     * @param batch - the sprite batch.
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public float getLives() {
        return lives;
    }

    @Override
    public void setLives(float lives) {
        this.lives = lives;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }


    @Override
    public Vector2 getCurVelocity() {
        return CurVelocity;
    }

    @Override
    public void setCurVelocity(Vector2 CurVelocity) {
        this.CurVelocity = CurVelocity;
    }

    @Override
    public void incCurVelocity(Vector2 incVel) {
        if(compareVelocity()) {
            CurVelocity.x += incVel.x;
            CurVelocity.y += incVel.y;
        }
    }

    @Override
    public void decCurVelocity(Vector2 decVel) {
        if(CurVelocity.isZero()) {
            CurVelocity.x -= decVel.x;
            CurVelocity.y -= decVel.y;
        }
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

}