package application;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.GameController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;

public abstract class BasicGameBuilder {

	public Scene scene;
	protected BorderPane pane;
	protected VBox playButtonMenu;
	protected Button check;
	protected Button owngame;
	protected Button autosolve;
	protected Button create;
	protected Button empty;
	protected Button hintButton;
	protected Label gameNotificationLabel;
	protected HBox buttonBox;
	protected HBox gameLabelBox;
	protected HBox emptySpaceBox;
	protected StatusBar statusbar;
	protected Label gameInfoLabel;
	protected Label playTimeLabel;
	protected ArrayList<ChangeListener> listeners = new ArrayList<>();

	protected Button done;
	
	VBox toolbox = new VBox();
	BasicGameLogic model;
	
	
	public BasicGameBuilder(BasicGameLogic model) {
		pane = new BorderPane();
		this.model = model;
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
		ToolBar toolbar = new ToolBar();
		FontAwesome fontAwesome = new FontAwesome();
		Glyph g = fontAwesome.create(FontAwesome.Glyph.CHECK);
		Glyph hint = fontAwesome.create(FontAwesome.Glyph.SUPPORT);
		Glyph autosolv = fontAwesome.create(FontAwesome.Glyph.CALCULATOR);
		
		
		
		hintButton = new Button("");
		hintButton.setGraphic(hint);

		
		
		autosolve = new Button("_A");
		
		autosolve.setGraphic(autosolv);
		create = new Button("Create Game");
		owngame = new Button("Custom Game");
		check = new Button("");
		check.setGraphic(g);
		
		

		KeyCombination autoS = new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN);
		Mnemonic mn = new Mnemonic(autosolve,autoS);
		scene.addMnemonic(mn);

		done = new Button("done");

		owngame.setVisible(false);

		
		setButtonActions();
		
		toolbar.getItems().addAll(hintButton, autosolve, check, done);
		
		toolbox.getChildren().add(toolbar);
	
	}
	public void addListeners(SudokuField[][] sudokuField) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				ChangeListener<String> changeListener = new ChangeListener<>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						// TODO Auto-generated method stub
						controller.compareResult(sudokuField);
						
					}
				};

				sudokuField[col][row].textProperty().addListener(changeListener);
				listeners.add(changeListener);
				
//				sudokuField[col][row].textProperty().addListener(new ChangeListener<String>() {
//					@Override
//					public void changed(ObservableValue<? extends String> observable, String oldValue,
//							String newValue) {
//						controller.compareResult(sudokuField);
//					}
//				});
				
			}
		}
	}

	public void removeListeners(SudokuField[][] sudokuField) {
		
		for (ChangeListener l : listeners) {
			for (SudokuField[] sss : sudokuField) {
				for (SudokuField field : sss) {
				
				field.textProperty().removeListener(l);
				}
			}
		}
	}

	
	

	protected Menu helpMenu;
	protected MenuItem rules;
	protected MenuBar menuBar;
	protected MenuItem save;
	protected MenuItem load;
	protected Menu file;

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
		VBox menuBox = new VBox();
		
		// menuBar for the scene
		menuBar = new MenuBar();
		StatusBar test = new StatusBar();

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
		
		toolbox.getChildren().addAll(menuBar);
		pane.setTop(toolbox);
	
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
	//	 load.setOnAction(controller::importGame);
		  save.setOnAction(controller::saveGame);
		reset.setOnAction(controller::resetHandler);
		mainMenuItem.setOnAction(controller::switchToMainMenu);
		hintButton.setOnAction(controller::hintHandeler);
	}
	
	
	public void createStatusBar(BorderPane pane) {
		gameNotificationLabel = new Label();
		playTimeLabel = new Label();
		gameInfoLabel = new Label();
	
		StatusBar statusBar = new StatusBar();
		statusBar.setText("");
		
		statusBar.getRightItems().add(gameInfoLabel);
		statusBar.getRightItems().add(playTimeLabel);
		
		statusBar.getLeftItems().add(gameNotificationLabel);
		
		
		Stream.of(gameInfoLabel, playTimeLabel, gameNotificationLabel).forEach(label -> label.getStyleClass().add("gamelabel"));
	
		pane.setBottom(statusBar);
	}
	

	/**
	 * 
	 * Getter und Setter für die Variablen dieser Klasse
	 */
	public SudokuField[][] getTextField() {
		return textField;
	}

	public Label getGameLabel() {
		return gameNotificationLabel;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getDifficulty() {
		return difficulty;
	}
	
	public Label getPlayTimeLabel() {
		return playTimeLabel;
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
	
	//hat infos über punkte, schwierigkeit, und spielzeit
	public Label getGameInfoLabel() {
		return gameInfoLabel;
	}
	
	public Label getGameNotificationLabel() {
		return gameNotificationLabel;
	}
	
	

	public abstract Scene getScene();

}