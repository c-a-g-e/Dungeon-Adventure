import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Room {

    /** Generates a random number. */
    protected static final Random RANDOM = new Random();

    private static SQLiteDataSource ds = null;

    /**
     * Generates a random int in the bounds of the parameters.
     * @param theLow is the lower bound of the possible number.
     * @param theHigh is the upper bound of the possible number.
     * @return the random number that was generated.
     */
    protected int generateRandomValue(final int theLow, final int theHigh) {
        return theLow + RANDOM.nextInt(theHigh - theLow + 1);
    }

    /** The x value of the room. */
    private int myX;
    /** The y value of the room. */
    private int myY;
    /** The monster in the room. */
    private Monster myMonster;
    /** True if healing potion is in the room. */
    private boolean myHealingPotion;
    /** True if a vision potion is in the room. */
    private boolean myVisionPotion;
    /** True if a crown piece is in the room. */
    private boolean myUsedVisionPotion;
    private boolean myPillar;
    private String myPillarName;
    /** True if there is a pit in the room. */
    private boolean myPit;
    /** True if the room is the entrance. */
    private boolean myEntrance;
    /** True if the room is the exit. */
    private boolean myExit;
    /** True if the room has been visited. */
    private boolean myVisited;
    /** True if the character is in the room. */
    private boolean myCharacter;
    /** Holds an int for the number of items in the room. */
    private int myNumberOfItems;
    public static double myDifficultyWeight;

    /**
     * Gets the x value of the room.
     * @return an int for the x value of the room in the 2d dungeon array.
     */
    public int getMyX() {
        return myX;
    }

    /**
     * Sets the x value of the room to a number in the bounds of the Dungeon.
     * @param theX is the value that the myX will be changed to.
     */
    public void setMyX(final int theX) {
        if (theX < 0 || theX >= Dungeon.MY_COLUMNS) {
            throw new IndexOutOfBoundsException("X not in bounds.");
        }
        myX = theX;
    }

    /**
     * Gets the y value of the room.
     * @return an int for the y value of the room in the 2d Dungeon array.
     */
    public int getMyY() {
        return myY;
    }
    /**
     * Sets the y value of the room to a number in the bounds of the Dungeon.
     * @param theY is the value that the myY will be changed to.
     */
    public void setMyY(final int theY) {
        if (theY < 0 || theY >= Dungeon.MY_ROWS) {
            throw new IndexOutOfBoundsException("Y not in bounds.");
        }
        myY = theY;
    }
    /** Sets a room to contain the entrance. */
    protected void setMyEntrance() {
        myEntrance = true;
    }

    /**
     * Determines if the room is the entrance.
     * @return true if the room is an entrance.
     */
    public boolean getMyEntrance() {
        return myEntrance;
    }
    /** Sets a room to contain the exit. */
    protected void setMyExit() {
        myExit = true;
    }

    /**
     * Determines if a room is the exit.
     * @return true if the room is the exit.
     */
    public boolean getMyExit() {
        return myExit;
    }
    /** True if the room contains a pit.
     * @return True if the room contains a pit.
     */
    public boolean getMyPit() {
        return myPit;
    }
    /** Indicates that a room has been visited by the hero. */
    private void setMyVisited() {
        myVisited = true;
    }

    /**
     * Determines if the room has been visited by the hero.
     * @return true if the room has been visited by the hero.
     */
    public boolean getMyVisited() {
        return myVisited;
    }

    /**
     * Determines if the room contains a monster.
     * @return which monster the room contains, null if no monster.
     */
    public Monster getMyMonster() {
        return myMonster;
    }

    /**
     * Sets the room's monster to a monster object.
     * @param theMonster the monster object that will be contained in the room.
     */
    public void setMyMonster(final Monster theMonster) { myMonster = theMonster; }

    /**
     * Determines the number of items in a room.
     * @return the int number of items in the room.
     */
    public int getMyNumberOfItems() {
        return myNumberOfItems;
    }

    /**
     * Sets the number of item to an int value.
     * @param theNumberOfItems the int value of items that the room will contain.
     */
    protected void setMyNumberOfItems(final int theNumberOfItems) {
        myNumberOfItems = theNumberOfItems;
    }

    /**
     * Sets the room to true if the room contains a Crown Piece.
     * @param theBool true if the room is to contain a Crown Piece and false if it isn't.
     */
    protected void setMyPillar(final boolean theBool) {
        myPillar = theBool;
    }

    /**
     * Determines if the room has a crown piece.
     * @return true if the room contains a crown piece, false if not.
     */
    public boolean getMyPillar() { return myPillar; }

    /**
     * Assigns a string to the myPillarName field
     * @param theName the name of the pillar
     */
    public void setMyPillarName(String theName) { myPillarName = theName; }

    public String getMyPillarName() {
        return myPillarName;
    }
    /**
     * Sets myCharacter to true if the hero is present in the room.
     * @param theBool is the boolean that will determine if the hero is in the room or not.
     */
    protected void setMyCharacter(final boolean theBool) {
        myCharacter = theBool;
    }

    /**
     * Sets myHealingPotion to true if there is a healing potion in the room.
     * @param theBool is true if there will be a healing potion in the room. False if it is being removed.
     */
    public void setMyHealingPotion(final boolean theBool) {
        myHealingPotion = theBool;
    }

    /**
     * Determines if there is a healing potion in the room.
     * @return true if there is a potion present in the room, false if not.
     */
    public boolean getMyHealingPotions() {
        return myHealingPotion;
    }
    /**
     * Sets myVisionPotion to true if there is a vision potion in the room.
     * @param theBool is true if there will be a vision potion in the room. False if it is being removed.
     */
    public void setMyVisionPotions(final boolean theBool) {
        myVisionPotion = theBool;
    }
    /**
     * Determines if there is a vision potion in the room.
     * @return true if there is a potion present in the room, false if not.
     */
    public boolean getMyVisionPotions() {
        return myVisionPotion;
    }
    /**
     * Determines whether there is a vision potion in the room object.
     * @param theBool true if there is a vision potion in the room.
     */
    public void setMyUsedVisionPotion(final boolean theBool) {
        myUsedVisionPotion = theBool;
    }


    /**
     * The constructor for the room object. This creates a room and generates pits, monsters, and potions.
     * @param theX is the x value of the room.
     * @param theY is the y value of the room.
     */
    public Room(final int theX, final int theY) {
        setMyX(theX);
        setMyY(theY);
        myPit = generateRandomValue(0,100) <= 10;
        if (!myPit && !myEntrance && !myExit) {
            if (generateRandomValue(0, 100) <= 10) {
                myMonster = monsterSelection();
            }
            myHealingPotion = generateRandomValue(0, 100) <= 10;
            myVisionPotion = generateRandomValue(0, 100) <= 5;
        }
    }

    /**
     * Randomly selects one of the three monsters and creates a monster object.
     * @return the monster object that is selected.
     */
    private Monster monsterSelection() {
        connectToDB();
        int randomMonster = generateRandomValue(1, 3);
        Monster monster;
        if (randomMonster == 1) {
            monster = createMonster("Ogre");
        } else if (randomMonster == 2) {
            monster = createMonster("Gremlin");
        } else {
            monster = createMonster("Skeleton");
        }
        return monster;
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

    protected static Monster createMonster(String theMonster) {
        int[] params = new int[8];
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT HitPoints, AttackSpeed, MinDamage, MaxDamage, " +
                    "HitChance, HealChance, MinHeal, MaxHeal " +
                    "FROM Monsters " +
                    "WHERE Class = " +
                    "'" + theMonster + "'");
            params[0] = rs.getInt("HitPoints");
            params[1] = rs.getInt("AttackSpeed");
            params[2] = rs.getInt("MinDamage");
            params[3] = rs.getInt("MaxDamage");
            params[4] = rs.getInt("HitChance");
            params[5] = rs.getInt("HealChance");
            params[6] = rs.getInt("MinHeal");
            params[7] = rs.getInt("MaxHeal");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return new Monster(theMonster, params[0] * myDifficultyWeight,
                params[1] * myDifficultyWeight,
                params[2] * myDifficultyWeight,
                params[3] * myDifficultyWeight,
                params[4] * myDifficultyWeight,
                params[5] * myDifficultyWeight,
                params[6] * myDifficultyWeight,
                params[7] * myDifficultyWeight);
    }

    /** Marks a room as visited. */
    public void markVisited() {
        setMyVisited();
    }

    /**
     * Checks if a room has no items in it.
     * @return true if there are no items in the room.
     */
    public boolean isEmpty() {
        return countNumberOfItems() == 0;
    }

    /** Sets all of the possible items in the room as false or null, effectively emptying it. */
    public void setEmpty() {
        myMonster = null;
        myHealingPotion = false;
        myVisionPotion = false;
        myPit = false;
        myPillar = false;
    }

    /**
     * Counts the number of monsters, potions, and crown pieces in a room.
     * @return the integer value of the number of items in the room.
     */
    private int countNumberOfItems() {
        int count = 0;
        if (myHealingPotion) {
            count++;
        }
        if (myVisionPotion) {
            count++;
        }
        if (myPillar) {
            count++;
        }
        if (myMonster != null) {
            count++;
        }
        if (myPit) {
            count = 1;
        }
        if (myEntrance || myExit) {
            count = 1;
        }
        return count;
    }

    /**
     * Determines which item(s) are being contained in the room.
     * @return A String representing what is in the room.
     */
    private String containsItem() {
        String item = "";
        if (!myCharacter) {
            if (myVisited || myUsedVisionPotion) {
                if (countNumberOfItems() == 1) {
                    if (myEntrance) {
                        item = "i";
                    } else if (myExit) {
                        item =  "O";
                    } else if (myPit) {
                        item =  "S";
                    } else if (myHealingPotion) {
                        item =  "H";
                    } else if (myVisionPotion) {
                        item =  "V";
                    } else if (myMonster != null) {
                        item =  "X";
                    } else if (myPillar) {
                        item = switch (myPillarName) {
                            case "Abstraction" -> "A";
                            case "Encapsulation" -> "E";
                            case "Inheritance" -> "I";
                            case "Polymorphism" -> "P";
                            default -> item;
                        };
                    }
                } else if (countNumberOfItems() == 0) {
                    item =  " ";
                } else {
                    item =  "" + countNumberOfItems() + "";
                }
            } else {
                return "?";
            }
        } else {
            item =  "*";
        }
        return item;
    }

    /**
     * Constructs a string that visually represents a room object.
     * @return the string representation of the room object.
     */
    public String toString() {
        String room;
        String item = containsItem();
        if (item.equals("M")) {
            item = String.valueOf(countNumberOfItems());
        }
        if (getMyX() == 0) {
            room = "| " + item + " :";
        } else if (getMyX() == Dungeon.MY_COLUMNS - 1) {
            room = " " + item + " |";
        } else {
            room = " " + item + " :";
        }
        return room;
    }

    /**
     * Prints what objects are in the rooms surrounding the room that the character is in. (This
     * is activated when a vision potion is used).
     */
    public void printVisionPotion() {
        switch (containsItem()) {
            case "i":
                if (myVisited) {
                    System.out.print("The entrance of the maze lies in this room. There is no turning back, coward. ");
                } else {
                    System.out.print("You are at the entrance of the maze. Collect all of the crown pieces and find the exit to escape.");
                }
                break;
            case "O":
                if (myVisited) {
                    System.out.print("Find the crown pieces to escape.");
                } else {
                    System.out.print("This room contains the exit of the maze. However, there is a barrier that " +
                            "\nprevents you from leaving without all three of the crown pieces.");
                }
                break;
            case "S":
                System.out.print("This room contains a punji filled spike pit. You don't want to fall in there...");
                break;
            case "H":
                System.out.print("A healing potion lies alone in this quiet, oddly serene room.");
                break;
            case "V":
                System.out.print("A vision potion can be found in a dusty chest tucked away in the corner of this room.");
                break;
            case "X":
                System.out.print("An unknown monster lurks in the dark corners of this room.");
                break;
            case "A":
            case "E":
            case "I":
            case "P":
                System.out.print("One of the four Pillars of OO lays suspiciously in the center of the room.");
                break;
            case " ":
                System.out.print("A seemingly empty room. Although, it's best to not drop your guard.");
                break;
            case "M":
                System.out.print("This room contains more than one thing: ");
                if (myHealingPotion) {
                    System.out.print("\n\tA healing potion.");
                } else if (myVisionPotion) {
                    System.out.print("\n\tA vision potion.");
                } else if (myPillar) {
                    System.out.print("\n\tA Pillar of OO.");
                } else if (myMonster != null) {
                    System.out.print("\n\tA deadly monster.");
                }
                break;
        }
    }

}

