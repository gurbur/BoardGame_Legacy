package checker;

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
	private int turn;
	
	private Scanner in;
	
	public CheckerGame(boolean whoFirst) {
		this.whoFirst = whoFirst;
		
		for(int i = 0; i < 5; i++) {
			pastCheckerBoard[i] = null;
		}
	}
	
	@Override
	public void gameStart() {
		//initialization
		checkerBoard = new CheckerBoard();
		whosTurn = whoFirst;
		makeBoard();
		in = new Scanner(System.in);
		turn = 0;
		
		//main game start
		mainGame();
	}
	
	private void mainGame() {
		while(true) { // main loop: ������ Ư���� ��Ȳ(�¸�, ���º� ��)�� �߻����� �ʴ� �̻�, ��� �����ϵ��� ����� ���ؼ� ���� ������ ���¸� ���ϵ��� �߽��ϴ�.
			
			
			if(turn % 2 == 0 && turn != 0) { // ���º� ���� ���� ���Ǻ� �Լ� ȣ���Դϴ�. ���� ¦����(2�� �÷��̾��� ���ʰ� ���� ��)�� ������ ���� �����ϵ��� �߽��ϴ�.
				savePastBoard();
			}
			
			if(isDraw()) { // ���º� ���� ���� ���Ǻ� �Լ� ȣ���Դϴ�. ���ºΰ� �߻��� ���, ���� ���¸� �����ְ� ���º� ó���Ѵٴ� ���� ����ְ� �����մϴ�.
				printGameBoard();
				System.out.println("������ ���°� 3�� ° �����ϴ�. ���º� ó���մϴ�.");
				break;
			}
			
			// ���ڰ� �����ϴ��� ���θ� �����ϴ� �κ��Դϴ�.
			int doesWinnerExist = doesWinnerExist();
			if(doesWinnerExist == 0) {
				System.out.println("1P�� �¸��Ͽ����ϴ�.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P�� �¸��Ͽ����ϴ�.");
				break;
			}
			// ---------------------------
			
			// ���� ���� �������� �����ְ�, ���� ���� ���¸� �����ִ� �κ��Դϴ�.
			printWhoNext();
			printGameBoard();
			// ---------------------------
			
			List<Integer> ableList = checkerBoard.getCapturableList(whosTurn); // �ǿ��� ���� �� �ִ� ���� �����ϴ��� ���θ� getCapturableList�� ���� �ҷ��ɴϴ�. ���̳�, �ٸ� ���ǵ鿡 ������, �Ϻ��� ���� �ƴϱ� ������, �Ʒ����� �ٽ��ѹ� ó���� ���ݴϴ�. 
			
			ableList = subtractByCondition(ableList); // ���� �� �ִ� ���� �ִ��� ���θ� ���� ����(��, ���� ��Ȳ ��)�� �´��� �ٽ��ѹ� Ȯ���մϴ�.
			
			while(true) { // loop for moving: �Է��� �߸����� ���� �ٽ��ѹ� �÷��̾�� ���� ���ƿ����� ���ֱ� ���� ���ѷ����Դϴ�. �������� ������ break�� ���� ���� ������ �˴ϴ�.
				try {// �÷��̾��� �Է¿� �̻��� ���� ��(���� ���� ��, �߸��� ����, �÷��̾��� ���� �ƴ� ���� ����, ���ڰ� �ƴ� �ٸ� ���� �Է��ϴ� ��� ���), ����ó���� ���� �ٽ� �Է��϶�� ������ ����ֱ� ���� try-catch�� �̿��߽��ϴ�.
					if(ableList.isEmpty()) {// ���� �� �ִ� ���� ���� ���� ����Դϴ�. �� ��쿡�� Ư���� ���� ó���� ���������� �ʽ��ϴ�.
					
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
						
						// ���� ���� ������ ���� ������ ��, �ڷ� ������ �� �ֵ��� �ϴ� 'King me'��Ģ�� ���� �κ��Դϴ�. �÷��̾�� �����ؾ��ϴ� ��ġ�� �޶� ���� ����� kingMe �Լ��� ȣ���ϵ��� �߽��ϴ�.
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 2p�� ���
							kingMe(targetPosition);
						// --------------------
						
						whosTurn = !whosTurn; // ��� �������� ���� ���� ������, ���� ��뿡�� �Ѿ�� ����Դϴ�.
						break; // break loop for moving
					}
					
					else { // ���� �� �ִ� ���� �ִ� ����Դϴ�. ����� ���� ���� �� ���� ������ �� ��ƾ� �Ѵٴ� �꿡 ����, ��������� �Ǿ����ϴ�.
						System.out.println("*����� ���� ���� �� �ִ� ���� �ֽ��ϴ�.");// ���� �� �ִ� ���� �ִٴ°� �����ݴϴ�. ������ ����� ���� ���� �� �ִ����� �����ַ� ������.. ���ܻ�Ȳ�� �ʹ� �������� �� ����� �ϴ� �����߽��ϴ�.
						
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
						
						// �Ʒ����ʹ� ���� ������, ����� ���� ��� ���� �κ��Դϴ�.
						int a;
						
						if(checkerBoard.getMarker(position).getPlayer() == 0) {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						else {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						
						if (checkerBoard.getMarker(position).getType() != 1) { // King�� �ƴ� ��.
							if(checkerBoard.getMarker(position).getPlayer() == 0) { // 1p
								if(position < targetPosition) { // downward(forward) 1p�� ���, �������� ������ ��������̱⿡, �̸� ����Ͽ� �ʱ� ��ġ�� ������ġ�� ũ��� ŷ�� �ƴ� ���� �ڷ� ���� ���ϰ� ���ҽ��ϴ�.
									
									checkerBoard.captureMarker(position, a, targetPosition);// 1. ����� ���� ���� ���,
									checkerBoard.move(position, a);//2. �� ĭ,
									checkerBoard.move(a, targetPosition);//3. �� ĭ �̵�.
									
									System.out.println("����� ���� ��ҽ��ϴ�!");
								}
								else {// upward(backward)
									throw new InputMismatchException("�ڷ� ������ �� �ִ� ���� ŷ ������ �� ���Դϴ�.");
								}
								
							}// end of 1p
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
						}// end of case for not king
						else { // king�� ���, ���̵� �ڵ� ���ϴ´�� ������ �� �ֱ� ������ Ư���� ���� ó���� �ʿ� �������ϴ�.
							checkerBoard.captureMarker(position, a, targetPosition);
							checkerBoard.move(position, a);
							checkerBoard.move(a, targetPosition);
							
							System.out.println("����� ���� ��ҽ��ϴ�!");
						}// end of case for king
						
						//���Ӷٱ�: üĿ������ ������ ���� ���� ����, ������ ���� ���� �� �ִ� ���� �� ���� ���� �� �� �� �������� �� ���� �⵵�� ����ϴ�. �Ʒ��� ���� �ٱ⸦ ���� �ڵ��Դϴ�.
						ableList = checkerBoard.getCapturableList(whosTurn);
						
						ableList = subtractByCondition(ableList);

						if(ableList.indexOf(targetPosition) % 2 == 0) {
							System.out.println("���� �� �ִ� ���� �� �ֽ��ϴ�!");
							whosTurn = !whosTurn;//�̸� ���� �ѹ� �� �ٲپ, �������� ���� �ٲٸ� ���� ���ڸ��� ���ƿͼ� ���� �ѹ� �� ����ǵ��� �߽��ϴ�.
						}
						// -------------------------
						
						// ���� ���� ������ ���� ������ ��, �ڷ� ������ �� �ֵ��� �ϴ� 'King me'��Ģ�� ���� �κ��Դϴ�. �÷��̾�� �����ؾ��ϴ� ��ġ�� �޶� ���� ����� kingMe �Լ��� ȣ���ϵ��� �߽��ϴ�.
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p�� ���
							kingMe(targetPosition);
						// --------------------
						
						// ��� �������� ������, ���� �ٲ� �Ŀ� ������ �����մϴ�. ���� �ٱⰡ �߻��ϴ� ���, ���� �ٽ� �����ϵ��� ���ݴϴ�.
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
				}
				
			} // end loop for moving
			
			turn++;
			
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
	
	private void makeBoard() { // ���带 ó�� ������ ���� �´� ��ġ�� ���� �÷� �α� ���� �Լ��Դϴ�.
		for (int i = 0; i < 12; i++) {
			checkerBoard.onBoard(new Marker(0, 0), i, i);
			checkerBoard.onBoard(new Marker(1, 0), i + 20, i + 12);
		}  //default case	
		
	}
	
	private void printWhoNext() { // � �÷��̾��� �������� �����ִ� �Լ��Դϴ�.
		if(whosTurn)
			System.out.println("1�� �÷��̾��� ���Դϴ�.");
		else
			System.out.println("2�� �÷��̾��� ���Դϴ�.");
	}
	
	private int doesWinnerExist() { // ���ڰ� �ִ����� üũ�ϱ� ���� �Լ��Դϴ�.
		//0: A�� �̱�, 1: B�� �̱�, -1: ���� �ȳ� or ���º�
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
	
	private void savePastBoard() { // ���º� ��Ģ�� ���� �Լ��Դϴ�. ���� ������ ���� �����صӴϴ�.
		if(!(pastCheckerBoard[0] == null) && !(pastCheckerBoard[1] == null) && !(pastCheckerBoard[2] == null))
			pastCheckerBoard[3] = CheckerBoard.copy(pastCheckerBoard[2]);
		if(!(pastCheckerBoard[0] == null) && !(pastCheckerBoard[1] == null))
			pastCheckerBoard[2] = CheckerBoard.copy(pastCheckerBoard[1]);
		if(!(pastCheckerBoard[0] == null))
			pastCheckerBoard[1] = CheckerBoard.copy(pastCheckerBoard[0]);
		pastCheckerBoard[0] = CheckerBoard.copy(checkerBoard);
	}
	
	private boolean isDraw() { // ���º� ��Ģ�� ���� �Լ��Դϴ�. ���� toString�� ���ϴ� ������� �۵��ϵ��� �߽��ϴ�..
		if(!(pastCheckerBoard[3] == null) && pastCheckerBoard[0].toString().equals(pastCheckerBoard[2].toString()) && pastCheckerBoard[1].toString().equals(pastCheckerBoard[3].toString()))
			return true;
		else
			return false;
	}
	
	private void kingMe(int position) { // king me ��Ģ�� ���� �Լ��Դϴ�.
		checkerBoard.exchangeToKing(position);
		System.out.printf("%dĭ, King Me!\n", position);
	}
	
	private List<Integer> subtractByCondition(List<Integer> ableList) { // CheckerBoard�� getCapturableList�� �ٽ��ѹ� ó���ϱ� ���� �Լ��Դϴ�. 
		for(int i = ableList.size() - 1; i >= 0; i--) { //���� �ʰ� ���� ������ �Ųٷ� ����
			int temp = ableList.get(i);
			if(i % 2 == 0) { // ¦��ĭ�� ���������� �ΰ�, Ȧ��ĭ���� ���� ���� ��ġ�� �ε��� �����Ƿ�, ¦��ĭ�� ������ �۵��ϵ��� �߽��ϴ�.
				if(!(checkerBoard.getBlank(temp).isEmpty()) && checkerBoard.getMarker(temp).getType() == 0) { // king�� �ƴѰ��, ���� �ڷ� ������ �⵵�� �ϴ� ��쿡 ���� ���� �����ϴ� �κ��Դϴ�.
					if(whosTurn) { // 1p�� ���
						if(temp > ableList.get(i + 1)) {
							ableList.remove(i + 1); // ArrayList�� ���, �պκ��� ���� ����� ���� ���� index�� �ٲ�� ������, ���� ������ ����� �պκ��� ���쵵�� �߽��ϴ�.
							ableList.remove(i);
						}
					}
					else { // 2p�� ���
						if(temp < ableList.get(i + 1)) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
				}
				if(ableList.size() >= 2) { // ��������� ���� �� �ִ� ���� ��ġ�� ��������, ��� �� �Ŀ� ���� ���� ��ġ�� �ٸ� ���� �ִٸ�, �̸� '���� �� ���� ��Ȳ'���� �Ǵ��Ͽ�, ���� �����ϵ��� �ϴ� �κ��Դϴ�.
					// upward
					if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == 3) {
						if(!checkerBoard.getBlank(temp - 7).isEmpty()) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == 5) {
						if(!checkerBoard.getBlank(temp - 9).isEmpty()) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == 4) {
						if(checkerBoard.isConnectedSilently(temp, temp - 7) && !(checkerBoard.getBlank(temp - 7).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp - 7)) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
						else if(checkerBoard.isConnectedSilently(temp, temp - 9) && !(checkerBoard.getBlank(temp - 9).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp - 9)) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					// downward
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == -3) {
						if(!checkerBoard.getBlank(temp + 7).isEmpty()) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == -5) {
						if(!checkerBoard.getBlank(temp + 9).isEmpty()) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else if(ableList.size() >= i + 1 && temp - ableList.get(i + 1) == -4) {
						if(checkerBoard.isConnectedSilently(temp, temp + 7) && !(checkerBoard.getBlank(temp + 7).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp + 7)) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
						else if(checkerBoard.isConnectedSilently(temp, temp + 9) && !(checkerBoard.getBlank(temp + 9).isEmpty()) && checkerBoard.isConnected(ableList.get(i + 1), temp + 9)) {
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
