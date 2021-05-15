package logic;

import application.SudokuField;

public class SudokuLogic extends BasicGameLogic {

	private Cell[][] cells;
	static int counter = 0;

	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[9][9];
		gameType = "Sudoku";
		hintCounter = 3;
	}
	
	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * Überprüft ob die Methoden checkRow, checkCol und CheckBox true zurückgeben
	 */
	@Override
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, col, guess) && checkCol(row, col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
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
	 * Überprüft in der Box der übergebenen Reihe und Zeile eine idente Zahl
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
	 * Autogenerator für ein neues Sudoku Befüllt rekursiv das im Hintergrund
	 * liegene Sudoku-Array.
	 */
	public boolean createSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
//						counter++;
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
		System.out.println(counter);
		return true;
	}

	/*
	 * Löst ein Sudoku rekursiv
	 */
	@Override
	public boolean solveSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < this.cells.length; y++) {
						counter++;
//						if(counter == 500000) {
//							return true;
//						}
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
		System.out.println(counter);
		return true;
	}

//	public boolean solveSudoku() {
//		int row = 0;
//		int col = 0;
//		boolean checkBlankSpaces = false;
//
//		/*
//		 * verify if sudoku is already solved and if not solved, get next "blank" space
//		 * position
//		 */
//		for (row = 0; row < this.cells.length; row++) {
//			for (col = 0; col < this.cells[row].length; col++) {
//				if (this.cells[row][col].getValue() == 0) {
//					checkBlankSpaces = true;
//					break;
//				}
//			}
//			if (checkBlankSpaces == true) {
//				break;
//			}
//		}
//		// no more "blank" spaces means the puzzle is solved
//		if (checkBlankSpaces == false) {
//			return true;
//		}
//
//		// try to fill "blank" space with correct num
//		for (int num = 1; num <= 9; num++) {
//			/*
//			 * isSafe checks that num isn't already present in the row, column, or 3x3 box
//			 * (see below)
//			 */
//			if (this.valid(row, col, num)) {
//				this.cells[row][col].setValue(num);
//
//				if (solveSudoku()) {
//					return true;
//				}
//
//				/*
//				 * if num is placed in incorrect position, mark as "blank" again then backtrack
//				 * with a different num
//				 */
//				this.cells[row][col].setValue(0);
//				;
//			}
//		}
//		return false;
//	}

	// https://www.geeksforgeeks.org/sudoku-backtracking-7/
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
//
//	public boolean testIfSolved() {
//		for (int row = 0; row < this.cells.length; row++) {
//			for (int col = 0; col < this.cells[row].length; col++) {
//				if (this.cells[row][col].getValue() == 0) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}

	/**
	 * Generiert automatische eine zufällig Zahl Hilfsarray mit den derzeitigen
	 * Werten des Sudokus wird erstellt Generiert daraufhin eine Lösung des
	 * derzeitigen Sudokustandes Überprüft ob generiert Zahl laut Lösung in die
	 * Zelle passt und fügt diese in Hilfsarray ein derzeitige Sudoku nimmt Werte
	 * des Hilfsarray an
	 */
	@Override
	public int[] hint() {
		int randomGuess = (int) (Math.random() * 9) + 1;
		int [] coordinates = new int [2];
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
	 * Befüllt Sudokuarray mit mit dem Wert 0
	 */
	@Override
	public void setUpLogicArray() {
		int box = 1;
		int uid = 0;
		for (int i = 0; i < this.cells.length; i++) {
			for (int j = 0; j < this.cells[i].length; j++) {
				if (i < 3 && j < 3) {
					box = 1;
				}

				else if (i < 3 && j < 6) {
					box = 2;
				} else if (i < 3 && j < 9) {
					box = 3;
				} else if (i < 6 && j < 3) {
					box = 4;
				} else if (i < 6 && j < 6) {
					box = 5;
				} else if (i < 6 && j < 9) {
					box = 6;
				} else if (i < 9 && j < 3) {
					box = 7;
				} else if (i < 9 && j < 6) {
					box = 8;
				} else if (i < 9 && j < 9) {
					box = 9;
				}
				Cell cell = new Cell(uid, i, j, box, 0, 0);
				cells[i][j] = cell;
				uid++;
			}
		}
	}

	/**
	 * Abhängig von der Übergebenen Zahl werden zufällige Zahl des Arrays auf 0
	 * gesetzt
	 */
	public void difficulty(int diff) {
		for (int row = 0; row < this.cells.length; row++) {
			for (int j = 0; j < this.cells[row].length; j++) {
				int random = (int) (Math.random() * 10) + 1;
				if (random <= diff) {
					this.cells[row][j].setIsReal(true);
				} else {
					this.cells[row][j].setValue(0);
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
	public Gamestate getGameState() {
		return this.gamestate;
	}

	@Override
	public boolean testIfSolved() {
		for(int row = 0; row < this.cells.length; row++) {
			for(int col = 0; col < this.cells[row].length; col++) {
				if(this.cells[row][col].getValue() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	// muss noch besprochen werden in welche klasse die methoden gehören

	// updatet den stand des sudoku arrays auf den stand des logik arrays, nach
	// autosolve immer?

}