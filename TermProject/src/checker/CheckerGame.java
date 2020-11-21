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
		//initialization
		checkerBoard = new CheckerBoard();
		makeBoard();
		
		//main game start
		mainGame();
	}
	
	
	private void mainGame() {
		while(true) {
			showGameBoard();
			
			
			
			
			
			
			break;
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "ü�� ��(8*8)���� �����ϴ� �����Դϴ�.\n";
		output += "�� ���� �밢�� �������� �ۿ� ������ �� ������, ���� ����� �� �ٷ� ������ �� ĭ ����� ��쿡�� �� ���� �پ�Ѿ �ݵ��! ��ƸԾ�� �մϴ�.\n";
		output += "���� ��Ƹ��� �� �ִ� ���� ���������� �ִ� ��쿡��, �� �Ͽ� ���ӵǾ� �ִ� ���� ��� ��ƸԾ�߸� �մϴ�.\n";
		output += "�̷� ���, �� �Ͽ� ���� ���� ������ ������ �� �ֽ��ϴ�.\n";
		output += "���� ���� ������ ������ ������ �����ϸ�,'ŷ(King)'����(A => Ak, B => Bk)�� �Ǿ�, �밢�� �Ĺ����ε� �̵��� �� �ְ� �˴ϴ�.\n";
		output += "������ ���� ��� �������� �¸��մϴ�. �������� ���°� �� �� ���� �ϴٸ� ���ºΰ� �˴ϴ�.\n";
		
		return output;
	}
	
	private void showGameBoard() {
		System.out.println(checkerBoard);
	}
	
	private void makeBoard() {
		for (int i = 0; i < 8; i++) {
			checkerBoard.onBoard(new Marker(0, 0), i, i);
			checkerBoard.onBoard(new Marker(1, 0), i + 24, i + 8);
		}	
		
	}
}
