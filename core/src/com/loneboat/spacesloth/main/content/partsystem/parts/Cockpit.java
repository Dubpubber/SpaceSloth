package com.loneboat.spacesloth.main.content.partsystem.parts;

import com.loneboat.spacesloth.main.content.partsystem.Part;
import com.loneboat.spacesloth.main.game.GameObject;

/**
 * com.loneboat.spacesloth.main.content.systems.parts
 * Created by Dubpub on 8/31/2015.
 */
public class Cockpit extends Part {

    public Cockpit(String RANK, double COST, float HEALTH, double REPAIR_FACTOR, String LOCAL_NAME, String NAME, GameObject gameObject) {
        super(RANK, COST, HEALTH, REPAIR_FACTOR, true, LOCAL_NAME, NAME, gameObject);
        createFixtureForPart(0, 0);
    }

}
