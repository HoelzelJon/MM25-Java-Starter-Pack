package mech.mania.MM25JavaStarterPack;

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
