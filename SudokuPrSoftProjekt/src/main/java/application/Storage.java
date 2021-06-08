package application;

import java.util.stream.Stream;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.StorageController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.BasicGameLogic;

public class Storage {

	// variablen für Anzeige der Spiele in der Liste

	StorageController controller;

	// Objekte bezüglich File und JSON Funktionalität

	// ÜberBehälter für die Scene
	protected Scene storageScene;

	// Objekte für die ListView und die HashMap welche benötigt wird um den Spieler
	// auswählen lassen zu können welches Spiel er laden will
	protected TableView<BasicGameLogic> tableView;

	// Right Click Menu Items
	protected ContextMenu contextMenu;
	protected MenuItem deleteMenuItem;
	protected MenuItem loadMenuItem;

	protected BorderPane storageContainerBox;

	protected HBox sceneHeaderBox;

	protected Label gameHeadLabel;
	protected Label gameScoreHeaderLabel;
	protected Label overallPointsHeaderLabel;
	protected Label averagePointsHeaderLabel;
	protected Label averageTimeHeaderLabel;
	protected Label overallTimeHeaderLabel;
	protected Label averagePointsResultLabel;
	protected Label overallPointsResultLabel;
	protected Label overallTimeResultLabel;
	protected Label averageTimeResultLabel;

	protected VBox listviewBox;

	FontAwesome fontAwesome = new FontAwesome();
	Glyph folderGraphic = fontAwesome.create(FontAwesome.Glyph.FOLDER);

	protected Stage stage;

	public Stage createStage() {
		storageScene = initializeStorageScene();
		Stage currentStage = GUI.getStage();
		double windowGap = 5;

		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(storageScene);
		stage.setX(currentStage.getX() + currentStage.getWidth() + windowGap);
		stage.setY(currentStage.getY());

		stage.showAndWait();

		return stage;
	}

	public Scene initializeStorageScene() {
		storageContainerBox = new BorderPane();
		storageScene = new Scene(storageContainerBox, 510, 600);
		controller = new StorageController(this);
		storageScene.getStylesheets().add("css/sudoku.css");
		storageContainerBox.getStyleClass().add("customBackgroundColor");
		storageContainerBox.setPadding(new Insets(15, 15, 15, 15));

		sceneHeaderBox = new HBox();
		gameHeadLabel = new Label("Game Overview");
		sceneHeaderBox.getChildren().addAll(gameHeadLabel);

		listviewBox = new VBox();
		tableView = new TableView<>();
		controller.setUpTableView();
		listviewBox.getChildren().add(tableView);

		storageContainerBox.setTop(sceneHeaderBox);
		storageContainerBox.setCenter(listviewBox);
		createGameStatBox();
		controller.fillListVew();

		setUpContextMenu();
		styleLabels();
		alignLabelWithWindowSize();

		return storageScene;
	}

	public void setUpContextMenu() {
		contextMenu = new ContextMenu();
		deleteMenuItem = new MenuItem("Delete");
		loadMenuItem = new MenuItem("Load Game");
		contextMenu.getItems().addAll(deleteMenuItem, loadMenuItem);
		addContextMenuFunctionality();

		deleteMenuItem.setOnAction(controller::deleteEntry);
		loadMenuItem.setOnAction(controller::handleLoadAction);
	}

	public void createGameStatBox() {
		HBox gameStatsBox = new HBox();
		gameScoreHeaderLabel = new Label("Game Scores");
		HBox gameStatsLabelBox = new HBox();
		gameStatsLabelBox.getChildren().add(gameScoreHeaderLabel);
		gameStatsLabelBox.setAlignment(Pos.BASELINE_LEFT);
		gameStatsBox.setPadding(new Insets(10, 10, 10, 10));
		gameStatsBox.setSpacing(70);

		gameStatsBox.getChildren().addAll(createAverageResultContainer(), gameStatsLabelBox,
				createOverallResultContainers());
		listviewBox.getChildren().add(gameStatsBox);
	}

	
	public VBox createAverageResultContainer() {
		VBox averageResultsBox = new VBox();
		averageResultsBox.setPadding(new Insets(40, 0, 0, 0));

		averagePointsHeaderLabel = new Label("Average Points");
		averageTimeHeaderLabel = new Label("Average Time");

		HBox averagepointsResultBox = new HBox();
		averagepointsResultBox.setAlignment(Pos.CENTER);
		averagepointsResultBox.setPadding(new Insets(0, 0, 15, 0));
		averagePointsResultLabel = new Label("");
		averagepointsResultBox.getChildren().add(averagePointsResultLabel);

		HBox averageTimeResultBox = new HBox();
		averageTimeResultLabel = new Label("");
		averageTimeResultBox.getChildren().add(averageTimeResultLabel);
		averageTimeResultBox.setAlignment(Pos.CENTER);

		averageResultsBox.getChildren().addAll(averagePointsHeaderLabel, averagepointsResultBox, averageTimeHeaderLabel,
				averageTimeResultBox);

		return averageResultsBox;
	}

	public VBox createOverallResultContainers() {
		VBox overallResultsBox = new VBox();
		overallResultsBox.setPadding(new Insets(40, 0, 0, 0));

		overallPointsHeaderLabel = new Label("Overall Points");
		overallTimeHeaderLabel = new Label("Overall Time");

		HBox overallpointsResultBox = new HBox();
		overallpointsResultBox.setAlignment(Pos.CENTER);
		overallpointsResultBox.setPadding(new Insets(0, 0, 15, 0));
		overallPointsResultLabel = new Label("");
		overallpointsResultBox.getChildren().add(overallPointsResultLabel);

		HBox overallTimeResultBox = new HBox();
		overallTimeResultLabel = new Label("");
		overallTimeResultBox.getChildren().add(overallTimeResultLabel);
		overallTimeResultBox.setAlignment(Pos.CENTER);

		overallResultsBox.getChildren().addAll(overallPointsHeaderLabel, overallpointsResultBox, overallTimeHeaderLabel,
				overallTimeResultBox);

		return overallResultsBox;
	}

	public void styleLabels() {
		Stream.of(averageTimeHeaderLabel, overallTimeHeaderLabel, overallPointsHeaderLabel, averagePointsHeaderLabel)
				.forEach(label -> label.getStyleClass().add("standardLabel"));

		Stream.of(averageTimeResultLabel, overallTimeResultLabel, overallPointsResultLabel, averagePointsResultLabel,
				gameScoreHeaderLabel, gameHeadLabel).forEach(label -> label.getStyleClass().add("storageResultLabel"));
	}

	// Methoe welche Funktionalität für Rechtsklick Menü ermöglicht
	public void addContextMenuFunctionality() {
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.SECONDARY)) {
					contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
				}
			}
		});

	}

	public void alignLabelWithWindowSize() {
		final SimpleDoubleProperty fontSizeBinding = new SimpleDoubleProperty(10);

		fontSizeBinding.bind(storageScene.widthProperty().add(storageScene.heightProperty()).divide(65));

		Stream.of(averagePointsHeaderLabel, averageTimeHeaderLabel, overallPointsHeaderLabel, overallTimeHeaderLabel,
				gameHeadLabel, gameScoreHeaderLabel)
				.forEach(label -> label.styleProperty()
						.bind(Bindings.concat("-fx-font-size: ", fontSizeBinding.asString())));

		Stream.of(averagePointsResultLabel, averageTimeResultLabel, overallPointsResultLabel, overallTimeResultLabel)
				.forEach(label -> label.styleProperty()
						.bind(Bindings.concat("-fx-font-size: ", fontSizeBinding.asString())));

	}

	public TableView<BasicGameLogic> getTableView() {
		return tableView;
	}

	public Label getOverallPointsLabel() {
		return overallPointsResultLabel;
	}

	public Label getAverageTimeResultLabel() {
		return averageTimeResultLabel;
	}

	public Label getOverallTimeResultLabel() {
		return overallTimeResultLabel;
	}

	public Label getAveragePointsResultLabel() {
		return averagePointsResultLabel;
	}

	public Stage getStage() {
		return stage;
	}
}
