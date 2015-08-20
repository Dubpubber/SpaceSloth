package com.loneboat.spacesloth.main.screens.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
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
        for(int asses = 0; asses < 25; asses++) {
            Asteroid a1 = new Asteroid(game, chandle, MainStage, world);
            MainStage.addActor(a1);
        }

        Pixmap pm = new Pixmap(Gdx.files.internal("Sprites/mouseSprite.png"));
        Gdx.input.setCursorImage(pm, 0, 0);
        pm.dispose();

        setIsDebugView(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        MainStage.act(Gdx.graphics.getDeltaTime());
        MainStage.draw();
    }
}
