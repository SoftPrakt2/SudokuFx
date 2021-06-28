package logic;

/**
 * extends BasicGameLogic
 * implements all abstract methods of BasicGameLogic
 * 
 * @author rafael
 */
public class SamuraiLogic extends BasicGameLogic {

	/**
	 * constructor for creating a Samurai-Object
	 * this constructor extends the BasicGameLogic constructor
	 * the size of the needed Cells-Array gets set with {@link #setCells(Cell[][])}
	 * the game type of the current game gets set with {@link #setGametype(String)}
	 */
	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super(gamestate, minutesPlayed, secondsPlayed);
		setCells(new Cell[21][21]);
		setGametype("Samurai");
		setNumbersToBeSolvable(140);
	//	this.setNumbersNeededToSolve(15);
		this.setSavedResults(new int[this.getCells().length][this.getCells().length]);
	}


	/**
	 * checks if the value already exists in a row
	 * gets called in {@link #valid(int, int, int)}
	 */
	@Override
	public boolean checkRow(int row, int col, int guess) {
		// ---------------------topLeft---------------------
		if (row < 9 && col < 9) {
			if (row > 5 && col > 5) {
				for (col = 6; col < 15; col++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 0; col < 9; col++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			if (row > 5 && col < 15) {
				for (col = 6; col < 15; col++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 12; col < 21; col++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			if (row < 15 && col > 5) {
				for (col = 6; col < 15; col++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 0; col < 9; col++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			if (row < 15 && col < 15) {
				for (col = 6; col < 15; col++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (col = 12; col < 21; col++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			for (col = 6; col < 15; col++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * checks if the value already exists in a column
	 * gets called in {@link #valid(int, int, int)}
	 */
	@Override
	public boolean checkCol(int row, int col, int guess) {
		if (row < 9 && col < 9) {
			if (row > 5 && col > 5) {
				for (row = 6; row < 15; row++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 0; row < 9; row++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------topRight---------------------
		else if (row < 9 && col > 11) {
			if (row > 5 && col < 15) {
				for (row = 6; row < 15; row++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 0; row < 9; row++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomLeft---------------------
		else if (row > 11 && col < 9) {
			if (row < 15 && col > 5) {
				for (row = 6; row < 15; row++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 12; row < 21; row++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		// ---------------------bottomRight---------------------
		else if (row > 11 && col > 11) {
			if (row < 15 && col < 15) {
				for (row = 6; row < 15; row++) {
					if (this.getCells()[row][col].getValue() == guess) {
						return false;
					}
				}
			}
			for (row = 12; row < 21; row++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		else if (row > 5 && row < 15 && col > 5 && col < 15) {
			for (row = 6; row < 15; row++) {
				if (this.getCells()[row][col].getValue() == guess) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * fills the cells-array with values and informations about coordinates
	 * so that the cells-array is not null
	 */
	@Override
	public void setUpLogicArray() {
		int box = 1;
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
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

	/**
	 * removes a certain amount of numbers from the array
	 * the amount depends on the difficulty chosen by the user
	 * 
	 */
	@Override
	public void difficulty() {
		int counter = getNumberOfVisibleValues();

        if(counter == 369) {
            removeValues();
        }
        else {
        	
        	while (counter != 0) {
        		int randCol = r.nextInt(this.getCells().length);
            	int randRow = r.nextInt(this.getCells().length);
                if (this.getCells()[randRow][randCol].getValue() != 0 && this.getCells()[randRow][randCol].getFixedNumber()
                        && this.getCells()[randRow][randCol].getValue() != -1) {
                	this.getCells()[randRow][randCol].setValue(0);
                	this.getCells()[randRow][randCol].setFixedNumber(false);
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
		if(this.getDifficulty() == 3) {
			return 210;
		}
		else if(this.getDifficulty() == 5) {
			return 190;
		}
		else if(this.getDifficulty() == 7) {
			return 170;
		}
		else {
			return 369;
		}
	}



	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public boolean proofFilledOut() {
		// TODO Auto-generated method stub
		return false;
	}
}
