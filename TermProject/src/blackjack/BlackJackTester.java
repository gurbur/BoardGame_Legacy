package blackjack;

import java.util.Scanner;


public class BlackJackTester {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		BlackJackGame blackjackGame;
		blackjackGame = new BlackJackGame();//tester
		
		/*
		 * System.out.println("���ӽ���: start, ��������: exit");
		while(true) {
			String isStart = in.nextLine();
			if(isStart.equals("start") || isStart.equals("Start")) {
				blackjackGame = new BlackJackGame();
				break;
			}
			else if(isStart.equals("exit"))
				return;
			else {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���(start OR exit).");
			}
		}
		*/
		
		blackjackGame.gameStart();
		in.close();
	}
		
}
