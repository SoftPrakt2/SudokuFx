package controller;

import java.io.File;

import org.json.simple.JSONObject;

import application.BasicGameBuilder;
import application.GUI;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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

	protected ObservableList<SudokuStorageModel> jsonObservableList = FXCollections.observableArrayList();

	TableColumn<SudokuStorageModel, String> gameTypecolumn = new TableColumn<>("GameType");
	TableColumn<SudokuStorageModel, String> difficultycolumn = new TableColumn<>("Difficulty");
	TableColumn<SudokuStorageModel, Integer> pointscolumn = new TableColumn<>("Points");
	TableColumn<SudokuStorageModel, String> playtimecolumn = new TableColumn<>("PlayTime");
	TableColumn<SudokuStorageModel, Gamestate> gamestatecolumn = new TableColumn<>("Gamestate");
	TableColumn<SudokuStorageModel, Integer> gameidcolumn = new TableColumn<>("GameID");
	
	IntegerProperty overallPointsProperty;
	
	

	SharedStoragePreferences sharedStorage = new SharedStoragePreferences();

	public StorageController(Storage storage) {
		this.storage = storage;
		storageModel = new SudokuStorageModel();
		dir = new File(sharedStorage.getPreferedDirectory()).listFiles();
	}

	// test
	public void handleLoadAction(ActionEvent e) {

		storageModel = storage.getTableView().getSelectionModel().getSelectedItem();

		if (storageModel.getGametype().equals("Sudoku")) {
			model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
			game = new SudokuGameBuilder(model);
		}
		if (storageModel.getGametype().equals("Samurai")) {
			model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
			game = new SamuraiGameBuilder(model);
		}

		storageModel.setLoadedLogic(model);
		storageModel.loadIntoModel();

		model = storageModel.getLoadedLogic();

		game.initializeGame();
		model.initializeTimer();
		model.getLiveTimer().start();
		game.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));

		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getPane());
		storage.getStage().close();

		SudokuField[][] s = game.getTextField();

		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				if (model.getCells()[j][i].getValue() != 0) {
					s[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}
				if (!model.getCells()[j][i].getIsReal()) {
					s[i][j].setDisable(false);
				}
			}
		}
	}

	public void setUpTableView() {
		storage.getTableView().getColumns().addAll(gameidcolumn, gameTypecolumn, difficultycolumn, pointscolumn,
				playtimecolumn, gamestatecolumn);
	}

	public void fillListVew() {

		JSONObject readedObject;
		System.out.println(sharedStorage.getPreferedDirectory());

		if (dir != null) {
			for (File child : dir) {
				if (child.getName().endsWith(".json")) {
					readedObject = storageModel.convertToJSON(child.getAbsoluteFile());

					storageModel.setJSONObject(readedObject);
					storageModel.setStoredInformations();
					jsonObservableList.add(storageModel);

				}
				storageModel = new SudokuStorageModel();
			}
			gameTypecolumn.setCellValueFactory(new PropertyValueFactory<>("gametype"));
			difficultycolumn.setCellValueFactory(new PropertyValueFactory<>("difficultystring"));
			pointscolumn.setCellValueFactory(new PropertyValueFactory<>("gamepoints"));
			playtimecolumn.setCellValueFactory(new PropertyValueFactory<>("playtimestring"));
			gamestatecolumn.setCellValueFactory(new PropertyValueFactory<>("gamestate"));
			gameidcolumn.setCellValueFactory(new PropertyValueFactory<>("gameid"));

		}
		storage.getTableView().setItems(jsonObservableList);
		calculateGameStats();
	}

	public void setGameStatInformations() {
//		storage.getPointsLabel().setText("Points: " + storageModel.getOverallGamePoints());
//		storage.getAverageTimeLabel().setText("Average Time played: " + storageModel.getPlayedMinutesOverall() + " min "
//				+ storageModel.getSecondsPlayedOverall() + " seconds ");
//		storage.getAveragePointsLabel().setText("Average Points: " + storageModel.getAveragePoints());
	}

	public void deleteEntry(ActionEvent e) {

		int deleteIndex = storage.getTableView().getSelectionModel().getSelectedIndex();

		jsonObservableList.remove(deleteIndex);

		if (dir[deleteIndex].delete()) {
			System.out.println("erfolgreich gelöscht");
		}

		dir = new File(sharedStorage.getPreferedDirectory()).listFiles();

	}

	public void handleDirectorySwitch(ActionEvent e) {
		DirectoryChooser directoryChooser = new DirectoryChooser();

		String path = directoryChooser.showDialog(GUI.getStage()).getAbsolutePath();

		sharedStorage.getStoragePrefs().put("DirectoryPath", path);
		jsonObservableList.clear();

		dir = new File(sharedStorage.getPreferedDirectory()).listFiles();
		fillListVew();
		
	}
	
	public void calculateGameStats() {
		overallPointsProperty = new SimpleIntegerProperty();
		
		IntegerBinding totalCost = Bindings.createIntegerBinding(() -> {
		    int total = 0 ;
		    for (SudokuStorageModel model : storage.getTableView().getItems()) {
		        total = total + model.getGamepoints();
		    }
		    return total ;
		
		
	}, storage.getTableView().getItems());
	
	storage.getOverallPointsLabel().textProperty().bind(totalCost.asString());
	
	
	}
	

}
