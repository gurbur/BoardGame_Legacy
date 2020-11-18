package dice;

public class Dice {
	public static int roll() {
		int output= (int)(Math.random()*6+1);
		return output;
	}
}
