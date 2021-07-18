package application;

import java.util.stream.Stream;

import controller.StorageController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.BasicGameLogic;


/**
 * This class defines the appearance of the programs game overview.
 * To do so different UI components like a table view, a context menu for this table view and different 
 * labels are created.
 * @author grube
 *
 */
public class GameOverview {

	/**
	 * controller of this View class which will be initialized when opening this
	 * class in the program
	 */
	private StorageController controller;

	/**
	 * stage object of this class
	 */
	private Stage stage;

	/**
	 * scene object of this class
	 */
	private Scene storageScene;

	/**
	 * the tableview of this scene shows saved games and contains basicgamelogic objects
	 */
	private TableView<BasicGameLogic> tableView;

	/**
	 * UI objects which will be used for the contextmenu of the {@link #tableView}
	 */
	private ContextMenu contextMenu;
	private MenuItem deleteMenuItem;
	private MenuItem loadMenuItem;

	/**
	 * main container for UI objects in this class
	 */
	private BorderPane storageContainerBox;


	/**
	 * labels for different headlines in the UI window
	 */
	private Label storageHeaderLabel;
	private Label gameScoreHeaderLabel;
	private Label overallPointsHeaderLabel;
	private Label averagePointsHeaderLabel;
	private Label averageTimeHeaderLabel;
	private Label overallTimeHeaderLabel;

	/**
	 * labels which will display information about the users played
	 * games
	 */
	private Label averagePointsResultLabel;
	private Label overallPointsResultLabel;
	private Label overallTimeResultLabel;
	private Label averageTimeResultLabel;

	/**
	 * UI container for the {@link #tableView}
	 */
	private VBox tableviewBox;

	
	
	/**
	 * variables which define the size of a main menu UI representation
	 */
	private double screenWidth;
	private double screenHeight;

	
	
	/**
	 * This method is used to set up the stage object of this class
	 * and show it in the UI 
	 * @return stage object which is shown in the UI 
	 */
	public Stage createStage() {
		storageScene = initializeStorageScene();
		Stage mainStage = GUI.getStage();
		
		
		stage = new Stage();
		
		//postioning of the new stage
		stage.setX(mainStage.getX());
		stage.setY(mainStage.getY());
		
		determineSizeToShow();
	
		stage.setWidth(screenWidth);
		stage.setHeight(screenHeight);
		
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(storageScene);
		stage.setTitle("Your saved games");

		stage.showAndWait();

		return stage;
	}

	/**
	 * This method is used to set up this classes scene object {@link #storageScene}.
	 * Doing this the main UI container {@link #storageContainerBox} of this class is initialized
	 * and filled with the needed UI objects.
	 * These UI objects are then appropriately styled as well as positioned in the scene
	 * Furthermore the controller of this view is initialized
	 * @return the created scene
	 */
	public Scene initializeStorageScene() {
		
		storageContainerBox = new BorderPane();
		storageContainerBox.getStyleClass().add("customBackgroundColor");
		storageContainerBox.setPadding(new Insets(15, 15, 15, 15));
		
		
		
		storageScene = new Scene(storageContainerBox);
		storageScene.getStylesheets().add(getClass().getResource("/CSS/sudoku.css").toExternalForm());
		controller = new StorageController(this);
		
		
		storageHeaderLabel = new Label("Game Overview");
		storageContainerBox.setTop(storageHeaderLabel);

		tableviewBox = new VBox();
		tableView = new TableView<>();
		controller.setUpTableView();
		tableviewBox.getChildren().add(tableView);
		
		storageContainerBox.setCenter(tableviewBox);
		createGameStatBox();
		controller.fillTableVew();

		setUpContextMenu();
		styleLabels();
		alignLabelWithWindowSize();

		return storageScene;
	}
	
	/**
	 * This method is used to determine the size of the Main Menu UI depending on the users monitor size
	 */
	public void determineSizeToShow() {
		
		
			Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			
			if(bounds.getWidth() * 0.35 < 435) {
				
				screenWidth = bounds.getWidth() * 0.35;
			} else {
				screenWidth = 435;
			}
		
			if(bounds.getHeight() * 0.85 < 620) {
				screenHeight = bounds.getHeight() * 0.85;
			} else {
				screenHeight = 620;
			}
		}
	
	
	

	/**
	 * This method is used to initialize the {@link #contextMenu} .
	 * Into this contextMenu the menuitems {@link #deleteMenuItem} and {@link #loadMenuItem}
	 * are inserted
	 */
	public void setUpContextMenu() {
		contextMenu = new ContextMenu();
		deleteMenuItem = new MenuItem("Delete");
		loadMenuItem = new MenuItem("Load Game");
		contextMenu.getItems().addAll(deleteMenuItem, loadMenuItem);
		addContextMenuFunctionality();

		deleteMenuItem.setOnAction(controller::deleteEntry);
		loadMenuItem.setOnAction(controller::handleLoadAction);
	}

	/**
	 * This method is used to add the functionality that a right mouse click inside
	 * the {@link #tableView} opens the contextMenu defined in the
	 * {@link #setUpContextMenu()} method.
	 */
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

	/**
	 * This method is used to initialize and position the
	 * {@link #gameScoreHeaderLabel} and the UI components regarding displaying game informations.
	 * To allow better positioning of the components these are put into a own HBox 
	 * The HBox gameStatsBox is the main container for the UI objects which are created in the
	 * {@link #createAverageResultContainer()} and
	 * {@link #createOverallResultContainers()} 
	 * The usage of the gameStatsBox allows better positioning of the containers as a whole inside the window
	 */
	public void createGameStatBox() {
		HBox gameStatsBox = new HBox();
		gameScoreHeaderLabel = new Label("Game Scores");
		gameStatsBox.setPadding(new Insets(10, 0, 0, 0));
		
		gameStatsBox.setAlignment(Pos.TOP_CENTER);

		gameStatsBox.getChildren().addAll(createAverageResultContainer(), gameScoreHeaderLabel,
				createOverallResultContainers());
		tableviewBox.getChildren().add(gameStatsBox);
	
		
	}

	/**
	 * This method initializes the UI elements which are needed to show the user the
	 * average time and points achieved in his games. 
	 * To ensure better alignment and positioning the result Labels are put into its own HBox 
	 * This allows better positioning and keeps the distance between the UI objects when the size of
	 * the window changes
	 * 
	 * @return the VBOX UI Container which contains all the needed UI objects to
	 *         display average information for the user
	 */
	public VBox createAverageResultContainer() {
		// the averageResultsBox is the main container of this method in which all UI
		// components of the method are inserted into
		VBox averageResultsBox = new VBox();
		averageResultsBox.setPadding(new Insets(30, 0, 0, 0));

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

	/**
	 * This method initializes the UI elements which are needed to show the user the
	 * overall time and points achieved in his games. 
	 * To ensure better alignment and positioning the result Labels are put into its own HBox. 
	 * This allows better positioning and keeps the distance between the UI objects when the size of
	 * the window changes
	 * 
	 * @return the VBOX UI Container which contains all the needed UI Objects to
	 *         display overall Game Information for the user
	 */
	public VBox createOverallResultContainers() {
		VBox overallResultsBox = new VBox();
		overallResultsBox.setPadding(new Insets(30, 0, 0, 0));

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

	/**
	 * This method is used to style the label objects of this class with the
	 * corresponding CSS styleclass in the Sudoku CSS file.
	 */
	public void styleLabels() {
		Stream.of(averageTimeHeaderLabel, overallTimeHeaderLabel, overallPointsHeaderLabel, averagePointsHeaderLabel)
				.forEach(label -> label.getStyleClass().add("standardLabel"));

		Stream.of(averageTimeResultLabel, overallTimeResultLabel, overallPointsResultLabel, averagePointsResultLabel
				).forEach(label -> label.getStyleClass().add("storageResultLabel"));
		
		gameScoreHeaderLabel.getStyleClass().add("storageHeaderLabel");
		storageHeaderLabel.getStyleClass().add("storageHeaderLabel");
	}

	/**
	 * This method is used to ensure that the different UI objects are scaled
	 * correctly with the size of the window.
	 * To ensure this behavior a DoubleProperty object is bind to the windows width and height informations.
	 * After that the labels font size are bind to the value of the DoubleProperty.
	 */
	public void alignLabelWithWindowSize() {
		final SimpleDoubleProperty fontSizeBinding = new SimpleDoubleProperty(10);

		fontSizeBinding.bind(storageScene.widthProperty().add(storageScene.heightProperty()).divide(70));

		Stream.of(averagePointsHeaderLabel, averageTimeHeaderLabel, overallPointsHeaderLabel, overallTimeHeaderLabel,
				storageHeaderLabel, gameScoreHeaderLabel)
				.forEach(label -> label.styleProperty()
						.bind(Bindings.concat("-fx-font-size: ", fontSizeBinding.asString())));

		Stream.of(averagePointsResultLabel, averageTimeResultLabel, overallPointsResultLabel, overallTimeResultLabel)
				.forEach(label -> label.styleProperty()
						.bind(Bindings.concat("-fx-font-size: ", fontSizeBinding.asString())));

	}

	
	//getter and setter of this class
	
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
