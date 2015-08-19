package com.loneboat.spacesloth.main.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;

/**
 * com.loneboat.spacesloth.main.game.actors
 * Created by Dubpub on 8/17/2015.
 */
public class MouseActor extends GameObject {

    /**
     * Creates a new game object that is animated but not a box2d object.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public MouseActor(SpaceSloth game, ContentHandler chandle, Stage active_stage) {
        super(game, chandle, active_stage, "MouseActor");
        createBasicSprite("Sprites/mouseSprite.png");
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }


    /**
     * @param batch       - the sprite batch.
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        game.getLogger().info("Loc: " + sprite.getX() + " " + sprite.getY());
        sprite.setPosition(Gdx.input.getX(), ContentHandler.GAMEHEIGHT - Gdx.input.getY());
        batch.draw(
                sprite.getTexture(),
                sprite.getX() - sprite.getOriginX(),
                sprite.getY() - sprite.getOriginY(),
                sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),
                sprite.getHeight(),
                1.0f, 1.0f,
                1.0f, sprite.getRegionX(), sprite.getRegionY(),
                sprite.getRegionWidth(), sprite.getRegionHeight(),
                false, false
        );
    }
}
