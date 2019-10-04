package blackjack;

public class Card {

//	F I E L D S 

	Rank rank;
	Suit suit;

//	C O NS T R U C T O R S

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

//	M E T H O D S 

	public int getValue() {
		return this.rank.getValue();
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

	public Rank getRank() {
		return this.rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

}
