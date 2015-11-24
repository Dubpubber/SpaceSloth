package com.loneboat.spacesloth.main.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.loneboat.spacesloth.main.Globals;
import com.loneboat.spacesloth.main.game.GameObject;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.util.ScreenUtil;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * com.loneboat.spacesloth.main.game.systems
 * Created by Dubpub on 11/22/2015.
 */
public class PlayerRadar extends Actor {

    private SlothShip player;
    private Stage hstage;
    private Stage mstage;

    private Image radarLine;

    private int RadarRange = 10;
    private float RadarScanSpeed = 3;
    private float degrees = 0;

    private ShapeRenderer sr;

    public PlayerRadar(SlothShip player, Stage mainstage, Stage hudstage) {
        this.player = player;
        this.hstage = hudstage;
        this.mstage = mainstage;
        createGUIRadar();
        new Timer().scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                createRadarGridFromScan();
            }
        }, 1, 1);
        sr = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(degrees < 360) {
            radarLine.setRotation(degrees);
        } else {
            degrees = 0;
        }
        degrees = degrees + 2;
        radarLine.draw(batch, parentAlpha);

        sr.setProjectionMatrix(hstage.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        ArrayList<GameObject> actors = ScreenUtil.getObjectsNearbyActor(player, RadarRange);
        for (GameObject obj : actors) {
            if(!(obj instanceof SlothShip)) {
                sr.line(new Vector2(
                        hstage.getViewport().getWorldWidth() / 2, hstage.getViewport().getWorldHeight() / 2),
                        obj.getPosition()
                );
            }
        }
        sr.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private void createGUIRadar() {
        Skin skin = new Skin();

        Pixmap px = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        px.setColor(0, 0, 0, 1);
        px.fill();
        Texture pxRadarBackdrop = new Texture(px);

        skin.add("backdrop", pxRadarBackdrop);

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
        radarBackground.setPosition(490, 0);
        hstage.addActor(radarBackground);

        Image radarBackdrop = new Image(skin.getDrawable("backdrop"));
        radarBackdrop.setSize(100, 100);
        radarBackdrop.setPosition(500, 7);
        hstage.addActor(radarBackdrop);

        Image radarLine = new Image(skin.getDrawable("radarline"));
        radarLine.setSize(2, 45);
        radarLine.setPosition(549, 56);
        radarLine.setOrigin(radarLine.getWidth() / 2,  0);
        this.radarLine = radarLine;

        px.dispose();
    }

    public void createRadarGridFromScan() {
        Vector2 playerVec = player.getPosition();
        // First get the nearby actors
        ArrayList<GameObject> actors = ScreenUtil.getObjectsNearbyActor(player, RadarRange);
        for (GameObject obj : actors) {
            Vector2 tempVec = obj.getPosition();
            float angle = MathUtils.atan2(tempVec.y, tempVec.x) - MathUtils.atan2(playerVec.y, playerVec.x);
            System.out.println("Angle of " + obj.ObjLabel + ": " + ScreenUtil.calculateNormalAngle(angle));
        }
    }

}