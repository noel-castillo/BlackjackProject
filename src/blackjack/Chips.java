package blackjack;

import java.util.HashMap;
import java.util.Map;

public class Chips {

//	F I E L D S 

//	Map<Str, Int> chipsMap: Will keep track of each player's name as a key, and the chip count respective to the player's name.
//	Same name users will pose a problem to the functionality of the program. 
	private Map<String, Integer> chipsMap;

//	Map<Int, Str> playerTurn: Will keep track of the player's numerical ID and the name respective to that ID. This field can be made
//	redundant with chipsMap replacing its functionality entirely as the code gets worked on / cleaned up.
	private Map<Integer, String> playerTurn;

//	C O N S T R U C T O R 

	public Chips() {
		chipsMap = new HashMap<>();
		playerTurn = new HashMap<>();
	}

//	M E T H O D S 

	public Map<String, Integer> getChipsMap() {
		return chipsMap;
	}

	public void setChipsMap(Map<String, Integer> chipsMap) {
		this.chipsMap = chipsMap;
	}

	public Map<Integer, String> getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(Map<Integer, String> playerTurn) {
		this.playerTurn = playerTurn;
	}

}
