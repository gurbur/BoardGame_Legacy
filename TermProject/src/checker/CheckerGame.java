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
		while(true) { // main loop: 게임이 특별한 상황(승리, 무승부 등)이 발생하지 않는 이상, 계속 진행하도록 만들기 위해서 무한 루프의 형태를 취하도록 했습니다.
			
			
			if(turn % 2 == 0 && turn != 0) { // 무승부 룰을 위한 조건부 함수 호출입니다. 턴이 짝수턴(2번 플레이어의 차례가 끝난 후)일 때에만 판을 복사하도록 했습니다.
				savePastBoard();
			}
			
			if(isDraw()) { // 무승부 룰을 위한 조건부 함수 호출입니다. 무승부가 발생할 경우, 판의 상태를 보여주고 무승부 처리한다는 말을 띄워주고 종료합니다.
				printGameBoard();
				System.out.println("보드의 상태가 3턴 째 같습니다. 무승부 처리합니다.");
				break;
			}
			
			// 승자가 존재하는지 여부를 결정하는 부분입니다.
			int doesWinnerExist = doesWinnerExist();
			if(doesWinnerExist == 0) {
				System.out.println("1P가 승리하였습니다.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P가 승리하였습니다.");
				break;
			}
			// ---------------------------
			
			// 누가 다음 턴인지를 보여주고, 현재 판의 상태를 보여주는 부분입니다.
			printWhoNext();
			printGameBoard();
			// ---------------------------
			
			List<Integer> ableList = checkerBoard.getCapturableList(whosTurn); // 판에서 잡을 수 있는 말이 존재하는지 여부를 getCapturableList를 통해 불러옵니다. 턴이나, 다른 조건들에 따르면, 완벽한 값은 아니기 때문에, 아래에서 다시한번 처리를 해줍니다. 
			
			ableList = subtractByCondition(ableList); // 잡을 수 있는 말이 있는지 여부를 현재 조건(턴, 판의 상황 등)에 맞는지 다시한번 확인합니다.
			
			while(true) { // loop for moving: 입력을 잘못했을 때에 다시한번 플레이어에게 턴이 돌아오도록 해주기 위한 무한루프입니다. 움직임이 끝나면 break를 통해 빠져 나오게 됩니다.
				try {// 플레이어의 입력에 이상이 있을 때(범위 밖의 값, 잘못된 선택, 플레이어의 말이 아닌 말을 선택, 숫자가 아닌 다른 값을 입력하는 경우 등등), 예외처리를 통해 다시 입력하라는 문구를 띄워주기 위해 try-catch를 이용했습니다.
					if(ableList.isEmpty()) {// 잡을 수 있는 말이 없을 때의 경우입니다. 이 경우에는 특별한 예외 처리가 존재하지는 않습니다.
					
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
						
						// 말이 상대방 진영의 끝에 도달할 때, 뒤로 진행할 수 있도록 하는 'King me'규칙을 위한 부분입니다. 플레이어별로 도달해야하는 위치가 달라서 서로 나누어서 kingMe 함수를 호출하도록 했습니다.
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p의 경우
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 2p의 경우
							kingMe(targetPosition);
						// --------------------
						
						whosTurn = !whosTurn; // 모든 움직임이 예외 없이 끝나고, 턴이 상대에게 넘어가는 경우입니다.
						break; // break loop for moving
					}
					
					else { // 잡을 수 있는 말이 있는 경우입니다. 상대의 말을 잡을 수 있을 때에는 꼭 잡아야 한다는 룰에 따라, 만들어지게 되었습니다.
						System.out.println("*상대의 말을 잡을 수 있는 말이 있습니다.");// 잡을 수 있는 말이 있다는걸 보여줍니다. 원래는 어디의 말로 잡을 수 있는지를 보여주려 했지만.. 예외상황이 너무 많아져서 그 기능은 일단 포기했습니다.
						
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
						
						// 아래부터는 말을 움직여, 상대의 말을 잡기 위한 부분입니다.
						int a;
						
						if(checkerBoard.getMarker(position).getPlayer() == 0) {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						else {
							a = ableList.get(ableList.indexOf(position) + 1);
						}
						
						if (checkerBoard.getMarker(position).getType() != 1) { // King이 아닐 때.
							if(checkerBoard.getMarker(position).getPlayer() == 0) { // 1p
								if(position < targetPosition) { // downward(forward) 1p의 경우, 내려가는 방향이 정면방향이기에, 이를 고려하여 초기 위치와 나중위치의 크기로 킹이 아닌 말이 뒤로 가지 못하게 막았습니다.
									
									checkerBoard.captureMarker(position, a, targetPosition);// 1. 상대의 말을 먼저 잡고,
									checkerBoard.move(position, a);//2. 한 칸,
									checkerBoard.move(a, targetPosition);//3. 두 칸 이동.
									
									System.out.println("상대의 말을 잡았습니다!");
								}
								else {// upward(backward)
									throw new InputMismatchException("뒤로 움직일 수 있는 말은 킹 상태의 말 뿐입니다.");
								}
								
							}// end of 1p
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
						}// end of case for not king
						else { // king인 경우, 앞이든 뒤든 원하는대로 움직일 수 있기 때문에 특별한 조건 처리가 필요 없었습니다.
							checkerBoard.captureMarker(position, a, targetPosition);
							checkerBoard.move(position, a);
							checkerBoard.move(a, targetPosition);
							
							System.out.println("상대의 말을 잡았습니다!");
						}// end of case for king
						
						//연속뛰기: 체커에서는 상대방의 말을 잡은 다음, 움직인 말로 잡을 수 있는 말이 또 있을 때에 한 번 더 움직여서 그 말을 잡도록 만듭니다. 아래는 연속 뛰기를 위한 코드입니다.
						ableList = checkerBoard.getCapturableList(whosTurn);
						
						ableList = subtractByCondition(ableList);

						if(ableList.indexOf(targetPosition) % 2 == 0) {
							System.out.println("잡을 수 있는 말이 또 있습니다!");
							whosTurn = !whosTurn;//미리 턴을 한번 더 바꾸어서, 마지막에 턴을 바꾸면 턴이 제자리로 돌아와서 턴이 한번 더 진행되도록 했습니다.
						}
						// -------------------------
						
						// 말이 상대방 진영의 끝에 도달할 때, 뒤로 진행할 수 있도록 하는 'King me'규칙을 위한 부분입니다. 플레이어별로 도달해야하는 위치가 달라서 서로 나누어서 kingMe 함수를 호출하도록 했습니다.
						if(targetPosition >= 28 && targetPosition <= 31 && whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p의 경우
							kingMe(targetPosition);
						else if(targetPosition >= 0 && targetPosition <= 3 && !whosTurn && checkerBoard.getMarker(targetPosition).getType() == 0)// 1p의 경우
							kingMe(targetPosition);
						// --------------------
						
						// 모든 움직임이 끝났고, 턴을 바꾼 후에 루프를 종료합니다. 연속 뛰기가 발생하는 경우, 턴을 다시 진행하도록 해줍니다.
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
				}
				
			} // end loop for moving
			
			turn++;
			
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
	
	private void makeBoard() { // 보드를 처음 세팅할 때에 맞는 위치에 말을 올려 두기 위한 함수입니다.
		for (int i = 0; i < 12; i++) {
			checkerBoard.onBoard(new Marker(0, 0), i, i);
			checkerBoard.onBoard(new Marker(1, 0), i + 20, i + 12);
		}  //default case	
		
	}
	
	private void printWhoNext() { // 어떤 플레이어의 턴인지를 보여주는 함수입니다.
		if(whosTurn)
			System.out.println("1번 플레이어의 턴입니다.");
		else
			System.out.println("2번 플레이어의 턴입니다.");
	}
	
	private int doesWinnerExist() { // 승자가 있는지를 체크하기 위한 함수입니다.
		//0: A가 이김, 1: B가 이김, -1: 승패 안남 or 무승부
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
	
	private void savePastBoard() { // 무승부 규칙을 위한 함수입니다. 게임 이전의 판을 저장해둡니다.
		if(!(pastCheckerBoard[0] == null) && !(pastCheckerBoard[1] == null) && !(pastCheckerBoard[2] == null))
			pastCheckerBoard[3] = CheckerBoard.copy(pastCheckerBoard[2]);
		if(!(pastCheckerBoard[0] == null) && !(pastCheckerBoard[1] == null))
			pastCheckerBoard[2] = CheckerBoard.copy(pastCheckerBoard[1]);
		if(!(pastCheckerBoard[0] == null))
			pastCheckerBoard[1] = CheckerBoard.copy(pastCheckerBoard[0]);
		pastCheckerBoard[0] = CheckerBoard.copy(checkerBoard);
	}
	
	private boolean isDraw() { // 무승부 규칙을 위한 함수입니다. 역시 toString을 비교하는 방법으로 작동하도록 했습니다..
		if(!(pastCheckerBoard[3] == null) && pastCheckerBoard[0].toString().equals(pastCheckerBoard[2].toString()) && pastCheckerBoard[1].toString().equals(pastCheckerBoard[3].toString()))
			return true;
		else
			return false;
	}
	
	private void kingMe(int position) { // king me 규칙을 위한 함수입니다.
		checkerBoard.exchangeToKing(position);
		System.out.printf("%d칸, King Me!\n", position);
	}
	
	private List<Integer> subtractByCondition(List<Integer> ableList) { // CheckerBoard의 getCapturableList를 다시한번 처리하기 위한 함수입니다. 
		for(int i = ableList.size() - 1; i >= 0; i--) { //번위 초과 에러 때문에 거꾸로 진행
			int temp = ableList.get(i);
			if(i % 2 == 0) { // 짝수칸에 시작지점을 두고, 홀수칸에는 잡을 말의 위치를 두도록 했으므로, 짝수칸일 때에만 작동하도록 했습니다.
				if(!(checkerBoard.getBlank(temp).isEmpty()) && checkerBoard.getMarker(temp).getType() == 0) { // king이 아닌경우, 말이 뒤로 움직여 잡도록 하는 경우에 대한 값을 제거하는 부분입니다.
					if(whosTurn) { // 1p의 경우
						if(temp > ableList.get(i + 1)) {
							ableList.remove(i + 1); // ArrayList의 경우, 앞부분의 값을 지우면 뒤의 값의 index가 바뀌기 때문에, 뒤의 값먼저 지우고 앞부분을 지우도록 했습니다.
							ableList.remove(i);
						}
					}
					else { // 2p의 경우
						if(temp < ableList.get(i + 1)) {
							ableList.remove(i + 1);
							ableList.remove(i);
						}
					}
				}
				if(ableList.size() >= 2) { // 출발지점과 잡을 수 있는 말의 위치를 바탕으로, 잡고 난 후에 말이 놓일 위치에 다른 말이 있다면, 이를 '잡을 수 없는 상황'으로 판단하여, 값을 제거하도록 하는 부분입니다.
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
