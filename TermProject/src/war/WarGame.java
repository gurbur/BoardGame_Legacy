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
				System.out.println("1P�� �¸��Ͽ����ϴ�.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P�� �¸��Ͽ����ϴ�.");
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
		String output = "��Ŀ�� ������ �÷���ī��� ����Ǵ� �����Դϴ�.\n";
		output += "�� ���� �÷��̾ �����ϸ� ī�嵦�� ���ݾ��� �����ϴ�.\n";
		output += "������ ī��� �� 3���� �� ������ �߰��մϴ�.\n";
		output += "������ �ִ� ī��� �� �� ���� ������ ����մϴ�.\n";
		output += "���º��� ��� ������ ���۵ǰ� ����ī�嵵 ������ ����մϴ�.\n";
		output += "��� ī�尡 �� �������� ������ ����ǰ� ���� ī�带 ���� ���� �¸��մϴ�.\n";
		return output;
	}
	
	private void printWhoNext() {
		if(whosTurn)
			System.out.println("1�� �÷��̾��� ���Դϴ�.");
		else
			System.out.println("2�� �÷��̾��� ���Դϴ�.");
	}
	
	/*private int doesWinnerExist() { //0: A�� �̱�, 1: B�� �̱�, -1: ���� �ȳ�
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
