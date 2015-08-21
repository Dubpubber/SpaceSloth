package com.loneboat.spacesloth.main.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.loneboat.spacesloth.main.SpaceSloth;

/**
 * com.loneboat.spacesloth.main.content
 * Created by Dubpub on 8/6/2015.
 */
public class ContentHandler {

    public static int GAMEWIDTH;
    public static int GAMEHEIGHT;

    // Save the game object and the asset manager for later use.
    private SpaceSloth game;
    private AssetManager manager;

    // Our general camera references will be kept here.
    private OrthographicCamera main_cam;
    private OrthographicCamera hud_cam;
    public static SpriteBatch batch;
    public static BitmapFont debugfont;

    // The updating progress of the asset manager.
    private float progress;

    /**
     * Constructor for the content handler, this class manages most global objects needed for the game.
     * @param game - can be changed to accommodate any like minded extension.
     */
    public ContentHandler(SpaceSloth game) {
        this.game = game;
        this.manager = new AssetManager();

        GAMEWIDTH = Gdx.graphics.getWidth();
        GAMEHEIGHT = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        debugfont = new BitmapFont();
        main_cam = new OrthographicCamera(1, GAMEWIDTH / GAMEHEIGHT);
        main_cam.setToOrtho(false, GAMEWIDTH, GAMEHEIGHT);
        hud_cam = new OrthographicCamera();
        hud_cam.setToOrtho(false, GAMEWIDTH, GAMEHEIGHT);

        queueAssets();
    }

    private void queueAssets() {
        manager.load("Backgrounds/spacebackground_1.jpg", Texture.class);
        manager.load("Sprites/Ship_A1.png", Texture.class);
        manager.load("Sprites/mouseSprite.png", Texture.class);
        manager.load("Sprites/Thruster_A1.png", Texture.class);
        manager.load("Sprites/GunMount_A1.png", Texture.class);
        manager.load("Sprites/Asteroid_2_smallball.png", Texture.class);
        manager.load("Sprites/Asteroid_2_bomb.png", Texture.class);
    }

    /**
     * Gets the asset manager object.
     * @return manager
     */
    public AssetManager getManager() {
        return manager;
    }

    /**
     * Updates the manager.
     * @return - if the manager is finished updating.
     */
    public boolean updateManager() {
        progress = manager.getProgress();
        manager.setLogger(game.getLogger());
        return manager.update();
    }

    public OrthographicCamera getMainCamera() {
        return main_cam;
    }

    public OrthographicCamera getHudCamera() {
        return hud_cam;
    }

    public float getProgress() {
        return progress * 100;
    }

}
