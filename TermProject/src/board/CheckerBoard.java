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
		if (isOnBoard(position) == false || isOnBoard(targetPosition) == true) {
			System.out.println("Error in CheckerBoard.move;one of selected blanks is already filled or empty.");
			return;
		}
		if (this.isConnected(position, targetPosition) == false) {
			System.out.println("Error in CheckerBoard.move;selected blanks are not connected.");
			return;
		}
		
		Marker markerTemp_start = blank.get(position).getData();
		Marker markerTemp_target = blank.get(targetPosition).getData();
		Blank<Marker> blankTemp_start = blank.get(position);
		Blank<Marker> blankTemp_target = blank.get(targetPosition);
		
		blankTemp_start.setData(markerTemp_target);
		blankTemp_target.setData(markerTemp_start);
		
		blank.set(position, blankTemp_start);
		blank.set(targetPosition, blankTemp_target);
		
		
		//System.out.println("Movement made Successfully.");
	}
	
	public void exchangeToKing(int position) { // normal상태의 게임말을 king상태의 게임말로 대체하는 메소드.
		int index = blank.get(position).getIndex();
		int player = blank.get(position).getData().getPlayer();
		Marker marker = new Marker(player, 1); // marker type: 0 = normal, 1 = king
		
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
	public List<Integer> getCapturableList(boolean whosTurn) {
		List<Integer> ableList = new ArrayList<Integer>();
		for(int i = 0; i < 32; i++)
			for(int j = 1; j < 32; j++) {
				if(isConnected(i,j) && isOnBoard(i) && isOnBoard(j) && (getMarker(i).getPlayer() != getMarker(j).getPlayer()) && this.getMarker(i).getPlayer() == (whosTurn ? 0 : 1)) {
					if(i - j == 4 && isConnected(j, j - 5)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == 4 && isConnected(j, j - 3)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == 3 && isConnected(j, j - 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == 5 && isConnected(j, j - 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -4 && isConnected(j, j + 3)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -4 && isConnected(j, j + 5)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -5 && isConnected(j, j + 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					else if(i - j == -3 && isConnected(j, j + 4)) {
						ableList.add(i);
						ableList.add(j);
					}
					
				}
			}
		return ableList;
	}
	
	public void captureMarker(int position, int target, int targetPosition) {
		Blank<Marker> temp = blank.get(target);
		temp.popData();
		//move(position, target);
		//move(target, targetPosition);
	}
	
	public List<Integer> whereCapturable(int a, boolean whosTurn/*, int b*/) {
		List<Integer> ableList = new ArrayList<Integer>();
		int player = whosTurn ? 0 : 1;
		
		
		
		if(isConnected(a, a - 4) && connectionSilently[a][a - 9] && isConnected(a - 4, a - 9) && !getBlank(a - 4).isEmpty() && getMarker(a - 4).getPlayer() != player /*&& getBlank(a - 9).isEmpty()*/) {
			ableList.add(a - 9);
		}
		if(isConnected(a, a - 3) && connectionSilently[a][a - 7] && isConnected(a - 3, a - 7) && !getBlank(a - 3).isEmpty() && getMarker(a - 3).getPlayer() != player /*&& getBlank(a - 7).isEmpty()*/) {
			ableList.add(a - 7);
		}
		if(isConnected(a, a + 4) && connectionSilently[a][a + 7] && isConnected(a + 4, a + 7) && !getBlank(a + 4).isEmpty() && getMarker(a + 4).getPlayer() != player /*&& getBlank(a + 7).isEmpty()*/) {
			ableList.add(a + 7);
		}
		if(isConnected(a, a + 5) && connectionSilently[a][a + 9] && isConnected(a + 5, a + 9) && !getBlank(a + 5).isEmpty() && getMarker(a + 5).getPlayer() != player /*&& getBlank(a + 9).isEmpty()*/) {
			ableList.add(a + 9);
		}
		
		if(isConnected(a, a - 5) && connectionSilently[a][a - 9] && isConnected(a - 5, a - 9) && !getBlank(a - 5).isEmpty() && getMarker(a - 5).getPlayer() != player /*&& getBlank(a - 9).isEmpty()*/) {
			ableList.add(a - 9);
		}
		if(isConnected(a, a - 4) && connectionSilently[a][a - 7] && isConnected(a - 4, a - 7) && !getBlank(a - 4).isEmpty() && getMarker(a - 4).getPlayer() != player /*&& getBlank(a - 7).isEmpty()*/) {
			ableList.add(a - 7);
		}
		if(isConnected(a, a + 3) && connectionSilently[a][a + 7] && isConnected(a + 3, a + 7) && !getBlank(a + 3).isEmpty() && getMarker(a + 3).getPlayer() != player /*&& getBlank(a + 7).isEmpty()*/) {
			ableList.add(a + 7);
		}
		if(isConnected(a, a + 4) && connectionSilently[a][a + 9] && isConnected(a + 4, a + 9) && !getBlank(a + 4).isEmpty() && getMarker(a + 4).getPlayer() != player /*&& getBlank(a + 9).isEmpty()*/) {
			ableList.add(a + 9);
		}
		/*
		if(a - b == 4 && isConnected(b, b - 5)) { // upward
			ableList.add(b - 5);
		}
		if(a - b == 4 && isConnected(b, b - 3)) {
			ableList.add(b - 3);
		}
		if(a - b == 3 && isConnected(b, b - 4)) {
			ableList.add(b - 4);
		}
		if(a - b == 5 && isConnected(b, b - 4)) {
			ableList.add(b - 4);
		}
		
		if(a - b == -4 && isConnected(b, b + 3)) { // downward
			ableList.add(b + 3);
		}
		if(a - b == -4 && isConnected(b, b + 5)) {
			ableList.add(b + 5);
		}
		if(a - b == -5 && isConnected(b, b + 4)) {
			ableList.add(b + 4);
		}
		if(a - b == -3 && isConnected(b, b + 4)) {
			ableList.add(b + 4);
		}
		*/
		
		return ableList;
	}
	
	public boolean isEmpty() {
		if(blank.isEmpty()) return true;
		else return false;
	}
	
	public static boolean isEquals(CheckerBoard a, CheckerBoard b) {
		for(int i = 0; i < 32; i++) {
			Blank<Marker> atmp = a.getBlank(i);
			Blank<Marker> btmp = b.getBlank(i);
			if(a.isEmpty() || b.isEmpty() || atmp.isEmpty() || btmp.isEmpty()) {
				return false;
			}
			else if(!a.isEmpty() && !b.isEmpty() && !(atmp.getData().getPlayer() == btmp.getData().getPlayer()) && !(atmp.getData().getType() == btmp.getData().getType())) {
				return false;
			}
		}
		return true;
		
	}
}
