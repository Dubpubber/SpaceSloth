package com.loneboat.spacesloth.main.game.worldobjects.ores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

/**
 * com.loneboat.spacesloth.main.game.worldobjects.ores
 * Created by Dubpub on 9/14/2015.
 */
public class Ore extends GameObject {

    private GameObject container;
    private OreType type;

    public enum OreType {
        ROCK(25), GOLD(5), PLATINUM(1);

        private final int weight;

        OreType(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return this.weight;
        }

        static int addWeights() {
            int r = 0;
            for (OreType type : values()) {
                r += type.getWeight();
            }
            return r;
        }

        public static OreType pickOre() {
            int randValue = MathUtils.random(addWeights());
            int totalSum = 0;
            for (OreType type : OreType.values()) {
                if ((randValue > totalSum) && randValue <= (totalSum + type.getWeight())) {
                    return type;
                }
                totalSum += type.getWeight();
            }
            return OreType.ROCK;
        }
    }

    public Ore(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world) {
        super(game, chandle, active_stage, world, "RandomOre");
    }

    public Ore(SpaceSloth game, ContentHandler chandle, Stage active_stage, World world, OreType oreType) {
        super(game, chandle, active_stage, world, oreType.name() + "Ore");
    }

    public void setContainer(GameObject gameObject) {
        this.container = gameObject;
        type = OreType.pickOre();
        createBody();
    }

    public GameObject getContainer() {
        return container;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private void createBody() {
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Globals.PixelsPerMetre);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Body body = world.createBody(bodyDef);
        Fixture oreFixture = body.createFixture(fixtureDef);

        // Set the fields of GameObject
        setBody(body);
        body.setUserData(this);
        fixture = oreFixture;

        updateTexture();
    }

    private void updateTexture() {
        Texture oreTypeOfTexture = null;

        switch(type) {
            case ROCK:
                if(MathUtils.randomBoolean())
                    oreTypeOfTexture = chandle.getManager().get("Sprites/Ore_1.png", Texture.class);
                else
                    oreTypeOfTexture = chandle.getManager().get("Sprites/Ore_2.png", Texture.class);
                break;
            case GOLD:
                oreTypeOfTexture = chandle.getManager().get("Sprites/Ore_4.png", Texture.class);
                break;
            case PLATINUM:
                oreTypeOfTexture = chandle.getManager().get("Sprites/Ore_3.png", Texture.class);
                break;
        }

        Box2DSprite sprite = this.createNewBox2dObject("oreTexture", oreTypeOfTexture);
        fixture.setUserData(sprite);
    }

}
