package controller;

import java.io.File;
import java.io.IOException;

import org.json.simple.JSONObject;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
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
import logic.FreeFormLogic;
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
	String path;
	
	File[] dir; 

	protected ObservableList<BasicGameLogic> jsonObservableList = FXCollections.observableArrayList();

	TableColumn<BasicGameLogic, String> gameTypecolumn = new TableColumn<>("GameType");
	TableColumn<BasicGameLogic, String> difficultycolumn = new TableColumn<>("Difficulty");
	TableColumn<BasicGameLogic, Integer> pointscolumn = new TableColumn<>("Points");
	TableColumn<BasicGameLogic, String> playtimecolumn = new TableColumn<>("PlayTime");
	TableColumn<BasicGameLogic, Gamestate> gamestatecolumn = new TableColumn<>("Gamestate");
	TableColumn<BasicGameLogic, Integer> gameidcolumn = new TableColumn<>("GameID");

	IntegerProperty overallPointsProperty;

	SharedStoragePreferences sharedStorage = new SharedStoragePreferences();

	public StorageController(Storage storage) {
		this.storage = storage;
		storageModel = new SudokuStorageModel();
		dir =  new File("SaveFiles").listFiles();
	}

	
	
	
	// test
	public void handleLoadAction(ActionEvent e) {
		
		model = storage.getTableView().getSelectionModel().getSelectedItem();

		if (model.getGametype().equals("Sudoku")) {
			game = new SudokuGameBuilder(model);
		}
		if (model.getGametype().equals("Samurai")) {
			game = new SamuraiGameBuilder(model);
		}
		if (model.getGametype().equals("FreeForm")) {
			game = new FreeFormGameBuilder(model);
		}
		

		game.initializeGame();
		model.initializeTimer();
		game.getGameInfoLabel().setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
		System.out.println(model.getSecondsplayed()+"sekunden");
		model.getLiveTimer().start();
		game.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));

		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getPane());
		storage.getStage().close();

		
		
		SudokuField[][] s = game.getTextField();
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				if(model instanceof FreeFormLogic) {
					s[i][j].setStyle("-fx-background-color: #"+model.getCells()[i][j].getBoxcolor()+";");
				}
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

	
	public void fillListVew() throws IOException {

		JSONObject readedObject;
	
		if (dir != null) {
			for (File child : dir) {
				if (child.getName().endsWith(".json")) {
					readedObject = storageModel.convertToJSON(child.getAbsoluteFile());

					storageModel.setStoredInformations(readedObject);
					model = storageModel.loadIntoModel(model);
					jsonObservableList.add(model);
				}
				
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



	public void deleteEntry(ActionEvent e) {

		int deleteIndex = storage.getTableView().getSelectionModel().getSelectedIndex();

		jsonObservableList.remove(deleteIndex);

		if (dir[deleteIndex].delete()) {
			System.out.println("erfolgreich gelöscht");
		}

		dir =  new File("SaveFiles").listFiles();

	}

	public void handleDirectorySwitch(ActionEvent e) throws IOException {
		DirectoryChooser directoryChooser = new DirectoryChooser();

		String path = directoryChooser.showDialog(GUI.getStage()).getAbsolutePath();

		sharedStorage.getStoragePrefs().put("DirectoryPath", path);
		jsonObservableList.clear();

		dir = new File(sharedStorage.getPreferedDirectory()).listFiles();
		fillListVew();

	}

	public void calculateGameStats() {
		
		IntegerBinding totalCost = Bindings.createIntegerBinding(() -> {
			int total = 0;
			for (BasicGameLogic model : storage.getTableView().getItems()) {
				total = total + model.getGamepoints();
			}
			return total;
		}, storage.getTableView().getItems());
		
		

		IntegerBinding averagePoints = Bindings.createIntegerBinding(() -> {
			int total = 0;
			int counter = 0;
			for (BasicGameLogic model : storage.getTableView().getItems()) {
				total = total + model.getGamepoints();
				counter++;
			}
			if (counter == 0)
				counter = 1;
			return total / counter;
		}, storage.getTableView().getItems());
		
		StringBinding overAllPlayTime = Bindings.createStringBinding(() -> {
			long playTime = 0;
			String time;
			for (BasicGameLogic model : storage.getTableView().getItems()) {
				playTime += model.getMinutesplayed()*60 + model.getSecondsplayed();
			}
			long minPlayed = playTime/60;
			long secPlayed = playTime%60;
			
			time =	String.format("%02d:%02d", minPlayed, secPlayed);
			return time;
		}, storage.getTableView().getItems());
		
		
		StringBinding averagePlayTime = Bindings.createStringBinding(() -> {
			long playTime = 0;
			String time;
			int counter = 0;
			for (BasicGameLogic model : storage.getTableView().getItems()) {
				playTime += model.getMinutesplayed()*60 + model.getSecondsplayed();
				counter++;
			}
			if(counter ==0) counter = 1;
			playTime = playTime/counter;
			long minPlayed = playTime/60;
			long secPlayed = playTime%60;
			
			
			time =	String.format("%02d:%02d", minPlayed, secPlayed);
			return time;
		}, storage.getTableView().getItems());
		
		storage.getOverallPointsLabel().textProperty().bind(totalCost.asString());
		storage.getAveragePointsResultLabel().textProperty().bind(averagePoints.asString());
		storage.getOverallTimeResultLabel().textProperty().bind(overAllPlayTime);
		storage.getAverageTimeResultLabel().textProperty().bind(averagePlayTime);
	}

}
