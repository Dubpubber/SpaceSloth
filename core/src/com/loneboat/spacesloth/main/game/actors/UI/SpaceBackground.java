package com.loneboat.spacesloth.main.game.actors.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.screens.GameLevel;

/**
 * com.loneboat.spacesloth.main.game.actors.UI
 * Created by Dubpub on 12/1/2015.
 *
 * - Insert explanation here -
 *
 */
public class SpaceBackground extends Actor {

    private GameLevel level;
    private SlothShip player;

    private ShapeRenderer debugRenderer;

    public SpaceBackground(GameLevel level, SlothShip player) {
        this.level = level;
        this.player = player;

        this.debugRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.box(player.getBodyX(), player.getBodyY(), 0, 250, 250, 0);
        debugRenderer.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
