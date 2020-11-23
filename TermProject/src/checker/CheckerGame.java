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
				System.out.println("1P가 승리하였습니다.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P가 승리하였습니다.");
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
					
						System.out.println("어느 위치에 있는 말을 움직일까요?");
						int position = in.nextInt();
						if(position < 0 || position > 32)
							throw new IndexOutOfBoundsException();
						if(checkerBoard.getBlank(position).isEmpty())
							throw new InputMismatchException("그 위치에는 말이 없습니다.");
						if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
							throw new InputMismatchException("플레이어의 말이 아닙니다.");
						
						System.out.println("말을 어느 위치로 옮길까요?");
						int targetPosition = in.nextInt();
						if((targetPosition < 0 && targetPosition > 32))
							throw new IndexOutOfBoundsException();
						if(!checkerBoard.getBlank(targetPosition).isEmpty() || position == targetPosition || !checkerBoard.isConnected(position, targetPosition))
							throw new InputMismatchException("그 위치로는 말이 움직일 수 없습니다.");
						if(whosTurn == true && position > targetPosition && checkerBoard.getMarker(position).getType() != 1) // 1p의 경우
							throw new InputMismatchException("뒤로 움직일 수 있는 말은 킹 상태의 말 뿐입니다.");
						else if(whosTurn == false && position < targetPosition && checkerBoard.getMarker(position).getType() != 1) // 2p의 경우
							throw new InputMismatchException("뒤로 움직일 수 있는 말은 킹 상태의 말 뿐입니다.");
						
						
						
						checkerBoard.move(position, targetPosition);
						System.out.println("말을 이동하였습니다.");
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p의 경우
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 2p의 경우
							kingMe(targetPosition);
						whosTurn = !whosTurn;
						break; // break loop for moving
					
					}
					else {
						System.out.println("*상대의 말을 잡을 수 있는 말이 있습니다.");
						
						System.out.println("어느 위치에 있는 말을 움직일까요?");
						int position = in.nextInt();
						//in.nextLine();
						if(position < 0 || position > 32)
							throw new IndexOutOfBoundsException();
						if(checkerBoard.getBlank(position).isEmpty())
							throw new InputMismatchException("그 위치에는 말이 없습니다.");
						if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
							throw new InputMismatchException("플레이어의 말이 아닙니다.");
						if(ableList.indexOf(position) % 2 != 0)
							throw new InputMismatchException("상대의 말을 잡을 수 있는 말이 있을 때에는 잡아야만 합니다.");
						
						
						System.out.println("말을 어느 위치로 옮길까요?");
						
						int targetPosition = in.nextInt();
						if((targetPosition < 0 && targetPosition > 32))
							throw new IndexOutOfBoundsException();
						if(!(checkerBoard.getBlank(targetPosition).isEmpty()) || position == targetPosition)
							throw new InputMismatchException("그 위치로는 말이 움직일 수 없습니다.");
						
						int a;
						
						if(checkerBoard.getMarker(position).getPlayer() == 0) {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						else {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						
						if (checkerBoard.getMarker(position).getType() != 1) { // King이 아닐 때.
							if(checkerBoard.getMarker(position).getPlayer() == 0) { // 1p
								if(position < targetPosition) { // downward(forward)
									
									checkerBoard.captureMarker(position, a, targetPosition);//먼저 잡고,
									checkerBoard.move(position, a);//한 칸,
									checkerBoard.move(a, targetPosition);//두 칸 이동.
									
									System.out.println("상대의 말을 잡았습니다!");
								}
								else {// upward(backward)
									throw new InputMismatchException("뒤로 움직일 수 있는 말은 킹 상태의 말 뿐입니다.");
								}
								
							}
							else { // 2p
								if(position < targetPosition) { // downward(backward)
									throw new InputMismatchException("뒤로 움직일 수 있는 말은 킹 상태의 말 뿐입니다.");
								}
								else { // upward(forward)
									checkerBoard.captureMarker(position, a, targetPosition);
									checkerBoard.move(position, a);
									checkerBoard.move(a, targetPosition);
									
									System.out.println("상대의 말을 잡았습니다!");
								}
							}
						}
						else {
							checkerBoard.captureMarker(position, a, targetPosition);
							checkerBoard.move(position, a);
							checkerBoard.move(a, targetPosition);
							
							System.out.println("상대의 말을 잡았습니다!");
						}
						//연속뛰기
						
						ableList = checkerBoard.getCapturableList(whosTurn);
						
						ableList = subtractByCondition(ableList);

						if(ableList.indexOf(targetPosition) % 2 == 0) {
							System.out.println("잡을 수 있는 말이 또 있습니다!");
							whosTurn = !whosTurn;	
						}
						
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p의 경우
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p의 경우
							kingMe(targetPosition);
						whosTurn = !whosTurn;
						break; // break loop for moving
					}
				} catch(InputMismatchException ex) {
					if(ex.getMessage() == null)
						System.out.println("잘못된 입력입니다: 0~32사이의 값을 넣어주세요.\n");
					else
						System.out.printf("잘못된 입력입니다: %s\n", ex.getMessage());
					in.nextLine();
				} catch(IndexOutOfBoundsException ex) {
					System.out.printf("잘못된 입력입니다: 0~32사이의 값을 입력해주세요.\n");
					ex.printStackTrace();
				}/* catch(Exception ex) {
					System.out.printf("%s\n", ex.getMessage());
				}*/
				
			} // end loop for moving
			
			
			
			if(whosTurn != whoFirst)
				savePastBoard();
			if(isDraw()) {
				printGameBoard();
				System.out.println("보드의 상태가 3턴 째 같습니다. 무승부 처리합니다.");
				break;
			}
			
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "체스 판(8*8)에서 진행하는 게임입니다.\n";
		output += "각 말은 대각선 전방으로 밖에 전진할 수 없으며, 만일 상대의 말 바로 다음이 한 칸 비었을 경우에는 그 말을 뛰어넘어서 반드시 잡아야 합니다.\n";
		output += "만일 잡을 수 있는 말이 연속적으로 있는 경우에는, 한 턴에 연속되어 있는 말을 모두 잡아야만 합니다.\n";
		output += "이런 경우, 한 턴에 제한 없이 여러번 움직일 수 있습니다.\n";
		output += "만일 말이 상대방의 마지막 진영에 도달하면,'킹(King)'상태(A => Ak, B => Bk)가 되어, 대각선 후방으로도 이동할 수 있게 됩니다.\n";
		output += "상대방의 말을 모두 잡으면 승리합니다. 게임판의 상태가 세 번 동일 하다면 무승부가 됩니다.\n";
		
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
			System.out.println("1번 플레이어의 턴입니다.");
		else
			System.out.println("2번 플레이어의 턴입니다.");
	}
	
	private int doesWinnerExist() { //0: A가 이김, 1: B가 이김, -1: 승패 안남
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
		System.out.printf("%d칸, King Me!\n", position);
	}
	
	private List<Integer> subtractByCondition(List<Integer> ableList) {
		for(int i = ableList.size() - 1; i >= 0; i--) { //번위 초과 에러 때문에 거꾸로 진행
			int temp = ableList.get(i);
			if(i % 2 == 0) {
				if(!(checkerBoard.getBlank(temp).isEmpty()) && checkerBoard.getMarker(temp).getType() == 0) { // king인 경우를 제외
					if(whosTurn) { // 1p의 경우
						if(temp > ableList.get(i + 1)) {
							System.out.println("7");
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
					else { // 2p의 경우
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
