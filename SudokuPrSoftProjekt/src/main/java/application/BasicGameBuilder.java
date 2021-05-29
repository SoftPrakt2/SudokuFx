package application;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.GameController;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;

public abstract class BasicGameBuilder {

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

	protected Label liveTimeLabel;

	protected ArrayList<ChangeListener> listeners = new ArrayList<>();
	protected ToolBar toolbar;

	protected AnimationTimer timer;

	int width;
	int height;

	protected Button done;

	VBox toolbox = new VBox();
	BasicGameLogic model;

	NewGamePopUp gamePopUp;
	PopOver popover;

	public BasicGameBuilder(BasicGameLogic model) {
		pane = new BorderPane();
		this.model = model;

	}

	protected SudokuField[][] textField;

	// schwierigkeit welche vom hauptmenü mit den gettern und settern unten
	// definiert wird
	protected int difficulty;

	protected GameController controller;

	protected GridPane playBoard;

	public void initializeGame() {
		controller = new GameController(this, model);

		gamePopUp = new NewGamePopUp(this, model);

		pane.setCenter(createBoard());
		pane.setPadding(new Insets(50, 50, 50, 50));

		createMenuBar(pane);
		createPlayButtons(pane);
		createStatusBar(pane);
	}

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
		toolbar = new ToolBar();
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
		Mnemonic mn = new Mnemonic(autosolve, autoS);
		GUI.getStage().getScene().addMnemonic(mn);

		done = new Button("done");

		done.setVisible(false);

		liveTimeLabel = new Label("");

		// benötigt für Abstand zwischen Buttons und Timer
		final Pane rightSpacer = new Pane();
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

		setButtonActions();

		toolbar.getItems().addAll(hintButton, autosolve, check, done, rightSpacer, liveTimeLabel);

		toolbox.getChildren().add(toolbar);

	}

	public void defineShortCuts() {

	}

	public void addListeners(SudokuField[][] sudokuField) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				ChangeListener<String> changeListener = new ChangeListener<>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						controller.compareResult(sudokuField);

					}
				};

				sudokuField[col][row].textProperty().addListener(changeListener);
				listeners.add(changeListener);

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
	protected MenuItem newGame;
	protected MenuItem save;
	protected MenuItem load;
	protected Menu file;

	protected MenuItem reset;
	protected Menu editMenu;

	protected Menu propertyMenu;
	protected RadioMenuItem conflictItem;
	protected MenuItem moreHintsItem;

	protected Menu sourceMenu;

	protected Menu mainMenu;
	protected MenuItem mainMenuItem;
	protected MenuItem createGameItem;

	protected Menu gameFunctions;
	protected MenuItem hintMenuItem;
	protected MenuItem autoSolveItem;
	protected MenuItem checkItem;

	protected SeparatorMenuItem seperator;
	protected MenuItem exitItem;

	protected MenuItem exportItem;
	protected MenuItem importItem;

	RulesStage rule;

	/**
	 * 
	 * Erstellt die Menüleiste mit den jeweiligen MenüItems
	 */
	public void createMenuBar(BorderPane pane) {
		// menuBar for the scene
		menuBar = new MenuBar();

		// Help eintrag mit rules menu
		helpMenu = new Menu("Help");
		rules = new MenuItem("Rules");
		rules.setOnAction(e -> {
			rule = new RulesStage();
			rule.showPopUp("Sudoku Rules");
		});
		helpMenu.getItems().add(rules);

		propertyMenu = new Menu("Properties");
		conflictItem = new RadioMenuItem("Show Conflicts");
		conflictItem.setSelected(false);
		moreHintsItem = new MenuItem("Add hints");
		propertyMenu.getItems().addAll(conflictItem, moreHintsItem);

		// savemenu mit save und load optionen
		file = new Menu("File");
		save = new MenuItem("Save");
		load = new MenuItem("Load");
		newGame = new MenuItem("New");
		exportItem = new MenuItem("Export");
		importItem = new MenuItem("Import");

		seperator = new SeparatorMenuItem();
		exitItem = new MenuItem("Exit");

		// SpielButton Funktionen für Menuü

		file.getItems().addAll(newGame, save, load, importItem, exportItem, seperator, exitItem);

		// menu Einträge für SpielFunktionen (Hint, Autosolve, check)

		popover = gamePopUp.createPopUp();

		// newgame menu eintrag
		editMenu = new Menu("Edit");

		createGameItem = new MenuItem("New Round");
		reset = new MenuItem("Reset");
		editMenu.getItems().addAll(createGameItem, reset, propertyMenu);

		mainMenu = new Menu("SudokuFX");

		mainMenuItem = new MenuItem("Go to Main Menu");
		mainMenu.getItems().addAll(mainMenuItem);

		sourceMenu = new Menu("Source");
		hintMenuItem = new MenuItem("Hint");
		autoSolveItem = new MenuItem("AutoSolve");
		checkItem = new MenuItem("Check");
		sourceMenu.getItems().addAll(hintMenuItem, autoSolveItem, checkItem);

		menuBar.getMenus().addAll(file, editMenu, sourceMenu, mainMenu, helpMenu);
		toolbox.getChildren().addAll(menuBar);
		menuBar.getStylesheets().add("menu-bar");
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
		hintButton.setOnAction(controller::hintHandeler);

		done.setOnAction(controller::manuelDoneHandler);
		checkItem.setOnAction(controller::checkHandler);
		hintMenuItem.setOnAction(controller::hintHandeler);
		autoSolveItem.setOnAction(controller::autoSolveHandler);

		// load.setOnAction(controller::importGame);
		save.setOnAction(controller::saveGame);
		reset.setOnAction(controller::resetHandler);
		mainMenuItem.setOnAction(controller::switchToMainMenu);

		mainMenuItem.setOnAction(e -> {
			controller.switchToMainMenu(e);

		});

		conflictItem.setOnAction(controller::switchOffConflicts);
		moreHintsItem.setOnAction(controller::handleMoreHints);
		// exitItem.setOnAction(e -> GUI.closeProgram());
		exportItem.setOnAction(controller::exportGame);
		importItem.setOnAction(controller::importGame);

		newGame.setOnAction(e -> {

			popover.show(hintButton, -30);

		});
	}

	public void createStatusBar(BorderPane pane) {
		gameNotificationLabel = new Label();

		gameInfoLabel = new Label();

		StatusBar statusBar = new StatusBar();
		statusBar.setText("");

		statusBar.getRightItems().add(gameInfoLabel);

		statusBar.getLeftItems().add(gameNotificationLabel);

		Stream.of(gameInfoLabel, gameNotificationLabel,liveTimeLabel).forEach(label -> label.getStyleClass().add("gameLabel"));

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

	public Button getCheckButton() {
		return this.check;
	}

	public Button getDoneButton() {
		return this.done;
	}

	// hat infos über punkte, schwierigkeit, und spielzeit
	public Label getGameInfoLabel() {
		return gameInfoLabel;
	}

	public Label getGameNotificationLabel() {
		return gameNotificationLabel;
	}

	public RadioMenuItem getConflictItem() {
		return conflictItem;
	}

	public BorderPane getPane() {
		return pane;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public AnimationTimer getTimer() {
		return timer;
	}

	public Label getLiveTimeLabel() {
		return liveTimeLabel;
	}
}