package com.loneboat.spacesloth.main.game.factions;

import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.game.actors.SlothShip;

import java.math.BigDecimal;

/**
 * com.loneboat.spacesloth.main.game.factions
 * Created by Dubpub on 11/27/2015.
 */
public abstract class Faction {

    // The game class
    private SpaceSloth game;

    // The player of SpaceSloth: Deep Space
    private SlothShip player;

    // The name of the faction.
    private String factionName;

    /**
     * Reputation for any faction is the faction's relation to the player.
     * Range: (0-1000)
     *  0-250: Attack player on sight.
     *  251-500: Neutral to the player.
     *  501-750: Friends to the player.
     *  751-1000: Allies to the player.
     *
     * To trade with this faction, the player must at least be friends with the faction.
     */
    private float reputation;

    /**
     * Strength represents the army strength of the faction.
     * Range: (0-2500)
     *   0-250: ClassE Fighters and bombers.
     *   251-300: ClassD Fighters and bombers. Rank 1 planetary defences.
     *   301-450: ClassC Fighters and bombers, ClassE Landers. Rank 2 planetary defences.
     *   351-750: ClassB Fighters and bombers, ClassD Landers. Rank 3 planetary defences. Market unlock. (Trade ship unlocked)
     *   751-1000: ClassB Fighters and bombers, ClassC Landers. Rank 3 planetary defences. Research bay unlock.
     *   1001-1500: ClassA Fighters and bombers, ClassB Landers. Rank 4 planetary defences. Advanced military unlock.
     *   1001-1500: ClassS Fighters and bombers, ClassA Landers. Rank 5 planetary defences. Land vehicles unlock.
     *   1501-2000: ClassS Fighters and bombers, ClassS Landers, BattleHorn StarShip C. Rank 5 planetary defences.
     *   2001-2250: ClassS Fighters and bombers, ClassS Landers, BattleHorn StarShip B. Rank 5 planetary defences.
     *   2251-2500: ClassS Fighters and bombers, ClassS Landers, BattleHorn StarShip A. Rank 6 planetary defences. Advanced rocket trigonometry.
     */
    private float strength;

    /**
     * Amount of base cash for killing any space, land, civilian unit.
     *
     *   Factors of increase:
     *    - Hostile planet takeovers
     *    - Army strength increase
     *    - Available resources
     *
     *   How it decreases:
     *    - Friendly planet takeovers
     *    - Trade
     *    - Peaceful
     */
    private BigDecimal bounty;

    /**
     * Creates a new faction object.
     * @param game - The core game needed for logging mostly.
     * @param player - The player.
     * @param factionName - Faction's name.
     */
    public Faction(SpaceSloth game, SlothShip player, String factionName) {
        this.game = game;
        this.player = player;
        this.factionName = factionName;
    }

    /**
     * Sets the faction's reputation.
     * @param reputation - float representing the new reputation.
     */
    public void setReptutation(float reputation) {
        this.reputation = reputation;
    }

    /**
     * Adds reputation to this faction.
     * @param reputation - float representing the increase to the new reputation.
     */
    public void addReputation(float reputation) {
        this.reputation += reputation;
    }

    /**
     * Subtract's reputation to this faction.
     * @param reputation - float representing the decrease from the current reputation.
     */
    public void subReputation(float reputation) {
        this.reputation -= reputation;
    }

    /**
     * Get's the factions reputation as a float value. Refer to the comment above the reputation variable for more information.
     * @return - This factions current reputation.
     */
    public float getReputation() {
        return reputation;
    }

    /**
     * Set's this faction's current military strength.
     * @param strength - The new strength of this faction represented by a float value.
     */
    public void setStrength(float strength) {
        this.strength = strength;
    }

    /**
     * Adds an amount to the current strength of this faction.
     * @param strength - Strength represented as float to add to the current strength of this faction.
     */
    public void addStrengthfloat(float strength) {
        this.strength += strength;
    }

    /**
     * Subtracts an amount to the current strength of this faction.
     * @param strength - Strength represented as float to subtract from the current strength of this faction.
     */
    public void subStrength(float strength) {
        this.strength -= strength;
    }

    /**
     * Gets the army strength of this faction. Refer to the comment above the strength variable for more information.
     * @return - The current strength of this faction.
     */
    public float getStrength() {
        return strength;
    }

    /**
     * Sets the current bounty amount of this faction.
     * @param bounty - Sets the bounty value of this faction. Represented by a big decimal.
     */
    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }

    /**
     * Adds an amount to the current faction's bounty.
     * @param addBounty - The bounty being added to the current faction represented by a big decimal.
     */
    public void addBounty(BigDecimal addBounty) {
        this.bounty = this.bounty.add(addBounty);
    }

    /**
     * Subtracts an amount from the current faction's bounty
     * @param subBounty - The bounty being subtracted from this faction.
     */
    public void subBounty(BigDecimal subBounty) {
        this.bounty = this.bounty.add(subBounty);
    }

    /**
     * Get's the bounty as a BigDecimal for this faction.
     * @return - bounty worth as big decimal.
     */
    public BigDecimal getBounty() {
        return bounty;
    }

}
