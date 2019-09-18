package mech.mania.API;

/**
 * Class to store the initial setup of a mech.
 * Created to be serialized to be sent to the game engine as a JSON object during game initialization.
 */
public class UnitSetup {
    private int[][] attackPattern;
    private boolean[][] terrainPattern;
    private int health;
    private int speed;

    public UnitSetup(int[][] ap, boolean[][] tp, int h, int s){
        attackPattern = ap;
        terrainPattern = tp;
        health = h;
        speed = s;
    }

    public boolean[][] getTerrainPattern() {
        return terrainPattern;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int[][] getAttackPattern() {
        return attackPattern;
    }
}
