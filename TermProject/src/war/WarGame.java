package war;

import java.util.ArrayList;
import java.util.Scanner;

import card.PlayingCard;
import card.PlayingCardDeck;
import game.Game;

public class WarGame implements Game{
	
	private boolean doNeedJoker=false;
	private PlayingCardDeck playingCardDeck;

	ArrayList<PlayingCard> p1back=new ArrayList<PlayingCard>();
	ArrayList<PlayingCard> p1front=new ArrayList<PlayingCard>();
	ArrayList<PlayingCard> p2back=new ArrayList<PlayingCard>();
	ArrayList<PlayingCard> p2front=new ArrayList<PlayingCard>();

	
	private final boolean whoFirst; //true == 1p, false == 2p
	private boolean whosTurn; //true == 1p, false == 2p
	
	private Scanner in;
	
	public WarGame(boolean whoFirst) {
		this.whoFirst = whoFirst;
		playingCardDeck=new PlayingCardDeck(doNeedJoker);
	}
	
	@Override
	public void gameStart() {
		
		whosTurn = whoFirst;
		in = new Scanner(System.in);
		mainGame();
	
	}
	private void mainGame() {
		while(true) { // main loop
			playingCardDeck =new PlayingCardDeck(doNeedJoker);
			int doesWinnerExist = doesWinnerExist();
			if(doesWinnerExist == 0) {
				System.out.println("1P가 승리하였습니다.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P가 승리하였습니다.");
				break;
			}			
			printWhoNext();
			
			halfCard();
			
			
			
		}
	}
	

	public void halfCard() {
		for(int i=0;i<26;i++) {
			int random = (int)(playingCardDeck.size() * Math.random());
			PlayingCard temp=playingCardDeck.get(random);
			p1back.add(temp);
			playingCardDeck.remove(random);
		}p2back=playingCardDeck;			
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "조커를 제외한 플레잉카드로 진행되는 게임입니다.\n";
		output += "두 명의 플레이어가 참여하며 카드덱의 절반씩을 가집니다.\n";
		output += "뒤집힌 카드들 중 3장을 내 진영에 추가합니다.\n";
		output += "진영에 있는 카드들 중 한 장을 뒤집어 대결합니다.\n";
		output += "무승부일 경우 전쟁이 시작되고 다음카드도 뒤집어 대결합니다.\n";
		output += "모든 카드가 다 뒤집히면 게임이 종료되고 많은 카드를 가진 쪽이 승리합니다.\n";
		return output;
	}
	
	private void printWhoNext() {
		if(whosTurn)
			System.out.println("1번 플레이어의 턴입니다.");
		else
			System.out.println("2번 플레이어의 턴입니다.");
	}
	
	/*private int doesWinnerExist() { //0: A가 이김, 1: B가 이김, -1: 승패 안남
		int countA = 0;
		int countB = 0;
		for(int i = 0; i < 32; i++) {
			if(checkerBoard.isOnBoard(i)) {
				if(checkerBoard.getMarker(i).getPlayer() == 0) countA++;
				else if(checkerBoard.getMarker(i).getPlayer() == 1) countB++;
			}
		}
		if(countA == 0) return 1;
		else if(countB == 0) return 0;
		else return -1;
	}*/
	
	

}
