package blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BlackjackApp {

//	F I E L D S 

	private Table table = new Table();
	private Deck deck = new Deck();
	private Chips chips = new Chips();
	private static Scanner kb = new Scanner(System.in);

//	int hidden: Facilitates when the dealer displays their cards and hand value.
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

//	proceed(): Gives the user the option to proceed to another round or to send players home. Will continue
//	to loop back to this method until next round is selected or if all players going home is selected.
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

//	headHome(): Display all current players and the user can select which player to send home and remove from the game.
//	User can also select all players and end the game entirely. 
	public void headHome() {
		System.out.println("Who is heading home?");
		for (int p : chips.getPlayerTurn().keySet()) {
			if (p != 0) {
				System.out.println(p + ". " + chips.getPlayerTurn().get(p));
			}
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
				System.out.println("Adios " + chips.getPlayerTurn().get(playerLeaving));
				chips.getPlayerTurn().remove(playerLeaving);
			}
		} catch (InputMismatchException e) {
			kb.nextLine();
			System.out.println("Invalid input");
			proceed();
		}
	}

//	dealHands(): Will shuffle the deck, reset hidden variable, print current chip count for all players.
//	Then deal 2 cards to each player and the Dealer. Each player's chip count will be reduced by 5 to simulate buying
//	into a round. Each player's individual chip count will be displayed once they buy in. 
//	Then every hand will be checked for a Blackjack winner.
	public void dealHands() {
		deck.shuffle();
		hidden = 0;

		printAllChips();
		for (int c : chips.getPlayerTurn().keySet()) {
			for (int i = 0; i < 2; i++) {
				Card card = deck.dealCard();
				table.getCardsOnTable().get(c).add(card);
			}
			int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
			balance += -5;
			chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
			if (c != 0) {
				System.out.println(chips.getPlayerTurn().get(c) + " bets $5.00 against the House.");
				printChips(c);
			}
			printHandAndValue(c);
		}

		checkBlackjack();
	}

//	clearHands(): Will remove all cards from each player and the Dealer's hand.
	public void clearHands() {
		for (int c : chips.getPlayerTurn().keySet()) {
			table.getCardsOnTable().get(c).removeAll(table.getCardsOnTable().get(c));
		}
	}

//	createHands(): The creation of a 'player name' for the amount of players prompted to the user
//	will be made into a key that is used to track chip count for that specific player. This initial setup
//	allows the game to begin with set of specific players. Additional players cannot be added past this setup.
	private void createHands() {
		System.out.print("How many players? >> ");
		int numberOfPlayers = kb.nextInt();

		table.getCardsOnTable().put(0, new ArrayList<>());
		chips.getChipsMap().put("Dealer", 1000000);
		chips.getPlayerTurn().put(0, "Dealer");
		for (int c = 1; c <= numberOfPlayers; c++) {
			table.getCardsOnTable().put(c, new ArrayList<>());
			System.out.print("Enter name of player " + c + " >>");
			String name = kb.next();
			chips.getChipsMap().put(name, 50);
			chips.getPlayerTurn().put(c, name);
		}

	}

//	checkBlackJack(): Checks for any players or the Dealer having Blackjack. If the Dealer has a Blackjack, then 
//	the round is over and the Dealer wins. If any player has Blackjack, then the game will still continue but the
//	players who got a Blackjack will not participate in the round considering that they have already won against the Dealer. 
	public void checkBlackjack() {

		for (int c : chips.getPlayerTurn().keySet()) {
			int value = 0;
			for (Card card : table.getCardsOnTable().get(c)) {
				value += card.getValue();
			}
			if (value == 21) {
				System.out.println(chips.getPlayerTurn().get(c) + " got a blackjack!");
				if (c == 0) {
					printHandAndValue(0);
					clearHands();
					proceed();
				}
			}
		}
	}

//	printHandAndValue(): will display the given player's cards and hand value. Special exception for the Dealer. The first time
//	printHandAndValue is called in a given round with the Dealer's parameter, the Dealer will only reveal one of his cards. 
//	Any subsequent calls to the method with the Dealer's parameters will reveal all his cards and hand value. 
	public void printHandAndValue(int player) {

		if (player > 0) {
			System.out.println(chips.getPlayerTurn().get(player) + "'s Hand:");
			for (Card card : table.getCardsOnTable().get(player)) {
				System.out.println(card);
			}
			System.out.println("Hand value: " + getValue(player));
			System.out.println("===================");
		} else {
			System.out.println("Dealer's Hand: ");
			for (Card card : table.getCardsOnTable().get(player)) {
				if (hidden != 0) {
					System.out.println(card);
				}
				hidden++;
			}
			if (hidden > 2) {
				System.out.println("Hand value: " + getValue(player));
			}
			System.out.println("========================");
		}
	}

//	checkBusted(): Will check if any player's hand value exceeds 21. If it does, then their round is over and the game
//	proceeds on to the next player or Dealer. 
	public boolean checkBusted(int player) {

		int value = getValue(player);
		if (value > 21) {
			System.out.println(chips.getPlayerTurn().get(player) + " BUSTED!");
			System.out.println("========================");
			if (player != 0) {
				return false;
			}
		}

		return true;

	}

//	getValue(): Will return the value of a hand for the given player. If the player's hand exceeds 21, then the method
//	will check for any ACE cards in their hands. Reducing their hand's value by 10 per ACE they have and only until 
//	their hand's value is below 21. Meaning a hand of 2 ACES will yield the value of 12. 
	public int getValue(int player) {
		int value = 0;
		for (Card card : table.getCardsOnTable().get(player)) {
			value += card.getValue();
		}
		for (Card card : table.getCardsOnTable().get(player)) {
			if (value > 21) {
				if (card.getRank() == Rank.ACE) {
					value -= 10;
				}
			}
		}
		return value;
	}

//	playGame(): This is where the game play occurs. Players are given a menu of options to take until they
//	decide to Stand or until they BUST. After every player takes their turn, the Dealer then goes.
	public void playGame() {

		for (int c : chips.getPlayerTurn().keySet()) {
			if (c != 0 && getValue(c) != 21 && table.getCardsOnTable().get(c).size() >= 2) {
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
						table.getCardsOnTable().get(c).add(card);
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

//		Dealer will hit while their hand value is below a player's hand value and below 17, but not if
//		the player's hand is a BUSTED hand. 
		printHandAndValue(0);
		for (int c : chips.getPlayerTurn().keySet()) {
			while (getValue(0) < getValue(c) && getValue(0) < 17 && getValue(c) < 21) {
				System.out.println("Dealer HITS!");
				Card card = deck.dealCard();
				table.getCardsOnTable().get(0).add(card);
				printHandAndValue(0);
				checkBusted(0);
			}
		}
		if (getValue(0) >= 17 && getValue(0) <= 21) {
			System.out.println("Dealer STANDS!");
		}

		checkWinner();

	}

//	checkWinner(): There is a set order of what constitutes a win or a loss. The series of if - else if
//	will select the appropriate conditions for every player versus the Dealer. 
	public void checkWinner() {

		int highest = getValue(0);
		for (int c : chips.getPlayerTurn().keySet()) {
			if (c != 0) {
//				If both Dealer and player get Blackjack, then results in a PUSH(Draw).
				if (getValue(c) == highest && getValue(c) == 21 && table.getCardsOnTable().get(c).size() == 2
						&& table.getCardsOnTable().get(0).size() == 2) {
					System.out.println(chips.getPlayerTurn().get(c) + " results in PUSH against the Dealer!");
					int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
					balance += 5;
					chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
					System.out.println("Chips returned to " + chips.getPlayerTurn().get(c));
					printChips(c);
				}
//				Dealer wins by Blackjack.
				else if (highest == 21 && table.getCardsOnTable().get(0).size() == 2) {
					System.out.println("The house wins against " + chips.getPlayerTurn().get(c));
				}
//				Player wins if their hand is greater than the dealer's and not over 21.
				else if (getValue(c) > highest && getValue(c) <= 21 && highest <= 21) {
					System.out.println(chips.getPlayerTurn().get(c) + " beats the Dealer!");
					int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
					balance += 10;
					chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
					System.out.println(chips.getPlayerTurn().get(c) + " wins $10.00");
					printChips(c);
				}
//				Player wins if their hand is equal to the dealer's, but the player got a Blackjack
				else if (getValue(c) == highest && getValue(c) == 21 && table.getCardsOnTable().get(c).size() == 2) {
					System.out.println(chips.getPlayerTurn().get(c) + " beats the Dealer!");
					int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
					balance += 10;
					chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
					System.out.println(chips.getPlayerTurn().get(c) + " wins $10.00");
					printChips(c);
				}
//				Both Dealer and Player have same hand value and neither BUSTED. Results in PUSH.
				else if (getValue(c) == highest && getValue(c) <= 21 && highest <= 21) {
					System.out.println(chips.getPlayerTurn().get(c) + " results in PUSH against the Dealer!");
					int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
					balance += 5;
					chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
					System.out.println("Chips returned to " + chips.getPlayerTurn().get(c));
					printChips(c);
				}
//				Dealer wins if their hand is greater in value and neither BUSTED.
				else if (getValue(c) < highest && getValue(c) <= 21 && highest <= 21) {
					System.out.println("The house wins against " + chips.getPlayerTurn().get(c));
				}
//				Dealer wins because the player BUSTED
				else if (getValue(c) > 21 && c != 0) {
					System.out.println(chips.getPlayerTurn().get(c) + " losses due to BUST");
				}
//				Dealer BUSTED and lost to any players who did not BUST
				else {
					System.out.println("The house losses due to BUST against " + chips.getPlayerTurn().get(c));
					int balance = chips.getChipsMap().get(chips.getPlayerTurn().get(c));
					balance += 10;
					chips.getChipsMap().put(chips.getPlayerTurn().get(c), balance);
					System.out.println(chips.getPlayerTurn().get(c) + " wins $10.00");
					printChips(c);
				}
			}
		}

		clearHands();
		proceed();
	}

//	printChips(): Will print the chip count for an individual player based on integer key parameter.
	public void printChips(int player) {
		System.out.println(chips.getPlayerTurn().get(player) + "'s chip count: $"
				+ chips.getChipsMap().get(chips.getPlayerTurn().get(player)) + ".00");
	}

//	printAllChips(): Will iterate through the keySet for playerTurn map and print out all chip counts.
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
