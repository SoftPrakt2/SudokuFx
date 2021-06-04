
package logic;

import java.time.Duration;
import java.time.LocalTime;

import com.google.gson.annotations.Expose;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
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

	private String difficultyString;
	
	public String playtimeString;
	

	
	protected int hintCounter;
	protected int countHintsPressed = 0;

	protected int difficulty;

	protected StringProperty liveTimePlayedString;

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
	
	public abstract boolean isConnected();
	

	
	
	public void initializeCustomGame() {
		setUpLogicArray();
	//	createSudoku();
		setDifficulty(0);
		setDifficultyString();
		difficulty();
		if(this instanceof FreeFormLogic) {
		setGameState(Gamestate.DRAWING);
		} else setGameState(Gamestate.CREATING);
	}
	
	
	
	public void setUpGameInformations() {
		setStartTime(System.currentTimeMillis());
		setGamePoints(10);
		setGameState(Gamestate.OPEN);
		setDifficultyString();
		setHintsPressed(0);
		setMinutesPlayed(0);
		setSecondsPlayed(0);
		initializeTimer();
		getLiveTimer().start();
	}
	
	

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
	

	
	public abstract boolean testIfSolved();

	/**
	 * Getter und Setter f�r Instanzvariablen
	 * 
	 */
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

	public int getHintCounter() {
		return hintCounter;
	}

	public void setHintCounter(int hintCounter) {
		this.hintCounter = hintCounter;
	}
	
	public int getHintsPressed() {
        return countHintsPressed;
    }

    public void setHintsPressed(int countHintsPressed) {
        this.countHintsPressed = countHintsPressed;
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
		if(this.getGamestate() == Gamestate.CREATING) {
			gameText = "Create your own game!";
		}
		
		if(this.getGamestate() == Gamestate.OPEN) {
			gameText = "Game ongoing!";
		}
		if(this.getGamestate() == Gamestate.DRAWING) {
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
                minutesPlayed = ((helpmin*60 + helpsec + elapsedSeconds)/60);
                secondsPlayed = (helpsec +  helpmin*60 + elapsedSeconds) % 60;
                
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

	public AnimationTimer getLiveTimer() {
		return timer;
	}
	
	public boolean timerIsRunning() {
		return timerIsRunning.get();
	}
	
	
	public abstract void shuffle();
	

	public StringProperty getStringProp() {
		return liveTimePlayedString;
	}
}
