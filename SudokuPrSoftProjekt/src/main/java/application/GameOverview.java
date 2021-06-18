package application;

import java.util.stream.Stream;

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


/**
 * This class defines the appearance of the programs game overview 
 * To do so different UI Components like a table view, a context menu for this table view and different 
 * labels are created
 * @author grube
 *
 */
public class GameOverview {

	/**
	 * Controller of this View class which will be initialized when opening this
	 * class in the program
	 */
	private StorageController controller;

	/**
	 * Stage object of this class
	 */
	private Stage stage;

	/**
	 * Scene object of this class
	 */
	private Scene storageScene;

	/**
	 * The tableview of this scene
	 */
	private TableView<BasicGameLogic> tableView;

	/**
	 * UI Objects which will be used for the contextmenu of the {@link #tableView}
	 */
	private ContextMenu contextMenu;
	private MenuItem deleteMenuItem;
	private MenuItem loadMenuItem;

	/**
	 * Main Container for UI Objects in this Class
	 */
	private BorderPane storageContainerBox;


	/**
	 * Labels for different headlines in the UI window
	 */
	private Label storageHeaderLabel;
	private Label gameScoreHeaderLabel;
	private Label overallPointsHeaderLabel;
	private Label averagePointsHeaderLabel;
	private Label averageTimeHeaderLabel;
	private Label overallTimeHeaderLabel;

	/**
	 * Labels which will be later filled with Information about the users played
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
	 * This method is used to set up the Stage Object of this class
	 * and show it in the UI 
	 * @return
	 */
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

	/**
	 * This method is used to set up this classes Scene object {@link #storageScene}
	 * Indoing this the main UI container {@link #storageContainerBox} of this class is initialized
	 * filled with the needed UI objects
	 * and appropriately styled as well as positioned in the scene
	 * Furthermore the controller of this View is initialized
	 * @return the created scene
	 */
	public Scene initializeStorageScene() {
		
		
		storageContainerBox = new BorderPane();
		storageContainerBox.getStyleClass().add("customBackgroundColor");
		storageContainerBox.setPadding(new Insets(15, 15, 15, 15));
		
		
		storageScene = new Scene(storageContainerBox, 510, 600);
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
	 * This method is used to initialize the {@link #contextMenu} Into this
	 * contextMenu the menuitems {@link #deleteMenuItem} and {@link #loadMenuItem}
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
	 * the {@link #tableView} opens the contextmenu defined in the
	 * {@link #setUpContextMenu()} method
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
	 * {@link #gameScoreHeaderLabel} label to allow better positioning of the label
	 * it is put into its own HBox.
	 * The HBox gameStatsBox is the main container forthe UI Objects which are created in the
	 * {@link #createAverageResultContainer()} and
	 * {@link #createOverallResultContainers()} The usage of the gameStatsBox allows
	 * better positioning of the containers as a whole inside the window
	 */
	public void createGameStatBox() {
		HBox gameStatsBox = new HBox();
		gameScoreHeaderLabel = new Label("Game Scores");
		gameStatsBox.setPadding(new Insets(10, 10, 10, 10));
		gameStatsBox.setSpacing(70);

		gameStatsBox.getChildren().addAll(createAverageResultContainer(), gameScoreHeaderLabel,
				createOverallResultContainers());
		tableviewBox.getChildren().add(gameStatsBox);
		BasicGameBuilder o;
		
	}

	/**
	 * This method initializes the UI elements which are needed to show the user the
	 * average time and points achieved in his games To ensure better alignment and
	 * positioning the result Labels are put into its own HBox This allows better
	 * positioning and keeps the distance between the UI objects when the size of
	 * the window changes
	 * 
	 * @return the VBOX UI Container which contains all the needed UI Objects to
	 *         display average Information for the user
	 */
	public VBox createAverageResultContainer() {
		// the averageResultsBox is the main Container of this method in which all UI
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
	 * overall time and points achieved in his games To ensure better alignment and
	 * positioning the result Labels are put into its own HBox This allows better
	 * positioning and keeps the distance between the UI objects when the size of
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
	 * corresponding CSS styleclass in the Sudoku CSS file
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
	 * This method is used to ensure that the different UI Objects are scaled
	 * correctly with the size of the window To ensure this behaviour a
	 * DoubleProperty Object is bind to the windows width and height informations
	 * After that the labels font size are bind to the value of the DoubleProperty
	 */
	public void alignLabelWithWindowSize() {
		final SimpleDoubleProperty fontSizeBinding = new SimpleDoubleProperty(10);

		fontSizeBinding.bind(storageScene.widthProperty().add(storageScene.heightProperty()).divide(65));

		Stream.of(averagePointsHeaderLabel, averageTimeHeaderLabel, overallPointsHeaderLabel, overallTimeHeaderLabel,
				storageHeaderLabel, gameScoreHeaderLabel)
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