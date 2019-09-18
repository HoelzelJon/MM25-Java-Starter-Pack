package mech.mania.API;

/**
 * Class to store information about a single mech.
 * Created to be deserialized from the game state JSON object.
 */
public class Unit {
    private static int ID_COUNTER = 0;

    private int hp; // unit's current health
    private int speed; // unit's speed (number of tiles it can move per turn)
    private Position pos; // position of the unit
    private int[][] attack; // 2-D grid of attack damages
    private boolean[][] terrain;
    private boolean isAlive;
    private int id;
    private int playerNum;

    Unit(Position setPosition, UnitSetup setup) {
        id = ID_COUNTER++;
        hp = setup.getHealth();
        speed = setup.getSpeed();
        pos = setPosition;
        attack = setup.getAttackPattern();
        terrain = setup.getTerrainPattern();
        isAlive = true;
    }

    public int getId() {
        return id;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public int[][] getAttack() {
        return attack;
    }

    public boolean[][] getTerrain() {
        return terrain;
    }
}

