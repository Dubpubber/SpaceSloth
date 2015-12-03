package com.loneboat.spacesloth.main.game.systems.console;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.loneboat.spacesloth.main.content.ContentHandler;
import com.loneboat.spacesloth.main.screens.GameLevel;

/**
 * com.loneboat.spacesloth.main.game.systems.console
 * Created by Dubpub on 12/2/2015.
 */
public class PlayerConsole extends Actor {

    public enum CrewType {

        ENGINEERING("C90000", "[ENG]"),
        COMMUNICATIONS("A941CC", "[COMMS]"),
        COLONIZATION("45E6B8", "[CLN]"),
        HQ("FFFFFF", "[HQ]"),
        BALLISTICS("F0229A", "[BLTS]"),
        FLEETCOMMAND("64B3E8", "[FC]"),
        FIRSTMATE("7164A3", "[FM]"),
        RANDD("F2EC3D", "[R&D]"),
        MEDICAL("ED984E", "[MED]"),
        ADMINISTRATION("08FF80", "[ADM]");

        private final String c;
        private final String title;

        CrewType(String c, String title) {
            this.c = c;
            this.title = title;
        }

        public Color getColor() {
            return Color.valueOf(c);
        }

        public String getTitle() {
            return title;
        }

    }

    private GameLevel level;

    private final int ConsoleWidth = 500;
    private final int ConsoleHeight = 250;

    private final float ConsoleX = 7;
    private final float ConsoleY = 505;

    private final String ConsoleStartingStr = "> ";

    private BitmapFont font;

    private TextField.TextFieldStyle style;
    private TextArea area;

    public PlayerConsole(GameLevel level, int fontSize) {
        this.level = level;

        font = ContentHandler.generateAllerFont(fontSize);
        createConsoleWindow();
    }

    @Override
    public void draw(Batch batch, float delta) {
        area.setStyle(style);
        area.draw(batch, delta);
    }

    @Override
    public void act(float parentAlpha) {
        area.act(parentAlpha);
    }

    public void createConsoleWindow() {
        style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = CrewType.HQ.getColor();

        area = new TextArea("Welcome, Captain. Press L to view the captain's log.", style);
        area.setPosition(ConsoleX, ConsoleY);
        area.setSize(ConsoleWidth, ConsoleHeight);

        for(CrewType crewType : CrewType.values()) {
            writeFromCrew(crewType, "Hello, Systems check!");
        }
    }

    public void write(String out) {
        area.appendText("\n" + ConsoleStartingStr + out);
    }

    public void writeFromCrew(CrewType type, String line) {
        String title = type.title;
        area.appendText("\n" + title + " " + line);
    }

    public void writeFromCrew(CrewType type, String[] lines) {
        String title = type.title;
        style.fontColor = type.getColor();
        for(String line : lines) {
            area.appendText("\n" + title + " " + line);
        }
    }

}