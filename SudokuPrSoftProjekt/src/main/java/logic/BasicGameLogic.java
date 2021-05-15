
package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.Storage;
import application.SudokuField;

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
	protected long loadedMinutesPlayed;
	protected long loadedSecondsPlayed;
	protected long startTime;
	protected int gameIDhelper;
	public String gameText = "";
	private int gameID = 0;
	
	protected int hintCounter;
	

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

	public void setLoadedMinutes(long loadedMinutesPlayed) {
		this.loadedMinutesPlayed = loadedMinutesPlayed;
	}

	public void setLoadedSeconds(long loadedSecondsPlayed) {
		this.loadedSecondsPlayed = loadedSecondsPlayed;
	}

	public long getLoadedMinutes() {
		return loadedMinutesPlayed;
	}

	public long getLoadedSeconds() {
		return loadedSecondsPlayed;
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

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public void saveGame() {
		Storage storage = new Storage();
		File file = storage.getSaveFile();
		
		JSONObject jsonFile = storage.convertToJSON(file);
		JSONArray jsonArray = (JSONArray) jsonFile.get("games");
		JSONObject newJSONGameData = new JSONObject();
		ArrayList<String> gameArray = new ArrayList<>();
		ArrayList<Boolean> playAbleArray = new ArrayList<>();

		for (Cell[] cellArray : this.getCells()) {
			for (Cell c : cellArray) {
				gameArray.add(Integer.toString(c.getValue()));
				playAbleArray.add(c.getIsReal());
			}
		}

		if (modelIDexists()) {
			JSONArray savedGamesArray = (JSONArray) storage.convertToJSON(file).get("games");
			for (int i = 0; i < savedGamesArray.size(); i++) {
				JSONObject overwrittenGame = (JSONObject) savedGamesArray.get(i);
				if ((int) (long) overwrittenGame.get("gameID") == gameIDhelper) {

					System.out.println("hiiiiiiiiiiiiii");
					overwrittenGame.remove("gameNumbers");
					overwrittenGame.remove("playAble");
					overwrittenGame.remove("points");
					overwrittenGame.remove("gameState");
					overwrittenGame.remove("secondsPlayed");
					overwrittenGame.remove("minutesPlayed");

					overwrittenGame.put("gameNumbers", gameArray);
					overwrittenGame.put("playAble", playAbleArray);
					overwrittenGame.put("points", getgamePoints());
					overwrittenGame.put("gameState", getGameState());
					overwrittenGame.put("secondsPlayed", secondsPlayed);
					overwrittenGame.put("minutesPlayed", minutesPlayed);

					jsonArray.remove(i);
					jsonArray.add(overwrittenGame);
				}
			}
		} else {
			System.out.println("test");

			newJSONGameData.put("type", gameType);
			newJSONGameData.put("gameNumbers", gameArray);
			newJSONGameData.put("playAble", playAbleArray);
			newJSONGameData.put("difficulty", "easy");
			newJSONGameData.put("points", getgamePoints());
			newJSONGameData.put("gameState", getGameState());
			newJSONGameData.put("minutesPlayed", getMinutesPlayed());
			newJSONGameData.put("secondsPlayed", getSecondsPlayed());
			jsonArray.add(newJSONGameData);

			newJSONGameData.put("gameID", storage.getLastGameID(file) + 1);
			gameID++;

			System.out.println(storage.getLastGameID(file));
		}
		jsonFile.put("games", jsonArray);

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(file, jsonFile);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	// load funktion
	public void loadIntoModel(JSONArray json, JSONArray json2) {
		setUpLogicArray();

		Iterator<String> iterator = json.iterator();
		Iterator<Boolean> booleanIterator = json2.iterator();

		for (Cell[] cellArray : this.getCells()) {
			for (Cell c : cellArray) {

				c.setValue(Integer.parseInt(iterator.next()));
				if (booleanIterator.next() == true) {
					c.setIsReal(true);
				}
			}
		}
	}

	public boolean modelIDexists() {
		Storage storage = new Storage();
		File file = storage.getSaveFile();
		JSONObject obj = storage.convertToJSON(file);
		JSONArray gameArray = (JSONArray) obj.get("games");

		for (int i = 0; i < gameArray.size(); i++) {
			JSONObject helper = (JSONObject) gameArray.get(i);
			int id = (int) (long) helper.get("gameID");
			if (id == getGameID()) {
				gameIDhelper = id;
				return true;
			}
		}
		return false;
	}
}
