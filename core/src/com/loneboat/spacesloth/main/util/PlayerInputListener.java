package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/16/2015.
 */
public class PlayerInputListener implements InputProcessor {

    private SlothShip player;
    private SpaceSloth game;
    private ContentHandler chandle;

    public PlayerInputListener(SlothShip player, SpaceSloth game, ContentHandler chandle) {
        this.player = player;
        this.game = game;
        this.chandle = chandle;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                player.getBody().setLinearVelocity(new Vector2(0, player.getProfile().getCurrentSpeed().y));
                break;
            case Input.Keys.S:
                player.getBody().setLinearVelocity(new Vector2(0, -player.getProfile().getCurrentSpeed().y));
                break;
            case Input.Keys.A:
                player.getBody().applyForceToCenter(new Vector2(-player.getProfile().getCurrentSpeed().x, 0), true);
                break;
            case Input.Keys.D:
                player.getBody().applyForceToCenter(new Vector2(player.getProfile().getCurrentSpeed().x, 0), true);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
