package application;

import java.io.File;
import java.util.stream.Stream;

import controller.GameController;
import controller.MainMenuController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import logic.Gamestate;
import logic.SudokuLogic;

public abstract class BasicGameBuilder {

	protected Scene scene;
	protected BorderPane pane;
	protected VBox playButtonMenu;
	protected Button check;
	protected Button owngame;
	protected Button autosolve;
	protected Button create;
	Button empty;
	protected ToggleButton hintButton;
	protected Label gameTextLabel;
	protected HBox buttonBox;
	HBox gameLabelBox;
	HBox emptySpaceBox;

	protected Button done;

	public BasicGameBuilder() {
		pane = new BorderPane();
	}

	
	protected String gameType;

	protected SudokuField[][] textField;

	// schwierigkeit welche vom hauptmenü mit den gettern und settern unten
	// definiert wird
	protected int difficulty;

	protected GameController controller;

	protected GridPane playBoard;

	public abstract Scene initializeScene();

	public abstract void createNumbers();

	// vielleicht besser in den gamebuilderklassen direkt die boards zu zeichnen?
	public abstract GridPane createBoard();

	/**
	 * 
	 * Instanziiert die Buttons mit dem jeweilgen Button-Text Diese werden zwecks
	 * des Layouts in eine HBox gegeben Ruft die Methode styleButton und
	 * setButtonActions auf Fügt die Buttons anschließend in das übegergeben
	 * BorderPane
	 */
	public void createPlayButtons(BorderPane pane) {
		playButtonMenu = new VBox(10);
		playButtonMenu.setSpacing(10);

		buttonBox = new HBox();
		gameLabelBox = new HBox();
		emptySpaceBox = new HBox();

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

		owngame.setVisible(false);

		styleButtons();
		setButtonActions();

		pane.setBottom(playButtonMenu);
		BorderPane.setAlignment(playButtonMenu, Pos.CENTER);
	}

	/**
	 * Weist den Buttons die CSS-Style-Klasse zu Sorgt für dynamische
	 * Buttonvergrößerung Legt die Position der Buttons in der Scene fest
	 */
	public void styleButtons() {
		DoubleProperty fontSize = new SimpleDoubleProperty(10);

		Stream.of(hintButton, check, autosolve, done).forEach(button -> button.getStyleClass().add("myButton2"));

		buttonBox.getChildren().addAll(hintButton, autosolve, check, done);
		buttonBox.setSpacing(5);

		gameLabelBox.getChildren().addAll(gameTextLabel);

		// passt größße der buttons an window an
		Stream.of(hintButton, check, autosolve, done)
				.forEach(button -> button.prefHeightProperty().bind(pane.heightProperty().divide(22)));
		Stream.of(hintButton, check, autosolve, done)
				.forEach(button -> button.prefWidthProperty().bind(pane.widthProperty().divide(4.5)));

		// regelt größe von text in buttons
		fontSize.bind(pane.widthProperty().add(pane.heightProperty()).divide(100));
		Stream.of(check, autosolve, hintButton, done).forEach(
				button -> button.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString())));

		buttonBox.setAlignment(Pos.TOP_CENTER);
		gameLabelBox.setAlignment(Pos.CENTER);
		playButtonMenu.getChildren().addAll(buttonBox, gameLabelBox, emptySpaceBox);
	}

	protected Menu helpMenu;
	protected MenuItem rules;
	protected MenuBar menuBar;
	protected MenuItem save;
	protected MenuItem load;
	protected Menu file;
//	protected MenuItem clearFieldItem;
	protected MenuItem reset;
	protected Menu editMenu;
	protected Menu difficultyMenu;

	protected Menu mainMenu;
	protected MenuItem mainMenuItem;
	protected MenuItem createGameItem;

	RulesStage rule;

	/**
	 * 
	 * Erstellt die Menüleiste mit den jeweiligen MenüItems
	 */
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

		// load.setOnAction(e -> openFile());

		file.getItems().addAll(save, load);

		// newgame menu eintrag
		editMenu = new Menu("Edit");
//		clearFieldItem = new MenuItem("Clear Field");
		createGameItem = new MenuItem("Create Game");
		reset = new MenuItem("Reset");
		editMenu.getItems().addAll(createGameItem, reset);

		mainMenu = new Menu("Main Menu");

		mainMenuItem = new MenuItem("Go to Main Menu");
		mainMenu.getItems().addAll(mainMenuItem);
		menuBar.getMenus().addAll(file, editMenu, mainMenu, helpMenu);
		menuBar.getStylesheets().add("menu-bar");

	}

	/**
	 * Weist den Buttons und MenüItems Actions zu
	 */
	public void setButtonActions() {
		createGameItem.setOnAction(controller::newGameHandler);
		autosolve.setOnAction(controller::checkHandler);
	
		check.setOnAction(controller::checkHandler);
		autosolve.setOnAction(controller::autoSolveHandler);
		done.setOnAction(controller::manuelDoneHandler);
		// load.setOnAction(controller::importGame);
		// save.setOnAction(controller::saveGame);
		reset.setOnAction(controller::resetHandler);
		mainMenuItem.setOnAction(controller::switchToMainMenu);
		pane.maxWidthProperty().bind(scene.widthProperty());
		hintButton.setOnAction(controller::hintHandeler);
	}

	/**
	 * 
	 * Getter und Setter für die Variablen dieser Klasse
	 */
	public SudokuField[][] getTextField() {
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

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getGameType() {
		return gameType;
	}

	public Button getCheckButton() {
		return this.check;
	}
	
	public Button getDoneButton() {
		return this.done;
	}

	public abstract Scene getScene();

}