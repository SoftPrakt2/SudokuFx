package logic;

import application.SudokuField;

public class SamuraiLogic extends BasicGameLogic {

	private Cell[][] cells;
//	private SudokuLogic topLeft;
//	private SudokuLogic topRight;
//	private SudokuLogic center;
//	private SudokuLogic bottomLeft;
//	private SudokuLogic bottomRight;
	public static int counter = 0;

	private SudokuLogic topLeft = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
	private SudokuLogic topRight = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
	private SudokuLogic center = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
	private SudokuLogic bottomLeft = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
	private SudokuLogic bottomRight = new SudokuLogic(Gamestate.OPEN, 0, 0, false);

	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		this.cells = new Cell[21][21];

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
		// TODO Auto-generated method stub
		topLeft.setUpLogicArray();
		topRight.setUpLogicArray();
		center.setUpLogicArray();
		bottomLeft.setUpLogicArray();
		bottomRight.setUpLogicArray();

		int box = 1;
		int uid = 0;
		for (int i = 0; i < this.cells.length; i++) {
			for (int j = 0; j < this.cells[i].length; j++) {
				box++;
				Cell cell = new Cell(i, j, box, 0, 0, -1);
				cells[i][j] = cell;
				uid++;
			}
		}
	}

	@Override
	public boolean createSudoku() {
		center.createSudoku();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				topLeft.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				topRight.getCells()[row + 6][col].setValue(center.getCells()[row][col + 6].getValue());
				bottomLeft.getCells()[row][col + 6].setValue(center.getCells()[row + 6][col].getValue());
				bottomRight.getCells()[row][col].setValue(center.getCells()[row + 6][col + 6].getValue());
			}
		}
		topLeft.createSudoku();
		topRight.createSudoku();
		bottomLeft.createSudoku();
		bottomRight.createSudoku();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
			}
		}
		return true;
	}

	@Override
	public int[] hint() {
		// TODO Auto-generated method stub
		boolean correctRandom = false;
		int randomRow = 0;
		int randomCol = 0;
		int randomHint = 0;
		int[] helpForHint = new int[2];
		int random = (int) (Math.random() * 5) + 1;
		int[] returnCoordinates = new int[2];

		
		
		while (!correctRandom) {
			random = (int) (Math.random() * 5) + 1;
			if (random == 1) {
				helpForHint = topLeft.hint();
				if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] > 5 && helpForHint[1] < 9
						&& center.valid(helpForHint[0] - 6, helpForHint[1] - 6,
								topLeft.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
//					topLeft.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
					topLeft.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					center.getCells()[helpForHint[0] - 6][helpForHint[1] - 6]
							.setValue(topLeft.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				break;
			}
			if (random == 2) {
				helpForHint = topRight.hint();
				if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] >= 0 && helpForHint[1] < 3
						&& center.valid(helpForHint[0] - 6, helpForHint[1] + 6,
								topRight.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
//					topRight.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					center.getCells()[helpForHint[0] - 6][helpForHint[1] + 6]
							.setValue(topRight.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				break;
			}
			if (random == 3) {
				helpForHint = center.hint();
				if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] >= 0 && helpForHint[1] < 3
						&& topLeft.valid(helpForHint[0] + 6, helpForHint[1] + 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					topLeft.getCells()[helpForHint[0] + 6][helpForHint[1] + 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] > 5 && helpForHint[1] < 9
						&& topRight.valid(helpForHint[0] + 6, helpForHint[1] - 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					topRight.getCells()[helpForHint[0] + 6][helpForHint[1] - 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] >= 0 && helpForHint[1] < 3
						&& bottomLeft.valid(helpForHint[0] - 6, helpForHint[1] + 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					bottomLeft.getCells()[helpForHint[0] - 6][helpForHint[1] + 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] > 5 && helpForHint[1] < 9
						&& bottomRight.valid(helpForHint[0] - 6, helpForHint[1] - 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					bottomRight.getCells()[helpForHint[0] - 6][helpForHint[1] - 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				break;
			}
			if (random == 4) {
				helpForHint = bottomLeft.hint();
				if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] > 5 && helpForHint[1] < 9
						&& center.valid(helpForHint[0] + 6, helpForHint[1] - 6,
								bottomLeft.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
					bottomLeft.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					center.getCells()[helpForHint[0] + 6][helpForHint[1] - 6]
							.setValue(bottomLeft.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				break;
			}
			if (random == 5) {
				helpForHint = bottomRight.hint();
				if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] >= 0 && helpForHint[1] < 3
						&& center.valid(helpForHint[0] + 6, helpForHint[1] + 6,
								bottomRight.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
					bottomRight.getCells()[helpForHint[0]][helpForHint[1]].setValue(random);
					center.getCells()[helpForHint[0] + 6][helpForHint[1] + 6]
							.setValue(bottomRight.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				break;
			}
			correctRandom = true;
		}

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
			}
		}
		return returnCoordinates;
	}

	@Override
	public boolean solveSudoku() {
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if (this.cells[row][col].getValue() == 0) {
					for (int y = 0; y < 9; y++) {
						counter++;
//						if(counter == 10000000) {
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
		
//		center.solveSudoku();
//		for (int row = 0; row < 3; row++) {
//			for (int col = 0; col < 3; col++) {
//				topLeft.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
//				topRight.getCells()[row + 6][col].setValue(center.getCells()[row][col + 6].getValue());
//				bottomLeft.getCells()[row][col + 6].setValue(center.getCells()[row + 6][col].getValue());
//				bottomRight.getCells()[row][col].setValue(center.getCells()[row + 6][col + 6].getValue());
//			}
//		}
//		topLeft.solveSudoku();
//		topRight.solveSudoku();
//		bottomLeft.solveSudoku();
//		bottomRight.solveSudoku();
//
//		for (int row = 0; row < 21; row++) {
//			for (int col = 0; col < 21; col++) {
//				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
////				this.setCell(row, col, this.topLeft.getCells()[row][col].getValue());
//				
//				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
////				this.setCell(row, col + 12, this.topRight.getCells()[row][col].getValue());
//				
//				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
////				this.setCell(row + 6, col + 6, this.center.getCells()[row][col].getValue());
//				
//				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
////				this.setCell(row + 12, col, this.bottomLeft.getCells()[row][col].getValue());
//				
//				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
////				this.setCell(row + 12, col + 12, this.bottomRight.getCells()[row][col].getValue());
//			}
//		}
//		return true;
	}

	@Override
	public void difficulty(int diff) {
		// TODO Auto-generated method stub
		topLeft.difficulty(diff);
		topRight.difficulty(diff);
		center.difficulty(diff);
		bottomLeft.difficulty(diff);
		bottomRight.difficulty(diff);

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
//				this.setCell(row, col, this.topLeft.getCells()[row][col].getValue());

				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
//				this.setCell(row, col + 12, this.topRight.getCells()[row][col].getValue());

				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
//				this.setCell(row + 6, col + 6, this.center.getCells()[row][col].getValue());

				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
//				this.setCell(row + 12, col, this.bottomLeft.getCells()[row][col].getValue());

				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
//				this.setCell(row + 12, col + 12, this.bottomRight.getCells()[row][col].getValue());
			}
		}
		for (int row = 0; row < 21; row++) {
			for (int col = 0; col < 21; col++) {
				if (this.cells[row][col].getValue() > 0) {
					this.cells[row][col].setIsReal(true);
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
		return null;
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
