package checker;

import java.util.Scanner;

public class CheckerTester {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		CheckerGame checkerGame;
		System.out.println("누가 선공할 지 선택해 주세요(1p OR 2p OR default(=1p)).");
		
		while(true) {
			String whoFirst = in.nextLine();
			if(whoFirst.equals("1p") || whoFirst.equals("default")) {
				checkerGame = new CheckerGame(true);
				break;
			}
			else if(whoFirst.equals("2p")) {
				checkerGame = new CheckerGame(false);
				break;
			}
			else if(whoFirst.equals("exit"))
				return;
			else {
				System.out.println("잘못된 입력입니다. 다시 입력해 주세요(1p OR 2p).");
			}
		}
		
		checkerGame.gameStart();
		
	}

}
