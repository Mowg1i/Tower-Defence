package towerdefence;

/**
 * The Tower class acts as a template for its subclasses and defines all of the
 * methods and variables used by all enemies. It is not explicitly defined as
 * abstract because it is useful on occasion to create a Tower instance before
 * it is known what type of tower it will be.
 */
public class Tower {

    /**
     * Tower constructor. Used as a super constructor to create different types
     * of towers.
     *
     * @param damage
     *            an int representing the damage the tower does an enemy.
     * @param position
     *            an int representing the position of the tower on the map.
     * @param loadTime
     *            an int representing how many time steps the tower must wait
     *            before firing each time.
     * @param cost
     *            an int representing how much the tower costs to place on the
     *            map.
     * @param symbol
     *            a string representation of the tower for drawing on the map.
     */
    Tower(int damage, int position, int loadTime, int cost, String symbol) {
        this.damage = damage;
        this.position = position;
        this.loadTime = loadTime;
        this.cost = cost;
        this.symbol = symbol;

    }

    /**
     * The x index of the tower - how close it is horizontally to the end of the
     * corridor.
     */
    private int position;

    /**
     * The damage dealt by the tower to an enemy.
     */
    private int damage;

    /**
     * The number of timesteps that must elapse before the tower is able to
     * fire.
     */
    private int loadTime;

    /**
     * The cost of the tower in coins.
     */
    private int cost;

    /**
     * The string representation of the tower for printing on the map.
     */
    private String symbol;

    /**
     * Returns the cost of the tower in coins.
     *
     * @return cost of tower
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Returns the damage done by the tower when it hits an enemy.
     *
     * @return damage done to enemy if hit by tower
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns the position of the tower horizontally on the map.
     *
     * @return tower position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Returns a string representation of the tower for printing on the map
     * (e.g. " _H\\_").
     *
     * @return string representing tower
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Returns the name of the kind of tower that the tower instance is.
     *
     * @return the name of the kind of tower - e.g. "Cannon"
     */
    public String name() {
        return this.getClass().getSimpleName();
    }

    /**
     * Sets the position of the tower.
     * 
     * @param pos
     *            the position of the tower from 0 to corridorLength-1
     */
    public void setPosition(int pos) {
        this.position = pos;
    }

    @Override
    public String toString() {

        return this.name() + " damage = " + this.damage + " position = " + this.position + " loadTime = "
                + this.loadTime + " cost = " + this.cost + " symbol = " + this.symbol;
    }

    /**
     * Returns whether the tower may fire on an enemy or not.
     *
     * @param timeStep
     *            the current timeStep of the game
     * @return true if the tower may fire, false otherwise
     */
    public boolean willFire(int timeStep) {
        return (timeStep % this.loadTime == 0);
    }

}
