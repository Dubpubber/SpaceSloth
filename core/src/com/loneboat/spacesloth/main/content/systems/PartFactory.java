package com.loneboat.spacesloth.main.content.systems;

import com.loneboat.spacesloth.main.game.actors.SlothShip;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * com.loneboat.spacesloth.main.content.systems
 * Created by Dubpub on 8/30/2015.
 *
 *  This class is solely responsible for handling the player's parts of their ship.
 *
 */
public class PartFactory {

    private SlothShip player;

    private List<Part> currentPartList;

    private HashMap<String, Part> partList;

    public void PartFactory(SlothShip player) {
        setPlayer(player);
        currentPartList = Collections.synchronizedList(new LinkedList<Part>());
        partList = new HashMap<String, Part>();
    }

    public void setPlayer(SlothShip player) {
        this.player = player;
    }

    public SlothShip getPlayer() {
        return player;
    }

    public void populatePartList() {
        // Add parts [A-F]
    }

}
