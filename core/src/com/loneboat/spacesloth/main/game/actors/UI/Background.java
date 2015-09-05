package com.loneboat.spacesloth.main.game.actors.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.loneboat.spacesloth.main.game.actors.UI
 * Created by Dubpub on 9/4/2015.
 */
public class Background extends Actor {
    private Texture background;

    public Background(Texture background) {
        this.background = background;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(background, getX(), getY(), getWidth(), getHeight());
    }

}
