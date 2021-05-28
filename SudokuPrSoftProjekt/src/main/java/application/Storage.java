package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.SudokuStorageModel;

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
	protected TableView<SudokuStorageModel> listView;

	

	// Right Click Menu Items
	ContextMenu contextMenu = new ContextMenu();
	MenuItem deleteMenuItem = new MenuItem("Delete");
	MenuItem loadMenuItem = new MenuItem("Load Game");

	// Labels and ContainerBoxes for gamestats at the bottom of the screen
	protected Label gameHeadLabel;
	protected BorderPane storageContainerBox = new BorderPane();
	protected VBox gameStatsBox = new VBox();

	protected HBox gameHeaderBox = new HBox();

	protected Label pointsLabel;
	protected Label averagePointsLabel;
	protected Label averageTimeLabel;

	protected VBox listviewBox;

	FontAwesome fontAwesome = new FontAwesome();
	Glyph folderGraphic = fontAwesome.create(FontAwesome.Glyph.FOLDER);

	protected Stage stage;
	

	protected Button directoryButton;

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

		return stage;
	}

	public Scene showStorageScene() {

		listView = new TableView<>();
		controller.setUpTableView();
		listviewBox = new VBox();

		storagePane = new BorderPane();
		storageScene = new Scene(storageContainerBox, 500, 500);
		// storageScene.getStylesheets().add("css/sudoku.css");

		gameHeadLabel = new Label("Game Overview");
		gameHeadLabel.setFont(new Font("Georgia", 20));

		directoryButton = new Button("");
		directoryButton.setGraphic(folderGraphic);
		directoryButton.setAlignment(Pos.BASELINE_RIGHT);

		directoryButton.setOnAction(controller::handleDirectorySwitch);

		gameHeaderBox.getChildren().addAll(gameHeadLabel, directoryButton);
		directoryButton.setAlignment(Pos.TOP_RIGHT);

		contextMenu.getItems().addAll(deleteMenuItem, loadMenuItem);

		// storageContainerBox.setSpacing(10);

		pointsLabel = new Label("");
//		pointsLabel.setText("Overall Points: " + calculateGamePoints());
		averagePointsLabel = new Label("");
		averageTimeLabel = new Label("");
		pointsLabel.setAlignment(Pos.BASELINE_LEFT);
		gameStatsBox.getChildren().addAll(pointsLabel, averageTimeLabel, averagePointsLabel);
		gameStatsBox.setAlignment(Pos.CENTER);

		// listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		storageContainerBox.setPadding(new Insets(100, 15, 15, 15));

		listviewBox.getChildren().add(listView);
		listviewBox.setPadding(new Insets(15, 15, 15, 15));

		storageContainerBox.setTop(gameHeaderBox);
		storageContainerBox.setCenter(listviewBox);
		// storageContainerBox.getChildren().addAll(gameHeaderBox, listView);

		controller.fillListVew();

		addContextMenuFunctionality();

		deleteMenuItem.setOnAction(e -> {
			controller.deleteEntry(e);

		});
		loadMenuItem.setOnAction(controller::handleLoadAction);
		// loadMenuItem.setOnAction(e ->
		// System.out.println(listView.getSelectionModel().getSelectedIndex()));

		// GUI.getStage().setScene(storageScene);

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




	public TableView<SudokuStorageModel> getTableView() {
		return listView;
	}

	public Label getPointsLabel() {
		return pointsLabel;
	}

	public Label getAverageTimeLabel() {
		return averageTimeLabel;
	}

	public Label getAveragePointsLabel() {
		return averagePointsLabel;
	}

	public Stage getStage() {
		return stage;
	}

}
