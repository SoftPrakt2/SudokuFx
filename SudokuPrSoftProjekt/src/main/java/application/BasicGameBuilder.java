package application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.GameController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;

/**
 * 
 * The abstract BasicGameBuilder class is the basic class for the Game UI. In
 * this class several UI Components like Buttons or the menu are created
 * 
 * @author grube
 *
 */
public abstract class BasicGameBuilder {

	/**
	 * The Main UI Container of a GameBuilder
	 */
	protected BorderPane gameRoot;
	protected BasicGameLogic model;

	/**
	 * 
	 * The toolbar is the container for {@link #autosolve} {@link #check}
	 * {@link #hintButton} and the {@link #liveTimeLabel} which displays the current
	 * gametime
	 */
	protected ToolBar toolBar;

	/**
	 * The VBox toolbox works as the container for {@link #toolBar} and
	 * {@link #menuBar} this VBox is needed, to position the {@link #toolBar} under
	 * the {@link #menuBar}
	 */
	protected VBox toolbox;

	/**
	 * Button components for the Game UI
	 */
	protected Button checkButton;
	protected Button autosolve;
	protected Button customNumbersDone;
	protected Button customColorsDone;
	protected Button hintButton;
	protected ComboBox<String> comboColorBox;

	/**
	 * Labels for the game {@link #gameNotificationLabel} displays text
	 * corresponding to the games state {@link #gameInfoLabel} displays information
	 * about the game like current Points or selected Difficulty
	 * {@link #liveTimeLabel} displays the current gametime
	 */
	protected Label gameNotificationLabel;
	protected Label gameInfoLabel;
	protected Label liveTimeLabel;
	protected StatusBar statusbar;

	/**
	 * {@link #fontAwesome} is used to style different button components of the Game
	 * UI
	 */
	protected FontAwesome fontAwesome;

	protected List<ChangeListener<String>> conflictListener;

	/**
	 * variables which define the size of the Game Window
	 */
	protected int sceneWidth;
	protected int sceneHeight;

	/**
	 * gamePopUp is an Object of the class {@link application.NewGamePopUp} popover
	 * will later be initialized with the
	 * {@link application.NewGamePopUp#createPopUp()} method
	 */
	protected NewGamePopUp gamePopUp;
	protected PopOver popover;

	protected SudokuField[][] textField;
	protected GameController controller;

	/**
	 * container for the playing field
	 */
	protected GridPane playBoard;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @param model Spiellogik welche dem GameBuilder übergeben wird
	 */
	protected BasicGameBuilder(BasicGameLogic model) {
		gameRoot = new BorderPane();
		this.model = model;
	}

	/**
	 * In this method the Game UI is initialized with all necessary UI components
	 * Furthermore the Game's controller is initialized with the GameBuilder and a
	 * corresponding GameLogic
	 */
	public void initializeGame() {
		controller = new GameController(this, model);
		gamePopUp = new NewGamePopUp();
		gameRoot.setPadding(new Insets(50, 50, 50, 50));
		createMenuBar();
		initializeToolBar();
		createManualControls();
		createStatusBar();
		setButtonActions();
		setMenuActions();
		gameRoot.setCenter(createBoard());
		conflictListener = new ArrayList<>();
	}

	/**
	 * Draws the playing field
	 * 
	 * @return the drawn playing field
	 */
	public abstract GridPane createBoard();

	/**
	 * Initializes the {@link #toolBar}, the Game Buttons and the
	 * {@link #liveTimeLabel} The rightSpacer is used to ensure appropiate distance
	 * between the buttons and the {@link #liveTimeLabel}
	 */
	public void initializeToolBar() {
		toolBar = new ToolBar();

		// Graphiken für Buttons
		fontAwesome = new FontAwesome();
		Glyph checkGraphic = fontAwesome.create(FontAwesome.Glyph.CHECK);
		Glyph hintGraphic = fontAwesome.create(FontAwesome.Glyph.SUPPORT);
		Glyph autosolveGraphic = fontAwesome.create(FontAwesome.Glyph.CALCULATOR);

		hintButton = new Button("");
		hintButton.setGraphic(hintGraphic);

		autosolve = new Button("");
		autosolve.setGraphic(autosolveGraphic);

		checkButton = new Button("");
		checkButton.setGraphic(checkGraphic);
		liveTimeLabel = new Label("");

		final Pane rightSpacer = new Pane();
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		toolBar.getItems().addAll(hintButton, autosolve, checkButton, rightSpacer, liveTimeLabel);
		toolbox.getChildren().add(toolBar);
	}

	/**
	 * Abstract method which is used to create the games specific manual game
	 * components
	 */
	public abstract void createManualControls();

	/**
	 * Adds listener to the SudokuFields, these listener are used to allow automatic
	 * conflict registration
	 */
	public void addConflictListeners() {
		ChangeListener<String> changeListener;
		for (int row = 0; row < textField.length; row++) {
			for (int col = 0; col < textField[row].length; col++) {
				changeListener = new ChangeListener<>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						controller.compareResult();

					}
				};

				textField[col][row].textProperty().addListener(changeListener);
				conflictListener.add(changeListener);

			}
		}
	}

	/**
	 * Removes the listeners from the SudokuFields added with the
	 * {@link #addConflictListeners()} method
	 */
	public void removeConflictListeners() {
		for (ChangeListener<String> l : conflictListener) {
			for (SudokuField[] sudokuFieldArray : textField) {
				for (SudokuField field : sudokuFieldArray) {

					field.textProperty().removeListener(l);
				}
			}
		}
	}

	protected MenuBar menuBar;

	// MenüObjekte für File Menü
	protected Menu fileMenu;
	protected MenuItem newGameMenuItem;
	protected MenuItem saveMenuItem;
	protected MenuItem importMenuItem;
	protected MenuItem exportMenuItem;
	protected SeparatorMenuItem seperatorItem;
	protected MenuItem exitMenuItem;

	// MenüObjekte für Edit Menü
	protected Menu editMenu;
	protected MenuItem resetMenuItem;
	protected MenuItem newRoundMenuItem;
	protected Menu propertyMenu;
	protected RadioMenuItem conflictMenuItem;

	// MenüObjekte für Edit Menü
	protected Menu sourceMenu;
	protected MenuItem hintMenuItem;
	protected MenuItem autoSolveMenuItem;
	protected MenuItem checkMenuItem;

	// MenüObjekte für SudokuFx Menü
	protected Menu sudokufxMenu;
	protected MenuItem mainMenuItem;

	// MenüObjekte für Help Menü
	protected Menu helpMenu;
	protected MenuItem rulesMenuItem;

	/**
	 * Initializes the Menu UI Components
	 */
	public void createMenuBar() {
		// menuBar for the scene
		menuBar = new MenuBar();
		toolbox = new VBox();

		// File Menü initialisierungen
		fileMenu = new Menu("File");
		saveMenuItem = new MenuItem("Save");

		newGameMenuItem = new MenuItem("New");
		exportMenuItem = new MenuItem("Export");
		importMenuItem = new MenuItem("Import");
		seperatorItem = new SeparatorMenuItem();
		exitMenuItem = new MenuItem("Exit");
		popover = gamePopUp.createPopUp();
		fileMenu.getItems().addAll(newGameMenuItem, saveMenuItem, importMenuItem, exportMenuItem, seperatorItem,
				exitMenuItem);

		// Edit Menü Initialisierungen
		editMenu = new Menu("Edit");
		newRoundMenuItem = new MenuItem("New Round");
		resetMenuItem = new MenuItem("Reset");
		propertyMenu = new Menu("Properties");
		conflictMenuItem = new RadioMenuItem("Show Conflicts");
		conflictMenuItem.setSelected(false);
		propertyMenu.getItems().addAll(conflictMenuItem);
		editMenu.getItems().addAll(newRoundMenuItem, resetMenuItem, propertyMenu);

		// Source Menü Initialisierungen
		sourceMenu = new Menu("Source");
		hintMenuItem = new MenuItem("Hint");
		autoSolveMenuItem = new MenuItem("AutoSolve");
		checkMenuItem = new MenuItem("Check");
		sourceMenu.getItems().addAll(hintMenuItem, autoSolveMenuItem, checkMenuItem);

		// SudokuFx Menü initialisierungen
		sudokufxMenu = new Menu("SudokuFX");
		mainMenuItem = new MenuItem("Go to Main Menu");
		sudokufxMenu.getItems().addAll(mainMenuItem);

		// Help Menü initialisierungen
		helpMenu = new Menu("Help");
		rulesMenuItem = new MenuItem("Rules");
		helpMenu.getItems().add(rulesMenuItem);

		menuBar.getMenus().addAll(fileMenu, editMenu, sourceMenu, sudokufxMenu, helpMenu);
		toolbox.getChildren().addAll(menuBar);

		gameRoot.setTop(toolbox);
		
		fileMenu.getStyleClass().add("menu-item");
		
		defineShortCuts();
	}

	/**
	 * Adds shortcut functionality to the UI Objects inside the Menu
	 */
	public void defineShortCuts() {

		KeyCombination hintKc = new KeyCodeCombination(KeyCode.H, KeyCombination.SHORTCUT_DOWN);
		KeyCombination autoSolveKc = new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN);
		KeyCombination checkKc = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
		KeyCombination resetKc = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
		KeyCombination autoConflictsKc = new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN);
		KeyCombination newRoundKc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		KeyCombination importKc = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
		KeyCombination exportKc = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
		KeyCombination saveKc = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		KeyCombination mainMenuKc = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);

		hintMenuItem.setAccelerator(hintKc);
		autoSolveMenuItem.setAccelerator(autoSolveKc);
		checkMenuItem.setAccelerator(checkKc);
		resetMenuItem.setAccelerator(resetKc);
		conflictMenuItem.setAccelerator(autoConflictsKc);
		newRoundMenuItem.setAccelerator(newRoundKc);
		importMenuItem.setAccelerator(importKc);
		exportMenuItem.setAccelerator(exportKc);
		saveMenuItem.setAccelerator(saveKc);
		mainMenuItem.setAccelerator(mainMenuKc);
	}

	/**
	 * 
	 * Defines which Action is fired when a specific button is pressed
	 */
	public void setButtonActions() {
		newRoundMenuItem.setOnAction(controller::newGameHandler);
		autosolve.setOnAction(controller::checkHandler);
		checkButton.setOnAction(controller::checkHandler);
		autosolve.setOnAction(controller::autoSolveHandler);
		hintButton.setOnAction(controller::hintHandeler);
		customNumbersDone.setOnAction(controller::manuelDoneHandler);
		if (this instanceof FreeFormGameBuilder) {
			customColorsDone.setOnAction(controller::customColorsDoneHandler);
		}
	}

	/**
	 * Defines which Action is fired when a specific menuitem is pressed
	 */
	public void setMenuActions() {
		checkMenuItem.setOnAction(controller::checkHandler);
		hintMenuItem.setOnAction(controller::hintHandeler);
		autoSolveMenuItem.setOnAction(controller::autoSolveHandler);
		saveMenuItem.setOnAction(controller::saveGame);
		resetMenuItem.setOnAction(controller::resetHandler);
		mainMenuItem.setOnAction(controller::switchToMainMenu);
		conflictMenuItem.setOnAction(controller::switchOffConflicts);
		exitMenuItem.setOnAction(e -> GUI.closeProgram());
		exportMenuItem.setOnAction(controller::exportGame);
		importMenuItem.setOnAction(controller::importGame);
		newGameMenuItem.setOnAction(e -> popover.show(hintButton, -30));
		rulesMenuItem.setOnAction(e -> {
			RulesStage rule = new RulesStage();
			rule.showRulePopUp();
		});
	}

	/**
	 * Creates a statusbar which is positioned on the bottom of the Game UI
	 */
	public void createStatusBar() {
		gameNotificationLabel = new Label();
		gameInfoLabel = new Label();

		StatusBar statusBar = new StatusBar();
		statusBar.setText("");

		statusBar.getRightItems().add(gameInfoLabel);

		statusBar.getLeftItems().add(gameNotificationLabel);

		Stream.of(gameInfoLabel, gameNotificationLabel, liveTimeLabel)
				.forEach(label -> label.getStyleClass().add("gameLabel"));

		gameRoot.setBottom(statusBar);
	}

	/**
	 * Calls the {@link application.GameController#createGame()} method
	 * this call is needed to ensure that numbers are shown in the textfield when selecting a game from the main menu
	 */
	public void createNumbers() {
		controller.createGame();
	}

	/**
	 *  auxiliary method which disables the game buttons and corrseponding menuitem functionality if needed 
	 */
	public void disablePlayButtons() {
		Stream.of(checkButton, autosolve, hintButton).forEach(button -> button.setDisable(true));
		Stream.of(autoSolveMenuItem,hintMenuItem,checkMenuItem).forEach(menuItem -> menuItem.setDisable(true));
			
	}

	public GameController getController() {
		return controller;
	}

	public BorderPane getGameUIRoot() {
		return gameRoot;
	}

	/**
	 * 
	 * Getter und Setter für die Variablen dieser Klasse
	 */
	public SudokuField[][] getTextField() {
		return this.textField;
	}

	public Button getCheckButton() {
		return this.checkButton;
	}

	public Button getAutoSolveButton() {
		return this.autosolve;
	}

	public Button getHintButton() {
		return this.hintButton;
	}

	public Button getColorsDoneButton() {
		return this.customColorsDone;
	}

	public Button getDoneButton() {
		return this.customNumbersDone;
	}

	// hat infos über punkte, schwierigkeit, und spielzeit
	public Label getGameInfoLabel() {
		return gameInfoLabel;
	}

	public Label getGameNotificationLabel() {
		return gameNotificationLabel;
	}

	public RadioMenuItem getConflictItem() {
		return conflictMenuItem;
	}

	public int getWidth() {
		return sceneWidth;
	}

	public int getHeight() {
		return sceneHeight;
	}

	public Label getLiveTimeLabel() {
		return liveTimeLabel;
	}

	public ComboBox<String> getColorBox() {
		return comboColorBox;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}
	
	public MenuItem getHintMenuItem() {
		return hintMenuItem;
	}
	public MenuItem getAutoSolveMenuItem() {
		return autoSolveMenuItem;
	}
	public MenuItem getCheckMenuItem() {
		return checkMenuItem;
	}

}