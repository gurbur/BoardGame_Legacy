package game;

public interface Game {
	//constructor must set default setting from __Tester.
	
	public void gameStart(); //call by __Tester.main(String[] args)
	//call mainGame()
	
	//mainGame() must call by this.gameStart()
}
