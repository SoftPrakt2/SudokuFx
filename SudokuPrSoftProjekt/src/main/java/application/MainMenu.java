package application;

import java.io.IOException;
import java.util.stream.Stream;

import controller.ModeController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * This class is used for creating the main menu of our programm, several UI Objects like Buttons or Labels are generated and aligned within several JavaFx containers
 * @author gruber 
 */
public class MainMenu {

	
	protected ToggleButton sudoku;
	protected ToggleButton samurai;
	protected ToggleButton freeform;
	private ToggleGroup toggleGroupGameMode;
	
	private Label selectModeLabel;
	
	private VBox gameDifficultyBox;
	
	private ToggleGroup toggleGroupDifficulty;
	private Label selectDifficultyLabel;
	protected ToggleButton easy;
	protected ToggleButton medium;
	protected ToggleButton hard;
	protected ToggleButton manual;
	private VBox gameModeBox;

	private VBox createGameBox;
	private Label createLabel;
	private Button createButton;
	
	private Button load;
	private Button exit;
	private final SimpleDoubleProperty fontSizeButtons = new SimpleDoubleProperty(10);
	private final SimpleDoubleProperty fontSizeLabel = new SimpleDoubleProperty(40);

	private Label welcomeLabel;
	private ModeController controllerMainMenu;

	private Scene mainScene;

	private BorderPane pane;

	
	
	
	public void setUpMainMenu() {

		controllerMainMenu = new ModeController(this);
		pane = new BorderPane();
		mainScene = new Scene(pane, 670, 670);

		mainScene.getStylesheets().add("css/sudoku.css");

		welcomeLabel = new Label("SudokuFx");
		createGameModeButtons();
		createDifficultyButtons();
		createMainMenuPlayButton();
		
		
		load = new Button("Load");
		exit = new Button("Exit");
		

		setButtonActions();
		styleGUIObjects();

		VBox container = new VBox();
		container.setSpacing(20);
		container.getChildren().addAll(welcomeLabel, gameModeBox, gameDifficultyBox, createGameBox, load, exit);
		container.setAlignment(Pos.CENTER);
		pane.setCenter(container);

	}

	
	/**
	 * This method creates the togglebuttons which are responsible for selecting a gamemode and a information label describing the use of the specific togglebuttons in the mainmenu 
	 * for better alignment the buttons are put into a HBox 
	 * To ensure that only one button is pressed at a time the togglebuttons in this method are put into a ToggleGroup
	 */
	public void createGameModeButtons() {
		gameModeBox = new VBox();
		HBox gameModeButtons = new HBox();
		selectModeLabel = new Label("Step 1: Choose a GameMode");
		

		gameModeButtons.setAlignment(Pos.CENTER);
		gameModeButtons.setPrefWidth(80);
		gameModeButtons.setSpacing(5);
		toggleGroupGameMode = new ToggleGroup();
		
		
		sudoku = new ToggleButton("Sudoku");
		samurai = new ToggleButton("Samurai");
		freeform = new ToggleButton("Freeform");
		sudoku.setToggleGroup(toggleGroupGameMode);
		samurai.setToggleGroup(toggleGroupGameMode);
		freeform.setToggleGroup(toggleGroupGameMode);

		
		gameModeButtons.getChildren().addAll(sudoku, samurai, freeform);
		gameModeBox.getChildren().addAll(selectModeLabel, gameModeButtons);
		gameModeBox.setSpacing(2);
		gameModeBox.setAlignment(Pos.CENTER);
	}

	/**
	 * This method creates the buttons which are responsible for selecting the difficulty of a game and a information label describing the use of the specific buttons in the mainmenu 
	 * For better alignment the buttons are put into a HBox 
	 * To ensure that only one button is pressed at a time the togglebuttons in this method are put into a ToggleGroup
	 */
	public void createDifficultyButtons() {
		gameDifficultyBox = new VBox();
		HBox difficultyButtons;
		difficultyButtons = new HBox();
		selectDifficultyLabel = new Label("Step 2: Choose the difficulty of the game");
		

		difficultyButtons.setAlignment(Pos.CENTER);
		difficultyButtons.setPrefWidth(80);
		difficultyButtons.setSpacing(5);
		toggleGroupDifficulty = new ToggleGroup();
		easy = new ToggleButton("Easy");
		medium = new ToggleButton("Medium");
		hard = new ToggleButton("Hard");
		manual = new ToggleButton("Manual");
		easy.setToggleGroup(toggleGroupDifficulty);
		medium.setToggleGroup(toggleGroupDifficulty);
		hard.setToggleGroup(toggleGroupDifficulty);
		manual.setToggleGroup(toggleGroupDifficulty);
		difficultyButtons.getChildren().addAll(easy, medium, hard, manual);
		gameDifficultyBox.getChildren().addAll(selectDifficultyLabel, difficultyButtons);
		gameDifficultyBox.setAlignment(Pos.CENTER);
		toggleGroupDifficulty.getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(true);
		});
	}
	
	/**
	 * This method creates the layout and the play button as well as a label describing the use of the button
	 * For better alignment the button and the label are put into a VBox
	 */
	public void createMainMenuPlayButton() {
		createGameBox = new VBox();
		createLabel = new Label("Step 3: Play");
		createButton = new Button("Play");
		createGameBox.getChildren().addAll(createLabel, createButton);
		createGameBox.setAlignment(Pos.CENTER);
		createButton.setDisable(true);
	}
	
	
	
	/**
	 * This method is used to connect the buttons of this view with methods which are defined in the @see SudokuFx.SudokuFx.application.ModeController class
	 */
	public void setButtonActions() {
		sudoku.setOnAction(controllerMainMenu::handleToSudoku);
		hard.setOnAction(controllerMainMenu::handleHard);
		easy.setOnAction(controllerMainMenu::handleEasy);
		medium.setOnAction(controllerMainMenu::handleMedium);
		manual.setOnAction(controllerMainMenu::handleManual);
		samurai.setOnAction(controllerMainMenu::handleToSamurai);
		freeform.setOnAction(controllerMainMenu::handleToFreeForm);
		load.setOnAction(event -> {
			try {
				controllerMainMenu.handleToLoad(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		createButton.setOnAction(controllerMainMenu::handleGameStart);
		exit.setOnAction(controllerMainMenu::handleExit);
	}
	
	
	
	/**
	 * This method is responsible for defining the look of the UI objects of this class
	 * The definition of the UI objects look is handled in the projects css style class 
	 * To ensure all UI objects scale with the windows size the UI objects size properties are bind to the size and height of the mainscene
	 */
	public void styleGUIObjects() {
		
		fontSizeButtons.bind(mainScene.widthProperty().add(mainScene.heightProperty()).divide(100));
		fontSizeLabel.bind(mainScene.widthProperty().add(mainScene.heightProperty()).divide(30));

		Stream.of(sudoku, samurai, freeform, easy, medium, hard, load, exit, manual, createButton).forEach(
				button -> button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeButtons.asString())));

		Stream.of(sudoku, samurai, freeform, easy, medium, hard, load, exit, manual, createButton)
				.forEach(button -> button.prefHeightProperty().bind(pane.heightProperty().divide(18)));

		Stream.of(sudoku, samurai, freeform, easy, medium, hard, load, exit, manual, createButton)
				.forEach(button -> button.prefWidthProperty().bind(pane.widthProperty().divide(4.75)));

		// design for buttons
		Stream.of(sudoku, samurai, freeform, load, exit, createButton, easy, medium, hard, manual)
				.forEach(button -> button.getStyleClass().add("myButton2"));

		welcomeLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeLabel.asString()));
		
		
		Stream.of(sudoku, samurai, freeform, easy, medium, hard, load, exit, manual, createButton)
		.forEach(button -> button.prefWidthProperty().bind(pane.widthProperty().divide(4.75)));
		
		

		welcomeLabel.getStyleClass().add("welcomeLabel");
		

		// größe der Buttons
		Stream.of(selectModeLabel, selectDifficultyLabel, createLabel)
				.forEach(label -> label.getStyleClass().add("mainMenuLabelsSmall"));

	}
	
	
	/**
	 * Gets the ToggleGroup of the playMode (Sudoku, Samurai, Freeform) buttons
	 * @return toggleGroupGameMode
	 */
	public ToggleGroup getPlayModeToggle() {
		return toggleGroupGameMode;
	}

	/**
	 * Gets the ToggleGroup of the difficulty (Easy,Medium,Hard, Manual) buttons
	 * @return toggleGroupGameMode
	 */
	public ToggleGroup getDifficultyToggle() {
		return toggleGroupDifficulty;
	}
	
	/**
	 * Gets this class main UI container 
	 * @return pane
	 */
	public Pane getPane() {
		return pane;
	}
	
	/**
	 * Gets this class scene bbject 
	 * @return mainScene
	 */
	public Scene getScene() {
		return mainScene;
	}
	
	/**
	 * Gets the createButton
	 * @return createButton
	 */
	public Button getPlayButton() {
		return createButton;
	}
	

}
