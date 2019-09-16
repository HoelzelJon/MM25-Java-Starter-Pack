package mech.mania.MM25JavaStarterPack;

import mech.mania.MM25JavaAPI.*;

/**
 * A class where contestants will implement their strategy for the MechMania25 Hackathon.
 */
public class Strategy {

    private int playerNum;
    private Unit[] myUnits;

    /**
     * Class constructor which records the player number (1 or 2).
     * @param playerNum
     */
    public Strategy(int playerNum){
        this.playerNum = playerNum;
    }

    /**
     * Method to set unit initializations. Run at the beginning of a game, after assigning player numbers.
     * @return An array of {@link UnitSetup} objects which define attack pattern, terrain creation pattern, health, and speed.
     * @see UnitSetup
     */
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

    /**
     * Method to implement the competitors strategy in the next turn of the game. This is where competitors should be
     * putting most of their code.
     * @param gameState An object recording the current state of the game.
     * @return An object representing the actions to execute this turn. Includes the movement and attack directions
     * for each unit and the priorities (order) in which to execute them.
     * @see Turn
     */
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
