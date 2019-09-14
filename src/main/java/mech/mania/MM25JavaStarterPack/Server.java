package mech.mania.MM25JavaStarterPack;

import java.util.HashMap;
import java.net.URLDecoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;

@RestController
public class Server {

    private Gson gson = new Gson();
    private HashMap<String, Strategy> games = new HashMap<>();

    // Start a new game
    @RequestMapping(value = "/game_init" , method = RequestMethod.POST)
    public @ResponseBody String game_init(@RequestBody String jsonString) {
        jsonString = decode(jsonString);
        GameInit gameInit = gson.fromJson(jsonString, GameInit.class);
        Strategy newStrategy = new Strategy(gameInit.playerNum);
        games.put(gameInit.gameId, newStrategy);
        UnitSetup[] unitSetup = newStrategy.getSetup();
        return gson.toJson(unitSetup);
    }

    // Process turn of a current game
    @RequestMapping(value = "/turn" , method = RequestMethod.POST)
    public @ResponseBody String turn(@RequestBody String jsonString) {
        jsonString = decode(jsonString);
        GameState gameState = gson.fromJson(jsonString, GameState.class);
        Turn turnResponse = games.get(gameState.getGameId()).doTurn(gameState);
        return gson.toJson(turnResponse);
    }

    // End game
    @RequestMapping(value = "/game_over" , method = RequestMethod.POST)
    public @ResponseBody String game_over(@RequestBody String jsonString) {
        jsonString = decode(jsonString);
        GameOver gameOver = gson.fromJson(jsonString, GameOver.class);
        games.remove(gameOver.gameId);
        return "";
    }

    /** Helper method to decode URL **/
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

/** Helper class for game_init end point **/
class GameInit{
    int playerNum;
    String gameId;
}

/** Helper class for game_over end point **/
class GameOver{
    String gameId;
}
