package com.loneboat.spacesloth.main.screens.utilscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.screens.GameScreen;
import com.loneboat.spacesloth.main.screens.space.AsteroidsLevel;

/**
 * com.loneboat.spacesloth.main.screens.utilscreens
 * Created by Dubpub on 8/7/2015.
 */
public class LoadingScreen extends GameScreen {
    private boolean finished;
    private BitmapFont titleScreenFont;
    private BitmapFont bodyFont;
    private GlyphLayout glyph;

    private Texture loadingScreenBackground;

    private Image DL_Texture;

    private int assetCount;
    /**
     * Constructor.
     *
     * @param game    - SpaceSloth class
     * @param chandle - Content handler object.
     */
    public LoadingScreen(SpaceSloth game, ContentHandler chandle) {
        super(game, chandle);
        font = new BitmapFont();

        Gdx.input.setInputProcessor(MainStage);

        game.getLogger().info("Switched to LoadingScreen.");
        titleScreenFont = ContentHandler.generateAllerFont(24);
        bodyFont = ContentHandler.generateAllerFont(14);
        glyph = new GlyphLayout();
        glyph.setText(titleScreenFont, "Loading parts...");

        // Add two cause I know them fonts won't count.
        assetCount = chandle.getManager().getQueuedAssets() + 2;

        loadingScreenBackground = new Texture(Gdx.files.internal("Backgrounds/SpaceSloth_TitleScreen_1.png"));
        loadingScreenBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture dl = new Texture(Gdx.files.internal("Textures/DL_text.png"));
        dl.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        DL_Texture = new Image(dl);
        DL_Texture.addAction(Actions.alpha(0));
        DL_Texture.act(0);
        DL_Texture.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(2), Actions.fadeOut(2))));
        DL_Texture.setSize(400, 25);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(15/255f, 15/255f, 15/225f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font.setColor(Color.valueOf("e3e3e3"));
        font.getData().setScale(1.0f);
        glyph.setText(bodyFont, "Loading! " + (chandle.getProgress()) + "%");
        // Update the manager
        if(chandle.updateManager()) {
            if(!finished) {
                game.getLogger().info("Finished loading " + chandle.getManager().getLoadedAssets() + " assets.");
                finished = true;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                game.setCurrentScreen(new AsteroidsLevel(game, chandle));
            }
        }
        batch.setProjectionMatrix(hud_cam.combined);
        batch.begin();

        // Images and Textures
        // Re-draw the color so this stuff isn't blinking.
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
        batch.draw(loadingScreenBackground, 0, 0, ContentHandler.GAMEWIDTH, ContentHandler.GAMEHEIGHT);

        // Font stuffs
        bodyFont.draw(batch, glyph, (ContentHandler.GAMEWIDTH - glyph.width) / 2, ContentHandler.GAMEHEIGHT / 2);
        font.setColor(Color.valueOf("fafa00"));
        glyph.setText(titleScreenFont, "Assets to be loaded: " + assetCount);
        titleScreenFont.draw(batch, glyph, (ContentHandler.GAMEWIDTH - glyph.width) / 2, (ContentHandler.GAMEHEIGHT / 2) + 25);

        if(finished) {
            DL_Texture.act(Gdx.graphics.getDeltaTime());
            DL_Texture.draw(batch, 1);
            // Update position to screen just incase.
            DL_Texture.setPosition((ContentHandler.GAMEWIDTH - DL_Texture.getWidth()) / 2, (ContentHandler.GAMEHEIGHT / 2) - 75);
        }

        batch.end();
    }

}
