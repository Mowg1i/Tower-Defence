package towerdefence;

/**
 * Abstract class Enemy acts as a template for its subclasses and defines all of
 * the methods and variables used by all enemies.It is not explicitly defined as
 * abstract because it is useful on occasion to create an Enemy instance before
 * it is known what type of enemy it will be.
 *
 * @author 170021928
 *
 */
public class Enemy {

  /**
   * Constructs an enemy instance.
   *
   * @param health
   *          the HP of the enemy
   * @param step
   *          how many steps the enemy takes per turn
   * @param symbol
   *          the string representation of the enemy for drawing on the map.
   */
  public Enemy(int health, double step, String symbol) {
    this.health = health;
    this.step = step;
    this.symbol = symbol;
    this.position = 0.0;
  }

  /**
   * The enemy's health points.
   */
  private int health;

  /**
   * How many steps the enemy may more forward each turn. Step is a double to
   * accommodate slow enemies like the elephant, who move only 0.5 spaces per
   * turn.
   */
  private double step;

  /**
   * position represents the location of the enemy along the corridor, from 0
   * (far left) to the given corridor length. It is a double to accommodate slow
   * enemies like the elephant, who move only 0.5 spaces per turn - so every
   * other turn they are technically between spaces. This does not appear on the
   * map, rather position is cast as an int before being drawn.
   */
  private double position;

  /**
   * symbol is a string of characters (e.g. " ~> " which represent the enemy
   * when drawn on the map)
   */
  private String symbol;

  /**
   * line is the y position of the enemy when drawn on the map. Enemies start
   * with a given line number which does not change as they move forward in a
   * straight line.
   */
  private int line;

  /**
   * Advances the position stored for the given enemy by the step of that enemy.
   */
  public void advance() {

    this.position += this.step;
  }

  /**
   * Returns the current HP of the enemy.
   *
   * @return the current HP of the enemy
   */
  public int getHealth() {
    return this.health;

  }

  /**
   * Returns the line (the vertical position) of the enemy on the map.
   *
   * @return the line (the vertical position) of the enemy on the map.
   */
  public int getLine() {
    return this.line;
  }

  /**
   * When returning a position we need to cast to int so it can be used to index
   * into the map. Casting to an int just truncates everything after the
   * decimal. For example, if the current position of an enemy is 1.5, it will
   * be returned as 1.
   *
   * @return current position of enemy cast as an int.
   */
  public int getPosition() {
    return (int) this.position;
  }

  /**
   * Returns the string representation of the enemy e.g. "/M@\\ "
   *
   * @return the string representation of the enemy for drawing on the map.
   */
  public String getSymbol() {
    return this.symbol;
  }

  /**
   * Checks if the enemy position is less than the tower position - if so, the
   * enemy is in range and can be hit. Accesses the damage attribute of the
   * given tower and subtracts the damage from the enemy's HP.
   *
   * @param t
   *          an instance of a tower object.
   */
  public void hit(Tower t) {

    if (t.getPosition() > this.getPosition()) {

      this.health -= t.getDamage();
    }

  }

  /**
   * I feel like I repeat myself a lot when commenting these getters and
   * setters, but it's what the style guide says to do. Returns the name of the
   * enemy
   *
   * @return the name of the enemy as a string - e.g. "Rat"
   */
  public String name() {
    return this.getClass().getSimpleName();
  }

  /**
   * Used to set the vertical starting position of the enemy. This is set when
   * the enemy is created and does not change.
   *
   * @param l
   *          an int between 1 and (height of the map - 1).
   */
  public void setLine(int l) {
    this.line = l;
  }

  /**
   * Sets how close the enemy is to the castle/end of the corridor.
   *
   * @param pos
   *          sets the position of the enemy
   */
  public void setPosition(int pos) {
    this.position = pos;
  }

  @Override
  public String toString() {

    return this.name() + " health = " + this.health + " step = " + this.step + " position = " + this.position
        + " symbol = " + this.symbol;
  }

}
