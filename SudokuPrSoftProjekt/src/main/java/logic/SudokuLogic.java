package logic;

/**
 * Extends BasicGameLogic.
 * Implements all abstract methods of BasicGameLogic.
 * Is a superclass of FreeFormLogic.
 * 
 * @author rafael
 */
public class SudokuLogic extends BasicGameLogic {

	/**
	 * Constructor for creating a SudokuLogic-Object.
	 * This constructor extends the BasicGameLogic constructor.
	 * The size of the needed Cells-Array gets set with {@link #setCells(Cell[][])}.
	 * The game type of the current game gets set with {@link #setGametype(String)}.
	 */
	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super(gamestate, minutesPlayed, secondsPlayed);
		setCells(new Cell[9][9]);
		setGametype("Sudoku");
		setNumbersToBeSolvable(17);
		this.setSavedResults(new int[this.getCells().length][this.getCells().length]);
	}
	
	/**
	 * Checks if the value already exists in a row.
	 * Gets called in {@link #valid(int, int, int)}.
	 */
	@Override
	public boolean checkRow(int row, int col, int guess) {
		for (col = 0; col < this.getCells().length; col++) {
			if (this.getCells()[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the value already exists in a column.
	 * Gets called in {@link #valid(int, int, int)}.
	 */
	@Override
	public boolean checkCol(int row, int col, int guess) {
		for (row = 0; row < this.getCells().length; row++) {
			if (this.getCells()[row][col].getValue() == guess) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Fills the cell-array with values and informations about coordinates.
	 * The box-id is the most important point of this method, because it is needed 
	 * for the checkBox method in FreeFormLogic, which is a subclass.
	 */
	@Override
	public void setUpLogicArray() {
		int box = 0;
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
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
				this.getCells()[row][col] = cell;
			}
		}
	}
	
	/**
	 * Solves a sudoku game. A recursive method for solving a new game.
	 * 
	 * @return true if sudoku was solved.
	 */	
	@Override
	public boolean solveSudoku() {
		// iterates the array
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				// checks if the cell has the value 0
				if (this.getCells()[row][col].getValue() == 0) {
					// checks witch of the numbers between 1 and 9 are valid inputs
					for (int y = 1; y <= 9; y++) {
						// checks if current number is valid
						if (valid(row, col, y)) {
							// sets value if the generated number is valid
							this.getCells()[row][col].setValue(y);
							if (solveSudoku()) {// recursive call of the the method
								return true;
							} else {//sets value to 0 if recursive call returns false
								this.getCells()[row][col].setValue(0);
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Removes a certain amount of numbers from the array.
	 * The amount depends on the difficulty chosen by the user.
	 */
	public void difficulty() {
        int counter = getNumberOfValuesToDelete();

        if(counter == 81) {
            removeValues();
        }
        else {
            while (counter != 0) {
                    int randCol = numberGenerator.nextInt(9);
                    int randRow = numberGenerator.nextInt(9);
                if (this.getCells()[randRow][randCol].getValue() != 0 && this.getCells()[randRow][randCol].getFixedNumber()) {
                	this.getCells()[randRow][randCol].setValue(0);
                	this.getCells()[randRow][randCol].setFixedNumber(false);
                    counter--;
                }
            }
        }
    }
	
	/**
	 * auxiliary method for {@link #difficulty()}.
	 * 3 = Hard;
	 * 5 = Medium;
	 * 7 = Easy;
	 */
	@Override
	public int getNumberOfValuesToDelete() {
		if(this.getDifficulty() == 3) {
			return 56;
		}
		else if(this.getDifficulty() == 5) {
			return 46;
		}
		else if(this.getDifficulty() == 7) {
			return 36;
		}
		else {
			return 81;
		}
	}	
}