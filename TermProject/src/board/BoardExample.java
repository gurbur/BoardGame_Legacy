
package board;

import java.util.ArrayList;
import java.util.List;

import marker.Marker;

public class BoardExample extends Board {
	private List<Blank<Marker>> blank = new ArrayList<Blank<Marker>>();
	
	public BoardExample() {
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
	}
	
	@Override
	public boolean isConnected(int a, int b) { return super.isConnected(a, b); }
	@Override
	public String toString() { //����ڿ��� ������ ���¸� ������ �� ����ϴ� �żҵ�.
		String output = "";
		for(int i = 0; i < 32; i++) {
			String block;
			if(i < 4 || (i >= 8 && i < 12) || (i >= 16 && i < 20) || (i >= 24 && i < 28)) output += "[] ";
			
			if(blank.get(i).isEmpty() == false) {
				if(((Marker)blank.get(i).getData()).getPlayer() == 0) {
					if(i < 4 || (i >= 8 && i < 12) || (i >= 16 && i < 20) || (i >= 24 && i < 28))
						block = " A ";
					else
						block = "A  ";
				}
				else {
					if(i < 4 || (i >= 8 && i < 12) || (i >= 16 && i < 20) || (i >= 24 && i < 28))
						block = " B ";
					else
						block = "B  ";
				}
			}
			else {
				if (i < 10) block = " " + i + " ";
				else block = "" + i + " ";
			}
			output += block;
			
			if((i >= 4 && i < 8) || (i >= 12 && i < 16) || (i >= 20 && i < 24) || (i >= 28 && i < 32)) output += "[] ";
			if(i == 3 || i == 7 || i == 11 || i == 15 || i == 19 || i == 23 || i == 27 || i == 31) output += "\n\n";
		}
		return output;
	}
	
	public void onBoard(Marker marker, int position, int index) { //Marker ��ü, blank���� ��ġ, ��ü ���Ӹ� �� �� ���Ӹ��� ���� index ��ȣ
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
		
		//ArrayList������ �� ���� ���� �ٲ��ֱ� ���� �۾�.
		
		//1. �� Blank���� 'data<Marker>'��� Blank�� 'markerTemp', 'blankTemp'����ϴ� �ӽ� ������ �־��ش�.
		Marker markerTemp_start = blank.get(position).getData();
		Marker markerTemp_target = blank.get(targetPosition).getData();
		Blank<Marker> blankTemp_start = blank.get(position);
		Blank<Marker> blankTemp_target = blank.get(targetPosition);
		//2. �� Blank �ȿ� data���� ���� ��ȯ�Ͽ� �־��ش�.
		blankTemp_start.setData(markerTemp_target);
		blankTemp_target.setData(markerTemp_start);
		//3. data�� �ٲ� Blank���� BoardExample�� �ν��Ͻ��� 'blank(:ArrayList<Blank>)'�ȿ� �־��ش�.
		blank.set(position, blankTemp_start);
		blank.set(targetPosition, blankTemp_target);
		//4. Blank���� data�� ��ȯ(=Marker�� �̵�)�� ���������� �̷������.
		
		//ArrayList�� ���������� index��ȣ���� ������ ������ �ڷῡ ������ �� ����.
		//����, ArrayList���� �ڷḦ �̾Ƴ� ����, �̾Ƴ� �ڷ��� ���� ���� ������ ���� �ٲ��ְ�, ����� �ڷḦ �ٽ� ArrayList�ȿ� �־��ִ� ���.
		//+)Generic Type�� ���, �迭 ������ �Ұ����ϱ� ������  ArrayList�� ����� �� �ۿ� ����.
		
		System.out.println("Movement Completed Successfully.");
	}
	
}
