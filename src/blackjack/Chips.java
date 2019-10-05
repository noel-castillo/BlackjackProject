package blackjack;

import java.util.HashMap;
import java.util.Map;

public class Chips {
	
//	F I E L D S 
	
	private Map<String, Integer> chipsMap;
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
