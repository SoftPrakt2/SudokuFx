package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import application.GUI;
import javafx.stage.FileChooser;

public class SudokuStorageModel {

	private SaveModel saveModel;

	private boolean fileExists = true;

	private FileChooser chooser;
	
	static int counter = 9;

	SharedStoragePreferences storagePref = new SharedStoragePreferences();

	public void setInformationsToStore(BasicGameLogic gameToSave) {
		int helper;
		saveModel.setGameArray(gameToSave.getCells());
		saveModel.setGametype(gameToSave.getGametype());
		saveModel.setDifficulty(gameToSave.getDifficulty());
		saveModel.setDifficultyString(gameToSave.getDifficultystring());
		saveModel.setGamePoints(gameToSave.getGamepoints());
		saveModel.setGameState(gameToSave.getGamestate());
		saveModel.setMinutesPlayed(gameToSave.getMinutesplayed());
		saveModel.setSecondsPlayed(gameToSave.getSecondsplayed());
		saveModel.setGameId(storagePref.getStoragePrefs().getInt("GameID", 1));
		counter++;
		helper = storagePref.getStoragePrefs().getInt("GameID", 0) + 1;
		storagePref.getStoragePrefs().putInt("GameID", helper);
	}

	
	public void saveGame(BasicGameLogic gameToSave) {
		
		Gson gson = new GsonBuilder().create();
		saveModel = new SaveModel();
		setInformationsToStore(gameToSave);

		String fileName = saveModel.getGametype() + "_" + saveModel.getDifficultyString() + "_" + "ID_"
				+ saveModel.getGameId() + ".json";
		
		File saveFile = new File("SaveFiles", fileName);
	
		
		JsonWriter writer;
		FileWriter fw;
		try {
			fw = new FileWriter(saveFile);
			writer = new JsonWriter(fw);
			gson.toJson(saveModel, SaveModel.class, writer);
			fw.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void exportGame(BasicGameLogic save) {
		Gson gson = new GsonBuilder().create();
		saveModel = new SaveModel();
		setInformationsToStore(save);
		chooser = new FileChooser();

		chooser.setTitle("Export your Game");
		chooser.setInitialFileName("mygame");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showSaveDialog(GUI.getStage());
		JsonWriter writer;
		try {
			writer = new JsonWriter(new FileWriter(file));
			gson.toJson(saveModel, SaveModel.class, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// filechooser anders machn is nicht so gut
	public SaveModel getImportedFile() {
		// JSONObject importedJson = new JSONObject();
		SaveModel importedGame = new SaveModel();
		chooser = new FileChooser();
		chooser.setTitle("Import your Game");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showOpenDialog(GUI.getStage());
		if (file != null) {
			importedGame = convertFileToSaveModel(file);
		} else {
			fileExists = false;
		}
		return importedGame;
	}

	
	
	
	public BasicGameLogic loadIntoModel(BasicGameLogic model, SaveModel savedGame) {
		if (savedGame.getGametype().equals("Sudoku"))
			model = new SudokuLogic(savedGame.getGameState(), savedGame.getMinutesPlayed(),
					savedGame.getSecondsPlayed(), false);
		if (savedGame.getGametype().equals("Samurai"))
			model = new SamuraiLogic(savedGame.getGameState(), savedGame.getMinutesPlayed(),
					savedGame.getSecondsPlayed(), false);
		if (savedGame.getGametype().equals("FreeForm"))
			model = new FreeFormLogic(savedGame.getGameState(), savedGame.getMinutesPlayed(),
					savedGame.getSecondsPlayed(), false);
		model.setGametype(savedGame.getGametype());
		model.setCells(savedGame.getGameArray());
		model.setGamePoints(savedGame.getGamePoints());
		model.setDifficulty(savedGame.getDifficulty());
		model.setDifficultyString();
		model.setGameID(savedGame.getGameId());
		model.setPlaytimestring(String.format("%02d:%02d", model.getMinutesplayed(), model.getSecondsplayed()));
		return model;
	}

	
	
	
	public SaveModel convertFileToSaveModel(File file) {
		SaveModel data = new SaveModel();
		Gson gson = new GsonBuilder().create();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			data = gson.fromJson(br, SaveModel.class);
			try {
				br.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public boolean fileExists() {
		return fileExists;
	}

}
