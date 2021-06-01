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
	 * �berpr�ft in der Box der �bergebenen Reihe und Zeile eine idente Zahl
	 * vorhanden ist.
	 */
	@Override
	public boolean checkBox(int row, int col, int guess) {
		int r = row - row % 3;
		int c = col - col % 3;

		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if (this.cells[i][j].getValue() == guess) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Autogenerator f�r ein neues Sudoku Bef�llt rekursiv das im Hintergrund
	 * liegene Sudoku-Array.
	 */
	public boolean createSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
						int a = (int) (Math.random() * 9) + 1;
						if (valid(row, col, a)) {
							this.cells[row][col].setValue(a);
							if (createSudoku()) {
								return true;
							} else {
								this.cells[row][col].setValue(0);
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * L�st ein Sudoku rekursiv
	 */
	@Override
	public boolean solveSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
						if (valid(row, col, y + 1)) {
							this.cells[row][col].setValue(y + 1);
							if (solveSudoku()) {
								return true;
							} else {
								this.cells[row][col].setValue(0);
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

//  https://www.geeksforgeeks.org/sudoku-backtracking-7/
//	@Override
//	public boolean solveSudoku(Cell[][] cells) {
//
//		/*
//		 * if we have reached the 8th row and 9th column (0 indexed matrix) , we are
//		 * returning true to avoid further backtracking
//		 */
//		if (row == N - 1 && col == N)
//			return true;
//
//			// Check if column value  becomes 9 ,
//			// we move to next row
//			// and column start from 0
//		if (col == N) {
//			row++;
//			col = 0;
//		}
//
//			// Check if the current position
//			// of the grid already
//			// contains value >0, we iterate
//			// for next column
//		if (this.cells[row][col].getValue() != 0)
//			return solveSudoku(row, col + 1);
//
//		for (int num = 1; num < 10; num++) {
//
//			// Check if it is safe to place
//			// the num (1-9)  in the
//			// given row ,col ->we move to next column
//			if (this.valid(row, col, num)) {
//
//				/*
//				 * assigning the num in the current (row,col) position of the grid and assuming
//				 * our assined num in the position is correct
//				 */
//				this.cells[row][col].setValue(num);
//
//// Checking for next
//// possibility with next column
//				if (solveSudoku(row, col + 1))
//					return true;
//			}
//			/*
//			 * removing the assigned num , since our assumption was wrong , and we go for
//			 * next assumption with diff num value
//			 */
//			this.cells[row][col].setValue(0);
//		}
//		return false;
//	}

	/**
	 * Generiert automatische eine zuf�llig Zahl Hilfsarray mit den derzeitigen
	 * Werten des Sudokus wird erstellt Generiert daraufhin eine L�sung des
	 * derzeitigen Sudokustandes �berpr�ft ob generiert Zahl laut L�sung in die
	 * Zelle passt und f�gt diese in Hilfsarray ein derzeitige Sudoku nimmt Werte
	 * des Hilfsarray an
	 */
	@Override
	public int[] hint() {
		int randomGuess = (int) (Math.random() * 9) + 1;
		int[] coordinates = new int[2];
		int counter = 0;
		int counter2 = 0;
		int counter3 = 0;

		int help[][] = new int[this.cells.length][this.cells.length];
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				help[row][col] = this.cells[row][col].getValue();
			}
		}
		this.solveSudoku();

		for (int row = 0; row < this.cells.length; row++) {
			counter2++;
			counter3++;
			for (int col = 0; col < this.cells[row].length; col++) {
				if (help[row][col] == 0 && this.cells[row][col].getValue() == randomGuess) {
					help[row][col] = randomGuess;
					coordinates[0] = row;
					coordinates[1] = col;
					counter++;
					break;
				}
			}
			if (counter3 == 500) {
				coordinates = null;
				break;
			}
			if (counter2 == 8) {
				row = 0;
				counter2 = 0;
				randomGuess = (int) (Math.random() * 9) + 1;
			}
			if (counter == 1) {
				break;
			}
		}
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				this.cells[row][col].setValue(help[row][col]);
			}
		}
		return coordinates;
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
	public void shuffle() {
		// TODO Auto-generated method stub
		
	}

	// muss noch besprochen werden in welche klasse die methoden geh�ren

	// updatet den stand des sudoku arrays auf den stand des logik arrays, nach
	// autosolve immer?

}