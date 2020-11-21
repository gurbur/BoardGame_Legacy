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
		for(int i = 0; i < 3; i++) {
			super.makeConnection(5 + i, 8 + i);
			super.makeConnection(13 + i, 16 + i);
			super.makeConnection(21 + i, 24 + i);
		}
	}
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
	
	public void onBoard(Marker marker, int position, int index) { //Marker ��ü, blank���� ��ġ, ��ü ���� ���� ĭ(or ��) �� �� ĭ�� ���� index ��ȣ
		//������ ��ġ�� Blank�� Marker�� �ø��� ���� �żҵ�. ������ ������ ��, ������ �����Լ����� �⺻������ �ö� �־�� �ϴ� ĭ�� Marker�� �ø��ϴ�. 
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
	public boolean isOnBoard(int position) { //������ ��ġ�� �ش��ϴ� Blank�� Marker�� �ִ��� �������� Ȯ���ϱ� ���� �żҵ�.
		if (blank.get(position).getData() != null) return true;
		else return false;
	}
	
	public void move(int position, int targetPosition) { //Blank�� �ö� �ִ� Marker�� �ٸ� Blank�� �ű�� ���� �żҵ�.
		if (isOnBoard(position) == false || isOnBoard(targetPosition) == true) {
			System.out.println("Error in BoardExample.move;one of selected blanks is already filled or empty.");
			return;
		}
		if (this.isConnected(position, targetPosition) == false) {
			System.out.println("Error in BoardExample.move;selected blanks are not connected.");
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
		
		
		System.out.println("Movement Completed Successfully.");
	}
	
	public void exchangeToKing(int position) { // normal������ ���Ӹ��� king������ ���Ӹ��� ��ü�ϴ� �޼ҵ�.
		int index = blank.get(position).getIndex();
		int player = blank.get(position).getData().getPlayer();
		Marker marker = new Marker(player, 1); // marker type: 0 = normal, 1 = king
		
		blank.set(position, new Blank<Marker>(index, marker));
	}
	
}
