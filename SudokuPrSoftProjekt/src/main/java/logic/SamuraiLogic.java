package logic;

import application.SudokuField;

public class SamuraiLogic extends BasicGameLogic {

	private Cell[][] cells;
	private SudokuLogic topLeft;
	private SudokuLogic topRight;
	private SudokuLogic center;
	private SudokuLogic bottomLeft;
	private SudokuLogic bottomRight;

	public SamuraiLogic(Gamestate gamestate, double timer, boolean isCorrect) {
		super(gamestate, timer, isCorrect);
		this.cells = new Cell[21][21];
		topLeft = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		topRight = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		center = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		bottomLeft = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		bottomRight = new SudokuLogic(Gamestate.OPEN, 0.0, false);
	}

	@Override
	public boolean checkRow(int row, int guess) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkCol(int col, int guess) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkBox(int row, int col, int guess) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(int row, int col, int guess) {
		// TODO Auto-generated method stub
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
				Cell cell = new Cell(uid, i, j, box, 0, -1);
				cells[i][j] = cell;
				uid++;
			}
		}
	}

	@Override
	public boolean createSudoku() {
		// TODO Auto-generated method stub

		topLeft.createSudoku();
		center.getCells()[0][0].setValue(topLeft.getCells()[6][6].getValue());
		center.getCells()[0][1].setValue(topLeft.getCells()[6][7].getValue());
		center.getCells()[0][2].setValue(topLeft.getCells()[6][8].getValue());
		center.getCells()[1][0].setValue(topLeft.getCells()[7][6].getValue());
		center.getCells()[1][1].setValue(topLeft.getCells()[7][7].getValue());
		center.getCells()[1][2].setValue(topLeft.getCells()[7][8].getValue());
		center.getCells()[2][0].setValue(topLeft.getCells()[8][6].getValue());
		center.getCells()[2][1].setValue(topLeft.getCells()[8][7].getValue());
		center.getCells()[2][2].setValue(topLeft.getCells()[8][8].getValue());

//	center.setCell(0, 0, topLeft.getCells()[6][6].getValue());
//	center.setCell(0, 1, topLeft.getCells()[6][7].getValue());
//	center.setCell(0, 2, topLeft.getCells()[6][8].getValue());
//	center.setCell(1, 0, topLeft.getCells()[7][6].getValue());
//	center.setCell(1, 1, topLeft.getCells()[7][7].getValue());
//	center.setCell(1, 2, topLeft.getCells()[7][8].getValue());
//	center.setCell(2, 0, topLeft.getCells()[8][6].getValue());
//	center.setCell(2, 1, topLeft.getCells()[8][7].getValue());
//	center.setCell(2, 2, topLeft.getCells()[8][8].getValue());
		topLeft.printCells();

		center.createSudoku();
		topRight.getCells()[6][0].setValue(center.getCells()[0][6].getValue());
		topRight.getCells()[6][1].setValue(center.getCells()[0][7].getValue());
		topRight.getCells()[6][2].setValue(center.getCells()[0][8].getValue());
		topRight.getCells()[7][0].setValue(center.getCells()[1][6].getValue());
		topRight.getCells()[7][1].setValue(center.getCells()[1][7].getValue());
		topRight.getCells()[7][2].setValue(center.getCells()[1][8].getValue());
		topRight.getCells()[8][0].setValue(center.getCells()[2][6].getValue());
		topRight.getCells()[8][1].setValue(center.getCells()[2][7].getValue());
		topRight.getCells()[8][2].setValue(center.getCells()[2][8].getValue());

//	topRight.setCell(6, 0, center.getCells()[0][6].getValue());
//	topRight.setCell(6, 1, center.getCells()[0][7].getValue());
//	topRight.setCell(6, 2, center.getCells()[0][8].getValue());
//	topRight.setCell(7, 0, center.getCells()[1][6].getValue());
//	topRight.setCell(7, 1, center.getCells()[1][7].getValue());
//	topRight.setCell(7, 2, center.getCells()[1][8].getValue());
//	topRight.setCell(8, 0, center.getCells()[2][6].getValue());
//	topRight.setCell(8, 1, center.getCells()[2][7].getValue());
//	topRight.setCell(8, 2, center.getCells()[2][8].getValue());
		center.printCells();

		topRight.createSudoku();
		bottomLeft.getCells()[0][6].setValue(center.getCells()[6][0].getValue());
		bottomLeft.getCells()[0][7].setValue(center.getCells()[6][1].getValue());
		bottomLeft.getCells()[0][8].setValue(center.getCells()[6][2].getValue());
		bottomLeft.getCells()[1][6].setValue(center.getCells()[7][0].getValue());
		bottomLeft.getCells()[1][7].setValue(center.getCells()[7][1].getValue());
		bottomLeft.getCells()[1][8].setValue(center.getCells()[7][2].getValue());
		bottomLeft.getCells()[2][6].setValue(center.getCells()[8][0].getValue());
		bottomLeft.getCells()[2][7].setValue(center.getCells()[8][1].getValue());
		bottomLeft.getCells()[2][8].setValue(center.getCells()[8][2].getValue());

//	bottomLeft.setCell(0, 6, center.getCells()[6][0].getValue());
//	bottomLeft.setCell(0, 7, center.getCells()[6][1].getValue());
//	bottomLeft.setCell(0, 8, center.getCells()[6][2].getValue());
//	bottomLeft.setCell(1, 6, center.getCells()[7][0].getValue());
//	bottomLeft.setCell(1, 7, center.getCells()[7][1].getValue());
//	bottomLeft.setCell(1, 8, center.getCells()[7][2].getValue());
//	bottomLeft.setCell(2, 6, center.getCells()[8][0].getValue());
//	bottomLeft.setCell(2, 7, center.getCells()[8][1].getValue());
//	bottomLeft.setCell(2, 8, center.getCells()[8][2].getValue());
		topRight.printCells();

		bottomLeft.createSudoku();
		bottomRight.getCells()[0][0].setValue(center.getCells()[6][6].getValue());
		bottomRight.getCells()[0][1].setValue(center.getCells()[6][7].getValue());
		bottomRight.getCells()[0][2].setValue(center.getCells()[6][8].getValue());
		bottomRight.getCells()[1][0].setValue(center.getCells()[7][6].getValue());
		bottomRight.getCells()[1][1].setValue(center.getCells()[7][7].getValue());
		bottomRight.getCells()[1][2].setValue(center.getCells()[7][8].getValue());
		bottomRight.getCells()[2][0].setValue(center.getCells()[8][6].getValue());
		bottomRight.getCells()[2][1].setValue(center.getCells()[8][7].getValue());
		bottomRight.getCells()[2][2].setValue(center.getCells()[8][8].getValue());

//	bottomRight.setCell(0, 0, center.getCells()[6][6].getValue());
//	bottomRight.setCell(0, 1, center.getCells()[6][7].getValue());
//	bottomRight.setCell(0, 2, center.getCells()[6][8].getValue());
//	bottomRight.setCell(1, 0, center.getCells()[7][6].getValue());
//	bottomRight.setCell(1, 1, center.getCells()[7][7].getValue());
//	bottomRight.setCell(1, 2, center.getCells()[7][8].getValue());
//	bottomRight.setCell(2, 0, center.getCells()[8][6].getValue());
//	bottomRight.setCell(2, 1, center.getCells()[8][7].getValue());
//	bottomRight.setCell(2, 2, center.getCells()[8][8].getValue());
		bottomLeft.printCells();

		bottomRight.createSudoku();
		bottomRight.printCells();

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
		int[] helpForHint = new int[2];
		int random = (int) (Math.random() * 5) + 1;

		while (!correctRandom) {
			random = (int) (Math.random() * 5) + 1;
			if (random == 1) {
				helpForHint = topLeft.hint();
					if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] > 5 && helpForHint[1] < 9
							&& center.valid(helpForHint[0] - 6, helpForHint[1] - 6,
									topLeft.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						topLeft.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					break;
			}
			if (random == 2) {
				helpForHint = topRight.hint();
					if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] >= 0 && helpForHint[1] < 3
							&& center.valid(helpForHint[0] - 6, helpForHint[1] + 6,
									topRight.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						topRight.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					break;
			}
			if (random == 3) {
				helpForHint = center.hint();
					if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] >= 0 && helpForHint[1] < 3
							&& topLeft.valid(helpForHint[0] + 6, helpForHint[1] + 6,
									center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] > 5 && helpForHint[1] < 9
							&& topRight.valid(helpForHint[0] + 6, helpForHint[1] - 6,
									center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] >= 0 && helpForHint[1] < 3
							&& bottomLeft.valid(helpForHint[0] - 6, helpForHint[1] + 6,
									center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] > 5 && helpForHint[1] < 9
							&& bottomRight.valid(helpForHint[0] - 6, helpForHint[1] - 6,
									center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					break;
			}
			if (random == 4) {
				helpForHint = bottomLeft.hint();
					if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] > 5 && helpForHint[1] < 9
							&& center.valid(helpForHint[0] + 6, helpForHint[1] - 6,
									bottomLeft.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						bottomLeft.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					break;
			}
			if (random == 5) {
				helpForHint = bottomRight.hint();
					if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] >= 0 && helpForHint[1] < 3
							&& center.valid(helpForHint[0] + 6, helpForHint[1] + 6,
									bottomRight.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
						bottomRight.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
						correctRandom = true;
					}
					break;
				}
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
		return null;
	}

	@Override
	public boolean solveSudoku() {
		topLeft.solveSudoku();
		center.getCells()[0][0].setValue(topLeft.getCells()[6][6].getValue());
		center.getCells()[0][1].setValue(topLeft.getCells()[6][7].getValue());
		center.getCells()[0][2].setValue(topLeft.getCells()[6][8].getValue());
		center.getCells()[1][0].setValue(topLeft.getCells()[7][6].getValue());
		center.getCells()[1][1].setValue(topLeft.getCells()[7][7].getValue());
		center.getCells()[1][2].setValue(topLeft.getCells()[7][8].getValue());
		center.getCells()[2][0].setValue(topLeft.getCells()[8][6].getValue());
		center.getCells()[2][1].setValue(topLeft.getCells()[8][7].getValue());
		center.getCells()[2][2].setValue(topLeft.getCells()[8][8].getValue());

//	center.setCell(0, 0, topLeft.getCells()[6][6].getValue());
//	center.setCell(0, 1, topLeft.getCells()[6][7].getValue());
//	center.setCell(0, 2, topLeft.getCells()[6][8].getValue());
//	center.setCell(1, 0, topLeft.getCells()[7][6].getValue());
//	center.setCell(1, 1, topLeft.getCells()[7][7].getValue());
//	center.setCell(1, 2, topLeft.getCells()[7][8].getValue());
//	center.setCell(2, 0, topLeft.getCells()[8][6].getValue());
//	center.setCell(2, 1, topLeft.getCells()[8][7].getValue());
//	center.setCell(2, 2, topLeft.getCells()[8][8].getValue());
		topLeft.printCells();

		center.solveSudoku();
		topRight.getCells()[6][0].setValue(center.getCells()[0][6].getValue());
		topRight.getCells()[6][1].setValue(center.getCells()[0][7].getValue());
		topRight.getCells()[6][2].setValue(center.getCells()[0][8].getValue());
		topRight.getCells()[7][0].setValue(center.getCells()[1][6].getValue());
		topRight.getCells()[7][1].setValue(center.getCells()[1][7].getValue());
		topRight.getCells()[7][2].setValue(center.getCells()[1][8].getValue());
		topRight.getCells()[8][0].setValue(center.getCells()[2][6].getValue());
		topRight.getCells()[8][1].setValue(center.getCells()[2][7].getValue());
		topRight.getCells()[8][2].setValue(center.getCells()[2][8].getValue());

//	topRight.setCell(6, 0, center.getCells()[0][6].getValue());
//	topRight.setCell(6, 1, center.getCells()[0][7].getValue());
//	topRight.setCell(6, 2, center.getCells()[0][8].getValue());
//	topRight.setCell(7, 0, center.getCells()[1][6].getValue());
//	topRight.setCell(7, 1, center.getCells()[1][7].getValue());
//	topRight.setCell(7, 2, center.getCells()[1][8].getValue());
//	topRight.setCell(8, 0, center.getCells()[2][6].getValue());
//	topRight.setCell(8, 1, center.getCells()[2][7].getValue());
//	topRight.setCell(8, 2, center.getCells()[2][8].getValue());
		center.printCells();

		topRight.solveSudoku();
		bottomLeft.getCells()[0][6].setValue(center.getCells()[6][0].getValue());
		bottomLeft.getCells()[0][7].setValue(center.getCells()[6][1].getValue());
		bottomLeft.getCells()[0][8].setValue(center.getCells()[6][2].getValue());
		bottomLeft.getCells()[1][6].setValue(center.getCells()[7][0].getValue());
		bottomLeft.getCells()[1][7].setValue(center.getCells()[7][1].getValue());
		bottomLeft.getCells()[1][8].setValue(center.getCells()[7][2].getValue());
		bottomLeft.getCells()[2][6].setValue(center.getCells()[8][0].getValue());
		bottomLeft.getCells()[2][7].setValue(center.getCells()[8][1].getValue());
		bottomLeft.getCells()[2][8].setValue(center.getCells()[8][2].getValue());

//	bottomLeft.setCell(0, 6, center.getCells()[6][0].getValue());
//	bottomLeft.setCell(0, 7, center.getCells()[6][1].getValue());
//	bottomLeft.setCell(0, 8, center.getCells()[6][2].getValue());
//	bottomLeft.setCell(1, 6, center.getCells()[7][0].getValue());
//	bottomLeft.setCell(1, 7, center.getCells()[7][1].getValue());
//	bottomLeft.setCell(1, 8, center.getCells()[7][2].getValue());
//	bottomLeft.setCell(2, 6, center.getCells()[8][0].getValue());
//	bottomLeft.setCell(2, 7, center.getCells()[8][1].getValue());
//	bottomLeft.setCell(2, 8, center.getCells()[8][2].getValue());
		topRight.printCells();

		bottomLeft.solveSudoku();
		bottomRight.getCells()[0][0].setValue(center.getCells()[6][6].getValue());
		bottomRight.getCells()[0][1].setValue(center.getCells()[6][7].getValue());
		bottomRight.getCells()[0][2].setValue(center.getCells()[6][8].getValue());
		bottomRight.getCells()[1][0].setValue(center.getCells()[7][6].getValue());
		bottomRight.getCells()[1][1].setValue(center.getCells()[7][7].getValue());
		bottomRight.getCells()[1][2].setValue(center.getCells()[7][8].getValue());
		bottomRight.getCells()[2][0].setValue(center.getCells()[8][6].getValue());
		bottomRight.getCells()[2][1].setValue(center.getCells()[8][7].getValue());
		bottomRight.getCells()[2][2].setValue(center.getCells()[8][8].getValue());

//	bottomRight.setCell(0, 0, center.getCells()[6][6].getValue());
//	bottomRight.setCell(0, 1, center.getCells()[6][7].getValue());
//	bottomRight.setCell(0, 2, center.getCells()[6][8].getValue());
//	bottomRight.setCell(1, 0, center.getCells()[7][6].getValue());
//	bottomRight.setCell(1, 1, center.getCells()[7][7].getValue());
//	bottomRight.setCell(1, 2, center.getCells()[7][8].getValue());
//	bottomRight.setCell(2, 0, center.getCells()[8][6].getValue());
//	bottomRight.setCell(2, 1, center.getCells()[8][7].getValue());
//	bottomRight.setCell(2, 2, center.getCells()[8][8].getValue());
		bottomLeft.printCells();

		bottomRight.solveSudoku();
		bottomRight.printCells();

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
	public void difficulty(int diff) {
		// TODO Auto-generated method stub
		topLeft.difficulty(diff);
		topRight.difficulty(diff);
		center.difficulty(diff);
		bottomLeft.difficulty(diff);
		bottomRight.difficulty(diff);

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				this.setCell(row, col, topLeft.getCells()[row][col].getValue());
//				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
				this.setCell(row, col + 12, topRight.getCells()[row][col].getValue());
//				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
				this.setCell(row + 6, col + 6, center.getCells()[row][col].getValue());
//				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				this.setCell(row + 12, col, bottomLeft.getCells()[row][col].getValue());
//				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
				this.setCell(row + 12, col + 12, bottomRight.getCells()[row][col].getValue());
//				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
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
		// TODO Auto-generated method stub
		return this.cells;
	}

	@Override
	public void setCell(int col, int row, int guess) {
		// TODO Auto-generated method stub
		this.cells[col][row].setValue(guess);
		this.cells[col][row].setIsReal(true);
	}

	@Override
	public void setGameState(Gamestate gamestate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Gamestate getGameState() {
		// TODO Auto-generated method stub
		return null;
	}
}
