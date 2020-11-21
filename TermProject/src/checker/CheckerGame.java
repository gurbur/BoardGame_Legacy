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
				System.out.println("1P가 승리하였습니다.");
				break;
			} else if(doesWinnerExist == 1) {
				System.out.println("2P가 승리하였습니다.");
				break;
			}
			
			printWhoNext();
			System.out.println("====================");
			printGameBoard();
			System.out.println("====================");
			while(true) {
				try {
					System.out.println("어느 위치에 있는 말을 움직일까요?");
					int position = in.nextInt();
					if(position < 0 && position > 32)
						throw new IndexOutOfBoundsException();
					if(checkerBoard.getBlank(position).isEmpty())
						throw new InputMismatchException("그 위치에는 말이 없습니다.");
					if(checkerBoard.getMarker(position).getPlayer() != (whosTurn ? 0 : 1))
						throw new InputMismatchException("플레이어의 말이 아닙니다.");
					
					System.out.println("말을 어느 위치로 옮길까요?");
					int targetPosition = in.nextInt();
					if((targetPosition < 0 && targetPosition > 32))
						throw new IndexOutOfBoundsException();
					if(!checkerBoard.getBlank(targetPosition).isEmpty() || position == targetPosition)
						throw new InputMismatchException("그 위치로는 말이 움직일 수 없습니다.");
					
					break;
				} catch(InputMismatchException ex) {
					System.out.printf("잘못된 입력입니다: %s\n", ex.getMessage());
					in.nextLine();
				} catch(IndexOutOfBoundsException ex) {
					System.out.printf("잘못된 입력입니다: 0~32사이의 값을 입력해주세요.\n");
				}
			}
			
			
			
			
			savePastBoard();
			if(isDraw()) {
				System.out.println("보드의 상태가 3턴 째 같습니다. 무승부 처리합니다.");
				break;
			}
			whosTurn = !whosTurn;
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "체스 판(8*8)에서 진행하는 게임입니다.\n";
		output += "각 말은 대각선 전방으로 밖에 전진할 수 없으며, 만일 상대의 말 바로 다음이 한 칸 비었을 경우에는 그 말을 뛰어넘어서 반드시 잡아먹어야 합니다.\n";
		output += "만일 잡아먹을 수 있는 말이 연속적으로 있는 경우에는, 한 턴에 연속되어 있는 말을 모두 잡아먹어야만 합니다.\n";
		output += "이런 경우, 한 턴에 제한 없이 여러번 움직일 수 있습니다.\n";
		output += "만일 말이 상대방의 마지막 진영에 도달하면,'킹(King)'상태(A => Ak, B => Bk)가 되어, 대각선 후방으로도 이동할 수 있게 됩니다.\n";
		output += "상대방의 말을 모두 따먹으면 승리합니다. 게임판의 상태가 세 번 동일 하다면 무승부가 됩니다.\n";
		
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
