package blackjack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

//	F I E L D S 

	private Map<Integer, List<Card>> cardsOnTable;

//	C O N S T R U C T O R 

	public Table() {
		cardsOnTable = new HashMap<>();
	}

//	M E T H O D S 

	public Map<Integer, List<Card>> getCardsOnTable() {
		return cardsOnTable;
	}

	public void setCardsOnTable(Map<Integer, List<Card>> cardsOnTable) {
		this.cardsOnTable = cardsOnTable;
	}
}
