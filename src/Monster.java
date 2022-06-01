/*
 * Monster.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */

/**
 * Contains the monster object with unique characteristics that the hero object will not have.
 */
public class Monster extends DungeonCharacter {

    /** The chance that the Monster will heal after being attacked. (Only if the Monster is still alive). */
    private int myHealChance;

    /** The minumum amount of health that the Monster will heal. */
    private int myMinHeal;

    /** The maximum amount of health that the Monster will heal. */
    private int myMaxHeal;

    /** The maximum hitpoints that the hero will ever have (to prevent over healing). */
    private int myMaxHitPoints;

    /**
     * The constructor method for the monster object that also initiates unique fields.
     * @param theName is the name.
     * @param theHitPoints is the starting hitpoints.
     * @param theAtkSpeed is the attack speed.
     * @param theMinDmg is the minimum damage this can deal.
     * @param theMaxDmg is the maximum damage this can deal.
     * @param theHitChance is the chance that their attack will hit.
     * @param theHealChance is the chance that they will heal after an attack.
     * @param theMinHeal is the minimum amount they can heal.
     * @param theMaxHeal is the maximum amount they can heal.
     */
    protected Monster(final String theName, final int theHitPoints, final int theAtkSpeed, final int theMinDmg,
                      final int theMaxDmg, final int theHitChance, final int theHealChance,
                      final int theMinHeal, final int theMaxHeal) {
        super(theName, theHitPoints, theAtkSpeed, theMinDmg, theMaxDmg, theHitChance);
        setHealChance(theHealChance);
        setMinHeal(theMinHeal);
        setMaxHeal(theMaxHeal);
        setMaxHitPoints(theHitPoints);
    }

    /**
     * Sets the chance the Monster will heal after taking damage,.
     * @param theHealChance is the desired minimum heal amount.
     */
    private void setHealChance(final int theHealChance) {
        myHealChance = theHealChance;
    }

    /**
     * Sets the minimum heal amount.
     * @param theMinHeal is the desired minimum heal amount.
     */
    private void setMinHeal(final int theMinHeal) {
        myMinHeal = theMinHeal;
    }

    /**
     * Sets the maximum heal amount.
     * @param theMaxHeal is the desired maximum heal amount.
     */
    private void setMaxHeal(final int theMaxHeal) {
        myMaxHeal = theMaxHeal;
    }

    /**
     * Sets the maximum hitpoints of the Monster. (Equal to the starting amount of hitpoints).
     * @param theMaxHitPoints is the maximum hitpoints the Monster will have at any given time.
     */
    private void setMaxHitPoints(final int theMaxHitPoints) {
        myMaxHitPoints = theMaxHitPoints;
    }

    /**
     * Gets the heal chance.
     * @return the heal chance.
     */
    public int getHealChance() {
        return myHealChance;
    }

    /**
     * Gets the minimum heal.
     * @return the minimum heal.
     */
    public int getMinHeal() {
        return myMinHeal;
    }

    /**
     * Gets the maximum heal.
     * @return the maximum heal.
     */
    public int getMaxHeal() {
        return myMaxHeal;
    }

    /**
     * Gets the maximum hitpoints the monster will have at any given time.
     * @return the maximum hitpoints.
     */
    public int getMaxHitPoints() {
        return myMaxHitPoints;
    }

    /**
     * Determines whether or not the monster can heal after taking damage. If the randomly
     * generated number is larger than the healChance or the !Monster.alive(), the method
     * will return false.
     * @return true if able to heal, false if not.
     */
    private boolean canHeal() {
        if (alive() && getHitPoints() != getMaxHitPoints()) {
            int checkHealChance = generateRandomValue(0, 100);
            return checkHealChance <= getHealChance();
        } else {
            return false;
        }
    }

    /**
     * Carries out the heal function. Adds a randomly generated number of hitpoints between
     * the minHeal and the maxHeal. This function cannot add hitpoints above the maximum hitpoints.
     */
    private void heal() {
        int heal = generateRandomValue(getMinHeal(), getMaxHeal());
        if ((heal + getHitPoints()) >= getMaxHitPoints()) {
            addHitPoints(getMaxHitPoints()-getHitPoints());
            System.out.println("The " + getName() + " has healed to full health!");
        } else {
            addHitPoints(heal);
            System.out.println("The Monster has healed for " + heal + " Health Points");
        }
        playSound(healSound);
    }

    /**
     * Subtracts hitpoints from the Monster's hit point pool. If the monster is alive() and canHeal(),
     * the Monster will heal after being attacked.
     * This method also plays sounds based on which Monster child class is losing hitpoints.
     * @param theHitPoints is the amount of hitpoints being subtracted from this object.
     */
    @Override
    protected void subtractHitPoints(final int theHitPoints) {
        if (canHeal() && alive()) {
            super.subtractHitPoints(theHitPoints);
            heal();
        } else if (alive()) {
            super.subtractHitPoints(theHitPoints);
        }
        if (getName().equals("Ogre")) {
            playSound(ogreHit);
        } else if (this instanceof Skeleton ) {
            playSound(skeletonHit);
        } else if (this instanceof Gremlin) {
            playSound(gremlinHit);
        }
    }

    /**
     * Sets the monsters turns for the round. A Monster will never have more turns than a Hero and it will
     * always have at least one turn.
     * @param opponent
     */
    protected void setTurnsVsHero(final DungeonCharacter opponent) {
        setTurns(getAtkSpeed() / opponent.getAtkSpeed());
        if (getTurns() == 0) {
            setTurns(1);
        }
    }

    /**
     * Attacks a Hero object and subtracts hitpoints determined by the minDmg and maxDmg of the Monster.
     * The attack also has a chance to miss.
     * @param opponent is the opposing object that will be losing hit points.
     */
    @Override
    public void attack(final DungeonCharacter opponent) {
        setTurnsVsHero(opponent);
        while (getTurns() > 0) {
            double hitChanceCheck = generateRandomValue(1, 100);
            if (hitChanceCheck <= getHitChance()) {
                if (this instanceof Ogre) {
                    playSound(ogreAttack);
                } else if (this instanceof Skeleton || this instanceof Gremlin) {
                    playSound(swordHit);
                }
                int atkDmg = generateRandomValue(getMinDmg(), getMaxDmg());
                opponent.subtractHitPoints(atkDmg);
                setTurns(getTurns() - 1);
            } else {
                System.out.println("Hit missed!");
                playSound(attackMiss);
                setTurns(getTurns() - 1);
            }
        }
    }
}
