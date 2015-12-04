package com.loneboat.spacesloth.main.game.systems.radar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.screens.GameLevel;
import com.loneboat.spacesloth.main.util.ScreenUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * com.loneboat.spacesloth.main.game.systems
 * Created by Dubpub on 11/22/2015.
 */
public class PlayerRadar extends Actor {

    private SlothShip player;
    private GameLevel level;
    private Stage hstage;

    private ShapeRenderer sr;

    private Image radarLine;
    private Image background;
    private Texture backdrop;
    private Texture radarShip;

    private Color coverColor;

    private int RadarRange = 65;
    private float RadarScanSpeed = 3.5f;
    private float degrees = 0;

    private float RadarPositionX = 1115;
    private float RadarPositionY = 5;

    private float RadarSizeX = 200;
    private float RadarSizeY = 200;

    private int rotationCount = 0;

    private HashMap<String, Float> rship;
    private ArrayList<BlipProfile> bps;

    public PlayerRadar(SlothShip player, GameLevel level) {
        this.player = player;
        this.level = level;
        this.hstage = level.HudStage;
        this.sr = new ShapeRenderer();
        coverColor = new Color(0, 1, 0, 1);

        // Add the ship radar coordinates //
        rship = new HashMap<>();
        rship.put("top_x", (RadarSizeX / 2) + RadarPositionX);
        rship.put("top_y", (RadarSizeY / 2) + RadarPositionY);

        rship.put("left_x", rship.get("top_x") - 5);
        rship.put("left_y", rship.get("top_y") - 5);

        rship.put("right_x", rship.get("top_x") + 5);
        rship.put("right_y", rship.get("top_y") - 5);

        rship.put("center_x", (rship.get("left_x") + rship.get("top_x") + rship.get("right_x")) / 3);
        rship.put("center_y", (rship.get("left_y") + rship.get("top_y") + rship.get("right_y")) / 3);

        level.getLogger().info("center x/y: " + rship.get("center_x") + " " + rship.get("center_y"));

        bps = new ArrayList<>();

        createGUIRadar();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        // Update the radar and increase the cover color A
        if(rotationCount == 1) {
            rotationCount = 0;
            coverColor.a = 1;
            updateRadar();
        } else {
            coverColor.a -= RadarScanSpeed / 200.0f;
        }

        // Rotate the radar bar.
        if(degrees < 360) {
            radarLine.setRotation(degrees);
        } else {
            degrees = 0;
            rotationCount++;
        }
        degrees += RadarScanSpeed;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.setProjectionMatrix(level.HudStage.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        // RADAR BACKGROUND //
        sr.setColor(Color.LIGHT_GRAY);
        sr.rect(background.getX(), background.getY(),
                background.getOriginX(), background.getOriginY(),
                background.getWidth(), background.getHeight(),
                1, 1, 0
        );
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        batch.draw(backdrop, RadarPositionX, RadarPositionY, RadarSizeX, RadarSizeY);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.setProjectionMatrix(level.HudStage.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0, 1, 0, coverColor.a);
        sr.circle(rship.get("center_x"), rship.get("center_y") + 4, RadarSizeX / 2);
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        batch.draw(
                radarShip,
                (rship.get("right_x") - (radarShip.getWidth() / 2) - 3.15f), rship.get("center_y"),
                6, 6,
                12, 12,
                1, 1,
                ScreenUtil.calculateNormalAngle(player.getBody().getAngle()),
                0, 0,
                radarShip.getWidth(), radarShip.getHeight(),
                false, false
        );
        batch.end();


        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        for(BlipProfile bp : bps) {
            sr.setColor(bp.getColor().r, bp.getColor().g, bp.getColor().b, coverColor.a);
            sr.rect(bp.getX(), bp.getY(),
                    bp.getX(), bp.getY(),
                    bp.getWidth(), bp.getHeight(),
                    1, 1, 0
            );
        }

        // RADAR LINE //
        sr.setColor(0, 1, 0, 1);
        sr.circle(rship.get("center_x"), rship.get("center_y") + 3, 4);

        sr.rect(radarLine.getX(), radarLine.getY() + 3,
                radarLine.getOriginX(), radarLine.getOriginY(),
                radarLine.getWidth(), (RadarSizeY / 2) - 10,
                1, 1, degrees
        );
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private void createGUIRadar() {
        Skin skin = new Skin();

        Pixmap px = new Pixmap((int) RadarSizeX, (int) RadarPositionY, Pixmap.Format.RGBA8888);
        px.setColor(0, 0.5f, 0, 1);
        px.fill();
        Texture pxRadarCover = new Texture(px);

        skin.add("cover", pxRadarCover);

        px = new Pixmap((int) RadarSizeX + 20, (int) RadarPositionY + 20, Pixmap.Format.RGBA8888);
        px.setColor(Color.LIGHT_GRAY);
        px.fill();
        Texture pxRadarBackground = new Texture(px);

        skin.add("background", pxRadarBackground);

        px = new Pixmap(10, (int) (RadarSizeY - 10), Pixmap.Format.RGBA8888);
        px.setColor(Color.GREEN);
        px.fill();
        Texture pxRadarLine = new Texture(px);

        skin.add("radarline", pxRadarLine);

        Image radarBackground = new Image(skin.getDrawable("background"));
        radarBackground.setSize(RadarSizeX + 20, RadarSizeY + 20);
        radarBackground.setPosition(
                RadarPositionX - 10,
                -5
        );
        this.background = radarBackground;

        Texture bk = ContentHandler.manager.get("Sprites/radar_background.png", Texture.class);
        bk.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backdrop = bk;

        Image radarLine = new Image(skin.getDrawable("radarline"));
        radarLine.setSize(2, 45);
        radarLine.setPosition(
                rship.get("center_x") - 1,
                rship.get("center_y")
        );
        radarLine.setOrigin(radarLine.getWidth() / 2,  0);
        this.radarLine = radarLine;

        radarShip = ContentHandler.manager.get("Sprites/radar_ship.png", Texture.class);

        px.dispose();
    }


    public void updateRadar() {
        bps.clear();
        ArrayList<GameObject> list = ScreenUtil.getObjectsNearbyActor(player, RadarRange);
        list.stream().filter(obj -> obj.bp != null).forEach(obj -> {
            // Now that we got the local objects of interest, process them in relation to the ship's blip so the shape renderer can render them.
            // First, get the angle.
            float angle = ScreenUtil.getAngleFromObject(player, obj);
            // Second, get the distance.
            float distance = player.getPosition().dst(obj.getPosition());
            // ((cos(angle) * distance) + x) = new x
            // ((sin(angle) * distance) + y) = new y
            float x0 = (float) ((Math.cos(angle) * distance) + rship.get("center_x"));
            float y0 = (float) ((Math.sin(angle) * distance) + rship.get("center_y"));
            obj.bp.updateLocation(x0, y0);
            bps.add(obj.bp);
        });
    }

    public int getBlips() {
        return bps.size();
    }

}