package checker;

import board.CheckerBoard;
import game.Game;
import marker.Marker;

public class CheckerGame implements Game{
	
	private CheckerBoard checkerBoard;
	private final boolean whoFirst; //true == 1p, false == 2p
	private boolean whoNext; //true == 1p, false == 2p
	
	
	public CheckerGame(boolean whoFirst) {
		this.whoFirst = whoFirst;
	}
	
	@Override
	public void gameStart() {
		checkerBoard = new CheckerBoard();
		
		mainGame();
	}
	
	
	private void mainGame() {
		while(true) {
			showGameBoard();
			
			break;
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly. search on wikipedia, namuwiki or something.
		String output = "체스 판(8*8)에서 진행하는 게임입니다.\n";
		output += "각 말은 대각선 전방으로 밖에 전진할 수 없으며, 만일 상대의 말 바로 다음이 한 칸 비었을 경우에는 그 말을 뛰어넘어서 반드시! 잡아먹어야 합니다.\n";
		output += "만일 잡아먹을 수 있는 말이 연속적으로 있는 경우에는, 한 턴에 연속되어 있는 말을 모두 잡아먹어야만 합니다.\n";
		output += "이런 경우, 한 턴에 제한 없이 여러번 움직일 수 있습니다.\n";
		output += "만일 말이 상대방의 마지막 진영에 도달하면,'킹(King)'상태(A => Ak, B => Bk)가 되어, 대각선 후방으로도 이동할 수 있게 됩니다.\n";
		output += "상대방의 말을 모두 따먹으면 승리합니다. 게임판의 상태가 세 번 동일 하다면 무승부가 됩니다.\n";
		
		return output;
	}
	
	private void showGameBoard() {
		System.out.println(checkerBoard);
	}
}
