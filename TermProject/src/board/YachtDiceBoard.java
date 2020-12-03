package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dice.Dice;

public class YachtDiceBoard extends Board{

	private int[] scoreBoard = new int[28];
	private List<Blank<Dice>> choicedDice = new ArrayList<Blank<Dice>>();
	private List<Blank<Dice>> rollingDice = new ArrayList<Blank<Dice>>();
	private boolean[] isGetBonus = new boolean[2];
	
	public YachtDiceBoard() {
		super(38);
		for(int i = 0; i < 26; i++)
			scoreBoard[i] = -1;
		scoreBoard[26] = 0;
		scoreBoard[27] = 0;
		for(int i = 0; i < 5; i++) {
			Blank<Dice> temp1 = new Blank<Dice>(28 + i, null);
			choicedDice.add(temp1);
			
			Blank<Dice> temp2 = new Blank<Dice>(33 + i, new Dice());
			rollingDice.add(temp2);
		}
		for(int i = 0; i < 2; i++) {
			isGetBonus[i] = false;
		}
		
	}
	
	@Override
	public String toString() {
		String output = "";
		output += showBoard();
		output += "========================\n";
		output += showDices();
		
		
		return output;
	}
	
	public int getScoreBoard(int index) { return scoreBoard[index]; }
	
	public String showBoard() {
		String[] scoreTable = new String[15];
		scoreTable[0] = "│Aces       │";
		scoreTable[1] = "│Deuces     │";
		scoreTable[2] = "│Threes     │";
		scoreTable[3] = "│Fours      │";
		scoreTable[4] = "│Fives      │";
		scoreTable[5] = "│Sixes      │";
		scoreTable[6] = "│Subtotal   │";
		scoreTable[7] = "│+35 Bonus  │";
		scoreTable[8] = "│Choice     │";
		scoreTable[9] = "│4 of a Kind│";
		scoreTable[10] = "│Full House │";
		scoreTable[11] = "│S.Straight │";
		scoreTable[12] = "│L.Straight │";
		scoreTable[13] = "│Yacht      │";
		scoreTable[14] = "│Total      │";
		
		String output = "";
		output += "┌───────────┬─────┬─────┐\n";
		output += "│           │  1P │  2P │\n";
		output += "├───────────┼─────┼─────┤\n";
		for(int i = 0; i < 15; i++) {
			if(i < 6) {
				if(scoreBoard[2 * i] >= 10) {
					if(scoreBoard[2 * i + 1] >= 10) {
						output += scoreTable[i]+" "+scoreBoard[2 * i]+"  │ "+scoreBoard[2 * i + 1]+"  │\n";
					} else if (scoreBoard[2 * i + 1] == -1) {
						output += scoreTable[i]+" "+scoreBoard[2 * i]+"  │  X  │\n";
					} else {
						output += scoreTable[i]+" "+scoreBoard[2 * i]+"  │  "+scoreBoard[2 * i + 1]+"  │\n";
					}
				} else if(scoreBoard[2 * i] == -1) {
					if(scoreBoard[2 * i + 1] >= 10) {
						output += scoreTable[i]+"  X  │ "+scoreBoard[2 * i + 1]+"  │\n";
					} else if (scoreBoard[2 * i + 1] == -1) {
						output += scoreTable[i]+"  X  │  X  │\n";
					} else {
						output += scoreTable[i]+"  X  │  "+scoreBoard[2 * i + 1]+"  │\n";
					}
				}
				else {
					if(scoreBoard[2 * i + 1] >= 10) {
						output += scoreTable[i]+"  "+scoreBoard[2 * i]+"  │ "+scoreBoard[2 * i + 1]+"  │\n";
					} else if(scoreBoard[2 * i + 1] == -1) {
						output += scoreTable[i]+"  "+scoreBoard[2 * i]+"  │  X  │\n";
					} else {
						output += scoreTable[i]+"  "+scoreBoard[2 * i]+"  │  "+scoreBoard[2 * i + 1]+"  │\n";
					}
				}
				output += "├───────────┼─────┼─────┤\n";
			}
			else if(i == 6) {
				int temp1 = getTotalScore(true, true);
				int temp2 = getTotalScore(false, true);
				if(temp1 < 0) temp1 = 0;
				if(temp2 < 0) temp2 = 0;
				if(temp1 >= 10) {
					if(temp2 >= 10) {
						output += scoreTable[i] + temp1+"/63│"+temp2+"/63│\n";
					} else {
						output += scoreTable[i] +temp1+"/63│ "+temp2+"/63│\n";
					}
				} else {
					if(temp2 >= 10) {
						output += scoreTable[i] + " "+temp1+"/63│"+temp2+"/63│\n";
					} else {
						output += scoreTable[i] + " "+temp1+"/63│ "+temp2+"/63│\n";
					}
				}
				output += "├───────────┼─────┼─────┤\n";
			}
			else if(i == 7) {
				int temp1 = getTotalScore(true, true);
				int temp2 = getTotalScore(false, true);
				if(temp1 >= 63)
					scoreBoard[12] = 35;
				else
					scoreBoard[12] = 0;
				if(temp2 >= 63)
					scoreBoard[13] = 35;
				else
					scoreBoard[12] = 0;
				if(temp1 >= 63) {
					if(temp2 >= 63) {
						output += scoreTable[i] + " 35  │ 35  │\n";
					} else {
						output += scoreTable[i] + " 35  │  0  │\n";
					}
				} else {
					if(temp2 >= 63) {
						output += scoreTable[i] + "  0  │ 35  │\n";
					} else {
						output += scoreTable[i] + "  0  │  0  │\n";
					}
				}
				output += "├───────────┼─────┼─────┤\n";
			}
			else { // i: 8 ~ 14
				if(scoreBoard[2 * i - 2] >= 10) {
					if(scoreBoard[2 * i - 1] >= 10) {
						output += scoreTable[i]+" "+scoreBoard[2 * i - 2]+"  │ "+scoreBoard[2 * i - 1]+"  │\n";
					} else if(scoreBoard[2 * i - 1] == -1) {
						output += scoreTable[i]+" "+scoreBoard[2 * i - 2]+"  │  X  │\n";
					} else {
						output += scoreTable[i]+" "+scoreBoard[2 * i - 2]+"  │  "+scoreBoard[2 * i - 1]+"  │\n";
					}
				} else if(scoreBoard[2 * i - 2] == -1) {
					if(scoreBoard[2 * i - 1] >= 10) {
						output += scoreTable[i]+"  X  │ "+scoreBoard[2 * i - 1]+"  │\n";
					} else if(scoreBoard[2 * i - 1] == -1) {
						output += scoreTable[i]+"  X  │  X  │\n";
					} else {
						output += scoreTable[i]+"  X  │  "+scoreBoard[2 * i - 1]+"  │\n";
					}
				} else {
					if(scoreBoard[2 * i - 1] >= 10) {
						output += scoreTable[i]+"  "+scoreBoard[2 * i - 2]+"  │ "+scoreBoard[2 * i - 1]+"  │\n";
					} else if(scoreBoard[2 * i - 1] == -1) {
						output += scoreTable[i]+"  "+scoreBoard[2 * i - 2]+"  │  X  │\n";
					} else {
						output += scoreTable[i]+"  "+scoreBoard[2 * i - 2]+"  │  "+scoreBoard[2 * i - 1]+"  │\n";
					}
				}
				
				if(i == 14) {
					output += "└───────────┴─────┴─────┘\n";
				} else {
					output += "├───────────┼─────┼─────┤\n";
				}
			}
		}
		return output;
	}
	
	public String showDices() {
		String output = "";
		output += "Choiced Dice\n";
		for(int i = 0; i < 5; i++) {
			try {
				Blank<Dice> tempBlank = choicedDice.get(i);
				output += tempBlank.getData().getShowingNum() + " ";
			} catch (NullPointerException ex) {
				output += "X ";
			} catch (IndexOutOfBoundsException ex) {
				output += "X ";
			}
		}
		output += "\n========================\n";
		output += "Rolling Dice\n";
		for(int i = 0; i < 5; i++) {
			try {
				Blank<Dice> tempBlank = rollingDice.get(i);
				output += tempBlank.getData().getShowingNum() + " ";
			} catch (NullPointerException ex) {
				output += "X ";
			} catch (IndexOutOfBoundsException ex) {
				output += "X ";
			}
			
		}
		
		return output;
	}
	
	private Dice getRollingDice(int index) throws IndexOutOfBoundsException { // input 0 ~ 4
		return rollingDice.get(index).getData();
	}
	
	private Dice getChoicedDice(int index) {
		return choicedDice.get(index).getData();
	}
	
	public int rollDice(int index) {
		try {
			Dice temp = getRollingDice(index);
			int output = temp.roll();
			rollingDice.set(index, new Blank<Dice>(33 + index, temp));
			return output;
		} catch (IndexOutOfBoundsException ex) {
			return -1;
		} catch (NullPointerException ex) {
			return -1;
		}
	}
	
	public int getDiceNum(boolean whichDice, int index) { //true = choiceDice, false = rollingDice / index 0~4
		try {
			if(whichDice) { // for choiceDice
				Dice temp = getChoicedDice(index);
				return temp.getShowingNum();
			}
			else { // for rollingDice
				Dice temp = getRollingDice(index);
				return temp.getShowingNum();
			}
		} catch(NullPointerException ex) {
			return 0;
		}
	}
	
	public void choiceDice(int index) throws IndexOutOfBoundsException {
		if(choicedDice.get(index).isEmpty() && !rollingDice.get(index).isEmpty()) {
			Blank<Dice> tempBlankRolling = rollingDice.get(index);
			Blank<Dice> tempBlankChoiced = choicedDice.get(index);
			Dice tempDiceRolling = tempBlankRolling.getData();
			Dice tempDiceChoiced = tempBlankChoiced.getData();
			
			tempBlankRolling.setData(tempDiceChoiced);
			tempBlankChoiced.setData(tempDiceRolling);
			
			choicedDice.set(index, tempBlankChoiced);
			rollingDice.set(index, tempBlankRolling);
			
		}
	}
	
	public void releaseDice(int index) throws IndexOutOfBoundsException {
		if(rollingDice.get(index).isEmpty() && !choicedDice.get(index).isEmpty()) {
			Blank<Dice> tempBlankRolling = rollingDice.get(index);
			Blank<Dice> tempBlankChoiced = choicedDice.get(index);
			Dice tempDiceRolling = tempBlankRolling.getData();
			Dice tempDiceChoiced = tempBlankChoiced.getData();
			
			tempBlankRolling.setData(tempDiceChoiced);
			tempBlankChoiced.setData(tempDiceRolling);
			
			choicedDice.set(index, tempBlankChoiced);
			rollingDice.set(index, tempBlankRolling);
		}
	}
	
	public void sortChoicedDice() {
		try {
			for(int i = 4; i > 0; i--) {
				for(int j = 0; j < i; j++) {
					int temp1 = getChoicedDice(j).getShowingNum();
					int temp2 = getChoicedDice(j + 1).getShowingNum();
					if(temp1 > temp2) {
						Blank<Dice> tempBlankj = choicedDice.get(j);
						Blank<Dice> tempBlankj1 = choicedDice.get(j + 1);
						Dice tempDice1 = tempBlankj.getData();
						Dice tempDice2 = tempBlankj1.getData();
						tempBlankj.setData(tempDice2);
						tempBlankj1.setData(tempDice1);
						
						choicedDice.set(j, tempBlankj);
						choicedDice.set(j + 1, tempBlankj1);
					}
					
				}
			}
		} catch(NullPointerException ex) {
			return; //pass(stop sorting)
		} catch(IndexOutOfBoundsException ex) {
			return; //pass(stop sorting)
		}
		
	}
	
	public void collectDices() { // 
		for(int i = 0; i < 5; i++) {
			if(!rollingDice.get(i).isEmpty()) {
				Blank<Dice> tempBlankRolling = rollingDice.get(i);
				Blank<Dice> tempBlankChoiced = choicedDice.get(i);
				Dice tempDiceRolling = tempBlankRolling.getData();
				Dice tempDiceChoiced = tempBlankChoiced.getData();
				tempBlankRolling.setData(tempDiceChoiced);
				tempBlankChoiced.setData(tempDiceRolling);
				
				rollingDice.set(i, tempBlankRolling);
				choicedDice.set(i, tempBlankChoiced);
				
			}
		}
	}
	
	public void choiceScore(boolean whosTurn, int selectedCategory) throws Exception {
		int counter = 0;
		int add1if2Pturn = 0;
		if(!whosTurn)
			add1if2Pturn++;
		
		
		switch(selectedCategory) { // 연산을 각 주사위를 더하는 방식으로 처리하는 경우는 미리 0으로 값을 바꿔줌.
		// 그 외에 제외해야 할 것도 Exception처리해줌
		case 0: //aces
			if(scoreBoard[0 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			break;
		case 1: //deuces
			if(scoreBoard[2 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			break;
		case 2: //threes
			if(scoreBoard[4 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			break;
		case 3: //fours
			if(scoreBoard[6 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			break;
		case 4: //fives
			if(scoreBoard[8 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			break;
		case 5: //sixes
			if(scoreBoard[10 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			break;
		case 6: //choices
			if(scoreBoard[14 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			scoreBoard[14 + add1if2Pturn] = 0;
			break;
		case 7: //4 of a kind
			if(scoreBoard[16 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			scoreBoard[16 + add1if2Pturn] = 0;
			break;
		case 8: //full house
			if(scoreBoard[18 + add1if2Pturn] != -1)
				throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
			scoreBoard[18 + add1if2Pturn] = 0;
			break;
		default: //그 외
			break;
		}
		collectDices();
		sortChoicedDice();
		for(int i = 0; i < 5; i++) {
			switch(selectedCategory) {
			case 0: //aces
				if(getChoicedDice(i).getShowingNum() == 1)
					counter++;
				scoreBoard[0 + add1if2Pturn] = counter * 1;
				break;
			case 1: //deuces
				if(getChoicedDice(i).getShowingNum() == 2)
					counter++;
				scoreBoard[2 + add1if2Pturn] = counter * 2;
				break;
			case 2: //threes
				if(getChoicedDice(i).getShowingNum() == 3)
					counter++;
				scoreBoard[4 + add1if2Pturn] = counter * 3;
				break;
			case 3: //fours
				if(getChoicedDice(i).getShowingNum() == 4)
					counter++;
				scoreBoard[6 + add1if2Pturn] = counter * 4;
				break;
			case 4: //fives
				if(getChoicedDice(i).getShowingNum() == 5)
					counter++;
				scoreBoard[8 + add1if2Pturn] = counter * 5;
				break;
			case 5: //sixes
				if(getChoicedDice(i).getShowingNum() == 6)
					counter++;
				scoreBoard[10 + add1if2Pturn] = counter * 6;
				break;
			case 6: //choices
				scoreBoard[14 + add1if2Pturn] += getChoicedDice(i).getShowingNum();
				break;
			case 7: //4 of a kind
				if(getChoicedDice(1).getShowingNum() == getChoicedDice(2).getShowingNum() && getChoicedDice(2).getShowingNum() == getChoicedDice(3).getShowingNum() 
						 && getChoicedDice(3).getShowingNum() == getChoicedDice(4).getShowingNum()) { //첫번째 주사위를 제외한 나머지 주사위들이 동일할 경우
					scoreBoard[16 + add1if2Pturn] += getChoicedDice(i).getShowingNum();
				}
				else if(getChoicedDice(0).getShowingNum() == getChoicedDice(1).getShowingNum() && getChoicedDice(1).getShowingNum() == getChoicedDice(2).getShowingNum()
						 && getChoicedDice(2).getShowingNum() == getChoicedDice(3).getShowingNum()) { //마지막 주사위를 제외한 나머지 주사위들이 동일할 경우
					scoreBoard[16 + add1if2Pturn] += getChoicedDice(i).getShowingNum();
				}
				else // 어느것도 충족하지 못하지만, 족보를 선택한 경우 0점부여.
					scoreBoard[16 + add1if2Pturn] = 0;
				break;
			case 8: //full house
				if(getChoicedDice(0).getShowingNum() == getChoicedDice(1).getShowingNum() && getChoicedDice(2).getShowingNum() == getChoicedDice(3).getShowingNum()
						 && getChoicedDice(3).getShowingNum() == getChoicedDice(4).getShowingNum()) { //1, 2번 주사위가 같고, 3, 4, 5번 주사위가 같은 경우
					scoreBoard[18 + add1if2Pturn] += getChoicedDice(i).getShowingNum();
				}
				else if(getChoicedDice(0).getShowingNum() == getChoicedDice(1).getShowingNum() && getChoicedDice(1).getShowingNum() == getChoicedDice(2).getShowingNum()
						 && getChoicedDice(3).getShowingNum() == getChoicedDice(4).getShowingNum()) { //1, 2, 3번 주사위가 같고, 4, 5번 주사위가 같은 경우
					scoreBoard[18 + add1if2Pturn] += getChoicedDice(i).getShowingNum();
				}
				else // 어느것도 충족하지 못하지만, 족보를 선택한 경우 0점부여.
					scoreBoard[18 + add1if2Pturn] = 0;
				break;
			case 9: //s. straight
				if(scoreBoard[20 + add1if2Pturn] != -1)
					throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
				boolean numberExist[] = new boolean[6];
				for(int j = 0; j < 5; j++)
					numberExist[i] = false;
				
				for(int j = 0; j < 5; j++) {
					numberExist[getChoicedDice(j).getShowingNum()] = true;
				}
				if(numberExist[0] && numberExist[1] && numberExist[2] && numberExist[3]) {
					//4개의 주사위가 각각 1, 2, 3, 4인 경우
					scoreBoard[20 + add1if2Pturn] = 15;
				}
				else if(numberExist[1] && numberExist[2] && numberExist[3] && numberExist[4]) {
					//4개의 주사위가 각각 2, 3, 4, 5인 경우
					scoreBoard[20 + add1if2Pturn] = 15;
				}
				else if(numberExist[2] && numberExist[3] && numberExist[4] && numberExist[5]) {
					//4개의 주사위가 각각 3, 4, 5, 6인 경우
					scoreBoard[20 + add1if2Pturn] = 15;
				}
				else //어느것도 충족하지 못하지만, 족보를 선택한 경우 0점부여.
					scoreBoard[20 + add1if2Pturn] = 0;
				break;
			case 10: //l. straight
				if(scoreBoard[22 + add1if2Pturn] != -1)
					throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
				if(getChoicedDice(0).getShowingNum() == 1 && getChoicedDice(1).getShowingNum() == 2 && getChoicedDice(2).getShowingNum() == 3
						 && getChoicedDice(3).getShowingNum() == 4 && getChoicedDice(4).getShowingNum() == 5) {
					//1, 2, 3, 4, 5번째 주사위가 각각 1, 2, 3, 4, 5인 경우
					scoreBoard[22 + add1if2Pturn] = 30;
				}
				else if(getChoicedDice(0).getShowingNum() == 2 && getChoicedDice(1).getShowingNum() == 3 && getChoicedDice(2).getShowingNum() == 4
						 && getChoicedDice(3).getShowingNum() == 5 && getChoicedDice(4).getShowingNum() == 6) {
					//1, 2, 3, 4, 5번째 주사위가 각각 2, 3, 4, 5, 6인 경우
					scoreBoard[22 + add1if2Pturn] = 30;
				}
				else //어느것도 충족하지 못하지만, 족보를 선택한 경우 0점부여.
					scoreBoard[22 + add1if2Pturn] = 0;
				break;
			case 11: //yacht
				if(scoreBoard[24 + add1if2Pturn] != -1)
					throw new Exception("이미 선택된 점수표입니다. 다른 점수표를 고르세요.");
				if(getChoicedDice(0).getShowingNum() == getChoicedDice(1).getShowingNum() && getChoicedDice(1).getShowingNum() == getChoicedDice(2).getShowingNum()
					&& getChoicedDice(2).getShowingNum() == getChoicedDice(3).getShowingNum() && getChoicedDice(3).getShowingNum() == getChoicedDice(4).getShowingNum()) {
					//1, 2, 3, 4, 5번째 주사위가 모두 같은 경우
					scoreBoard[24 + add1if2Pturn] = 50;
				}
				else //어느것도 충족하지 못하지만, 족보를 선택한 경우 0점부여.
					scoreBoard[24 + add1if2Pturn] = 0;
				break;
			default: //error
				System.out.println("Error has occured in YachtDiceBoard.choiceScore.switchcase");
				System.exit(-1);
			}// end of switch case
			if(selectedCategory >= 9 && selectedCategory <= 11) //s.straight부터 yacht까지는 점수가 고정되기 때문에, 반복하여 수행할 필요가 없으므로 break.
				break;
		}// end of for
		
	}
	
	public int getTotalScore(boolean whosTurn, boolean bonusOrTotal) { // true: 1p, false: 2p / true: bonus, false: total
		int output = 0;
		if(whosTurn) {
			if(bonusOrTotal) {
				for(int i = 0; i < 11; i += 2) {
					if(scoreBoard[i] != -1)
						output += scoreBoard[i];
				}
			}
			else {
				for(int i = 0; i < 25; i += 2) {
					if(scoreBoard[i] != -1)
						output += scoreBoard[i];
				}
			}
		}
		else {
			if(bonusOrTotal) {
				for(int i = 1; i < 12; i += 2) {
					if(scoreBoard[i] != -1)
						output += scoreBoard[i];
				}
			}
			else {
				for(int i = 1; i < 26; i += 2) {
					if(scoreBoard[i] != -1)
						output += scoreBoard[i];
				}
			}
		}
		return output;
	}
	
	public int nameToBoardIndex(String name) { // error: -1
		name = name.toLowerCase();
		switch(name) {
		case "aces":
			return 0;
		case "deuces":
			return 1;
		case "threes":
			return 2;
		case "fours":
			return 3;
		case "fives":
			return 4;
		case "sixes":
			return 5;
		case "choice":
			return 6;
		case "4 of a kind":
			return 7;
		case "full house":
			return 8;
		case "s.straight":
			return 9;
		case "l.straight":
			return 10;
		case "yacht":
			return 11;
		default:
			return -1;
		}
	}
	
	public boolean isNoDiceToRoll() {
		if(rollingDice.get(0).isEmpty() && rollingDice.get(1).isEmpty() && rollingDice.get(2).isEmpty() && rollingDice.get(3).isEmpty() && rollingDice.get(4).isEmpty())
			return true;
		else
			return false;
	}
	
	public void refresh() {
		if(this.getTotalScore(true, true) >= 63)
			scoreBoard[12] = 35;
		if(this.getTotalScore(false, true) >= 63)
			scoreBoard[13] = 35;
		int temp1 = 0;
		int temp2 = 0;
		for(int i = 0; i < 26; i++) {
			if(i % 2 == 0 && scoreBoard[i] != -1) {
				temp1 += scoreBoard[i];
			}
			else if(i % 2 == 1 && scoreBoard[i] != -1){
				temp2 += scoreBoard[i];
			}
		}
		scoreBoard[26] = temp1;
		scoreBoard[27] = temp2;
		for(int i = 0; i < 5; i++) {
			releaseDice(i);
		}
	}
	
}
