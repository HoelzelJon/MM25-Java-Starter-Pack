package mech.mania.MM25JavaStarterPack;

/**
 * Represents a single mech.
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

    int getSpeed() {
        return speed;
    }

    Position getPos() {
        return pos;
    }

    void setPos(Position pos) {
        this.pos = pos;
    }

    boolean isAlive() {
        return isAlive;
    }
}

