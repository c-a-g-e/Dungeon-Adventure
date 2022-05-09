/*
 * Thief.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */

/**
 * Specifies the unique stats and methods for the Thief Hero.
 */
public class Thief extends Hero{

    /**
     * A constructor method for the unique Thief Hero.
     * @param theName is the name of the Thief.
     */
    protected Thief(final String theName) {
        super(theName, 75, 6, 20, 40,
                80, 40, 40);
    }

    /**
     * Checks if the specialAbility is successful. There is a 40% chance that the Thief will
     * gain an extra turn for the round, a 40% chance that the Thief won't be affected,
     * and a 20% chance that the Thief will lose their turn for that round.
     * @return the result of the check in string form.
     */
    private String checkSpecial() {
        String s;
        playSound(thiefSpecial);
        System.out.println(getName() + " is attempting their special move.");
        int checkSpecial = generateRandomValue(0, 100);
        if (checkSpecial <= 40) {
            setTurns(getTurns() + 1);
            System.out.println(getName() + " gained another attack for this turn!");
            s = "succeeded";
        } else if (checkSpecial > 40 && checkSpecial <= 80) {
            setTurns(getTurns());
            System.out.println("Special move failed.");
            s = "failed";
        } else {
            setTurns(0);
            System.out.println("Special move backfired!" +
                    "\n" + getName() + " doesn't get to attack this round.");
            s = "backfired";
        }
        return s;
    }

    /**
     * Gives the Thief the ability to use their special attack.
     * After using their attack (assuming they didn't lose a turn because of it), the user
     * will be able to choose to attack or run away.
     * @param opponent is the opponent the hero is facing.
     */
    @Override
    protected void specialAbility(final DungeonCharacter opponent) {
        String s = checkSpecial();
        String attackPrompt = """

                What would you like to do next?

                Enter (1) to use your normal attack
                Enter (3) to run away
                """;
        if (s.equals("backfired")) {
        } else {
            int response;
            do {
                response = getIntInRange(console, attackPrompt, 1, 3);
                if (response == 1) {
                    while (getTurns() > 0) {
                        double hitChanceCheck = generateRandomValue(0, 100);
                        if (hitChanceCheck <= getHitChance()) {
                            int atkDmg = generateRandomValue(getMinDmg(), getMaxDmg());
                            opponent.subtractHitPoints(atkDmg);
                        } else {
                            System.out.println("\nHit missed!\n");
                        }
                        setTurns(getTurns() - 1);
                    }
                } else if (response == 2) {
                    System.out.println("(2) is not a valid entry.");
                } else if (response == 3) {
                    setRunAway(true);
                    System.out.println(getName() + " has run away.");
                }
            } while (response == 2);
        }
    }
}
