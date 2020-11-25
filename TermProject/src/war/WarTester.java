package war;

import java.util.Scanner;


public class WarTester {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		WarGame warGame;
		System.out.println("게임시작: start, 게임종료: exit");
		while(true) {
			String isStart = in.nextLine();
			if(isStart.equals("start") || isStart.equals("Start")) {
				warGame = new WarGame();
				break;
			}
			else if(isStart.equals("exit"))
				return;
			else {
				System.out.println("잘못된 입력입니다. 다시 입력해 주세요(start OR exit).");
			}
		}
		
		warGame.gameStart();
		in.close();
	}

}
