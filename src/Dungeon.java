import java.util.Random;
import java.util.Scanner;

public class Dungeon {

    /** The number of rows in the 2d dungeon array. */
    public static final int MY_ROWS = 5;
    /** The number of columns in the 2d dungeon array. */
    public static final int MY_COLUMNS = 5;

    /** Generates a random integer. */
    public static final Random RANDOM = new Random();

    public static String[] myPillars = {"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};
    public static double myDifficultyWeight;

    /**
     * Generates a random value from a low to a high.
     * @param theLow is the lowest possible integer.
     * @param theHigh is the highest possible integer.
     * @return the integer that is randomly generated.
     */
    public static int generateRandomValue(final int theLow, final int theHigh) {
        return theLow + RANDOM.nextInt(theHigh - theLow + 1);
    }

    /**
     * Generates a 2d array filled with room objects.
     * @param theHero the hero that will navigate the dungeon.
     * @return the dungeon that is created.
     */
    //TODO improve implementation of difficulty and test
    public static Room[][] generateDungeon(final Hero theHero) {
        Room[][] maze = new Room[MY_ROWS][MY_COLUMNS];
        for (int row = 0; row < MY_ROWS; row++) {
            for (int column = 0; column < MY_COLUMNS; column++) {
                maze[row][column] = new Room(column, row);
                Room.myDifficultyWeight = myDifficultyWeight;
            }
        }
        findValidEntrancePoint(maze, theHero);
        createExit(maze);
        generatePillars(maze);
        return maze;
    }

    /**
     * Finds a room on the perimeter of the dungeon that can contain an entrance.
     * @param theDungeon the dungeon that will be used.
     * @param hero the hero that will navigate the dungeon.
     */
    private static void findValidEntrancePoint(final Room[][] theDungeon, final Hero hero) {
        int entranceHeading = generateRandomValue(1, 4);
        int randRow = generateRandomValue(0,MY_ROWS - 1);
        int randCol = generateRandomValue(0,MY_COLUMNS - 1);
        if (entranceHeading == 1) {
            createEntrance(theDungeon, 0, randCol);
            placeHeroAtEntrance(hero, randCol, 0);
        } else if (entranceHeading == 2) {
            createEntrance(theDungeon, randRow, MY_COLUMNS - 1);
            placeHeroAtEntrance(hero, MY_COLUMNS - 1, randRow);
        } else if (entranceHeading == 3) {
            createEntrance(theDungeon, MY_ROWS - 1, randCol);
            placeHeroAtEntrance(hero, randCol, MY_ROWS - 1);
        } else {
            createEntrance(theDungeon, randRow, 0);
            placeHeroAtEntrance(hero, 0, randRow);
        }
    }

    /**
     * Creates the entrance to the maze by emptying the room, making it the entrance,
     * and putting the character there to start.
     * @param theDungeon the dungeon that will be used.
     * @param theY is the y value of the room in the 2d dungeon array.
     * @param theX is the x value of the room in the 2d dungeon array.
     */
    private static void createEntrance(final Room[][] theDungeon, final int theY, final int theX) {
        theDungeon[theY][theX].setEmpty();
        theDungeon[theY][theX].setMyEntrance();
        theDungeon[theY][theX].setMyCharacter(true);
    }

    /**
     * Places the character in the room of the dungeon that was made the entrance.
     * @param theHero is the hero that will be placed at the entrance.
     * @param theX is the x (column) of the dungeon that the entrance is located.
     * @param theY is the y (row) of the dungeon that the entrance is located.
     */
    private static void placeHeroAtEntrance(final Hero theHero, final int theX, final int theY) {
        theHero.setMyRoomY(theY);
        theHero.setMyRoomX(theX);
    }

    /**
     * Creates the exit on the perimeter of the dungeon, which will not be in the same room as the entrance.
     * @param theDungeon is the dungeon that the exit will be created in.
     */
    private static void createExit(final Room[][] theDungeon) {
        int exitHeading = generateRandomValue(1, 4);
        int randRow = generateRandomValue(0,MY_ROWS - 1);
        int randCol = generateRandomValue(0,MY_COLUMNS - 1);
        if (exitHeading == 1) {
            while (theDungeon[0][randCol].getMyEntrance()) {
                randCol = generateRandomValue(0, MY_COLUMNS - 1);
            }
            theDungeon[0][randCol].setMyExit();
        } else if (exitHeading == 2) {
            while (theDungeon[randRow][4].getMyEntrance()) {
                randRow = generateRandomValue(0, MY_ROWS - 1);
            }
            theDungeon[randRow][4].setMyExit();
        } else if (exitHeading == 3) {
            while (theDungeon[4][randCol].getMyEntrance()) {
                randCol = generateRandomValue(0, MY_COLUMNS - 1);
            }
            theDungeon[4][randCol].setMyExit();
        } else {
            while (theDungeon[randRow][0].getMyEntrance()) {
                randRow = generateRandomValue(0, MY_ROWS - 1);
            }
            theDungeon[randRow][0].setMyExit();
        }
    }

    /**
     * Generates three crown pieces in different rooms that do not contain the entrance,
     * exit, a pit, or another crown piece.
     * @param theDungeon is the dungeon that the crown pieces will be contained in.
     */
    //TODO change crown pieces to pillars of OO
    private static void generatePillars(final Room[][] theDungeon) {
        int randRow = generateRandomValue(0, MY_ROWS - 1);
        int randCol = generateRandomValue(0, MY_COLUMNS - 1);
        for (int i = 0; i < 4; i++) {
            while (theDungeon[randRow][randRow].getMyEntrance() ||
                    theDungeon[randRow][randRow].getMyExit() ||
                    theDungeon[randRow][randCol].getMyPit() ||
                    theDungeon[randRow][randCol].getMyPillar()) {
                randRow = generateRandomValue(0, MY_ROWS - 1);
                randCol = generateRandomValue(0, MY_COLUMNS - 1);
            }
            theDungeon[randRow][randCol].setMyPillar(true);
            theDungeon[randRow][randCol].setMyPillarName(myPillars[i]);
        }
    }

    /**
     * Moves the character north, south, west, or east depending on the user input.
     * @param theDungeon is the dungeon that the hero resides in.
     * @param theHero is the hero that will be moved.
     * @param theMove is the direction that the hero will move in.
     */
    public static void move(final Room[][] theDungeon, final Hero theHero, final String theMove) {
        if (validMove(theHero, theMove)) {
            if (theMove.equalsIgnoreCase("n")) {
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(false);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].markVisited();
                theHero.setMyRoomY(-1);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(true);
            } else if (theMove.equalsIgnoreCase("w")) {
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(false);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].markVisited();
                theHero.setMyRoomX(-1);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(true);
            } else if (theMove.equalsIgnoreCase("s")) {
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(false);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].markVisited();
                theHero.setMyRoomY(1);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(true);
            } else if (theMove.equalsIgnoreCase("e")) {
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(false);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].markVisited();
                theHero.setMyRoomX(1);
                theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()].setMyCharacter(true);
            }
        } else {
            System.out.println("You cannot move there.");
        }
    }

    /**
     * Checks whether or not the move that the user wants to make is in the bounds of the dungeon.
     * @param theHero is the hero that will be moved.
     * @param theMove is the direction that the user would like to move.
     * @return whether or not the move is valid.
     */
    private static boolean validMove(final Hero theHero, final String theMove) {
        boolean result = true;
        if (theMove.equalsIgnoreCase("n")) {
            if (theHero.getMyRoomY() - 1 < 0) {
                result = false;
            }
        } else if (theMove.equalsIgnoreCase("w")) {
            if (theHero.getMyRoomX() - 1 < 0) {
                result = false;
            }
        } else if (theMove.equalsIgnoreCase("s")) {
            if (theHero.getMyRoomY() + 1 > MY_ROWS - 1) {
                result = false;
            }
        } else if (theMove.equalsIgnoreCase("e")) {
            if (theHero.getMyRoomX() + 1 > MY_COLUMNS - 1) {
                result = false;
            }
        }
        return result;
    }

    //add sneak chance when there is a monster. different heroes have different chances, cannot pick up items in room if sneak is successful.
    //could also add run away. if hero runs away, he returns to the previous room and the monster stays there.
    /**
     * Determines what the character does depending on what is in the room. If there is a monster,
     * the hero will fight it, if there are items, the hero will pick them up and it will update
     * the hero and the room class, and if the exit is present and the hero has all of the crown
     * pieces, the player will have the option to end the game.
     * @param theDungeon is the dungeon that contains the explorable rooms.
     * @param theHero is the hero that will explore the rooms.
     */
    public static void exploreRoom(final Room[][] theDungeon, final Hero theHero) {
        Room theRoom = theDungeon[theHero.getMyRoomY()][theHero.getMyRoomX()];
        if (theRoom.isEmpty()) {
            System.out.println("You stumbled upon a seemingly empty room. No time to drop your guard...");
        }
        if (theRoom.getMyMonster() != null) {
            String monsterName = theRoom.getMyMonster().getName();
            System.out.println("You have stumbled upon an evil " + monsterName + "!");
            System.out.println("The " + monsterName + " charges at you with haste and you break out in battle!");
            DungeonAdventure.battle(theHero, theRoom.getMyMonster());
            if (theHero.alive()) {
                theRoom.setMyMonster(null);
            }
        }
        if (theRoom.getMyHealingPotions()) {
            theHero.setMyHealthPotions(theHero.getMyHealthPotions() + 1);
            System.out.println("You find a healing potion. You have: " + theHero.getMyHealthPotions());
            theRoom.setMyHealingPotion(false);
        }
        if (theRoom.getMyVisionPotions()) {
            theHero.setMyVisionPotions(theHero.getMyVisionPotions() + 1);
            System.out.println("You find a vision potion. You have: " + theHero.getMyVisionPotions());
            theRoom.setMyVisionPotions(false);
        }
        if (theRoom.getMyPillar()) {
            theHero.setMyCrownPieces(theHero.getMyCrownPieces() + 1);
            theRoom.setMyPillar(false);
            if (theHero.getMyCrownPieces() == 3) {
                System.out.println("""

                        You have found all of the crown pieces. Make your way to the exit
                        to escape the dungeon.""");
            } else {
                System.out.println("You found a crown piece! Only " +
                        (3 - theHero.getMyCrownPieces()) +
                        " pieces to go!");
            }
        }
        if (theRoom.getMyPit()) {
            int pitDamage = generateRandomValue(1, 20);
            System.out.println("You walked into a hidden spike pit. and lost " + pitDamage + " health points.");
            theHero.subtractHitPoints(pitDamage);
        }
        if (theRoom.getMyExit()) {
            if (theHero.getMyCrownPieces() == 3) {
                Scanner input = new Scanner(System.in);
                String response = "";
                System.out.println("""
                        
                        You see the exit on the other side of the room and the crown pieces fit
                        perfectly into the slot on the door. It creaks open slowly, revealing a tunnel with
                        a hint of sunlight peaking through. You turn and look at the dungeon. Something
                        is causing you to want to explore more. Turn around and explore the dungeon more? (Y/N)""");
                if (input.hasNext()) {
                    response = input.next();
                    while (!("y".equalsIgnoreCase(response) || "n".equalsIgnoreCase(response))) {
                        response = input.next();
                    }
                    if ("n".equalsIgnoreCase(response)) {
                        theHero.setMyEscaped(true);
                    }
                }
            } else {
                System.out.println("""
                         
                         You find what looks like an exit, but you notice the door is locked shut.
                         The door has a large cutout that resembles a crown. Maybe that's what the
                         crown pieces are for...
                        """);
            }
        }
    }

    /**
     * Prints out the maze in its current state by printing out each individual room as
     * well as a top line, dividing lines, and a bottom line.
     * @param theDungeon is the dungeon that will be printed.
     */
    public static void printDungeon(final Room[][] theDungeon) {
        System.out.print("_____________________\n");
        for (int row = 0; row < MY_ROWS; row++) {
            for (int column = 0; column < MY_COLUMNS; column++) {
                System.out.print(theDungeon[row][column].toString());
            }
            if (row != MY_ROWS - 1) {
                System.out.print("\n+- -+- -+- -+- -+- -+\n");
            }
        }
        System.out.print("\n---------------------\n");
    }

}
