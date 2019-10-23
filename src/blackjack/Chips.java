package blackjack;

import java.util.HashMap;
import java.util.Map;

public class Chips {

//	F I E L D S 

	private Map<String, Integer> chips = new HashMap<>();

//	C O N S T R U C T O R 

	public Chips() {
	}

//	M E T H O D S 

	public void printChips(String player) {
		System.out.println(player + "'s chip count: $" + chips.get(player) + ".00");
	}

	public void printAllChips() {
		System.out.println("===================");
		for (String player : chips.keySet()) {
			if (!player.equals("Dealer")) {
				printChips(player);
			}
		}
		System.out.println("===================");
	}

	public Map<String, Integer> getChips() {
		return chips;
	}

	public void generateChips(Hand hands) {
		for(String player : hands.getPlayersHand().keySet()) {
			chips.put(player, 50);
		}
		
	}

}
