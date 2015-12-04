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

    public enum UrgencyLevel {

        LOW("B7FF59");

        private final String c;

        UrgencyLevel(String c) {
            this.c = c;
        }

        public Color getColor() {
            return Color.valueOf(c);
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

    private String message;

    public PlayerConsole(GameLevel level, int fontSize) {
        this.level = level;

        font = ContentHandler.generateAllerFont(fontSize);
        createConsoleWindow();
    }

    @Override
    public void draw(Batch batch, float delta) {
        font.getData().markupEnabled = true;
        area.draw(batch, delta);
    }

    @Override
    public void act(float parentAlpha) {
        area.act(parentAlpha);
    }

    public void createConsoleWindow() {
        style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = Color.GREEN;

        area = new TextArea("Welcome, Captain. Press L to view the captain's log.", style);
        area.setPosition(ConsoleX, ConsoleY);
        area.setSize(ConsoleWidth, ConsoleHeight);
        area.setCursorPosition(0);

        setMessageFromCrew(CrewType.COMMUNICATIONS, "Opening comms with the departments... stand by for confirmation notices.");
        for(CrewType type : CrewType.values()) {
            setMessageFromCrew(type, type.getGreeting());
        }
    }

    public void setMessageFromCrew(CrewType type, String line) {
        String title = type.title;
        message = "[#" + type.getColor() + "]" + title + "[] " + line + " Over.";
    }

    public void writeMessage() {
        area.appendText(message);
    }

}