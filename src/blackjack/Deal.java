package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deal {
	
//	F I E L D S 
	
	List<Card> hand1;
	List<Card> hand2;
	
//	C O N S T R U C T O R S
	
	public Deal() {
		hand1 = new ArrayList<>();
		hand2 = new ArrayList<>();
		
	}
	
//	M A I N 

	public static void main(String[] args) {
		Deal deal = new Deal();
		deal.run();
	}
	
//	M E T H O D S 

	public void run() {
		dealInitialHands();
	}
	
	public void dealInitialHands() {
		Deck deck = new Deck();
		deck.createNewDeck();
		deck.shuffle();

		Map<Integer, List<Card>> game = new HashMap<>();
		game.put(1, hand1);
		game.put(2, hand2);
		int playerTurn = 1;
		for (int i = 0; i < 2; i++) {
			Card c = deck.dealCard();
			hand1.add(c);
			game.put(playerTurn, hand1);
		}
		printHandAndValue(game, playerTurn);
		playerTurn = 2;
		for (int i = 0; i < 2; i++) {
			Card c = deck.dealCard();
			hand2.add(c);
			game.put(playerTurn, hand2);
		}
		printHandAndValue(game, playerTurn);
	}

	public void printHandAndValue(Map<Integer, List<Card>> game, int playerTurn) {

			int value = 0;
			for (Card card : game.get(playerTurn)) {
				System.out.println(card);
				value += card.getValue();
				if (value == 21 && game.get(playerTurn).size() == 2) {
					System.out.println("Blackjack!");
				}
			}
			System.out.println("Player " + playerTurn + " hand value: " + value);
	}

}
