package mech.mania.API;

/**
 * Class to hold the the 2D array of {@link Tile} objects.
 * Created to be deserialized from the game state JSON object.
 */
public class Board {
    private String gameId;
    private Tile[][] tiles; // 2-D array of all tiles on the board

    public String getGameId() {
        return gameId;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
