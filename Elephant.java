package towerdefence;

/**
 * An Elephant! A slow enemy that can take a lot of damage.
 *
 * @author 170021928
 *
 */
public class Elephant extends Enemy {

    /**
     * Constructs an Elephant object. The Enemy super constructor takes 3
     * parameters: the health, the step and the symbol associated with the
     * elephant.
     */
    public Elephant() {
        super(HEALTH, STEP, SYMBOL);
    }

    /**
     * The starting health of the enemy.
     */
    private static final int HEALTH = 10;

    /**
     * How many steps the enemy may more forward each turn.
     */
    private static final double STEP = 0.5;

    /**
     * Symbol is a string of characters which represent the enemy when drawn on
     * the map.
     */
    private static final String SYMBOL = "/M@\\ ";
}
