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
	
	
	static BorderPane pane = new BorderPane();
	static Scene mainScene = new Scene(pane,600,600);
	
	private ToggleButton sudoku;
	private ToggleButton samurai;
	private ToggleButton freeform;
	private ToggleGroup toggleGroupGameMode;
	private Button load;
	
	
	ToggleGroup toggleGroupDifficulty;
	private ToggleButton easy;
	private ToggleButton medium;
	private ToggleButton hard;
	private ToggleButton manual;
	private Button createButton = new Button("Create");
	private Button exit = new Button("Exit");
	private DoubleProperty fontSize = new SimpleDoubleProperty(10);
	
	Label welcomeLabel = new Label("SudokuFx");
	MainMenuController controllerMainMenu = new MainMenuController(this);
	
	
	
	public Scene setUpMainMenu() {
		
		
		//beh�lter f�r gamemode Buttons und verhalten f�r toggles
		HBox gameModeButtons = new HBox();
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
		
		
		//beh�lter f�r difficulty Buttons und verhalten f�r toggles
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
		
		
		//action events f�r alle buttons
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
		
	//design sachen f�r buttons
		//binds font text of buttons to window size
		fontSize.bind(mainScene.widthProperty().add(mainScene.heightProperty()).divide(90));
		Stream.of(sudoku, samurai, freeform, easy, medium, hard, load,exit, manual, createButton)
		.forEach(button -> button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString())));
		
		//design for buttons
		 Stream.of(sudoku, samurai, freeform,load,exit,createButton, easy, medium, hard,manual).forEach(button -> 
		    button.getStyleClass().add("myButton2"));
		 
		//gr��e der Buttons
		 Stream.of(sudoku, samurai, freeform,load, easy, medium, hard).forEach(button -> 
		    button.setMinWidth(gameModeButtons.getPrefWidth()));
		 
		 //style f�r SudokuFx Label
		 welcomeLabel.setFont(new Font("Georgia",40));
		
		
		VBox container = new VBox();
		container.setSpacing(20);
		container.getChildren().addAll(welcomeLabel,gameModeButtons,difficultyButtons,load,createButton,exit);
		container.setAlignment(Pos.CENTER);
		pane.setCenter(container);
		
		
		//stylesheets f�r scene
		mainScene.getStylesheets().add("/CSS/sudoku.css");
		
	
		
		return mainScene;
		
		
	}
	
	
	
	public  Scene getScene() {
		return mainScene;
	}
	
	public ToggleGroup getPlayModeToggle() {
		return toggleGroupGameMode;
	}
	
	public ToggleGroup getDifficultyToggle() {
		return toggleGroupDifficulty;
	}
	
}
