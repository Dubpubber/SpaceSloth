package com.loneboat.spacesloth.main.game.systems;

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
    private Image cover;

    private Color coverColor;

    private int RadarRange = 40;
    private float RadarScanSpeed = 3.5f;
    private float degrees = 0;

    private int rotationCount = 0;

    private HashMap<String, Integer> rship;
    private ArrayList<BlipProfile> bps;

    public PlayerRadar(SlothShip player, GameLevel level) {
        this.player = player;
        this.level = level;
        this.hstage = level.HudStage;
        this.sr = new ShapeRenderer();
        coverColor = Color.GREEN;

        // Add the ship radar coordinates //
        rship = new HashMap<>();
        rship.put("left_x", 555);
        rship.put("left_y", 45);

        rship.put("top_x", 560);
        rship.put("top_y", 60);

        rship.put("right_x", 565);
        rship.put("right_y", 45);

        rship.put("center_x", (555 + 560 + 565) / 3);
        rship.put("center_y", (45 + 60 + 45) / 3);

        bps = new ArrayList<>();

        createGUIRadar();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(rotationCount == 1) {
            rotationCount = 0;
            coverColor.a = 1;
            updateRadar();
        } else {
            coverColor.a -= RadarScanSpeed / 200.0f;
        }

        if(degrees < 360) {
            radarLine.setRotation(degrees);
        } else {
            degrees = 0;
            rotationCount++;
        }
        degrees += RadarScanSpeed;
        batch.end();
        // Begin Drawing //
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

        batch.begin();
        batch.draw(backdrop, 510, 5, 100, 100);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.setProjectionMatrix(level.HudStage.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor(0, 1, 0, coverColor.a);
        sr.circle(rship.get("center_x"), rship.get("center_y") + 5, 50);

        // sr.triangle(10, 10, 30, 50, 50, 10);
        sr.setColor(1, 1, 1, coverColor.a);
        sr.triangle(
                555, 45,
                560, 60,
                565, 45
        );

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
        sr.circle(rship.get("center_x"), rship.get("center_y") + 5, 4);

        sr.rect(radarLine.getX(), radarLine.getY() + 5,
                radarLine.getOriginX(), radarLine.getOriginY(),
                radarLine.getWidth(), radarLine.getHeight(),
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

        Pixmap px = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        px.setColor(0, 0.5f, 0, 1);
        px.fill();
        Texture pxRadarCover = new Texture(px);

        skin.add("cover", pxRadarCover);

        px = new Pixmap(120, 120, Pixmap.Format.RGBA8888);
        px.setColor(Color.LIGHT_GRAY);
        px.fill();
        Texture pxRadarBackground = new Texture(px);

        skin.add("background", pxRadarBackground);

        px = new Pixmap(10, 45, Pixmap.Format.RGBA8888);
        px.setColor(Color.GREEN);
        px.fill();
        Texture pxRadarLine = new Texture(px);

        skin.add("radarline", pxRadarLine);

        Image radarBackground = new Image(skin.getDrawable("background"));
        radarBackground.setSize(120, 120);
        radarBackground.setPosition(
                500,
                -5
        );
        this.background = radarBackground;

        backdrop = ContentHandler.manager.get("Sprites/radar_background.png", Texture.class);

        Image radarCover = new Image(skin.getDrawable("cover"));
        radarCover.setSize(backdrop.getWidth(), backdrop.getHeight());
        radarCover.setPosition(
                510,
                5
        );
        this.cover = radarCover;

        Image radarLine = new Image(skin.getDrawable("radarline"));
        radarLine.setSize(2, 45);
        radarLine.setPosition(
                rship.get("center_x") - 1,
                rship.get("center_y")
        );
        radarLine.setOrigin(radarLine.getWidth() / 2,  0);
        this.radarLine = radarLine;

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

}