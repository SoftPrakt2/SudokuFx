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
 * This class definies the saving and loading logic of the program
 * To do so several methods regarding saving, loading, importing and exporting are implemented
 */

public class SudokuStorage {
	
	//checks if a imported file exists
	private boolean fileExists = true;
	
	//checks if a game has already been saved
	private boolean gameAlreadySaved = false;
	
	private FileChooser chooser;

	SharedStoragePreferences storagePref = new SharedStoragePreferences();
	
	//save directory of the program
	File[] fileDirectory = new File("SaveFiles").listFiles();

	
	/**
	 * Auxiliary method sets the fields of an {@link logic.SaveModel} objects with informations
	 * from the parameter explained down below
	 * @param gameToSave specific game whose informations need to be stored inside a savemodel object
	 * @return SaveModel object configured with informations from the @param gameToSave                
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
		
		//if the game was never saved before give it an ID
		if(gameToSave.getGameid() < 1) {
		saveModel.setGameId(storagePref.getStoragePrefs().getInt("GameID", 1));
		helper = (saveModel.getGameId()+1);
		storagePref.getStoragePrefs().putInt("GameID", helper);
		
		//safety mechanism to ensure that when first saving the game and pressing multiple times the save button 
		//in the menu only one game is saved
		gameToSave.setGameID(saveModel.getGameId());
		
		
		} else {
			saveModel.setGameId(gameToSave.getGameid());
		}
		
		
	
		return saveModel;
	}
	

	/**
	 * This method handles the actual saving process, in an object of the {@link logic.SaveModel} class
	 * the needed informations about the to be saved game are stored
	 * The saveFile File object then is filled with the informations stored in the {@link logic.SaveModel} object
	 * To write the specific file a JSONWriter and a Filewriter is needed
	 * @param gameToSave specific game which should be saved
	 */
	public void saveGame(BasicGameLogic gameToSave) {
	
		SaveModel saveModel = setInformationsToStore(gameToSave);
		Gson gson = new GsonBuilder().create();
		JsonWriter writer;
		FileWriter fw;

		String fileName = "ID_" + saveModel.getGameId() + "_" + saveModel.getGametype() + "_"
				+ saveModel.getDifficultyString() + ".json";
		
		
		File saveFile = new File("SaveFiles", fileName);
		//if game id already exists overwrite the corresponding file
		for(File file : fileDirectory) {
			if (file.getName().endsWith(".json")) {	
			SaveModel help = this.convertFileToSaveModel(file);
			if(help.getGameId() == gameToSave.getGameid()) {
				gameAlreadySaved = true;
				try {
					fw = new FileWriter(file);
					writer = new JsonWriter(fw);
					gson.toJson(saveModel, SaveModel.class, writer);
					fw.close();
					writer.close();
				} catch (IOException e) {
					  System.err.print("The game could not be saved");
				}
			}
		}
		}
		
	//if game was never saved before create new file
	if(!gameAlreadySaved) {
		try {
			fw = new FileWriter(saveFile);
			writer = new JsonWriter(fw);
			gson.toJson(saveModel, SaveModel.class, writer);
			fw.close();
			writer.close();
		} catch (IOException e) {
			  System.err.print("The game could not be saved");
		}
	}
	}

	/**
	* This method handles the actual export process, in an object of the {@link logic.SaveModel} class
	 * the needed informations about the to be exported game are stored.
	 * The saveFile File object then is filled with the informations stored in the {@link logic.SaveModel} object.
	 * To locate the path where the user wants to save the file an object of the FileChooser class is initialized 
	 * and an object of the Jsonwriter class is used to write the game informations to the file.
	 * 
	 * @param gameToExport specific game which should be exported
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
			 System.err.print("The game could not be exported");
		}
	}
	
	
	/**
	 * This method is needed to extract the needed informations of a file the user wants to import
	 * @return {@link logic.SaveModel} object with the informations stored from the imported file
	 */
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
	
	
	/**
	 * This method is used to load the informations stored in a {@link logic.SaveModel} object into an 
	 * object of the  {@link logic.BasicGameLogic} class
	 * Depending on the saved gameType the corresponding GameBuilder will be initialized
	 * @param model which should filled with informations from the saved game
	 * @param savedGame {@link logic.SaveModel} object which contains the informations which should be loaded into the model parameter explained above
	 * @return the BasicGameLogic model which is now filled with the saved informations
	 */
	public BasicGameLogic loadIntoModel(BasicGameLogic model, SaveModel savedGame) {
		if (savedGame.getGametype().equals("Sudoku"))
			model = new SudokuLogic(savedGame.getGameState(), savedGame.getMinutesPlayed(),
					savedGame.getSecondsPlayed());
		
		if (savedGame.getGametype().equals("Samurai"))
			model = new SamuraiLogic(savedGame.getGameState(), savedGame.getMinutesPlayed(),
					savedGame.getSecondsPlayed());
		
		if (savedGame.getGametype().equals("FreeForm"))
			model = new FreeFormLogic(savedGame.getGameState(), savedGame.getMinutesPlayed(),
					savedGame.getSecondsPlayed());
		
		
		model.setGametype(savedGame.getGametype());
		model.setCells(savedGame.getGameArray());
		model.setGamePoints(savedGame.getGamePoints());
		model.setDifficulty(savedGame.getDifficulty());
		model.setDifficultyString();
		model.setGameID(savedGame.getGameId());
		model.setPlaytimestring(String.format("%02d:%02d", model.getMinutesplayed(), model.getSecondsplayed()));
		return model;
	}
	
	
	
	/**
	 * This auxiliary method is needed to extract the needed informations of a file the user wants to import
	 * @return an object of the {@link logic.SaveModel} class 
	 */
	public SaveModel convertFileToSaveModel(File file) {
		SaveModel data = new SaveModel();
		Gson gson = new GsonBuilder().create();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			data = gson.fromJson(br, SaveModel.class);
			closeBufferedReader(br);
		} catch (FileNotFoundException e) {
			System.err.print("The file was not found");
		}
		return data;
	}
	

	
	/**
	 * this auxiliary method is needed to close the reader used to read informations from a file
	 */
	public void closeBufferedReader(BufferedReader br) {
		try {
			br.close();
		} catch (IOException e) {
			System.err.print("Reader could not be closed");
		}
	}
	
	
	public boolean fileExists() {
		return fileExists;
	}
	
	
	
	
}
	
	
	
	
	


