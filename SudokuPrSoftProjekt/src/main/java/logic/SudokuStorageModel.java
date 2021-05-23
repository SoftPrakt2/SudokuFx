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


import application.GUI;
import application.Storage;
import javafx.stage.FileChooser;

public class SudokuStorageModel {

	
	BasicGameLogic savedModel;
	int saveCounter;

	Cell[][] gameArray;
	
	String savedGameType;
	int difficulty;
	int gamePoints;
	Gamestate gameState;
	long minutesPlayed;
	long secondsPlayed;
	String difficultyString;



	FileChooser chooser;

	SharedStoragePreferences storagePref = new SharedStoragePreferences();
	
	
	
	public SudokuStorageModel(BasicGameLogic savedModel) {
		this.savedModel = savedModel;
	}
	
	
	public void prepareSave() {
		gameArray = savedModel.getCells();
		savedGameType = savedModel.getGameType();
		difficulty = savedModel.getDifficulty();
		difficultyString = savedModel.getDifficultyString();
		gamePoints = savedModel.getgamePoints();
		gameState = savedModel.getGameState();
		minutesPlayed = savedModel.getMinutesPlayed();
		secondsPlayed = savedModel.getSecondsPlayed();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject storeIntoJSON() {

		JSONObject jsonObject;
	

		Gson gson = new Gson();

		String cellArray = gson.toJson(gameArray, Cell[][].class);

		
		jsonObject = new JSONObject();
		jsonObject.put("type", savedGameType);
		jsonObject.put("gameNumbers", cellArray);
		jsonObject.put("difficulty", difficulty);
		jsonObject.put("difficultyString", difficultyString);
		jsonObject.put("points", gamePoints);
		jsonObject.put("gameState", gameState);
		jsonObject.put("minutesPlayed", minutesPlayed);
		jsonObject.put("secondsPlayed", secondsPlayed);

	
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
	
	

	public void saveGame() {
		
		prepareSave();
		
//		if(savedGameType.equals("Sudoku")) {
//			saveCounter = SharedStoragePreferences.sudokuSaveCounter;
//			SharedStoragePreferences.sudokuSaveCounter += 1;
//		}
//		
//		
//		if(savedGameType.equals("Samurai"))  {
//			saveCounter = SharedStoragePreferences.samuraiSaveCounter;
//			SharedStoragePreferences.samuraiSaveCounter += 1;
//		}
//		if(savedGameType.equals("FreeForm")) {
//			saveCounter = SharedStoragePreferences.freeFormSaveCounter;
//			SharedStoragePreferences.freeFormSaveCounter += 1;
//		}
		

		storeIntoJSON();
		
		String fileName = savedGameType + "_" + difficultyString + "_" +saveCounter +".json";
		File saveFile = new File(storagePref.getPreferedDirectory(), fileName);
		storeIntoFile(saveFile);
	}

	public void exportGame() {
		prepareSave();
		storeIntoJSON();
		chooser = new FileChooser();

		chooser.setTitle("Export your Game");
		chooser.setInitialFileName("mygame");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showSaveDialog(GUI.getStage());
		storeIntoFile(file);
	}

	public void importGame() {
		chooser = new FileChooser();
		chooser.setTitle("Import your Game");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showOpenDialog(GUI.getStage());
		JSONObject obj = convertToJSON(file);
		
		String gameTypeHelper = (String)obj.get("type");
		
		System.out.println(gameTypeHelper);
		
//		if (obj.get("type").equals(savedModel.getGameType())) {
//			
//		}
		
		loadIntoModel(savedModel, obj);
		
		
		
	}

	

	

	public void loadIntoModel(BasicGameLogic savedModel, JSONObject obj) {
		Gson g = new Gson();
		Cell[][] cell = g.fromJson((String)obj.get("gameNumbers"), Cell[][].class);
		savedModel.setCells(cell);
		
		savedModel.setGamePoints((int) (long) obj.get("points"));
		savedModel.setDifficulty((int) (long) obj.get("difficulty"));
		
		savedModel.setStartTime(System.currentTimeMillis());
		savedModel.setLoadedSeconds((int) (long) obj.get("secondsPlayed"));
		savedModel.setLoadedMinutes((int) (long) obj.get("minutesPlayed"));
		savedModel.setDifficultyString();
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

	public String getDirectory() {
		return directoryPath;
	}

	public void setDirectory(String directoryPath) {
		this.directoryPath = directoryPath;
	}

}
