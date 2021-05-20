package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.BasicGameBuilder;
import application.GUI;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import logic.BasicGameLogic;

import logic.*;

public class StorageController {

	Scene playScene;
	BasicGameBuilder game;
	BasicGameLogic model;
	
	String gameIdentifier;
	SudokuStorageModel saveModel;
	
	Scene gameScene;
	Storage storage;
	File[] dir;
	

	public StorageController(Storage storage) {
		this.storage = storage;
		saveModel = new SudokuStorageModel(model);
	//	dir = new File(saveModel.getDirectory()).listFiles();
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
	

		saveModel.loadIntoModel(model,(JSONObject) storage.getSaveMap().get(helper));
		model.setGamePoints((int) (long) storage.getSaveMap().get(helper).get("points"));
	//	model.setGameID((int) (long) storage.getSaveMap().get(helper).get("gameID"));
	
	
		// model.setSecondsPlayed(storage.getSaveMap().get(helper).get(e));
		model.setStartTime(System.currentTimeMillis());
	

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
		storage.getStage().close();
	}
	
	
	
	public void fillListVew() {
	
		
//		
		JSONObject obj;
		
	
		File [] dir = new File(saveModel.getDirectory()).listFiles();
		
			for(File child : dir) {
			
				obj = saveModel.convertToJSON(child.getAbsoluteFile());
			
				String gameString = "Game "  + ": " + (String) obj.get("type") + " | Time: "
						+ obj.get("minutesPlayed") + " min " + obj.get("secondsPlayed") + " sek " + " | Difficulty: "
						+ obj.get("difficulty") + " | Points: " + obj.get("points");
				storage.getObservableList().add(gameString);
				storage.getSaveMap().put(gameString, obj);
				System.out.println(storage.getSaveMap().keySet());
			}
		
			storage.getListView().setItems(storage.getObservableList());
			
	}
	
	
	public void deleteEntry(ActionEvent e)  {
		System.out.println("yeeeeeeet");

		int counter = 0;
		
		int deleteIndex = storage.getListView().getSelectionModel().getSelectedIndex();
		System.out.println(deleteIndex + "indexxxxxxxxxxxx");
		File [] dir = new File(saveModel.getDirectory()).listFiles();
		
		for(File child : dir) {
			
			try {
				if(counter ==deleteIndex) {
				storage.getObservableList().remove(deleteIndex);
		
				Files.delete(Paths.get(child.getAbsolutePath()));
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//	storage.getObservableList().remove(deleteIndex);
		//		storage.getHashMap().clear();
			
		//	storage.getObservableList().remove(deleteIndex);
			counter++;
		}
	}

		
//		int index = storage.getListView().getSelectionModel().getSelectedIndex();
//		storage.getObservableList().remove(index);
//		JSONArray help = (JSONArray) storage.getJSONObject().get("games");
//		storage.getJSONObject().remove("games");
//		help.remove(index);
//		
//		
//		
//		storage.getJSONObject().put("games", help);
//
//		
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			mapper.writeValue(storage.getSaveFile(), storage.getJSONObject());
//		} catch (IOException ie) {
//			ie.printStackTrace();
//		}
		
//	}
	
	

	
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
