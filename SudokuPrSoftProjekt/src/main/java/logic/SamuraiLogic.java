package logic;

import application.SudokuField;

public class SamuraiLogic extends BasicGameLogic {


	public static int counter = 0;
	public static int N = 21;

	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		cells = new Cell[21][21];
		gameType = "Samurai";
		hintCounter = 10;
	}
	

	@Override
	public boolean checkRow(int row, int col, int guess) {
		// ---------------------topLeft---------------------
		if (row < 9 && col < 9) {
			if (row > 5 && col > 5) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 0; col < 9; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			if (row > 5 && col < 15) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 12; col < 21; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			if (row < 15 && col > 5) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 0; col < 9; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			if (row < 15 && col < 15) {
				for (col = 6; col < 15; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 12; col < 21; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			for (col = 6; col < 15; col++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkCol(int row, int col, int guess) {
		if (row < 9 && col < 9) {
			if (row > 5 && col > 5) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 0; row < 9; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			if (row > 5 && col < 15) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 0; row < 9; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			if (row < 15 && col > 5) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 12; row < 21; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			if (row < 15 && col < 15) {
				for (row = 6; row < 15; row++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 12; row < 21; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			for (row = 6; row < 15; row++) {
				if (this.cells[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean checkBox(int row, int col, int guess) {
		if (row < 9 && col < 9) {
			int r = row - row % 3;
			int c = col - col % 3;

			for (row = r; row < r + 3; row++) {
				for (col = c; col < c + 3; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
		}
		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			int r = row - row % 3;
			int c = col - col % 3;

			for (row = r; row < r + 3; row++) {
				for (col = c; col < c + 3; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			int r = row - row % 3;
			int c = col - col % 3;

			for (row = r; row < r + 3; row++) {
				for (col = c; col < c + 3; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			int r = row - row % 3;
			int c = col - col % 3;

			for (row = r; row < r + 3; row++) {
				for (col = c; col < c + 3; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			int r = row - row % 3;
			int c = col - col % 3;

			for (row = r; row < r + 3; row++) {
				for (col = c; col < c + 3; col++) {
					if (this.cells[row][col].getValue() == guess) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean valid(int row, int col, int guess) {
		if (this.checkRow(row, col, guess) && this.checkCol(row, col, guess) && this.checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	@Override
	public void setUpLogicArray() {
		int box = 1;
		int uid = 0;
		Cell cell;
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (row < 6 && col > 8 && col < 12) {
					cell = new Cell(row, col, box, 0, 0, -1);
					cells[row][col] = cell;
				}
				else if (row > 8 && row < 12 && col < 6) {
					cell = new Cell(row, col, box, 0, 0, -1);
					cells[row][col] = cell;
				}
				else if (row > 8 && row < 12 && col > 14) {
					cell = new Cell(row, col, box, 0, 0, -1);
					cells[row][col] = cell;
				}
				else if (row > 14 && col > 8 && col < 12) {
					cell = new Cell(row, col, box, 0, 0, -1);
					cells[row][col] = cell;
				}
				else {
					cell = new Cell(row, col, box, 0, 0, 0);
					cells[row][col] = cell;
				}
				box++;
				uid++;
			}
		}
	}

	@Override
	public boolean createSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < 9; y++) {
						int a = (int)(Math.random() * 9) + 1;				
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

	@Override
	public int[] hint() {
		// TODO Auto-generated method stub
		boolean correctRandom = false;
		int randomRow = 0;
		int randomCol = 0;
		int randomNumber = 0;
		int [] coordinates = new int[2];

		int help[][] = new int[21][21];
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				help[row][col] = this.cells[row][col].getValue();
			}
		}

		this.solveSudoku();

		while (!correctRandom) {
			randomRow = (int) (Math.random() * 20) + 0;
			randomCol = (int) (Math.random() * 20) + 0;
			randomNumber = (int) (Math.random() * 9) + 1;
			if (this.cells[randomRow][randomCol].getValue() == randomNumber && help[randomRow][randomCol] == 0) {
				help[randomRow][randomCol] = randomNumber;
				coordinates[0] = randomRow;
				coordinates[1] = randomCol; 
				correctRandom = true;
			}
		}

		for (int row = 0; row < 21; row++) {
			for (int col = 0; col < 21; col++) {
				this.cells[row][col].setValue(help[row][col]);
			}
		}
		return coordinates;
	}

//	@Override
//	public boolean solveSudoku(Cell [][] cells) {
//		
//		int row = 0;
//		   int  col = 0;
//		    boolean checkBlankSpaces = false;
//
//		    /* verify if sudoku is already solved and if not solved,
//		    get next "blank" space position */ 
//		    for (row = 0; row < this.cells.length; row++) {
//		        for (col = 0; col < this.cells[row].length; col++) {
//		            if (this.cells[row][col].getValue() == 0) {
//		                checkBlankSpaces = true;
//		                break;
//		            }
//		        }
//		        if (checkBlankSpaces == true) {
//		            break;
//		        }
//		    }
//		    // no more "blank" spaces means the puzzle is solved
//		    if (checkBlankSpaces == false) {
//		        return true;
//		    }
//
//		    // try to fill "blank" space with correct num
//		    for (int num = 1; num <= 9; num++) {
//		        /* isSafe checks that num isn't already present 
//		        in the row, column, or 3x3 box (see below) */ 
//		        if (this.valid(row, col, num)) {
//		            cells[row][col].setValue(num);
//
//		            if (solveSudoku(cells)) {
//		                return true;
//		            }
//
//		            /* if num is placed in incorrect position, 
//		            mark as "blank" again then backtrack with 
//		            a different num */ 
//		            cells[row][col].setValue(0);;
//		        }
//		    }
//		    return false;
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
//	@Override
//	public boolean solveSudoku() {
//		for (int row = 0; row < this.cells.length; row++) {
//			for (int col = 0; col < this.cells[row].length; col++) {
//				if (this.cells[row][col].getValue() == 0) {
//					for (int y = 0; y < 9; y++) {
//						counter++;
////						if(counter == 10000000) {
////							return true;
////						}
//					
//						if (valid(row, col, y + 1)) {
//							this.cells[row][col].setValue(y + 1);
//							if (solveSudoku()) {
//								return true;
//							} else {
//								this.cells[row][col].setValue(0);
//							}
//						}
//					}
//					return false;
//				}
//			}
//		}
//		System.out.println(counter);
//		return true;
//	}

	public boolean solveSudoku() {
		int row = 0;
		int col = 0;
		boolean checkBlankSpaces = false;

		/*
		 * verify if sudoku is already solved and if not solved, get next "blank" space
		 * position
		 */
		for (row = 0; row < this.cells.length; row++) {
			for (col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					checkBlankSpaces = true;
					break;
				}
			}
			if (checkBlankSpaces == true) {
				break;
			}
		}
		// no more "blank" spaces means the puzzle is solved
		if (checkBlankSpaces == false) {
			return true;
		}

		// try to fill "blank" space with correct num
		for (int num = 1; num <= 9; num++) {
			/*
			 * isSafe checks that num isn't already present in the row, column, or 3x3 box
			 * (see below)
			 */
			counter++;
			if (this.valid(row, col, num)) {
				this.cells[row][col].setValue(num);

				if (solveSudoku()) {
					return true;
				}

				/*
				 * if num is placed in incorrect position, mark as "blank" again then backtrack
				 * with a different num
				 */
				this.cells[row][col].setValue(0);
				;
			}
		}
//		System.out.println(counter);
		return false;
	}

	@Override
	public void difficulty() {
		int diff = this.difficulty;
		// TODO Auto-generated method stub
		for(int row = 0; row < this.cells.length; row++) {
			for(int col = 0; col < this.cells[row].length; col++) {
				if(this.cells[row][col].getValue() != -1) {
					int random = (int) (Math.random() * 10) + 1;
					if(random <= diff) {
						this.cells[row][col].setIsReal(true);
					}
					else {
						this.cells[row][col].setValue(0);;
					}
				}
			}
		}
		
	}

	@Override
	public void printCells() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == -1) {
					System.out.print("- ");
				} else {
					System.out.print(this.cells[row][col].getValue() + " ");
				}
			}
			System.out.println();
		}
	}

	@Override
	public Cell[][] getCells() {
		return this.cells;
	}

	@Override
	public void setCell(int row, int col, int guess) {
		this.cells[row][col].setValue(guess);
		this.cells[row][col].setIsReal(true);
	}

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
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					return false;
				}
			}
		}
		return true;
	}

}
