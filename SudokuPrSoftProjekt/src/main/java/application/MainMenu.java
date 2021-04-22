package application;

import java.util.stream.Stream;

import controller.MainMenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu {
	
	
	static BorderPane pane = new BorderPane();
	static Scene mainScene = new Scene(pane,400,400);
	private Button sudoku;
	private Button samurai;
	private Button freeform;
	private Button load;
	
	
	
	private ToggleButton easy;
	private ToggleButton medium;
	private ToggleButton hard;
	private Button setting = new Button("Settings");
	private Button exit = new Button("Exit");
	
	MainMenuController controllerMainMenu = new MainMenuController(this);
	
	
	public Scene setUpMainMenu() {
		
		VBox container = new VBox();
		container.setSpacing(10);
		
		HBox gameModeButtons = new HBox();
		gameModeButtons.setAlignment(Pos.CENTER);
		gameModeButtons.setPrefWidth(80);
		
		sudoku = new Button("Sudoku");
		samurai = new Button("Samurai");
		freeform = new Button("Freeform");
		gameModeButtons.getChildren().addAll(sudoku,samurai,freeform);
		
		
		HBox difficultyButtons = new HBox();
		difficultyButtons.setAlignment(Pos.CENTER);
		difficultyButtons.setPrefWidth(80);
		
		easy = new ToggleButton("Easy");
		medium = new ToggleButton("Medium");
		hard = new ToggleButton("Hard");
		
		difficultyButtons.getChildren().addAll(easy,medium,hard);
		
	
		
		load = new Button("Load");
		
		
		sudoku.setOnAction(controllerMainMenu::handleToSudoku);
		hard.setOnAction(controllerMainMenu::handleHard);
		easy.setOnAction(controllerMainMenu::handleEasy);
		
		samurai.setOnAction(controllerMainMenu::handleToSamurai);

		load.setOnAction(controllerMainMenu::handleToLoad);
		
		exit.setOnAction(controllerMainMenu::handleExit);
		
		//design for buttons
		 Stream.of(sudoku, samurai, freeform,load,exit,setting).forEach(button -> 
		    button.getStyleClass().add("button1"));
		
		//größe der Buttons
		 Stream.of(sudoku, samurai, freeform,load).forEach(button -> 
		    button.setMinWidth(gameModeButtons.getPrefWidth()));
		 
		
		
		Label welcomeLabel = new Label("SudokuFx");
		welcomeLabel.setFont(new Font("Arial",20));
		
		container.getChildren().addAll(welcomeLabel,gameModeButtons,difficultyButtons,load,setting,exit);
		container.setAlignment(Pos.CENTER);
		
		pane.setCenter(container);
		
		
		
		mainScene.getStylesheets().add("/CSS/sudoku.css");
		
		
		
		
		

		//button styles
		
		
		
		
		
		
		
		
		return mainScene;
		
		
		
		
		
		
		
		
		
	}
	
	
	public  Scene getScene() {
		return mainScene;
	}
	
	
}
