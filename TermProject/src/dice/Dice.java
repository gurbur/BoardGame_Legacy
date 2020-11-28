package dice;

public class Dice {
	private int showingNum = 1;
	public int roll() {
		int output= (int)(Math.random()*6+1);
		showingNum = output;
		return output;
	}
	
	public int getShowingNum() {
		return showingNum;
	}
}
