package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import controller.StorageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Storage {

	Button back;

	// variablen für Anzeige der Spiele in der Liste
	protected String gameIdentifier;
	protected int saveCounter = 1;

	StorageController controller = new StorageController(this);

	// Objekte bezüglich File und JSON Funktionalität
	
	
	

	// ÜberBehälter für die Scene
	protected Scene storageScene;
	protected BorderPane storagePane;

	// Objekte für die ListView und die HashMap welche benötigt wird um den Spieler
	// auswählen lassen zu können welches Spiel er laden will
	protected ListView<String> listView = new ListView<>();
	protected HashMap<String, JSONObject> saveMap = new HashMap<>();
	protected ObservableList<String> jsonObservableList = FXCollections.observableArrayList();

	// Right Click Menu Items
	ContextMenu contextMenu = new ContextMenu();
	MenuItem deleteMenuItem = new MenuItem("Delete");
	MenuItem loadMenuItem = new MenuItem("Load Game");

	// Labels and ContainerBoxes for gamestats at the bottom of the screen
	protected VBox storageContainerBox = new VBox();
	protected VBox gameStatsBox = new VBox();
	protected Label gameInfoHead = new Label("GameStats");
	protected Label pointsLabel;
	protected Label averagePointsLabel;
	protected Label averageTimeLabel;
	
	protected Stage stage;
	
	
	public Stage createStage() {
		Scene storageScene = showStorageScene();
		Stage currentStage = GUI.getStage();
		double windowGap = 5;
		
		
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(storageScene);
		stage.setX(currentStage.getX() + currentStage.getWidth() + windowGap);
		stage.setY(currentStage.getY());
		
		stage.setResizable(false);
		stage.showAndWait();
		stage.initModality(Modality.APPLICATION_MODAL);
		return stage;
	}
	
	

	public Scene showStorageScene() {

		storagePane = new BorderPane();
		storageScene = new Scene(storageContainerBox, 500, 500);

		controller.fillListVew();

		contextMenu.getItems().addAll(deleteMenuItem, loadMenuItem);

		storageContainerBox.setSpacing(10);
		storageContainerBox.setPadding(new Insets(20, 20, 20, 20));

		pointsLabel = new Label();
		pointsLabel.setText("Overall Points: " + calculateGamePoints());
		averagePointsLabel = new Label("Average Points: " + (double) calculateGamePoints());
		averageTimeLabel = new Label("Average PlayTime: " + calculateAverageTimePlayed());
		pointsLabel.setAlignment(Pos.BASELINE_LEFT);
		gameStatsBox.getChildren().addAll(gameInfoHead, pointsLabel, averageTimeLabel, averagePointsLabel);
		gameStatsBox.setAlignment(Pos.CENTER);

		back = new Button("Back");
		
		storageContainerBox.getChildren().addAll(listView, gameStatsBox, back);

		

		addContextMenuFunctionality();

		deleteMenuItem.setOnAction(e -> {
			controller.deleteEntry(e);
			redraw();
		});
		loadMenuItem.setOnAction(controller::handleLoadAction);

		back.setOnAction(e -> GUI.getStage().setScene(GUI.getMainMenu()));

	//	GUI.getStage().setScene(storageScene);

		return storageScene;
	}

	// Methoe welche Funktionalität für Rechtsklick Menü ermöglicht
	public void addContextMenuFunctionality() {
		listView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getButton().equals(MouseButton.SECONDARY)) {
					contextMenu.show(listView, event.getScreenX(), event.getScreenY());
				}
			}
		});
		
		
	
		
	}
	
	
	

	public void redraw() {
		System.out.println(calculateGamePoints());
		pointsLabel.setText("Overall Points: " + calculateGamePoints());
	}

	public double calculateGamePoints() {
//		JSONObject helpObject = controller.convertToJSON(saveFile);
//		double gamePoints = 0;
//		JSONArray helpArray = (JSONArray) helpObject.get("games");
//
//		for (int i = 0; i < helpArray.size(); i++) {
//			JSONObject obj = (JSONObject) helpArray.get(i);
//			gamePoints += (double) (long) obj.get("points");
//		}
//		return gamePoints;
		return 5;
	}

	public String calculateAverageTimePlayed() {
//		JSONObject helpObject = controller.convertToJSON(saveFile);
//		JSONArray helpArray = (JSONArray) helpObject.get("games");
//		String averageGameTimeString = "";
//		int minPlayed = 0;
//		int secPlayed = 0;
//		int counter = 0;
//		int showMinutes;
//		int showSeconds;
//
//		for (int i = 0; i < helpArray.size(); i++) {
//			JSONObject obj = (JSONObject) helpArray.get(i);
//			minPlayed += (int) (long) (obj.get("minutesPlayed")) * 60;
//			secPlayed += (int) (long) (obj.get("secondsPlayed"));
//			counter++;
//		}
//		if (counter > 0) {
//			int playTime = (minPlayed + secPlayed) / counter;
//			showMinutes = playTime / 60;
//			showSeconds = playTime % 60;
//			averageGameTimeString = showMinutes + " minutes " + showSeconds + " seconds ";
//		}

		return "";
	}

	public String getMostPlayedType() {
//		JSONObject helpObject = controller.convertToJSON(saveFile);
//		JSONArray helpArray = (JSONArray) helpObject.get("games");
//		int sudokuCounter = 0;
//		int freeFormCounter = 0;
//		int samuraiCounter = 0;
//		String gameType = "";
//		HashMap<String, Integer> gameTypeMap = new HashMap<>();
//
//		for (int i = 0; i < helpArray.size(); i++) {
//			JSONObject obj = (JSONObject) helpArray.get(i);
//			if (((String) obj.get("type")).equals("Samurai"))
//				samuraiCounter++;
//			if (((String) obj.get("type")).equals("Sudoku"))
//				sudokuCounter++;
//			if (((String) obj.get("type")).equals("FreeForm"))
//				freeFormCounter++;
//		}
//		gameTypeMap.put("Sudoku", sudokuCounter);
//		gameTypeMap.put("Samurai", samuraiCounter);
//		gameTypeMap.put("FreeForm", freeFormCounter);
//
//		Optional<Entry<String, Integer>> maxEntry = gameTypeMap.entrySet().stream()
//				.max((Entry<String, Integer> e1, Entry<String, Integer> e2) -> e1.getValue().compareTo(e2.getValue()));
//		String s = maxEntry.get().toString();
//
		return "";
	}




//	public File getSaveFile() {
//		return saveFile;
//	}



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
	
	
	public Stage getStage() {
		return stage;
	}
	
	
	
	
	
	
	
	
	

}
