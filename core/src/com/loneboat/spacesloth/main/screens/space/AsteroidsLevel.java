package com.loneboat.spacesloth.main.screens.space;

import com.badlogic.gdx.graphics.Texture;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.UI.DynamicBackground;
import com.loneboat.spacesloth.main.game.handlers.AsteroidsLevelListener;
import com.loneboat.spacesloth.main.screens.GameLevel;

/**
 * com.loneboat.spacesloth.main.screens.space
 * Created by Dubpub on 8/7/2015.
 *
 * Just your basic asteroids level.
 */
public class AsteroidsLevel extends GameLevel {

    public AsteroidsLevel(SpaceSloth game, ContentHandler handler) {
        super(game, handler, DIFFICULTY.EASYMODE, 1000);
        game.getLogger().info("Switched to an AsteroidsLevel");

        // Set Contact Listener.
        world.setContactListener(new AsteroidsLevelListener(game));
        spawnAsteroids(350);

        setIsDebugView(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
