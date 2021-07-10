
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
 * The abstract class BasicGameLogic is a basic class for all Sudoku-Subclasses
 * (SudokuLogic, SumraiLogic). This class creates variables and methods like
 * createSudoku, solveSudoku, valid and hint, that get used by the subclasses.
 * 
 * it also defines abstract methods that need to be implemented by the
 * subclasses SudokuLogic and SamuraiLogic.
 *
 * @author rafael
 */
public abstract class BasicGameLogic {
	/**
	 * A Sudoku game can have different states, depending on what the user does. For
	 * Example: OPEN when the game is still ongoing, AUTOSOLVED if the user chose to
	 * automatically solve the current sudoku game, ...
	 */
	private Gamestate gamestate;

	/**
	 * defines the game type when a subclass is initialized
	 */
	private String gametype;
	private int gamePoints;
	private long minutesPlayed;
	private long secondsPlayed;
	private String gameText;
	private int gameID = 0;
	
	protected Random numberGenerator;

	/**
	 * this Cell-Array contains the values of a game. Every Cell also has information
	 * regarding its current Row, Column and Box.
	 */
	private Cell[][] cells;

	/**
	 * Saves the integer values of the created sudoku, this is needed to generate a
	 * background solution of a game for comparing user inputs in the UI. Gets used
	 * by GameController
	 * {@link controller.GameController.#hintHandeler(javafx.event.ActionEvent)}
	 */
	private int[][] savedResults;
	/**
	 * This integer-Array is used in the {@link #hint()} method to save the current
	 * values from the Cell-Array before the {@link #solveSudoku()} method gets used.
	 */
	private int[][] temporaryValues;

	/**
	 * this variable is used to display the selected difficulty in letters.
	 */
	private String difficultyString;

	/**
	 * this variable will be used to display the game time informations created in
	 * the {@link #timer}.
	 */
	private String playtimeString;

	protected int shuffleCounter;

	/**
	 * Defines the difficulty for a game. Will be needed in the {@link #difficulty}
	 * method
	 */
	private int difficulty;

	/**
	 * This variable is needed to determine how many numbers are currently inside
	 * the sudoku board (both from the user and automatically generated). Used to
	 * determine if the game is correctly saved or not
	 */
	private int numbersInsideTextField;
	/**
	 * This StringProperty contains the current playing time. This variable will
	 * later be displayed through the
	 * {@link application.BasicGameBuilder#getLiveTimeLabel()} label
	 */
	private StringProperty liveTimePlayedString;

	/**
	 * Variable which describes how many numbers are needed for a Sudoku Game to be
	 * solvable.
	 */
	private int numbersToBeSolvable;

	/**
	 * variable which holds the amount of numbers the user has typed into the game
	 * field. This variable is needed to check if the user has entered enough numbers
	 * while creating a manual game for the game to be solvable.
	 */
	private int manualNumbersInserted;
	
	
	/**
	 * timer object which is responsible for updating {@link #getSecondsplayed()} and {@link #getMinutesplayed() in real time}.
	 */
	private AnimationTimer timer;
	private BooleanProperty timerIsRunning = new SimpleBooleanProperty();
	

	/**
	 * constructor for creating a BasicGameLogic object.
	 * 
	 * @param gamestate     : differentiates between several game states that a game
	 *                      can have.
	 * @param minutesPlayed : the number of minutes, that the player has played his
	 *                      current game.
	 * @param secondsPlayed : the number of seconds, that the player has played his
	 *                      current game.
	 */
	protected BasicGameLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed) {
		super();
		this.gamestate = gamestate;
		this.minutesPlayed = minutesPlayed;
		this.secondsPlayed = secondsPlayed;
		liveTimePlayedString = new SimpleStringProperty();
		shuffleCounter = 0;
		this.numberGenerator = new Random();
	}

	/**
	 * abstract methods that need to be implemented by the subclasses.
	 */
	public abstract void setUpLogicArray();

	public abstract void difficulty();

	public abstract int getNumberOfVisibleValues();

	public abstract boolean isConnected();

	public abstract boolean checkRow(int row, int col, int guess);

	public abstract boolean checkCol(int row, int col, int guess);
	
	public abstract boolean proofFilledOut();

	/**
	 * Checks if the number already exists in a box.
	 * 
	 * @param row   : row-coordinate of the new number.
	 * @param col   : cow-coordinate of the new number.
	 * @param guess : new number.
	 * @return: returns true if there is no duplicate value in the current box.
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
	 * checks if checkRow, checkCol and checkBox return true.
	 * 
	 * @param row   : row-coordinate of the new number.
	 * @param col   : cow-coordinate of the new number.
	 * @param guess : new number.
	 * @return: returns true if there is no duplicate value according to the sudoku rules.
	 */
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, col, guess) && checkCol(row, col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	/**
	 * auto generator for a new sudoku game. A recursive method for creating a new
	 * game.
	 * 
	 * @return true if a sudoku array was successfully created with no empty cells.
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
						int randomNum = numberGenerator.nextInt(9) + 1;
						// checks if the generated number is valid
						if (valid(row, col, randomNum)) {
							// sets value if the generated number is valid
							this.cells[row][col].setValue(randomNum);
							if (createSudoku()) {// recursive call the the method
								return true;
							} else {//sets value to 0 if recursive call returns false
								this.cells[row][col].setValue(0);
							}
						}
					}
					return false;
				}
			}
		}

		this.savedResults = new int[this.cells.length][this.cells.length];
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				this.getSavedResults()[row][col] = this.getCells()[row][col].getValue();
			}
		}
		return true;
	}

	/**
	 * This auxiliary method is used to store a games solution inside a help array.
	 * This is needed so user inputs can be checked if they are correct.
	 * 
	 * @return solved sudoku game in the form of a Cell-Array.
	 */
	public int[][] createBackgroundSolution() {
		Cell[][] help = this.getCells();

		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				if (!this.getCells()[row][col].isReal) {
					this.getCells()[row][col].setValue(0);
				}

			}
		}
		this.solveSudoku();

		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				this.getSavedResults()[row][col] = this.getCells()[row][col].getValue();
				System.out.println(this.getSavedResults()[row][col]);
			}
		}
		this.setCells(help);

		return this.getSavedResults();
	}

	/**
	 * Solves a sudoku game. A recursive method for solving a new game.
	 * 
	 * @return true if sudoku was solved.
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
							if (solveSudoku()) {// recursive call of the the method
								return true;
							} else {//sets value to 0 if recursive call returns false
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
	 * Gives the user a valid hint. Sudoku is solved before a hint is given so that an
	 * situation does not occur, where the sudoku game becomes unsolvable, because of
	 * a given hint.
	 * 
	 * @return the coordinates of a hint.
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
		if (!this.testIfSolved()) {
			return null;
		}

		// chooses random coordinates and a random number that will be shown as a hint
		while (!correctRandom) {
			// generates random coordinates and a random number
			int randomCol = numberGenerator.nextInt(this.cells.length);
			int randomRow = numberGenerator.nextInt(this.cells.length);
			int randomNumber = numberGenerator.nextInt(9) + 1;
			if (this.cells[randomRow][randomCol].getValue() == randomNumber
					&& temporaryValues[randomRow][randomCol] == 0
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
		// returns the coordinates of the hint
		if (coordinates != null) {
			this.cells[coordinates[0]][coordinates[1]].setIsHint(true);
		}

		return coordinates;
	}
	
	/**
	 * This method is used to initialize a timer object. This method furthermore sets
	 * the {@link #getMinutesplayed()} and {@link #getSecondsplayed()} variables in
	 * realtime.
	 * @author gruber 
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
	 * This method is used to encapsulate all methods which are needed to setup a
	 * new cell array for manually created games.
	 */
	public void initializeCustomGame() {
		setSecondsPlayed(0);
		setMinutesPlayed(0);
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
	 * This method is used to encapsulate all methods which are used set up all game
	 * information variables and  resets values so that a new game can be created
	 * correctly.
	 */
	public void setUpGameInformations() {
		setGamePoints(50);
		setGameState(Gamestate.OPEN);
		setDifficultyString();
		setMinutesPlayed(0);
		setSecondsPlayed(0);
		initializeTimer();
		getLiveTimer().start();
	}

	/**
	 * This method is used to encapsulate all methods which are needed to setup a
	 * new cell array.
	 */
	public void setUpGameField() {
		setUpLogicArray();
		setShuffleCounter(0);
		createSudoku();
		difficulty();
	}
	
	/**
	 * This method is used to save the values of a created sudoku game.
	 */
	public void connectToSavedResults() {
		for (int row = 0; row < this.getCells().length; row++) {
			for (int col = 0; col < this.getCells()[row].length; col++) {
				this.getSavedResults()[row][col] = this.getCells()[row][col].getValue();
			}
		}
	}
	
	/**
	 * Tests if the sudoku game still contains the value 0. Returns false if the
	 * sudoku game still contains the value 0 returns true otherwise.
	 * 
	 * @return true if the game was solved and has no 0 in it.
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
	 * Removes all values from the Cell-Array.
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
	 * This method is used to set the game text which will be displayed in the
	 * {@link application.BasicGameBuilder#getGameInfoLabel()} label.
	 * 
	 * @return the string depending on the current game state.
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
			gameText = "Unsolvable Sudoku state!";
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
		if (this.getGamestate() == Gamestate.NOTENOUGHNUMBERS) {
			gameText = "Not enough numbers! Please insert " + (getNumbersToBeSolvable() - manualNumbersInserted)
					+ " more numbers to enable game functions";
		}
		if (this.getGamestate() == Gamestate.NOFORMS) {
			gameText = "Your forms are not properly connected!";
		}
		if (this.getGamestate() == Gamestate.MANUALCONFLICT) {
			gameText = "Your game has conflicts!";
		}
		return gameText;
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
	
	/**
	 * Sets the value of a cell.
	 * 
	 * @param row
	 * @param col
	 * @param box
	 * @param value
	 */
	public void setCell(int row, int col, int box, int value) {
		this.cells[row][col] = new Cell(row, col, box, value);
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

	public int getGameid() {
		return gameID;
	}

	public String getDifficultystring() {
		return difficultyString;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
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

	public StringProperty getTimeProperty() {
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

	public int getNumbersInsideTextField() {
		return this.numbersInsideTextField;
	}

	public int[][] getSavedResults() {
		return this.savedResults;
	}

	public void setSavedResults(int[][] cells) {
		this.savedResults = cells;
	}
}
