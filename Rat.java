package towerdefence;

/**
 * A Rat! A quick moving but weak enemy.
 *
 * @author 170021928
 *
 */

public class Rat extends Enemy {

  /**
   * Constructs a Rat object. The Enemy super constructor takes 3 parameters:
   * the health, the step and the symbol associated with the rat.
   */
  public Rat() {
    super(HEALTH, STEP, SYMBOL);
  }

  /**
   * The starting health of the enemy.
   */
  private static final int HEALTH = 1;

  /**
   * How many steps the enemy may more forward each turn.
   */
  private static final double STEP = 2.0;

  /**
   * Symbol is a string of characters which represent the enemy when drawn on
   * the map.
   */
  private static final String SYMBOL = "  ~> ";

}
