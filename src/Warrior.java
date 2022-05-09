/*
 * Warrior.java
 *
 * TCSS 142 - Winter 2021
 * Assignment 2
 */

/**
 * Specifies the unique stats and methods for the Warrior Hero.
 */
public class Warrior extends Hero {

    /**
     * A child class of Hero with specific unique statistics.
     * @param theName is the name of the hero.
     */
    protected Warrior(final String theName) {
        super(theName, 125, 4, 35, 60, 80,
                20, 40);
    }

    /**
     * Allows the Warrior to use their special attack.
     * Generates a random number between 0 and 100. If the number is less than or equal to their
     * specialHitChance, then the Warrior will deal 75-125 damage.
     * @param opponent is the opponent the hero is facing.
     */
    @Override
    protected void specialAbility(final DungeonCharacter opponent) {
        double checkCrushingBlow = generateRandomValue(0, 100);
        System.out.println(getName() + " is attempting Crushing Blow!");
        if (checkCrushingBlow <= mySpecialSkillChance) {
            int damage = generateRandomValue(75, 125);
            playSound(warriorSpecial);
            opponent.subtractHitPoints(damage);
        } else {
            playSound(attackMiss);
            System.out.println("Crushing Blow misses.");
        }
    }

}
