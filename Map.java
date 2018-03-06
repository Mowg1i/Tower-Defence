package towerdefence;

import java.util.ArrayList;

/**
 * The map is used to display information about tower and enemy positions to the
 * user. It is stored as a 2d array and is updated and reprinted throughout the
 * game.
 *
 * @author 170021928
 *
 */

public class Map {

  /**
   * The length of the corridor - this is initialised by the game to either a
   * number set by the user or the default of 15.
   *
   * @param corridorLength
   *          the corridor length supplied
   */
  Map(int corridorLength) {

    this.castlePos = corridorLength + 1;
    this.map = this.init(this.castlePos);
  }

  /**
   * The height of the map drawn. The actual area of play is mapHeight -2, as
   * the first two rows are used to draw the tower positions and the towers
   * themselves.
   */
  private static final int MAPHEIGHT = 7;

  /**
   * The position of the castle that the user must defend at the end of the
   * corridor.
   */
  private int castlePos;
  /**
   * The row position of the towers - this is set to row 1, the second row in
   * the map array. Row 0 is used to print the possible tower positions.
   */
  private int towerPos = 1;

  /**
   * A 2d array that is used to hold the map information.
   */
  private String[][] map;

  /**
   * Checks if there is free space on the map to place a given tower - stops the
   * user placing two towers in the same position.
   *
   * @param pos
   *          an integer representing the position of the tower that the user
   *          wants
   * @return true if the tower can be places, false if not.
   */
  public boolean checkIfFree(int pos) {

    if (pos < 0 || pos > this.castlePos) {
      return false;
    }
    if (this.map[this.towerPos][pos] != "     ") {
      return false;
    }

    return true;

  }

  /**
   * Returns the height of the map, this is fixed and is used when generating
   * enemies.
   *
   * @return the height of the map
   */
  public int getMapHeight() {
    return MAPHEIGHT;
  }

  /**
   * Initialises a blank map with the given corridor length.
   *
   * @param corridorLength
   *          an int (up to 60) used to determine the length of the map to
   *          initialise
   * @return the initialised map
   */
  private String[][] init(int corridorLength) {

    // ten is used to check whether the numbers being printed to represent
    // the
    // tower positions are ten or over - if so, we must print one less space
    // for each of the so the map doesn't go wonky.
    final int ten = 10;

    this.map = new String[MAPHEIGHT][this.castlePos + 1];

    for (int i = 0; i < MAPHEIGHT; i++) {
      for (int j = 0; j < corridorLength; j++) {
        this.map[i][j] = "     ";

        if (i == 0) {
          if (j < ten) {
            this.map[i][j] = "  " + j + "  ";
          } else if (j < corridorLength - 1) {
            this.map[i][j] = "  " + j + " ";
          }
        }
      }
    }

    // adding the castle at the end
    for (int k = 0; k < MAPHEIGHT; k++) {
      this.map[k][this.castlePos - 1] = " |^|_";
      this.map[k][this.castlePos] = "_|^| ";
    }

    return this.map;

  }

  /**
   * Given the current array lists of enemies and towers, plots them on the map.
   * It first initialises the map to be blank - this is important, so that
   * enemies who have died and then been removed from the arraylist do not still
   * appear on the map when it is updated.
   *
   * @param enemies
   *          arrayList of enemy objects
   * @param towers
   *          arrayList of tower Objects
   */
  public void print(ArrayList<Enemy> enemies, ArrayList<Tower> towers) {

    this.map = this.init(this.castlePos);

    for (Enemy enemy : enemies) {

      this.map[enemy.getLine()][enemy.getPosition()] = enemy.getSymbol();

    }

    for (Tower tower : towers) {

      this.map[this.towerPos][tower.getPosition()] = tower.getSymbol();

    }

    System.out.print("\n");

    for (int i = 0; i < MAPHEIGHT; i++) {
      for (int j = 0; j < this.castlePos + 1; j++) {
        System.out.print(this.map[i][j]);
      }
      System.out.print("\n");
    }
    System.out.print("\n");

  }

}
