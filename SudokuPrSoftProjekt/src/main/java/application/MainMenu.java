package application;

import java.util.stream.Stream;

import controller.ModeController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu {
	
	
	
	private Label selectModeLabel;
	VBox gameModeBox;
	HBox gameModeButtons;
	private ToggleButton sudoku;
	private ToggleButton samurai;
	private ToggleButton freeform;
	private ToggleGroup toggleGroupGameMode;
	private Button load;
	
	
	VBox gameDifficultyBox;
	HBox difficultyButtons;
	
	ToggleGroup toggleGroupDifficulty;
	private Label selectDifficultyLabel;
	private ToggleButton easy;
	private ToggleButton medium;
	private ToggleButton hard;
	private ToggleButton manual;
	
	
	
	private Label createLabel;
	private Button createButton;
	
	
	
	private Button exit;
	private SimpleDoubleProperty fontSizeButtons = new SimpleDoubleProperty(10);
	private SimpleDoubleProperty fontSizeLabel = new SimpleDoubleProperty(40);
	
	Label welcomeLabel;
	ModeController controllerMainMenu = new ModeController(this);
	
	 Scene mainScene;
	 
	 BorderPane pane;
	 
	 
	
	
	public void setUpMainMenu() {
		
		
		 pane = new BorderPane();
		mainScene = new Scene(pane,670,670);
		
		mainScene.getStylesheets().add("css/sudoku.css");
		
		createGameModeButtons(pane);
		createDifficultyButtons(pane);
		
		
		
		
		
		VBox createGameBox = new VBox();
		createLabel = new Label("Step 3: Play");
		createLabel.getStyleClass().add("mainMenuLabelsSmall");
		createButton = new Button("Play");
		createGameBox.getChildren().addAll(createLabel, createButton);
		createGameBox.setAlignment(Pos.CENTER);
		
		
		exit = new Button("Exit");
		welcomeLabel = new Label("SudokuFx");
	
	
	
		load = new Button("Load");
		
		
		setButtonActions();
		styleButtons();
		
	
		
		
		VBox container = new VBox();
		container.setSpacing(20);
		container.getChildren().addAll(welcomeLabel,gameModeBox,gameDifficultyBox,createGameBox,load,exit);
		container.setAlignment(Pos.CENTER);
		pane.setCenter(container);
		
		
		//stylesheets für scene
		
	
	}
	
	
	public void createGameModeButtons(BorderPane pane) {
		gameModeBox = new VBox();
		gameModeButtons = new HBox();
		selectModeLabel = new Label("Step 1: Choose a GameMode");
		selectModeLabel.getStyleClass().add("mainMenuLabelsSmall");
		
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
		
		
		gameModeButtons.getChildren().addAll(sudoku,samurai,freeform);
		gameModeBox.getChildren().addAll(selectModeLabel,gameModeButtons);
		gameModeBox.setSpacing(2);
		gameModeBox.setAlignment(Pos.CENTER);
	}
	
	
	public void createDifficultyButtons(BorderPane pane) {
		 gameDifficultyBox = new VBox();
		 difficultyButtons = new HBox();
		selectDifficultyLabel = new Label ("Step 2: Choose the difficulty of the game");
		selectDifficultyLabel.getStyleClass().add("mainMenuLabelsSmall");
		
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
		difficultyButtons.getChildren().addAll(easy,medium,hard,manual);
		gameDifficultyBox.getChildren().addAll(selectDifficultyLabel, difficultyButtons);
		gameDifficultyBox.setAlignment(Pos.CENTER);
	}
	
	public void setButtonActions() {
		sudoku.setOnAction(controllerMainMenu::handleToSudoku);
		hard.setOnAction(controllerMainMenu::handleHard);
		easy.setOnAction(controllerMainMenu::handleEasy);
		medium.setOnAction(controllerMainMenu::handleMedium);
		manual.setOnAction(controllerMainMenu::handleManual);
		samurai.setOnAction(controllerMainMenu::handleToSamurai);
		freeform.setOnAction(controllerMainMenu::handleToFreeForm);
		load.setOnAction(controllerMainMenu::handleToLoad);
		createButton.setOnAction(controllerMainMenu::handleGameStart);
		exit.setOnAction(controllerMainMenu::handleExit);
	}
	
	public void styleButtons() {
		//design sachen für buttons
				//binds font text of buttons to window size
		fontSizeButtons.bind(mainScene.widthProperty().add(mainScene.heightProperty()).divide(100));
		fontSizeLabel.bind(mainScene.widthProperty().add(mainScene.heightProperty()).divide(30));
		
				Stream.of(sudoku, samurai, freeform, easy, medium, hard, load,exit, manual, createButton)
				.forEach(button -> button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeButtons.asString())));
				
				Stream.of(sudoku, samurai, freeform,easy, medium, hard, load,exit, manual, createButton)
				.forEach(button -> button.prefHeightProperty().bind(pane.heightProperty().divide(18)));
				
				Stream.of(sudoku, samurai, freeform, easy, medium, hard, load,exit, manual, createButton)
				.forEach(button -> button.prefWidthProperty().bind(pane.widthProperty().divide(4.75)));
				
				//design for buttons
				 Stream.of(sudoku, samurai, freeform,load,exit,createButton, easy, medium, hard,manual).forEach(button -> 
				    button.getStyleClass().add("myButton2"));
				 
				 welcomeLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeLabel.asString()));
				 
				 welcomeLabel.getStyleClass().add("welcomeLabel");
			//	 welcomeLabel.setFont(new Font("Georgia",40));
				 
				//größe der Buttons
				 Stream.of(sudoku, samurai, freeform,load, easy, medium, hard).forEach(button -> 
				    button.setMinWidth(gameModeButtons.getPrefWidth()));
				 
				
				 
				// System.out.println(GUI.getStage().getWidth());
				 
				 //style für SudokuFx Label
				
			//	Label.class.cast(welcomeLabel).setFont(Font.font("Georgia", newFontSizeInt));
	}
	
	
	
	
	public ToggleGroup getPlayModeToggle() {
		return toggleGroupGameMode;
	}
	
	public ToggleGroup getDifficultyToggle() {
		return toggleGroupDifficulty;
	}
	
	public Pane getPane() {
		return pane;
	}
	
	public Scene getScene() {
		return mainScene;
	}
	
}
