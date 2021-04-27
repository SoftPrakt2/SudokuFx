package application;

import java.io.File;
import java.util.stream.Stream;

import controller.BasicController;
import controller.MainMenuController;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class BasicGameBuilder {

	protected VBox playButtonMenu;
	protected Button check;
	protected Button owngame;
	protected Button autosolve;
	protected Button create;
	protected ToggleButton hintButton;
	protected Label gameTextLabel;
	
	protected Button done;
	
	
//variablen für die zeitmessung
	protected long startTime;

	protected SudokuField[][] textField;
	
	//schwierigkeit welche vom hauptmenü mit den gettern und settern unten definiert wird
	protected int difficulty;
	
	//punkte für den spielstand 
	protected int gamePoints = 10;

	ButtonBar buttonBar;

	BasicController controller;


	GridPane playBoard;

	// change back to void
	public abstract Scene initializeScene();
	
	public abstract void createNumbers();

	// vielleicht besser in den gamebuilderklassen direkt die boards zu zeichnen?
	public abstract GridPane createBoard();

	// creates Vbox and adds several buttons to scene
	public void createPlayButtons(BorderPane pane) {

		playButtonMenu = new VBox(10);
		// playButtonMenu.setPrefWidth(100);
		playButtonMenu.setSpacing(10);

		HBox buttonBox = new HBox();
		HBox gameLabelBox = new HBox();
		HBox emptySpaceBox = new HBox();
		
		Button empty = new Button("");
		empty.setVisible(false);
		emptySpaceBox.getChildren().add(empty);
		
		gameTextLabel = new Label("Game ongoing!");
		gameTextLabel.setFont(new Font("Dekko", 25));
		

	
		hintButton = new ToggleButton("Hint");
		
		check = new Button("Check");
		autosolve = new Button("AutoSolve");
		create = new Button("Create Game");
		owngame = new Button("Custom Game");
	
		done = new Button("done");
		done.setVisible(false);
		//if(MainMenuController.difficulty == 0) done.setVisible(true);
		
		owngame.setVisible(false);

		Stream.of(hintButton, check, autosolve, done)
				.forEach(button -> button.getStyleClass().add("myButton2"));
		
		
		buttonBox.getChildren().addAll(hintButton, autosolve, check, done);
		buttonBox.setSpacing(5);
	
		gameLabelBox.getChildren().addAll(gameTextLabel);

		Stream.of(hintButton, check, autosolve, done).forEach(button -> button.prefHeightProperty().bind(pane.heightProperty().divide(22)));
		Stream.of(hintButton, check, autosolve, done).forEach(button -> button.prefWidthProperty().bind(pane.widthProperty().divide(7)));
		
		
		buttonBox.setAlignment(Pos.TOP_CENTER);
		gameLabelBox.setAlignment(Pos.CENTER);
		playButtonMenu.getChildren().addAll(buttonBox, gameLabelBox,empty);

		
		pane.setBottom(playButtonMenu);
		BorderPane.setAlignment(playButtonMenu, Pos.CENTER);
		
		
		
	
		
	}

	protected Menu helpMenu;
	protected MenuItem rules;
	protected MenuBar menuBar;
	protected MenuItem save;
	protected MenuItem load;
	protected Menu overView;
	protected Menu file;
	protected MenuItem clearFieldItem;
	protected MenuItem reset;
	protected Menu editMenu;
	protected Menu difficultyMenu;

	protected Menu mainMenu;
	protected MenuItem mainMenuItem;
	protected MenuItem createGameItem;

	protected ToggleGroup difficultyToggle;

	protected RadioMenuItem easy;
	protected RadioMenuItem medium;
	protected RadioMenuItem hard;
	

	RulesStage rule;

	Label label = new Label("easy");

	public void createMenuBar(BorderPane pane) {

		// menuBar for the scene
		menuBar = new MenuBar();
		pane.setTop(menuBar);

		// Help eintrag mit rules menu
		helpMenu = new Menu("Help");
		rules = new MenuItem("Rules");
		rules.setOnAction(e -> {
			rule = new RulesStage();
			rule.showPopUp("Sudoku Rules");
		});
		helpMenu.getItems().add(rules);

		

		// savemenu mit save und load optionen
		file = new Menu("File");
		save = new MenuItem("Save");
		load = new MenuItem("Load");

		load.setOnAction(e -> openFile());

		file.getItems().addAll(save, load);
		

		// newgame menu eintrag
		editMenu = new Menu("Edit");
		clearFieldItem = new MenuItem("Clear Field");
		createGameItem = new MenuItem("Create Game");
		reset = new MenuItem("Reset");
		editMenu.getItems().addAll(createGameItem, clearFieldItem, reset);

		// overview Menu opens overview window on click
		overView = new Menu("Overview");
		

		mainMenu = new Menu("Main Menu");

		mainMenuItem = new MenuItem("Go to Main Menu");
		mainMenu.getItems().addAll(mainMenuItem);
		menuBar.getMenus().addAll(file, editMenu, mainMenu,helpMenu);
		menuBar.getStylesheets().add("menu-bar");

	}

	public File openFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose a Sudoku");
		File defaultDirectory = new File("d:/sudoku");
		chooser.setInitialDirectory(defaultDirectory);
		Stage window = GUI.getStage();
		File selectedFile = chooser.showOpenDialog(window);

		return selectedFile;
	}

	
	
	
	//gehört vielleicht in controller muss besprochen werden
	
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	
	public int getGamePoints() {
		return gamePoints;
	}
	
	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}
	

	public  SudokuField[][] getTextField() {
		return textField;
	}
	
	
	
	public Label getGameLabel() {
		return gameTextLabel;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	

	public abstract Scene getScene();

}