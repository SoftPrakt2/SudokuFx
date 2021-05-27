package controller;

import java.io.File;

import org.json.simple.JSONObject;

import application.BasicGameBuilder;
import application.GUI;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SharedStoragePreferences;
import logic.SudokuLogic;
import logic.SudokuStorageModel;

public class StorageController {

	Scene playScene;
	BasicGameBuilder game;
	BasicGameLogic model;

	String gameIdentifier;
	SudokuStorageModel storageModel;

	Scene gameScene;
	Storage storage;
	File[] dir;
	

	SharedStoragePreferences sharedStorage = new SharedStoragePreferences();

	public StorageController(Storage storage) {
		this.storage = storage;
		storageModel = new SudokuStorageModel();
		dir = new File(sharedStorage.getPreferedDirectory()).listFiles();
	}

	// test
	public void handleLoadAction(ActionEvent e) {
		String helper = "";

		for (String key : storage.getSaveMap().keySet()) {
			if (key.equals(storage.getListView().getSelectionModel().getSelectedItem())) {
				helper = key;
				// gib json object der storagemodel
				storageModel.setJSONObject(storage.getSaveMap().get(key));
				if (storage.getSaveMap().get(key).get("type").equals("Sudoku")) {
					model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
					game = new SudokuGameBuilder(model);

				} else if (storage.getSaveMap().get(key).get("type").equals("Samurai")) {
					model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
					game = new SamuraiGameBuilder(model);
				}
			}
		}

		// geht weil in zeile 57 wurde das json object reingeladen
		storageModel.setLoadedLogic(model);
		// ladet die gespeicherten infos rein in das erstellte modell aus zeile 60 bzw
		// 64
		storageModel.loadIntoModel();

		game.initializeGame();

		SudokuField[][] s = game.getTextField();

		// storagemodelgetloaded einheitlich!!!
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				if (storageModel.getLoadedLogic().getCells()[j][i].getValue() != 0) {
					s[i][j].setText(Integer.toString(storageModel.getLoadedLogic().getCells()[j][i].getValue()));
				}
				if (!storageModel.getLoadedLogic().getCells()[j][i].getIsReal()) {
					s[i][j].setDisable(false);
				}
			}
		}
		

		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getPane());
		storage.getStage().close();
	}

	public void fillListVew() {

		JSONObject readedObject;
		System.out.println(sharedStorage.getPreferedDirectory());

		if (dir != null) {
			for (File child : dir) {
				if (child.getName().endsWith(".json")) {
					readedObject = storageModel.convertToJSON(child.getAbsoluteFile());

					String gameString = "GameType " + ": " + (String) readedObject.get("type") + " | Difficulty: "
							+ readedObject.get("difficultyString") + " | Time: " + readedObject.get("minutesPlayed")
							+ " min " + readedObject.get("secondsPlayed") + " sek " + " | Points: "
							+ readedObject.get("points");
					storage.getObservableList().add(gameString);
					storage.getSaveMap().put(gameString, readedObject);
					System.out.println(storage.getSaveMap().keySet());
				}

			}
			storage.getListView().setItems(storage.getObservableList());
			storageModel.calculateGameStats();
			setGameStatInformations();
			System.out.println(dir.length + "llllllllllllllll" + storage.getObservableList().size());
		}
	}

	public void setGameStatInformations() {
		storage.getPointsLabel().setText("Points: " + storageModel.getOverallGamePoints());
		storage.getAverageTimeLabel().setText("Average Time played: " + storageModel.getPlayedMinutesOverall() + " min "
				+ storageModel.getSecondsPlayedOverall() + " seconds ");
		storage.getAveragePointsLabel().setText("Average Points: " + storageModel.getAveragePoints());
	}

	public void deleteEntry(ActionEvent e) {
		System.out.println(storage.getListView().getSelectionModel().getSelectedIndex());

		int counter = 0;

		File[] helper = dir;

		int deleteIndex = storage.getListView().getSelectionModel().getSelectedIndex();
	
				storage.getObservableList().remove(deleteIndex);
				// Files.delete(Paths.get(dir[i].getAbsolutePath()));
				dir[deleteIndex].delete();
			

				// TODO Auto-generated catch block

				dir = new File(sharedStorage.getPreferedDirectory()).listFiles();

		
	}
	
	public void handleDirectorySwitch(ActionEvent e) {
		DirectoryChooser directoryChooser = new DirectoryChooser();

		String path = directoryChooser.showDialog(GUI.getStage()).getAbsolutePath();

		sharedStorage.getStoragePrefs().put("DirectoryPath", path);
		storage.getObservableList().clear();
		storage.getHashMap().clear();
		dir = new File(sharedStorage.getPreferedDirectory()).listFiles();
		fillListVew();
	}
	

}
