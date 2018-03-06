package towerdefence;

/**
 * A Camel! A medium kind of enemy with a moderate speed and middling health.
 *
 * @author 170021928
 *
 */

public class Camel extends Enemy {

    /**
     * Constructs a Camel object. The Enemy super constructor takes 3
     * parameters: the health, the step and the symbol associated with the
     * camel.
     */
    public Camel() {
        super(HEALTH, STEP, SYMBOL);
    }

    /**
     * The starting health of the enemy.
     */
    private static final int HEALTH = 5;

    /**
     * How many steps the enemy may more forward each turn.
     */
    private static final double STEP = 1.0;

    /**
     * Symbol is a string of characters which represent the enemy when drawn on
     * the map.
     */
    private static final String SYMBOL = "  MP ";
}
