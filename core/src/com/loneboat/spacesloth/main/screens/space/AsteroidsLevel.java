package com.loneboat.spacesloth.main.screens.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.game.handlers.AsteroidsLevelListener;
import com.loneboat.spacesloth.main.game.worldobjects.Asteroid;
import com.loneboat.spacesloth.main.screens.GameScreen;

/**
 * com.loneboat.spacesloth.main.screens.space
 * Created by Dubpub on 8/7/2015.
 *
 * Just your basic asteroids level.
 */
public class AsteroidsLevel extends GameScreen {

    private int difficulty = 0;

    private SlothShip player;

    public AsteroidsLevel(SpaceSloth game, ContentHandler handler, int difficulty) {
        super(game, handler);
        this.difficulty = difficulty;
        game.getLogger().info("Switched to an AsteroidsLevel");

        // Create Player
        player = new SlothShip(game, handler, MainStage, world);
        player.setCurrentScreen(this);
        MainStage.addActor(player);
        MainStage.setKeyboardFocus(player);
        setLeadActor(player, true);
        Gdx.input.setInputProcessor(player.getPlayerInputListener());

        // Create debug asteroids
        for(int i = 0; i < 75; i++) {
            Asteroid tempasteroid = new Asteroid(game, chandle, MainStage, world, 100);
            tempasteroid.setCurrentScreen(this);
            MainStage.addActor(tempasteroid);
        }

        // Set Contact Listener.
        world.setContactListener(new AsteroidsLevelListener(game));

        // Apply the mouse image.
        Pixmap pm = new Pixmap(Gdx.files.internal("Sprites/mouseSprite.png"));
        Gdx.input.setCursorImage(pm, 0, 0);
        pm.dispose();

        setIsDebugView(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

/*        if(getRoundedTimer() % 3 == 0) {
            // Activate random event.
            AsteroidBomb bomb = new AsteroidBomb(game, chandle, MainStage, world, player);
            bomb.setCurrentScreen(this);
            MainStage.addActor(bomb);
        }*/
    }
}
