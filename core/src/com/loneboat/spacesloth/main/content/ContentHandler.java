package com.loneboat.spacesloth.main.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.partsystem.PartFactory;
import sun.font.TrueTypeFont;

/**
 * com.loneboat.spacesloth.main.content
 * Created by Dubpub on 8/6/2015.
 */
public class ContentHandler {

    public static int GAMEWIDTH;
    public static int GAMEHEIGHT;

    // Save the game object and the asset manager for later use.
    private SpaceSloth game;
    public static AssetManager manager;

    // Our general camera references will be kept here.
    private OrthographicCamera main_cam;
    private OrthographicCamera hud_cam;
    private Viewport fit;
    public static SpriteBatch batch;

    // The updating progress of the asset manager.
    private float progress;

    // For the parts, duh.
    // TODO: Rename PartFactory to PartFactoryAssemblyObjectDroneProduct (joking)
    public PartFactory partFactory;

    // All the fonts for the game. So cool! Right?! RIGHT?
    public static BitmapFont debugfont;
    public static BitmapFont onScreenText;

    /**
     * Constructor for the content handler, this class manages most global objects needed for the game.
     * @param game - can be changed to accommodate any like minded extension.
     */
    public ContentHandler(SpaceSloth game) {
        this.game = game;
        manager = new AssetManager();

        GAMEWIDTH = Gdx.graphics.getWidth();
        GAMEHEIGHT = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        debugfont = new BitmapFont();
        main_cam = new OrthographicCamera(1, GAMEWIDTH / GAMEHEIGHT);
        main_cam.setToOrtho(false, GAMEWIDTH, GAMEHEIGHT);
        hud_cam = new OrthographicCamera();
        hud_cam.setToOrtho(false, GAMEWIDTH, GAMEHEIGHT);

        PartFactory.function(game);

        queueAssets();
        onScreenText = generateAllerFont(22);

    }

    private void queueAssets() {
        manager.load("Sprites/mouseSprite.png", Texture.class);
        manager.load("Sprites/Asteroid_2_bomb.png", Texture.class);
        manager.load("Sprites/BlueBlast_A1.png", Texture.class);
        manager.load("Sprites/SpaceBackground_1.png", Texture.class);
        manager.load("Sprites/radar_background.png", Texture.class);
        manager.load("Sprites/radar_ship.png", Texture.class);
        manager.load("Backgrounds/SpaceSloth_SpaceBackground_1.png", Texture.class);

        // Load ship parts
        manager.load("Parts/Sprites/BaseCockpit.png", Texture.class);
        manager.load("Parts/Sprites/BaseGunMount.png", Texture.class);
        manager.load("Parts/Sprites/BaseHull.png", Texture.class);
        manager.load("Parts/Sprites/BaseThrusters.png", Texture.class);
        manager.load("Parts/Sprites/BaseWing1.png", Texture.class);
        manager.load("Parts/Sprites/BaseWing2.png", Texture.class);

        manager.load("Parts/Sprites/BaseCockpit_1.png", Texture.class);
        manager.load("Parts/Sprites/BaseGunMount_1.png", Texture.class);
        manager.load("Parts/Sprites/BaseHull_1.png", Texture.class);
        manager.load("Parts/Sprites/BaseThrusters_1.png", Texture.class);
        manager.load("Parts/Sprites/BaseWing1_1.png", Texture.class);
        manager.load("Parts/Sprites/BaseWing2_1.png", Texture.class);

        // Load ship systems
        manager.load("Parts/Sprites/BaseShield.png", Texture.class);

        // Load ores
        manager.load("Sprites/Ore_1.png", Texture.class);
        manager.load("Sprites/Ore_2.png", Texture.class);
        manager.load("Sprites/Ore_3.png", Texture.class);
        manager.load("Sprites/Ore_4.png", Texture.class);

        // Load animation packs!
        manager.load("Animations/ss_asteroidpack.pack", TextureAtlas.class);

        // Load the fonts
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter s1 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        s1.fontFileName = "Fonts/AnonymousPro-Regular.ttf";
        s1.fontParameters.minFilter = Texture.TextureFilter.Linear;
        s1.fontParameters.magFilter = Texture.TextureFilter.Linear;
        manager.load("Fonts/AnonymousPro-Regular.ttf", BitmapFont.class, s1);
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

    public Animation getAnimation(String textureAtlas, String handle, float duration) {
        if(manager.isLoaded(textureAtlas)) {
            TextureAtlas atlas = manager.get(textureAtlas, TextureAtlas.class);
            return new Animation(duration, atlas.findRegions(handle));
        } else
            return null;
    }

    public Viewport getHudViewport() {
        return fit;
    }

    public void loadFonts() {

    }

    public static BitmapFont generateAllerFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Aller.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        return generator.generateFont(parameter);
    }

}
