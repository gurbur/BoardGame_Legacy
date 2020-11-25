package card;

import java.util.ArrayList;

public class PlayingCardDeck {

	private boolean doNeedJoker;
	private ArrayList<PlayingCard> CardDeck= new ArrayList<PlayingCard>();
	
	//public �߰�
	public PlayingCardDeck(boolean doNeedJoker) {
		this.makeCards();
		this.doNeedJoker = doNeedJoker;
		if(doNeedJoker == true)
			makeJokerCards();		
		shuffleCards();
	}
	
	//�⺻ 52���� ī�带 ����
	private void makeCards() {
		for(int i = 1; i <= 13; i++) {
			CardDeck.add(new PlayingCard('��', i));
			CardDeck.add(new PlayingCard('��', i));
			CardDeck.add(new PlayingCard('��', i));
			CardDeck.add(new PlayingCard('��', i));
		}		
	}
	
	//��Ŀī�� �߰�
	private void makeJokerCards() {
		CardDeck.add(new PlayingCard('J', 0));// ���
		CardDeck.add(new PlayingCard('J', 1));// �÷�
	}
		
	public void shuffleCards() {
		int i, j;
		int nCards = CardDeck.size();
		for (int n = 0; n < 500; n++) {
			i = (int) (Math.random()*nCards);
			j = (int) (Math.random()*nCards);
			PlayingCard cTmp1 = CardDeck.get(i);
			PlayingCard cTmp2 = CardDeck.get(j);	  
			CardDeck.set(i, cTmp2);
			CardDeck.set(j, cTmp1);
		}
	}
	
	public PlayingCard pop() {
		int random = (int)(CardDeck.size() * Math.random());
		PlayingCard temp = CardDeck.get(random);
		CardDeck.remove(random);
		//int index = 0;
		//PlayingCard temp = CardDeck.get(index);
		//CardDeck.remove(index);
		return temp;
	}
	
	public void reset() {
		for(int i = 0; i < CardDeck.size(); i++)
			CardDeck.remove(i);
		makeCards();
		if(doNeedJoker == true)
			makeJokerCards();
		shuffleCards();
	}

	public int size() {
		return CardDeck.size();
	}
	
}
