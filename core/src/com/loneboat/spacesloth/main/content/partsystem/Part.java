package com.loneboat.spacesloth.main.content.partsystem;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * com.loneboat.spacesloth.main.content.systems
 * Created by Dubpub on 8/30/2015.
 *
 * The long awaited part system! WOOHOO!
 *
 *  - Parts are ordered and identified by rank and name.
 *  - Each part has different responsibilities.
 *
 *  - Current list of visible parts:
 *    [Double Mold] Wings
 *     - In charge of handing steering torque of the ship.
 *    [Single Mold] Cockpit
 *     - On screen HUD improvements.
 *    [Single Mold] GunMount
 *     - Fire rate.
 *    [Single Mold] Hull
 *     - Amount of gold your ship can hold.
 *    [Single Mold] Thrusters
 *     - Duration of thrust for your ship.
 *
 *  - Current List of ship systems parts:
 *    [Single Mold] Reactor
 *     - Base ship speed and how fast thrust recharges
 *    [Single Mold] Armory
 *     - Weapon type count ship can hold.
 *    [Single Mold] Tractor beam
 *     - Range on which your ship can pull gold in.
 *    [Single Mold] Refinery
 *     - Dictates your ships ability to harvest gold from ore.
 * Rank Overview:
 *  [A-F]
 *      Tier 1 list of parts.
 *  [A1-F1]
 *      Tier 2 list of parts.
 *  [A2-F3]
 *      Tier 3 list of parts.
 *  [S-X]
 *      Tier 4 list of parts.
 *  [S1000-X1000 Series]
 *      Dev. list of parts.
 */
public class Part {
    private String Rank;
    private double Cost;
    private float Health;
    private float RepairFactor;
    private boolean Visible;
    private String LocalName;
    private String FileName;
    private String RGB;
    private float Alpha;
    private String shortHand;
    private PartType partType;
    private ArrayList<PartFactory.Property> properties;

    public Part() {
        properties = new ArrayList<PartFactory.Property>();
    }

    public boolean isVisible() {
        return Visible;
    }

    public void setVisible(boolean visible) {
        Visible = visible;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public float getHealth() {
        return Health;
    }

    public void setHealth(float health) {
        Health = health;
    }

    public String getLocalName() {
        return LocalName;
    }

    public void setLocalName(String localName) {
        LocalName = localName;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public float getRepairFactor() {
        return RepairFactor;
    }

    public void setRepairFactor(float repairFactor) {
        RepairFactor = repairFactor;
    }

    public float getAlpha() {
        return Alpha;
    }

    public void setAlpha(float alpha) {
        Alpha = alpha;
    }

    public String getRGB() {
        return RGB;
    }

    public void setRGB(String RGB) {
        this.RGB = RGB;
    }

    public Color getColor() {
        Color c = Color.valueOf(getRGB());
        c.set(c.r, c.g, c.b, getAlpha());
        return c;
    }

    public String getShortHand() {
        return shortHand;
    }

    public void setShortHand(String shortHand) {
        this.shortHand = shortHand;
    }

    public PartType getPartType() {
        return partType;
    }

    public void setPartType(PartType partType) {
        this.partType = partType;
    }

    public void addProperty(PartFactory.Property property) {
        properties.add(property);
    }

    public PartFactory.Property getProperty(String key) {
        for(PartFactory.Property property : properties) {
            if(property.getKey().equalsIgnoreCase(key))
                return property;
        }
        return null;
    }
}
