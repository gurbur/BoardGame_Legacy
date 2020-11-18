package dice;

public class Dice {
	public int roll() {
		int output= (int)(Math.random()*6+1);
		return output;
	}
}
