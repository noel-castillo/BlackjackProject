package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Deal {

//	F I E L D S 

	private Map<Integer, List<Card>> game;
	private Deck deck;
	private int playerTurn;
	private int numberOfPlayers;

//	C O N S T R U C T O R S

	public Deal() {
		deck = new Deck();
		game = new HashMap<>();

	}

//	M A I N 

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);

		Deal deal = new Deal();
		deal.run(kb);

	}

//	M E T H O D S 

	public void run(Scanner kb) {
		dealInitialHands(kb);
	}

	public void dealInitialHands(Scanner kb) {
		deck.createNewDeck();
		deck.shuffle();

		System.out.print("How many players? >> ");
		numberOfPlayers = kb.nextInt();

		createHands(numberOfPlayers);

		for (int c = 0; c <= numberOfPlayers; c++) {

			playerTurn = c;
			for (int i = 0; i < 2; i++) {
				Card card = deck.dealCard();
				game.get(playerTurn).add(card);
			}
			printHand(game, playerTurn);
			getValue(game, playerTurn);
		}
	}

	private void createHands(int numberOfPlayers) {

		for (int c = 0; c <= numberOfPlayers; c++) {
			game.put(c, new ArrayList<>());
		}

	}

	public void printHand(Map<Integer, List<Card>> hand, int playerTurn) {

		int value = 0;
		if (playerTurn == 0) {
			System.out.println("Dealer's Hand: ");
		} else {
			System.out.println("Player " + playerTurn + "'s Hand:");
		}
		for (Card card : hand.get(playerTurn)) {
			System.out.println(card);
			value += card.getValue();
			if (value == 21 && hand.get(playerTurn).size() == 2) {
				System.out.println("Blackjack!");
			}
		}
	}

	public int getValue(Map<Integer, List<Card>> game, int playerTurn) {
		int value = 0;
		for (Card card : game.get(playerTurn)) {
			value += card.getValue();
		}
		System.out.println("Hand value: " + value);
		System.out.println("===================");

		return value;
	}

}
