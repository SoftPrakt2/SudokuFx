package logic;

import application.SudokuField;

public class SamuraiLogic extends BasicGameLogic {

	private Cell[][] cells;
	private SudokuLogic topLeft;
	private SudokuLogic topRight;
	private SudokuLogic center;
	private SudokuLogic bottomLeft;
	private SudokuLogic bottomRight;
	private static int counter = 0;

	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
        super(gamestate, minutesPlayed,secondsPlayed, isCorrect);
        this.cells = new Cell[21][21];
        topLeft = new SudokuLogic(Gamestate.OPEN, 0,0, false);
        topRight = new SudokuLogic(Gamestate.OPEN, 0,0, false);
        center = new SudokuLogic(Gamestate.OPEN, 0,0, false);
        bottomLeft = new SudokuLogic(Gamestate.OPEN, 0,0, false);
        bottomRight = new SudokuLogic(Gamestate.OPEN, 0,0, false);
    }

	@Override
	public boolean checkRow(int row, int guess) {
		// TODO Auto-generated method stub
//		if (row < 6) {
//			for (int col = 0; col < 9; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					System.out.println("test");
//					return false;
//				}
//			}
//			for (int col = 12; col < 21; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (row >= 6 && row < 9) {
//			for (int col = 0; col < 9; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int col = 6; col < 15; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int col = 15; col < 21; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (row > 8 && row < 12) {
//			for (int col = 9; col < 12; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (row >= 12 && row < 15) {
//			for (int col = 0; col < 9; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int col = 6; col < 15; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int col = 15; col < 21; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (row > 14) {
//			for (int col = 0; col < 9; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int col = 12; col < 21; col++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
		return false;
	}

	@Override
	public boolean checkCol(int col, int guess) {
		// TODO Auto-generated method stub
//		if (col < 6) {
//			for (int row = 0; row < 9; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int row = 12; row < 21; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (col >= 6 && col < 9) {
//			for (int row = 0; row < 9; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int row = 6; row < 15; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int row = 15; row < 21; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (col > 8 && col < 12) {
//			for (int row = 9; row < 12; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (col >= 12 && col < 15) {
//			for (int row = 0; row < 9; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int row = 6; row < 15; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int row = 15; row < 21; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
//
//		if (col > 14) {
//			for (int row = 0; row < 9; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//			for (int row = 12; row < 21; row++) {
//				if (this.cells[row][col].getValue() == guess) {
//					return false;
//				}
//			}
//		}
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
//		if(row >= 6 && row < 9 && col >= 6 && col < 9) {
//			if(this.topLeft.valid(row, col, guess)
//					&& this.center.valid(row - 6, col - 6, guess)){
//				return true;
//			}
//		}
//		else if(row >= 0 && row < 9 && col >= 0 && col < 9) {
//			if(this.topLeft.valid(row, col, guess)) {
//				return true;
//			}
//		}
//		else if(row >= 6 && row < 9 && col >= 12 && col < 15) {
//			if(this.topRight.valid(row, col - 12, guess) 
//					&& this.center.valid(row, col - 6, guess)) {
//				return true;
//			}
//		}
//		else if(row >= 0 && row < 9 && col >= 12 && col < 21) {
//			if(this.topRight.valid(row, col - 12, guess)) {
//				return true;
//			}
//		}
//		else if(row >= 6 && row < 15 && col >= 6 && col < 15) {
//			if(this.center.valid(row - 6, col - 6, guess)) {
//				
//			}
//		}
//		else if(row >= 12 && row < 15 && col >= 6 && col < 9) {
//			if(this.bottomLeft.valid(row - 12, col, guess)
//					&& this.center.valid(row - 6, col - 6, guess)) {
//				return true;
//			}
//		}
//		else if(row >= 12 && row < 21 && col >= 0 && col < 9) {
//			if(this.bottomLeft.valid(row - 12, col, guess)) {
//				return true;
//			}
//		}
//		else if(row >= 12 && row < 15 && col >= 12 && col < 15) {
//			if(this.bottomRight.valid(row - 12, col - 12, guess)
//					&& this.center.valid(row - 6, col - 6, guess)) {
//				return true;
//			}
//		}
//		else if(row >= 12 && row < 21 && col >= 12 && col < 121) {
//			if(this.bottomRight.valid(row - 12, col - 12, guess)) {
//				return true;
//			}
//		}
		return true;
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
//					topRight.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
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
//					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
					topLeft.getCells()[helpForHint[0] + 6][helpForHint[1] + 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				if (helpForHint[0] >= 0 && helpForHint[0] < 3 && helpForHint[1] > 5 && helpForHint[1] < 9
						&& topRight.valid(helpForHint[0] + 6, helpForHint[1] - 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
//					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
					topRight.getCells()[helpForHint[0] + 6][helpForHint[1] - 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] >= 0 && helpForHint[1] < 3
						&& bottomLeft.valid(helpForHint[0] - 6, helpForHint[1] + 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
//					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
					bottomLeft.getCells()[helpForHint[0] - 6][helpForHint[1] + 6]
							.setValue(center.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				if (helpForHint[0] > 5 && helpForHint[0] < 9 && helpForHint[1] > 5 && helpForHint[1] < 9
						&& bottomRight.valid(helpForHint[0] - 6, helpForHint[1] - 6,
								center.getCells()[helpForHint[0]][helpForHint[1]].getValue())) {
//					center.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
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
//					bottomLeft.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
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
//					bottomRight.getCells()[helpForHint[0]][helpForHint[1]].setValue(0);
					center.getCells()[helpForHint[0] + 6][helpForHint[1] + 6]
							.setValue(bottomRight.getCells()[helpForHint[0]][helpForHint[1]].getValue());
					
				}
				break;
			}
			correctRandom = true;
		}
		
//		topLeft.printCells();
//		topLeft.hint();
//		topLeft.printCells();
		
		
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
		return null;
	}

	@Override
	public boolean solveSudoku() {		
		center.solveSudoku();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				topLeft.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				topRight.getCells()[row + 6][col].setValue(center.getCells()[row][col + 6].getValue());
				bottomLeft.getCells()[row][col + 6].setValue(center.getCells()[row + 6][col].getValue());
				bottomRight.getCells()[row][col].setValue(center.getCells()[row + 6][col + 6].getValue());
			}
		}
		topLeft.solveSudoku();
		topRight.solveSudoku();
		bottomLeft.solveSudoku();
		bottomRight.solveSudoku();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
//				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
				this.setCell(row, col, this.topLeft.getCells()[row][col].getValue());
				
//				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
				this.setCell(row, col + 12, this.topRight.getCells()[row][col].getValue());
				
//				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				this.setCell(row + 6, col + 6, this.center.getCells()[row][col].getValue());
				
//				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
				this.setCell(row + 12, col, this.bottomLeft.getCells()[row][col].getValue());
				
//				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
				this.setCell(row + 12, col + 12, this.bottomRight.getCells()[row][col].getValue());
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
//				this.getCells()[row][col].setValue(topLeft.getCells()[row][col].getValue());
				this.setCell(row, col, this.topLeft.getCells()[row][col].getValue());
				
//				this.getCells()[row][col + 12].setValue(topRight.getCells()[row][col].getValue());
				this.setCell(row, col + 12, this.topRight.getCells()[row][col].getValue());
				
//				this.getCells()[row + 6][col + 6].setValue(center.getCells()[row][col].getValue());
				this.setCell(row + 6, col + 6, this.center.getCells()[row][col].getValue());
				
//				this.getCells()[row + 12][col].setValue(bottomLeft.getCells()[row][col].getValue());
				this.setCell(row + 12, col, this.bottomLeft.getCells()[row][col].getValue());
				
//				this.getCells()[row + 12][col + 12].setValue(bottomRight.getCells()[row][col].getValue());
				this.setCell(row + 12, col + 12, this.bottomRight.getCells()[row][col].getValue());
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
		// TODO Auto-generated method stub
		return false;
	}
}
