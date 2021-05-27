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
	
	
	String gametype = "";
	String savedGameType;
	int difficulty;
	int gamePoints;
	Gamestate gameState;
	long minutesPlayed;
	long secondsPlayed;
	String difficultyString;

	JSONObject importedGame;

	FileChooser chooser;
	
	
	int playedMinutesOverall;
	int playedSecondsOverall;
	double averagePoints; 
	double overAllPoints;
	

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
	
	

	public void saveGame(BasicGameLogic save) {
		
		prepareSave(save);
		
		storeIntoJSON();
		
		String fileName = savedGameType + "_" + difficultyString + "_" +System.currentTimeMillis() +".json";
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
	
	
	
	
	
	//filechooser anders machn is nicht so gut
	public void getImportedGame() {
		chooser = new FileChooser();
		chooser.setTitle("Import your Game");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showOpenDialog(GUI.getStage());
		importedGame = convertToJSON(file);
		gametype = (String) importedGame.get("type");
		
	}
	
	

	public void loadIntoModel() {
		gametype = (String) importedGame.get("type");

		Gson g = new Gson();
		Cell[][] cell = g.fromJson((String)importedGame.get("gameNumbers"), Cell[][].class);
		
		savedModel.setCells(cell);
		
		savedModel.setGamePoints((int) (long) importedGame.get("points"));
		savedModel.setDifficulty((int) (long) importedGame.get("difficulty"));
		
		savedModel.setStartTime(System.currentTimeMillis());
		savedModel.setLoadedSeconds((int) (long) importedGame.get("secondsPlayed"));
		savedModel.setLoadedMinutes((int) (long) importedGame.get("minutesPlayed"));
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
	
	
	
	public double calculateGamePoints() {
//		JSONObject helpObject = controller.convertToJSON(saveFile);
//		double gamePoints = 0;
//		JSONArray helpArray = (JSONArray) helpObject.get("games");
//
//		for (int i = 0; i < helpArray.size(); i++) {
//			JSONObject obj = (JSONObject) helpArray.get(i);
//			gamePoints += (double) (long) obj.get("points");
//		}
//		return gamePoints;
		return 5;
	}

	public String calculateAverageTimePlayed() {
//		JSONObject helpObject = controller.convertToJSON(saveFile);
//		JSONArray helpArray = (JSONArray) helpObject.get("games");
//		String averageGameTimeString = "";
//		int minPlayed = 0;
//		int secPlayed = 0;
//		int counter = 0;
//		int showMinutes;
//		int showSeconds;
//
//		for (int i = 0; i < helpArray.size(); i++) {
//			JSONObject obj = (JSONObject) helpArray.get(i);
//			minPlayed += (int) (long) (obj.get("minutesPlayed")) * 60;
//			secPlayed += (int) (long) (obj.get("secondsPlayed"));
//			counter++;
//		}
//		if (counter > 0) {
//			int playTime = (minPlayed + secPlayed) / counter;
//			showMinutes = playTime / 60;
//			showSeconds = playTime % 60;
//			averageGameTimeString = showMinutes + " minutes " + showSeconds + " seconds ";
//		}

		return "";
	}
	
	
	public void calculateGameStats() {
		JSONObject helpJSON;
		int fileCounter = 0;
		
		for (File file : saveDirectory) {
			helpJSON = convertToJSON(file);
			overAllPoints += (double) (long)helpJSON.get("points");
			
			playedMinutesOverall += (int) (long) (helpJSON.get("minutesPlayed")) * 60;
			playedSecondsOverall += (int) (long) (helpJSON.get("secondsPlayed"));
			
	
			fileCounter++;
		}
		if(fileCounter == 0) fileCounter = 1;
		int helpCalculate = (playedMinutesOverall + playedSecondsOverall) / fileCounter;
		
		playedMinutesOverall = helpCalculate/60;
		playedSecondsOverall = helpCalculate %60;
		
		averagePoints = gamePoints/fileCounter;
		
	}
	
	
	
	public BasicGameLogic getLoadedLogic() {
		return savedModel;
	}
	
	
	public void setLoadedLogic(BasicGameLogic savedModel) {
		this.savedModel = savedModel;
	}
	
	
	public void setJSONObject(JSONObject importedGame) {
		this.importedGame = importedGame;
	}
	
	public String getLoadedGameType() {
		return gametype;
	}
	
	public File[] getSaveDirectory() {
		return saveDirectory;
	}
	
	
	public double getOverallGamePoints() {
		return overAllPoints;
	}
	
	public double getAveragePoints() {
		return averagePoints;
	}
	
	public int getPlayedMinutesOverall() {
		return playedMinutesOverall;
	}
	
	public int getSecondsPlayedOverall() {
		return playedSecondsOverall;
	}

}
