package com.loneboat.spacesloth.main.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;

/**
 * com.loneboat.spacesloth.main.util
 * Created by Dubpub on 8/16/2015.
 */
public class PlayerInputListener implements InputProcessor {
    private SpaceSloth game;
    private ContentHandler chandle;

    public boolean w, s, a, d, left, right, pageup, space, button_left, shift;

    public PlayerInputListener(SpaceSloth game, ContentHandler chandle) {
        this.game = game;
        this.chandle = chandle;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                w = true;
                break;
            case Input.Keys.S:
                s = true;
                break;
            case Input.Keys.A:
                a = true;
                break;
            case Input.Keys.D:
                d = true;
                break;
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.RIGHT:
                right = true;
                break;
            case Input.Keys.PAGE_UP:
                pageup = true;
                break;
            case Input.Keys.SPACE:
                space = true;
                break;
            case Input.Keys.SHIFT_LEFT:
                shift = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                w = false;
                break;
            case Input.Keys.S:
                s = false;
                break;
            case Input.Keys.A:
                a = false;
                break;
            case Input.Keys.D:
                d = false;
                break;
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.RIGHT:
                right = false;
                break;
            case Input.Keys.PAGE_UP:
                pageup = false;
                break;
            case Input.Keys.SPACE:
                space = false;
                break;
            case Input.Keys.SHIFT_LEFT:
                shift = false;
                break;
        }
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
