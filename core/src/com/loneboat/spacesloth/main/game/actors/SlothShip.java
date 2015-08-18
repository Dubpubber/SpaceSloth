package com.loneboat.spacesloth.main.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.util.MouseUtil;
import com.loneboat.spacesloth.main.util.PlayerInputListener;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.game.actors
 * Created by Dubpub on 8/7/2015.
 */
public class SlothShip extends GameObject {

    private PlayerInputListener playerInputListener;
    private Profile profile;

    public class Profile {
        private SlothShip sloth;
        private Vector2 currentSpeed;

        public Profile(SlothShip sloth) {
            this.sloth = sloth;
        }

        public Vector2 getCurrentSpeed() {
            return currentSpeed;
        }

        public void setCurrentSpeed(Vector2 newSpeed) {
            this.currentSpeed = newSpeed;
        }

    }

    /**
     * Creates a new game object that is animated but not a box2d object.
     *
     * @param game         - Game Object.
     * @param chandle      - Content Handler.
     * @param active_stage - Stage on which the this game object will be acting on.
     */
    public SlothShip(SpaceSloth game, final ContentHandler chandle, Stage active_stage, World world) {
        super(game, chandle, active_stage, world, "Mr. Slothers");

        profile = new Profile(this);
        profile.setCurrentSpeed(new Vector2(10, 10));

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create player

        bdef.position.set(350 / Globals.PixelsPerMetre, 350 / Globals.PixelsPerMetre);
        bdef.type = BodyDef.BodyType.DynamicBody;

        shape.setAsBox(48 / Globals.PixelsPerMetre, 42 / Globals.PixelsPerMetre);
        fdef.shape = shape;

        Texture texture = chandle.getManager().get("Sprites/Ship_A1.png", Texture.class);
        Box2DSprite sprite = new Box2DSprite(texture);
        Body body = world.createBody(bdef).createFixture(fdef).getBody();
        body.setUserData(sprite);
        setBody(body);
        setBox2DSprite(sprite);

        getBody().setLinearDamping(0.1f);

        playerInputListener = new PlayerInputListener(this, game, chandle);
        setOrigin(getBodyX() / 2, getBodyY() / 2);
    }

    /**
     * @param batch       - the sprite batch.
     * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        getBody().setTransform(body.getPosition().x, body.getPosition().y, MouseUtil.getMouseAngleRelativeToScreen() / 180);
        //box2DSprite.setRotation(MouseUtil.getMouseAngleRelativeToScreen());
    }

    public Profile getProfile() {
        return profile;
    }

    public PlayerInputListener getPlayerInputListener() {
        return playerInputListener;
    }

}
