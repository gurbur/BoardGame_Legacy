package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
import card.PlayingCard;
import card.PlayingCardDeck;
import game.Game;
import war.WarGame;

public class BlackJackGame implements Game{
	
	private ArrayList<PlayingCard> playingCardDeck = new ArrayList<PlayingCard>();
	//�÷��̾� 4��
	private BlackJackPlayer p1,p2,p3,p4;
	//ī����
	private ArrayList<PlayingCard> dealer_hand=new ArrayList<PlayingCard>();
	//ī�� �� 
	private int dealer_sum=0;

	//���° ��������
	private int round;
	
	private Scanner in;
	
	public BlackJackGame() {
		p1=new BlackJackPlayer();
		p2=new BlackJackPlayer();
		p3=new BlackJackPlayer();
		p4=new BlackJackPlayer();
		dealer_hand.clear();
		dealer_sum=0;

		round=0;
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
	
	//��ȯ�� ���� ����
	public PlayingCard popCardDeck() {
		PlayingCard temp = playingCardDeck.get(0);
		System.out.printf("%c%d ", temp.getSuit(), temp.getNum());
		playingCardDeck.remove(0);
		return temp;
	}
	
	public void round() {
		bet();
		draw();
		
	}
	//���� �� ���� �Է¹޴´�.
	public void bet() {
		System.out.println("������ �����մϴ�. �ݾ��� �Է��ϼ���.(10�̻�, ���� ����)\n");
		try {
			int ante=0;
			System.out.print("player1:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p1.setAnte(ante);
				p1.setCash(p1.getCash()-ante);
			}
			System.out.print("player2:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p2.setAnte(ante);
				p2.setCash(p2.getCash()-ante);
			}
			System.out.print("player3:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p3.setAnte(ante);
				p3.setCash(p3.getCash()-ante);
			}
			System.out.print("player14");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p4.setAnte(ante);
				p4.setCash(p4.getCash()-ante);
			}
		}catch(IndexOutOfBoundsException ex) {
			System.out.println("10�̻� �����ϼ���.");
			bet();
		}		
	}
	
	//ó�� ī�� �� �徿 �����ְ�, �����ְ�, ����Ȯ���ϰ�
	public void draw() {
		System.out.printf("%d���带 �����մϴ�. ī�带 ����մϴ�.", ++round);
		for(int i=0;i<2;i++) {
			ArrayList<PlayingCard> hand=new ArrayList<PlayingCard>();
			//hand=p1.getHand();
			hand.clear();
			hand.add(popCardDeck());
			p1.setHand(hand);
			hand.clear();
			hand.add(popCardDeck());
			p2.setHand(hand);
			hand.clear();
			hand.add(popCardDeck());
			p3.setHand(hand);
			hand.clear();
			hand.add(popCardDeck());
			p4.setHand(hand);
			dealer_hand.add(popCardDeck());
			dealer_sum+=dealer_hand.get(i).getNum();
		}
		
		System.out.printf("player 1�� ī��� %s�Դϴ�. ������ %d�Դϴ�.\n", p1.getHand().toString(),  p1.getSum());
		if(p1.getSum()==21&&p1.getHand().size()==2) {
			System.out.println("�����Դϴ�.");
			p1.setBlackjack(true);
		}
		System.out.printf("player 2�� ī��� %s�Դϴ�. ������ %d�Դϴ�.\n", p2.getHand().toString(),  p2.getSum());
		if(p2.getSum()==21&&p2.getHand().size()==2){
			System.out.println("�����Դϴ�.");
			p2.setBlackjack(true);
		}
		System.out.printf("player 3�� ī��� %s�Դϴ�. ������ %d�Դϴ�.\n", p3.getHand().toString(),  p3.getSum());
		if(p3.getSum()==21&&p3.getHand().size()==2){
			System.out.println("�����Դϴ�.");
			p3.setBlackjack(true);
		}
		System.out.printf("player 4�� ī��� %s�Դϴ�. ������ %d�Դϴ�.\n", p4.getHand().toString(),  p4.getSum());
		if(p4.getSum()==21&&p4.getHand().size()==2){
			System.out.println("�����Դϴ�.");
			p4.setBlackjack(true);
		}
		System.out.printf("������ ī���� �ϳ��� %s�Դϴ�.\n", dealer_hand.get(1).toString(), dealer_sum);
	}
	
	public void hit_or_stand(BlackJackPlayer p) {
		int size=p.getHand().size();
		boolean dd=p.isDoubledown();
		if(p.getSum()<21&& !dd) {
			if(size==2) {
				System.out.println("Hit or Stand? Or Double down?");
				while(true) {
				String isNext = in.nextLine();
				if(isNext.equals("hit") || isNext.equals("Hit")) {
					hit(p);
					System.out.printf("���� ī��� %s�Դϴ�.",p.getHand().get(0).toString());
					System.out.printf("������ %d�Դϴ�.\n",p.getSum());
				}
				else if(isNext.equals("stand")|| isNext.equals("Stand")) {
					//return;
					break;
				}
				else if(isNext.equals("double down")|| isNext.equals("Double down")) {
					p.setDoubledown(true);
					hit(p);
					break;
				}
				else {
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���(Hit OR Stand OR Double down).");				}
				}
			}
			else {
				System.out.println("Hit or Stand?");
				while(true) {
					String isNext = in.nextLine();
					if(isNext.equals("hit") || isNext.equals("Hit")) {
						break;
					}
					else if(isNext.equals("stand")|| isNext.equals("Stand"))
						break;				
					else 
						System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���(Hit OR Stand).");
					
				}
			}
		}
		else if(p.getSum()==21){
			if(size==2)
				System.out.println("���� �Դϴ�!");
			System.out.println("ī���� ���� 21�Դϴ�.");
		}
		else {
			System.out.println("ī���� ���� 21�̻��Դϴ�. Bust�Ͽ����ϴ�.");
		}		
	}

	public void hit(BlackJackPlayer p) {
		ArrayList<PlayingCard> hand=p.getHand();
		hand.add(popCardDeck());
		p.setHand(hand);
		if(p.isDoubledown()) {
			System.out.println("����ٿ� �ϼ̽��ϴ�.");
			int ante=p.getAnte();
			ante+=ante;
			p.setAnte(ante);
			p.setDoubledown(true);
		}
	}



}
