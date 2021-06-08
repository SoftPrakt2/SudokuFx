package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SaveModel;
import logic.SharedStoragePreferences;
import logic.SudokuStorageModel;

public class StorageController {

	private BasicGameBuilder game;
	private BasicGameLogic model;
	private SudokuStorageModel storageModel;
	private Storage storage;
	private File[] dir = new File("SaveFiles").listFiles();

	protected ObservableList<BasicGameLogic> jsonObservableList;

	TableColumn<BasicGameLogic, String> gameTypecolumn;
	TableColumn<BasicGameLogic, String> difficultycolumn;
	TableColumn<BasicGameLogic, Integer> pointscolumn;
	TableColumn<BasicGameLogic, String> playtimecolumn;
	TableColumn<BasicGameLogic, Gamestate> gamestatecolumn;
	TableColumn<BasicGameLogic, Integer> gameidcolumn;

	IntegerProperty overallPointsProperty;

	SharedStoragePreferences sharedStorage = new SharedStoragePreferences();

	public StorageController(Storage storage) {
		this.storage = storage;
		storageModel = new SudokuStorageModel();
	}

	
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

		if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)) {
			model.initializeTimer();
			model.getLiveTimer().start();
		}

		if (model.getGamestate().equals(Gamestate.CREATING)) {
			if (game instanceof FreeFormGameBuilder) {
				game.getToolBar().getItems().add(3, game.getDoneButton());
			}
			game.getDoneButton().setVisible(true);
			game.disablePlayButtons();
		}

		if (model.getGamestate().equals(Gamestate.DRAWING)) {
			game.getColorBox().setVisible(true);
			game.getColorsDoneButton().setVisible(true);
			game.disablePlayButtons();
		}

		
		game.getGameInfoLabel().setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
		game.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));
		game.getGameNotificationLabel().setText(model.getGameText());

		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getPane());
		storage.getStage().close();

		alignArrays();

	}

	
	@SuppressWarnings("unchecked")
	public void setUpTableView() {
		gameTypecolumn = new TableColumn<>("GameType");
		difficultycolumn = new TableColumn<>("Difficulty");
		pointscolumn = new TableColumn<>("Points");
		playtimecolumn = new TableColumn<>("PlayTime");
		gamestatecolumn = new TableColumn<>("Gamestate");
		gameidcolumn = new TableColumn<>("GameID");
		storage.getTableView().getColumns().addAll(gameidcolumn, gameTypecolumn, difficultycolumn, pointscolumn,
				playtimecolumn, gamestatecolumn);
	}

	public void fillListVew() {

		jsonObservableList = FXCollections.observableArrayList();
		
		SaveModel readedObject;

		if (dir != null) {
			for (File child : dir) {
				if (child.getName().endsWith(".json")) {
					readedObject = storageModel.convertFileToSaveModel(child.getAbsoluteFile());
					model = storageModel.loadIntoModel(model, readedObject);
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

		if (dir[deleteIndex].exists()) {
			try {
				Files.delete(dir[deleteIndex].toPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		dir = new File("SaveFiles").listFiles();
	}

	public void alignArrays() {
		SudokuField[][] s = game.getTextField();
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				if (model instanceof FreeFormLogic && !model.getCells()[j][i].getBoxcolor().equals("")) {
					s[i][j].setColor(model.getCells()[j][i].getBoxcolor());
				}
				if (model.getCells()[j][i].getValue() != 0) {
					s[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}

				if (model.getCells()[j][i].getIsReal() && model.getCells()[j][i].getValue() != 0) {
					System.out.println(model.getCells()[j][i].getIsReal());
					s[i][j].setDisable(true);
				}
			}
		}
	}

	
	
	public void calculateGameStats() {

		IntegerBinding totalCost = Bindings.createIntegerBinding(() -> {
			int total = 0;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				total = total + savedModel.getGamepoints();
			}
			return total;
		}, storage.getTableView().getItems());

		IntegerBinding averagePoints = Bindings.createIntegerBinding(() -> {
			int total = 0;
			int counter = 0;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				total = total + savedModel.getGamepoints();
				counter++;
			}
			if (counter == 0) {
				counter = 1;
			}
			return Math.floorDiv(total, counter);
		}, storage.getTableView().getItems());

		StringBinding overAllPlayTime = Bindings.createStringBinding(() -> {
			long playTime = 0;
			String time;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				playTime += savedModel.getMinutesplayed() * 60 + savedModel.getSecondsplayed();
			}

			long minPlayed = playTime / 60;
			long secPlayed = playTime % 60;

			time = String.format("%02d:%02d", minPlayed, secPlayed);
			return time;
		}, storage.getTableView().getItems());

		StringBinding averagePlayTime = Bindings.createStringBinding(() -> {
			long playTime = 0;
			String time;
			int counter = 0;
			for (BasicGameLogic savedModel : storage.getTableView().getItems()) {
				playTime += savedModel.getMinutesplayed() * 60 + savedModel.getSecondsplayed();
				counter++;
			}
			if (counter == 0)
				counter = 1;
			playTime = Math.round(playTime / counter);
			long minPlayed = playTime / 60;
			long secPlayed = playTime % 60;

			time = String.format("%02d:%02d", minPlayed, secPlayed);
			return time;
		}, storage.getTableView().getItems());

		storage.getOverallPointsLabel().textProperty().bind(totalCost.asString());
		storage.getAveragePointsResultLabel().textProperty().bind(averagePoints.asString());
		storage.getOverallTimeResultLabel().textProperty().bind(overAllPlayTime);
		storage.getAverageTimeResultLabel().textProperty().bind(averagePlayTime);
	}

}
