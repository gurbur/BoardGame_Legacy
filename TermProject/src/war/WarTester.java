package war;

import java.util.Scanner;


public class WarTester {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		WarGame warGame;
		System.out.println("���ӽ���: start, ��������: exit");
		while(true) {
			String isStart = in.nextLine();
			if(isStart.equals("start") || isStart.equals("Start")) {
				warGame = new WarGame();
				break;
			}
			else if(isStart.equals("exit"))
				return;
			else {
				System.out.println("�߸��� �Է��Դϴ�. �ٽ� �Է��� �ּ���(start OR exit).");
			}
		}
		
		warGame.gameStart();
		in.close();
	}

}
