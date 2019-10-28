package blackjack;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class BlackjackApp {

//	F I E L D S 

	private static Scanner kb = new Scanner(System.in);
	private Chips chips = new Chips();
	private Deck deck = new Deck();
	private Hand hands = new Hand();

//	M A I N 

	public static void main(String[] args) {

		BlackjackApp app = new BlackjackApp();
		app.playGame();
		kb.close();
	}
	
//	C O N S T R U C T O R
	
	public BlackjackApp() {
		System.out.println("Welcome to the Blackjack Table");
		deck.deckCreation(kb);
		System.out.println("Deck size: " + deck.checkDeckSize() + " cards.");
		hands.dealInitialHands(kb, deck, chips.getChips());
		chips.generateChips(hands);
	}

//	M E T H O D S 


	public void proceed() {

		System.out.println("\nSelect an option:");
		System.out.println("1. Next round.");
		System.out.println("2. Head home.");
		try {
			int proceed = kb.nextInt();
			switch (proceed) {
			case 1:
				hands.dealHands(deck, kb, chips.getChips());
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

	public void headHome() {
		Map<Integer, String> whoToSendHome = new HashMap<>();
		System.out.println("Who is heading home?");
		int counter = 1;
		for (String player : hands.getPlayersHand().keySet()) {
			if (!player.equals("Dealer")) {
				System.out.println(counter + ". " + player);
			}
			whoToSendHome.put(counter, player);
			counter++;
			
		}
		System.out.println(0 + ". All players.");
		System.out.print(">>");
		try {
			int playerLeaving = kb.nextInt();
			if (playerLeaving == 0) {
				System.out.println("Adios amigos!");
				kb.close();
				System.exit(0);
			} else {
				System.out.println("Adios " + whoToSendHome.get(playerLeaving));
				hands.getPlayersHand().remove(whoToSendHome.get(playerLeaving));
			}
		} catch (Exception e) {
			kb.nextLine();
			System.out.println("Invalid input");
			proceed();
		}
	}

	public void playGame() {
		for (String player : hands.getPlayersHand().keySet()) {
			if (!player.equals("Dealer") && hands.getHandValue(player) != 21 && hands.getPlayersHand().get(player).size() >= 2) {
				boolean proceed = true;
				while (proceed) {
					System.out.println("1. Hit.");
					System.out.println("2. Stand.");
					System.out.println("3. Display hand.");
					System.out.println("4. Display chip count.");
					System.out.print(player + " select an option >> ");

					String choice = kb.next();

					switch (choice) {
					case "1":
						System.out.println(player + " HITS!");
						Card card = deck.dealCard();
						hands.getPlayersHand().get(player).add(card);
						hands.printHandAndValue(player);
						proceed = hands.checkBustedHand(player);
						break;
					case "2":
						System.out.println(player + " STANDS!");
						System.out.println("========================");
						proceed = false;
						break;
					case "3":
						hands.printHandAndValue(player);
						break;
					case "4":
						chips.printChips(player);
						break;
					default:
						System.out.println("Invalid Input.");
						break;
					}
				}
			}
		}
		hands.printHandAndValue("Dealer");
		for (String player : hands.getPlayersHand().keySet()) {
			while (hands.getHandValue("Dealer") < hands.getHandValue(player) && hands.getHandValue("Dealer") < 17
					&& hands.getHandValue(player) < 21) {
				System.out.println("Dealer HITS!");
				Card card = deck.dealCard();
				hands.getPlayersHand().get("Dealer").add(card);
				hands.printHandAndValue("Dealer");
				hands.checkBustedHand("Dealer");
			}
		}
		if (hands.getHandValue("Dealer") >= 17 && hands.getHandValue("Dealer") <= 21) {
			System.out.println("Dealer STANDS!");
		}

		hands.checkWinner(chips.getChips());
		proceed();

	}

}
