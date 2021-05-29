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
	protected TableView<SudokuStorageModel> tableView;

	// Right Click Menu Items
	ContextMenu contextMenu = new ContextMenu();
	MenuItem deleteMenuItem = new MenuItem("Delete");
	MenuItem loadMenuItem = new MenuItem("Load Game");

	// Labels and ContainerBoxes for gamestats at the bottom of the screen
	protected Label gameHeadLabel;
	protected BorderPane storageContainerBox = new BorderPane();

	protected HBox gameHeaderBox = new HBox();

	protected Label pointsHeaderLabel;
	protected Label averagePointsHeaderLabel;
	protected Label averageTimeHeaderLabel;
	protected Label overallTimeHeaderLabel;
	protected Label averagePointsResultLabel;
	protected Label overallPointsResultLabel;
	

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

	//	stage.setResizable(false);
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
	
		VBox gameStatsBox = new VBox();
		Label gameStatsLabel = new Label("Game Scores");
		gameStatsLabel.setFont(new Font("Georgia",20));
		
		gameStatsLabel.setAlignment(Pos.CENTER);
		
		pointsHeaderLabel = new Label("Overall Points");
		averagePointsHeaderLabel = new Label ("Average Points");
		gameStatsBox.setPadding(new Insets(10,10,10,10));
		gameStatsBox.getChildren().add(gameStatsLabel);
		gameStatsBox.setAlignment(Pos.CENTER);
		
		HBox pointHeaderBox = new HBox();
	//	pointsBox.setPadding(new Insets(5,5,5,5));
		pointsHeaderLabel.setFont(new Font("Georgia",14));
		averagePointsHeaderLabel.setFont(new Font("Georgia",14));
		pointHeaderBox.setSpacing(230);
		pointHeaderBox.getChildren().addAll(averagePointsHeaderLabel,pointsHeaderLabel);
		
		HBox pointResultBox = new HBox();
		pointResultBox.setPadding(new Insets(0,10,0,35));
		averagePointsResultLabel = new Label("0");
		averagePointsResultLabel.setFont(new Font("Georgia",13));
		pointResultBox.setSpacing(315);
		
		overallPointsResultLabel = new Label("0");
		overallPointsResultLabel.setFont(new Font("Georgia",13));
		pointResultBox.getChildren().addAll(averagePointsResultLabel,overallPointsResultLabel);
		
		
		HBox timeBox = new HBox();

		averageTimeHeaderLabel = new Label ("Average Time");
		overallTimeHeaderLabel = new Label ("Overall Time");
		averageTimeHeaderLabel.setFont(new Font("Georgia",14));
		overallTimeHeaderLabel.setFont(new Font("Georgia",14));
		timeBox.setSpacing(237);
		timeBox.setPadding(new Insets(30,0,0,0));
		timeBox.getChildren().addAll(averageTimeHeaderLabel, overallTimeHeaderLabel);
		
		gameStatsBox.getChildren().addAll(pointHeaderBox,pointResultBox,timeBox);
		
		
		
		
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

	public TableView<SudokuStorageModel> getTableView() {
		return tableView;
	}

	public Label getOverallPointsLabel() {
		return overallPointsResultLabel;
	}

//	public Label getAverageTimeLabel() {
//		return averageTimeLabel;
//	}
//
//	public Label getAveragePointsLabel() {
//		return averagePointsLabel;
//	}

	public Stage getStage() {
		return stage;
	}
}
