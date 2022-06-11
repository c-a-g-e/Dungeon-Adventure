/*
 * Hero.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */

/**
 * Contains the hero object with unique characteristics that the monster object will not have.
 */
class Hero extends DungeonCharacter{

    /** The chance that the hero will block the next incoming attack from the monster. */
    private int myBlockChance;
    /** The chance that the hero's special skill will succeed. */
    protected int mySpecialSkillChance;
    /** The amount of turns that a hero gets per round. */
    private double myTurns;
    /** The number of health potions that the hero has */
    private int myHealthPotions;
    /** The number of vision potions that the hero has. */
    private int myVisionPotions;
    /** The number of crown pieces that the hero has. */
    private int myPillars;
    /** The x (column) coordinate of the room that the character is in. */
    private int myRoomX;
    /** The y (row) coordinate of the room that the character is in. */
    private int myRoomY;
    /** True if the hero has escaped the dungeon. */
    private boolean myEscaped;
    /** The boolean that determines whether or not the player wants to run away from the battle and quit the game. */
    private boolean myRunAway;
//    private String[] myCollectedPillars = new String[4];


    /**
     * The constructor method for the hero object that also initiates unique fields.
     * @param theName is the name.
     * @param theHitPoints is the hitpoints.
     * @param theAtkSpeed is the attack speed.
     * @param theMinDmg is the minimum damage.
     * @param theMaxDmg is the maximum damage.
     * @param theHitChance is the chance that the hero will land their attack.
     * @param theBlockChance is the chance that the hero will block the next monster attack.
     * @param theSpecialSkillChance is the chance that the hero's special skill will be sucecssful.
     */
    protected Hero(final String theName, final int theHitPoints, final int theAtkSpeed, final int theMinDmg,
                   final int theMaxDmg, final int theHitChance, final int theBlockChance, final int theSpecialSkillChance) {
        super(theName, theHitPoints, theAtkSpeed, theMinDmg, theMaxDmg, theHitChance);
        setBlockChance(theBlockChance);
        setSpecialSkillChance(theSpecialSkillChance);
    }

    /**
     * Sets the hitpoints of the hero.
     * @param theHitPoints The desired hit points.
     */
    protected void setHitPoints(final double theHitPoints) {
        super.setHitPoints(theHitPoints);
    }

    /**
     * Sets the special skill chance of the hero.
     * @param theSpecialSkillChance the desired special skill chance as an int from 1-100.
     */
    protected void setSpecialSkillChance(final int theSpecialSkillChance) {
        mySpecialSkillChance = theSpecialSkillChance;
    }

    /**
     * Sets the block chance of the hero.
     * @param theBlockChance is the desired chance that the next monster attack is blocked.
     */
    protected void setBlockChance(final int theBlockChance) {
        myBlockChance = theBlockChance;
    }

    /**
     * Sets the turns of the hero.
     * @param theTurns is the desired turns.
     */
    protected void setTurns(final double theTurns) {
        myTurns = theTurns;
    }

    /**
     * Sets the runAway field to True or False.
     * @param theRunAway the true or false run away.
     */
    protected void setRunAway(final Boolean theRunAway) {
        myRunAway = theRunAway;
    }

    /**
     * Sets the x (column) value of the room that the character is in.
     * @param theX is the int y value.
     */
    public void setMyRoomX(final int theX) {
        myRoomX += theX;
    }

    /**
     * Sets the y (row) value of the room that the character is in.
     * @param theY is the int y value.
     */
    public void setMyRoomY(final int theY) {
        myRoomY += theY;
    }

    /**
     * Sets whether or not the character has escaped the dungeon.
     * @param theBool true if the character has escaped.
     */
    public void setMyEscaped(final boolean theBool) {
        myEscaped = theBool;
    }

    /**
     * Sets the amount of health potions to the integer parameterized amount.
     * @param theAmount the amount that the potions will be set to.
     */
    public void setMyHealthPotions(final int theAmount) {
        myHealthPotions = theAmount;
    }

    /**
     * Sets the amount of health potions to the integer parameterized amount.
     * @param theAmount the amount the the potions will be set to.
     */
    public void setMyVisionPotions(final int theAmount) {
        myVisionPotions = theAmount;
    }

    /**
     * Sets the amount of crown pieces to the integer parameterized amount.
     * @param theAmount the amount that the crown pieces will be set to.
     */
    public void setMyPillars(final int theAmount) {
        myPillars = theAmount;
    }

    /**
     * Gets the block chance of the hero.
     * @return the block chance.
     */
    public int getBlockChance() {
        return myBlockChance;
    }

    /**
     * Gets the amount of turns that a hero has.
     * @return the amount of turns in int.
     */
    public double getTurns() {
        return myTurns;
    }

    /**
     * Gets the runAway value
     * @return the run away field.
     */
    public boolean getRunAway() {
        return myRunAway;
    }

    /**
     * Retrieves the hero's y (column) coordinate in the dungeon.
     * @return the int x (column) value.
     */
    public int getMyRoomX() {
        return myRoomX;
    }

    /**
     * Retrieves the hero's y (row) coordinate in the dungeon.
     * @return the int y (row) value.
     */
    public int getMyRoomY() {
        return myRoomY;
    }

    /**
     * Returns whether or not the hero has escaped the dungeon (win condition).
     * @return true if the hero has escaped.
     */
    public boolean getMyEscaped() { return myEscaped; }

    /**
     * Retrieves the number of health potions that the hero is holding.
     * @return the number of health potions.
     */
    public int getMyHealthPotions() { return myHealthPotions; }
    /**
     * Retrieves the number of vision potions that the hero is holding.
     * @return the number of vision potions.
     */
    public int getMyVisionPotions() { return myVisionPotions; }
    /**
     * Retrieves the number of crown pieces that the hero is holding.
     * @return the number of crown pieces.
     */
    public int getMyPillars() { return myPillars; }

    /**
     * Checks whether or not the hero can block depending on their block chance.
     * @return true if the hero can block, false if not.
     */
    protected boolean canBlock() {
        double checkMyBlockChance = generateRandomValue(0, 100);
        return checkMyBlockChance <= getBlockChance();
    }

    /**
     * Overrides the parent method to implement blocking.
     * @param theHitPoints is the amount of hitpoints being subtracted from this object.
     */
    @Override
    protected void subtractHitPoints(final double theHitPoints) {
        if (canBlock()) {
            playSound(shieldBlock);
            System.out.println(getName() + " blocked the attack!");
            System.out.println("*0* damage");
        } else {
            super.subtractHitPoints(theHitPoints);
            if (this instanceof Sorceress) {
                playSound(femaleHit);
            } else {
                playSound(maleHit);
            }
        }
    }

    /**
     * Sets the hero's turns versus a monster. The hero will never have fewer turns than the monster.
     * If the hero has less attack speed than the monster, the hero and the monster will both be assigned 1 turn.
     * @param opponent
     */
    protected void setTurnsVsMonster(final DungeonCharacter opponent) {
        setTurns(getAtkSpeed() / opponent.getAtkSpeed());
        if (getTurns() == 0) {
            setTurns(1);
        }
    }

    /**
     * Overrides the parent method to prompt the user to attack, use their special, or run away.
     * @param opponent is the opposing object that will be losing hit points.
     */
    @Override
    public void attack(final DungeonCharacter opponent) {
        setTurnsVsMonster(opponent);
        String attackPrompt = """
                
                What would you like to do next?
                
                Enter (1) to use your normal attack
                Enter (2) to use a special ability
                Enter (3) to run away
                """;
        int response = getIntInRange(console, attackPrompt, 1, 3);
        if (response == 1) {
            super.attack(opponent);
        } else if (response == 2) {
            this.specialAbility(opponent);
        } else if (response == 3) {
            setRunAway(true);
            System.out.println(getName() + " has run away.");
            playSound(runAway);
        }
    }

    /**
     * Generates a random number and heals the hero when a healing potion is used.
     */
    public void useHealingPotion() {
        myHealthPotions--;
        int heal = Dungeon.generateRandomValue(15, 30);
        addHitPoints(heal);
        playSound(healSound);
        System.out.println(getName() + " has healed for " + heal + " hit points!");
    }

    /**
     * Prints out what the rooms surrounding the hero contain when a vision potion is used.
     * @param theDungeon is the dungeon that contains the rooms.
     */
    public void useVisionPotion(final Room[][] theDungeon) {
        System.out.println("You use a vision potion that displays the rooms surrounding your position: \n");
        if (getMyRoomY() - 1 >= 0) {
            theDungeon[getMyRoomY() - 1][getMyRoomX()].setMyUsedVisionPotion(true);
            System.out.print("Northern room contains: ");
            theDungeon[getMyRoomY() - 1][getMyRoomX()].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomY() - 1 >= 0 && getMyRoomX() + 1 <= Dungeon.MY_COLUMNS - 1) {
            theDungeon[getMyRoomY() - 1][getMyRoomX() + 1].setMyUsedVisionPotion(true);
            System.out.print("Northeastern room contains: ");
            theDungeon[getMyRoomY() - 1][getMyRoomX() + 1].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomX() + 1 <= Dungeon.MY_COLUMNS - 1) {
            theDungeon[getMyRoomY()][getMyRoomX() + 1].setMyUsedVisionPotion(true);
            System.out.print("Eastern room contains: ");
            theDungeon[getMyRoomY()][getMyRoomX() + 1].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomY() + 1 <= Dungeon.MY_ROWS - 1 && getMyRoomX() + 1 <= Dungeon.MY_COLUMNS - 1) {
            theDungeon[getMyRoomY() + 1][getMyRoomX() + 1].setMyUsedVisionPotion(true);
            System.out.print("Southeastern room contains: ");
            theDungeon[getMyRoomY() + 1][getMyRoomX() + 1].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomY() + 1 <= Dungeon.MY_ROWS - 1) {
            theDungeon[getMyRoomY() + 1][getMyRoomX()].setMyUsedVisionPotion(true);
            System.out.print("Southern room contains: ");
            theDungeon[getMyRoomY() + 1][getMyRoomX()].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomY() + 1 <= Dungeon.MY_ROWS - 1 && getMyRoomX() - 1 >= 0) {
            theDungeon[getMyRoomY() + 1][getMyRoomX() - 1].setMyUsedVisionPotion(true);
            System.out.print("Southwestern room contains: ");
            theDungeon[getMyRoomY() + 1][getMyRoomX() - 1].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomX() - 1 >= 0) {
            theDungeon[getMyRoomY()][getMyRoomX() - 1].setMyUsedVisionPotion(true);
            System.out.print("Western room contains: ");
            theDungeon[getMyRoomY()][getMyRoomX() - 1].printVisionPotion();
            System.out.println();
        }
        if (getMyRoomY() - 1 >= 0 && getMyRoomX() - 1 >= 0) {
            theDungeon[getMyRoomY() - 1][getMyRoomX() - 1].setMyUsedVisionPotion(true);
            System.out.print("Northwestern room contains: ");
            theDungeon[getMyRoomY() - 1][getMyRoomX() - 1].printVisionPotion();
            System.out.println();
        }
        setMyVisionPotions(getMyVisionPotions() - 1);
    }

    /**
     * The special attack of a specific child class.
     * @param opponent is the opponent the hero is facing.
     */
    protected void specialAbility(final DungeonCharacter opponent) {}

    /**
     * Prints the hero's health as well as how many items they have.
     */
    public void printInventory() {
        System.out.println(getName() + "'s status:" + "" +
                "\n\tHitpoints: " + getHitPoints() +
                "\n\tInventory: " +
                "\n\t\tHealth Potions: " + getMyHealthPotions() +
                "\n\t\tVision Potions: " + getMyVisionPotions() +
                "\n\t\tPillars of OO: " + getMyPillars());
    }

}

