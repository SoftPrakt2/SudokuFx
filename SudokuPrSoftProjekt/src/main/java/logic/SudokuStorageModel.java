package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.GUI;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;

public class SudokuStorageModel {

	BasicGameLogic savedModel;
	static int saveCounter = 1;

	ArrayList<String> gameArray = new ArrayList<>();
	ArrayList<Boolean> playAbleArray = new ArrayList<>();
	String savedGameType;
	int difficulty;
	int gamePoints;
	Gamestate gameState;
	long minutesPlayed;
	long secondsPlayed;
	String difficultyString;

	 String directoryPath = "/D:/SudokuGames";
	
	 FileChooser chooser;
	

	public SudokuStorageModel(BasicGameLogic savedModel) {
		this.savedModel = savedModel;
	}

	public void saveGame() {
		prepareSave();
		
		storeIntoJSON();
		
		String fileName = savedGameType + difficultyString + ".json";
		File saveFile = new File(directoryPath, fileName);
		
		storeIntoFile(saveFile);
	}
	
	public void exportGame() {
		prepareSave();
		storeIntoJSON();
		chooser = new FileChooser();
		
		chooser.setTitle("Export your Game");
		chooser.setInitialFileName("mygame");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON","*.json"));
		File file = chooser.showSaveDialog(GUI.getStage());
		storeIntoFile(file);
	}
	
	
	public void importGame() {
		chooser = new FileChooser();
		chooser.setTitle("Import your Game");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON","*.json"));
		File file = chooser.showOpenDialog(GUI.getStage());
		loadIntoModel(savedModel, convertToJSON(file));
	}
	
	
	
	
	public void loadIntoModel(BasicGameLogic savedModel, JSONObject obj) {
		savedModel.setUpLogicArray();
		savedModel.setGamePoints((int) (long) obj.get("points"));

		savedModel.setDifficulty((int) (long) obj.get("difficulty"));

		savedModel.setStartTime(System.currentTimeMillis());
		savedModel.setLoadedSeconds((int) (long) obj.get("secondsPlayed"));
		savedModel.setLoadedMinutes((int) (long) obj.get("minutesPlayed"));

		JSONArray numbersArray = (JSONArray) obj.get("gameNumbers");
		JSONArray booleanArray = (JSONArray) obj.get("playAble");

		Iterator<String> iterator = numbersArray.iterator();
		Iterator<Boolean> booleanIterator = booleanArray.iterator();

		for (Cell[] cellArray : savedModel.getCells()) {
			for (Cell c : cellArray) {

				c.setValue(Integer.parseInt(iterator.next()));
				if (booleanIterator.next() == true) {
					c.setIsReal(true);
				}
			}
		}
	}
	
	
	
	

	public void prepareSave() {
		// stores the numbers of the playfield and the value for locking fields into the
		// help variables
		for (Cell[] cellArray : savedModel.getCells()) {
			for (Cell c : cellArray) {
				gameArray.add(Integer.toString(c.getValue()));
				playAbleArray.add(c.getIsReal());
			}
		}

		savedGameType = savedModel.getGameType();
		difficulty = savedModel.getDifficulty();
		difficultyString = savedModel.getDifficultyString();
		gamePoints = savedModel.getgamePoints();
		gameState = savedModel.getGameState();
		minutesPlayed = savedModel.getMinutesPlayed();
		secondsPlayed = savedModel.getSecondsPlayed();
	}
	
	
	

	public JSONObject storeIntoJSON() {
	
		
		JSONObject jsonObject;
		saveCounter++;
		jsonObject = new JSONObject();
		jsonObject.put("type", savedGameType);
		jsonObject.put("gameNumbers", gameArray);
		jsonObject.put("playAble", playAbleArray);
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
	
	
	public JSONObject convertToJSON(File file) {

		JSONObject jsonObject = new JSONObject();

		JSONParser parser = new JSONParser();

		try(Reader reader = new FileReader(file)) {
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
