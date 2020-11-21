package checker;

import java.util.Scanner;
/*
 * Tester Å¬·¡½º¿¡¼­´Â ¼³Á¤°ª¸¸ ÀÔ·Â¹Ş°í GameÅ¬·¡½ºÀÇ gameStart()¸¦ È£­„ÇÏµµ·Ï ÇØÁÖ¼¼¿ä.
 * 
 */

public class CheckerTester {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		CheckerGame checkerGame;
		System.out.println("´©°¡ ¼±°øÇÒ Áö ¼±ÅÃÇØ ÁÖ¼¼¿ä(1p OR 2p OR default(=1p)).");
		
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
				System.out.println("Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØ ÁÖ¼¼¿ä(1p OR 2p).");
			}
		}
		
		checkerGame.gameStart();
		
	}

}
