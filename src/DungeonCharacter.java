/*
 * DungeonCharacter.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */

import org.sqlite.SQLiteDataSource;

import java.util.Random;
import java.util.Scanner;
import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

/**
 * The parent object of the Hero and Monster classes. This class contains all of the general
 * methods, fields and actions that both the Hero and Monster classes will use.
 */
public class DungeonCharacter {

    /** The random object that is used to generate random numbers. */
    protected static final Random RANDOM_NUM = new Random();

    /** The default scanner. */
    protected static final Scanner console = new Scanner(System.in);

    /** The WAV sound file for male heroes receiving damage. */
    File maleHit = new File("res/maleHit.wav");

    /** The WAV sound file for female heroes receiving damage. */
    File femaleHit = new File("res/femaleHit.wav");

    /** The WAV sound file for an Ogre Monster receiving damage. */
    File ogreHit = new File("res/ogreHit.wav");

    /** The WAV sound file for a Skeleton Monster receiving damage. */
    File skeletonHit = new File("res/skeletonHit.wav");

    /** The WAV sound file for a Gremlin Monster receiving damage. */
    File gremlinHit = new File("res/gremlinHit.wav");

    /** The WAV sound file for the Thief Hero's attack receiving damage. */
    File throwKnife = new File("res/throwKnife.wav");

    /** The WAV sound file for healing. */
    File healSound = new File("res/heal.wav");

    /** The WAV sound file for a Sorcerer Hero dealing damage. */
    File magic = new File("res/magic.wav");

    /** The WAV sound file for an Ogre Monster dealing damage. */
    File ogreAttack = new File("res/ogreAttack.wav");

    /** The WAV sound file for Heroes blocking damage. */
    File shieldBlock = new File("res/shieldBlock.wav");

    /** The WAV sound file for a Warrior Hero's special ability. */
    File warriorSpecial = new File("res/warriorSpecial.wav");

    /** The WAV sound file for a DungeonCharacter with a sword dealing damage. */
    File swordHit = new File("res/swordHit.wav");

    /** The WAV sound file for a Thief Hero's special ability. */
    File thiefSpecial = new File("res/thiefSpecial.wav");

    /** The WAV sound file for an attack that misses. */
    File attackMiss = new File("res/attackMiss.wav");

    /** The WAV sound file for a Hero that runs away. */
    File runAway = new File("res/runAway.wav");

    /** The name of the DungeonCharacter. */
    private String myName;

    /** The Hit/Health points of the DungeonCharacter. */
    private double myHitPoints;

    /** The attack speed of the DungeonCharacter. */
    private int myAtkSpeed;

    /** The minimum damage that the DungeonCharacter can deal. */
    private int myMinDmg;

    /** The maximum damage that the DungeonCharacter can deal. */
    private int myMaxDmg;

    /** The chance that the DungeonCharacter's attack lands. */
    private int myHitChance;

    /** The amount of attacks that the DungeonCharacter gets per round. */
    private double myTurns;

    protected void connectToDB() {
        SQLiteDataSource ds = null;
        //establish connection
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:DungeonCharacter.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Generates a random integer in the bounds of the inputted low and high values.
     * @param theLow The low end of the bound.
     * @param theHigh The high end of the bound.
     * @return The random value that is generated.
     */
    protected static double generateRandomValue(final double theLow, final double theHigh) {
        return theLow + RANDOM_NUM.nextInt((int) (theHigh - theLow + 1));
    }

    /**
     * The DungeonCharacter constructor that passes in all of the statistics of the character and
     * initializes the fields.
     * @param theName The name.
     * @param theHitPoints The Hit/Health points.
     * @param theAtkSpeed The attack speed.
     * @param theMinDmg The minimum damage.
     * @param theMaxDmg The maximum damage.
     * @param theHitChance The hit chance.
     */
    protected DungeonCharacter(final String theName, final int theHitPoints, final int theAtkSpeed,
                               final int theMinDmg, final int theMaxDmg, final int theHitChance) {
        connectToDB();
        setName(theName);
        setHitPoints(theHitPoints);
        setAtkSpeed(theAtkSpeed);
        setMinDmg(theMinDmg);
        setMaxDmg(theMaxDmg);
        setHitChance(theHitChance);
    }

    /**
     * Sets myName to a String.
     * @param theName The desired name in string format.
     */
    protected void setName(final String theName) {
        myName = theName;
    }

    /**
     * Sets the Hit/Health points as an integer.
     * @param theHitPoints The desired hit points.
     */
    protected void setHitPoints(final double theHitPoints) {
        if (theHitPoints < 0) {
            throw new IllegalArgumentException("Hit points passed to setHitPoints was negative.");
        }
        myHitPoints = theHitPoints;
        if (myHitPoints <= 0) {
            myHitPoints = 0;
        }
    }

    /**
     * Sets the attack speed to a desired int.
     * @param theAtkSpeed The desired attack speed.
     */
    protected void setAtkSpeed(final int theAtkSpeed) {
        if (theAtkSpeed < 0) {
            throw new IllegalArgumentException("Attack speed passed to setAtkSpeed was negative.");
        }
        myAtkSpeed = theAtkSpeed;
    }

    /**
     * Sets the minimum damage to a desired int.
     * @param theMinDmg The desired minimum damage.
     */
    protected void setMinDmg(final int theMinDmg) {
        if (theMinDmg <= 0) {
            throw new IllegalArgumentException("Min damage must be greater than 0.");
        }
        myMinDmg = theMinDmg;
    }

    /**
     * Sets the maximum damage to a desired int.
     * @param theMaxDmg The desired maximum damage.
     */
    protected void setMaxDmg(final int theMaxDmg) {
        if (theMaxDmg <= 0) {
            throw new IllegalArgumentException("Max damage must be greater than 0.");
        }
        myMaxDmg = theMaxDmg;
    }

    /**
     * Sets the hit chance to a desired int.
     * @param theHitChance The desired hit chance.
     */
    protected void setHitChance(final int theHitChance) {
        if (theHitChance <= 0 || theHitChance > 100) {
            throw new IllegalArgumentException("Hit chance must be a positive number between 1 and 100.");
        }
        myHitChance = theHitChance;
    }

    /**
     * Sets the turns to a desired int.
     * @param theTurns is the desired turns.
     */
    protected void setTurns(final double theTurns) {
//        if (theTurns < 0) {
//            throw new IllegalArgumentException("Turns passed in must be positive.");
//        }
        myTurns = theTurns;
    }


    /**
     * Returns the name of the object.
     * @return the name field.
     */
    public String getName() {
        return myName;
    }

    /**
     * Returns the hit points of the object.
     * @return the hit points field.
     */
    public double getHitPoints() {
        return myHitPoints;
    }

    /**
     * Returns the attack speed of the object.
     * @return the attack speed field.
     */
    public double getAtkSpeed() {
        return myAtkSpeed;
    }

    /**
     * Returns the minimum damage of the object.
     * @return the minimum damage.
     */
    public double getMinDmg() {
        return myMinDmg;
    }

    /**
     * Returns the maximum damage of the object.
     * @return the maximum damage.
     */
    public double getMaxDmg() {
        return myMaxDmg;
    }

    /**
     * Returns the chance that the object will do damage to another.
     * @return the hit chance.
     */
    public double getHitChance() {
        return myHitChance;
    }

    /**
     * Returns the amount of times an object can attack in one round.
     * @return the turns.
     */
    public double getTurns() {
        return myTurns;
    }

    /**
     * Removes a randomly generated amount of hit points from the opposing object
     * from this object's min and max damage fields. This method run for the amount of turns that an object has.
     * @param opponent is the opposing object that will be losing hit points.
     */
    public void attack(final DungeonCharacter opponent) {
        while (getTurns() > 0) {
            double hitChanceCheck = generateRandomValue(0, 100);
            if (hitChanceCheck <= getHitChance()) {
                double atkDmg = generateRandomValue(myMinDmg, myMaxDmg);
                if (this instanceof Sorceress) {
                    playSound(magic);
                } else if (this instanceof Warrior) {
                    playSound(swordHit);
                } else if (this instanceof Thief) {
                    playSound(throwKnife);
                }
                opponent.subtractHitPoints(atkDmg);
                setTurns(getTurns() - 1);
            } else if (hitChanceCheck > getHitChance()) {
                System.out.println("\nHit missed!\n");
                setTurns(getTurns() - 1);
                playSound(attackMiss);
            }
        }
    }

    /**
     * Checks whether or not this object has > 0 hit points.
     * @return true if alive (hitpoints > 0) and false if dead (hitpoints <= 0).
     */
    public boolean alive() {
        return myHitPoints > 0;
    }

    /**
     * Subtracts hitpoints from an object.
     * This will set the hitpoints to 0 if this object's hitpoints are negative.
     * @param theHitPoints is the amount of hitpoints being subtracted from this object.
     */
    protected void subtractHitPoints(final double theHitPoints) {
        if (getHitPoints() - theHitPoints <= 0) {
            System.out.println("Killing Blow landed!\n*" + theHitPoints + "* damage");
            setHitPoints(0);
        } else {
            System.out.println("Attack Landed!\n*" + theHitPoints + "* damage\n");
            setHitPoints(getHitPoints() - theHitPoints);
        }
    }

    /**
     * Adds hitpoints to an object.
     * @param theHitPoints the desired amount of hitpoints to be added.
     */
    protected void addHitPoints(final double theHitPoints) {
        myHitPoints += theHitPoints;
    }

    /**
     * Checks whether or not the user inputted int is within the desired range.
     * This will keep running until the user inputs an in that is in the desired range.
     * @param console is the scanner that will be used for user input.
     * @param prompt is the prompt that the user is answering from.
     * @param minMenuInput the low end of the options on the menu.
     * @param maxMenuInput the high end of the options on the menu.
     * @return the user inputted int that is within the desired range.
     */
    public static int getIntInRange(final Scanner console, final String prompt,
                                    final int minMenuInput, final int maxMenuInput) {
        int input = getInt(console, prompt);
        while (input < minMenuInput || input > maxMenuInput) {
            System.out.print("Input out of range.\nPlease enter a number that corresponds to a menu prompt.\n");
            input = getInt(console, prompt);
        }
        return input;
    }

    /**
     * Checks to see whether the user inputted an int or not. The program will keep running if the
     * user input is not an int.
     * @param console is the scanner that will be used for user input.
     * @param prompt is the prompt that the user is answering from.
     * @return the user inputted int.
     */
    public static int getInt(final Scanner console, final String prompt) {
        System.out.println(prompt);
        while (!console.hasNextInt()) {
            console.next();
            System.out.println("Invalid input. Please enter an integer.");
            System.out.println(prompt);
        }
        return console.nextInt();
    }

    /**
     * Plays the sound from a WAV file that is passed in through the parameter.
     * @param theSound The WAV file that will be played.
     */
    protected void playSound(final File theSound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(theSound));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()/1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

