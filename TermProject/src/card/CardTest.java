package card;
import java.util.ArrayList;
import java.util.Scanner;


public class CardTest {
	public static void main(String[] args) {
		PlayingCardDeck Deck = new PlayingCardDeck(true);
		
		int deckSize = Deck.size();
		System.out.println(deckSize);
		
		
		for(int i = 0; i < deckSize; i++) {
			//for���� �������� size()�� �ְԵǸ�, �� �ݺ����� size()�� ȣ���Ͽ� ������ �˻��ϴ°� ���ƿ�. �׷��� pop�Լ��� deck�� size�� 1�� �ٿ�������.. �ᱹ�� ���ݹۿ� ����� �ȵǴ°���.
			//�׷��� �����δ� �ݺ����� ����ϱ� ���� ����� �޾Ƽ� ���� ������ �����Ͽ� ��ߵ� �� ���ƿ�.
			System.out.println("" + (i + 1) + ": " + Deck.pop());
		}
		
		
	}
}
