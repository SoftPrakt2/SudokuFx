package application;

import java.util.stream.Stream;

import controller.MainMenuController;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu {
	
	
	
	private Label selectModeLabel;
	private ToggleButton sudoku;
	private ToggleButton samurai;
	private ToggleButton freeform;
	private ToggleGroup toggleGroupGameMode;
	private Button load;
	
	
	ToggleGroup toggleGroupDifficulty;
	private Label selectDifficultyLabel;
	private ToggleButton easy;
	private ToggleButton medium;
	private ToggleButton hard;
	private ToggleButton manual;
	
	
	
	private Label createLabel;
	private Button createButton;
	
	
	
	private Button exit;
	private DoubleProperty fontSize = new SimpleDoubleProperty(10);
	
	Label welcomeLabel;
	MainMenuController controllerMainMenu = new MainMenuController(this);
	
	 Scene mainScene;
	 
	 
	
	
	public Scene setUpMainMenu() {
		
		
		BorderPane pane = new BorderPane();
		mainScene = new Scene(pane,600,600);
		mainScene.getStylesheets().add("/css/sudoku.css");
		
		
		VBox createGameBox = new VBox();
		createLabel = new Label("Step 3: Play");
		createLabel.getStyleClass().add("mainMenuLabelsSmall");
		createButton = new Button("Play");
		createGameBox.getChildren().addAll(createLabel, createButton);
		createGameBox.setAlignment(Pos.CENTER);
		
		
		exit = new Button("Exit");
		welcomeLabel = new Label("SudokuFx");
		
		//behälter für gamemode Buttons und verhalten für toggles
		VBox gameModeBox = new VBox();
		HBox gameModeButtons = new HBox();
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
		
		
		//behälter für difficulty Buttons und verhalten für toggles
		VBox gameDifficultyBox = new VBox();
		HBox difficultyButtons = new HBox();
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
	
		load = new Button("Load");
		
		
		//action events für alle buttons
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
		
	//design sachen für buttons
		//binds font text of buttons to window size
		fontSize.bind(mainScene.widthProperty().add(mainScene.heightProperty()).divide(90));
		Stream.of(sudoku, samurai, freeform, easy, medium, hard, load,exit, manual, createButton)
		.forEach(button -> button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString())));
		
		//design for buttons
		 Stream.of(sudoku, samurai, freeform,load,exit,createButton, easy, medium, hard,manual).forEach(button -> 
		    button.getStyleClass().add("myButton2"));
		 
		//größe der Buttons
		 Stream.of(sudoku, samurai, freeform,load, easy, medium, hard).forEach(button -> 
		    button.setMinWidth(gameModeButtons.getPrefWidth()));
		 
		 //style für SudokuFx Label
		 welcomeLabel.setFont(new Font("Georgia",40));
		
		
		VBox container = new VBox();
		container.setSpacing(20);
		container.getChildren().addAll(welcomeLabel,gameModeBox,gameDifficultyBox,createGameBox,load,exit);
		container.setAlignment(Pos.CENTER);
		pane.setCenter(container);
		
		
		//stylesheets für scene
		
		
	
		
		return mainScene;
		
		
	}
	
	
	
	public ToggleGroup getPlayModeToggle() {
		return toggleGroupGameMode;
	}
	
	public ToggleGroup getDifficultyToggle() {
		return toggleGroupDifficulty;
	}
	
	
	
}
