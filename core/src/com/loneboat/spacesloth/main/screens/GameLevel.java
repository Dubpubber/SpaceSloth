package com.loneboat.spacesloth.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.game.ProjectileObject;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.game.actors.UI.PlayerHUD;
import com.loneboat.spacesloth.main.game.systems.PlayerRadar;
import com.loneboat.spacesloth.main.game.worldobjects.Asteroid;
import com.loneboat.spacesloth.main.game.worldobjects.enemies.AsteroidBomb;
import com.loneboat.spacesloth.main.game.worldobjects.ores.Ore;
import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/28/2015.
 *
 *  Separate from GameScreen because not every screen is a level.
 *      This class holds things specific to the active level and handles such processes.
 */
public class GameLevel extends GameScreen {

    private SlothShip player;

    private final DIFFICULTY levelDifficulty;

    // Projectiles
    // TODO: MAke into treemap or stack.
    private ArrayList<ProjectileObject> projectiles;

    // To destroy
    private ArrayList<GameObject> removables;

    // Simple HashMap of Limit Tracking.
    private HashMap<String, AtomicInteger> worldlyObjects;

    public GameLevel(SpaceSloth game, ContentHandler chandle, DIFFICULTY levelDifficulty) {
        super(game, chandle);

        this.levelDifficulty = levelDifficulty;
        projectiles = new ArrayList<ProjectileObject>();
        removables = new ArrayList<GameObject>();
        worldlyObjects = new HashMap<String, AtomicInteger>();
        setMousePixmap();
        adjustLimits();
        spawnPlayer();
        addPlayerHUD();
        addSystems();
    }

    public enum DIFFICULTY {
        EASYMODE(1), FRIENDLY(2), AGGRESIVE(3), TORMENT(4), UBER(5);

        private final int difficultylevel;
        DIFFICULTY(int difficultylevel) {
            this.difficultylevel = difficultylevel;
        }

        private int getLevel() {
            return difficultylevel;
        }

    }

    @Override
    public void render(float delta) {
        // First, clear the screen.
        Gdx.graphics.getGL20().glClearColor(0.50f, 0.50f, 0.50f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        timer += delta;

        if(delay > -0.2)
            delay -= delta;

        MainStage.act(delta);
        HudStage.act(delta);

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
        Vector3 worldcoords = new Vector3(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()), 0);
        hud_cam.unproject(worldcoords);
        mouseLoc = new Vector2(worldcoords.x, worldcoords.y);

        // TODO: fix background rendering later. Workaround for now!
        batch.setProjectionMatrix(hud_cam.combined);
        if(static_background != null) {
            batch.begin();
            batch.draw(static_background, -640, HEIGHT - static_background.getHeight());
            batch.end();
        }

        batch.setProjectionMatrix(main_cam.combined);
        // Fourth, draw all our game objects using box2d-utils!
        // - Notice how the projection matrix is set to project onto the main camera.
        batch.begin();
        Box2DSprite.draw(batch, world);
        AnimatedBox2DSprite.draw(batch, world);
        batch.end();

        // Iterate destroy list
        if(removables.size() > 0) {
            game.getLogger().info("Destroying " + removables.size() + " objects from the world.");
            for (Iterator<GameObject> itr = removables.iterator(); itr.hasNext(); ) {
                GameObject go = itr.next();
                if(go instanceof ProjectileObject) {
                    ((ProjectileObject) go).getShooter().decProjectileCount(1);
                    projectiles.remove(go);
                } else {
                    removeFromCount(go);
                }
                if(go.destroy()) {
                    itr.remove();
                }
            }
        }

        // Iterate projectiles
        for(Iterator<ProjectileObject> itr = projectiles.iterator(); itr.hasNext(); ) {
            ProjectileObject projectileObject = itr.next();

            projectileObject.update(delta);

            if(!projectileObject.isActive()) {
                if(projectileObject.getShooter() != null)
                    projectileObject.getShooter().decProjectileCount(1);
                projectileObject.destroy();
                itr.remove();
            }
        }

        // Check debug button.
        if(player != null)
            setIsDebugView(player.getPlayerInputListener().F1);

        // Finally, we're going to draw the debugs
        if(isDebugView && LeadActor != null) {
            box2DCam.position.set(
                    LeadActor.getBodyX(),
                    LeadActor.getBodyY(),
                    0
            );
            box2DCam.update();
            debugRenderer.render(world, box2DCam.combined);

            // Notice how we're still projecting to the hud cam!
            Runtime runtime = Runtime.getRuntime();
            batch.setProjectionMatrix(hud_cam.combined);
            batch.begin();
            font.draw(batch, "Debug Mode", 3, 475);
            font.draw(batch, "Frames: " + Gdx.graphics.getFramesPerSecond(), 3, 450);
            font.draw(batch, "Current body count: " + world.getBodyCount(), 3, 425);
            font.draw(batch, "Memory Usage: " + ((runtime.totalMemory() - runtime.freeMemory()) / mb) + " mb", 3, 400);
            font.draw(batch, "Current ship velocity: " + player.getCurVelocity().x + " " + player.getCurVelocity().y + " / " + player.getMaxVelocity().x + " " + player.getMaxVelocity().y + " isBoosting: " + player.isBoosting, 3, 375);
            font.draw(batch, "Current Health: " + player.getHealth() + " Total Health: " + player.getMaxHealth() + " Parts Equipped Count: " + player.getProfile().getEquippedPartCount(), 3, 350);
            font.draw(batch, "Mouse X/Y: " + mouseLoc.x + " " + mouseLoc.y, 3, 325);
            batch.end();
        }

        MainStage.draw();
        HudStage.draw();

        // Lastly, Step the world's physics then allow the subclass to override!
        world.step(Globals.WorldStep, 8, 3);
    }

    private void setMousePixmap() {
        Pixmap pm = new Pixmap(Gdx.files.internal("Sprites/mouseSprite.png"));
        Gdx.input.setCursorImage(pm, 0, 0);
        pm.dispose();
    }

    public SlothShip getPlayer() {
        return player;
    }

    public void setPlayer(SlothShip player) {
        this.player = player;
    }

    public void addPlayerHUD() {
        HudStage.addActor(new PlayerHUD(player, chandle, HudStage));
    }

    public void addSystems() {
        HudStage.addActor(new PlayerRadar(player, this));
    }

    public void addProjectile(ProjectileObject object) {
        if(!projectiles.contains(object)) {
            projectiles.add(object);
        }
    }

    public boolean isQueuedForDestroy(GameObject gameObject) {
        return removables.contains(gameObject);
    }

    public void queueDestroyObject(GameObject gameObject) {
        if(!removables.contains(gameObject))
            removables.add(gameObject);
    }

    private void adjustLimits() {
        worldlyObjects.put("Asteroid", new AtomicInteger(100));
        worldlyObjects.put("Asteroid_c", new AtomicInteger(0));
        worldlyObjects.put("AsteroidBomb", new AtomicInteger(10 * levelDifficulty.getLevel()));
        worldlyObjects.put("AsteroidBomb_c", new AtomicInteger(0));
        worldlyObjects.put("RandomOre", new AtomicInteger(50));
        worldlyObjects.put("RandomOre_c", new AtomicInteger(0));
    }

    public boolean checkPopulationLimit(GameObject obj) {
        String lbl = obj.ObjLabel;
        return worldlyObjects.containsKey(lbl) && worldlyObjects.get(lbl + "_c").get() < worldlyObjects.get(lbl).get();
    }

    public boolean checkPopulationLimit(String lbl) {
        return worldlyObjects.containsKey(lbl) && worldlyObjects.get(lbl + "_c").get() < worldlyObjects.get(lbl).get();
    }

    public void addToCount(GameObject obj) {
        String lbl = obj.ObjLabel;
        worldlyObjects.get(lbl + "_c").incrementAndGet();
    }

    public void addToCount(String lbl) {
        worldlyObjects.get(lbl + "_c").incrementAndGet();
    }

    public void removeFromCount(GameObject obj) {
        String lbl = obj.ObjLabel;
        worldlyObjects.get(lbl + "_c").decrementAndGet();
    }

    public void removeFromCount(String lbl) {
        worldlyObjects.get(lbl + "_c").decrementAndGet();
    }

    public void spawnPlayer() {
        player = new SlothShip(game, chandle, MainStage, world);
        player.setLevel(this);
        MainStage.addActor(player);
        MainStage.setKeyboardFocus(player);
        setLeadActor(player, true);
        Gdx.input.setInputProcessor(player.getPlayerInputListener());
    }

    public void spawnAsteroid() {
        createAsteroid("Asteroid");
    }

    public void spawnAsteroids(int amount) {
        for(int i = 0; i < amount; i++) {
            createAsteroid("Asteroid");
        }
    }

    public void spawnAsteroidBomb() {
        createAsteroid("AsteroidBomb");
    }

    public void spawnAsteroidBombs(int amount) {
        for(int i = 0; i < amount; i++) {
            createAsteroid("AsteroidBomb");
        }
    }

    public void createAsteroid(String asteroid_type) {
        if(checkPopulationLimit(asteroid_type)) {
            if(asteroid_type.contains("Asteroid")) {
                Asteroid asteroid = new Asteroid(game, chandle, MainStage, world, 100, 10000);
                asteroid.setLevel(this);
                MainStage.addActor(asteroid);
                addToCount(asteroid);
            } else {
                AsteroidBomb asteroidBomb = new AsteroidBomb(game, chandle, MainStage, world, getPlayer());
                asteroidBomb.setLevel(this);
                MainStage.addActor(asteroidBomb);
                addToCount(asteroidBomb);
            }
        }
    }

    public void spawnOre(GameObject gameObject) {
        if(checkPopulationLimit("RandomOre")) {
            Ore item = new Ore(game, chandle, MainStage, world);
            item.setLevel(gameObject.level);
            item.setContainer(gameObject);
            addToCount(item);
        }
    }

    public void writeTextToScreen(String text) {

    }

    public Logger getLogger() {
        return game.getLogger();
    }

}