package yachtdice;

import java.util.InputMismatchException;
import java.util.Scanner;

import board.YachtDiceBoard;
import game.Game;

public class YachtDiceGame implements Game{
	private YachtDiceBoard board = new YachtDiceBoard();
	private boolean whosTurn = true;
	private int turn;
	private Scanner in;
	private int phase = 0;
	private boolean flagP1Bonus;
	private boolean flagP2Bonus;
	
	
	public YachtDiceGame() {
		
	}
	@Override
	public void gameStart() {
		flagP1Bonus = false;
		flagP2Bonus = false;
		turn = 0;
		in = new Scanner(System.in);
		
		mainGame();
	}
	
	private void mainGame() {
		while(true) {
			if(whosTurn == true) {
				turn++;
			}
			doesExistBonus();
			
			phase = 2;
			board.refresh();
			printWhosTurn();
			printGameState();
			
			if(turn == 12 && whosTurn == false) { // 승패 결정
				findWinner();
				break;
			}
			
			while(true) {
				System.out.println("엔터키를 입력하여 주사위를 굴리세요!");
				String input = in.nextLine();
				if(input.equals("")) {
					break;
				}
			}
			System.out.println("주사위를 굴렸습니다");
			
			for (int i = 0; i < 5; i++) {
				board.rollDice(i);
			}
			
			while(true) { // loop for choosing target
				if(phase > 0 && !board.isNoDiceToRoll()) {
					board.sortChoicedDice();
					System.out.println(board.showDices());
					
					System.out.println("고를 주사위가 있으면 번호를 입력하고, 나와있는 주사위로 점수표를 고르려면 점수표의 이름을 입력하세요.\n");
					System.out.println("이미 고른 주사위를 제외하려면 !(주사위 위치)를 입력하세요.");
					System.out.println("주사위를 다시 굴리려면 다른 입력 없이 엔터키를 입력하세요(주사위를 "+phase+"번 더 던질 수 있습니다).");
					
					
					try {
						String input = in.nextLine();
						
						if(!input.equals("") && !input.substring(0, 1).equals("!")) {
							
							
									
							int inputToInt = 0;
							
							boolean flag_inputIsNumber = true;
							
							if(!isInteger(input)) {
								flag_inputIsNumber = false;
							}
							if(flag_inputIsNumber) {
								inputToInt = input.charAt(0) - '0';
							}
							
							
							int integeredInput = 0;
							
							if(!flag_inputIsNumber) { // 문자가 입력되었을 때
								integeredInput = board.nameToBoardIndex(input);
								if(integeredInput == -1) {
									throw new InputMismatchException();
								}
								try {
									switch(integeredInput) {
									case 0: //aces
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 1: //deuces
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 2: //threes
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 3: //fours
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 4: //fives
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 5: //sixes
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 6: //choices
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 7: //4 of a kind
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 8: //full house
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 9: //s. straight
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 10: //l. straight
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									case 11: //yacht
										board.choiceScore(whosTurn, integeredInput);
										phase = 0;
										break;
									default: //error
										System.out.println("주사위를 굴리거나, 주사위를 고르거나, 점수표에 있는 이름을 입력해 주세요.");
										break;
									}
									break;
								} catch (Exception ex) {
									System.err.printf("%s", ex.getMessage());
								}
							} else { // 숫자가 입력되었을 때 
								if(inputToInt <= 0 && inputToInt > 5)
									throw new InputMismatchException();
								try {
									board.choiceDice(inputToInt - 1);
									System.out.println("주사위를 선택했습니다.");
								} catch (IndexOutOfBoundsException ex) {
									System.err.println("그 위치에는 주사위가 없습니다.");
								}
							}
							
							
							
						}
						else if(!input.equals("") && input.substring(0, 1).equals("!")) {
								int tempTarget = input.substring(1).charAt(0) - '0';
								if(tempTarget > 5 || tempTarget < 0) {
									throw new InputMismatchException();
								}
								
								board.releaseDice(tempTarget - 1);
								System.out.println("해당 주사위를 돌려두었습니다.");
						}
						else { // 입력값이 없이 엔터키를 입력한 경우
							System.out.println("주사위를 굴렸습니다");
							phase--;
							for (int i = 0; i < 5; i++) {
								board.rollDice(i);
							}
						}
						
						
						
						
					} catch (InputMismatchException ex) {
						System.err.println("잘못된 입력입니다. 1~5의 정수 값, 또는 점수표에 있는 이름을 입력해 주세요.");
					}
				}// end of if(phase >= 0)
				else {
					board.collectDices(); //남은 rollingDice를 choicedDice로 회수
					board.sortChoicedDice();
					System.out.println(board.showDices());
					System.out.println("더 이상 주사위를 굴릴 수 없습니다. 점수표에 있는 이름을 입력하여 점수를 기록해 주세요.");
					String input = in.nextLine();
					int integeredInput = board.nameToBoardIndex(input);
					try {
						switch(integeredInput) {
						case 0: //aces
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 1: //deuces
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 2: //threes
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 3: //fours
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 4: //fives
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 5: //sixes
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 6: //choices
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 7: //4 of a kind
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 8: //full house
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 9: //s. straight
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 10: //l. straight
							board.choiceScore(whosTurn, integeredInput);
							break;
						case 11: //yacht
							board.choiceScore(whosTurn, integeredInput);
							break;
						default: //error
							System.out.println("점수표에 있는 이름을 입력해 주세요.");
							break;
						}
						if(integeredInput >= 0 && integeredInput <= 11)
							break;
					} catch (Exception ex) {
						System.err.printf("%s", ex.getMessage());
					}
					
				}
				
				
				
				
			}
			
			whosTurn = !whosTurn;
			
			
		}
	}
	
	@Override
	public String toString() { //must show game instruction properly.
		String output = "쉽게 말해, 주사위로 포커처럼 '족보'를 만드는 게임입니다.\n";
		output += "각 턴마다 플레이어는 주사위를 최대 세 번까지 던질 수 있고,\n";
		output += "세 번 던지거나 플레이어가 그만 던지기를 원한다면, 족보에서 점수를 고를 수 있습니다.\n";
		output += "매 번 던진 후에, 플레이어는 주사위를 골라 '가지고'있을 수 있으며,\n";
		output += "반대로 던진 후에 가지고있던 주사위를 '버려', 새로운 조합을 꾀할 수도 있습니다.\n";
		output += "한 번 나온 족보가 다시 나와도, 이미 점수판에 기록되어있다면 그 값은 같은 곳에 기록할 수 없습니다.\n";
		output += "Aces~Sixes까지는, 각 숫자가 나온 주사위 눈의 총합을 적을 수 있습니다.\n";
		output += "ex)주사위가 2,2,2,3,5가 나왔고, Deuces를 고르면, 2가 3개 이므로 6점이 기록됩니다.\n";
		output += "Aces~Sixes의 총합이 63점이 넘으면(Subtotal에 표시됩니다.),\n";
		output += "35점의 Bonus점수를 획득할 수 있습니다.\n";
		output += "Choice는 특별한 조건 없이, 나온 주사위 5개의 총합을 기록합니다.\n";
		output += "4 of a Kind는 4개의 같은 주사위가 있을 때에만 주사위 5개의 총합을 기록합니다.\n";
		output += "Full House는 2개의 주사위, 3개의 주사위가 같을 때에만 5개의 총합을 기록합니다.\n";
		output += "Small Straight(S.Straight)는 1,2,3,4 또는 2,3,4,5 또는 3,4,5,6이\n";
		output += "주사위 중에 있을 때에만 15점(고정)을 기록합니다.\n";
		output += "Large Straight(L.Straight)는 1,2,3,4,5 또는 2,3,4,5,6이\n";
		output += "주사위 중에 있을 때에만 30점(고정)을 기록합니다.\n";
		output += "Yacht는 주사위 5개의 값이 모두 같을 때에만 50점(고정)을 기록합니다.\n";
		output += "모든 칸에 값이 차면 승패가 결정되고, Total의 값이 큰 플레이어가 승리합니다!\n";
		output += "추신: 잘 모르겠다면, 그냥 한 번 해보세요!\n";
		
		return output;
	}
	
	private void printWhosTurn() {
		if(whosTurn) {
			System.out.println("1번 플레이어의 "+ turn +"번쨰 턴입니다.");
		} else {
			System.out.println("2번 플레이어의 "+ turn +"번째 턴입니다.");
		}
	}
	
	private void printGameState() {
		System.out.println("========================");
		System.out.println(board);
		System.out.println("========================");
	}
	
	private boolean isInteger(String s) { //정수 판별 함수
		try {
	     	Integer.parseInt(s);
	    	return true;
	    } catch(NumberFormatException e) {  //문자열이 나타내는 숫자와 일치하지 않는 타입의 숫자로 변환 시 발생
	    	return false;
	    }
	}
	
	private void doesExistBonus() {
		if(flagP1Bonus == false && board.getScoreBoard(12) == 63) {
			System.out.println("Bonus!");
			flagP1Bonus = true;
		}
		if(flagP2Bonus == false && board.getScoreBoard(12) == 63) {
			System.out.println("Bonus!");
			flagP2Bonus = true;
		}
	}
	
	private void findWinner() {
		int p1Score = board.getScoreBoard(26);
		int p2Score = board.getScoreBoard(27);
		
		if(p1Score > p2Score) {
			System.out.println("1번 플레이어가 "+p1Score+"로 승리했습니다!");
		}
		else if(p1Score < p2Score) {
			System.out.println("2번 플레이어가 "+p2Score+"로 승리했습니다!");
		}
		else if(p1Score == p2Score) {
			System.out.println("동점입니다. 무승부입니다.");
		}
	}
	
}
