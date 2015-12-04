package com.loneboat.spacesloth.main.game.systems.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.screens.GameLevel;

/**
 * com.loneboat.spacesloth.main.game.systems.console
 * Created by Dubpub on 12/2/2015.
 */
public class PlayerConsole extends Actor {

    public enum CrewType {

        // RED
        ENGINEERING("FF0D0D", "[ENG]", "Systems go Captain."),
        // BLUE
        COMMUNICATIONS("4775FF", "[COMMS]", "Comms active."),
        // YELLOW ORANGE
        COLONIZATION("FFD119", "[CLN]", "Colonization office reporting."),
        // PINK
        QUARTERMASTER("FF0AFB", "[QMTR]", "Hull's priming for some stocking."),
        // WHITE
        HQ("FFFFFF", "[HQ]", "Station online."),
        // RED ORANGE
        BALLISTICS("FF780A", "[BLTS]", "Weapons active and ready Captain!"),
        // ORANGE
        FLEETCOMMAND("FFCC00", "[FC]", "Fleet command standing by."),
        // TEAL
        FIRSTMATE("2EFFFC", "[FM]", "At your side Captain."),
        // PURPLE
        RANDD("B66EFF", "[R&D]", "Lab reporting."),
        // GREEN
        MEDICAL("45FF48", "[MED]", "Ready for treatment."),
        // BLUE GREEN
        ADMINISTRATION("45FFA8", "[ADM]", "Ready.");

        private final String c;
        private final String title;
        private final String greeting;

        CrewType(String c, String title, String greeting) {
            this.c = c;
            this.title = title;
            this.greeting = greeting;
        }

        public Color getColor() {
            return Color.valueOf(c);
        }

        public String getTitle() {
            return title;
        }

        public String getGreeting() {
            return greeting;
        }

    }

    private GameLevel level;

    private final int ConsoleWidth = 500;
    private final int ConsoleHeight = 250;

    private final float ConsoleX = 0;
    private final float ConsoleY = 505;

    private final String ConsoleStartingStr = "> ";

    private BitmapFont font;

    private Array<Label> entries;
    private final int history = 10;

    // UI elements
    private Label.LabelStyle entryStyle;

    private ScrollPane pane;
    private Table console;
    private Table scrollingTable;

    private ShapeRenderer sr;
    private boolean debug = true;

    private Timer refreshTimer;

    public PlayerConsole(GameLevel level, int fontSize) {
        this.level = level;

        font = ContentHandler.generateAllerFont(fontSize);
        sr = new ShapeRenderer();

        entries = new Array<>(history);

        // UI elements
        console = new Table();

        entryStyle = new Label.LabelStyle(font, Color.WHITE);

        for(int i = 0; i < history; i++) {
            Label l = new Label("Numbah: " + MathUtils.random(0, 1000000000), entryStyle);
            entries.add(l);
            console.add(l).align(Align.left).fill(true, false).row();
        }

        pane = new ScrollPane(console);

        scrollingTable = new Table();
        scrollingTable.add(pane);
        scrollingTable.setSize(ConsoleWidth, ConsoleHeight);
        scrollingTable.setPosition(ConsoleX, ConsoleY);

        level.HudStage.addActor(scrollingTable);
        refreshTimer = new Timer();
        refreshTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                refresh();
            }
        }, 0, 3);
    }

    @Override
    public void draw(Batch batch, float delta) {
        super.draw(batch, delta);
        if(debug) {
            level.HudStage.setDebugTableUnderMouse(true);
        }
    }

    @Override
    public void act(float parentAlpha) {

    }

    public void refresh() {
        level.getLogger().info("Updating console.");
    }

}