package card;

import java.util.ArrayList;

public class PlayingCardDeck {
	//private char[] suitarray= {'J','♣', '♥', '◆', '♠'};
	//private PlayingCard[] CardDeck=new PlayingCard[54];
	//배열로 구현
	
	private boolean doNeedJoker;
	private ArrayList<PlayingCard> CardDeck= new ArrayList<PlayingCard>();
	
	PlayingCardDeck(boolean doNeedJoker) {
		this.makeCards();
		this.doNeedJoker = doNeedJoker;
		if(doNeedJoker == true)
			makeJokerCards();
	}
	
	//기본 52개의 카드를 만듦
	private void makeCards() {
		for(int i = 1; i <= 13; i++) {
			CardDeck.add(new PlayingCard('♣', i));
			CardDeck.add(new PlayingCard('♥', i));
			CardDeck.add(new PlayingCard('◆', i));
			CardDeck.add(new PlayingCard('♠', i));
		}		
	}
	
	//조커카드 추가
	private void makeJokerCards() {
		CardDeck.add(new PlayingCard('J', 1));
		CardDeck.add(new PlayingCard('J', 2));
	}
		
	public void shuffleCards() {
		int i, j;
		int nCards = CardDeck.size();
		for (int n = 0; n < 500; n++) {
			i = (int) (Math.random()*nCards);
			j = (int) (Math.random()*nCards);
			PlayingCard cTmp1=CardDeck.get(i);
			PlayingCard cTmp2=CardDeck.get(j);		  
			CardDeck.set(i, cTmp2);
			CardDeck.set(i, cTmp1);
		}
	}
	
	public Card pop() {
		int random=(int)(CardDeck.size()*Math.random());
		PlayingCard temp=CardDeck.get(random);
		CardDeck.remove(random);
		return temp;
	}
	
	public void reset() {
		for(int i = 0; i < CardDeck.size(); i++)
			CardDeck.remove(i);
		makeCards();
		shuffleCards();		
	}

	public int size() {
		return CardDeck.size();
	}
		
}
