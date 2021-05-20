package application;

import controller.MainMenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewGamePopUp {
	
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
	
	
	Scene popUpScene; 
	Stage stage;
	
	MainMenuController controllerMainMenu = new MainMenuController(new MainMenu());
	

		public Stage createStage() {
			Scene storageScene = showPopUpScene();
			Stage currentStage = GUI.getStage();
			double windowGap = 5;
			
			stage = new Stage();
			stage.setScene(storageScene);
			stage.setX(currentStage.getX() + currentStage.getWidth() + windowGap);
			stage.setY(currentStage.getY());
			
			stage.setResizable(false);
			stage.show();
			return stage;
		}
	
	
	
	
	public Scene showPopUpScene() {
		BorderPane pane = new BorderPane();
		popUpScene = new Scene(pane,600,600);
		popUpScene.getStylesheets().add("/css/sudoku.css");
		
		
		VBox createGameBox = new VBox();
		createLabel = new Label("Step 3: Play");
		createLabel.getStyleClass().add("mainMenuLabelsSmall");
		createButton = new Button("Create");
		createGameBox.getChildren().addAll(createLabel, createButton);
		createGameBox.setAlignment(Pos.CENTER);
		
		
		
		
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
		
		createButton.setOnAction(controllerMainMenu::handleGameStart);
		
		VBox container = new VBox();
		container.setSpacing(20);
		container.getChildren().addAll(gameModeBox,gameDifficultyBox,createGameBox);
		
		
		
		
		container.setAlignment(Pos.CENTER);
		pane.setCenter(container);
		
		return popUpScene;
	}
	
	
	
	
	
	
	
	
}
