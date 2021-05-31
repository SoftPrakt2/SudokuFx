package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.GUI;
import javafx.stage.FileChooser;

public class SudokuStorageModel {

	BasicGameLogic savedModel;
	int saveCounter;

	Cell[][] gameArray;

	String gametype;
	String savedGameType;
	int difficulty;
	int gamePoints;
	Gamestate gameState;
	long minutesPlayed;
	long secondsPlayed;
	String difficultyString;
	String playTimeString;

	int gameID;
	int helper;

	
	JSONObject importedJson;

	FileChooser chooser;

	SharedStoragePreferences storagePref = new SharedStoragePreferences();

	File[] saveDirectory = new File(storagePref.getPreferedDirectory()).listFiles();

	public void prepareSave(BasicGameLogic save) {

		gameArray = save.getCells();
		savedGameType = save.getGameType();
		difficulty = save.getDifficulty();
		difficultyString = save.getDifficultyString();
		gamePoints = save.getgamePoints();
		gameState = save.getGameState();
		minutesPlayed = save.getMinutesPlayed();
		secondsPlayed = save.getSecondsPlayed();
		gameID = storagePref.getStoragePrefs().getInt("GameID", 1);

		helper = storagePref.getStoragePrefs().getInt("GameID", 0) + 1;

		storagePref.getStoragePrefs().putInt("GameID", helper);
	}

	@SuppressWarnings("unchecked")
	public JSONObject storeIntoJSON() {

		JSONObject jsonObject;

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String cellArray = gson.toJson(gameArray, Cell[][].class);
		String gameTypeGson = gson.toJson(savedGameType, String.class);
		String difficultyNumberGson = gson.toJson(difficulty, Integer.class);
		String difficutlyStringGson = gson.toJson(difficultyString, String.class);
		String gamePointsGson = gson.toJson(gamePoints, Integer.class);
		String gameStateGson = gson.toJson(gameState, Gamestate.class);
		String minutesPlayedGson = gson.toJson(minutesPlayed, Integer.class);
		String secondsPlayedGson = gson.toJson(secondsPlayed, Integer.class);

		String gameIDGson = gson.toJson(gameID, Integer.class);

		jsonObject = new JSONObject();
		jsonObject.put("type", gameTypeGson);
		jsonObject.put("gameNumbers", cellArray);
		jsonObject.put("difficulty", difficultyNumberGson);
		jsonObject.put("difficultyString", difficutlyStringGson);
		jsonObject.put("points", gamePointsGson);
		jsonObject.put("gameState", gameStateGson);
		jsonObject.put("minutesPlayed", minutesPlayedGson);
		jsonObject.put("secondsPlayed", secondsPlayedGson);
		jsonObject.put("gameIDGson", gameIDGson);

		return jsonObject;
	}

	public void storeIntoFile(File saveFile) {

		try {
			ObjectMapper mapper = new ObjectMapper();

			mapper.writeValue(saveFile, storeIntoJSON());

		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public void saveGame(BasicGameLogic save) {

		prepareSave(save);

		storeIntoJSON();

		String fileName = savedGameType + "_" + difficultyString + "_" + "ID_" + gameID + ".json";
		File saveFile = new File(storagePref.getPreferedDirectory(), fileName);
		storeIntoFile(saveFile);
	}

	public void exportGame(BasicGameLogic save) {
		prepareSave(save);
		storeIntoJSON();
		chooser = new FileChooser();

		chooser.setTitle("Export your Game");
		chooser.setInitialFileName("mygame");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showSaveDialog(GUI.getStage());
		storeIntoFile(file);
	}

	// filechooser anders machn is nicht so gut
	public void setImportedFile() {

		Gson g = new Gson();

		chooser = new FileChooser();
		chooser.setTitle("Import your Game");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showOpenDialog(GUI.getStage());
		importedJson = convertToJSON(file);
		gametype = g.fromJson((String) importedJson.get("type"), String.class);

	}

	public void setStoredInformations() {
		Gson g = new Gson();

		gametype = g.fromJson(importedJson.get("type").toString(), String.class);
		gamePoints = g.fromJson(importedJson.get("points").toString(), Integer.class);
		difficulty = g.fromJson((String) importedJson.get("difficulty"), Integer.class);
		difficultyString = g.fromJson((String) importedJson.get("difficultyString"), String.class);
		minutesPlayed = g.fromJson((String) importedJson.get("minutesPlayed"), Integer.class);
		secondsPlayed = g.fromJson((String) importedJson.get("secondsPlayed"), Integer.class);
		playTimeString = minutesPlayed + " min " + secondsPlayed + " s ";
		gameArray = g.fromJson((String) importedJson.get("gameNumbers"), Cell[][].class);
		gameState = g.fromJson((String) importedJson.get("gameState"), Gamestate.class);
	//	gameID = g.fromJson((String) importedJson.get("gameIDGson"), Integer.class);
	}

	public void loadIntoModel() {
		savedModel.setCells(gameArray);
		savedModel.setGamePoints(gamePoints);
		savedModel.setDifficulty(difficulty);
		savedModel.setStartTime(System.currentTimeMillis());
		savedModel.setSecondsPlayed(secondsPlayed);
		savedModel.setMinutesPlayed(minutesPlayed);
		savedModel.setDifficultyString();
		savedModel.initializeTimer();
	}

	public JSONObject convertToJSON(File file) {

		JSONObject jsonObject = new JSONObject();

		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader(file)) {
			Object obj = parser.parse(reader);
			jsonObject = (JSONObject) obj;
			parser.reset();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonObject;
	}

	public BasicGameLogic getLoadedLogic() {
		return savedModel;
	}

	public void setLoadedLogic(BasicGameLogic savedModel) {
		this.savedModel = savedModel;
	}

	public void setJSONObject(JSONObject importedJson) {
		this.importedJson = importedJson;
	}

	public String getLoadedGameType() {
		return gametype;
	}

	public File[] getSaveDirectory() {
		return saveDirectory;
	}

	public String getGametype() {
		return this.gametype;
	}

	public int getGamepoints() {
		return gamePoints;
	}

	public String getDifficultystring() {
		return this.difficultyString;
	}

	public String getPlaytimestring() {
		return playTimeString;
	}

	public Gamestate getGamestate() {
		return gameState;
	}
	
	public long getMinutesPlayed() {
		return minutesPlayed;
	}
	
	public long getSecondsPlayed() {
		return secondsPlayed;
	}
	
	

	public int getGameid() {
		return gameID;
	}

}
