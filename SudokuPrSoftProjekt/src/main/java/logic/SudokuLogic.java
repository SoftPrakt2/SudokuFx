package logic;

/**
 * extends BasicGameLogic
 * implements all abstract methods of BasicGameLogic
 * is a superclass of FreeFormLogic
 * 
 * @author rafael
 */
public class SudokuLogic extends BasicGameLogic {

	/**
	 * constructor for creating a SudokuLogic-Object
	 * this constructor extends the BasicGameLogic constructor
	 * the size of the needed Cells-Array gets set with {@link #setCells(Cell[][])}
	 * the game type of the current game gets set with {@link #setGametype(String)}
	 */
	public SudokuLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super(gamestate, minutesPlayed, secondsPlayed);
		setCells(new Cell[9][9]);
		setGametype("Sudoku");
		setNumbersToBeSolvable(1);
		this.setSavedResults(new int[this.getCells().length][this.getCells().length]);
	}
	
	/**
	 * checks if the value already exists in a row
	 * gets called in {@link #valid(int, int, int)}
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
	 * checks if the value already exists in a column
	 * gets called in {@link #valid(int, int, int)}
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
	 * fills the cells-array with values and informations about coordinates
	 * the box-id is the most important past of this method, because it is needed 
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
	 * removes a certain amount of numbers from the array
	 * the amount depends on the difficulty chosen by the user
	 * 
	 */
	public void difficulty() {
        int counter = getNumberOfVisibleValues();

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
	 * auxiliary method for {@link #difficulty()}
	 * 3 = Hard
	 * 5 = Medium
	 * 7 = Easy
	 */
	@Override
	public int getNumberOfVisibleValues() {
		if(this.getDifficulty() == 3) {
			return 57;
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

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public boolean proofFilledOut() {
		return false;
	}
	
}