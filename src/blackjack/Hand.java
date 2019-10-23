package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Hand {

//	F I E L D S

	private Map<String, List<Card>> playersHand = new HashMap<>();
	private static int hidden = 0;

//	C O N S T R U C T O R 

	public Hand() {

	}

//	M E T H O D S

	public void dealHands(Deck deck, Scanner kb, Map<String, Integer> chips) {
		if (deck.checkDeckSize() < (playersHand.keySet().size() * 5)) {
			System.out.println("Deck is running low.");
			System.out.println("Creating new deck.");
			deck.deckCreation(kb);
			System.out.println("Deck size: " + deck.checkDeckSize() + " cards.");
		}

		deck.shuffle();

		for (String player : playersHand.keySet()) {
			for (int i = 0; i < 2; i++) {
				Card card = deck.dealCard();
				playersHand.get(player).add(card);
			}
			printHandAndValue(player);
		}

		checkBlackjack(chips);
	}

	public void dealInitialHands(Scanner kb, Deck deck, Map<String, Integer> chips) {
		System.out.print("How many players? >> ");
		int numberOfPlayers = kb.nextInt();

		for (int c = 0; c <= numberOfPlayers; c++) {
			if (c == 0) {
				List<Card> hand = new ArrayList<>();
				for (int i = 0; i < 2; i++) {
					Card card = deck.dealCard();
					hand.add(card);
				}
				playersHand.put("Dealer", hand);
			} else {
				System.out.print("Enter name of player " + c + " >> ");
				String name = kb.next();
				List<Card> hand = new ArrayList<>();
				for (int i = 0; i < 2; i++) {
					Card card = deck.dealCard();
					hand.add(card);
				}
				playersHand.put(name, hand);
			}
		}
		checkBlackjack(chips);
		for (String player : playersHand.keySet()) {
			printHandAndValue(player);
		}
	}

	public void clearHands() {
		for (String player : playersHand.keySet()) {
				playersHand.get(player).removeAll(playersHand.get(player));
		}
		hidden = 0;
	}

	public int getHandValue(String player) {
		int value = 0;
		for (Card card : playersHand.get(player)) {
			value += card.getValue();
		}
		for (Card card : playersHand.get(player)) {
			if (value > 21) {
				if (card.getRank() == Rank.ACE) {
					value -= 10;
				}
			}
		}
		return value;
	}

	public void printHandAndValue(String player) {

		if (!player.equals("Dealer")) {
			System.out.println(player + "'s Hand:");
			for (Card card : playersHand.get(player)) {
				System.out.println(card);
			}
			System.out.println("Hand value: " + getHandValue(player));
			System.out.println("===================");
		} else {
			System.out.println("Dealer's Hand: ");
			for (Card card : playersHand.get(player)) {
				if (hidden != 0) {
					System.out.println(card);
				}
				hidden++;
			}
			if (hidden > 2) {
				System.out.println("Hand value: " + getHandValue(player));
			}
			System.out.println("========================");
		}
	}

	public boolean checkBustedHand(String player) {

		if (getHandValue(player) > 21) {
			System.out.println(player + " BUSTED!");
			System.out.println("========================");
			if (!player.equals("Dealer")) {
				return false;
			}
		}

		return true;

	}

	public void checkWinner(Map<String, Integer> chips) {

		int highest = getHandValue("Dealer");
		for (String player : playersHand.keySet()) {
			if (!player.equals("Dealer")) {
//				If both Dealer and player get Blackjack, then results in a PUSH(Draw).
				if (getHandValue(player) == highest && getHandValue(player) == 21 && playersHand.get(player).size() == 2
						&& playersHand.get("Dealer").size() == 2) {
					System.out.println(player + " results in PUSH against the Dealer!");
					int balance = chips.get(player);
					balance += 5;
					chips.put(player, balance);
					System.out.println("Chips returned to " + player);
					System.out.println(player + "'s balance: $" + chips.get(player));
				}
//				Dealer wins by Blackjack.
				else if (highest == 21 && playersHand.get(player).size() == 2) {
					System.out.println("The house wins against " + player);
				}
//				Player wins if their hand is greater than the dealer's and not over 21.
				else if (getHandValue(player) > highest && getHandValue(player) <= 21 && highest <= 21) {
					System.out.println(player + " beats the Dealer!");
					int balance = chips.get(player);
					balance += 10;
					chips.put(player, balance);
					System.out.println(player + " wins $10.00");
					System.out.println(player + "'s balance: $" + chips.get(player));
				}
//				Player wins if their hand is equal to the dealer's, but the player got a Blackjack
				else if (getHandValue(player) == highest && getHandValue(player) == 21
						&& playersHand.get(player).size() == 2) {
					System.out.println(player + " beats the Dealer!");
					int balance = chips.get(player);
					balance += 10;
					chips.put(player, balance);
					System.out.println(player + " wins $10.00");
					System.out.println(player + "'s balance: $" + chips.get(player));
				}
//				Both Dealer and Player have same hand value and neither BUSTED. Results in PUSH.
				else if (getHandValue(player) == highest && getHandValue(player) <= 21 && highest <= 21) {
					System.out.println(player + " results in PUSH against the Dealer!");
					int balance = chips.get(player);
					balance += 5;
					chips.put(player, balance);
					System.out.println("Chips returned to " + player);
					System.out.println(player + "'s balance: $" + chips.get(player));
				}
//				Dealer wins if their hand is greater in value and neither BUSTED.
				else if (getHandValue(player) < highest && getHandValue(player) <= 21 && highest <= 21) {
					System.out.println("The house wins against " + player);
				}
//				Dealer wins because the player BUSTED
				else if (getHandValue(player) > 21 && !player.equals("Dealer")) {
					System.out.println(player + " losses due to BUST");
				}
//				Dealer BUSTED and lost to any players who did not BUST
				else {
					System.out.println("The house losses due to BUST against " + player);
					int balance = chips.get(player);
					balance += 10;
					chips.put(player, balance);
					System.out.println(player + " wins $10.00");
					System.out.println(player + "'s balance: $" + chips.get(player));
				}
			}
		}

		clearHands();
	}

	public void checkBlackjack(Map<String, Integer> chips) {

		for (String player : playersHand.keySet()) {
			int value = 0;
			for (Card card : playersHand.get(player)) {
				value += card.getValue();
			}

			if (value == 21) {
				System.out.println(player + " got a blackjack!");
				if (player.equals("Dealer")) {
					printHandAndValue(player);
					checkWinner(chips);
				}
			}
		}
	}

	public Map<String, List<Card>> getPlayersHand() {
		return playersHand;
	}
	

}
