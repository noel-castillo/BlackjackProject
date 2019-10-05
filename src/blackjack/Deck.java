package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Deck {

//	F I E L D S

	List<Card> deck;

//	C O N S T R U C T O R S

	public Deck() {
		deck = new ArrayList<>();
	}

//	M E T H O D S 

	public List<Card> createNewDeck() {
		deck = new ArrayList<>();
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				deck.add(new Card(r, s));
			}
		}
		return deck;
	}

	public List<Card> createNewMultiShoeDeck() {
		deck = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
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
		System.out.println("2. Multideck shoe (4 decks).");
		System.out.println("0. Quit.");
		try {
			int deckSelection = kb.nextInt();
			switch (deckSelection) {
			case 1:
				deck = createNewDeck();
				break;
			case 2:
				deck = createNewMultiShoeDeck();
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
