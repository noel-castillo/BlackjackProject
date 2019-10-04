package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {

//	F I E L D S

	List<Card> deck;

//	C O N S T R U C T O R S

	public Deck() {
		deck = new ArrayList<>(52);
	}

//	M E T H O D S 

	public List<Card> createNewDeck() {
		deck = new ArrayList<>(52);
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				deck.add(new Card(r, s));
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

}
