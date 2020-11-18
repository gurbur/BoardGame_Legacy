package marker;

public class Marker {
	private final int player;
	private final int type;
	
	public Marker(int player, int type) {
		this.player = player;
		this.type = type;
	}
	
	public int getPlayer() { return player; }
	public int getType() { return type; }
	
}
