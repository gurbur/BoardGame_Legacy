package war;

import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;
import card.PlayingCard;
import card.PlayingCardDeck;
import game.Game;

public class WarGame implements Game{
	
	private boolean doNeedJoker=false;
	private PlayingCardDeck playingCardDeck;
	private final int readycard=3;
	
	private int turn;
	private int isWar=0;//두 번 일어날 수도 있어서 int형
	//뒤집히지 않은 카드
	private ArrayList<PlayingCard> p1back=new ArrayList<PlayingCard>();
	private ArrayList<PlayingCard> p2back=new ArrayList<PlayingCard>();
	//뒤집힌, 승리하여 가져온 카드
	private int p1front=0;
	private int p2front=0;
	//진영에 내어 놓은 카드
	private PlayingCard[] p1ready=new PlayingCard[readycard];
	private PlayingCard[] p2ready=new PlayingCard[readycard];
	//출격카드
	private PlayingCard p1sortie;
	private PlayingCard p2sortie;
	
	private Scanner in;
	
	public WarGame() {
		p1back.clear();p2back.clear();
		p1front=0;p2front=0;
		for(int i=0;i<readycard;i++) {
			p1ready[i]=null;
			p2ready[i]=null;
		}p1sortie=null;p2sortie=null;
		turn=0;
	}
	
	@Override
	public void gameStart() {
		playingCardDeck=new PlayingCardDeck(doNeedJoker);
		in = new Scanner(System.in);
		mainGame();
	
	}
	private void mainGame() {
		while(true) { // main loop
			playingCardDeck =new PlayingCardDeck(doNeedJoker);
		
			divideCard();
			setReady();
			while(turn<=23) {
				pickSortieOutOf3();
				match();
			}
			if(turn==24) {
				pickSortieOutOf2();
				match();
			}
			if(turn==25) {
				pickSortieOutOf1();
				match();
			}	
			
			int doesWinnerExist = doesWinnerExist();
			if(doesWinnerExist == 0) {
				System.out.println("1P가 승리하였습니다.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P가 승리하였습니다.");
				break;
			} else if(doesWinnerExist == -1) {
				System.out.println("무승부입니다.");
				break;
			}	
		}
	}
	
	//turn<=23 && isWar==0
	public void pickSortieOutOf3() {
		boolean pickthesortie=true;
		do {
			System.out.println("***********************************************");
			System.out.println((turn+1)+"번째 결투: 1,2,3번 카드중 어느 것을 출격하시겠습니까?");
		try {
			System.out.print("player 1:");
			int p1=in.nextInt();
			if(p1<1||p1>3)
				throw new IndexOutOfBoundsException();
			p1sortie=p1ready[p1-1];p1ready[p1-1]=null;
			if(turn<23) {
				p1ready[p1-1]=p1back.get(0);p1back.remove(0);}
			else if(p1==1||p1==2) {
				p1ready[p1-1]=p1ready[2];p1ready[2]=null;}
			System.out.print("player 2:");
			int p2=in.nextInt();
			if(p2<1||p2>3)
				throw new IndexOutOfBoundsException();
			p2sortie=p2ready[p2-1];p2ready[p2-1]=null;
			if(turn<23) {
				p2ready[p2-1]=p2back.get(0);p2back.remove(0);}
			else if(p2==1||p2==2) {
				p2ready[p2-1]=p2ready[2];p2ready[2]=null;}
			turn++;
			pickthesortie=false;
		} catch(InputMismatchException ex) {//정수형으로 입력하지 않으면
			if(ex.getMessage() == null)
				System.out.println("잘못된 입력입니다: 1~3사이의 값을 넣어주세요.\n");
			else
				System.out.printf("잘못된 입력입니다: %s\n", ex.getMessage());
			in.nextLine();
		} catch(IndexOutOfBoundsException ex) {//범위 밖의 정수를 입력하면
			System.out.printf("잘못된 입력입니다: 1~3사이의 값을 입력해주세요.\n");
			//ex.printStackTrace();
		}}while(pickthesortie);		
	}
	
	//turn==24 || isWar==1
	public void pickSortieOutOf2() {
		boolean pickthesortie=true;
		do {
			System.out.println("***********************************************");
			System.out.println((turn+1)+"번째 결투: 1,2번 카드중 어느 것을 출격하시겠습니까?");
		try {
			System.out.print("player 1:");
			int p1=in.nextInt();
			if(p1<1||p1>2)
				throw new IndexOutOfBoundsException();
			p1sortie=p1ready[p1-1];p1ready[p1-1]=null;
			if(p1==1) {
				p1ready[0]=p1ready[1];p1ready[1]=null;}						
			System.out.print("player 2:");
			int p2=in.nextInt();
			if(p2<1||p2>2)
				throw new IndexOutOfBoundsException();
			p2sortie=p2ready[p2-1];p2ready[p2-1]=null;
			if(p2==1) {
				p2ready[0]=p2ready[1];p2ready[1]=null;}
			turn++;
			pickthesortie=false;
		} catch(InputMismatchException ex) {//정수형으로 입력하지 않으면
			if(ex.getMessage() == null)
				System.out.println("잘못된 입력입니다: 1~3사이의 값을 넣어주세요.\n");
			else
				System.out.printf("잘못된 입력입니다: %s\n", ex.getMessage());
			in.nextLine();
		} catch(IndexOutOfBoundsException ex) {//범위 밖의 정수를 입력하면
			System.out.printf("잘못된 입력입니다: 1~3사이의 값을 입력해주세요.\n");
			//ex.printStackTrace();
		}}while(pickthesortie);		
	}
	
	//turn==25 || isWar==2
	public void pickSortieOutOf1() {
		System.out.println("***********************************************");
		System.out.println((turn+1)+"번째 결투: 마지막 카드를 출격합니다.");
		p1sortie=p1ready[0];p1ready[0]=null;
		p2sortie=p2ready[0];p2ready[0]=null;
		turn++;
	}
	
	public void match() {
		int p1Num, p2Num;
		p1Num=p1sortie.getNum();
		p2Num=p2sortie.getNum();
		char p1Suit, p2Suit;
		p1Suit=p1sortie.getSuit();
		p2Suit=p2sortie.getSuit();
		int winnersGet=(isWar+1)*2;
		System.out.println("출격한 카드:");
		System.out.printf("player 1: %c%d, player 2: %c%d\n\n", p1Suit, p1Num, p2Suit, p2Num);
		if(p1Num==1)//에이스가 가장 강하기 때문에
			p1Num+=100;
		if(p2Num==1)
			p2Num+=100;
		if(p1Num>p2Num) {
			System.out.println("player 1의 승리입니다."+winnersGet+"개의 카드를 가져갑니다.");
			p1front+=winnersGet;			
			System.out.println("\n획득한 카드 수");
			System.out.printf("player 1: %d\nplayer 2: %d\n\n", p1front, p2front);
			isWar=0;
			}
		else if(p1Num<p2Num) {
			System.out.println("player 2의 승리입니다."+winnersGet+"개의 카드를 가져갑니다.");
			p2front+=winnersGet;
			System.out.println("\n획득한 카드 수");
			System.out.printf("player 1: %d\nplayer 2: %d\n\n", p1front, p2front);
			isWar=0;
			}
		else {
			if(turn==25) {
				System.out.println("무승부입니다.\n더이상 출격할 카드가 없어,각자의 카드를 가져갑니다.");
				p1front++;p2front++;
				isWar=0;
			}
			else {
				System.out.println("무승부입니다. 전쟁이 시작됩니다.");
				isWar++;
				if(isWar==1) {
					 pickSortieOutOf2();
					 match();
				}
				else if(isWar==2) {//이 이상은 잘 실행되지 않음.
					pickSortieOutOf1();
					match();
				}
				else {
					System.out.println("세 번째 무승부입니다.각자의 카드를 가져갑니다.");//룰을 몰라서ㅜㅜ
					p1front+=3;p2front+=3;
					isWar=0;
				}
			}			
		}
	}
	
	public void setReady() {
		for(int i=0;i<3;i++) {
			p1ready[i]=p1back.get(0);
			p1back.remove(0);
			p2ready[i]=p2back.get(0);
			p2back.remove(0);		
		}
	}

	public void divideCard() {
		for(int i=0;i<26;i++) 
			p1back.add(playingCardDeck.pop());
		for(int i=0;i<26;i++) 
			p2back.add(playingCardDeck.pop());	
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
	
	private int doesWinnerExist() { //0: A가 이김, 1: B가 이김, -1: 승패 안남
		if(p1front<p2front) return 1;
		else if(p1front>p2front) return 0;
		else return -1;
	}

}
