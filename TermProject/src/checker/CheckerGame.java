package checker;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
		
		for(int i = 0; i < 5; i++) {
			pastCheckerBoard[i] = new CheckerBoard();
		}
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
		while(true) { // main loop
			
			int doesWinnerExist = doesWinnerExist();
			if(doesWinnerExist == 0) {
				System.out.println("1P�� �¸��Ͽ����ϴ�.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P�� �¸��Ͽ����ϴ�.");
				break;
			}
			
			printWhoNext();
			printGameBoard();
			
			
			List<Integer> ableList = new ArrayList<Integer>();
			
			ableList = checkerBoard.getCapturableList();
			
			while(true) { // loop for moving
				try {
					if(ableList.isEmpty()) {
					
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
						if(whosTurn == true && position > targetPosition && checkerBoard.getMarker(position).getType() != 1) // 1p�� ���
							throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
						else if(whosTurn == false && position < targetPosition && checkerBoard.getMarker(position).getType() != 1) // 2p�� ���
							throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
						
						
						
						checkerBoard.move(position, targetPosition);
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						break; // break loop for moving
					
					}
					else {
						System.out.println("����� ���� ���� �� �ִ� ���� �ֽ��ϴ�.");
						System.out.printf("%d", ableList.get(0));
						if(ableList.size() > 1) {
							for(int i = 2; i < ableList.size(); i += 2)
								System.out.printf(", %d", ableList.get(i));
						}
						System.out.println("ĭ�� �ִ� ���� ������ ����� ���� ��ƾ߸� �մϴ�.\n");
						System.out.println("��� ��ġ�� �ִ� ���� �����ϱ��?");
						int position = in.nextInt();
						if(position < 0 && position > 32)
							throw new IndexOutOfBoundsException();
						if(checkerBoard.getBlank(position).isEmpty())
							throw new InputMismatchException("�� ��ġ���� ���� �����ϴ�.");
						if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
							throw new InputMismatchException("�÷��̾��� ���� �ƴմϴ�.");
						if(!ableList.contains(position))
							throw new InputMismatchException("����� ���� ���� �� �ִ� ���� ���� ������ ��ƾ߸� �մϴ�.");
						
						int target = ableList.get(ableList.indexOf(position) + 1);
						System.out.println("���� ��� ��ġ�� �ű���?");
						System.out.printf("%d", target);
						//ableList.index
						
						int targetPosition = in.nextInt();
						if((targetPosition < 0 && targetPosition > 32))
							throw new IndexOutOfBoundsException();
						if(!checkerBoard.getBlank(targetPosition).isEmpty() || position == targetPosition)
							throw new InputMismatchException("�� ��ġ�δ� ���� ������ �� �����ϴ�.");
						
						
						break; // break loop for moving
					}
				} catch(InputMismatchException ex) {
					System.out.printf("�߸��� �Է��Դϴ�: %s\n", ex.getMessage());
					in.nextLine();
				} catch(IndexOutOfBoundsException ex) {
					System.out.printf("�߸��� �Է��Դϴ�: 0~32������ ���� �Է����ּ���.\n");
				}
				
			}// end loop for moving
			
			
			
			if(whosTurn != whoFirst)
				savePastBoard();
			if(isDraw()) {
				printGameBoard();
				System.out.println("������ ���°� 3�� ° �����ϴ�. ���º� ó���մϴ�.");
				break;
			}
			whosTurn = !whosTurn;
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "ü�� ��(8*8)���� �����ϴ� �����Դϴ�.\n";
		output += "�� ���� �밢�� �������� �ۿ� ������ �� ������, ���� ����� �� �ٷ� ������ �� ĭ ����� ��쿡�� �� ���� �پ�Ѿ �ݵ�� ��ƾ� �մϴ�.\n";
		output += "���� ���� �� �ִ� ���� ���������� �ִ� ��쿡��, �� �Ͽ� ���ӵǾ� �ִ� ���� ��� ��ƾ߸� �մϴ�.\n";
		output += "�̷� ���, �� �Ͽ� ���� ���� ������ ������ �� �ֽ��ϴ�.\n";
		output += "���� ���� ������ ������ ������ �����ϸ�,'ŷ(King)'����(A => Ak, B => Bk)�� �Ǿ�, �밢�� �Ĺ����ε� �̵��� �� �ְ� �˴ϴ�.\n";
		output += "������ ���� ��� ������ �¸��մϴ�. �������� ���°� �� �� ���� �ϴٸ� ���ºΰ� �˴ϴ�.\n";
		
		return output;
	}
	
	private void printGameBoard() {
		System.out.println("=========================");
		System.out.print(checkerBoard);
		System.out.println("=========================");
	}
	
	private void makeBoard() {
		/*for (int i = 0; i < 8; i++) {
			checkerBoard.onBoard(new Marker(0, 0), i, i);
			checkerBoard.onBoard(new Marker(1, 0), i + 24, i + 8);
		}*/  //default case	
		
		/*
		 * for (int i = 0; i < 8; i++) { checkerBoard.onBoard(new Marker(0, 1), i, i);
		 * checkerBoard.onBoard(new Marker(1, 1), i + 24, i + 8); }
		 */ // test case: all marker is king
		
		checkerBoard.onBoard(new Marker(0, 0), 24, 0);
		checkerBoard.onBoard(new Marker(1, 0), 7, 1);
		// test case: only one marker each
		
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
		if(!pastCheckerBoard[0].isEmpty() &&!pastCheckerBoard[1].isEmpty() &&!pastCheckerBoard[2].isEmpty())
			pastCheckerBoard[3] = pastCheckerBoard[2];
		if(!pastCheckerBoard[0].isEmpty() &&!pastCheckerBoard[1].isEmpty())
			pastCheckerBoard[2] = pastCheckerBoard[1];
		if(!pastCheckerBoard[0].isEmpty())
			pastCheckerBoard[1] = pastCheckerBoard[0];
		pastCheckerBoard[0] = checkerBoard;
	}
	
	private boolean isDraw() {
		if(!(pastCheckerBoard[2].isEmpty()) && CheckerBoard.isEquals(checkerBoard,pastCheckerBoard[1]) && CheckerBoard.isEquals(pastCheckerBoard[0],pastCheckerBoard[2]) && CheckerBoard.isEquals(pastCheckerBoard[1],pastCheckerBoard[3]))
			return true;
		else
			return false;
	}
	
	private void kingMe(int position) {
		checkerBoard.exchangeToKing(position);
		System.out.printf("%dĭ, King Me!\n", position);
	}
	
}
