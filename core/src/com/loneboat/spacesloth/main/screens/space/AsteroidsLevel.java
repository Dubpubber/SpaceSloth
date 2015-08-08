package com.loneboat.spacesloth.main.screens.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
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

        Gdx.input.setInputProcessor(MainStage);

        player = new SlothShip(game, handler, MainStage, world);
        MainStage.addActor(player);
        MainStage.setKeyboardFocus(player);
        setStaticBackground(chandle.getManager().get("Backgrounds/spacebackground_1.jpg", Texture.class));
        setLeadActor(player, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        MainStage.act(Gdx.graphics.getDeltaTime());
        MainStage.draw();
    }
}
