package board;


import java.util.Scanner;
import marker.Marker;


public class BoardTester {

	public static void main(String[] args) {
		BoardExample board = new BoardExample();
		
		board = boardMaker(board);
		Scanner in = new Scanner(System.in);
		
		System.out.println(board.toString());
		while(true) {
			
			System.out.println("----------------");
			System.out.println("�����̰� ���� ���� ��ġ�� ������.");
			int markerToMove = in.nextInt();
			
			if(markerToMove == -1)
				break;
			System.out.println("�� ���� ���� �ű��� ��ġ�� ������.");
			int targetPosition = in.nextInt();
			board.move(markerToMove, targetPosition);
			System.out.println("\n" + board.toString());
			
		}
		System.out.println("������ ����Ǿ����ϴ�.");
	}
	
	private static BoardExample boardMaker(BoardExample board) {
		for(int i = 0; i < 8; i++) {
			board.onBoard(new Marker(0, 0), i, i);// Object, position, index
			board.onBoard(new Marker(1, 0), i + 24, i + 8);
		}
		return board;
	}
	
	
	
}
