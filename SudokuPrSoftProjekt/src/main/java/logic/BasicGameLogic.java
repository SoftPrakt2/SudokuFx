//package logic;
//
//public abstract class BasicGameLogic{
//
//	private Gamestate gamestate;
//	private double timer;
//	private boolean isCorrect;
//	
//	public BasicGameLogic(Gamestate gamestate, double timer, boolean isCorrect) {
//		super();
//		this.gamestate = gamestate;
//		this.timer = timer;
//		this.isCorrect = isCorrect;
//	}
//	
//	public boolean valid(){
//		if (checkRows() == true && checkCols() == true && checkBoxs() == true ) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	public abstract boolean checkRows();
//	public abstract boolean checkCols();
//	public abstract boolean checkBoxs();
//	
//	
//	public boolean validUser(){
//		if (checkRowsUser() == true && checkColsUser() == true && checkBoxsUser() == true ) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	public abstract boolean checkRowsUser();
//	public abstract boolean checkColsUser();
//	public abstract boolean checkBoxsUser();
//	
//	
//	public abstract void autofill();
//	
//	public abstract void fill();
//	
//	public abstract void hint();
//	
//	public abstract void autosolve();
//	
//	public abstract void difficulty(int diff);
//	
//	public abstract void printCells();
//}

package logic;

/**
 * 
 * Stellt die Basisvariablen und Methoden für die ableitenden Logiken dar
 * Deklariert abstract Methoden, welche von den Unterklassen implementiert werden müssen.
 *
 */
public abstract class BasicGameLogic {

	protected Gamestate gamestate;
	private boolean isCorrect;

	
	private int gamePoints = 10;
	private long minutesPlayed;
	private long secondsPlayed;
	private long startTime;
	private String gameText = "";
	private int gameID = 0;
	
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

	public abstract void difficulty(int diff);

	public abstract void printCells();

	public abstract Cell[][] getCells();

	public abstract void setCell(int col, int row, int guess);

	public abstract void setGameState(Gamestate gamestate);

	public abstract Gamestate getGameState();

	public abstract boolean testIfSolved();
	/**
	 * Getter und Setter für Instanzvariablen
	 * 
	 */
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

	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public String getGameText() {
        if(this.getGameState() == Gamestate.OPEN) {
            gameText = "Game ongoing!";
        }
        if(this.getGameState() == Gamestate.DONE) {
            gameText = "Congratulations you won!";
        }
        if(this.getGameState() == Gamestate.INCORRECT) {
            gameText = "Sorry your Sudoku is not correct yet";
        }
        if(this.getGameState() == Gamestate.AutoSolved) {
            gameText = "Autosolved";
        }
        if(this.getGameState() == Gamestate.CONFLICT) {
            gameText = "Please remove the conflicts before autosolving";
        }
        if(this.getGameState() == Gamestate.UNSOLVABLE) {
        	gameText = "Unsolvable Sudoku! New Solution generated";
        }
        
        return gameText;
    }
	
	public int getGameID() {
		return gameID;
	}
	
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	
}