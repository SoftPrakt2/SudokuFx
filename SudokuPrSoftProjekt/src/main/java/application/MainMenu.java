package application;

import java.util.stream.Stream;

import controller.MainMenuController;
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

public class MainMenu {
	
	
	static BorderPane pane = new BorderPane();
	static Scene mainScene = new Scene(pane,600,600);
	private ToggleButton sudoku;
	private ToggleButton samurai;
	private ToggleButton freeform;
	ToggleGroup toggleGroup;
	private Button load;
	
	
	ToggleGroup toggleGroupDifficulty;
	private ToggleButton easy;
	private ToggleButton medium;
	private ToggleButton hard;
	private ToggleButton manual;
	private Button createButton = new Button("Create");
	private Button exit = new Button("Exit");
	
	MainMenuController controllerMainMenu = new MainMenuController(this);
	
	
	public Scene setUpMainMenu() {
		
		VBox container = new VBox();
		container.setSpacing(20);
		
		HBox gameModeButtons = new HBox();
		gameModeButtons.setAlignment(Pos.CENTER);
		gameModeButtons.setPrefWidth(80);
		gameModeButtons.setSpacing(5);
		
		toggleGroup = new ToggleGroup();
		sudoku = new ToggleButton("Sudoku");
		//sudoku.setPickOnBounds(false);
		samurai = new ToggleButton("Samurai");
		freeform = new ToggleButton("Freeform");
		sudoku.setToggleGroup(toggleGroup);
		samurai.setToggleGroup(toggleGroup);
		freeform.setToggleGroup(toggleGroup);
		gameModeButtons.getChildren().addAll(sudoku,samurai,freeform);
		
		
		HBox difficultyButtons = new HBox();
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
		
	
		
		load = new Button("Load");
		
		
		sudoku.setOnAction(controllerMainMenu::handleToSudoku);
		hard.setOnAction(controllerMainMenu::handleHard);
		easy.setOnAction(controllerMainMenu::handleEasy);
		medium.setOnAction(controllerMainMenu::handleMedium);
		manual.setOnAction(controllerMainMenu::handleManual);
		samurai.setOnAction(controllerMainMenu::handleToSamurai);
		freeform.setOnAction(controllerMainMenu::handleToFreeForm);
		load.setOnAction(controllerMainMenu::handleToLoad);
		
		//nur test
		createButton.setOnAction(controllerMainMenu::handleGameStart);
		
		exit.setOnAction(controllerMainMenu::handleExit);
		
		//design for buttons
		 Stream.of(sudoku, samurai, freeform,load,exit,createButton, easy, medium, hard,manual).forEach(button -> 
		    button.getStyleClass().add("myButton2"));
		
		//gr��e der Buttons
		 Stream.of(sudoku, samurai, freeform,load, easy, medium, hard).forEach(button -> 
		    button.setMinWidth(gameModeButtons.getPrefWidth()));
		 
		
		
		Label welcomeLabel = new Label("SudokuFx");
		welcomeLabel.setFont(new Font("Georgia",40));
		
		container.getChildren().addAll(welcomeLabel,gameModeButtons,difficultyButtons,load,createButton,exit);
		container.setAlignment(Pos.CENTER);
		
		pane.setCenter(container);
		
		
		
		mainScene.getStylesheets().add("/CSS/sudoku.css");
		
		//button styles
		
		return mainScene;
		
		
	}
	public static int testtt;
	
	public  Scene getScene() {
		return mainScene;
	}
	
	public ToggleGroup getPlayModeToggle() {
		return toggleGroup;
	}
	
	public ToggleGroup getDifficultyToggle() {
		return toggleGroupDifficulty;
	}
	
}
