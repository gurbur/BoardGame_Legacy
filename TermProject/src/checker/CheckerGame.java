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
			
			List<Integer> ableList = checkerBoard.getCapturableList(whosTurn);
			System.out.println("====================");
			for(int i = 0; i < ableList.size(); i++) {
				System.out.printf("%d\n", ableList.get(i));
			}
			System.out.println("====================");
			
			ableList = subtractByCondition(ableList);
			System.out.println("Subtracted");
			
			System.out.println("====================");
			for(int i = 0; i < ableList.size(); i++) {
				System.out.printf("%d\n", ableList.get(i));
			}
			System.out.println("====================");
			
			while(true) { // loop for moving
				try {
					if(ableList.isEmpty()) {
					
						System.out.println("��� ��ġ�� �ִ� ���� �����ϱ��?");
						int position = in.nextInt();
						if(position < 0 || position > 32)
							throw new IndexOutOfBoundsException();
						if(checkerBoard.getBlank(position).isEmpty())
							throw new InputMismatchException("�� ��ġ���� ���� �����ϴ�.");
						if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
							throw new InputMismatchException("�÷��̾��� ���� �ƴմϴ�.");
						
						System.out.println("���� ��� ��ġ�� �ű���?");
						int targetPosition = in.nextInt();
						if((targetPosition < 0 && targetPosition > 32))
							throw new IndexOutOfBoundsException();
						if(!checkerBoard.getBlank(targetPosition).isEmpty() || position == targetPosition || !checkerBoard.isConnected(position, targetPosition))
							throw new InputMismatchException("�� ��ġ�δ� ���� ������ �� �����ϴ�.");
						if(whosTurn == true && position > targetPosition && checkerBoard.getMarker(position).getType() != 1) // 1p�� ���
							throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
						else if(whosTurn == false && position < targetPosition && checkerBoard.getMarker(position).getType() != 1) // 2p�� ���
							throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
						
						
						
						checkerBoard.move(position, targetPosition);
						System.out.println("���� �̵��Ͽ����ϴ�.");
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 2p�� ���
							kingMe(targetPosition);
						whosTurn = !whosTurn;
						break; // break loop for moving
					
					}
					else {
						System.out.println("*����� ���� ���� �� �ִ� ���� �ֽ��ϴ�.");
						
						System.out.println("��� ��ġ�� �ִ� ���� �����ϱ��?");
						int position = in.nextInt();
						//in.nextLine();
						if(position < 0 || position > 32)
							throw new IndexOutOfBoundsException();
						if(checkerBoard.getBlank(position).isEmpty())
							throw new InputMismatchException("�� ��ġ���� ���� �����ϴ�.");
						if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
							throw new InputMismatchException("�÷��̾��� ���� �ƴմϴ�.");
						if(ableList.indexOf(position) % 2 != 0)
							throw new InputMismatchException("����� ���� ���� �� �ִ� ���� ���� ������ ��ƾ߸� �մϴ�.");
						
						
						System.out.println("���� ��� ��ġ�� �ű���?");
						
						int targetPosition = in.nextInt();
						if((targetPosition < 0 && targetPosition > 32))
							throw new IndexOutOfBoundsException();
						if(!(checkerBoard.getBlank(targetPosition).isEmpty()) || position == targetPosition)
							throw new InputMismatchException("�� ��ġ�δ� ���� ������ �� �����ϴ�.");
						
						int a;
						
						if(checkerBoard.getMarker(position).getPlayer() == 0) {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						else {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						
						if (checkerBoard.getMarker(position).getType() != 1) { // King�� �ƴ� ��.
							if(checkerBoard.getMarker(position).getPlayer() == 0) { // 1p
								if(position < targetPosition) { // downward(forward)
									
									checkerBoard.captureMarker(position, a, targetPosition);//���� ���,
									checkerBoard.move(position, a);//�� ĭ,
									checkerBoard.move(a, targetPosition);//�� ĭ �̵�.
									
									System.out.println("����� ���� ��ҽ��ϴ�!");
								}
								else {// upward(backward)
									throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
								}
								
							}
							else { // 2p
								if(position < targetPosition) { // downward(backward)
									throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
								}
								else { // upward(forward)
									checkerBoard.captureMarker(position, a, targetPosition);
									checkerBoard.move(position, a);
									checkerBoard.move(a, targetPosition);
									
									System.out.println("����� ���� ��ҽ��ϴ�!");
								}
							}
						}
						else {
							checkerBoard.captureMarker(position, a, targetPosition);
							checkerBoard.move(position, a);
							checkerBoard.move(a, targetPosition);
							
							System.out.println("����� ���� ��ҽ��ϴ�!");
						}
						//���Ӷٱ�
						
						ableList = checkerBoard.getCapturableList(whosTurn);
						
						ableList = subtractByCondition(ableList);

						if(ableList.indexOf(targetPosition) % 2 == 0) {
							System.out.println("���� �� �ִ� ���� �� �ֽ��ϴ�!");
							whosTurn = !whosTurn;	
						}
						
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						whosTurn = !whosTurn;
						break; // break loop for moving
					}
				} catch(InputMismatchException ex) {
					if(ex.getMessage() == null)
						System.out.println("�߸��� �Է��Դϴ�: 0~32������ ���� �־��ּ���.\n");
					else
						System.out.printf("�߸��� �Է��Դϴ�: %s\n", ex.getMessage());
					in.nextLine();
				} catch(IndexOutOfBoundsException ex) {
					System.out.printf("�߸��� �Է��Դϴ�: 0~32������ ���� �Է����ּ���.\n");
					ex.printStackTrace();
				}/* catch(Exception ex) {
					System.out.printf("%s\n", ex.getMessage());
				}*/
				
			} // end loop for moving
			
			
			
			if(whosTurn != whoFirst)
				savePastBoard();
			if(isDraw()) {
				printGameBoard();
				System.out.println("������ ���°� 3�� ° �����ϴ�. ���º� ó���մϴ�.");
				break;
			}
			
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
		/*for (int i = 0; i < 12; i++) {
			checkerBoard.onBoard(new Marker(0, 0), i, i);
			checkerBoard.onBoard(new Marker(1, 0), i + 20, i + 12);
		}  //default case	
		*/
		checkerBoard.onBoard(new Marker(0, 1), 0, 0);
		checkerBoard.onBoard(new Marker(1, 1), 31, 1);
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
	
	private List<Integer> subtractByCondition(List<Integer> ableList) {
		for(int i = ableList.size() - 1; i >= 0; i--) { //���� �ʰ� ���� ������ �Ųٷ� ����
			int temp = ableList.get(i);
			if(i % 2 == 0) {
				if(!(checkerBoard.getBlank(temp).isEmpty()) && checkerBoard.getMarker(temp).getType() == 0) { // king�� ��츦 ����
					if(whosTurn) { // 1p�� ���
						if(temp > ableList.get(i + 1)) {
							System.out.println("7");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else { // 2p�� ���
						if(temp < ableList.get(i + 1)) {
							System.out.println("8");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
				}
				if(ableList.size() >= 2) {
					// upward
					if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == 3) {
						if(!checkerBoard.getBlank(temp - 7).isEmpty()) {
							System.out.println("9");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == 5) {
						if(!checkerBoard.getBlank(temp - 9).isEmpty()) {
							System.out.println("10");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == 4) {
						System.out.printf("%s\n", checkerBoard.isConnectedSilently(temp, temp - 7));
						System.out.printf("%s\n", !(checkerBoard.getBlank(temp - 7).isEmpty()));
						System.out.printf("%s\n", checkerBoard.isConnectedSilently(temp, temp - 9));
						System.out.printf("%s\n", !(checkerBoard.getBlank(temp - 9).isEmpty()));
						if(temp == 22) {
							System.out.printf("%s\n", checkerBoard.getBlank(temp - 9).getData().getPlayer());
							System.out.printf("%s\n", checkerBoard.getBlank(temp - 9).getData().getType());
						}
						
						
						if(checkerBoard.isConnectedSilently(temp, temp - 7) && !(checkerBoard.getBlank(temp - 7).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp - 7)) {
							System.out.println("1");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
						else if(checkerBoard.isConnectedSilently(temp, temp - 9) && !(checkerBoard.getBlank(temp - 9).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp - 9)) {
							System.out.println("2");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					// downward
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == -3) {
						if(!checkerBoard.getBlank(temp + 7).isEmpty()) {
							System.out.println("3");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == -5) {
						if(!checkerBoard.getBlank(temp + 9).isEmpty()) {
							System.out.println("4");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == -4) {
						if(checkerBoard.isConnectedSilently(temp, temp + 7) && !(checkerBoard.getBlank(temp + 7).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp + 7)) {
							System.out.println("5");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
						else if(checkerBoard.isConnectedSilently(temp, temp + 9) && !(checkerBoard.getBlank(temp + 9).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp + 9)) {
							System.out.println("6");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					
					
				}
			}
		}
		return ableList;
	}
}
