package Card;
import java.util.ArrayList;
import java.util.Scanner;


public class CardTest {
	public static void main(String[] args) {
		// # PlayingCardDeck�� ��ü �ȿ� �̹� �ִ� �����Ϳ���. �����Լ������� PlayingCardDeck�� ��ü�� ������ �� �� ���ƿ�.
		//ArrayList<PlayingCard> PlayingCardDeck= new ArrayList<PlayingCard>();
		PlayingCardDeck Deck = new PlayingCardDeck(true);
		// # �����ڰ� makeCards()�� ���������� ȣ���ϵ��� �ٲپ����. makeCards(Deck);
		//makeCard(Deck);
		
		for(int i=0;i<Deck.size();i++) {
			
			System.out.println(Deck.pop().toString());		
	}

}
}
