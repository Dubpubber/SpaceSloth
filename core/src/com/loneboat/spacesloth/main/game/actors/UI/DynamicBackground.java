package com.loneboat.spacesloth.main.game.actors.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.screens.GameLevel;

import java.util.ArrayList;

/**
 * com.loneboat.spacesloth.main.game.actors.UI
 * Created by Dubpub on 12/1/2015.
 *
 * - Insert explanation here -
 *
 */
public class DynamicBackground extends Actor {

    private GameLevel level;
    private SlothShip player;

    private ShapeRenderer debugRenderer;

    // Each tile size.
    private float TileSizeX = 100;
    private float TileSizeY = 100;

    // Total map size on an x -> y axis
    // For example:
    //  -- 10 would be 100
    //  -- 20 would be 400
    // Try not to set this value too high.
    // Make this value odd. It <i>can</i> be even, just shouldn't
    private int background_size = 11;

    // Map data represented as a 2D array of course!
    private Vector2[][] mapTiles;

    // Put them into an easy to calculate arraylist
    private ArrayList<Vector2> tiles;

    private Texture background;

    public DynamicBackground(GameLevel level) {
        this.level = level;
        this.debugRenderer = new ShapeRenderer();
        mapTiles = new Vector2[background_size][background_size];
        tiles = new ArrayList<>();
        background = ContentHandler.manager.get("Backgrounds/SpaceSloth_SpaceBackground_1.png", Texture.class);
        TileSizeX = background.getWidth();
        TileSizeY = background.getHeight();
        generateMap();
    }

    public void setPlayer(SlothShip player) {
        this.player = player;
    }

    /**
     * You'll probably enjoy this one future employers... and grimace - maybe your current employers will enjoy it enough to elect you director >_>
     *
     * /* @- Basic logic loop -@
     * 1. First, we're going to assume all possible fills:
     *  - Origin tile: referenced as OT; where all tiles will assume position from. On an x-y axis, this would be (0,0)
     *  - East tile(s): tile(s) directly east of the OT. Found using the formula: (OT.x + (TileSizeX * posXOffset), 0)
     *  - West tile(s): tile(s) directly west of the OT. Found using the formula: (OT.x - (TileSizeX * posXOffset), 0)
     *  - North tile(s): tile(s) directly north of the OT. Found using the formula: (OT.y + (TileSizeY * posYOffset), 0)
     *  - South tile(s): tile(s) directly south of the OT. Found using the formula: (OT.y - (TileSizeY * posYOffset), 0)
     *  - Diagonal cardinal directions are a tad more complicated but still doable. Basically, the only difference being is that both vector values must be assigned.
     *  - Northeast tiles(s): tile(s) to the right, then up (Again, we're assuming the x,y axis here).
     *    - ((OT.x + (TileSizeX * posXOffset), (OT.y + (TileSizeY * posYOffset))
     *  - Southeast tile(s): tiles(s) to the right, then down
     *    - ((OT.x + (TileSizeX * posXOffset), (OT.y - (TileSizeY * posYOffset))
     *  - Northwest tile(s): tile(s) to the left then up.
     *    - ((OT.x - (TileSizeX * posXOffset)), (OT.y + (TileSizeY * posYOffset))
     *  - Southwest tile(s): tile(s) to the left then down.
     *    - ((OT.x - (TileSizeX * posXOffset)), (OT.y - (TileSizeY * posYOffset))
     *
     * 2. So, to even start this, we need to set the origin asap.
     */
    public void generateMap() {
        int posXOffset = 1;
        int posYOffset = 1;
        int half = background_size / 2;

        mapTiles[half][half] = new Vector2(0, 0);
        Vector2 OT = mapTiles[background_size / 2][background_size / 2];

        // Populate east and west.
        for(int x = 0; x < mapTiles[half].length; x++) {
            if(x != half) {
                if (x < half) { // 0 - (half - 1), WEST TILES!
                    mapTiles[half][x] = new Vector2(OT.x - (TileSizeX * ((half + 1) - posXOffset)), 0);
                } else {
                    mapTiles[half][x] = new Vector2(OT.x + (TileSizeX * (posXOffset - half)), 0);
                }
                posXOffset++;
            }
        }

        // Populate north and south
        for(int x = 0; x < mapTiles[half].length; x++) {
            Vector2 OTt = mapTiles[half][x];
            for (int y = 0; y < background_size; y++) {
                if (y != half) {
                    if (y < half) {
                        mapTiles[y][x] = new Vector2(OTt.x, OTt.y - (TileSizeY * ((half + 1) - posYOffset)));
                    } else {
                        mapTiles[y][x] = new Vector2(OTt.x, OTt.y + (TileSizeY * (posYOffset - half)));
                    }
                    posYOffset++;
                }
            }
            posYOffset = 1;
        }

        for (Vector2[] mapTile : mapTiles) {
            for (Vector2 aMapTile : mapTile) {
                System.out.print(aMapTile + "         ");
                if (aMapTile != null) {
                    Vector2 vec = aMapTile;
                    tiles.add(vec);
                }
            }
            System.out.println();
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.begin();
        for(Vector2 vec : tiles) {
            if(player.inMap) {
                batch.draw(background, (-player.getBodyX()) - vec.x, (-player.getBodyY()) - vec.y, TileSizeX, TileSizeY);
            } else {
                batch.draw(background, (-level.lastLoc.x) - vec.x, (-level.lastLoc.y) - vec.y, TileSizeX, TileSizeY);
            }
        }
        batch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
