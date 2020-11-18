package Card;

public class PlayingCard extends Card{

	private final char suit;
	private final int num;
	public PlayingCard(char suit,int num) {
		super();
		this.num = num;
		this.suit = suit;
	}
	
	public int getNum() {
		return num;
	}

	public char getSuit() {
		return suit;
	}
	// print this card
		// #PlayingCard 객체가 직접 출력을 하는게 아니라, ToString()을 Override해서 메인 함수에서 프린트 하는게 더 좋은 것 같아요!
		/*public void print() {
			System.out.printf("%c%d ", suit, num);
		}*/
		@Override
		public String toString() {
			return "" + suit + " " + num;
		}
}
