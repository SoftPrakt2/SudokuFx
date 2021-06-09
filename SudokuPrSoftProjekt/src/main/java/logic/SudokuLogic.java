package logic;

import java.util.Random;

/**
 * extends BasicGameLogic
 * implements all abstract methods of BasicGameLogic
 * @author rafael
 *
 */
public class SudokuLogic extends BasicGameLogic {

	/**
	 * constructor for creating a SudokuLogic-Object
	 * @param gamestate
	 * @param minutesPlayed
	 * @param secondsPlayed
	 * @param isCorrect
	 */
	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[9][9];
		gametype = "Sudoku";
		hintCounter = 3;
	}

	/**
	 * checks if the value already exists in a row
	 */
	@Override
	public boolean checkRow(int row, int col, int guess) {
		for (col = 0; col < this.cells.length; col++) {
			if (this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if the value already exists in a column
	 */
	@Override
	public boolean checkCol(int row, int col, int guess) {
		for (row = 0; row < this.cells.length; row++) {
			if (this.cells[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * fills the sudoku array with values and informations about coordinates
	 */
	@Override
	public void setUpLogicArray() {
		int box = 0;
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (row < 3 && col < 3) {
					box = 1;
				} else if (row < 3 && col < 6) {
					box = 2;
				} else if (row < 3 && col < 9) {
					box = 3;
				} else if (row < 6 && col < 3) {
					box = 4;
				} else if (row < 6 && col < 6) {
					box = 5;
				} else if (row < 6 && col < 9) {
					box = 6;
				} else if (row < 9 && col < 3) {
					box = 7;
				} else if (row < 9 && col < 6) {
					box = 8;
				} else if (row < 9 && col < 9) {
					box = 9;
				}
				Cell cell = new Cell(row, col, box, 0);
				cells[row][col] = cell;
			}
		}
	}

	/**
	 * removes a certain amount of numbers from the array
	 * the amount depends on the difficulty chosen by the user
	 * 
	 */
	public void difficulty() {
        int counter = getNumberOfVisibleValues();

        if(counter == 81) {
            removeValues();
        }
        else {
        	Random r = new Random();
            while (counter != 0) {
                	int randCol = r.nextInt(9);
                	int randRow = r.nextInt(9);
                if (this.cells[randRow][randCol].getValue() != 0 && this.cells[randRow][randCol].getIsReal()) {
                    this.cells[randRow][randCol].setValue(0);
                    this.cells[randRow][randCol].setIsReal(false);
                    counter--;
                }
            }
        }
    }
	
	/**
	 * auxiliary method for {@link #difficulty()}
	 */
	@Override
	public int getNumberOfVisibleValues() {
		if(this.difficulty == 3) {
			return 56;
		}
		else if(this.difficulty == 5) {
			return 46;
		}
		else if(this.difficulty == 7) {
			return 36;
		}
		else {
			return 81;
		}
	}

//	public void printCells() {
//		for (int row = 0; row < this.cells.length; row++) {
//			for (int col = 0; col < this.cells[row].length; col++) {
//				System.out.print(this.cells[row][col].getValue() + " ");
//				if (col == 2 || col == 5) {
//					System.out.print("|");
//				}
//			}
//			System.out.println();
//			if (row == 2 || row == 5 || row == 8) {
//				System.out.println("-------------------");
//			}
//		}
//	}

	@Override
	public boolean isConnected() {
		return false;
	}
}