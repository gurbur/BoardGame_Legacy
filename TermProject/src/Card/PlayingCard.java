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
		// #PlayingCard ��ü�� ���� ����� �ϴ°� �ƴ϶�, ToString()�� Override�ؼ� ���� �Լ����� ����Ʈ �ϴ°� �� ���� �� ���ƿ�!
		/*public void print() {
			System.out.printf("%c%d ", suit, num);
		}*/
		@Override
		public String toString() {
			return "" + suit + " " + num;
		}
}
