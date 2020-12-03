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
			
			if(turn == 12 && whosTurn == false) { // ���� ����
				findWinner();
				break;
			}
			
			while(true) {
				System.out.println("����Ű�� �Է��Ͽ� �ֻ����� ��������!");
				String input = in.nextLine();
				if(input.equals("")) {
					break;
				}
			}
			System.out.println("�ֻ����� ���Ƚ��ϴ�");
			
			for (int i = 0; i < 5; i++) {
				board.rollDice(i);
			}
			
			while(true) { // loop for choosing target
				if(phase > 0 && !board.isNoDiceToRoll()) {
					board.sortChoicedDice();
					System.out.println(board.showDices());
					
					System.out.println("�� �ֻ����� ������ ��ȣ�� �Է��ϰ�, �����ִ� �ֻ����� ����ǥ�� ������ ����ǥ�� �̸��� �Է��ϼ���.\n");
					System.out.println("�̹� �� �ֻ����� �����Ϸ��� !(�ֻ��� ��ġ)�� �Է��ϼ���.");
					System.out.println("�ֻ����� �ٽ� �������� �ٸ� �Է� ���� ����Ű�� �Է��ϼ���(�ֻ����� "+phase+"�� �� ���� �� �ֽ��ϴ�).");
					
					
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
							
							if(!flag_inputIsNumber) { // ���ڰ� �ԷµǾ��� ��
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
										System.out.println("�ֻ����� �����ų�, �ֻ����� ���ų�, ����ǥ�� �ִ� �̸��� �Է��� �ּ���.");
										break;
									}
									break;
								} catch (Exception ex) {
									System.err.printf("%s", ex.getMessage());
								}
							} else { // ���ڰ� �ԷµǾ��� �� 
								if(inputToInt <= 0 && inputToInt > 5)
									throw new InputMismatchException();
								try {
									board.choiceDice(inputToInt - 1);
									System.out.println("�ֻ����� �����߽��ϴ�.");
								} catch (IndexOutOfBoundsException ex) {
									System.err.println("�� ��ġ���� �ֻ����� �����ϴ�.");
								}
							}
							
							
							
						}
						else if(!input.equals("") && input.substring(0, 1).equals("!")) {
								int tempTarget = input.substring(1).charAt(0) - '0';
								if(tempTarget > 5 || tempTarget < 0) {
									throw new InputMismatchException();
								}
								
								board.releaseDice(tempTarget - 1);
								System.out.println("�ش� �ֻ����� �����ξ����ϴ�.");
						}
						else { // �Է°��� ���� ����Ű�� �Է��� ���
							System.out.println("�ֻ����� ���Ƚ��ϴ�");
							phase--;
							for (int i = 0; i < 5; i++) {
								board.rollDice(i);
							}
						}
						
						
						
						
					} catch (InputMismatchException ex) {
						System.err.println("�߸��� �Է��Դϴ�. 1~5�� ���� ��, �Ǵ� ����ǥ�� �ִ� �̸��� �Է��� �ּ���.");
					}
				}// end of if(phase >= 0)
				else {
					board.collectDices(); //���� rollingDice�� choicedDice�� ȸ��
					board.sortChoicedDice();
					System.out.println(board.showDices());
					System.out.println("�� �̻� �ֻ����� ���� �� �����ϴ�. ����ǥ�� �ִ� �̸��� �Է��Ͽ� ������ ����� �ּ���.");
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
							System.out.println("����ǥ�� �ִ� �̸��� �Է��� �ּ���.");
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
		String output = "���� ����, �ֻ����� ��Ŀó�� '����'�� ����� �����Դϴ�.\n";
		output += "�� �ϸ��� �÷��̾�� �ֻ����� �ִ� �� ������ ���� �� �ְ�,\n";
		output += "�� �� �����ų� �÷��̾ �׸� �����⸦ ���Ѵٸ�, �������� ������ �� �� �ֽ��ϴ�.\n";
		output += "�� �� ���� �Ŀ�, �÷��̾�� �ֻ����� ��� '������'���� �� ������,\n";
		output += "�ݴ�� ���� �Ŀ� �������ִ� �ֻ����� '����', ���ο� ������ ���� ���� �ֽ��ϴ�.\n";
		output += "�� �� ���� ������ �ٽ� ���͵�, �̹� �����ǿ� ��ϵǾ��ִٸ� �� ���� ���� ���� ����� �� �����ϴ�.\n";
		output += "Aces~Sixes������, �� ���ڰ� ���� �ֻ��� ���� ������ ���� �� �ֽ��ϴ�.\n";
		output += "ex)�ֻ����� 2,2,2,3,5�� ���԰�, Deuces�� ����, 2�� 3�� �̹Ƿ� 6���� ��ϵ˴ϴ�.\n";
		output += "Aces~Sixes�� ������ 63���� ������(Subtotal�� ǥ�õ˴ϴ�.),\n";
		output += "35���� Bonus������ ȹ���� �� �ֽ��ϴ�.\n";
		output += "Choice�� Ư���� ���� ����, ���� �ֻ��� 5���� ������ ����մϴ�.\n";
		output += "4 of a Kind�� 4���� ���� �ֻ����� ���� ������ �ֻ��� 5���� ������ ����մϴ�.\n";
		output += "Full House�� 2���� �ֻ���, 3���� �ֻ����� ���� ������ 5���� ������ ����մϴ�.\n";
		output += "Small Straight(S.Straight)�� 1,2,3,4 �Ǵ� 2,3,4,5 �Ǵ� 3,4,5,6��\n";
		output += "�ֻ��� �߿� ���� ������ 15��(����)�� ����մϴ�.\n";
		output += "Large Straight(L.Straight)�� 1,2,3,4,5 �Ǵ� 2,3,4,5,6��\n";
		output += "�ֻ��� �߿� ���� ������ 30��(����)�� ����մϴ�.\n";
		output += "Yacht�� �ֻ��� 5���� ���� ��� ���� ������ 50��(����)�� ����մϴ�.\n";
		output += "��� ĭ�� ���� ���� ���а� �����ǰ�, Total�� ���� ū �÷��̾ �¸��մϴ�!\n";
		output += "�߽�: �� �𸣰ڴٸ�, �׳� �� �� �غ�����!\n";
		
		return output;
	}
	
	private void printWhosTurn() {
		if(whosTurn) {
			System.out.println("1�� �÷��̾��� "+ turn +"���� ���Դϴ�.");
		} else {
			System.out.println("2�� �÷��̾��� "+ turn +"��° ���Դϴ�.");
		}
	}
	
	private void printGameState() {
		System.out.println("========================");
		System.out.println(board);
		System.out.println("========================");
	}
	
	private boolean isInteger(String s) { //���� �Ǻ� �Լ�
		try {
	     	Integer.parseInt(s);
	    	return true;
	    } catch(NumberFormatException e) {  //���ڿ��� ��Ÿ���� ���ڿ� ��ġ���� �ʴ� Ÿ���� ���ڷ� ��ȯ �� �߻�
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
			System.out.println("1�� �÷��̾ "+p1Score+"�� �¸��߽��ϴ�!");
		}
		else if(p1Score < p2Score) {
			System.out.println("2�� �÷��̾ "+p2Score+"�� �¸��߽��ϴ�!");
		}
		else if(p1Score == p2Score) {
			System.out.println("�����Դϴ�. ���º��Դϴ�.");
		}
	}
	
}
