package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Deck {

//	F I E L D S

	private List<Card> deck;

//	C O N S T R U C T O R S

	public Deck() {
		deck = new ArrayList<>();
	}

//	M E T H O D S 

	private List<Card> createNewDeck(int numOfDecks) {
		deck = new ArrayList<>();
		for (int i = 0; i < numOfDecks; i++) {
			for (Suit s : Suit.values()) {
				for (Rank r : Rank.values()) {
					deck.add(new Card(r, s));
				}
			}
		}
		return deck;
	}

	public int checkDeckSize() {
		return deck.size();
	}

	public Card dealCard() {
		return deck.remove(0);
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public void deckCreation(Scanner kb) {

		System.out.println("Select deck type:");
		System.out.println("1: Standard single deck.");
		System.out.println("2. Multideck shoe (2 decks).");
		System.out.println("3. Multideck shoe (4 decks).");
		System.out.println("4. Multideck shoe (8 decks).");
		System.out.println("0. Quit.");
		try {
			int deckSelection = kb.nextInt();
			switch (deckSelection) {
			case 1:
				deck = createNewDeck(1);
				break;
			case 2:
				deck = createNewDeck(2);
				break;
			case 3:
				deck = createNewDeck(4);
				break;
			case 4:
				deck = createNewDeck(8);
				break;
			case 0:
				System.out.println("Adios amigo!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input.");
				deckCreation(kb);
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
		}
	}

}
