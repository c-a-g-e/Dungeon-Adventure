/*
 * DungeonAdventure.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */
import org.sqlite.SQLiteDataSource;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Runs the heroes vs monsters game that puts a hero object against a monster object against
 * each other in battle.
 *
 * @author Cage Peterson
 * @version 3/18/2021
 */
public class DungeonAdventure {

    private static SQLiteDataSource ds = null;

    /**
     * Runs the game until the uer quits or loses and gives the user the option to replay the game.
     * @param args is the default param.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        displayIntro();
        Hero hero;
        Room[][] dungeon;
        do {
            selectDifficulty(input);
            hero = heroSelection(input);
            dungeon = Dungeon.generateDungeon(hero);
            System.out.println(hero.getName() + " takes a deep breath and enters the dark, damp dungeon.");
            while (!hero.getMyEscaped() && !hero.getRunAway() && hero.alive()) {
                Dungeon.printDungeon(dungeon);
                command(input, hero, dungeon);
            }
        } while (repeat(input));
        exitMessage();
        input.close();
    }

    /** The sound file for hero victory. */
    private final static File victory = new File("res/victory.wav");

    /** The sound file for hero defeat. */
    private final static File defeat = new File("res/defeat.wav");

    /**
     * The sound file for running away. (It sounds a bit like the character is falling down
     * a pit, but he's running away, I promise ;).
     */
    private final static File runAway = new File("res/runAway.wav");

    /**
     * Plays the sound from a WAV file that is passed in through the parameter.
     * @param theSound The WAV file that will be played.
     */
    public static void playSound(final File theSound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(theSound));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()/1000);
        } catch (Exception e) {
            //noinspection ThrowablePrintedToSystemOut
            System.out.println(e);
        }
    }

    /** Displays the title and the intro method for the program. */
    private static void displayIntro() {
        String intro = """
                ------------------------The Catacombs of Tartarus------------------------

                   Welcome to Tartarus. This dungeon will test your abilities as well as
                   your courage. To escape, you must find all four Pillars of OO, which serve as a key
                   to escape. You will be met with furious beasts and other challenges along the way.
                   
                   Good luck; you will need it.

                """;

        System.out.print(intro);

    }

    /**
     * Prompts the user to input a name for their hero.
     * @param theInput The scanner that will be used.
     * @return The name that is inputted.
     */
    private static String promptForName(final Scanner theInput) {
        String s;
            do {
                System.out.println("\nWhat is your name, hero?");
                s = theInput.next();
            } while (s == null);
        return s;
    }

    /**
     * Gives the user information about each hero and allows them to choose which one to play as.
     * @param theInput The scanner that will be used.
     * @return The hero object that could either be Warrior, Sorceress, or Thief depending on what the
     * user chooses.
     */
    private static Hero heroSelection(final Scanner theInput) {
        String heroMenu = """

                                Which Hero would you like to battle as?
                                
                                      |   Warrior   |   Sorceress   |   Thief   |
                        Health Points |     125     |      75       |    75     |
                        Attack Speed  |      4      |       5       |     6     |
                        Hit Chance    |     80%     |      70%      |    80%    |
                        Minimum Damage|     35      |      25       |    40     |
                        Maximum Damage|     60      |      45       |    45     |
                        Block Chance  |     20%     |      30%      |    40%    |
                        Special Move  |  Crushing   |     Heal      | Surprise  |
                                      |    Blow     |               |  Attack   |
                                      |Deals 75-175 |Heals for 20HP |If success-|
                                      |damage with a|               |ful, Thief |
                                      |40% hit      |               |gets an ex-|
                                      |chance.      |               |tra attack,|
                                      |             |               |if not, thi|
                                      |             |               |ef loses   |
                                      |             |               |attack for |
                                      |             |               |the current|
                                      |             |               |turn.      |

                        """;
        System.out.print(heroMenu);
        String heroPrompt = """
                Enter (1) to play as the Warrior
                Enter (2) to play as the Sorceress
                Enter (3) to play as the Thief """;
        int response = DungeonCharacter.getIntInRange(theInput, heroPrompt, 1, 3);
        String name = promptForName(theInput);
        Hero hero;
        connectToDB();
        if (response == 1) {
            hero = createHero(name, "Warrior");
        } else if (response == 2) {
            hero = createHero(name, "Sorceress");
        } else {
            hero = createHero(name, "Thief");
        }
        return hero;
    }

    private static void selectDifficulty(final Scanner theInput) {
        String difficultyPrompt = """
                                
                What difficulty would you like to play on?
                                
                Enter (1) to play on Easy
                Enter (2) to play on Normal (Recommended)
                Enter (3) to play on Hard
                """;
        int response = DungeonCharacter.getIntInRange(theInput, difficultyPrompt, 1, 3);
        if (response == 1) {
            Dungeon.setMyDifficultyWeight(5);
        } else if (response == 2) {
            Dungeon.setMyDifficultyWeight(15);
        } else {
            Dungeon.setMyDifficultyWeight(30);
        }
    }


    protected static void connectToDB() {
        //establish connection
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:DungeonCharacter.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    protected static Hero createHero(String theName, String theHero) {
        int[] params = new int[7];
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT HitPoints, AttackSpeed, MinDamage, MaxDamage, " +
                    "HitChance, BlockChance, SpecialSkillChance " +
                    "FROM Heroes " +
                    "WHERE Class = " +
                    "'" + theHero + "'");
            params = new int[7];
            params[0] = rs.getInt("HitPoints");
            params[1] = rs.getInt("AttackSpeed");
            params[2] = rs.getInt("MinDamage");
            params[3] = rs.getInt("MaxDamage");
            params[4] = rs.getInt("HitChance");
            params[5] = rs.getInt("BlockChance");
            params[6] = rs.getInt("SpecialSkillChance");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        if (theHero.equals("Warrior")) {
            return new Warrior(theName, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        } else if (theHero.equals("Sorceress")) {
            return new Sorceress(theName, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        } else {
            return new Thief(theName, params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        }
    }


    /**
     * Carries out the commands that the user inputs each turn.
     * @param theInput is the input for the command that the user wants to carry out.
     * @param theHero is the hero that will be effected by the command.
     * @param theDungeon is the dungeon that the command will be carried out in.
     */
    private static void command(final Scanner theInput, final Hero theHero, final Room[][] theDungeon) {
        String response = getValidCommand(theInput);
        if ("c".equalsIgnoreCase(response)) {
            displayMenu();
        }
        if ("n".equalsIgnoreCase(response) || "north".equalsIgnoreCase(response)) {
            Dungeon.move(theDungeon, theHero, "n");
            Dungeon.exploreRoom(theDungeon, theHero);
        } else if ("s".equalsIgnoreCase(response) || "south".equalsIgnoreCase(response)) {
            Dungeon.move(theDungeon, theHero, "s");
            Dungeon.exploreRoom(theDungeon, theHero);
        } else if ("w".equalsIgnoreCase(response) || "west".equalsIgnoreCase(response)) {
            Dungeon.move(theDungeon, theHero, "w");
            Dungeon.exploreRoom(theDungeon, theHero);
        } else if ("e".equalsIgnoreCase(response) || "east".equalsIgnoreCase(response)) {
            Dungeon.move(theDungeon, theHero, "e");
            Dungeon.exploreRoom(theDungeon, theHero);
        } else if ("i".equalsIgnoreCase(response) || "info".equalsIgnoreCase(response)) {
            theHero.printInventory();
        } else if ("m".equalsIgnoreCase(response) || "map".equalsIgnoreCase(response)) {
            Dungeon.printDungeon(theDungeon);
        } else if ("k".equalsIgnoreCase(response) || "key".equalsIgnoreCase(response)) {
            printKey();
        } else if ("p".equalsIgnoreCase(response) || "heal".equalsIgnoreCase(response)) {
            if (theHero.getMyHealthPotions() > 0) {
                theHero.useHealingPotion();
            } else {
                System.out.println("You do not have any healing potions.");
            }
        } else if ("v".equalsIgnoreCase(response) || "vision".equalsIgnoreCase(response)) {
            if (theHero.getMyVisionPotions() > 0) {
                theHero.useVisionPotion(theDungeon);
            } else {
                System.out.println("You do not have any vision potions.");
            }
        } else if ("q".equalsIgnoreCase(response) || "quit".equalsIgnoreCase(response)) {
            theHero.setMyEscaped(true);
        } else if ("z".equalsIgnoreCase(response)) {
            Dungeon.displayFullDungeon(theDungeon);
        } else if ("x".equalsIgnoreCase(response)) {
            theHero.setHitPoints(10000);
        } else if ("l".equalsIgnoreCase(response)) {
            theHero.setHitPoints(1);
        }

    }

    /**
     * Ensures that the command being entered is valid.
     * @param theInput is the user's command input.
     * @return the user's validated input.
     */
    private static String getValidCommand(final Scanner theInput) {
        String response = "";
        String[] validCommands = {"c", "commands", "n", "north", "s", "south", "w", "west", "e", "east", "i", "info",
                "r", "room", "m", "map", "p", "heal", "v", "vision", "q", "quit", "z"};
        while ("".equals(response)) {
            System.out.println("Please enter a command (c for commands): ");
            if (theInput.hasNext()) {
                response = theInput.next();
            }
            for (String s : validCommands) {
                if (response.equalsIgnoreCase(s)) {
                    response = s;
                    break;
                }
            }
        }
        return response;
    }

    /**
     * Puts the chosen hero and monster against each other until one of them "dies" (loses all hitpoints).
     * @param theHero The hero object that will fight.
     * @param theMonster The monster object that will fight.
     */
    public static void battle(final Hero theHero, final Monster theMonster) {
        while (theHero.alive() && theMonster.alive() && !theHero.getRunAway()) {
            System.out.println("\n____________Hero Attack____________");
            System.out.println(theHero.getName() + "'s Hit Points: " +theHero.getHitPoints());
            System.out.println(theMonster.getName() + "'s Hit Points: " + theMonster.getHitPoints() + "\n");
            theHero.attack(theMonster);
            if (theMonster.alive() && !theHero.getRunAway()) {
                System.out.println("\n____________Monster Attack____________");
                System.out.println(theHero.getName() + "'s Hit Points: " + theHero.getHitPoints());
                System.out.println(theMonster.getName() + "'s Hit Points: " + theMonster.getHitPoints() + "\n");
                theMonster.attack(theHero);
            }
        }
        if (!theHero.alive()) {
            System.out.println(theHero.getName() + " has fallen.");
            playSound(defeat);
            System.out.println(theMonster.getName() + " has defeated the mighty " + theHero.getName() + " with\n" +
                    theMonster.getHitPoints() + " hitpoints remaining.");
        } else if (!theMonster.alive()) {
            System.out.println(theMonster.getName() + " has fallen.");
            playSound(victory);
            System.out.println(theHero.getName() + " has defeated the lowly " + theMonster.getName() + " with\n" +
                    theHero.getHitPoints() + " hitpoints remaining.");
        } else if (!theHero.getRunAway()) {
            System.out.println(theHero.getName() + " has run away.");
            playSound(runAway);
        }
    }

    /**
     * Displays the menu for all of the command options.
     */
    private static void displayMenu() {
        String menu = """
                Actions Menu:
                
                    Movement:
                        (n or north) move your hero north.
                        (s or south) move your hero south.
                        (e or east) move your hero east.
                        (w or west) move your hero west.
                    Hero:
                        (i or info) display the status of your hero.
                        (m or map) display a map of the dungeon.
                        (k or key) display a key for the map.
                    Inventory:
                        (p or heal) to use a healing potion.
                        (v or vision) to use a vision potion.
                    Game:
                        (q or quit) to abandon the dungeon.
                """;
        System.out.println(menu);
    }

    /**
     * Prints the legend/key for the room labels.
     */
    private static void printKey() {
        System.out.println("""
                
                Below is a key for the chambers found in Tartarus:
                    i: The entrance of the dungeon.
                    O: The exit of the dungeon.
                    P, A, I, E: The chamber contains a Pillar of OO (Polymorphism, Abstraction, Inheritance, Encapsulation).
                    H: The chamber contains a health potion.
                    V: The chamber contains a vision potion.
                    P: The chamber contains a deadly pit.
                    X: In the chamber lurks a deadly monster.
                    # (2-4) : There are multiple items in this chamber. Explore it to find out more.
                """);
    }

    /**
     * Asks the user if they would like to repeat the program.
     * @param theInput The scanner that will be used.
     * @return Returns true if the user would like to repeat, false if the user would like to quit.
     */
    private static boolean repeat(final Scanner theInput) {
        System.out.println("\nWould you like to play again? (Y/N)");
        String s = theInput.next();
        System.out.println();
        return (s.equalsIgnoreCase("Y") || s.equalsIgnoreCase ("yes"));
    }

    /** Displays the exit message. */
    private static void exitMessage() {
        System.out.println("""

                Game Over
                Thank you for playing!""");

    }
}
