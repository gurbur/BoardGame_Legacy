package card;
import java.util.ArrayList;
import java.util.Scanner;


public class CardTest {
	public static void main(String[] args) {
		PlayingCardDeck Deck = new PlayingCardDeck(true);
		
		int deckSize = Deck.size();
		System.out.println(deckSize);
		
		
		for(int i = 0; i < deckSize; i++) {
			//for문의 조건으로 size()를 넣게되면, 매 반복마다 size()를 호출하여 조건을 검사하는것 같아요. 그런데 pop함수는 deck의 size를 1씩 줄여나가니.. 결국은 절반밖에 출력이 안되는거죠.
			//그러니 앞으로는 반복문을 사용하기 전에 사이즈를 받아서 따로 변수에 저장하여 써야될 것 같아요.
			System.out.println("" + (i + 1) + ": " + Deck.pop());
		}
		
		
	}
}
