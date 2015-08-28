package com.loneboat.spacesloth.main.game.actors.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;

/**
 * com.loneboat.spacesloth.main.game.actors.UI
 * Created by Dubpub on 8/27/2015.
 */
public class PlayerHUD extends Actor {

    private SlothShip player;
    private ContentHandler chandle;
    private Stage HudStage;
    private Texture hudsprite;
    private OrthographicCamera hudcam;
    private ProgressBar healthbar;
    private ProgressBar boostbar;

    public PlayerHUD(SlothShip player, ContentHandler chandle, Stage HudStage) {
        this.player = player;
        this.chandle = chandle;
        this.HudStage = HudStage;
        hudsprite = chandle.getManager().get("Sprites/HUDSprite.png", Texture.class);
        hudcam = chandle.getHudCamera();
        createBars();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(hudsprite, (ContentHandler.GAMEWIDTH - hudsprite.getWidth()) / 2, 10);
        updateHUD();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void createBars() {
        // First create the healthbar.
        // Create a pixmap for the filler of the healthbar.
        Skin skin = new Skin();

        Pixmap px = new Pixmap(57, 1, Pixmap.Format.RGBA8888);
        px.setColor(139, 0, 0, 1);
        px.fill();
        Texture pxTextureHealthBar = new Texture(px);

        skin.add("health", pxTextureHealthBar);

        px.setColor(0, 0, 83, 1);
        px.fill();
        Texture pxTextureBoostBar = new Texture(px);

        skin.add("boost", pxTextureBoostBar);

        px.setColor(189, 190, 205, 0.5f);
        px.fill();
        Texture pxTextureBackground = new Texture(px);

        skin.add("background", pxTextureBackground);

        ProgressBar.ProgressBarStyle healhbarstyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("background"), skin.newDrawable("health"));
        healhbarstyle.knobBefore = healhbarstyle.knob;
        healthbar = new ProgressBar(0, player.getMaxHealth(), 1.0f, true, healhbarstyle);
        healthbar.setAnimateDuration(1.0f);
        healthbar.setValue(player.getHealth());
        healthbar.setSize(57, 56);
        healthbar.setPosition(199, 14);

        ProgressBar.ProgressBarStyle boostbarstyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("background"), skin.newDrawable("boost"));
        boostbarstyle.knobBefore = boostbarstyle.knob;
        boostbar = new ProgressBar(0, player.getBoostCap(), 1.0f, true, boostbarstyle);
        boostbar.setAnimateDuration(1.0f);
        boostbar.setValue(player.getCurBoost());
        boostbar.setSize(57, 56);
        boostbar.setPosition(384, 14);

        px.dispose();

        HudStage.addActor(healthbar);
        HudStage.addActor(boostbar);

    }

    public void updateHUD() {
        healthbar.setValue(player.getHealth());
        boostbar.setValue(player.getCurBoost());
    }

}
