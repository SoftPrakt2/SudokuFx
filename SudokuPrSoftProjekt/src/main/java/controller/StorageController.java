package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.BasicGameBuilder;
import application.GUI;
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
	//test
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
	
	
	
	
	public void deleteEntry(ActionEvent e) {

		
		int index = storage.getListView().getSelectionModel().getSelectedIndex();
		storage.getObservableList().remove(index);
		JSONArray help = (JSONArray) storage.getJSONObject().get("games");
		storage.getJSONObject().remove("games");
		help.remove(index);
		
		
		
		storage.getJSONObject().put("games", help);

		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(storage.getSaveFile(), storage.getJSONObject());
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
	}
	
	
	public JSONObject convertToJSON(File file) {
		
		JSONObject jsonObject = new JSONObject();
		
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
			jsonObject = (JSONObject) obj;
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
	
	public int getLastGameID(File file) {
		JSONParser parser = new JSONParser();

		int gameId = 0;

		try {
			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray array = (JSONArray) jsonObject.get("games");

			JSONObject helper = (JSONObject) array.get(array.size() - 1);

			gameId = (int) (long) helper.get("gameID");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(gameId);

		if (gameId == 0)
			return 0;
		else
			return gameId;
	}
	
	
	
	
	
	
	
}
