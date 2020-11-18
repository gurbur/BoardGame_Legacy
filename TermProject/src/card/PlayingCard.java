package card;

public class PlayingCard extends Card{

	private final char suit;
	private final int num;
	public PlayingCard(char suit,int num) {
		this.num = num;
		this.suit = suit;
	}
	
	public int getNum() {
		return num;
	}

	public char getSuit() {
		return suit;
	}
	
	@Override
	public String toString() {
		return "" + suit + num;
	}
}
