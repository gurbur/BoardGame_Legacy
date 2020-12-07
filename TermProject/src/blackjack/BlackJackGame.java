package blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import card.PlayingCard;
import card.PlayingCardDeck;
import game.Game;

public class BlackJackGame implements Game{
	
	private ArrayList<PlayingCard> playingCardDeck = new ArrayList<PlayingCard>();
	//�÷��̾� 4��
	private BlackJackPlayer p1,p2,p3,p4;
	//ī����
	private ArrayList<PlayingCard> dealer_hand=new ArrayList<PlayingCard>();
	//ī�� �� 
	private int dealer_sum=0;
	private boolean dealer_blackjack=false;
	private boolean dealer_bust=false;
	//���° ��������
	private int round;
	
	private Scanner in;
	
	public BlackJackGame() {
		p1=new BlackJackPlayer("Player1");
		p2=new BlackJackPlayer("Player2");
		p3=new BlackJackPlayer("Player3");
		p4=new BlackJackPlayer("Player4");
		dealer_hand.clear();
		dealer_sum=0;
		dealer_blackjack=false;
		dealer_bust=false;

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
			for(int i=0;i<10;i++) {
				System.out.println("*************************************");
				System.out.printf("%d��° �����Դϴ�.\n\n",++round);
				round();
			}
			game_result(); 
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
	public PlayingCard popCard() {
		PlayingCard temp = playingCardDeck.get(0);
		//System.out.printf("%c%d ", temp.getSuit(), temp.getNum());
		playingCardDeck.remove(0);
		return temp;
	}
	
	public void round() {
		bet();
		draw();
		hit_or_stand(p1);
		hit_or_stand(p2);
		hit_or_stand(p3);
		hit_or_stand(p4);
		dealer_play();
		whoWin(p1);
		whoWin(p2);
		whoWin(p3);
		whoWin(p4);
		round_init(p1);
		round_init(p2);
		round_init(p3);
		round_init(p4);
		dealer_hand.clear();
		dealer_sum=0;
		dealer_bust=false;
		dealer_blackjack=false;
	}
	//���� �� ���� �Է¹޴´�.
	public void bet() {
		System.out.println("������ �����մϴ�. �ݾ��� �Է��ϼ���.(10�̻�, ���� ����)\n");
		int ante=0;
		try {
			System.out.print("player1:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p1.setAnte(ante);
			}			
			System.out.print("player2:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p2.setAnte(ante);
			}
			System.out.print("player3:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p3.setAnte(ante);
			}
			System.out.print("player4:");
			ante=in.nextInt();
			in.nextLine();
			if(ante<10) 
				throw new IndexOutOfBoundsException();
			else {
				p4.setAnte(ante);
			}
		}catch(IndexOutOfBoundsException ex) {
			System.out.println("10�̻� �����ϼ���.");
			bet();
		}
		System.out.println();
	}
	//ó�� ī�� �� �徿 �����ְ�, �����ְ�, ����Ȯ���ϰ�
	public void draw() {
		for(int i=0;i<2;i++) {
			p1.addHand(popCard());
			p2.addHand(popCard());
			p3.addHand(popCard());
			p4.addHand(popCard());
			dealer_hand.add(popCard());			
		}
		int sum=0;
		System.out.println("Player1�� �����Դϴ�.");
		System.out.printf(" ī��� %s�Դϴ�.", p1.getHand().toString());		
		sum=p1.getHand().get(0).getNum_forBlackjack()+p1.getHand().get(1).getNum_forBlackjack();
		p1.setSum(sum);
		ace(p1,p1.getHand().get(0));
		ace(p1,p1.getHand().get(1));
		System.out.printf(" ������ %d�Դϴ�.\n", p1.getSum());
	
		System.out.println("Player2�� �����Դϴ�.");
		System.out.printf(" ī��� %s�Դϴ�.", p2.getHand().toString());
		sum=p2.getHand().get(0).getNum_forBlackjack()+p2.getHand().get(1).getNum_forBlackjack();
		p2.setSum(sum);
		ace(p2,p2.getHand().get(0));
		ace(p2,p2.getHand().get(1));
		System.out.printf(" ������ %d�Դϴ�.\n", p2.getSum());
		
		System.out.println("Player3�� �����Դϴ�.");
		System.out.printf(" ī��� %s�Դϴ�.", p3.getHand().toString());
		sum=p3.getHand().get(0).getNum_forBlackjack()+p3.getHand().get(1).getNum_forBlackjack();
		p3.setSum(sum);
		ace(p3,p3.getHand().get(0));
		ace(p3,p3.getHand().get(1));
		System.out.printf(" ������ %d�Դϴ�.\n", p3.getSum());
		
		System.out.println("Player4�� �����Դϴ�.");
		System.out.printf(" ī��� %s�Դϴ�.", p4.getHand().toString());
		sum=p4.getHand().get(0).getNum_forBlackjack()+p4.getHand().get(1).getNum_forBlackjack();
		p4.setSum(sum);
		ace(p4,p4.getHand().get(0));
		ace(p4,p4.getHand().get(1));
		System.out.printf(" ������ %d�Դϴ�.\n", p4.getSum());
		
		System.out.printf("������ ī���� �ϳ��� %s�Դϴ�.\n\n", dealer_hand.get(1).toString());
	}
	//
	public void hit_or_stand(BlackJackPlayer p) {
		//�ΰ��� ���� 21�̸�
		System.out.printf("%s�� �����Դϴ�.\n",p.getName());
		if(p.getSum()<21) {
			while(true) {
				System.out.printf("���� %d�Դϴ�.",p.getSum());
				System.out.println(" 21�����Դϴ�.(Hit OR Stand)");				
				String isNext = in.nextLine();
				if(isNext.equals("hit") || isNext.equals("Hit")) {
					hit(p);
					break;
				}				
				else if(isNext.equals("stand")|| isNext.equals("Stand")) 
					break;
				else 
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���.(Hit OR Stand)");											
			}
		}
		//���� 21
		else if(p.getSum()==21){
			int size=p.getHand().size();
			if(size==2) {
				System.out.print("���� �Դϴ�! ");
				p.setBlackjack(true);
			}
			System.out.println("ī���� ���� 21�Դϴ�.");
		}
		//bust
		else {
			System.out.println("ī���� ���� 21�̻��Դϴ�. Bust�Ͽ����ϴ�.");
			p.setBust(true);
		}		
	}

	public void hit(BlackJackPlayer p) {
		int size=p.getHand().size();
		if(size==2) {//����ٿ� ����
			System.out.println("����ٿ� �Ͻðڽ��ϱ�?(yes or no, ����ٿ� ���� ī�带 ���� �� ����.)");
			while(true) {
				String isNext = in.nextLine();
				if(isNext.equals("yes") || isNext.equals("Yes")) {
					System.out.println("����ٿ��Դϴ�. �� ���� �ι�� �ø��ϴ�.");
					int ante=p.getAnte();
					ante+=ante;
					p.setAnte(ante);
					p.setDoubledown(true);
					break;
				}				
				else if(isNext.equals("no")|| isNext.equals("No")) 
					break;
				else 
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���.(yes or no)");											
			}
		}//����ٿ� ��
		p.addHand(popCard());//������� ī�� ���� �߰��Ѱ�
		System.out.printf("���� ī��� %s�Դϴ�.",p.getHand().get(0).toString());
		p.setSum(p.getSum()+p.getHand().get(0).getNum_forBlackjack());
		ace(p,p.getHand().get(0));
		System.out.printf("������ %d�Դϴ�.\n",p.getSum());
		if(!p.isDoubledown()) {
			hit_or_stand(p);
		}
	}
	
	public void dealer_play() {
		in.nextLine();
		System.out.printf("������ ī��� %s �Դϴ�.\n",dealer_hand.toString());
		dealer_sum+=dealer_hand.get(0).getNum_forBlackjack();			
		dealer_sum+=dealer_hand.get(1).getNum_forBlackjack();
		for(int i=0;i<2;i++) {
			if(dealer_hand.get(i).getNum()==1) {
				System.out.println("Ace�� 11�� ����Ͻðڽ��ϱ�?");			
				while(true) {
					String isNext= in.nextLine();//NullPointerException �߻�����
					if(isNext.equals("yes") || isNext.equals("Yes")) {
						dealer_sum+=10;
						break;
					}				
					else if(isNext.equals("no")|| isNext.equals("No")) 
						break;
					else 
						System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���.(yes or no)");											
				}
			}
		}

		if(dealer_sum==21)
			dealer_blackjack=true;
		if(dealer_sum<17) {
			System.out.println("���� 17���� �����Ƿ� �߰��� ī�带 �̽��ϴ�.");
			dealer_hand.add(popCard());
			dealer_sum+=dealer_hand.get(0).getNum_forBlackjack();				
			System.out.printf("���� ī��� %s �Դϴ�. ���� %d�Դϴ�.\n",dealer_hand.get(0).toString(), dealer_sum);
			if(dealer_sum<17) {
				dealer_play();
			}
		}
		System.out.printf("������ ������ %d�Դϴ�.\n",dealer_sum);
		if(dealer_blackjack) {
			System.out.println("�����Դϴ�!");	
			dealer_blackjack=true;
		}
		if(dealer_sum>21) {
			System.out.println("������ Bust�Ͽ����ϴ�.");	
			dealer_bust=true;
		}
	}
	public void whoWin(BlackJackPlayer p) {
		if(p.isBust()) {
			System.out.printf("%s�� bust�Ͽ� �� �� %d���� �ҽ��ϴ�.\n",p.getName(),p.getAnte());	
			int cash=p.getCash()-p.getAnte();
			p.setCash(cash);
		}
		if(!p.isBust()&&dealer_bust) {
			System.out.printf("������ bust�Ͽ� �� �� %d���� �޽��ϴ�.\n",p.getAnte());	
			int cash=p.getCash()+p.getAnte();
			p.setCash(cash);
		}
		if(!p.isBust()&&!dealer_bust) {
			int sum=p.getSum();
			if(sum>dealer_sum) {
				System.out.printf("%s�� ���� ������ �պ��� Ů�ϴ�. �� �� %d���� �޽��ϴ�.\n",p.getName(),p.getAnte());	
				int cash=p.getCash()+p.getAnte();
				p.setCash(cash);
			}
			else if(sum<dealer_sum) {
				System.out.printf("%s�� ���� ������ �պ��� �۽��ϴ�. �� �� %d���� �ҽ��ϴ�.\n",p.getName(),p.getAnte());	
				int cash=p.getCash()-p.getAnte();
				p.setCash(cash);
			}else {
				if(dealer_blackjack&&p.isBlackjack()) {
					System.out.printf("������  %s�� ��� ��������, ���º��Դϴ�.\n",p.getName());	
				}else if(!dealer_blackjack&&p.isBlackjack()) {
					System.out.printf("%s�� �������� �¸��߽��ϴ�.\n",p.getName());	
					int cash=p.getCash()+p.getAnte();
					p.setCash(cash);
				}else if(dealer_blackjack&&!p.isBlackjack()) {
					System.out.printf("������ �����̹Ƿ�  %s�� �й��Դϴ�.\n",p.getName());	
					int cash=p.getCash()-p.getAnte();
					p.setCash(cash);
				}else {
					System.out.printf("������  %s�� ���� �����Ƿ� ���º��Դϴ�.\n",p.getName());
				}
			}
		}		
		System.out.printf(" %s�� �ܾ��� %d���Դϴ�.\n",p.getName(),p.getCash());	
	}
	public void game_result() {
		System.out.printf("%s�� �ܾ��� %d���Դϴ�.\n",p1.getName(),p1.getCash());
		System.out.printf("%s�� �ܾ��� %d���Դϴ�.\n",p2.getName(),p2.getCash());
		System.out.printf("%s�� �ܾ��� %d���Դϴ�.\n",p3.getName(),p3.getCash());
		System.out.printf("%s�� �ܾ��� %d���Դϴ�.\n",p4.getName(),p4.getCash());
		int[] cash=new int[4];
		cash[0]=p1.getCash();
		cash[1]=p2.getCash();
		cash[2]=p3.getCash();
		cash[3]=p4.getCash();
		int i=0;
		int max=cash[0];
		int maxnum=0;
		for(i=1;i<4;i++) {
			if(cash[i]>max) {
				max=cash[i];
				maxnum=i;
			}
		}
		System.out.printf("����ڴ� �÷��̾� p%d�Դϴ�. �ܾ��� %d���Դϴ�.\n",(maxnum+1),max);
	}
	public void ace(BlackJackPlayer p,PlayingCard c) {
		if(c.getNum()==1) {
			System.out.println("Ace�� 11�� ����Ͻðڽ��ϱ�?");			
			while(true) {
				String isNext= in.nextLine();
				if(isNext.equals("yes") || isNext.equals("Yes")) {
					int sum=p.getSum();
					sum+=10;
					p.setSum(sum);
					break;
				}				
				else if(isNext.equals("no")|| isNext.equals("No")) 
					break;
				else 
					System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���.(yes or no)");											
			}
		}
	}
	public void round_init(BlackJackPlayer p) {
		p.setSum(0);
		p.setDoubledown(false);
		p.setBlackjack(false);
		p.setBust(false);
		p.initHand();	
	}

	@Override
	public String toString() {
		return "BlackJackGame []";
	}

}
