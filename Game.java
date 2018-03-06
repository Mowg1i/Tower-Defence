package towerdefence;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * My brilliant tower game! This class contains all the game logic. Run the game
 * from the command line thus:
 *
 * java -jar towerDefence.jar <corridor length>
 *
 * Corridor length is optional - if one is not provided, a default of 20 will be
 * used. The shorter the corridor, the harder the game! The maximum possible
 * corridor length is 60 - if a higher number is provided, the game will use a
 * default corridor length of 15.
 *
 * @author 170021928
 *
 */
public class Game {

    /**
     * Constructor for the game class.
     *
     * @param corridorLength
     *            int taken from the command line which determines the length of
     *            the corridor.
     */
    Game(int corridorLength) {
        this.corridorLength = corridorLength;
    }

    /**
     * The difficulty increase threshold - after this number of timesteps, the
     * inverse spawn rate will be decreased.
     */
    private static final int DIFFICULTY_INCREASE_THRESHOLD = 10;

    /**
     * The initial inverse enemy spawn rate - the higher the number, the less
     * likely an enemy is to be spawned.
     */
    private static final int INITIAL_INVERSE_SPAWNING_RATE = 20;

    /**
     * Contains all the enemies that are currently alive.
     */
    private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    /**
     * Contains all the towers.
     */
    private static ArrayList<Tower> towers = new ArrayList<Tower>();

    /**
     * The default corridor length, used if no arguments are passed.
     */
    private static final int DEFAULTCORRIDOR = 15;

    /**
     * The maximum corridor length. This is set to 60, so that when the purse
     * starting value is calculated the game remains playable. If the corridor
     * was longer than 60, the purse start value would be less than 10, which is
     * not enough to buy any towers.
     */
    private static final int MAXCORRIDOR = 60;

    /**
     * The purse start value. The length of the corridor is subtracted from
     * this, so that if you have a shorter corridor you get a bit more money.
     */
    private static final int PURSESTART = 70;

    /**
     * Stores the height of the map when we create a new map. This is used when
     * generating start positions of the enemies.
     */
    private int mapHeight;

    /**
     * Holds the player's coins.
     */
    private int purse;

    /**
     * Holds the length of the corridor if it has been specified by the user.
     */
    private int corridorLength;

    /**
     * Tracks the current timeStep - this is used to determine if each tower is
     * ready to fire.
     */
    private int timeStep = 1;

    /**
     * The main method does these things:
     *
     * Sets up and prints the map. Initialises the purse - this relates to the
     * corridor length, so that if you have a shorter corridor you get a bit
     * more money. Otherwise it's too hard with a very short corridor. Opens a
     * scanner to get user input and prompts for tower choices. Sets towers.
     * Randomly generates enemies. If by chance no enemies were generated, sets
     * some.
     *
     * Now the game is set up, and so it simply loops through advancing the game
     * state, giving the option to buy towers, updating the map, and
     * incrementing the time step until the game is over.
     *
     * @param args
     *            takes one argument when run, an int which determines the
     *            length of the game corridor. It's optional.
     */
    public static void main(String[] args) {

        Game newGame;

        if (args.length == 1 && Integer.parseInt(args[0]) <= MAXCORRIDOR) {
            newGame = new Game(Integer.parseInt(args[0]));
        } else {
            newGame = new Game(DEFAULTCORRIDOR);
        }

        Map newMap = new Map(newGame.corridorLength);
        newGame.mapHeight = newMap.getMapHeight();
        newMap.print(enemies, towers);
        newGame.purse = PURSESTART - newGame.corridorLength;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to ANIMAL ADVANCE!");
        newGame.buyTowers(newMap, scanner);

        newGame.generateEnemies(enemies);

        // if there are not many enemies randomly generated to start with, add
        // some
        // so that the game doesn't end really soon
        if (enemies.size() <= 2) {

            // setting the enemies' line positions
            final int l2 = 2;
            final int l3 = 3;
            final int l4 = 4;

            Rat newRat = new Rat();
            newRat.setLine(l2);
            enemies.add(newRat);

            Elephant newElephant = new Elephant();
            newElephant.setLine(l3);
            enemies.add(newElephant);

            Camel newCamel = new Camel();
            newCamel.setLine(l4);
            enemies.add(newCamel);
        }

        newMap.print(enemies, towers);

        while (newGame.endGame() == 0) {

            newGame.advance();

            newGame.buyTowers(newMap, scanner);

            newMap.print(enemies, towers);
            newGame.incrementTimeStep();

        }
        scanner.close();
    }

    /**
     * Advances the game state by checking which towers may fire and firing
     * them, updating the enemies based on the shots fired, and generating new
     * enemies.
     */
    public void advance() {

        this.fire();
        this.updateEnemies();
        this.generateEnemies(enemies);
    }

    /**
     * This is the method that interacts with the user and takes and validates
     * input.
     *
     * It allows the user to pick if (and which) tower they want to purchase,
     * and place it on the map at a location of their choice. It also performs
     * various checks to make sure that the user input is valid.
     *
     *
     * @param newMap
     *            the map to be updated (this is the map generated at the start
     *            of a new game)
     * @param scanner
     *            the scanner opened at the start of a new game, to take user
     *            input.
     */
    public void buyTowers(Map newMap, Scanner scanner) {

        while (true) {

            System.out.println("PURSE: " + this.purse);

            System.out.println("Enter 1 to buy a Slingshot: DMG: 1 DELAY: 1 COST: 10");
            System.out.println("Enter 2 to buy a Catapult:  DMG: 5 DELAY: 3 COST: 20");
            System.out.println("Enter 3 to buy a Cannon:    DMG: 10 DELAY: 5 COST: 30");
            System.out.println("Enter 4 to continue.");

            // User input options (avoiding the dreaded magic number!)
            final int slingshotOption = 1;
            final int catapultOption = 2;
            final int cannonOption = 3;
            final int continueOption = 4;

            int choice = this.getNumber(scanner);

            if (choice == continueOption) {
                break;

            } else if (choice < slingshotOption || choice > continueOption) {
                System.out.println("Sorry, that's not an option.");

            } else {

                Tower newTower = null;

                // we pos to create a new tower, but it is a placeholder that
                // will be updated shortly. We must create the tower object now,
                // so we can access its cost to make sure the user can afford it
                // before saving it to the array.
                int pos = 0;

                switch (choice) {

                    case slingshotOption:
                        newTower = new Slingshot(pos);
                        break;
                    case catapultOption:
                        newTower = new Catapult(pos);
                        break;
                    case cannonOption:
                        newTower = new Cannon(pos);
                        break;
                    default:
                        break;
                }

                if (newTower.getCost() > this.purse) {
                    System.out.println("You don't have enough money for that. Choose another option.");

                } else {
                    this.purse -= newTower.getCost();

                    System.out.println("Choose tower position from 0 to " + (this.corridorLength - 1));

                    pos = this.getNumber(scanner);

                    while (!newMap.checkIfFree(pos)) {
                        System.out.println("You cannot place two towers in the same location.");
                        System.out.println("Choose tower position from 0 to " + (this.corridorLength - 1));
                        pos = this.getNumber(scanner);
                    }

                    newTower.setPosition(pos);
                    towers.add(newTower);
                    newMap.print(enemies, towers);
                }
            }
        }
    }

    /**
     * an int between -1 and 1 which is used to check if the game has been won
     * or not and prints a pithy statement. If the enemies have reached the end
     * of the corridor, the game is lost, or if they have all been killed, the
     * game is won. 0 is returned if the game is still in play.
     *
     * @return an int, either -1, 1 or 0
     */
    public int endGame() {

        for (Enemy enemy : enemies) {
            if (enemy.getPosition() >= this.corridorLength) {
                System.out.println(
                        "The animals have breached your defences. Fortunately, they're friendly and have brought cake.");
                return -1; // lost
            }
        }
        if (enemies.size() == 0) {
            System.out.println(
                    "You have killed all the animals. Gazing out over the battlefield, you wonder what the point of it all was.");
            return 1; // won
        } else {
            return 0; // still playing
        }
    }

    /**
     * Loops through the towers and checks if each is ready to fire. If so,
     * finds an enemy within range and fires on it. Updates the enemy's health
     * accordingly.
     */
    public void fire() {

        for (Tower tower : towers) {

            if (tower.willFire(this.getTimeStep())) {

                while (true) {

                    Enemy randomEnemy = enemies.get(new Random().nextInt(enemies.size()));

                    if (randomEnemy.getPosition() < tower.getPosition()) {

                        randomEnemy.hit(tower);
                        System.out.println(tower.name() + " hit " + randomEnemy.name() + " with " + tower.getDamage()
                                + " damage.");

                        break;
                    }
                }
            }
        }
    }

    /**
     * Randomly generates and instantiates enemies and adds them to the enemies
     * array list.
     *
     * @param enemies
     *            takes the array list of existing enemies
     * @return the array list updated with new enemies
     */
    public ArrayList<Enemy> generateEnemies(ArrayList<Enemy> enemies) {

        // the higher the enemyGenOutOf number, the lower the chance that
        // an enemy will be randomly generated.
        int inverseSpawnRate = INITIAL_INVERSE_SPAWNING_RATE;

        // the number of possible random numbers that could result in an enemy
        // being generated.
        final int enemyNum = 4;

        // so as the game continues, the likelihood that enemies are generated
        // becomes higher.
        if (this.timeStep % DIFFICULTY_INCREASE_THRESHOLD == 0 && inverseSpawnRate > enemyNum) {
            inverseSpawnRate--;
        }

        for (int i = 2; i < this.mapHeight; i++) {

            // for each line position, we get a random number from 0 -
            // enemyGen-1.
            Random rn = new Random();
            int r = (rn.nextInt(inverseSpawnRate) + 1);

            // if that number is between 1 and 4, an enemy will be generated.
            if (r > 0 && r <= enemyNum) {

                Enemy newEnemy = null;

                // if the random number is 4, an Elephant is generated. 3, a
                // Camel. 2 or 1, a rat. I had the numbers hardcoded before
                // which seems simpler, but checkstyle was upset about it.
                switch (r) {

                    case enemyNum:
                        newEnemy = new Elephant();
                        break;
                    case enemyNum - 1:
                        newEnemy = new Camel();
                        break;
                    default:
                        newEnemy = new Rat();
                }

                newEnemy.setLine(i);
                enemies.add(newEnemy);
            }
        }
        return enemies;
    }

    /**
     * Insists that the user provides a number. - Repeatedly asks until one is
     * provided.
     *
     * @param s
     *            an open scanner
     * @return the int provided by the user
     */
    public int getNumber(Scanner s) {

        while (!s.hasNextInt()) {
            System.out.println("Give me a number, please.");
            s.next();
        }
        return s.nextInt();
    }

    /**
     * The current timestep.
     *
     * @return the current timestep
     */
    public int getTimeStep() {
        return this.timeStep;
    }

    /**
     * increments the timestep by one.
     */
    public void incrementTimeStep() {
        this.timeStep += 1;
    }

    /**
     * loops through the enemies array list, advances each of them and prints
     * out information on their status for the user. If their health is 0 or
     * below, they die and the user gets a coin.
     */
    public void updateEnemies() {

        for (int e = enemies.size() - 1; e >= 0; e--) {

            System.out.println(enemies.get(e).name() + " HP: " + enemies.get(e).getHealth());
            enemies.get(e).advance();

            if (enemies.get(e).getHealth() <= 0) {
                System.out.print(enemies.get(e).name() + " died.");
                System.out.println(" You get 1 COIN for killing a sentient being!");
                this.purse++;
                enemies.remove(e);
            }
        }
    }
}
