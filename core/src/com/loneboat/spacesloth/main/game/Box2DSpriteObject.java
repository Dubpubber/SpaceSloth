package com.loneboat.spacesloth.main.game;

import com.badlogic.gdx.graphics.Texture;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.game
 * Created by Dubpub on 8/21/2015.
 */
public class Box2DSpriteObject extends Box2DSprite {

    private GameObject object;

    public Box2DSpriteObject(Texture texture, GameObject object) {
        super(texture);
        this.object = object;
    }

    public GameObject getGameObject() {
        return object;
    }

}
