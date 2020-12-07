package blackjack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import card.PlayingCard;
import card.PlayingCardDeck;
import game.Game;

public class BlackJackGame implements Game{
	
	private ArrayList<PlayingCard> playingCardDeck = new ArrayList<PlayingCard>();
	//플레이어 4명
	private BlackJackPlayer p1,p2,p3,p4;
	//카드패
	private ArrayList<PlayingCard> dealer_hand=new ArrayList<PlayingCard>();
	//카드 합 
	private int dealer_sum=0;
	private boolean dealer_blackjack=false;
	private boolean dealer_bust=false;
	//몇번째 게임인지
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
				System.out.printf("%d번째 라운드입니다.\n\n",++round);
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
    //반환값 수정 가능
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
	//각자 걸 돈을 입력받는다.
	public void bet() {
		System.out.println("베팅을 시작합니다. 금액을 입력하세요.(10이상, 제한 없음)\n");
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
			System.out.println("10이상 베팅하세요.");
			bet();
		}
		System.out.println();
	}
	//처음 카드 두 장씩 나눠주고, 보여주고, 블랙잭확인하고
	public void draw() {
		for(int i=0;i<2;i++) {
			p1.addHand(popCard());
			p2.addHand(popCard());
			p3.addHand(popCard());
			p4.addHand(popCard());
			dealer_hand.add(popCard());			
		}
		int sum=0;
		System.out.println("Player1의 차례입니다.");
		System.out.printf(" 카드는 %s입니다.", p1.getHand().toString());		
		sum=p1.getHand().get(0).getNum_forBlackjack()+p1.getHand().get(1).getNum_forBlackjack();
		p1.setSum(sum);
		ace(p1,p1.getHand().get(0));
		ace(p1,p1.getHand().get(1));
		System.out.printf(" 총합은 %d입니다.\n", p1.getSum());
	
		System.out.println("Player2의 차례입니다.");
		System.out.printf(" 카드는 %s입니다.", p2.getHand().toString());
		sum=p2.getHand().get(0).getNum_forBlackjack()+p2.getHand().get(1).getNum_forBlackjack();
		p2.setSum(sum);
		ace(p2,p2.getHand().get(0));
		ace(p2,p2.getHand().get(1));
		System.out.printf(" 총합은 %d입니다.\n", p2.getSum());
		
		System.out.println("Player3의 차례입니다.");
		System.out.printf(" 카드는 %s입니다.", p3.getHand().toString());
		sum=p3.getHand().get(0).getNum_forBlackjack()+p3.getHand().get(1).getNum_forBlackjack();
		p3.setSum(sum);
		ace(p3,p3.getHand().get(0));
		ace(p3,p3.getHand().get(1));
		System.out.printf(" 총합은 %d입니다.\n", p3.getSum());
		
		System.out.println("Player4의 차례입니다.");
		System.out.printf(" 카드는 %s입니다.", p4.getHand().toString());
		sum=p4.getHand().get(0).getNum_forBlackjack()+p4.getHand().get(1).getNum_forBlackjack();
		p4.setSum(sum);
		ace(p4,p4.getHand().get(0));
		ace(p4,p4.getHand().get(1));
		System.out.printf(" 총합은 %d입니다.\n", p4.getSum());
		
		System.out.printf("딜러의 카드중 하나는 %s입니다.\n\n", dealer_hand.get(1).toString());
	}
	//
	public void hit_or_stand(BlackJackPlayer p) {
		//두개의 합이 21미만
		System.out.printf("%s의 차례입니다.\n",p.getName());
		if(p.getSum()<21) {
			while(true) {
				System.out.printf("합이 %d입니다.",p.getSum());
				System.out.println(" 21이하입니다.(Hit OR Stand)");				
				String isNext = in.nextLine();
				if(isNext.equals("hit") || isNext.equals("Hit")) {
					hit(p);
					break;
				}				
				else if(isNext.equals("stand")|| isNext.equals("Stand")) 
					break;
				else 
					System.out.println("잘못된 입력입니다. 다시 입력해 주세요.(Hit OR Stand)");											
			}
		}
		//합이 21
		else if(p.getSum()==21){
			int size=p.getHand().size();
			if(size==2) {
				System.out.print("블랙잭 입니다! ");
				p.setBlackjack(true);
			}
			System.out.println("카드의 합이 21입니다.");
		}
		//bust
		else {
			System.out.println("카드의 합이 21이상입니다. Bust하였습니다.");
			p.setBust(true);
		}		
	}

	public void hit(BlackJackPlayer p) {
		int size=p.getHand().size();
		if(size==2) {//더블다운 시작
			System.out.println("더블다운 하시겠습니까?(yes or no, 더블다운 이후 카드를 뽑을 수 없음.)");
			while(true) {
				String isNext = in.nextLine();
				if(isNext.equals("yes") || isNext.equals("Yes")) {
					System.out.println("더블다운입니다. 건 돈을 두배로 올립니다.");
					int ante=p.getAnte();
					ante+=ante;
					p.setAnte(ante);
					p.setDoubledown(true);
					break;
				}				
				else if(isNext.equals("no")|| isNext.equals("No")) 
					break;
				else 
					System.out.println("잘못된 입력입니다. 다시 입력해 주세요.(yes or no)");											
			}
		}//더블다운 끝
		p.addHand(popCard());//여기까지 카드 한장 추가한것
		System.out.printf("뽑은 카드는 %s입니다.",p.getHand().get(0).toString());
		p.setSum(p.getSum()+p.getHand().get(0).getNum_forBlackjack());
		ace(p,p.getHand().get(0));
		System.out.printf("총합은 %d입니다.\n",p.getSum());
		if(!p.isDoubledown()) {
			hit_or_stand(p);
		}
	}
	
	public void dealer_play() {
		in.nextLine();
		System.out.printf("딜러의 카드는 %s 입니다.\n",dealer_hand.toString());
		dealer_sum+=dealer_hand.get(0).getNum_forBlackjack();			
		dealer_sum+=dealer_hand.get(1).getNum_forBlackjack();
		for(int i=0;i<2;i++) {
			if(dealer_hand.get(i).getNum()==1) {
				System.out.println("Ace를 11로 계산하시겠습니까?");			
				while(true) {
					String isNext= in.nextLine();//NullPointerException 발생지점
					if(isNext.equals("yes") || isNext.equals("Yes")) {
						dealer_sum+=10;
						break;
					}				
					else if(isNext.equals("no")|| isNext.equals("No")) 
						break;
					else 
						System.out.println("잘못된 입력입니다. 다시 입력해 주세요.(yes or no)");											
				}
			}
		}

		if(dealer_sum==21)
			dealer_blackjack=true;
		if(dealer_sum<17) {
			System.out.println("합이 17보다 작으므로 추가로 카드를 뽑습니다.");
			dealer_hand.add(popCard());
			dealer_sum+=dealer_hand.get(0).getNum_forBlackjack();				
			System.out.printf("뽑은 카드는 %s 입니다. 합은 %d입니다.\n",dealer_hand.get(0).toString(), dealer_sum);
			if(dealer_sum<17) {
				dealer_play();
			}
		}
		System.out.printf("딜러의 점수는 %d입니다.\n",dealer_sum);
		if(dealer_blackjack) {
			System.out.println("블랙잭입니다!");	
			dealer_blackjack=true;
		}
		if(dealer_sum>21) {
			System.out.println("딜러가 Bust하였습니다.");	
			dealer_bust=true;
		}
	}
	public void whoWin(BlackJackPlayer p) {
		if(p.isBust()) {
			System.out.printf("%s가 bust하여 건 돈 %d원을 잃습니다.\n",p.getName(),p.getAnte());	
			int cash=p.getCash()-p.getAnte();
			p.setCash(cash);
		}
		if(!p.isBust()&&dealer_bust) {
			System.out.printf("딜러가 bust하여 건 돈 %d원을 받습니다.\n",p.getAnte());	
			int cash=p.getCash()+p.getAnte();
			p.setCash(cash);
		}
		if(!p.isBust()&&!dealer_bust) {
			int sum=p.getSum();
			if(sum>dealer_sum) {
				System.out.printf("%s의 합이 딜러의 합보다 큽니다. 건 돈 %d원을 받습니다.\n",p.getName(),p.getAnte());	
				int cash=p.getCash()+p.getAnte();
				p.setCash(cash);
			}
			else if(sum<dealer_sum) {
				System.out.printf("%s의 합이 딜러의 합보다 작습니다. 건 돈 %d원을 잃습니다.\n",p.getName(),p.getAnte());	
				int cash=p.getCash()-p.getAnte();
				p.setCash(cash);
			}else {
				if(dealer_blackjack&&p.isBlackjack()) {
					System.out.printf("딜러와  %s가 모두 블랙잭으로, 무승부입니다.\n",p.getName());	
				}else if(!dealer_blackjack&&p.isBlackjack()) {
					System.out.printf("%s가 블랙잭으로 승리했습니다.\n",p.getName());	
					int cash=p.getCash()+p.getAnte();
					p.setCash(cash);
				}else if(dealer_blackjack&&!p.isBlackjack()) {
					System.out.printf("딜러가 블랙잭이므로  %s가 패배입니다.\n",p.getName());	
					int cash=p.getCash()-p.getAnte();
					p.setCash(cash);
				}else {
					System.out.printf("딜러와  %s의 합이 같으므로 무승부입니다.\n",p.getName());
				}
			}
		}		
		System.out.printf(" %s의 잔액은 %d원입니다.\n",p.getName(),p.getCash());	
	}
	public void game_result() {
		System.out.printf("%s의 잔액은 %d원입니다.\n",p1.getName(),p1.getCash());
		System.out.printf("%s의 잔액은 %d원입니다.\n",p2.getName(),p2.getCash());
		System.out.printf("%s의 잔액은 %d원입니다.\n",p3.getName(),p3.getCash());
		System.out.printf("%s의 잔액은 %d원입니다.\n",p4.getName(),p4.getCash());
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
		System.out.printf("우승자는 플레이어 p%d입니다. 잔액은 %d원입니다.\n",(maxnum+1),max);
	}
	public void ace(BlackJackPlayer p,PlayingCard c) {
		if(c.getNum()==1) {
			System.out.println("Ace를 11로 계산하시겠습니까?");			
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
					System.out.println("잘못된 입력입니다. 다시 입력해 주세요.(yes or no)");											
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
