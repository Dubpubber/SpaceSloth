package com.loneboat.spacesloth.main.game.actors.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;

/**
 * com.loneboat.spacesloth.main.game.actors.UI
 * Created by Dubpub on 8/27/2015.
 */
public class PlayerHUD extends Actor {

    private SlothShip player;
    private ContentHandler chandle;
    private Texture hudsprite;
    private OrthographicCamera hudcam;

    public PlayerHUD(SlothShip player, ContentHandler chandle) {
        this.player = player;
        this.chandle = chandle;
        hudsprite = chandle.getManager().get("Sprites/HUDSprite.png", Texture.class);
        hudcam = chandle.getHudCamera();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(hudsprite, 10, 10);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
