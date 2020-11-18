package Card;

import java.util.ArrayList;
/* 
 * 1. 내부 데이터(CardDeck)는 public이 아니고 private로 해주세요! (data encapsulation)
 * 2. CardDeck은 이미 클래스 내부에 있는 값이니, 외부에서 parameter로 받아줄 필요는 없어요!
 * 3. MakeCards(), makeJoker()는 private로 바꿔서 생성자로 호출하는게 나을 것 같아요.
 * 4. size()함수를 추가해줬어요!
 * 
*/

public class PlayingCardDeck {
	//private char[] suitarray= {'J','♣', '♥', '◆', '♠'};
	//private PlayingCard[] CardDeck=new PlayingCard[54];
	//배열로 구현
	
	private ArrayList<PlayingCard> CardDeck= new ArrayList<PlayingCard>();
	
	PlayingCardDeck(boolean doNeedJoker) {
		this.makeCards();
		if(doNeedJoker == true) 
			makeJokerCards();
	}
	
	//기본 52개의 카드를 만듦
	private void makeCards() {
		for(int i=1;i<=13;i++) {
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
		for (int n=0; n<500; n++) {
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
		for(int i=0; i < CardDeck.size();i++)
			CardDeck.remove(i);
		makeCards();
		shuffleCards();		
	}
	// # 메인함수에서 반복문 등에 사용할 일이 있을거 같아 임의로 추가해보았습니다
	public int size() {
		return CardDeck.size();
	}
		
}
