package towerdefence;

/**
 * A Slingshot! This is a weak, but cheap tower that is quick to reload.
 *
 * @author 170021928
 *
 */
public class Slingshot extends Tower {

    /**
     * A slingshot, which is an instance of a tower. It is instantiated by
     * passing int damage, int position, int loadTime, int cost, String symbol
     * to the Tower super constructor.
     *
     * @param position
     *            the position of the tower - this is provided by the user when
     *            the tower is set up.
     */
    public Slingshot(int position) {
        // int damage, int position, int loadTime, int cost, String symbol
        super(DAMAGE, position, LOADTIME, COST, SYMBOL);
    }

    // this would work with just 'static' variables, but checkstyle keeps
    // shouting about magic numbers so I made them final.

    /**
     * The damage dealt by the tower to an enemy.
     */
    private static final int DAMAGE = 1;

    /**
     * The number of timesteps that must elapse before the tower is able to
     * fire.
     */
    private static final int LOADTIME = 1;

    /**
     * The cost of the tower in coins.
     */
    private static final int COST = 10;

    /**
     * The string representation of the tower for printing on the map.
     */
    private static final String SYMBOL = " _Y>_";

}
