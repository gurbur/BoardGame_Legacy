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
			System.out.println("움직이고 싶은 말의 위치를 고르세요.");
			int markerToMove = in.nextInt();
			
			if(markerToMove == -1)
				break;
			System.out.println("그 말을 어디로 옮길지 위치를 고르세요.");
			int targetPosition = in.nextInt();
			board.move(markerToMove, targetPosition);
			System.out.println("\n" + board.toString());
			
		}
		System.out.println("게임이 종료되었습니다.");
	}
	
	private static BoardExample boardMaker(BoardExample board) {
		for(int i = 0; i < 8; i++) {
			board.onBoard(new Marker(0, 0), i, i);// Object, position, index
			board.onBoard(new Marker(1, 0), i + 24, i + 8);
		}
		return board;
	}
	
	
	
}
