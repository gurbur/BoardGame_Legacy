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
		
		//������ blank�鿡 ������踦 ����� �ݴϴ�. super class�� makeConnection()�� �̿��մϴ�.
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
		
		//���Ӹ��� ����� ���� ��� ��쿡, ������ ���� �� �����ϱ� ���ؼ� �� �밢���� �����ϴ� ��ĭ ������ blank�� ���̿� ���� ���Ը� ����ϴ�.
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
	public String toString() { //����ڿ��� ������ ���¸� ������ �� ����ϴ� �żҵ�.
		String output = "";
		for(int i = 0; i < 32; i++) {
			String block;
			if(i % 8 >= 0 && i % 8 <= 3) output += "[] "; //�� ĭ�� ���� ��µǾ� �ϴ� ���
			
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
			
			if(i % 8 >= 4 && i % 8 <= 7) output += "[] ";//�� ĭ�� ���߿� ��µǾ� �ϴ� ���
			if(i % 4 == 3) output += "\n";//���� �ٷ� �Ѿ�� �ϴ� ���
		}
		return output;
	}
	
	private void makeConnectionSilently(int a, int b) { connectionSilently[a][b] = true; }
	
	public void onBoard(Marker marker, int position, int index) { //Marker ��ü, blank���� ��ġ, ��ü ���� ���� ĭ(or ��) �� �� ĭ�� ���� index ��ȣ
		//������ ��ġ�� Blank�� Marker�� �ø��� ���� �żҵ�. ������ ������ ��, ������ �����Լ����� �⺻������ �ö� �־�� �ϴ� ĭ�� Marker�� �ø��ϴ�. 
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
	public Marker getMarker(int index) {
		return blank.get(index).getData();
	}
	
	public Blank<Marker> getBlank(int index) {
		return blank.get(index);
	}
	
	public boolean isOnBoard(int position) { //������ ��ġ�� �ش��ϴ� Blank�� Marker�� �ִ��� �������� Ȯ���ϱ� ���� �żҵ�.
		if (blank.get(position).getData() != null) return true;
		else return false;
	}
	
	public void move(int position, int targetPosition) { //Blank�� �ö� �ִ� Marker�� �ٸ� Blank�� �ű�� ���� �żҵ�.
		//--����׸� ���ؼ� ���Ƿ� �߰��� �ڵ��Դϴ�.--
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
	
	public void exchangeToKing(int position) { // normal������ ���Ӹ��� king������ ���Ӹ��� ��ü�ϴ� �޼ҵ�.
		int index = blank.get(position).getIndex();
		int player = blank.get(position).getData().getPlayer();
		Marker marker = new Marker(player, 1); // marker type: 0 = normal, 1 = king
		
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
	public List<Integer> getCapturableList(boolean whosTurn) { // ���� �� �ִ� ���� �ִ����� ã�� ���� ������ �Լ��Դϴ�. ��ü�� blank�� ���� �����Ͽ�, �� �밢�� �󿡼� ��ĭ �ٷ� ���� �ִ� ��� ����� ���� ã�� �����Դϴ�.
		// ������ �߰����� ó���� �ʿ��Ͽ�, �ʿ��� ���, CheckerGame class���� ���ǿ� ���� �ʴ� ��츦 �����ϵ��� �߽��ϴ�.
		List<Integer> ableList = new ArrayList<Integer>();
		for(int i = 0; i < 32; i++)
			for(int j = 1; j < 32; j++) {
				if(isConnected(i,j) && isOnBoard(i) && isOnBoard(j) && (getMarker(i).getPlayer() != getMarker(j).getPlayer()) && this.getMarker(i).getPlayer() == (whosTurn ? 0 : 1)) {
					if(i - j == 4 && j > 5 && isConnected(j, j - 5)) {
						ableList.add(i); // ¦��ĭ(0����)���� �������,
						ableList.add(j); // Ȧ��ĭ���� ����������� ���� �� �ִ� ���� ��ġ�� �㵵�� ǥ���߽��ϴ�.
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
	
	public void captureMarker(int position, int target, int targetPosition) { // ���� ���� ���� ����ϴ� �Լ��Դϴ�. ������ ��� �� ���Ŀ� �̵����� �ϵ��� ����������, ����� ������ �ʹ� �����Ͽ� �̵��� CheckerGame���� �ϵ��� �߽��ϴ�.
		Blank<Marker> temp = blank.get(target);
		temp.popData();
	}
	
	public List<Integer> whereCapturable(int a, boolean whosTurn) { // getCapturable�� ��� ������ ���� �� �ִ� ����� ���� ã�´ٸ�, �� �Լ��� �־��� �� ��ġ���� ���� �� �ִ� ���� �ִ����� üũ�մϴ�.
		List<Integer> ableList = new ArrayList<Integer>();
		int player = whosTurn ? 0 : 1; // �÷��̾� ���� �ǿ��� ��ġ�� ���� ������ �ٸ��� ����Ǿ, ���׿����ڷ� int�� ���� �޾ƿ����� �߽��ϴ�.
		
		
		
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
	
	public boolean isEmpty() { // ���º� ���� ����� ���� ���� �Լ��Դϴ�. �� ���� ���� ���� ����ִ����� üũ�ϴ� �Լ��Դϴٸ�.. ��� �ص� ���� �Լ��� �������� ���� �ƴ϶� �ּҰ����� ������ ���⿡, �׳� ����ִ� ���� ���¸� ��ü�� toString�� ���ϵ��� �߽��ϴ�. ���� ������ true, �ٸ� ������ false�� ����մϴ�.
		if("[] Ak []  1 []  2 []  3 \r\n 4 []  5 []  6 []  7 [] \r\n[]  8 []  9 [] 10 [] 11 \r\n12 [] 13 [] 14 [] 15 [] \r\n[] 16 [] 17 [] 18 [] 19 \r\n20 [] 21 [] 22 [] 23 [] \r\n[] 24 [] 25 [] 26 [] 27 \r\n28 [] 29 [] 30 [] Bk [] \r\n".equals(this.toString())) {
			return true;
		}
		return false;
	}
	
	public static boolean isEquals(CheckerBoard a, CheckerBoard b) { // ���º� ���� ����� ���� ���� �Լ��Դϴ�. �־��� �� ���� �������� üũ�ϴ� �Լ��Դϴ�. ���� isEmpty�Լ��� ����, �������� ���� ���ϴ°� �ƴ϶� �ּҰ����� ���ؼ�.. �� �Լ��� toString�� ���ϵ��� �߽��ϴ�.
		// �ǰ� ���������� �۵��Ѵٴ� ������ �־, static���� �������־����ϴ�.
		if(a.toString().equals(b.toString()))
			return true;
		else
			return false;
	}
	
	public static CheckerBoard copy(CheckerBoard board) { // ���º� ���� ����� ���� ���� �Լ��Դϴ�. �־��� ���� �����Ͽ� ���ο� ���� ����ϴ�. �� �Լ� ��ŭ�� �ּ� ���� �ƴ϶� ���� ������ �����ϰ� ����� ���ؼ�, blankŬ������ �߰��� clone�Լ��� override�ϵ��� ������־���, �� �Լ��� �̿��Ͽ� ����� �����ϴ�.
		CheckerBoard output = new CheckerBoard();
		
		for(int i = 0; i < 32; i++)
			output.blank.add(new Blank<Marker>(0, null));
		
		for(int i = 0; i < board.blank.size(); i++) {
			output.blank.set(i, board.blank.get(i).clone());
		}
		
		return output;
	}
	
	
	
	
}
