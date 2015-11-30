package com.loneboat.spacesloth.main.screens.utilscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(222/255f, 208/255f, 121/225f, 1f);
        font.setColor(Color.valueOf("e3e3e3"));
        font.getData().setScale(1.0f);
        glyph.setText(bodyFont, "Loading " + chandle.getManager().getQueuedAssets() + " assets... " + (chandle.getProgress()) + "%");
        // Update the manager
        if(chandle.updateManager()) {
            if(!finished) {
                game.getLogger().info("Finished loading " + chandle.getManager().getLoadedAssets() + " assets.");
                finished = true;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                game.setCurrentScreen(new AsteroidsLevel(game, chandle));
            }
            glyph.setText(bodyFont, "Finished loading all game assets! press any button to continue.");
        }
        batch.setProjectionMatrix(hud_cam.combined);
        batch.begin();
        batch.setColor(Color.BLACK);
        bodyFont.draw(batch, glyph, (ContentHandler.GAMEWIDTH - glyph.width) / 2, ContentHandler.GAMEHEIGHT / 2);
        font.setColor(Color.valueOf("fafa00"));
        glyph.setText(titleScreenFont, "SpaceSloth");
        titleScreenFont.draw(batch, glyph, (ContentHandler.GAMEWIDTH - glyph.width) / 2, 350);
        batch.end();
    }

}
