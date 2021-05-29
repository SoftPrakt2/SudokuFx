
package logic;

import java.time.Duration;
import java.time.LocalTime;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Stellt die Basisvariablen und Methoden für die ableitenden Logiken dar
 * Deklariert abstract Methoden, welche von den Unterklassen implementiert
 * werden müssen.
 *
 */
public abstract class BasicGameLogic {

	protected Gamestate gamestate;
	private boolean isCorrect;
	protected String gameType = "";
	protected int gamePoints = 10;
	protected long minutesPlayed;
	protected long secondsPlayed;

	protected long startTime;
	protected int gameIDhelper;
	public String gameText = "";
	private int gameID = 0;

	protected Cell[][] cells;

	private String difficultyString;

	protected int hintCounter;

	protected int difficulty;

	protected StringProperty liveTimePlayedString;

	// Variablen für Timer
	AnimationTimer timer;
	BooleanProperty running = new SimpleBooleanProperty();

	public BasicGameLogic(Gamestate gamestate, long minutesPlayed, long secondsPlayed, boolean isCorrect) {
		super();
		this.gamestate = gamestate;
		this.minutesPlayed = minutesPlayed;
		this.secondsPlayed = secondsPlayed;
		this.isCorrect = isCorrect;
	}

	public abstract boolean checkRow(int row, int col, int guess);

	public abstract boolean checkCol(int row, int col, int guess);

	public abstract boolean checkBox(int row, int col, int guess);

	public abstract boolean valid(int row, int col, int guess);

	public abstract void setUpLogicArray();

	public abstract boolean createSudoku();

	public abstract int[] hint();

	public abstract boolean solveSudoku();

	public abstract void difficulty();

	public abstract void printCells();

	public abstract void setCell(int col, int row, int guess);

	public String getGameType() {
		return gameType;
	}

	public abstract boolean testIfSolved();

	/**
	 * Getter und Setter für Instanzvariablen
	 * 
	 */

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public int getgamePoints() {
		return gamePoints;
	}

	public long getMinutesPlayed() {
		return minutesPlayed;
	}

	public long getSecondsPlayed() {
		return secondsPlayed;
	}

	public void setMinutesPlayed(long minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public void setSecondsPlayed(long secondsPlayed) {
		this.secondsPlayed = secondsPlayed;
	}

//	public void setLoadedMinutes(long loadedMinutesPlayed) {
//		this.loadedMinutesPlayed = loadedMinutesPlayed;
//	}
//
//	public void setLoadedSeconds(long loadedSecondsPlayed) {
//		this.loadedSecondsPlayed = loadedSecondsPlayed;
//	}

//	public long getLoadedMinutes() {
//		return loadedMinutesPlayed;
//	}
//
//	public long getLoadedSeconds() {
//		return loadedSecondsPlayed;
//	}

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

	public int getHintCounter() {
		return hintCounter;
	}

	public void setHintCounter(int hintCounter) {
		this.hintCounter = hintCounter;
	}

	public void setGameState(Gamestate gamestate) {
		this.gamestate = gamestate;
	};

	public Gamestate getGameState() {
		return this.gamestate;
	}

	public String getGameText() {
		if (this.getGameState() == Gamestate.OPEN) {
			gameText = "Game ongoing!";
		}
		if (this.getGameState() == Gamestate.DONE) {
			gameText = "Congratulations you won!";
		}
		if (this.getGameState() == Gamestate.INCORRECT) {
			gameText = "Sorry your Sudoku is not correct yet";
		}
		if (this.getGameState() == Gamestate.AutoSolved) {
			gameText = "Autosolved";
		}
		if (this.getGameState() == Gamestate.CONFLICT) {
			gameText = "Please remove the conflicts before autosolving";
		}
		if (this.getGameState() == Gamestate.UNSOLVABLE) {
			gameText = "Unsolvable Sudoku! New Solution generated";
		}

		return gameText;
	}

	public int getGameID() {
		return gameID;
	}

	public String getDifficultyString() {
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

	public long calculateGameTime() {
		long time;
		long endTime = System.currentTimeMillis();
		time = (endTime - getStartTime()) / 1000;
		// time += getLoadedMinutes() * 60 + getLoadedSeconds();
		setSecondsPlayed(time);
		if (time > 60) {
			setMinutesPlayed(time / 60);
			setSecondsPlayed(time % 60);
		}
		return time;
	}

	public void initializeTimer() {

		liveTimePlayedString = new SimpleStringProperty("");

		running = new SimpleBooleanProperty();

		timer = new AnimationTimer() {

			private LocalTime startTime;

			@Override
			public void handle(long now) {
				long elapsedSeconds = Duration.between(startTime, LocalTime.now()).getSeconds();
				minutesPlayed = elapsedSeconds / 60;
				secondsPlayed = elapsedSeconds % 60;

				liveTimePlayedString.set(String.format("%02d:%02d", minutesPlayed, secondsPlayed));
			}

			@Override
			public void start() {
				running.set(true);
				startTime = LocalTime.now();
				super.start();
			}

			@Override
			public void stop() {
				running.set(false);
				super.stop();
			}
		};

	}

	public AnimationTimer getLiveTimer() {
		return timer;
	}

	public StringProperty getStringProp() {
		return liveTimePlayedString;
	}
}
