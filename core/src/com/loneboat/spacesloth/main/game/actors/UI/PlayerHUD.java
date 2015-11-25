package com.loneboat.spacesloth.main.game.actors.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.screens.GameScreen;

/**
 * com.loneboat.spacesloth.main.game.actors.UI
 * Created by Dubpub on 8/27/2015.
 */
public class PlayerHUD extends Actor {

    private SlothShip player;
    private ContentHandler chandle;
    private Stage HudStage;
    private Pixmap hudpix;
    private OrthographicCamera hudcam;
    private Image hudImage;
    private ProgressBar healthbar;
    private ProgressBar boostbar;

    public PlayerHUD(SlothShip player, ContentHandler chandle, Stage HudStage) {
        this.player = player;
        this.chandle = chandle;
        this.HudStage = HudStage;
        hudcam = chandle.getHudCamera();
        createControlPanel();
        createBars();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        hudImage.draw(batch, parentAlpha);

        /* Health bar act and draw */
        healthbar.draw(batch, parentAlpha);
        healthbar.act(parentAlpha);

        /* Boost bar act and draw */
        boostbar.draw(batch, parentAlpha);
        boostbar.act(parentAlpha);

        /* Update the HUD */
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
        healthbar.setPosition(5, 5);

        ProgressBar.ProgressBarStyle boostbarstyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("background"), skin.newDrawable("boost"));
        boostbarstyle.knobBefore = boostbarstyle.knob;
        boostbar = new ProgressBar(0, player.getBoostCap(), 1.0f, true, boostbarstyle);
        boostbar.setAnimateDuration(0.5f);
        boostbar.setValue(player.getCurBoost());
        boostbar.setSize(57, 56);
        boostbar.setPosition(65, 5);

        px.dispose();

    }

    public void createControlPanel() {
        hudpix = new Pixmap(Gdx.graphics.getWidth(), 66, Pixmap.Format.RGBA8888);
        hudpix.setColor(Color.LIGHT_GRAY);
        hudpix.fill();
        hudImage = new Image(new TextureRegion(new Texture(hudpix)));
        hudImage.setPosition(0, 0);
        hudpix.dispose();
    }

    public void updateHUD() {
        healthbar.setValue(player.getHealth());
        boostbar.setValue(player.getCurBoost());
    }

}
