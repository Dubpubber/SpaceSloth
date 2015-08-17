package com.loneboat.spacesloth.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.screens
 * Created by Dubpub on 8/6/2015.
 */
public abstract class GameScreen implements Screen {

    // Get our basics
    public SpaceSloth game;
    public ContentHandler chandle;

    // Box2d Objects
    public World world;

    // Setup the stages
    public Stage MainStage;
    public SpriteBatch batch;
    public BitmapFont font;

    // Grab our cameras from the content handler
    public OrthographicCamera main_cam;
    public OrthographicCamera hud_cam;

    // Box2d debug renderer
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera box2DCam;

    // Input trackers
    public float timer;
    public Vector2 mouseLoc;

    // Background texture.
    private Texture static_background;

    // Main Actor
    private GameObject LeadActor;
    private boolean CameraFollow;

    /**
     * Constructor.
     * @param game - SpaceSloth class
     * @param chandle - Content handler object.
     */
    public GameScreen(SpaceSloth game, ContentHandler chandle) {
        this.game = game;
        this.chandle = chandle;

        // Create the world
        world = new World(new Vector2(0, 0f), true);

        // Create the stage objects.
        MainStage = new Stage();
        batch = ContentHandler.batch;
        font = ContentHandler.debugfont;
    }

    public void setStaticBackground(Texture texture) {
        this.static_background = texture;
    }

    public void setLeadActor(GameObject actor, boolean CameraFollow) {
        this.LeadActor = actor;
        this.CameraFollow = CameraFollow;
    }

    /**
     * The first method that will be ran after the screen is switched to this.
     */
    @Override
    public void show() {
        this.debugRenderer = new Box2DDebugRenderer();

        // Assign our cameras.
        main_cam = chandle.getMainCamera();
        main_cam.setToOrtho(false, ContentHandler.GAMEWIDTH, ContentHandler.GAMEHEIGHT);

        hud_cam = chandle.getHudCamera();
        hud_cam.setToOrtho(false, ContentHandler.GAMEWIDTH, ContentHandler.GAMEHEIGHT);

        box2DCam = new OrthographicCamera();
        box2DCam.setToOrtho(false, ContentHandler.GAMEWIDTH / Globals.PixelsPerMetre, ContentHandler.GAMEHEIGHT / Globals.PixelsPerMetre);
    }


    @Override
    public void render(float delta) {
        // First, clear the screen.
        Gdx.graphics.getGL20().glClearColor(0.50f, 0.50f, 0.50f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Second; update the main camera's position.
        if(LeadActor != null && CameraFollow) {
            main_cam.position.set(
                    LeadActor.getBodyX(),
                    LeadActor.getBodyY(),
                    0
            );
            // Update that camera
            main_cam.update();
        }

        // Now we're going to update the mouse relative to the screen.
        Vector3 worldcoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        hud_cam.unproject(worldcoords);
        mouseLoc = new Vector2(worldcoords.x, worldcoords.y);

        // Then, draw the background. (So it's always just the background. This must always be rendered first!)
        batch.setProjectionMatrix(main_cam.combined);
        if(static_background != null) {
            batch.begin();
            batch.draw(static_background, 0, 0);
            batch.end();
        }

        // Fourth, draw all our game objects using box2d-utils!
        // - Notice how the projection matrix is set to project onto the main camera.
        batch.begin();
        Box2DSprite.draw(batch, world);
        AnimatedBox2DSprite.draw(batch, world);
        batch.end();

        // Fifth, we're going to draw the hud
        batch.setProjectionMatrix(hud_cam.combined);
        batch.begin();
        batch.end();
        //

        // Finally, we're going to draw the debugs
        if(game.getLogger().getLevel() == Logger.DEBUG && LeadActor != null) {
            box2DCam.position.set(
                    LeadActor.getBodyX(),
                    LeadActor.getBodyY(),
                    0
            );
            box2DCam.update();
            debugRenderer.render(world, box2DCam.combined);

            float angle = (float) Math.atan2(
                    (Gdx.input.getY() - 270),
                    -(Gdx.input.getX() - 270)
            );

            angle = (float) -Math.toDegrees(angle);

            // Notice how we're still projecting to the hud cam!
            batch.setProjectionMatrix(hud_cam.combined);
            batch.begin();
            font.draw(batch, "Debug Mode", 3, 475);
            font.draw(batch, "X: " + Math.round(mouseLoc.x), 3, 450);
            font.draw(batch, "Y: " + Math.round(mouseLoc.y), 3, 425);
            font.draw(batch, "Angle: " + angle, 3, 400);
            batch.end();
        }

        // Lastly, Step the world's physics then allow the subclass to override!
        world.step(Globals.WorldStep, 8, 3);
    }

    @Override
    public void resize(int width, int height) {
        // Update the main camera.
        main_cam.viewportWidth = width / 25;
        main_cam.viewportHeight = height / 25;
        main_cam.update();

        MainStage.getBatch().setProjectionMatrix(main_cam.combined);

        // Update the hub camera.
        hud_cam.viewportWidth = width;
        hud_cam.viewportHeight = height;
        hud_cam.update();

        MainStage.getBatch().setProjectionMatrix(hud_cam.combined);

        // Update the box2d debug camera.
        box2DCam.viewportWidth = width / 25;
        box2DCam.viewportHeight = height / 25;
        box2DCam.update();

        MainStage.getBatch().setProjectionMatrix(box2DCam.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        MainStage.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    public void addLeadActor() {

    }

}
