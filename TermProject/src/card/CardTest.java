package card;
import java.util.ArrayList;
import java.util.Scanner;


public class CardTest {
	public static void main(String[] args) {
		
		PlayingCardDeck Deck = new PlayingCardDeck(true);
		
		for(int i = 0; i < Deck.size(); i++) {
			
			System.out.println(Deck.pop().toString());		
	}

}
}
