package mech.mania.API;

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.List;

/**
 * Class to store the state of the game.
 * Created to be deserialized from the game state JSON object.
 */
public class GameState {
    private Tile[][] tiles; // current map
    private List<Unit> units;
    private String gameId;
    private String[] playerNames;
    private int turnsTaken;

    /* Helper Functions */

    /**
     * Helper method to return shortest path from {@code start} to {@code end}
     * avoiding locations in {@code tilesToAvoid}.
     * @param start The {@link Position} to start from.
     * @param end The desired end {@link Position}.
     * @param tilesToAvoid An array of {@link Position}s to avoid in the path.
     * @return An array of {@link Direction} objects indicating the path from start to end.
     */
    public Direction[] pathTo(Position start, Position end, Position[] tilesToAvoid){
        return null;
    }

    /**
     * Same as {@link GameState#pathTo(Position, Position, Position[])}
     * with {@code tilesToAvoid} defaulted to an empty array.
     * @see GameState#pathTo(Position, Position, Position[])
     */
    public Direction[] pathTo(Position start, Position end){
        Position[] tilesToAvoid = new Position[0];
        return pathTo(start, end, tilesToAvoid);
    }

    /**
     * Helper method to return what damage would be done at what locations for a hypothetical attack.
     * @param unitPosition The position from which the attack would be fired.
     * @param attackPattern The attack pattern with which the attack would be fired.
     * @param dir The direction the attack would be fired in.
     * @return A list of {@link Pair}s of (Position, Integer) indicating how much damage would be done at
     * certain locations if a unit with {@code attackPattern} were to attack in direction {@code dir}.
     * from position {@unit position}. Positions returned are absolute, not relative to {@code unitPosition}.
     */
    public List<Pair<Position, Integer>> getPositionsOfAttackPattern(Position unitPosition, int[][] attackPattern, Direction dir){
        return null; // TODO
    }

    /**
     * Same as {@link GameState#getPositionsOfAttackPattern(Position, int[][], Direction)}
     * with {@code unitPosition} defaulted to the specified unit's position and
     * {@code attackPattern} defaulted to the specified unit's attack pattern.
     * @see GameState#getPositionsOfAttackPattern(Position, int[][], Direction)
     */
    public List<Pair<Position, Integer>> getPositionsOfAttackPattern(int unitId, Direction dir){
        Unit unit = getUnit(unitId);
        return getPositionsOfAttackPattern(unit.getPos(), unit.getAttack(), dir);
    }

    /**
     * Helper method to return the new absolute position given an initial position and a set of movement steps.
     * Does NOT account for any map conditions. This means it can return a position where terrain
     * or another unit exists, and it can also return a position which is outside of the game map.
     * @param start {@link Position} to start from.
     * @param movementSteps Array of {@link Direction}s indicating movement steps to take.
     * @return The final absolute {@link Position} obtained from beginning at {@code start}
     * and moving according to {@code movementSteps} irrespective of the current game's map.
     */
    public Position getPositionAfterMovement(Position start, Direction[] movementSteps){
        return null; // TODO
    }

    /**
     * Helper method to indicate if an {@link Decision} object is valid.
     * @param d The {@link Decision} to evaluate.
     * @return A boolean representing the validity of the Decision.
     */
    public boolean isDecisionValid(Decision d){
        return false; // TODO
    }

    /**
     * Helper method to indicate if a {@link UnitSetup} object is valid.
     * @param s The {@link UnitSetup} to evaluate.
     * @return A boolean representing the validity of the Decision.
     */
    public boolean isUnitSetupValid(UnitSetup s){
        return false; // TODO
    }

    /**
     * Helper method to return the {@link Unit} with the specified {@code unitId}.
     * @param unitId Unit ID of the unit to return.
     * @return the {@link Unit} with the specified {@code unitId}.
     */
    public Unit getUnit(int unitId){
        for(Unit u: units){
            if(u.getId() == unitId){
                return u;
            }
        }
        return null;
    }

    /**
     * Helper method to return a {@link List} of {@link Unit}s that belong to the specified player.
     * @param playerNum The player number of the player whose units should be returned.
     * @return A {@link List} of {@link Unit}s that belong to the specified player.
     */
    public List<Unit> getPlayerUnits(int playerNum){
        List<Unit> playerUnits = new ArrayList<>();
        for(Unit u : units){
            if(u.getPlayerNum() == playerNum) {
                playerUnits.add(u);
            }
        }
        return playerUnits;
    }

    public int getTurnsTaken() {
        return turnsTaken;
    }

    public String getGameId() {
        return gameId;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Unit> getUnits() {
        return units;
    }
}

