package logic;

import java.util.Random;

public class SudokuLogic extends BasicGameLogic {

	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[9][9];
		gametype = "Sudoku";
		hintCounter = 3;
	}

	/**
	 * Überprüft in der übergebenen Reihe ob eine idente Zahl vorhanden ist
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
	 * Überprüft in der übergebenen Zeile ob eine idente Zahl vorhanden ist
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
	 * Befüllt Sudokuarray mit dem Wert 0
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
	 * Abhängig von der Übergebenen Zahl werden zufällige Zahl des Arrays auf 0 gesetzt
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