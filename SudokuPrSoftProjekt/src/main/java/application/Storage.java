package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Storage {


	FileChooser fileChooser;

	JSONParser parser = new JSONParser();
	File saveFile = new File("/C:/test2/saveGames.json");
	JSONObject jsonObject = convertToJSON(saveFile);
	
	Scene storageScene;
	BorderPane storagePane;
	Button load;
	Button back;
	Button delete;
	
	String gameIdentifier;
	
	int saveCounter = 1;
	
	HashMap<String, JSONObject> saveMap = new HashMap<>();
	ListView<String> listView = new ListView<>();
	ObservableList<String> jsonObservableList = FXCollections.observableArrayList();
	
	BasicGameBuilder loadedGame;
	Scene gameScene;
	
	public Scene showStorageScene() {
		Stage window = new Stage();
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(5,5,5,50));
		
		
		window.setWidth(500);
		storagePane = new BorderPane();
		storageScene = new Scene(layout,500,500);
		window.setScene(storageScene);
		



		Label label = new Label("Please select a savegame");
		
		
		Label chosenLabel = new Label("Bitte wähle einen Spielstand");
		
		load = new Button("Load");
		back = new Button("Back");
		delete = new Button("Delete");
		
		storagePane.setCenter(listView);
			
	
		layout.getChildren().addAll(label, listView,load,back,delete);
		
	
		back.setOnAction(e-> GUI.getStage().setScene(GUI.getMainMenu()));
		fillListVew();
		load.setOnAction(e -> handleLoadAction(e));
		delete.setOnAction(e-> deleteEntry(e));
		
		return storageScene;
	}
	
	
	public void fillListVew() {
		JSONObject helpObject = convertToJSON(saveFile);
		JSONArray helpArray = (JSONArray) helpObject.get("games");
		
		for(int i = 0; i < helpArray.size(); i++) {
			int helper = saveCounter;
			JSONObject obj = (JSONObject) helpArray.get(i);
			
			gameIdentifier = (String)obj.get("type")  + " " + helper;
			
			String gameString = (String)obj.get("type")  + " " + helper + " Time: " + obj.get("minutesPlayed") 
			+ " min " + obj.get("secondsPlayed") + " sek " + "Difficulty: " + obj.get("difficulty") +" Points: " + obj.get("points");
			jsonObservableList.add(gameString);
			saveMap.put(gameString, obj);
			saveCounter++;
		}
		
			listView.setItems(jsonObservableList);
			saveCounter = 0;
	}
	
	
	
	
	
	public void deleteEntry(ActionEvent e) {

		int index = listView.getSelectionModel().getSelectedIndex();
		jsonObservableList.remove(index);
		JSONArray help = (JSONArray) jsonObject.get("games");
		jsonObject.remove("games");
		help.remove(index);
		jsonObject.put("games",help);
		
		
		saveFile = new File("/D:/test2/saveGames.json");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(saveFile, jsonObject);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	
	
	
	
public void handleLoadAction(ActionEvent e) {
		
		for(String key : getSaveMap().keySet()) {
			if(key.equals(getListView().getSelectionModel().getSelectedItem())) {
				if(getSaveMap().get(key).get("type").equals("Samurai")) {
					loadedGame = new SamuraiGameBuilder();
				
				} else loadedGame = new SudokuGameBuilder();
				
				
				
				gameScene =	loadedGame.initializeScene();
		
				loadedGame.controller.loadIntoSudokuField((JSONArray)saveMap.get(key).get("gameNumbers"),(JSONArray)saveMap.get(key).get("playAble"));
				loadedGame.controller.setModelGamePoints((int)(long)saveMap.get(key).get("points"));
				GUI.getStage().setScene(gameScene);
				long minutes = (long) saveMap.get(key).get("minutesPlayed");
				long sek = (long) saveMap.get(key).get("secondsPlayed");
				loadedGame.controller.setLoadedSeconds(sek);
				loadedGame.controller.setLoadedMinutes(minutes);
				loadedGame.controller.setModellStartTime();
				loadedGame.controller.setModelID((int)(long)saveMap.get(key).get("gameID"));
				
				System.out.println((int)(long)saveMap.get(key).get("gameID"));
				
				
			}
		}
}
	

public FileChooser setUpFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a file");
		
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
		return fileChooser;
	}
	
	public JSONObject convertToJSON(File file) {
		
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

	
	
	
	public File getSaveFile() {
		return saveFile;
	}
	
	public HashMap<String, JSONObject> getSaveMap() {
		return saveMap;
	}
	
	
	public ObservableList<String> getObservableList() {
		return jsonObservableList;
	}
	
	public ListView<String> getListView() {
		return listView;
	}
	
	
	
	
	
	
	
	
	
	
}
	