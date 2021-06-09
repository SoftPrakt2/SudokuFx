
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
 * 
 * Stellt die Basisvariablen und Methoden f�r die ableitenden Logiken dar
 * Deklariert abstract Methoden, welche von den Unterklassen implementiert
 * werden m�ssen.
 *
 */
public abstract class BasicGameLogic {

//test
	protected Gamestate gamestate;
	protected boolean isCorrect;
	protected String gametype = "";
	protected int gamePoints = 10;
	protected long minutesPlayed;
	protected long secondsPlayed;

	protected long startTime;
	protected int gameIDhelper;
	protected String gameText = "";
	private int gameID = 0;

	protected Cell[][] cells;
	protected int[][] temporaryValues;

	private String difficultyString;

	public String playtimeString;

	protected int shuffleCounter;

	protected int hintCounter;
	protected int countHintsPressed = 0;

	protected int difficulty;

	protected StringProperty liveTimePlayedString;
	
	protected int numbersToBeSolvable;
	protected int manualNumbersInserted;
	

	// Variablen f�r Timer
	AnimationTimer timer;
	BooleanProperty timerIsRunning = new SimpleBooleanProperty();

	protected BasicGameLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super();
		this.gamestate = gamestate;
		this.minutesPlayed = minutesPlayed;
		this.secondsPlayed = secondsPlayed;
		this.isCorrect = isCorrect;
		liveTimePlayedString = new SimpleStringProperty();
		shuffleCounter = 0;
	}
	
	
	

	/**
	 * Abstrakte Methoden welche von Unterklassen implementiert werden m�ssen.
	 */
	public abstract void setUpLogicArray();

	public abstract void difficulty();
	
	public abstract int getNumberOfVisibleValues();

//	public abstract void printCells();

//	public abstract void setCell(int col, int row, int guess);

//	public abstract boolean testIfSolved();

	public abstract boolean isConnected();

	public abstract boolean checkRow(int row, int col, int guess);

	public abstract boolean checkCol(int row, int col, int guess);

	/**
	 * �berpr�ft in der Box der �bergebenen Reihe und Zeile eine idente Zahl vorhanden ist
	 * Gibt true zur�ck falls keine idente Zahl vorhanden ist
	 * Gibt false zur�ck falls eine idente Zahl vorhanden ist
	 * @param row
	 * @param col
	 * @param guess
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
	 * �berpr�ft ob CheckRow, CheckCol und CheckBox true zur�ck geben.
	 * @param row
	 * @param col
	 * @param guess
	 * @return
	 */
	public boolean valid(int row, int col, int guess) {
		if (checkRow(row, col, guess) && checkCol(row, col, guess) && checkBox(row, col, guess)) {
			return true;
		}
		return false;
	}

	/**
	 * Autogenerator f�r ein neues Sudoku Bef�llt rekursiv das im Hintergrund
	 * liegende Sudoku-Array.
	 * 
	 * @return
	 */
	public boolean createSudoku() {
		Random r = new Random();
		// Iteration durch Array
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				// Es wird nach einer Zelle gesucht die einen Wert 0 aufweist
				if (this.cells[row][col].getValue() == 0) {
					// Anzahl von Versuchen (in diesem Falle 9) die durchlaufen werden sollen
					// bis eine L�sung gefunden wird
					for (int y = 0; y < 9; y++) {
						// es wird eine zuf�llige Zahl generiert
						int randomNum = r.nextInt(9) + 1;
						// �berpr�fung ob generierte Zahl den Sudokuregeln entspricht
						if (valid(row, col, randomNum)) {
							// Zahl ist OK und wird in die Array eingef�gt
							this.cells[row][col].setValue(randomNum);
							if (createSudoku()) {// rekursiever Aufruf f�r Backtracking
								return true;
							} else {// Fals keine L�sung gefunden wird wird der Wert der Zelle zur�ck auf 0 gesetzt
								this.cells[row][col].setValue(0);
							}
						}
					}
					// Gibt false zur�ck wenn in den vorgegebenen Versuchen keine passende Zahl
					// gefunden wird
					return false;
				}
			}
		}
		// Gibt true zur�ck falls keine lehren Felder mehr vorhanden sind (hei�t
		// indirekt, dass das Sudoku fertig generiert worden ist)
		return true;
	}

	/**
	 * L�st ein Sudoku rekursiv
	 * 
	 * @return
	 */
	public boolean solveSudoku() {
		// Iteration durch Array
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				// Es wird nach einer Zelle gesucht die einen Wert 0 aufweist
				if (this.cells[row][col].getValue() == 0) {
					// Alle m�glichen Zahlen werden durchprobiert (1-9)
					for (int y = 1; y <= 9; y++) {
						// �berpr�fung ob Zahl den Sudokuregeln entspricht
						if (valid(row, col, y)) {
							// Zahl ist OK und wird in die Array eingef�gt
							this.cells[row][col].setValue(y);
							if (solveSudoku()) {// rekursiever Aufruf f�r Backtracking
								return true;
							} else {// Fals keine L�sung gefunden wird wird der Wert der Zelle zur�ck auf 0 gesetzt
								this.cells[row][col].setValue(0);
							}
						}
					}
					// Gibt false zur�ck wenn in den vorgegebenen Versuchen keine passende Zahl
					// gefunden wird
					return false;
				}
			}
		}
		// Sudoku wurde gel�st
		return true;
	}

	/**
	 * hint() schaltet eine Zahl f�r den Benutzer an einer zuf�lligen Stelle frei.
	 * davor wird immer die solveSudoku() ausgef�ht, damit nie eine Zahl eingef�gt
	 * wird, welche im nachhinein das Sudoku unl�sbar macht.
	 * 
	 * @return
	 */
	public int[] hint() {
		boolean correctRandom = false;
		int[] coordinates = new int[2];
		int counter = 0;

		// es wird eine int-Array erstellt welche die derzeitigen Values der einzelnen
		// Zellen �bernimmt.
		temporaryValues = new int[this.cells.length][this.cells.length];
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				temporaryValues[row][col] = this.cells[row][col].getValue();
			}
		}

		// solve-Methode wird f�r das derzeitige Spiel ausgef�hrt
		this.solveSudoku();

//		Random randCoordinate = new Random();
		Random r = new Random();
		// es werden zuf�lligen Koordinaten und eine zuf�llige Zahlen generiert bis
		// diese den Bedingungen in der If entsprechen
		while (!correctRandom) {
			// generiert zuf�llige Koordinaten und eine zuf�llige Zahl
			int randomCol = r.nextInt(this.cells.length);
			int randomRow = r.nextInt(this.cells.length);
			int randomNumber = r.nextInt(9) + 1;
			// Falls die Zahl bei den Koordinaten der Zahl an der gleichen Koordinaten im
			// gel�sten Sudoku entsprechen wird diese in die Hilfsarry eingef�gt
			if (this.cells[randomRow][randomCol].getValue() == randomNumber && temporaryValues[randomRow][randomCol] == 0
					&& this.cells[randomRow][randomCol].getValue() != -1) {
				temporaryValues[randomRow][randomCol] = randomNumber;
				coordinates[0] = randomRow;
				coordinates[1] = randomCol;
				correctRandom = true;
			}
			counter++;
			if (counter == 10000) {
				coordinates = null;
				break;
			}
		}

		// die Values der Sudokuzellen werden gleich den int-Werten des Hilfsarrays
		// gesetzt
		for (int row = 0; row < this.cells.length; row++) {
			for (int col = 0; col < this.cells[row].length; col++) {
				this.cells[row][col].setValue(temporaryValues[row][col]);
			}
		}
		// returniert die Koordinaten der eingef�gt Zahl
		return coordinates;
	}
	
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
	
	public void setCell(int row, int col, int box, int value) {
		this.cells[row][col] = new Cell(row, col, box, value);
	}
	
	public void removeValues() {
        for (int row = 0; row < this.cells.length; row++) {
            for (int col = 0; col < this.cells[row].length; col++) {
                this.cells[row][col].setIsReal(false);
                this.cells[row][col].setValue(0);
            }
        }
	}

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

	public void setUpGameInformations() {
		setStartTime(System.currentTimeMillis());
		setGamePoints(10);
		setGameState(Gamestate.OPEN);
		setDifficultyString();
		setMinutesPlayed(0);
		setSecondsPlayed(0);
		initializeTimer();
		getLiveTimer().start();
	}

	public void setUpGameField() {
		setUpLogicArray();
		setShuffleCounter(0);
		createSudoku();
		difficulty();
	}

	/**
	 * Getter und Setter f�r Instanzvariablen
	 * 
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
		return cells;
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

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}




	public void setGameState(Gamestate gamestate) {
		this.gamestate = gamestate;
	}

	public Gamestate getGamestate() {
		return this.gamestate;
	}

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
}
