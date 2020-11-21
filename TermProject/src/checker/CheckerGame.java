package checker;

import java.util.InputMismatchException;
import java.util.Scanner;
import board.CheckerBoard;
import game.Game;
import marker.Marker;

public class CheckerGame implements Game{
	
	private CheckerBoard checkerBoard;
	private CheckerBoard[] pastCheckerBoard = new CheckerBoard[5];
	
	private final boolean whoFirst; //true == 1p, false == 2p
	private boolean whosTurn; //true == 1p, false == 2p
	
	private Scanner in;
	
	public CheckerGame(boolean whoFirst) {
		this.whoFirst = whoFirst;
	}
	
	@Override
	public void gameStart() {
		//initialization
		checkerBoard = new CheckerBoard();
		whosTurn = whoFirst;
		makeBoard();
		in = new Scanner(System.in);
		//main game start
		mainGame();
	}
	
	private void mainGame() {
		while(true) {
			
			int doesWinnerExist = doesWinnerExist();
			if(doesWinnerExist == 0) {
				System.out.println("1P�� �¸��Ͽ����ϴ�.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P�� �¸��Ͽ����ϴ�.");
				break;
			}
			
			printWhoNext();
			System.out.println("====================");
			printGameBoard();
			System.out.println("====================");
			while(true) {
				try {
					System.out.println("��� ��ġ�� �ִ� ���� �����ϱ��?");
					int position = in.nextInt();
					if(position < 0 && position > 32)
						throw new IndexOutOfBoundsException();
					if(checkerBoard.getBlank(position).isEmpty())
						throw new InputMismatchException("�� ��ġ���� ���� �����ϴ�.");
					if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
						throw new InputMismatchException("�÷��̾��� ���� �ƴմϴ�.");
					
					System.out.println("���� ��� ��ġ�� �ű���?");
					int targetPosition = in.nextInt();
					if((targetPosition < 0 && targetPosition > 32))
						throw new IndexOutOfBoundsException();
					if(!checkerBoard.getBlank(targetPosition).isEmpty() || position == targetPosition)
						throw new InputMismatchException("�� ��ġ�δ� ���� ������ �� �����ϴ�.");
					
					break;
				} catch(InputMismatchException ex) {
					System.out.printf("�߸��� �Է��Դϴ�: %s\n", ex.getMessage());
					in.nextLine();
				} catch(IndexOutOfBoundsException ex) {
					System.out.printf("�߸��� �Է��Դϴ�: 0~32������ ���� �Է����ּ���.\n");
				}
			}
			
			
			
			
			savePastBoard();
			if(isDraw()) {
				System.out.println("������ ���°� 3�� ° �����ϴ�. ���º� ó���մϴ�.");
				break;
			}
			whosTurn = !whosTurn;
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "ü�� ��(8*8)���� �����ϴ� �����Դϴ�.\n";
		output += "�� ���� �밢�� �������� �ۿ� ������ �� ������, ���� ����� �� �ٷ� ������ �� ĭ ����� ��쿡�� �� ���� �پ�Ѿ �ݵ�� ��ƸԾ�� �մϴ�.\n";
		output += "���� ��Ƹ��� �� �ִ� ���� ���������� �ִ� ��쿡��, �� �Ͽ� ���ӵǾ� �ִ� ���� ��� ��ƸԾ�߸� �մϴ�.\n";
		output += "�̷� ���, �� �Ͽ� ���� ���� ������ ������ �� �ֽ��ϴ�.\n";
		output += "���� ���� ������ ������ ������ �����ϸ�,'ŷ(King)'����(A => Ak, B => Bk)�� �Ǿ�, �밢�� �Ĺ����ε� �̵��� �� �ְ� �˴ϴ�.\n";
		output += "������ ���� ��� �������� �¸��մϴ�. �������� ���°� �� �� ���� �ϴٸ� ���ºΰ� �˴ϴ�.\n";
		
		return output;
	}
	
	private void printGameBoard() {
		System.out.println(checkerBoard);
	}
	
	private void makeBoard() {
		for (int i = 0; i < 8; i++) {
			checkerBoard.onBoard(new Marker(0, 0), i, i);
			checkerBoard.onBoard(new Marker(1, 0), i + 24, i + 8);
		}	
		
	}
	
	private void printWhoNext() {
		if(whosTurn)
			System.out.println("1�� �÷��̾��� ���Դϴ�.");
		else
			System.out.println("2�� �÷��̾��� ���Դϴ�.");
	}
	
	private int doesWinnerExist() { //0: A�� �̱�, 1: B�� �̱�, -1: ���� �ȳ�
		int countA = 0;
		int countB = 0;
		for(int i = 0; i < 32; i++) {
			if(checkerBoard.isOnBoard(i)) {
				if(checkerBoard.getMarker(i).getPlayer() == 0) countA++;
				else if(checkerBoard.getMarker(i).getPlayer() == 1) countB++;
			}
		}
		if(countA == 0) return 1;
		else if(countB == 0) return 0;
		else return -1;
	}
	
	private void savePastBoard() {
		if(pastCheckerBoard[0].equals(null))
			pastCheckerBoard[0] = checkerBoard;
		if(!pastCheckerBoard[0].equals(null) && pastCheckerBoard[1].equals(null))
			pastCheckerBoard[1] = pastCheckerBoard[0];
		if(!pastCheckerBoard[0].equals(null) &&!pastCheckerBoard[1].equals(null) && pastCheckerBoard[2].equals(null))
			pastCheckerBoard[2] = pastCheckerBoard[1];
		if(!pastCheckerBoard[0].equals(null) &&!pastCheckerBoard[1].equals(null) &&!pastCheckerBoard[2].equals(null) && pastCheckerBoard[3].equals(null))
			pastCheckerBoard[3] = pastCheckerBoard[2];
		if(!pastCheckerBoard[0].equals(null) &&!pastCheckerBoard[1].equals(null) &&!pastCheckerBoard[2].equals(null) &&!pastCheckerBoard[3].equals(null) && pastCheckerBoard[4].equals(null))
			pastCheckerBoard[4] = pastCheckerBoard[3];
	}
	
	private boolean isDraw() {
		if(!(pastCheckerBoard[2].equals(null)) && checkerBoard.equals(pastCheckerBoard[1]) && pastCheckerBoard[0].equals(pastCheckerBoard[2]) && pastCheckerBoard[1].equals(pastCheckerBoard[3]) && pastCheckerBoard[2].equals(pastCheckerBoard[4]))
			return true;
		else
			return false;
	}
	
}
