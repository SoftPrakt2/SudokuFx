package application;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.StorageController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.BasicGameLogic;
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
	protected TableView<BasicGameLogic> tableView;

	// Right Click Menu Items
	ContextMenu contextMenu = new ContextMenu();
	MenuItem deleteMenuItem = new MenuItem("Delete");
	MenuItem loadMenuItem = new MenuItem("Load Game");

	// Labels and ContainerBoxes for gamestats at the bottom of the screen
	protected Label gameHeadLabel;
	protected BorderPane storageContainerBox = new BorderPane();

	protected HBox gameHeaderBox = new HBox();

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
	

	protected Button directoryButton;

	public Stage createStage() {
		 storageScene = showStorageScene();
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

	public Scene showStorageScene() {
		
		tableView = new TableView<>();
		controller.setUpTableView();
		listviewBox = new VBox();

		storagePane = new BorderPane();
		storageScene = new Scene(storageContainerBox, 500, 600);
	//	storageScene.getStylesheets().add("css/sudoku.css");
		
		gameHeadLabel = new Label("Game Overview");
		gameHeadLabel.setFont(new Font("Georgia", 20));

		directoryButton = new Button("");
		directoryButton.setGraphic(folderGraphic);
		//directoryButton.setAlignment(Pos.BASELINE_RIGHT);

		directoryButton.setOnAction(controller::handleDirectorySwitch);

		gameHeaderBox.getChildren().addAll(gameHeadLabel, directoryButton);
		gameHeaderBox.setSpacing(285);
		directoryButton.setAlignment(Pos.TOP_RIGHT);

		contextMenu.getItems().addAll(deleteMenuItem, loadMenuItem);

		

		storageContainerBox.setPadding(new Insets(15, 15, 15, 15));

		listviewBox.getChildren().add(tableView);
		listviewBox.setPadding(new Insets(0, 15, 15, 0));

		storageContainerBox.setTop(gameHeaderBox);
		storageContainerBox.setCenter(listviewBox);
		createGameStatBox();
		
		controller.fillListVew();

		addContextMenuFunctionality();

		deleteMenuItem.setOnAction(e -> {
			controller.deleteEntry(e);

		});
		loadMenuItem.setOnAction(controller::handleLoadAction);

		
		directoryButton.getStyleClass().add("storageButton");
		
		return storageScene;
	}
	
	
	public void createGameStatBox() {
	
		HBox gameStatsBox = new HBox();
		Label gameStatsLabel = new Label("Game Scores");
		gameStatsLabel.setFont(new Font("Georgia",20));
		
		gameStatsLabel.setAlignment(Pos.CENTER);
		
		
		
		averagePointsHeaderLabel = new Label ("Average Points");
		gameStatsBox.setPadding(new Insets(10,10,10,10));
		gameStatsBox.setSpacing(70);
		
		//gameStatsBox.setAlignment(Pos.CENTER);
		
		VBox averageResultsBox = new VBox();
		averageResultsBox.setPadding(new Insets(40,0,0,0));
		
		
		
		averagePointsHeaderLabel = new Label ("Average Points");
		averagePointsResultLabel = new Label("0");
		averagePointsHeaderLabel.setFont(new Font("Georgia",14));
		HBox averagepointsResultBox = new HBox();
		averagepointsResultBox.getChildren().add(averagePointsResultLabel);
		averagepointsResultBox.setAlignment(Pos.CENTER);
		averagepointsResultBox.setPadding(new Insets(0,0,15,0));
		averagePointsResultLabel.setFont(new Font("Georgia",20));
		
		
		averageTimeHeaderLabel = new Label ("Average Time");
		averageTimeResultLabel = new Label("0");
		averageTimeHeaderLabel.setFont(new Font("Georgia",14));
		HBox averageTimeResultBox = new HBox();
		averageTimeResultBox.getChildren().add(averageTimeResultLabel);
		averageTimeResultBox.setAlignment(Pos.CENTER);
		averageTimeResultLabel.setFont(new Font("Georgia",20));
		averageResultsBox.getChildren().addAll(averagePointsHeaderLabel,averagepointsResultBox,averageTimeHeaderLabel,averageTimeResultBox);
		
		
		
		
		VBox overallResultsBox = new VBox();
		overallResultsBox.setPadding(new Insets(40,0,0,0));
		
		overallPointsHeaderLabel = new Label("Overall Points");
		overallPointsResultLabel = new Label("0");
		overallPointsHeaderLabel.setFont(new Font("Georgia",14));
		HBox overallpointsResultBox = new HBox();
		overallpointsResultBox.getChildren().add(overallPointsResultLabel);
		overallpointsResultBox.setAlignment(Pos.CENTER);
		overallpointsResultBox.setPadding(new Insets(0,0,15,0));
		overallPointsResultLabel.setFont(new Font("Georgia",20));
		
		
		
		overallTimeHeaderLabel = new Label ("Overall Time");
		overallTimeResultLabel = new Label("0");
		overallTimeHeaderLabel.setFont(new Font("Georgia",14));
		HBox overallTimeResultBox = new HBox();
		overallTimeResultBox.getChildren().add(overallTimeResultLabel);
		overallTimeResultBox.setAlignment(Pos.CENTER);
		overallTimeResultLabel.setFont(new Font("Georgia",20));
		overallResultsBox.getChildren().addAll(overallPointsHeaderLabel,overallpointsResultBox,overallTimeHeaderLabel, overallTimeResultBox);
		
		

		gameStatsBox.getChildren().addAll(averageResultsBox, gameStatsLabel, overallResultsBox);

		listviewBox.getChildren().add(gameStatsBox);

	}
	
	
	// Methoe welche Funktionalität für Rechtsklick Menü ermöglicht
	public void addContextMenuFunctionality() {
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getButton().equals(MouseButton.SECONDARY)) {
					contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
				}
			}
		});

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
