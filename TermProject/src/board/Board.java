package board;

public class Board {
	private boolean[][] connection; //Blank���� ������踦 ��Ÿ���� ���� ����. ������ ũ�Ⱑ ������ �ʱ� ������, ArrayList�� �ƴ� 2���� �迭�� �����Ͽ���.
	
	
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
