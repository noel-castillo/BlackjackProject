package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BlackjackApp {

//	F I E L D S 

	private Map<Integer, List<Card>> game;
	private Deck deck;
	private int playerTurn;
	private int numberOfPlayers;
	private static Scanner kb = new Scanner(System.in);
	private static boolean hidden = true;

//	C O N S T R U C T O R 

	public BlackjackApp() {
		game = new HashMap<>();
		deck = new Deck();
		deck.deckCreation(kb);
		System.out.println(deck.checkDeckSize());
	}

//	M A I N 

	public static void main(String[] args) {

		BlackjackApp app = new BlackjackApp();
		app.run();
		kb.close();
	}

//	M E T H O D S 

	public void run() {

		createHands();
		dealHands();
		playGame();

	}

	public void proceed() {

		System.out.println("\nSelect an option:");
		System.out.println("1. Next round.");
		System.out.println("2. Head home.");
		try {
			int proceed = kb.nextInt();
			switch (proceed) {
			case 1:
				run();
				break;
			case 2:
				System.out.println("Adios amigo!");
				kb.close();
				System.exit(0);
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

	public void dealHands() {
		deck.createNewDeck();
		deck.shuffle();

		for (int c = 0; c <= numberOfPlayers; c++) {

			playerTurn = c;
			for (int i = 0; i < 2; i++) {
				Card card = deck.dealCard();
				game.get(playerTurn).add(card);
			}
			printHandAndValue(playerTurn);
		}

		checkBlackjack();
	}

	private void createHands() {
		System.out.print("How many players? >> ");
		numberOfPlayers = kb.nextInt();

		for (int c = 0; c <= numberOfPlayers; c++) {
			game.put(c, new ArrayList<>());
		}

	}

	public void checkBlackjack() {

		for (int c = 0; c <= numberOfPlayers; c++) {
			int value = 0;
			for (Card card : game.get(c)) {
				value += card.getValue();
			}
			if (value == 21) {
				if (c > 0) {
					System.out.println("Player " + c + " got a blackjack!");
				} else {
					System.out.println("Dealer got a blackjack!");
				}

				checkWinner();
			}
		}
	}

	public void printHandAndValue(int playerTurn) {

		if (playerTurn > 0) {
			System.out.println("Player " + playerTurn + "'s Hand:");
			for (Card card : game.get(playerTurn)) {
				System.out.println(card);
			}
			System.out.println("Hand value: " + getValue(playerTurn));
			System.out.println("===================");
		} else {
			System.out.println("Dealer's Hand: ");
			for (Card card : game.get(playerTurn)) {
				if (!hidden) {
					System.out.println(card);
				}
				hidden = false;
			}
			if (!hidden && game.get(1).size() > 0) {
				System.out.println("Hand value: " + getValue(playerTurn));
			}
			System.out.println("========================");
		}
	}

	public boolean checkBusted(int playerTurn) {

		int value = getValue(playerTurn);
		if (value > 21) {
			if (playerTurn > 0) {
				System.out.println("Player " + playerTurn + " BUSTED!");
				System.out.println("========================");
				return false;
			} else {
				System.out.println("Dealer BUSTED!");
				System.out.println("========================");
			}
		}
		return true;

	}

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

		for (int c = 1; c <= numberOfPlayers; c++) {
			boolean proceed = true;
			while (proceed) {
				System.out.println("1. Hit.");
				System.out.println("2. Stand.");
				System.out.println("3. Display hand.");
				System.out.print("Player " + c + " select an option >> ");

				String choice = kb.next();

				switch (choice) {
				case "1":
					System.out.println("Player " + c + " HITS!");
					Card card = deck.dealCard();
					game.get(c).add(card);
					printHandAndValue(c);
					proceed = checkBusted(c);
					break;
				case "2":
					System.out.println("Player " + c + " STANDS!");
					System.out.println("========================");
					proceed = false;
					break;
				case "3":
					printHandAndValue(c);
					break;
				default:
					System.out.println("Invalid Input.");
					break;
				}
			}
		}

		printHandAndValue(0);
		for (int c = 1; c <= numberOfPlayers; c++) {
			if (getValue(0) < getValue(c) && getValue(0) < 17 && getValue(c) < 21) {
				System.out.println("Dealer HITS!");
				Card card = deck.dealCard();
				game.get(0).add(card);
				printHandAndValue(0);
				checkBusted(0);
			} else if (c == numberOfPlayers && getValue(0) <= 21) {
				System.out.println("Dealer STANDS!");
			}
		}

		checkWinner();

	}

	public void checkWinner() {

		String winner = "";
		int draw = 0;
		int highest = 0;
		for (int c = 0; c <= numberOfPlayers; c++) {
			if (getValue(c) > highest && getValue(c) <= 21) {
				highest = getValue(c);
				if (c > 0) {
					winner = "Player " + c + " is the winner!";
				} else {
					winner = "The house wins!";
				}
				draw = 0;
			} else if (getValue(c) == highest) {
				draw++;
			}
		}
		if (draw == 0) {
			System.out.println(winner);
		} else {
			System.out.println("Push! There is no winner.");
		}
		proceed();
	}
}
