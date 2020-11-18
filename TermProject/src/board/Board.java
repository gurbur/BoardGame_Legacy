package board;

public class Board {
	private boolean[][] connection; //Blank들의 연결관계를 나타내기 위한 변수. 변수의 크기가 변하지 않기 떄문에, ArrayList가 아닌 2차원 배열로 선언하였다.
	
	
	public Board(int boardSize) {
		connection = new boolean[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				connection[i][j] = false;
			}
		}
	}//end of Constructor
	
	void makeConnection(int a, int b) { connection[a][b] = true; }
	
	boolean isConnected(int a, int b) { return connection[a][b]; }
	
}
