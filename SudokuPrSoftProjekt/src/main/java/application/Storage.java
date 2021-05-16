package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.StorageController;
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
	
	URL url = getClass().getResource("/json/saveGames.json");
	File saveFile = new File(url.getPath());
	
	
	
	JSONObject jsonObject = convertToJSON(saveFile);
	
	Scene storageScene;
	
	BorderPane storagePane;
	
	Button load;
	
	Button back;
	
	Button delete;
	
	String gameIdentifier;
	
	HashMap<String, JSONObject> saveMap = new HashMap<>();
	ListView<String> listView = new ListView<>();
	ObservableList<String> jsonObservableList = FXCollections.observableArrayList();
	BasicGameBuilder loadedGame;
	Scene gameScene;
	StorageController controller;
	int saveCounter = 1;

	public Scene showStorageScene() {
		controller = new StorageController(this);

		Stage window = new Stage();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(5, 5, 5, 50));

		window.setWidth(500);
		storagePane = new BorderPane();
		storageScene = new Scene(layout, 500, 500);
		window.setScene(storageScene);

		Label label = new Label("Please select a savegame");
		Label chosenLabel = new Label("Bitte wähle einen Spielstand");

		load = new Button("Load");
		back = new Button("Back");
		delete = new Button("Delete");

		storagePane.setCenter(listView);
		layout.getChildren().addAll(label, listView, load, back, delete);
		back.setOnAction(e -> GUI.getStage().setScene(GUI.getMainMenu()));
		fillListVew();
		delete.setOnAction(e -> deleteEntry(e));
		load.setOnAction(controller::handleLoadAction);

		return storageScene;
	}

	public void fillListVew() {
		JSONObject helpObject = convertToJSON(saveFile);
		JSONArray helpArray = (JSONArray) helpObject.get("games");

		for (int i = 0; i < helpArray.size(); i++) {
			int helper = saveCounter;
			JSONObject obj = (JSONObject) helpArray.get(i);

			gameIdentifier = (String) obj.get("type") + " " + helper;

			String gameString = (String) obj.get("type") + " " + helper + " Time: " + obj.get("minutesPlayed") + " min "
					+ obj.get("secondsPlayed") + " sek " + "Difficulty: " + obj.get("difficulty") + " Points: "
					+ obj.get("points");
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
		jsonObject.put("games", help);

		saveFile = new File(url.getPath());
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(saveFile, jsonObject);
		} catch (IOException ie) {
			ie.printStackTrace();
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

	public HashMap<String, JSONObject> getHashMap() {
		return saveMap;
	}

}
