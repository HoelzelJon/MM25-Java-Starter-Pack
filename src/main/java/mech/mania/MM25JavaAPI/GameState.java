package mech.mania.MM25JavaAPI;

import java.util.ArrayList;
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

    public List<Unit> getPlayerUnits(int playerNum){
        List<Unit> playerUnits = new ArrayList<>();
        for(Unit u : units){
            if(u.getPlayerNum() == playerNum) {
                playerUnits.add(u);
            }
        }
        return playerUnits;
    }
}

