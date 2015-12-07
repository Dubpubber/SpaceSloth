package com.loneboat.spacesloth.main.game.worldobjects.galaxy;

import com.loneboat.spacesloth.main.game.actors.SlothShip;
import com.loneboat.spacesloth.main.game.factions.Faction;
import com.loneboat.spacesloth.main.game.worldobjects.ores.Ore;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * com.loneboat.spacesloth.main.game.worldobjects.galaxy
 * Created by Dubpub on 12/5/2015.
 */
public interface Planet {

    /*
        Sets and gets the name of the planet.
     */
    String getName();
    void setName(String name);

    /*
        Visited or not visited.
     */
    boolean getVisited();
    void setVisited(boolean visited);

    /*
        Sets and gets the effective scan level for the planet.
     */
    int getScanLevel();
    void setScanLevel(int level);

    /*
        Resource list for all known possible resources on the planet.
     */
    ArrayList<Ore> getResources();
    void setResources(ArrayList<Ore> resources);
    void addResource(Ore ore);
    void removeResource(Ore ore);
    void getResourceCount(Ore ore);

    /*
        Sets what faction owns this planet.
     */
    Faction getOwner();
    void setOwner(Faction faction);

    /*
        Sets/Gets the planet's required entry level.
     */
    int getEntryLevel();
    void setEntryLevel(int level);
    boolean hasEntryLevel(SlothShip player);

    /*
        More developed planets have entry fee's attached to them.
     */
    BigDecimal getEntryFee();
    void setEntryFee(BigDecimal price);
    boolean hasEntryFee(SlothShip player);

}
