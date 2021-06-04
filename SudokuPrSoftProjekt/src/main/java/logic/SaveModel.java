package logic;

public class SaveModel  {
	
	

	private Cell[][] gameArray;
	private String gametype;
	private String savedGameType;
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
