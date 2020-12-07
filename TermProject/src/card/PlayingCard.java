package card;

import java.util.Scanner;

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
	
	public int getNum_forBlackjack() {
		if(num>10) {
			return 10;
		}
		return num;
	}


	public char getSuit() {
		return suit;
	}
	
	@Override
	public String toString() {
		if(suit == 'J') {
			if(num == 0)
				return "Black Joker";
			else
				return "Color Joker";
		}
		if(num == 1)
			return "A" + suit;
		else if(num == 11)
			return "J" + suit;
		else if(num == 12)
			return "Q" + suit;
		else if(num == 13)
			return "K" + suit;
		else
			return "" + num + suit;
	}
}
