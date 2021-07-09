package logic;


/**
 * This class defines the content which is in a saved File
 * Due to the complexitiy of de- and serializing abstract classes like the {@link application.BasicGameLogic} class
 * the developers decided to implement this auxiliary class.
 * An object of this class contains all informations which are needed to save and load a game.
 * These objects are put into a File during the save/export procedure
 * @author grube
 *
 */

public class SaveModel  {
	
	private Cell[][] gameArray;
	private String gametype;
	private int difficulty;
	private int gamePoints;
	private Gamestate gameState;
	private long minutesPlayed;
	private long secondsPlayed;
	private String difficultyString;
	private String playTimeString;
	private int gameId;

	
	public Cell[][] getGameArray() {
		return gameArray;
	}
	
	public void setGameArray(Cell[][] gameArray) {
		this.gameArray = gameArray;
	}

	public String getGametype() {
		return gametype;
	}

	public void setGametype(String gametype) {
		this.gametype = gametype;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getGamePoints() {
		return gamePoints;
	}

	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}

	public Gamestate getGameState() {
		return gameState;
	}

	public void setGameState(Gamestate gameState) {
		this.gameState = gameState;
	}

	public long getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed(long minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public long getSecondsPlayed() {
		return secondsPlayed;
	}

	public void setSecondsPlayed(long secondsPlayed) {
		this.secondsPlayed = secondsPlayed;
	}

	public String getDifficultyString() {
		return difficultyString;
	}

	public void setDifficultyString(String difficultyString) {
		this.difficultyString = difficultyString;
	}

	public String getPlayTimeString() {
		return playTimeString;
	}

	public void setPlayTimeString(String playTimeString) {
		this.playTimeString = playTimeString;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	
	
	
	
	
	
}
