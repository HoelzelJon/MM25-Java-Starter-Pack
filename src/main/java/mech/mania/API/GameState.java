package mech.mania.API;

/**
 * Class to store the state of the game.
 * Created to be deserialized from the game state JSON object.
 */
public class GameState {
    private Board map; // current map
    private Unit[] p1Units; // array of Player 1's units
    private Unit[] p2Units; // array of Player 2's units
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

    public Unit[] getP1Units() {
        return p1Units;
    }

    public Board getMap() {
        return map;
    }

    public Unit[] getP2Units() {
        return p2Units;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setMap(Board map) {
        this.map = map;
    }

    public void setP1Units(Unit[] p1Units) {
        this.p1Units = p1Units;
    }

    public void setP2Units(Unit[] p2Units) {
        this.p2Units = p2Units;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public void setTurnsTaken(int turnsTaken) {
        this.turnsTaken = turnsTaken;
    }
}

