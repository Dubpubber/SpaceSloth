package com.loneboat.spacesloth.main.game.worldobjects.galaxy;

import com.loneboat.spacesloth.main.game.worldobjects.ores.Ore;

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



}
