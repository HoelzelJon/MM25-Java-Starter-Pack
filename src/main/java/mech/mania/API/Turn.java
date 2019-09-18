package mech.mania.API;

/**
 * Class to store the actions to be executed on the next turn.
 * Created to be serialized to send to the game engine as a JSON object.
 */
public class Turn {
    private int[] priorities;
    private Direction[][] movements;
    private Direction[] attacks;

    public Turn(int[] p, Direction[][] m, Direction[] a){
        priorities = p;
        movements = m;
        attacks = a;
    }

    public Direction[] getAttacks() {
        return attacks;
    }

    public Direction[][] getMovements() {
        return movements;
    }

    public int[] getPriorities() {
        return priorities;
    }
}
