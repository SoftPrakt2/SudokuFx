package logic;

import java.util.HashMap;
import java.util.Map;

/**
 * extends BasicGameLogic and implements all abstract methods of BasicGameLogic.
 * 
 * @author rafael
 */
public class SamuraiLogic extends BasicGameLogic {

	private Map<Integer, int [][]> temporaryValues;
	private int counter;
	private int countRecursive;

	
	/**
	 * constructor for creating a Samurai-Object. This constructor extends the
	 * BasicGameLogic constructor. The size of the needed Cell-Array gets set with
	 * {@link #setCells(Cell[][])}. The game type of the current game gets set with
	 * {@link #setGametype(String)}.
	 */
	public SamuraiLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super(gamestate, minutesPlayed, secondsPlayed);
		setCells(new Cell[21][21]);
		setGametype("Samurai");
		setNumbersToBeSolvable(130);
		this.setSavedResults(new int[this.getCells().length][this.getCells().length]);
		this.temporaryValues = new HashMap<>();
		this.counter = 0;
		this.countRecursive = 0;
	}

	/**
	 * Checks if the value already exists in a row. Gets called in
	 * {@link #valid(int, int, int)}
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
	 * Checks if the value already exists in a column. Gets called in
	 * {@link #valid(int, int, int)}.
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
	 * Fills the cells-array with values and informations about coordinates so that
	 * the cells-array is not null.
	 */
	@Override
	public void setUpLogicArray() {
		int box = 1;
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				if (!(row < 6 && col > 8 && col < 12) && !(row > 8 && row < 12 && col < 6)
						&& !(row > 8 && row < 12 && col > 14) && !(row > 14 && col > 8 && col < 12)) {
					setCell(row, col, box, 0);
				} else {
					setCell(row, col, box, -1);
				}
			}
		}
	}

	/**
	 * Removes a certain amount of numbers from the array. The amount depends on the
	 * difficulty chosen by the user.
	 */
	@Override
	public void difficulty() {
		counter = 0;
		countRecursive = 0;
		int amountOfNumbers = getNumberOfValuesToDelete();

		if (amountOfNumbers == 369) {
			removeValues();
		} else {
			while (amountOfNumbers != 0) {
				int randCol = numberGenerator.nextInt(this.getCells().length);
				int randRow = numberGenerator.nextInt(this.getCells().length);
				if (this.getCells()[randRow][randCol].getValue() != 0
						&& this.getCells()[randRow][randCol].getFixedNumber()
						&& this.getCells()[randRow][randCol].getValue() != -1) {
					this.getCells()[randRow][randCol].setValue(0);
					this.getCells()[randRow][randCol].setFixedNumber(false);
					amountOfNumbers--;
				}
			}
		}
	}

	/**
	 * Auxiliary method for {@link #difficulty()}.
	 * 3 = Hard;
	 * 5 = Medium;
	 * 7 = Easy;
	 */
	@Override
	public int getNumberOfValuesToDelete() {
		if (this.getDifficulty() == 3) {
			return 239;
		} else if (this.getDifficulty() == 5) {
			return 200;
		} else if (this.getDifficulty() == 7) {
			return 170;
		} else {
			return 369;
		}
	}

	/**
	 * Solves all cells of the cell-array where there is only one possible solution.
	 * This method gets used before the actual {@link #solveSudoku()} method gets used,
	 * so that the time need for creating a solution gets shortened.
	 */
	public void SolveCellsWithOnlyOnePossibleValue() {
		/**
		 * a temporary integer-array is created and gets all the values of the current cell-array / current game.
		 */
		int[][] helper = new int[this.getCells().length][this.getCells().length];
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				helper[row][col] = this.getCells()[row][col].getValue();
			}
		}

		int numberOfPossibleValues = 0;
		int rightValue = 0;

		/**
		 * Searches for cells with only one possible solution. Cells with only one solution get solved.
		 */
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				numberOfPossibleValues = 0;
				if (this.getCells()[row][col].getValue() == 0) {
					for (int y = 1; y <= 9; y++) {
						if (this.valid(row, col, y)) {
							numberOfPossibleValues++;
							rightValue = y;
						}
					}
					if (numberOfPossibleValues == 1) {
						helper[row][col] = rightValue;
						this.getCells()[row][col].setValue(rightValue);
					}
				}
			}
		}

		/**
		 * the current values of the helper-array get inserted into the cell-array.
		 */
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				if (this.valid(row, col, helper[row][col]))
					this.getCells()[row][col].setValue(helper[row][col]);
			}
		}
	}

	@Override
	public boolean solveSudoku() {
		int[] coordinates = new int[2];
		if (counter == 0) {
			for (int row = 0; row < 10; row++) {
				SolveCellsWithOnlyOnePossibleValue();
			}
			counter++;
		}
		int[][] helper = new int[this.getCells().length][this.getCells().length];
		int minPossibilities = 10;
		int numberOfPossibleValues = 0;
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				numberOfPossibleValues = 0;
				if (this.getCells()[row][col].getValue() == 0) {
					for (int y = 1; y <= 9; y++) {
						if (this.valid(row, col, y)) {
							numberOfPossibleValues++;
						}
					}
					if (numberOfPossibleValues < minPossibilities) {
						minPossibilities = numberOfPossibleValues;
						coordinates[0] = row;
						coordinates[1] = col;
					}
				}
			}
		}

		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				helper[row][col] = this.getCells()[row][col].getValue();
			}
		}
		// checks if the cell has the value 0
		if (this.getCells()[coordinates[0]][coordinates[1]].getValue() == 0) {
			// checks witch of the numbers between 1 and 9 are valid inputs
			for (int y = 1; y <= 9; y++) {
				// checks if current number is valid
				if (valid(coordinates[0], coordinates[1], y)) {
					// sets value if the generated number is valid
					this.temporaryValues.put(countRecursive, helper);
					
					this.getCells()[coordinates[0]][coordinates[1]].setValue(y);
					for (int row = 0; row < 3; row++) {
						SolveCellsWithOnlyOnePossibleValue();
					}
					this.countRecursive++;
					if (solveSudoku()) {// recursive call the the method
						return true;
					}
					else {
						this.getCells()[coordinates[0]][coordinates[1]].setValue(0);
						this.countRecursive--;
						for (int row2 = 0; row2 < this.getCells().length; row2++) {
							for (int col2 = 0; col2 < this.getCells()[row2].length; col2++) {
								this.getCells()[row2][col2].setValue(this.temporaryValues.get(countRecursive)[row2][col2]);
							}
						}
					}
				}
			}
			return false;
		}
		return true;
	}
}
