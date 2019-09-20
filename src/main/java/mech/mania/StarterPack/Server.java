package mech.mania.StarterPack;

import mech.mania.API.*;

import java.util.HashMap;
import java.net.URLDecoder;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;

/**
 * A class to handle HTTP POST requests
 */
@RestController
public class Server {

    private Gson gson = new Gson();
    private HashMap<String, Strategy> games = new HashMap<>();

    /**
     * Method to handle POST requests to the /game_init endpoint to begin a new game.
     * @param jsonString A JSON object which contains a player number and game ID.
     * @return A JSON object encoding the {@link UnitSetup} array returned by the contestant's {@link Strategy}
     */
    @RequestMapping(value = "/game_init" , method = RequestMethod.POST)
    public @ResponseBody String game_init(@RequestBody String jsonString) {
        jsonString = decode(jsonString);
        GameInit gameInit = gson.fromJson(jsonString, GameInit.class);
        Strategy newStrategy = new Strategy(gameInit);
        games.put(gameInit.gameId, newStrategy);
        UnitSetup[] unitSetup = newStrategy.getSetup(gameInit.playerNum);
        return gson.toJson(unitSetup);
    }

    /**
     * Method to handle POST requests to the /turn endpoint to execute actions for a turn.
     * @param jsonString A JSON object which contains the current game state.
     * @return A JSON object encoding the {@link Decision} array returned by the contestant's {@link Strategy}
     */
    @RequestMapping(value = "/turn" , method = RequestMethod.POST)
    public @ResponseBody String turn(@RequestBody String jsonString) {
        jsonString = decode(jsonString);
        GameState gameState = gson.fromJson(jsonString, GameState.class);
        Decision[] turnResponse = games.computeIfAbsent(gameState.getGameId(), gameId -> new Strategy(gameState)).doTurn(gameState);
        System.out.println("Turn response: " + gson.toJson(turnResponse));
        return gson.toJson(turnResponse);
    }

    /**
     * Method to handle POST requests to the /game_over endpoint to end a game.
     * @param jsonString A JSON object which contains a game ID.
     * @return An empty String.
     */
    @RequestMapping(value = "/game_over" , method = RequestMethod.POST)
    public @ResponseBody String game_over(@RequestBody String jsonString) {
        jsonString = decode(jsonString);
        GameOver gameOver = gson.fromJson(jsonString, GameOver.class);
        games.remove(gameOver.gameId);
        return "";
    }

    /**
     * Method to handle POST requests to the /health endpoint to check that the server is running correctly.
     * @return "200"
     */
    @RequestMapping(value = "/health" , method = RequestMethod.GET)
    public @ResponseBody String health() {
        return "200";
    }

    /**
     * A helper method to decode the json String from its URL encoding.
     * @param jsonString The URL encoded String with a trailing '='
     * @return The URL decoded String without a trailing '='
     */
    private String decode(String jsonString) {
        try {
            jsonString = URLDecoder.decode(jsonString, "UTF-8");
            if(jsonString.substring(jsonString.length()-1).equals("=")) {
                // Remove trailing '='
                jsonString = jsonString.substring(0, jsonString.length() - 1);
            }
        } catch (Exception e) {
        }
        return jsonString;
    }
}

/** A helper class for the /game_init end point **/
class GameInit{
    int playerNum;
    String gameId;
}

/** A helper class for the /game_over end point **/
class GameOver{
    String gameId;
    String result; // Will be "TIE", "WIN", or "LOSE"
}
