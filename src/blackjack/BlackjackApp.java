package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BlackjackApp {

//	F I E L D S 

	private Map<Integer, List<Card>> game = new HashMap<>();
	private Deck deck = new Deck();
	private Chips chips = new Chips();
	private int playerTurn;
	private int numberOfPlayers;
	private static Scanner kb = new Scanner(System.in);

//	The purpose of the variable hidden is to facilitate when the dealer displays their cards and hand value.
	private static int hidden = 0;

//	M A I N 

	public static void main(String[] args) {

		BlackjackApp app = new BlackjackApp();
		app.run();
		kb.close();
	}

//	M E T H O D S 

	public void run() {

		deck.deckCreation(kb);
		System.out.println("Deck size: " + deck.checkDeckSize() + " cards.");
		createHands();
		dealHands();
		playGame();

	}

//	proceed method is to give the user the option to proceed to 
//	another iteration of the program round.
//	Or to call the headHome method. 
	public void proceed() {

		System.out.println("\nSelect an option:");
		System.out.println("1. Next round.");
		System.out.println("2. Head home.");
		try {
			int proceed = kb.nextInt();
			switch (proceed) {
			case 1:
				dealHands();
				playGame();
				break;
			case 2:
				headHome();
				proceed();
				break;
			default:
				System.out.println("Invalid input");
				proceed();
				break;
			}
		} catch (InputMismatchException e) {
			kb.nextLine();
			System.out.println("Invalid input.");
			proceed();
		}
	}

//	headHome method will display all current players by the key element in the playerTurn map
//	which is an integer with their entered name being the value. 
//	User can then select the appropriate player and the key & value will then be removed from the map.
	public void headHome() {
		System.out.println("Who is heading home?");
		for (int p : chips.getPlayerTurn().keySet()) {
			if (p != 0) {
				System.out.println(p + ". " + chips.getPlayerTurn().get(p));
			}
		}
		System.out.println(0 + ". All players.");
		System.out.print(">>");
		int playerLeaving = kb.nextInt();
		if (playerLeaving == 0) {
			System.out.println("Adios amigos!");
			kb.close();
			System.exit(0);
		} else {
			System.out.println("Adios " + chips.getPlayerTurn().get(playerLeaving));
			chips.getPlayerTurn().remove(playerLeaving);
		}
	}

//	dealHands: Will reset the integer variable hidden to hide the Dealer's initial card and hand value.
//	Will also reduce the value for each key in the chipsMap by 5 as the value represents chip count. 
//	It will also call a shuffle method to the deck, print out the hand and value for each player, and
//	call the checkBlackjack method after displaying all hands and card values, which is important
//	to be done afterwards for the fluidity of the game. 
	public void dealHands() {
		deck.shuffle();
		hidden = 0;

		printAllChips();
		for (int c : chips.getPlayerTurn().keySet()) {
			playerTurn = c;
			for (int i = 0; i < 2; i++) {
				Card card = deck.dealCard();
				game.get(playerTurn).add(card);
			}
			int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
			balance += -5;
			chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
			if (c != 0) {
				System.out.println(chips.getPlayerTurn().get(c) + " bets $5.00 against the House.");
				printChips(c);
			}
			printHandAndValue(playerTurn);
		}

		checkBlackjack();
	}

//	clearHands method will remove all values of List<Card> for the entire keySet of Map<Integer, List<Card>> game
	public void clearHands() {
		for (int c : chips.getPlayerTurn().keySet()) {
			game.get(c).removeAll(game.get(c));
		}
	}

//	The importance of createHands is in the creation of a 'player name' for the amount of players prompted to the user
//	Each player name will be made into a key for the chipsMap in order to record their ongoing integer value that represents chips
	private void createHands() {
		System.out.print("How many players? >> ");
		numberOfPlayers = kb.nextInt();

		game.put(0, new ArrayList<>());
		chips.getChipsMap().put("Dealer", 1000000);
		chips.getPlayerTurn().put(0, "Dealer");
		for (int c = 1; c <= numberOfPlayers; c++) {
			game.put(c, new ArrayList<>());
			System.out.print("Enter name of player " + c + " >>");
			String name = kb.next();
			chips.getChipsMap().put(name, 50);
			chips.getPlayerTurn().put(c, name);
		}

	}

//	Program needs a separate checkBlackjack method in order to call it at an opportune time.
//	Within this method is an if statement where if the dealer gets a Blackjack, then the Dealer
//	wins and the round is over. If a player gets a Blackjack, the round cannot be over right away
//	because there may still be other players competing against the Dealer.
	public void checkBlackjack() {

		for (int c : chips.getPlayerTurn().keySet()) {
			int value = 0;
			for (Card card : game.get(c)) {
				value += card.getValue();
			}
			if (value == 21) {
				System.out.println(chips.getPlayerTurn().get(c) + " got a blackjack!");
				if (c == 0) {
					clearHands();
					proceed();
				}
			}
		}
	}

	public void printHandAndValue(int playerTurn) {

		if (playerTurn > 0) {
			System.out.println(chips.getPlayerTurn().get(playerTurn) + "'s Hand:");
			for (Card card : game.get(playerTurn)) {
				System.out.println(card);
			}
			System.out.println("Hand value: " + getValue(playerTurn));
			System.out.println("===================");
		} else {
			System.out.println("Dealer's Hand: ");
			for (Card card : game.get(playerTurn)) {
				if (hidden != 0) {
					System.out.println(card);
				}
				hidden++;
			}
			if (hidden > 2) {
				System.out.println("Hand value: " + getValue(playerTurn));
			}
			System.out.println("========================");
		}
	}

//	checkBusted: The importance for checkBusted to be a boolean, is to end the player's turn if their hand's value
//	goes over 21. They will continue to have options during their round so long as checkBusted 
//	returns true.
	public boolean checkBusted(int playerTurn) {

		int value = getValue(playerTurn);
		if (value > 21) {
			System.out.println(chips.getPlayerTurn().get(playerTurn) + " BUSTED!");
			System.out.println("========================");
			if (playerTurn != 0) {
				return false;
			}
		}

		return true;

	}

//	getValue: Will return the value of a hand for the given integer key respective to game Map.
	public int getValue(int playerTurn) {
		int value = 0;
		for (Card card : game.get(playerTurn)) {
			value += card.getValue();
		}
		for (Card card : game.get(playerTurn)) {
			if (value > 21) {
				if (card.getRank() == Rank.ACE) {
					value -= 10;
				}
			}
		}
		return value;
	}

	public void playGame() {

		for (int c : chips.getPlayerTurn().keySet()) {
			if (c != 0 && getValue(c) != 21 && game.get(c).size() >= 2) {
				boolean proceed = true;
				while (proceed) {
					System.out.println("1. Hit.");
					System.out.println("2. Stand.");
					System.out.println("3. Display hand.");
					System.out.println("4. Display chip count.");
					System.out.print(chips.getPlayerTurn().get(c) + " select an option >> ");

					String choice = kb.next();

					switch (choice) {
					case "1":
						System.out.println(chips.getPlayerTurn().get(c) + " HITS!");
						Card card = deck.dealCard();
						game.get(c).add(card);
						printHandAndValue(c);
						proceed = checkBusted(c);
						break;
					case "2":
						System.out.println(chips.getPlayerTurn().get(c) + " STANDS!");
						System.out.println("========================");
						proceed = false;
						break;
					case "3":
						printHandAndValue(c);
						break;
					case "4":
						printChips(c);
						break;
					default:
						System.out.println("Invalid Input.");
						break;
					}
				}
			}
		}

		printHandAndValue(0);
		for (int c : chips.getPlayerTurn().keySet()) {
			if (getValue(0) < getValue(c) && getValue(0) < 17 && getValue(c) < 21) {
				System.out.println("Dealer HITS!");
				Card card = deck.dealCard();
				game.get(0).add(card);
				printHandAndValue(0);
				checkBusted(0);
			} else if (c == chips.getPlayerTurn().size() && getValue(0) <= 21) {
				System.out.println("Dealer STANDS!");
			}
		}

		checkWinner();

	}

//	checkWinner: There is a set order of what constitutes a win or a loss. The series of if - else if
//	will select the appropriate conditions for every player vs the Dealer. 
	public void checkWinner() {

		int highest = getValue(0);
		for (int c : chips.getPlayerTurn().keySet()) {
			if (getValue(c) > highest && getValue(c) <= 21 && c != 0 && highest <= 21) {
				System.out.println(chips.getPlayerTurn().get(c) + " beats the Dealer!");
				int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
				balance += 10;
				chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
				System.out.println(chips.getPlayerTurn().get(c) + " wins $10.00");
				printChips(c);
			} else if (getValue(c) == highest && getValue(c) <= 21 && c != 0 && highest <= 21) {
				System.out.println(chips.getPlayerTurn().get(c) + " results in PUSH against the Dealer!");
				int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
				balance += 5;
				chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
				System.out.println("Chips returned to " + chips.getPlayerTurn().get(c));
				printChips(c);
			} else if (getValue(c) < highest && getValue(c) <= 21 && c != 0 && highest <= 21) {
				System.out.println("The house wins against " + chips.getPlayerTurn().get(c));
			} else if (getValue(c) > 21 && c != 0) {
				System.out.println(chips.getPlayerTurn().get(c) + " losses due to BUST");
			} else if (c != 0) {
				System.out.println("The house losses due to BUST against " + chips.getPlayerTurn().get(c));
				int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
				balance += 10;
				chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
				System.out.println(chips.getPlayerTurn().get(c) + " wins $10.00");
				printChips(c);
			}
		}

		clearHands();
		proceed();
	}

//	printChips will print the chip count for an individual player taking in the int key as a parameter to identify player
	public void printChips(int player) {
		System.out.println(chips.getPlayerTurn().get(player) + "'s chip count: $"
				+ chips.getChipsMap().get(chips.getPlayerTurn().get(player)) + ".00");
	}

//	printAllChips will iterate through the keySet for playerTurn map and print out all chip counts.
	public void printAllChips() {
		System.out.println("===================");
		for (int c : chips.getPlayerTurn().keySet()) {
			if (c != 0) {
				printChips(c);
			}
		}
		System.out.println("===================");
	}
}
