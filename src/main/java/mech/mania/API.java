//package mech.mania;

//import com.google.gson.Gson;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
public class API {

	private Game game; //instance of game
	private String playerName; //playerName
	private int playerNum; //Set playerNum from JSON

	public game_API(Game g, String playerName){
		this.game = g;
		this.playerName = playerName;
	}

	public void getTurnDecision(){ 
		/*
		Their code here
		*/
	}

	public void getUnitSetups(){
		/*
		Their code here
		*/
	}

	public Direction[] pathTo(Position start, Position end, Position[] tilesToAvoid){
		Queue<Tuple> q = new LinkedList<Tuple>();
		Tuple<Position, ArrayList<String>> tuple = new Tuple<Position, ArrayList<String>>(start, new ArrayList<String>());
		boolean[][] visited = new boolean[12][12];
		for(int i = 0; i < visited.length; i++){
			for(int j = 0; j < visited[i].length; j++){
				visited[i][j] = false;
			}
		}

		while(q.size() > 0){
			Tuple<Position, ArrayList<String>> tuple = q.pop();
			Position position = tuple.getX();
			ArrayList<String> directions = tuple.getY();
			if(visited[position.getY()][position.getX()]){
				continue;
			}else{
				visited[position.getY()][posiion.getX()] = true;
			}
			if(position.equals(end)){
				return directions;
			}
		}

		return null;
	}

	// Helper function
	private Position getPositionAfterMovement(Position init, Direction[] movementSteps){
		Position p = new Position(init.getX(), init.getY());
		for(int i = 0; i < movementSteps.length; i++){
			if(game.getMap().inBounds(p.getNewPosition(movementSteps[i]))){
				p = p.getNewPosition(movementSteps[i]);
			}

		}
		return p;
	}

	public Unit[] getMyUnits() {
		if(playerNum == 1){
			return game.getP1Units();
		}else{
			return game.getP2Units();
		}		
		return game.getP1Units();
	}
	
	public Unit[] getEnemyUnits() {
		if(playerNum == 1){
			return game.getP1Units();
		}else{
			return game.getP2Units();
		}
	}
	
	public Unit getUnitAt(Position pos) {
		return game.getMap().tileAt(pos).getUnit();
	}
	
	public Tile getTile(Position pos) {
		return game.getMap().tileAt(pos);
	}
}
