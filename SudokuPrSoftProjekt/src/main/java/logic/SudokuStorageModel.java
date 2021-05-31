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

	private BasicGameLogic savedModel;
	private int saveCounter;

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
	private	int helper;

	FileChooser chooser;

	SharedStoragePreferences storagePref = new SharedStoragePreferences();

	File[] saveDirectory = new File(storagePref.getPreferedDirectory()).listFiles();

	public void prepareSave(BasicGameLogic save) {

		gameArray = save.getCells();
		savedGameType = save.getGametype();
		difficulty = save.getDifficulty();
		difficultyString = save.getDifficultystring();
		gamePoints = save.getGamepoints();
		gameState = save.getGamestate();
		minutesPlayed = save.getMinutesplayed();
		secondsPlayed = save.getSecondsplayed();
		gameId = storagePref.getStoragePrefs().getInt("GameID", 1);

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

		String gameIDGson = gson.toJson(gameId, Integer.class);

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

		String fileName = savedGameType + "_" + difficultyString + "_" + "ID_" + gameId + ".json";
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
	public JSONObject setImportedFile() {

		Gson g = new Gson();

		chooser = new FileChooser();
		chooser.setTitle("Import your Game");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showOpenDialog(GUI.getStage());
		JSONObject importedJson = convertToJSON(file);
		return importedJson;
	}
	

	public void setStoredInformations(JSONObject obj) {

		Gson g = new Gson();
		
		gametype = g.fromJson(obj.get("type").toString(), String.class);
		
		gamePoints = g.fromJson(obj.get("points").toString(), Integer.class);
		difficulty = g.fromJson((String) obj.get("difficulty"), Integer.class);
		difficultyString = g.fromJson((String) obj.get("difficultyString"), String.class);
		minutesPlayed = g.fromJson((String) obj.get("minutesPlayed"), Integer.class);
		secondsPlayed = g.fromJson((String) obj.get("secondsPlayed"), Integer.class);
		playTimeString = minutesPlayed + " min " + secondsPlayed + " s ";
		gameArray = g.fromJson((String) obj.get("gameNumbers"), Cell[][].class);
		gameState = g.fromJson((String) obj.get("gameState"), Gamestate.class);
		gameId = g.fromJson((String) obj.get("gameIDGson"), Integer.class);
		
	}

	
	public BasicGameLogic loadIntoModel(BasicGameLogic model) {
		if(gametype.equals("Sudoku")) model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
		if (gametype.equals("Samurai")) model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
		model.setGametype(gametype);
		model.setCells(gameArray);
		model.setGamePoints(gamePoints);
		model.setDifficulty(difficulty);
		model.setStartTime(System.currentTimeMillis());
		model.setSecondsPlayed(secondsPlayed);
		model.setMinutesPlayed(minutesPlayed);
		model.setDifficultyString();
		model.initializeTimer();
		model.setGameID(gameId);
		model.setPlaytimestring(String.format("%02d:%02d", minutesPlayed, secondsPlayed));
		return model;
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

	

}
