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
        glyph = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(222/255f, 208/255f, 121/225f, 1f);
        glyph.setText(font, "Loading " + chandle.getManager().getQueuedAssets() + " assets... " + (chandle.getProgress()) + "%");
        // Update the manager
        if(chandle.updateManager()) {
            if(!finished) {
                game.getLogger().info("Finished loading " + chandle.getManager().getLoadedAssets() + " assets.");
                finished = true;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                game.setCurrentScreen(new AsteroidsLevel(game, chandle));
            }
            glyph.setText(font, "Finished loading all game assets! press any button to continue.");
        }
        batch.setProjectionMatrix(hud_cam.combined);
        batch.begin();
        batch.setColor(Color.BLACK);
        font.draw(batch, glyph, (ContentHandler.GAMEWIDTH - glyph.width) / 2, ContentHandler.GAMEHEIGHT / 2);
        batch.end();
    }

}
