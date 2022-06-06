/*
 * Sorceress.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */

/**
 * Specifies the unique stats and methods for the Sorceress Hero.
 */
public class Sorceress extends Hero {

    /**
     * A constructor for the unique Sorceress Hero.
     * @param theName is the name of the Sorceress.
     */
    protected Sorceress(final String theName, int theHitPoints, int theAtkSpeed, int theMinDmg, int theMaxDmg,
                        int theHitChance, int theBlockChance, int theSpecialSkillChance) {
        super(theName, theHitPoints, theAtkSpeed, theMinDmg, theMaxDmg,
                theHitChance, theBlockChance, theSpecialSkillChance);
        setMaxHitPoints(75);
    }

    /** The maximum hitpoints that the hero will ever have (to prevent over healing). */
    private int myMaxHitPoints;

    /**
     * Sets the maximum hitpoints of the Sorceress.
     * @param theMaxHitPoints is the desired maximum hit points. (Equal to the starting amount of hitpoints).
     */
    private void setMaxHitPoints(final int theMaxHitPoints) {
        myMaxHitPoints = theMaxHitPoints;
    }

    /**
     * Gets the maximum hitpoints of the sorceress.
     * @return the maximum hitpoints.
     */
    public int getMaxHitPoints() {
        return myMaxHitPoints;
    }

    /**
     * Adds a randomly generated amount of health to the Sorceress between 20 and 40.
     * This method cannot heal over the maximum hitpoints of the Sorceress.
     */
    private void heal() {
        double heal = generateRandomValue(20, 40);
        if ((heal + getHitPoints()) >= getMaxHitPoints()) {
            addHitPoints(getMaxHitPoints()-getHitPoints());
            System.out.println(getName() + " has healed to full health!");
        } else {
            addHitPoints(heal);
            System.out.println(getName() + " has healed for " + heal + " Health Points");
        }
        playSound(healSound);
    }

    /**
     * Gives the special ability to heal.
     * @param opponent is the opponent the hero is facing.
     */
    @Override
    protected void specialAbility(final DungeonCharacter opponent) {
        heal();
    }

}
