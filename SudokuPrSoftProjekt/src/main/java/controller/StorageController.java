package controller;

import org.json.simple.JSONArray;

import application.BasicGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

public class StorageController {

	Scene playScene;
	BasicGameBuilder game;
	BasicGameLogic model;
	Scene gameScene;
	Storage storage;
	public int difficulty;

	public StorageController(Storage storage) {
		this.storage = storage;
	}

	public void handleLoadAction(ActionEvent e) {
		String helper = "";

		for (String key : storage.getSaveMap().keySet()) {
			if (key.equals(storage.getListView().getSelectionModel().getSelectedItem())) {
				helper = key;
				if (storage.getSaveMap().get(key).get("type").equals("Sudoku")) {
					model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
					game = new SudokuGameBuilder(model);
				} else if (storage.getSaveMap().get(key).get("type").equals("Samurai")) {
					model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
					game = new SamuraiGameBuilder(model);
				}
			}
		}

		gameScene = game.initializeScene();
		JSONArray help = (JSONArray) storage.getSaveMap().get(helper).get("gameNumbers");
		JSONArray help2 = (JSONArray) storage.getSaveMap().get(helper).get("playAble");

		model.loadIntoModel(help, help2);
		model.setGamePoints((int) (long) storage.getSaveMap().get(helper).get("points"));
		model.setGameID((int) (long) storage.getSaveMap().get(helper).get("gameID"));
		System.out.println(model.getGameID());
		game.setDifficulty(1);

		model.printCells();
		// model.setSecondsPlayed(storage.getSaveMap().get(helper).get(e));
		model.setStartTime(System.currentTimeMillis());
		model.setLoadedSeconds((int) (long) storage.getSaveMap().get(helper).get("secondsPlayed"));
		model.setLoadedMinutes((int) (long) storage.getSaveMap().get(helper).get("minutesPlayed"));

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
		GUI.getStage().setScene(gameScene);
	}
}
