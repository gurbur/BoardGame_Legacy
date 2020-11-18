package board;

public class Blank <T> {
	private final int index;
	private T data;
	
	public Blank(int index, T data) {
		this.index = index;
		this.data = data;
	}
	
	public T getData() { return data; }
	int getIndex() { return index; }
	
	public T popData() {
		T tmp = data;
		data = null;
		return tmp;
	}
	
	public void setData(T data) { this.data = data; }
	
	public boolean isEmpty() {
		if(data == null) return true;
		else return false;
	}
}
