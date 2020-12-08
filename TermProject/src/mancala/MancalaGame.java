package mancala;

import java.util.ArrayList;
import java.util.List;

import board.Blank;
import game.Game;

public class MancalaGame implements Game{
	private List<Blank<Integer>> blanks = new ArrayList<Blank<Integer>>();
	private boolean whosTurn;
	
	
	
	public MancalaGame() {
		for(int i = 0; i < 14; i++) {
			blanks.add(new Blank<Integer>(i, 0)); // index, seed 수
		}
		for(int i = 1; i < 13; i++) {
			Blank<Integer> tempBlank = new Blank<Integer>(i, 3); // index, seed 수 / 초기에는 바깥쪽에 모두 seed가 3개씩 놓여있음
			
			blanks.set(i, tempBlank);
		}
		
		
		System.out.println(this.toString());
	}
	
	@Override
	public void gameStart() {
		whosTurn = true;
		
		mainGame();
	}
	
	@Override
	public String toString() {
		String output = "";
		output += "┌───┐┌───┐┌───┐┌───┐┌───┐┌───┐┌───┐┌───┐\n";
		
		output += "│   ││#01││#02││#03││#04││#05││#06││   │\n";
		
		output += String.format("│ 1P││%3d││%3d││%3d││%3d││%3d││%3d││ 2P│\n"
				,blanks.get(1).getData(),blanks.get(2).getData(),blanks.get(3).getData()
				,blanks.get(4).getData(),blanks.get(5).getData(),blanks.get(6).getData());//print outer area's num
		
		output += "│   │└───┘└───┘└───┘└───┘└───┘└───┘│   │\n";
		
		output += String.format("│%3d│┌───┐┌───┐┌───┐┌───┐┌───┐┌───┐│%3d│\n"
				,blanks.get(0).getData(),blanks.get(13).getData());//print inner area's num
		
		output += "│   ││#07││#08││#09││#10││#11││#12││   │\n";
		
		output += String.format("│   ││%3d││%3d││%3d││%3d││%3d││%3d││   │\n"
				,blanks.get(7).getData(),blanks.get(8).getData(),blanks.get(9).getData()
				,blanks.get(10).getData(),blanks.get(11).getData(),blanks.get(12).getData());//print outer area's num
		
		output += "└───┘└───┘└───┘└───┘└───┘└───┘└───┘└───┘\n";
		
		
		return output;
	}
	
	private void mainGame() {
		
		
		
		
	}
	
	
	
	
}
