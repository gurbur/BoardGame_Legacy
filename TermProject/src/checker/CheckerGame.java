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
}
