package logic;

import java.util.Random;

public class SamuraiLogic extends BasicGameLogic {

	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super(gamestate, minutesPlayed, secondsPlayed, isCorrect);
		cells = new Cell[21][21];
		gametype = "Samurai";
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
	public void setUpLogicArray() {
		int box = 1;
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				if(!(row < 6 && col > 8 && col < 12) && !(row > 8 && row < 12 && col < 6) &&
						!(row > 8 && row < 12 && col > 14) && !(row > 14 && col > 8 && col < 12)) {
					setCell(row, col, box, 0);
				}
				else {
					setCell(row, col, box, -1);
				}
			}
		}
	}

	@Override
	public void difficulty() {
		int counter = getNumberOfVisibleValues();

        if(counter == 369) {
            removeValues();
        }
        else {
        	Random r = new Random();
        	while (counter != 0) {
        		int randCol = r.nextInt(this.cells.length);
            	int randRow = r.nextInt(this.cells.length);
                if (this.cells[randRow][randCol].getValue() != 0 && this.cells[randRow][randCol].getIsReal()
                        && this.cells[randRow][randCol].getValue() != -1) {
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
			return 230;
		}
		else if(this.difficulty == 5) {
			return 200;
		}
		else if(this.difficulty == 7) {
			return 180;
		}
		else {
			return 369;
		}
	}

//	@Override
//	public void printCells() {
//		for (int row = 0; row < this.cells.length; row++) {
//			for (int col = 0; col < this.cells[row].length; col++) {
//				if (this.cells[row][col].getValue() == -1) {
//					System.out.print("- ");
//				} else {
//					System.out.print(this.cells[row][col].getValue() + " ");
//				}
//			}
//			System.out.println();
//		}
//	}

	@Override
	public boolean isConnected() {
		return false;
	}
}
