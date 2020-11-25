package board;

import java.util.ArrayList;
import java.util.List;
import marker.Marker;

public class CheckerBoard extends Board{
	
	private List<Blank<Marker>> blank = new ArrayList<Blank<Marker>>();
	/* marker type
	 * 0: normal
	 * 1: king
	 */
	private boolean[][] connectionSilently = new boolean[32][32];
	
	
	public CheckerBoard() {
		super(32);
		
		for(int i = 0; i < 32; i++)
			blank.add(new Blank<Marker>(0, null));
		
		//보드의 blank들에 연결관계를 만들어 줍니다. super class의 makeConnection()을 이용합니다.
		for(int i = 0; i < 7; i++) {
			super.makeConnection(4 * i, 4 * i + 4);
			super.makeConnection(4 * i + 4, 4 * i);
			super.makeConnection(4 * i + 1, 4 * i + 5);
			super.makeConnection(4 * i + 5, 4 * i + 1);
			super.makeConnection(4 * i + 2, 4 * i + 6);
			super.makeConnection(4 * i + 6, 4 * i + 2);
			super.makeConnection(4 * i + 3, 4 * i + 7);
			super.makeConnection(4 * i + 7, 4 * i + 3);
		}
		for(int i = 0; i < 4; i++) {
			super.makeConnection(8 * i, 8 * i + 5);
			super.makeConnection(8 * i + 5, 8 * i);
			super.makeConnection(8 * i + 1, 8 * i + 6);
			super.makeConnection(8 * i + 6, 8 * i + 1);
			super.makeConnection(8 * i + 2, 8 * i + 7);
			super.makeConnection(8 * i + 7, 8 * i + 2);
			
		}
		for(int i = 1; i <= 3; i++) {
			super.makeConnection(8 * i - 3, 8 * i);
			super.makeConnection(8 * i, 8 * i - 3);
			super.makeConnection(8 * i - 2, 8 * i + 1);
			super.makeConnection(8 * i + 1, 8 * i - 2);
			super.makeConnection(8 * i - 1, 8 * i + 2);
			super.makeConnection(8 * i + 2, 8 * i - 1);
		}
		
		//게임말이 상대의 말을 잡는 경우에, 연산을 조금 더 쉽게하기 위해서 한 대각선에 존재하는 두칸 떨어진 blank들 사이에 연결 관게를 만듭니다.
		for(int i = 0; i < 24; i++) {
			if(i % 4 != 0) {
				makeConnectionSilently(i, i + 7);
				makeConnectionSilently(i + 7, i);
			}
			if(i % 4 != 3) {
				makeConnectionSilently(i, i + 9);
				makeConnectionSilently(i + 9, i);
			}
		}
		
		
		
	}
	
	
	public boolean isConnectedSilently(int a, int b) { return connectionSilently[a][b]; }
	@Override
	public boolean isConnected(int a, int b) { return super.isConnected(a, b); }
	@Override
	public String toString() { //사용자에게 보드의 상태를 보여줄 때 사용하는 매소드.
		String output = "";
		for(int i = 0; i < 32; i++) {
			String block;
			if(i % 8 >= 0 && i % 8 <= 3) output += "[] "; //빈 칸이 먼저 출력되야 하는 경우
			
			if(blank.get(i).isEmpty() == false) {
				if(blank.get(i).getData().getPlayer() == 0) {
					if(blank.get(i).getData().getType() == 0)
						block = "A  ";
					else
						block = "Ak ";
				}
				else {
					if(blank.get(i).getData().getType() == 0)
						block = "B  ";
					else
						block = "Bk ";
				}
			}
			else {
				if (i < 10) block = " " + i + " ";
				else block = "" + i + " ";
			}
			output += block;
			
			if(i % 8 >= 4 && i % 8 <= 7) output += "[] ";//빈 칸이 나중에 출력되야 하는 경우
			if(i % 4 == 3) output += "\n";//다음 줄로 넘어가야 하는 경우
		}
		return output;
	}
	
	private void makeConnectionSilently(int a, int b) { connectionSilently[a][b] = true; }
	
	public void onBoard(Marker marker, int position, int index) { //Marker 객체, blank상의 위치, 전체 게임 판의 칸(or 말) 중 이 칸이 갖는 index 번호
		//지정된 위치의 Blank에 Marker를 올리기 위한 매소드. 게임을 시작할 때, 게임의 메인함수에서 기본적으로 올라가 있어야 하는 칸에 Marker를 올립니다. 
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
	public Marker getMarker(int index) {
		return blank.get(index).getData();
	}
	
	public Blank<Marker> getBlank(int index) {
		return blank.get(index);
	}
	
	public boolean isOnBoard(int position) { //지정된 위치에 해당하는 Blank에 Marker가 있는지 없는지를 확인하기 위한 매소드.
		if (blank.get(position).getData() != null) return true;
		else return false;
	}
	
	public void move(int position, int targetPosition) { //Blank에 올라가 있는 Marker를 다른 Blank로 옮기기 위한 매소드.
		//--디버그를 위해서 임의로 추가한 코드입니다.--
		if (isOnBoard(position) == false || isOnBoard(targetPosition) == true) {
			System.out.println("Error in CheckerBoard.move;one of selected blanks is already filled or empty.");
			return;
		}
		if (this.isConnected(position, targetPosition) == false) {
			System.out.println("Error in CheckerBoard.move;selected blanks are not connected.");
			return;
		}
		//-----
		
		Marker markerTemp_start = blank.get(position).getData();
		Marker markerTemp_target = blank.get(targetPosition).getData();
		Blank<Marker> blankTemp_start = blank.get(position);
		Blank<Marker> blankTemp_target = blank.get(targetPosition);
		
		blankTemp_start.setData(markerTemp_target);
		blankTemp_target.setData(markerTemp_start);
		
		blank.set(position, blankTemp_start);
		blank.set(targetPosition, blankTemp_target);
	}
	
	public void exchangeToKing(int position) { // normal상태의 게임말을 king상태의 게임말로 대체하는 메소드.
		int index = blank.get(position).getIndex();
		int player = blank.get(position).getData().getPlayer();
		Marker marker = new Marker(player, 1); // marker type: 0 = normal, 1 = king
		
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
	public List<Integer> getCapturableList(boolean whosTurn) { // 잡을 수 있는 말이 있는지를 찾기 위해 정의한 함수입니다. 객체의 blank의 값을 참조하여, 한 대각선 상에서 한칸 바로 옆에 있는 모든 경우의 수를 찾는 원리입니다.
		// 하지만 추가적인 처리가 필요하여, 필요한 경우, CheckerGame class에서 조건에 맞지 않는 경우를 제외하도록 했습니다.
		List<Integer> ableList = new ArrayList<Integer>();
		for(int i = 0; i < 32; i++)
			for(int j = 1; j < 32; j++) {
				if(isConnected(i,j) && isOnBoard(i) && isOnBoard(j) && (getMarker(i).getPlayer() != getMarker(j).getPlayer()) && this.getMarker(i).getPlayer() == (whosTurn ? 0 : 1)) {
					if(i - j == 4 && j > 5 && isConnected(j, j - 5)) {
						ableList.add(i); // 짝수칸(0부터)에는 출발지점,
						ableList.add(j); // 홀수칸에는 출발지점에서 잡을 수 있는 말의 위치를 담도록 표현했습니다.
					}
					else if(i - j == 4 && j > 3 && isConnected(j, j - 3)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == 3 && j > 4 && isConnected(j, j - 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == 5 && j > 4 && isConnected(j, j - 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -4 && j < 28 && isConnected(j, j + 3)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -4 && j < 26 && isConnected(j, j + 5)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -5 && j < 27 && isConnected(j, j + 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -3 && j < 27 && isConnected(j, j + 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					
				}
			}
		return ableList;
	}
	
	public void captureMarker(int position, int target, int targetPosition) { // 말을 잡을 때에 사용하는 함수입니다. 원래는 잡고 난 이후에 이동까지 하도록 정의했지만, 디버그 과정이 너무 복잡하여 이동은 CheckerGame에서 하도록 했습니다.
		Blank<Marker> temp = blank.get(target);
		temp.popData();
	}
	
	public List<Integer> whereCapturable(int a, boolean whosTurn) { // getCapturable이 모든 말에서 잡을 수 있는 경우의 수를 찾는다면, 이 함수는 주어진 한 위치에서 잡을 수 있는 말이 있는지를 체크합니다.
		List<Integer> ableList = new ArrayList<Integer>();
		int player = whosTurn ? 0 : 1; // 플레이어 별로 판에서 위치에 대한 연산이 다르게 적용되어서, 삼항연산자로 int로 값을 받아오도록 했습니다.
		
		
		
		if(isConnected(a, a - 4) && connectionSilently[a][a - 9] && isConnected(a - 4, a - 9) && !getBlank(a - 4).isEmpty() && getMarker(a - 4).getPlayer() != player) {
			ableList.add(a - 9);
		}
		if(isConnected(a, a - 3) && connectionSilently[a][a - 7] && isConnected(a - 3, a - 7) && !getBlank(a - 3).isEmpty() && getMarker(a - 3).getPlayer() != player) {
			ableList.add(a - 7);
		}
		if(isConnected(a, a + 4) && connectionSilently[a][a + 7] && isConnected(a + 4, a + 7) && !getBlank(a + 4).isEmpty() && getMarker(a + 4).getPlayer() != player) {
			ableList.add(a + 7);
		}
		if(isConnected(a, a + 5) && connectionSilently[a][a + 9] && isConnected(a + 5, a + 9) && !getBlank(a + 5).isEmpty() && getMarker(a + 5).getPlayer() != player) {
			ableList.add(a + 9);
		}
		
		if(isConnected(a, a - 5) && connectionSilently[a][a - 9] && isConnected(a - 5, a - 9) && !getBlank(a - 5).isEmpty() && getMarker(a - 5).getPlayer() != player) {
			ableList.add(a - 9);
		}
		if(isConnected(a, a - 4) && connectionSilently[a][a - 7] && isConnected(a - 4, a - 7) && !getBlank(a - 4).isEmpty() && getMarker(a - 4).getPlayer() != player) {
			ableList.add(a - 7);
		}
		if(isConnected(a, a + 3) && connectionSilently[a][a + 7] && isConnected(a + 3, a + 7) && !getBlank(a + 3).isEmpty() && getMarker(a + 3).getPlayer() != player) {
			ableList.add(a + 7);
		}
		if(isConnected(a, a + 4) && connectionSilently[a][a + 9] && isConnected(a + 4, a + 9) && !getBlank(a + 4).isEmpty() && getMarker(a + 4).getPlayer() != player) {
			ableList.add(a + 9);
		}
		
		return ableList;
	}
	
	public boolean isEmpty() { // 무승부 룰을 만들기 위해 만든 함수입니다. 이 게임 판의 위가 비어있는지를 체크하는 함수입니다만.. 어떻게 해도 원본 함수의 직접적인 값이 아니라 주소값만을 복사해 오기에, 그냥 비어있는 판의 형태를 객체의 toString과 비교하도록 했습니다. 같을 때에는 true, 다를 때에는 false를 출력합니다.
		if("[] Ak []  1 []  2 []  3 \r\n 4 []  5 []  6 []  7 [] \r\n[]  8 []  9 [] 10 [] 11 \r\n12 [] 13 [] 14 [] 15 [] \r\n[] 16 [] 17 [] 18 [] 19 \r\n20 [] 21 [] 22 [] 23 [] \r\n[] 24 [] 25 [] 26 [] 27 \r\n28 [] 29 [] 30 [] Bk [] \r\n".equals(this.toString())) {
			return true;
		}
		return false;
	}
	
	public static boolean isEquals(CheckerBoard a, CheckerBoard b) { // 무승부 룰을 만들기 위해 만든 함수입니다. 주어진 두 판이 같은지를 체크하는 함수입니다. 위의 isEmpty함수와 같이, 직접적인 값을 비교하는게 아니라 주소값만을 비교해서.. 이 함수도 toString을 비교하도록 했습니다.
		// 판과 독립적으로 작동한다는 느낌이 있어서, static으로 정의해주었습니다.
		if(a.toString().equals(b.toString()))
			return true;
		else
			return false;
	}
	
	public static CheckerBoard copy(CheckerBoard board) { // 무승부 룰을 만들기 위해 만든 함수입니다. 주어진 판을 복사하여 새로운 판을 만듭니다. 이 함수 만큼은 주소 값이 아니라 실제 값만을 복사하게 만들기 위해서, blank클래스가 추가로 clone함수를 override하도록 만들어주었고, 이 함수를 이용하여 만들어 졌습니다.
		CheckerBoard output = new CheckerBoard();
		
		for(int i = 0; i < 32; i++)
			output.blank.add(new Blank<Marker>(0, null));
		
		for(int i = 0; i < board.blank.size(); i++) {
			output.blank.set(i, board.blank.get(i).clone());
		}
		
		return output;
	}
	
	
	
	
}
