package Card;
import java.util.ArrayList;
import java.util.Scanner;


public class CardTest {
	public static void main(String[] args) {
		// # PlayingCardDeck의 객체 안에 이미 있는 데이터예요. 메인함수에서는 PlayingCardDeck의 객체만 있으면 될 거 같아요.
		//ArrayList<PlayingCard> PlayingCardDeck= new ArrayList<PlayingCard>();
		PlayingCardDeck Deck = new PlayingCardDeck(true);
		// # 생성자가 makeCards()를 내부적으로 호출하도록 바꾸었어요. makeCards(Deck);
		//makeCard(Deck);
		
		for(int i=0;i<Deck.size();i++) {
			
			System.out.println(Deck.pop().toString());		
	}

}
}
