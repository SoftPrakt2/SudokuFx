
package logic;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Creates all variables and methods that are needed by all subclasses
 * Creates abstract methods that need to be implemented by the subclasses
 *
 */
public abstract class BasicGameLogic {

	private Gamestate gamestate;

	private String gametype = "";
	
	
	private int gamePoints = 10;
	
	
	private long minutesPlayed;
	private long secondsPlayed;
	protected Random r;

	

	private String gameText = "";
	private int gameID = 0;

	private Cell[][] cells;
	private int[][] temporaryValues;
	
	/**
	 * this variable is used to display the selected difficulty with letters
	 */
	private String difficultyString;

	private String playtimeString;

	protected int shuffleCounter;

	private int difficulty;
	
	
	/**
	 * this variable is needed to determine how many numbers are currently inside the sudokuboard (both from the user and automatically generated)
	 * used for determing if the game is correctly saved or not
	 */
	private int numbersInsideTextField;
	

	/**
	 * this StringProperty contains the current playing time
	 * this variable will later be displayed through the {@link application.BasicGameBuilder#getLiveTimeLabel()} label
	 */
	private StringProperty liveTimePlayedString;
	
	/**
	 * variable which describes how many numbers are needed for a Sudoku Game to be solvable
	 */
	private int numbersToBeSolvable;
	
	
	/**
	 * variable which holds the amount of numbers the user has typed into the game field
	 * This variable is needed to check if the user has entered enough numbers while creating a manual game
	 * for the game to be solvable
	 */
	private int manualNumbersInserted;
	
	private AnimationTimer timer;
	private BooleanProperty timerIsRunning = new SimpleBooleanProperty();

	/**
	 * constructor for creating a BasicGameLogic object
	 * @param gamestate : differentiates between several gamestates that a game can have
	 * @param minutesPlayed
	 * @param secondsPlayed
	 * @param isCorrect : boolean to check if the sudoku was solved correctly
	 */
	protected BasicGameLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super();
		this.gamestate = gamestate;
		this.minutesPlayed = minutesPlayed;
		this.secondsPlayed = secondsPlayed;
		liveTimePlayedString = new SimpleStringProperty();
		shuffleCounter = 0;
		this.r = new Random();
	}
	
	
	

	/**
	 * abstract methods that need to be implemented by the subclasses
	 */
	public abstract void setUpLogicArray();

	public abstract void difficulty();
	
	public abstract int getNumberOfVisibleValues();

	public abstract boolean isConnected();

	public abstract boolean checkRow(int row, int col, int guess);

	public abstract boolean checkCol(int row, int col, int guess);

	/**
	 * Checks if the number already exists in the box
	 * @param row : row-coordinate of the new number
	 * @param col : row-coordinate of the new number
	 * @param guess : new number
	 * @return
	 */
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
	 * checks if checkRow, checkCol and checkBox return true
	 * @param row : row-coordinate of the new number
	 * @param col : row-coordinate of the new number
	 * @param guess : new number
	 * @return
	 */
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, col, guess) && checkCol(row, col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	/**
	 * autogenerator for a new sudoku game
	 * a recursive method for creating a new game
	 * @return
	 */
	public boolean createSudoku() {
		// iterates the array
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				// checks if the cell has the value 0
				if (this.cells[row][col].getValue() == 0) {
					// number of tries to find a valid number 
					for (int y = 0; y < 9; y++) {
						// generates a random number between 1 and 9
						int randomNum = r.nextInt(9) + 1;
						// checks if the generated number is valid
						if (valid(row, col, randomNum)) {
							// sets value if the generated number is valid
							this.cells[row][col].setValue(randomNum);
							if (createSudoku()) {// recursive call the the method
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
		return true;
	}

	/**
	 * solves a sudoku game
	 * a recursive method for solving a new game
	 * @return
	 */
	public boolean solveSudoku() {
		// iterates the array
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				// checks if the cell has the value 0
				if (this.cells[row][col].getValue() == 0) {
					// checks witch of the numbers between 1 and 9 are valid inputs
					for (int y = 1; y <= 9; y++) {
						// checks if current number is valid
						if (valid(row, col, y)) {
							// sets value if the generated number is valid
							this.cells[row][col].setValue(y);
							if (solveSudoku()) {// recursive call the the method
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
		return true;
	}

	/**
	 * gives the user a valid hint
	 * sudoku is solved before hint is given so that an situation 
	 * does not occur, where the sudoku game becomes unsolvable
	 * @return
	 */
	public int[] hint() {
		boolean correctRandom = false;
		int[] coordinates = new int[2];
		int counter = 0;

		// current values of the sudoku game get saved
		temporaryValues = new int[this.cells.length][this.cells.length];
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				temporaryValues[row][col] = this.cells[row][col].getValue();
			}
		}

		// current sudoku gets solved
		this.solveSudoku();

		// chooses random coordinates and a random number that will be shown as a hint
		while (!correctRandom) {
			// generates random coordinates and a random number
			int randomCol = r.nextInt(this.cells.length);
			int randomRow = r.nextInt(this.cells.length);
			int randomNumber = r.nextInt(9) + 1;
			if (this.cells[randomRow][randomCol].getValue() == randomNumber && temporaryValues[randomRow][randomCol] == 0
					&& this.cells[randomRow][randomCol].getValue() != -1) {
				temporaryValues[randomRow][randomCol] = randomNumber;
				coordinates[0] = randomRow;
				coordinates[1] = randomCol;
				correctRandom = true;
			}
			counter++;
			if (counter == 50000) {
				coordinates = null;
				break;
			}
		}

		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				this.cells[row][col].setValue(temporaryValues[row][col]);
			}
		}
		// returns the coordinates the the hint
		return coordinates;
	}
	
	/**
	 * tests if the sudoku game still contains the value 0
	 * @return
	 */
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
	
	/**
	 * sets the value of the cell
	 * @param row
	 * @param col
	 * @param box
	 * @param value
	 */
	public void setCell(int row, int col, int box, int value) {
		this.cells[row][col] = new Cell(row, col, box, value);
	}
	
	/**
	 * removes all values of the array
	 */
	public void removeValues() {
        for (int row = 0; row < this.cells.length; row++) {
            for (int col = 0; col < this.cells[row].length; col++) {
                this.cells[row][col].setFixedNumber(false);
                this.cells[row][col].setValue(0);
            }
        }
	}

	
	/**
	 * sets game information at the beginning of a new manual game
	 */
	public void initializeCustomGame() {
		setUpLogicArray();
		setDifficulty(0);
		setDifficultyString();
		difficulty();
		if (this instanceof FreeFormLogic) {
			setGameState(Gamestate.DRAWING);
		} else
			setGameState(Gamestate.CREATING);
	}

	/**
	 * initializes a new game
	 * calls all methods that are needed to create a game
	 */
	public void setUpGameInformations() {
	//	setStartTime(System.currentTimeMillis());
		setGamePoints(10);
		setGameState(Gamestate.OPEN);
		setDifficultyString();
		setMinutesPlayed(0);
		setSecondsPlayed(0);
		initializeTimer();
		getLiveTimer().start();
	}

	/**
	 * calls all methods for a new game
	 */
	public void setUpGameField() {
		setUpLogicArray();
		setShuffleCounter(0);
		createSudoku();
		difficulty();
	}
	
	
	/**
	 * starts a timer when a new game is created
	 */
	public void initializeTimer() {

		liveTimePlayedString = new SimpleStringProperty("");

		timerIsRunning = new SimpleBooleanProperty();

		timer = new AnimationTimer() {
			long helpmin = minutesPlayed;
			long helpsec = secondsPlayed;

			private LocalTime startTime;

			@Override
			public void handle(long now) {
				long elapsedSeconds = Duration.between(startTime, LocalTime.now()).getSeconds();
				minutesPlayed = ((helpmin * 60 + helpsec + elapsedSeconds) / 60);
				secondsPlayed = (helpsec + helpmin * 60 + elapsedSeconds) % 60;

				liveTimePlayedString.set(String.format("%02d:%02d", minutesPlayed, secondsPlayed));
			}

			@Override
			public void start() {
				timerIsRunning.set(true);
				startTime = LocalTime.now();
				super.start();
			}

			@Override
			public void stop() {
				timerIsRunning.set(false);
				super.stop();
			}
		};

	}

	/**
	 * getter and setter
	 */
	public String getGametype() {
		return this.gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
	}

	public String getPlaytimestring() {
		return playtimeString;
	}

	public void setPlaytimestring(String playtimeString) {
		this.playtimeString = playtimeString;
	}

	public Cell[][] getCells() {
		return this.cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public int getGamepoints() {
		return gamePoints;
	}

	public long getMinutesplayed() {
		return minutesPlayed;
	}

	public long getSecondsplayed() {
		return secondsPlayed;
	}

	public void setMinutesPlayed(long minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public void setSecondsPlayed(long secondsPlayed) {
		this.secondsPlayed = secondsPlayed;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}

	
	public void setGameState(Gamestate gamestate) {
		this.gamestate = gamestate;
	}

	public Gamestate getGamestate() {
		return this.gamestate;
	}
	
	/**
	 * This method is used to set the gametext which will be displayed in the
	 * {@link application.BasicGameBuilder#getGameInfoLabel()} label
	 * @return the string depending on the current gamestate
	 */
	public String getGameText() {

		if (this.getGamestate() == Gamestate.DONE) {
			gameText = "Congratulations you won!";
		}
		if (this.getGamestate() == Gamestate.INCORRECT) {
			gameText = "Sorry your Sudoku is not correct yet";
		}
		if (this.getGamestate() == Gamestate.AUTOSOLVED) {
			gameText = "Autosolved";
		}
		if (this.getGamestate() == Gamestate.CONFLICT) {
			gameText = "Please remove the conflicts";
		}
		if (this.getGamestate() == Gamestate.UNSOLVABLE) {
			gameText = "Unsolvable Sudoku! New Solution generated";
		}
		if (this.getGamestate() == Gamestate.CREATING) {
			gameText = "Create your own game!";
		}
		if (this.getGamestate() == Gamestate.OPEN) {
			gameText = "Game ongoing!";
		}
		if (this.getGamestate() == Gamestate.DRAWING) {
			gameText = "Draw your own forms!";
		}
		if (this.getGamestate() == Gamestate.UNSOLVABLEMANUALSUDOKU) {
			gameText = "Your created Sudoku is unsolvable! Please create a new Sudoku.";
		}
		if (this.getGamestate() == Gamestate.NOTENOUGHNUMBERS) {
            gameText = "Not enough numbers! Please insert " + (getNumbersToBeSolvable() - manualNumbersInserted)
                    + " more numbers to create the game";
        }
        if (this.getGamestate() == Gamestate.NOFORMS) {
            gameText = "Your forms are not properly connected!";
        }
        if(this.getGamestate() == Gamestate.MANUALCONFLICT) {
        	gameText = "Your game has conflicts!";
        }
        
		return gameText;
	}

	public int getGameid() {
		return gameID;
	}

	public String getDifficultystring() {
		return difficultyString;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public void setDifficultyString() {
		if (difficulty == 3) {
			difficultyString = "Hard";
		}
		if (difficulty == 5) {
			difficultyString = "Medium";
		}
		if (difficulty == 7) {
			difficultyString = "Easy";
		}
		if (difficulty == 0) {
			difficultyString = "Manual";
		}
	}

	public AnimationTimer getLiveTimer() {
		return timer;
	}

	public boolean timerIsRunning() {
		return timerIsRunning.get();
	}

	public void setShuffleCounter(int counter) {
		this.shuffleCounter = counter;
	}

	public StringProperty getStringProp() {
		return liveTimePlayedString;
	}
	
	public int getNumbersToBeSolvable() {
		return numbersToBeSolvable;
	}
	
	public void setManualNumbersInserted(int manualNumbersInserted) {
		this.manualNumbersInserted = manualNumbersInserted;
	}

	public void setNumbersToBeSolvable(int numbersToBeSolvable) {
		this.numbersToBeSolvable = numbersToBeSolvable;
	}
	
	public void setNumbersInsideTextField(int numbersInsideTextField) {
		this.numbersInsideTextField = numbersInsideTextField;
	}
	
		
	public int getNumbersInsideTextField () {
		return this.numbersInsideTextField;
	}
	
	
	
	
	
}
