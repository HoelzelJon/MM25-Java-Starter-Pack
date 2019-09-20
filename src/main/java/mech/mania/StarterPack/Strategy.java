package mech.mania.StarterPack;

import mech.mania.API.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class where contestants will implement their strategy for the MechMania25 Hackathon.
 */
public class Strategy {
    // define any private variables here
    // NOTE: Since the server may be restarted or moved mid-game, you MUST initialize any variables you put here in each of the below constructors.
    //       If the server is restarted or moved, these variables will not have the values you previously set them with.
    //       If you need truly persistent data, you could set up a database and communicate with that from your script

    /**
     * This constructor is called when a game is first started.
     * @param init The initial state of this new game
     * @see GameInit
     */
    public Strategy(GameInit init) {
        // initialize variables here
    }

    /**
     * This constructor is called if/when the server restarts in the middle of a game
     * @param state the current state of the game
     * @see GameState
     */
    public Strategy(GameState state) {
        // initialize variables here
    }

    /**
     * Method to set unit initializations. Run at the beginning of a game, after assigning player numbers.
     * @return An array of {@link UnitSetup} objects which define attack pattern, terrain creation pattern, health, and speed.
     * @see UnitSetup
     */
    public UnitSetup[] getSetup(int playerNum){
        // Default values
        int[][] attackPattern = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 1, 1, 0, 1, 1, 0},
                {0, 0, 1, 2, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0},
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
        int health = 4;
        int speed = 4;

        UnitSetup unit1;
        UnitSetup unit2;
        UnitSetup unit3;
        if(playerNum == 1) {
            // Define units if player 1
            unit1 = new UnitSetup(attackPattern, terrainPattern, health, speed, 1);
            unit2 = new UnitSetup(attackPattern, terrainPattern, health, speed, 2);
            unit3 = new UnitSetup(attackPattern, terrainPattern, health, speed, 3);
        }
        else{
            // Define units if player 2
            unit1 = new UnitSetup(attackPattern, terrainPattern, health, speed, 4);
            unit2 = new UnitSetup(attackPattern, terrainPattern, health, speed, 5);
            unit3 = new UnitSetup(attackPattern, terrainPattern, health, speed, 6);
        }

        UnitSetup[] unitSetup = {unit1, unit2, unit3};
        return unitSetup;
    }

    /**
     * Method to implement the competitors strategy in the next turn of the game. This is where competitors should be
     * putting most of their code.
     * @param gameState An object recording the current state of the game.
     * @return An object representing the actions to execute this turn. Includes the movement and attack directions
     * for each unit and the priorities (order) in which to execute them.
     * @see Decision
     */
    public Decision[] doTurn(GameState gameState){
        int playerNum = gameState.getPlayerNum();
        List<Unit> myUnits = gameState.getPlayerUnits(playerNum);
        List<Unit> enemyUnits = gameState.getPlayerUnits(playerNum == 1 ? 2 : 1);

        // Default values
        Decision[] turnResponse = new Decision[myUnits.size()];
        for(int u = 0; u < myUnits.size(); u++) {
            int priority = u + 1;
            Direction[] movementSteps = new Direction[myUnits.get(u).getSpeed()];
            // Find nearest enemy unit
            int minDist = 0;
            List<Direction> closestPath = null;
            for(Unit enemyUnit : enemyUnits){
                List<Direction> path = gameState.pathTo(myUnits.get(u).getPos(), enemyUnit.getPos());
                if(path == null){
                    continue;
                }
                if(path.size() <= minDist){
                    minDist = path.size();
                    closestPath = new ArrayList<>(path);
                }
            }

            // Set defaults if pathTo failed
            for (int s = 0; s < movementSteps.length; s++) {
                // move randomly
                int r = (int) (Math.random() * 5);
                switch (r) {
                    case 0:
                        movementSteps[s] = Direction.UP;
                        break;
                    case 1:
                        movementSteps[s] = Direction.DOWN;
                        break;
                    case 2:
                        movementSteps[s] = Direction.LEFT;
                        break;
                    case 3:
                        movementSteps[s] = Direction.RIGHT;
                        break;
                    default:
                        movementSteps[s] = Direction.STAY;
                        break;
                }
            }

            // If possible, head to hard-coded locations to break through walls
            if (closestPath == null){
                // Go to (5, 3) if possible
                closestPath = gameState.pathTo(myUnits.get(u).getPos(), new Position(5, 3));
                if (closestPath == null){
                    // If (5, 3) was blocked, got to (4, 4)
                    closestPath = gameState.pathTo(myUnits.get(u).getPos(), new Position(4, 4));
                }
            }

            // Attack if would damage walls or an enemy unit
            Direction attackDirection = Direction.STAY;
            List<Pair<Position, Integer>> posOfAttack =
                    gameState.getPositionsOfAttackPattern(
                            gameState.getPositionAfterMovement(myUnits.get(u).getPos(), movementSteps),
                            myUnits.get(u).getAttack(), Direction.UP);
            for(Pair p : posOfAttack){
                Position pos = (Position)p.getFirst();
                try {
                    Tile t = gameState.getTiles()[pos.x][pos.y];
                    if (t.getType() != Tile.Type.BLANK || (t.getUnit() != null && t.getUnit().getPlayerNum() != playerNum)) {
                        attackDirection = Direction.UP;
                        break;
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    continue;
                }
            }
            // Move toward closest bot
            for (int s = 0; s < movementSteps.length; s++) {
                if (closestPath.size() > s) {
                    movementSteps[s] = closestPath.get(s);
                }
            }

            // Attack if you would be within 1 tile of your target
            if (movementSteps.length >= closestPath.size() - 1) {
                attackDirection = Direction.UP;
            }
            turnResponse[u] = new Decision(priority, movementSteps, attackDirection, myUnits.get(u).getId());
        }

        return turnResponse;
    }
}
