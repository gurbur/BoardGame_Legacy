package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
import card.PlayingCard;
import card.PlayingCardDeck;
import game.Game;

public class BlackJackGame implements Game{
	
	private ArrayList<PlayingCard> playingCardDeck = new ArrayList<PlayingCard>();
	//각자카드 합 
	private int dealer_sum=0;
	private int p1sum, p2sum, p3sum, p4sum;
	//각자 가진 재산
	private int p1cash, p2cash, p3cash, p4cash;
	//각자 걸 돈
	private int p1ante, p2ante, p3ante, p4ante;
	//몇번째 게임인지
	private int round;
	//각자 카드패
	private ArrayList<PlayingCard> dealer_hand=new ArrayList<PlayingCard>();
	private ArrayList<PlayingCard> p1hand=new ArrayList<PlayingCard>();
	private ArrayList<PlayingCard> p2hand=new ArrayList<PlayingCard>();
	private ArrayList<PlayingCard> p3hand=new ArrayList<PlayingCard>();
	private ArrayList<PlayingCard> p4hand=new ArrayList<PlayingCard>();
	
	private Scanner in;
	
	public BlackJackGame() {
		dealer_sum=0;
		p1sum=0;
		p2sum=0;	
		p3sum=0;
		p4sum=0;
	
		p1cash=0;
		p2cash=0;	
		p3cash=0;
		p4cash=0;
		
		p1ante=0;
		p2ante=0;	
		p3ante=0;
		p4ante=0;
		
		round=1;
		
		dealer_hand.clear();
		p1hand.clear();
		p2hand.clear();
		p3hand.clear();
		p4hand.clear();
	}
	
	@Override
	public void gameStart() {
		in = new Scanner(System.in);
		mainGame();
	}
	
	public void mainGame() {
		while(true) { // main loop
			makeCardDeck();
		
		}
	}
	
	
	
	public void makeCardDeck() {
		PlayingCardDeck	playingCardDeck1 = new PlayingCardDeck(false);
		PlayingCardDeck	playingCardDeck2 = new PlayingCardDeck(false);
		PlayingCardDeck	playingCardDeck3 = new PlayingCardDeck(false);
		PlayingCardDeck	playingCardDeck4 = new PlayingCardDeck(false);
		for(int i=0;i<52;i++) 
			playingCardDeck.add(playingCardDeck1.pop());
		for(int i=0;i<52;i++) 
			playingCardDeck.add(playingCardDeck2.pop());
		for(int i=0;i<52;i++) 
			playingCardDeck.add(playingCardDeck3.pop());
		for(int i=0;i<52;i++) 
			playingCardDeck.add(playingCardDeck4.pop());
	}
	
	//반환값 수정 가능
	public void draw() {
		PlayingCard temp = playingCardDeck.get(0);
		System.out.printf("%c%d ", temp.getSuit(), temp.getNum());
		playingCardDeck.remove(0);
	}




}
