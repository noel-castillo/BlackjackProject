package blackjack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Players {
	
//	THIS CLASS IS NOT YET IMPLEMENTED INTO THE BLACKJACK PROJECT. 
	
	
//	M E T H O D S

	public static Map<String, List<Card>> createPlayers(Scanner kb) {
		Map<String, List<Card>> players = new HashMap<>();
		System.out.print("How many players? >> ");
		int numberOfPlayers = kb.nextInt();
		
		players.put("Dealer", null);
		
		for (int c = 1; c <= numberOfPlayers; c++) {
			System.out.print("Enter name of player " + c + " >> ");
			String name = kb.next();
			players.put(name, null);
		}
		
		return players;

	}
}
