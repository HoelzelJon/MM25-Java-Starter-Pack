package mech.mania.MM25JavaStarterPack;

public class Strategy {

    private int playerNum;
    private Unit[] myUnits;

    public Strategy(int playerNum){
        this.playerNum = playerNum;
    }

    // Define initial bot setups
    public UnitSetup[] getSetup(){
        // Default values
        int[][] attackPattern = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}
        };
        boolean[][] terrainPattern = {
                {false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false}
        };
        int health = 1;
        int speed = 1;

        // Define units
        UnitSetup unit1 = new UnitSetup(attackPattern, terrainPattern, health, speed);
        UnitSetup unit2 = new UnitSetup(attackPattern, terrainPattern, health, speed);
        UnitSetup unit3 = new UnitSetup(attackPattern, terrainPattern, health, speed);

        UnitSetup[] unitSetup = {unit1, unit2, unit3};
        return unitSetup;
    }

    // Take a turn
    public Turn doTurn(GameState gameState){
        // Update myUnits
        if(playerNum == 1) {
            myUnits = gameState.getP1Units();
        }
        else if(playerNum == 2){
            myUnits = gameState.getP2Units();
        }

        // Default values
        int[] priorities = {1, 2, 3};
        Direction[][] movements = new Direction[3][];
        for(int u = 0; u < myUnits.length; u++) {
            Direction[] m = new Direction[myUnits[u].getSpeed()];
            for (int s = 0; s < m.length; s++) {
                m[s] = Direction.STAY;
            }
            movements[u] = m;
        }
        Direction[] attacks = {Direction.STAY, Direction.STAY, Direction.STAY};

        return new Turn(priorities, movements, attacks);
    }
}
