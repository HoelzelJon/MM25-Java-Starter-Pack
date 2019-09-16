package mech.mania.MM25JavaAPI;

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
