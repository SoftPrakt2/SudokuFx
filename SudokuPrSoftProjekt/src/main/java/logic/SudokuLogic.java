package logic;

public class SudokuLogic extends BasicGameLogic {

	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[9][9];
		gametype = "Sudoku";
		hintCounter = 3;
	}

	/**
	 * �berpr�ft ob die Methoden checkRow, checkCol und CheckBox true zur�ckgeben
	 */
	@Override
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, col, guess) && checkCol(row, col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	/**
	 * �berpr�ft in der �bergebenen Reihe ob eine idente Zahl vorhanden ist
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
	 * �berpr�ft in der �bergebenen Zeile ob eine idente Zahl vorhanden ist
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
	 * Bef�llt Sudokuarray mit mit dem Wert 0
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
	 * Abh�ngig von der �bergebenen Zahl werden zuf�llige Zahl des Arrays auf 0
	 * gesetzt
	 */
	public void difficulty() {
        int counter = 81;
        if (this.difficulty == 3)
            counter = 56;
        if (this.difficulty == 5)
            counter = 46;
        if (this.difficulty == 7)
            counter = 36;

        if(counter == 81) {
            for (int row = 0; row < this.cells.length; row++) {
                for (int col = 0; col < this.cells[row].length; col++) {
                    this.cells[row][col].setIsReal(false);
                    this.cells[row][col].setValue(0);
                }
            }
        }
        else {
            while (counter != 0) {
                int randomCol = (int) (Math.floor(Math.random() * 8.9999));
                int randomRow = (int) (Math.floor(Math.random() * 8.9999));
                if (this.cells[randomRow][randomCol].getValue() != 0 && this.cells[randomRow][randomCol].getIsReal()) {
                    this.cells[randomRow][randomCol].setValue(0);
                    this.cells[randomRow][randomCol].setIsReal(false);
                    counter--;
                }
            }
        }
    }

	public void setCell(int col, int row, int guess) {
		this.cells[col][row].setValue(guess);
		this.cells[col][row].setIsReal(true);
	}

	public void printCells() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				System.out.print(this.cells[row][col].getValue() + " ");
				if (col == 2 || col == 5) {
					System.out.print("|");
				}
			}
			System.out.println();
			if (row == 2 || row == 5 || row == 8) {
				System.out.println("-------------------");
			}
		}
	}

	/**
	 * Getter und Setter der Variablen
	 */
	@Override
	public void setGameState(Gamestate gamestate) {
		this.gamestate = gamestate;
	}

	@Override
	public Gamestate getGamestate() {
		return this.gamestate;
	}

	@Override
	public boolean testIfSolved() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isConnected() {
		return false;
	}
}