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
	private int isWar=0;//�� �� �Ͼ ���� �־ int��
	//�������� ���� ī��
	private ArrayList<PlayingCard> p1back=new ArrayList<PlayingCard>();
	private ArrayList<PlayingCard> p2back=new ArrayList<PlayingCard>();
	//������, �¸��Ͽ� ������ ī��
	private int p1front=0;
	private int p2front=0;
	//������ ���� ���� ī��
	private PlayingCard[] p1ready=new PlayingCard[readycard];
	private PlayingCard[] p2ready=new PlayingCard[readycard];
	//���ī��
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
				System.out.println("1P�� �¸��Ͽ����ϴ�.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P�� �¸��Ͽ����ϴ�.");
				break;
			} else if(doesWinnerExist == -1) {
				System.out.println("���º��Դϴ�.");
				break;
			}	
		}
	}
	
	//turn<=23 && isWar==0
	public void pickSortieOutOf3() {
		boolean pickthesortie=true;
		do {
			System.out.println("***********************************************");
			System.out.println((turn+1)+"��° ����: 1,2,3�� ī���� ��� ���� ����Ͻðڽ��ϱ�?");
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
		} catch(InputMismatchException ex) {//���������� �Է����� ������
			if(ex.getMessage() == null)
				System.out.println("�߸��� �Է��Դϴ�: 1~3������ ���� �־��ּ���.\n");
			else
				System.out.printf("�߸��� �Է��Դϴ�: %s\n", ex.getMessage());
			in.nextLine();
		} catch(IndexOutOfBoundsException ex) {//���� ���� ������ �Է��ϸ�
			System.out.printf("�߸��� �Է��Դϴ�: 1~3������ ���� �Է����ּ���.\n");
			//ex.printStackTrace();
		}}while(pickthesortie);		
	}
	
	//turn==24 || isWar==1
	public void pickSortieOutOf2() {
		boolean pickthesortie=true;
		do {
			System.out.println("***********************************************");
			System.out.println((turn+1)+"��° ����: 1,2�� ī���� ��� ���� ����Ͻðڽ��ϱ�?");
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
		} catch(InputMismatchException ex) {//���������� �Է����� ������
			if(ex.getMessage() == null)
				System.out.println("�߸��� �Է��Դϴ�: 1~3������ ���� �־��ּ���.\n");
			else
				System.out.printf("�߸��� �Է��Դϴ�: %s\n", ex.getMessage());
			in.nextLine();
		} catch(IndexOutOfBoundsException ex) {//���� ���� ������ �Է��ϸ�
			System.out.printf("�߸��� �Է��Դϴ�: 1~3������ ���� �Է����ּ���.\n");
			//ex.printStackTrace();
		}}while(pickthesortie);		
	}
	
	//turn==25 || isWar==2
	public void pickSortieOutOf1() {
		System.out.println("***********************************************");
		System.out.println((turn+1)+"��° ����: ������ ī�带 ����մϴ�.");
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
		System.out.println("����� ī��:");
		System.out.printf("player 1: %c%d, player 2: %c%d\n\n", p1Suit, p1Num, p2Suit, p2Num);
		if(p1Num==1)//���̽��� ���� ���ϱ� ������
			p1Num+=100;
		if(p2Num==1)
			p2Num+=100;
		if(p1Num>p2Num) {
			System.out.println("player 1�� �¸��Դϴ�."+winnersGet+"���� ī�带 �������ϴ�.");
			p1front+=winnersGet;			
			System.out.println("\nȹ���� ī�� ��");
			System.out.printf("player 1: %d\nplayer 2: %d\n\n", p1front, p2front);
			isWar=0;
			}
		else if(p1Num<p2Num) {
			System.out.println("player 2�� �¸��Դϴ�."+winnersGet+"���� ī�带 �������ϴ�.");
			p2front+=winnersGet;
			System.out.println("\nȹ���� ī�� ��");
			System.out.printf("player 1: %d\nplayer 2: %d\n\n", p1front, p2front);
			isWar=0;
			}
		else {
			if(turn==25) {
				System.out.println("���º��Դϴ�.\n���̻� ����� ī�尡 ����,������ ī�带 �������ϴ�.");
				p1front++;p2front++;
				isWar=0;
			}
			else {
				System.out.println("���º��Դϴ�. ������ ���۵˴ϴ�.");
				isWar++;
				if(isWar==1) {
					 pickSortieOutOf2();
					 match();
				}
				else if(isWar==2) {//�� �̻��� �� ������� ����.
					pickSortieOutOf1();
					match();
				}
				else {
					System.out.println("�� ��° ���º��Դϴ�.������ ī�带 �������ϴ�.");//���� ���󼭤̤�
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
		String output = "��Ŀ�� ������ �÷���ī��� ����Ǵ� �����Դϴ�.\n";
		output += "�� ���� �÷��̾ �����ϸ� ī�嵦�� ���ݾ��� �����ϴ�.\n";
		output += "������ ī��� �� 3���� �� ������ �߰��մϴ�.\n";
		output += "������ �ִ� ī��� �� �� ���� ������ ����մϴ�.\n";
		output += "���º��� ��� ������ ���۵ǰ� ����ī�嵵 ������ ����մϴ�.\n";
		output += "��� ī�尡 �� �������� ������ ����ǰ� ���� ī�带 ���� ���� �¸��մϴ�.\n";
		return output;
	}
	
	private int doesWinnerExist() { //0: A�� �̱�, 1: B�� �̱�, -1: ���� �ȳ�
		if(p1front<p2front) return 1;
		else if(p1front>p2front) return 0;
		else return -1;
	}

}
