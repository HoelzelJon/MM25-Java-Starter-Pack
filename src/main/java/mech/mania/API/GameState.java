package mech.mania.API;

import java.util.*;

/**
 * Class to store the state of the game.
 * Created to be deserialized from the game state JSON object.
 */
public class GameState {
    public static final int ATTACK_PATTERN_SIZE = 7;
    public static final int BASE_HEALTH = 1;
    public static final int BASE_SPEED = 1;
    static final int MAX_POINTS = 24;
    static final int TERRAIN_COST = 2;
    private static final int[] DAMAGE_SCALING = {
            1, 3, 6, 10, 15, 21, 27
    };
    private static final int[] SPEED_SCALING = {
            1, 2, 4, 6, 9, 12, 16, 20, 25
    };
    private static final int[] HEALTH_SCALING = {
            1, 2, 4, 6, 9, 12, 16, 20, 25
    };

    private int playerNum;
    private Tile[][] tiles; // current map
    private List<Unit> units;
    private String gameId;
    private String[] playerNames;
    private int turnsTaken;

    /* Helper Functions */

    /**
     * Helper method to return shortest path from {@code start} to {@code end}
     * avoiding locations in {@code tilesToAvoid}. Returns null if impossible.
     * @param start The {@link Position} to start from.
     * @param end The desired end {@link Position}.
     * @param tilesToAvoid An array of {@link Position}s to avoid in the path.
     * @return An ArrayList of {@link Direction} objects indicating the path from start to end.
     * Returns null if it is not possible to get to {@code end} from {@code start}.
     */
    public List<Direction> pathTo(Position start, Position end, Position[] tilesToAvoid){
        Queue<Pair<Position, List<Direction>>> q = new LinkedList<>();
        List<Direction> init_directions = new ArrayList<>();
        q.add(new Pair<>(start, init_directions));
        Boolean[][] visited = new Boolean[tiles.length][tiles[0].length];
        for(int i = 0; i<visited.length; i++){
            for(int j = 0; j<visited[i].length; j++){
                visited[i][j] = false;
            }
        }

        while(!q.isEmpty()){
            Pair<Position, List<Direction>> pair = q.remove();
            Position position = pair.getFirst();
            List<Direction> directions = pair.getSecond();
            if(visited[position.x][position.y]){
                continue;
            }
            else{
                visited[position.x][position.y] = true;
            }
            if(position.equals(end)){
                return directions;
            }
            Position left = new Position(position.x - 1, position.y);
            if(!((left.x < 0) || (shouldAvoid(left, tilesToAvoid)) || visited[left.x][left.y] ||
                    getTiles()[left.x][left.y].getType() != Tile.Type.BLANK)){
                List<Direction> left_directions = new ArrayList<>(directions);
                left_directions.add(Direction.LEFT);
                q.add(new Pair<>(left, left_directions));
            }
            Position right = new Position(position.x + 1, position.y);
            if(!((right.x >= getTiles().length) || (shouldAvoid(right, tilesToAvoid)) || visited[right.x][right.y] ||
                    getTiles()[right.x][right.y].getType() != Tile.Type.BLANK)){
                List<Direction> right_directions = new ArrayList<>(directions);
                right_directions.add(Direction.RIGHT);
                q.add(new Pair<>(right, right_directions));
            }
            Position down = new Position(position.x, position.y - 1);
            if(!((down.y < 0) || (shouldAvoid(down, tilesToAvoid)) || visited[down.x][down.y] ||
                    getTiles()[down.x][down.y].getType() != Tile.Type.BLANK)){
                List<Direction> down_directions = new ArrayList<>(directions);
                down_directions.add(Direction.DOWN);
                q.add(new Pair<>(down, down_directions));
            }
            Position up = new Position(position.x, position.y + 1);
            if (!((up.y >= getTiles()[0].length) || (shouldAvoid(up, tilesToAvoid)) || visited[up.x][up.y] ||
                    getTiles()[up.x][up.y].getType() != Tile.Type.BLANK)) {
                List<Direction> up_directions = new ArrayList<>(directions);
                up_directions.add(Direction.UP);
                q.add(new Pair<>(up, up_directions));
            }
        }
        return null;
    }

    /**
     * Same as {@link GameState#pathTo(Position, Position, Position[])}
     * with {@code tilesToAvoid} defaulted to an empty array.
     * @see GameState#pathTo(Position, Position, Position[])
     */
    public List<Direction> pathTo(Position start, Position end){
        Position[] tilesToAvoid = new Position[0];
        return pathTo(start, end, tilesToAvoid);
    }

    /**
     * Helper method for {@link GameState#pathTo(Position, Position, Position[])} to determine if
     * a {@link Position} is in the given array of {@link Position}s to avoid.
     * @param position {@link Position} to evaluate.
     * @param toAvoid Array of {@link Position}s to avoid.
     * @return True if {@code position} is also inside of {@code toAvoid}, false otherwise.
     */
    private boolean shouldAvoid(Position position, Position[] toAvoid){
        for(Position avoid: toAvoid){
            if(position.equals(avoid)){
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to return what damage would be done at what locations for a hypothetical attack.
     * @param unitPosition The {@link Position} from which the attack would be fired.
     * @param attackPattern The attack pattern with which the attack would be fired.
     * @param dir The {@link Direction} the attack would be fired in.
     * @return A list of {@link Pair}s of (Position, Integer) indicating how much damage would be done at
     * certain locations if a unit with {@code attackPattern} were to attack in {@link Direction} {@code dir}.
     * from position {@code unitPosition}. Positions returned are absolute, not relative to {@code unitPosition}.
     * Returns null if {@code attackPattern} is invalid.
     */
    public List<Pair<Position, Integer>> getPositionsOfAttackPattern(Position unitPosition, int[][] attackPattern, Direction dir){
        if(!isAttackPatternValid(attackPattern)){
            return null;
        }
        List<Pair<Position, Integer>> positionsOfAttackPattern = new ArrayList<Pair<Position, Integer>>();

        // Rotate attack pattern based on direction
        int width = attackPattern.length;
        int height = attackPattern[0].length;
        int[][] newAttackPattern = new int[height][width];
        if (dir == Direction.LEFT) {
            for (int y = 0; y < newAttackPattern[0].length; y++) {
                for (int x = 0; x < newAttackPattern.length; x++) {
                    newAttackPattern[x][y] = attackPattern[y][width - x - 1];
                }
            }
        } else if (dir == Direction.DOWN) {
            for (int y = 0; y < newAttackPattern[0].length; y++) {
                for (int x = 0; x < newAttackPattern.length; x++) {
                    newAttackPattern[x][y] = attackPattern[width - x - 1][height - y - 1];
                }
            }
        } else if (dir == Direction.RIGHT) {
            for (int y = 0; y < newAttackPattern[0].length; y++) {
                for (int x = 0; x < newAttackPattern.length; x++) {
                    newAttackPattern[x][y] = attackPattern[height - y - 1][x];
                }
            }
        } else if (dir == Direction.UP) {
            for (int y = 0; y < newAttackPattern[0].length; y++) {
                for (int x = 0; x < newAttackPattern.length; x++) {
                    newAttackPattern[x][y] = attackPattern[y][x];
                }
            }
        } else { // direction = STAY
            for (int y = 0; y < newAttackPattern[0].length; y++) {
                for (int x = 0; x < newAttackPattern.length; x++) {
                    newAttackPattern[x][y] = 0;
                }
            }
        }

        int x0 = unitPosition.x - (ATTACK_PATTERN_SIZE / 2); // x-coordinate of where attack[0][0] will go
        int y0 = unitPosition.y - (ATTACK_PATTERN_SIZE / 2); // y-coordinate of where attack[0][0] will go
        for(int x = 0; x < attackPattern.length; x++){
            for(int y = 0; y < attackPattern[x].length; y++){
                if(newAttackPattern[x][y] > 0){
                    Position pos = new Position(x0 + x, y0 + y);
                    Pair<Position, Integer> toAdd = new Pair<>(pos, newAttackPattern[x][y]);
                    positionsOfAttackPattern.add(toAdd);
                }
            }
        }
        return positionsOfAttackPattern;
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
        Position positionAfterMovement = new Position(start.x, start.y);
        for(Direction d: movementSteps){
            if(d == Direction.UP){
                positionAfterMovement.y++;
            }
            else if(d == Direction.DOWN){
                positionAfterMovement.y--;
            }
            if(d == Direction.LEFT){
                positionAfterMovement.x--;
            }
            if(d == Direction.RIGHT){
                positionAfterMovement.x++;
            }
            else{
                // d == STAY. Do nothing.
            }
        }
        return positionAfterMovement;
    }

    /**
     * Helper method to indicate if an {@link Decision} object is valid.
     * @param decision The {@link Decision} to evaluate.
     * @param unitId The unitId of the {@link Unit} for which this {@link Decision} should be evaluated.
     * @return A boolean representing the validity of the {@link Decision}.
     */
    public boolean isDecisionValid(Decision decision, int unitId){
        if (decision == null ||
                decision.getMovement() == null ||
                decision.getAttack() == null ||
                Arrays.stream(decision.getMovement()).anyMatch(Objects::isNull) ||
                decision.getMovement().length != units.get(unitId).getSpeed()) {
            return false;
        }
        return true;
    }

    /**
     * Helper method to indicate if a {@link UnitSetup} object is valid.
     * @param setup The {@link UnitSetup} to evaluate.
     * @return A boolean representing the validity of the Decision.
     */
    public boolean isUnitSetupValid(UnitSetup setup){
        int[][] attackPattern;
        boolean[][] terrainPattern;
        int health;
        int speed;
        try {
            attackPattern = setup.getAttackPattern();
            terrainPattern = setup.getTerrainPattern();
            health = setup.getHealth();
            speed = setup.getSpeed();
        }
        catch (NullPointerException e) {
            return false;
        }

        if (attackPattern == null
                || terrainPattern == null
                || health < BASE_HEALTH
                || speed < BASE_SPEED
                || attackPattern.length != ATTACK_PATTERN_SIZE
                || terrainPattern.length != ATTACK_PATTERN_SIZE) {
            return false;
        }

        for (int x = 0; x < ATTACK_PATTERN_SIZE; x++) {
            if (attackPattern[x].length != ATTACK_PATTERN_SIZE || terrainPattern[x].length != ATTACK_PATTERN_SIZE) {
                return false;
            }

            for (int y = 0; y < ATTACK_PATTERN_SIZE; y ++) {
                if ((x == 3 && y == 3) || Math.abs(x-3) + Math.abs(y-3) > 3) {
                    // this position should not have any attack or terrain creation set
                    if (attackPattern[x][y] > 0 || terrainPattern[x][y]) {
                        return false;
                    }
                }
            }
        }

        int sum = 0;
        for (int[] row : attackPattern) {
            for (int cell : row) {
                if (cell < 0 || cell >= DAMAGE_SCALING.length) {
                    return false;
                }  else if (cell > 1) {
                    sum += DAMAGE_SCALING[cell - 1];
                }
            }
        }

        for (boolean[] row : terrainPattern) {
            for (boolean creatingTerrain : row) {
                if (creatingTerrain) {
                    sum += TERRAIN_COST;
                }
            }
        }

        if (health >= HEALTH_SCALING.length || speed >= SPEED_SCALING.length) {
            return false;
        } else if (HEALTH_SCALING[health - 1] + SPEED_SCALING[speed - 1] + sum > MAX_POINTS) {
            return false;
        }

        return true;
    }

    /**
     * Helper method to indicate if an {@code attackPattern} is valid.
     * @param attackPattern 2D array of integers representing an attack pattern.
     * @return True if {@code attackPattern} is valid, false otherwise.
     */
    public boolean isAttackPatternValid(int[][] attackPattern){
        for (int x = 0; x < ATTACK_PATTERN_SIZE; x++) {
            if (attackPattern[x].length != ATTACK_PATTERN_SIZE) {
                return false;
            }

            for (int y = 0; y < ATTACK_PATTERN_SIZE; y ++) {
                if ((x == 3 && y == 3) || Math.abs(x-3) + Math.abs(y-3) > 3) {
                    // this position should not have any attack or terrain creation set
                    if (attackPattern[x][y] > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Helper method to indicate if a {@code terrainPattern} is valid.
     * @param terrainPattern 2D array of integers representing an attack pattern.
     * @return True if {@code terrainPattern} is valid, false otherwise.
     */
    public boolean isTerrainPatternValid(boolean[][] terrainPattern){
        for (int x = 0; x < ATTACK_PATTERN_SIZE; x++) {
            if (terrainPattern[x].length != ATTACK_PATTERN_SIZE) {
                return false;
            }

            for (int y = 0; y < ATTACK_PATTERN_SIZE; y ++) {
                if ((x == 3 && y == 3) || Math.abs(x-3) + Math.abs(y-3) > 3) {
                    // this position should not have any attack or terrain creation set
                    if (terrainPattern[x][y]) {
                        return false;
                    }
                }
            }
        }
        return true;
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

    public int getPlayerNum() {
        return playerNum;
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

    public List<Unit> getUnits() {
        return units;
    }
}

