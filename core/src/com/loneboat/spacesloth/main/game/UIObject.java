package com.loneboat.spacesloth.main.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * com.loneboat.spacesloth.main.game
 * Created by Dubpub on 8/21/2015.
 *
 * Basically a smaller, less memory intensive subclass of actor. It's purpose being to simple display things like text, images, sprites and more on screen.
 *  (Separate from Box2D)
 */
public class UIObject extends Actor {

    // Enum declaring the UIObject's Type
    private enum UIType {
        TEXT
    }

    // UIType variable
    public UIType ObjectType;

    // Global UIObject variables
    private float x;
    private float y;

    // Text UIObject
    private String text;
    private BitmapFont font;

    /**
     * Creates a UIObject of UIType - text.
     * @param text - String to be displayed on screen.
     * @param x - x position of actor.
     * @param y - y position of actor.
     */
    public UIObject(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;

        // Create a new bitmap font object.
        font = new BitmapFont();

        // Set the UIObjects Type.
        ObjectType = UIType.TEXT;
    }

    /**
     * Creates a UIObject of UIType - text.
     * @param text - String to be displayed on screen.
     * @param x - x position of actor.
     * @param y - y position of actor.
     * @param font - allows input of font.
     */
    public UIObject(String text, float x, float y, BitmapFont font) {
        this.text = text;
        this.x = x;
        this.y = y;

        // Set the font.
        this.font = font;

        // Set the UIObjects Type.
        ObjectType = UIType.TEXT;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        switch(ObjectType) {
            case TEXT:
                // Make sure the font doesn't equal null.
                if(font != null)
                    font.draw(batch, text, x, y);
                break;
        }
    }

}
