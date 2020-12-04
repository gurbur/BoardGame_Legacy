package blackjack;

import java.util.ArrayList;


import card.PlayingCard;
import card.PlayingCardDeck;

public class BlackJackTester {
	public static ArrayList<PlayingCard> playingCardDeck = new ArrayList<PlayingCard>();
	public static void main(String[] args) {

		boolean doNeedJoker=false;

		PlayingCardDeck	playingCardDeck1 = new PlayingCardDeck(doNeedJoker);
		PlayingCardDeck	playingCardDeck2 = new PlayingCardDeck(doNeedJoker);
		PlayingCardDeck	playingCardDeck3 = new PlayingCardDeck(doNeedJoker);
		PlayingCardDeck	playingCardDeck4 = new PlayingCardDeck(doNeedJoker);
		for(int i=0;i<52;i++) {
			playingCardDeck.add(playingCardDeck1.pop());
		}
		for(int i=0;i<52;i++) {
			playingCardDeck.add(playingCardDeck2.pop());
		}
		for(int i=0;i<52;i++) {
			playingCardDeck.add(playingCardDeck3.pop());
		}
		for(int i=0;i<52;i++) {
			playingCardDeck.add(playingCardDeck4.pop());
		}
		for(int i=0;i<52;i++) {
			pop();
		}
		System.out.println("");
		for(int i=0;i<52;i++) {
			pop();
		}
		System.out.println("");
		for(int i=0;i<52;i++) {
			pop();
		}
		System.out.println("");for(int i=0;i<52;i++) {
			pop();
		}
		
	}	
	
	public static void pop() {
		PlayingCard temp = playingCardDeck.get(0);
		System.out.printf("%c%d ", temp.getSuit(), temp.getNum());
		playingCardDeck.remove(0);
	}
		
}
