package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import application.GUI;
import javafx.stage.FileChooser;




/**
 * 
 * @author grube
 * Diese Klasse definiert die generelle Speicherlogik hierbei werden Methoden 
 * welche das Speichern und Laden eines Spiels ermöglichen definiert
 */


public class SudokuStorageModel {

	//private SaveModel saveModel;

	private boolean fileExists = true;

	private FileChooser chooser;

	SharedStoragePreferences storagePref = new SharedStoragePreferences();

	/**
	 * Diese Methode überträgt Informationen in ein Objekt der SaveModel Klasse
	 * @param gameToSave Spiel Model dessen Informationen in das SaveModel geladen werden sollen
	 */
	public SaveModel setInformationsToStore(BasicGameLogic gameToSave) {
		SaveModel saveModel = new SaveModel();
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

		helper = storagePref.getStoragePrefs().getInt("GameID", 0) + 1;
		storagePref.getStoragePrefs().putInt("GameID", helper);
		return saveModel;
	}

	
	/**
	 * Diese Methode übernimmt den tatsächlichen Speichervorgang, hierfür wird ein Objekt 
	 * der SaveModel Klasse erstellt, dieses wird mit der {@link #setInformationsToStore(BasicGameLogic)} Methode befüllt
	 * Anschließend wird mit einem JSONWriter und FileWriter die tatsächliche Datei geschrieben
	 * @param gameToSave
	 */
	public void saveGame(BasicGameLogic gameToSave) {
		SaveModel saveModel = setInformationsToStore(gameToSave);
		Gson gson = new GsonBuilder().create();
		
		String fileName = "ID_" + saveModel.getGameId() + "_" + saveModel.getGametype() + "_"
				+ saveModel.getDifficultyString() + ".json";

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
	
	
	/**
	 * Diese Methode übernimmt den tatsächlichen ExportVorgang, hierfür wird ein Objekt 
	 * der SaveModel Klasse erstellt, dieses wird mit der {@link #setInformationsToStore(BasicGameLogic)} Methode befüllt
	 * 
	 * @param gameToExport
	 */
	public void exportGame(BasicGameLogic gameToExport) {
		Gson gson = new GsonBuilder().create();
		SaveModel saveModel = setInformationsToStore(gameToExport);
		chooser = new FileChooser();

		chooser.setTitle("Export your Game");
		chooser.setInitialFileName("mygame");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		File file = chooser.showSaveDialog(GUI.getStage());
		JsonWriter writer;
		try {
			if (file != null) {
				writer = new JsonWriter(new FileWriter(file));
				gson.toJson(saveModel, SaveModel.class, writer);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public SaveModel getImportedFile() {
		
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
			closeBufferedReader(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	public void closeBufferedReader(BufferedReader br) {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean fileExists() {
		return fileExists;
	}

}
