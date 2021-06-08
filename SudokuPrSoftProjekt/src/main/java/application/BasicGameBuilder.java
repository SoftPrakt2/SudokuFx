package application;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.GameController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;


/**
 * Diese abstrakte Klasse stellt die Grundlage f�r eine Spiel UI dar. 
 * In dieser Klasse werden verschiedenste UI Komponenten wie Buttons oder das Men� erstellt. 
 * @author grube
 *
 */
public abstract class BasicGameBuilder {

	protected BorderPane pane;
	protected BasicGameLogic model;

	//beh�lter und buttons f�r Spielbuttons
	protected ToolBar toolBar;
	protected VBox toolbox;
	protected VBox playButtonMenu;
	protected Button check;
	protected Button owngame;
	protected Button autosolve;
	protected Button customNumbersDone;
	protected Button customColorsDone;
	protected Button hintButton;
	protected ComboBox<String> comboColorBox;
	
	protected HBox gameLabelBox;
	protected Label gameNotificationLabel;
	protected Label gameInfoLabel;
	protected Label liveTimeLabel;
	protected StatusBar statusbar;
	

	protected FontAwesome fontAwesome;
	
	protected ArrayList<ChangeListener> conflictListener;
	


	// Variablen welche f�r die Festlegung der Fenstergr��e ben�tigt werden
	protected int sceneWidth;
	protected int sceneHeight;

	protected NewGamePopUp gamePopUp;
	protected PopOver popover;
	
	
	
	/**
	 * Konstruktor der Klasse 
	 * @param model Spiellogik welche dem GameBuilder �bergeben wird
	 */
	protected BasicGameBuilder(BasicGameLogic model) {
		pane = new BorderPane();
		this.model = model;
		}
	
	
	
	
	protected SudokuField[][] textField;

	// schwierigkeit welche vom hauptmen� mit den gettern und settern unten
	// definiert wird
	protected int difficulty;

	protected GameController controller;

	protected GridPane playBoard;

	public void initializeGame() {
		
		controller = new GameController(this, model);
		gamePopUp = new NewGamePopUp();
		pane.setPadding(new Insets(50, 50, 50, 50));
		createMenuBar();
		initializeToolBar();
		createManualControls();
		createStatusBar();
		setButtonActions();
		pane.setCenter(createBoard());
		conflictListener = new ArrayList<>();
	}
	
	
	/**
	 * Zeichnet ein Spielfeld f�r das jeweilige Spiel
	 * @return das gezeichnete Spielfeld
	 */
	public abstract GridPane createBoard();


	
	
	/**
	 * Inizialisiert eine ToolBar welche als Beh�lter f�r die ebenfalls initialisierten Buttons und ein Zeitlabel dient
	 * Die initialisierten Buttons werden mithilfe von Glyph Grafiken grafisch gestaltet
	 * Um ben�tigte Abst�nde zwischen den Buttons und dem Zeiteintrag zu gew�hrleisten werden weiters leere Panes eingef�gt
	 */
	public void initializeToolBar() {
		toolBar = new ToolBar();

		// Graphiken f�r Buttons
		fontAwesome = new FontAwesome();
		Glyph checkGraphic = fontAwesome.create(FontAwesome.Glyph.CHECK);
		Glyph hintGraphic = fontAwesome.create(FontAwesome.Glyph.SUPPORT);
		Glyph autosolveGraphic = fontAwesome.create(FontAwesome.Glyph.CALCULATOR);
		
		
		hintButton = new Button("");
		hintButton.setGraphic(hintGraphic);

		autosolve = new Button("");
		autosolve.setGraphic(autosolveGraphic);

		check = new Button("");
		check.setGraphic(checkGraphic);
		liveTimeLabel = new Label("");

		
		final Pane rightSpacer = new Pane();
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		final Pane rightSpacer2 = new Pane();
		HBox.setHgrow(rightSpacer2, Priority.SOMETIMES);
		toolBar.getItems().addAll(hintButton, autosolve, check, rightSpacer, liveTimeLabel);
		toolbox.getChildren().add(toolBar);
	}
	
	
	public abstract void createManualControls();
	
	
	
	public void addConflictListeners() {
		for (int row = 0; row < textField.length; row++) {
			for (int col = 0; col < textField[row].length; col++) {
				ChangeListener<String> changeListener = new ChangeListener<>() {
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
	
	
	public void removeConflictListeners() {
		for (ChangeListener<String> l : conflictListener) {
			for (SudokuField[] sudokuFieldArray : textField) {
				for (SudokuField field : sudokuFieldArray) {

					field.textProperty().removeListener(l);
				}
			}
		}
	}
	
	//Hauptmen� Leiste f�r die Spielscenes
	protected MenuBar menuBar;

	// Men�Objekte f�r File Men�
	protected Menu file;
	protected MenuItem newGame;
	protected MenuItem save;
	protected MenuItem load;
	protected MenuItem importItem;
	protected MenuItem exportItem;
	protected SeparatorMenuItem seperator;
	protected MenuItem exitItem;

	// Men�Objekte f�r Edit Men�
	protected Menu editMenu;
	protected MenuItem reset;
	protected MenuItem newRoundItem;
	protected Menu propertyMenu;
	protected RadioMenuItem conflictItem;

	

	// Men�Objekte f�r Edit Men�
	protected Menu sourceMenu;
	protected MenuItem hintMenuItem;
	protected MenuItem autoSolveItem;
	protected MenuItem checkItem;

	// Men�Objekte f�r SudokuFx Men�
	protected Menu mainMenu;
	protected MenuItem mainMenuItem;

	//Men�Objekte f�r Help Men�
	protected Menu helpMenu;
	protected MenuItem rules;
	protected RulesStage rule;

	/**
	 * 
	 * Erstellt die Men�leiste mit den jeweiligen Men�Items
	 */
	public void createMenuBar() {
		// menuBar for the scene
		menuBar = new MenuBar();
		toolbox = new VBox();

		//File Men� initialisierungen
		file = new Menu("File");
		save = new MenuItem("Save");
		load = new MenuItem("Load");
		newGame = new MenuItem("New");
		exportItem = new MenuItem("Export");
		importItem = new MenuItem("Import");
		seperator = new SeparatorMenuItem();
		exitItem = new MenuItem("Exit");
		popover = gamePopUp.createPopUp();
		file.getItems().addAll(newGame, save, importItem, exportItem, seperator, exitItem);
		
		//Edit Men� initialisierungen
		editMenu = new Menu("Edit");
		newRoundItem = new MenuItem("New Round");
		reset = new MenuItem("Reset");
		propertyMenu = new Menu("Properties");
		editMenu.getItems().addAll(newRoundItem, reset, propertyMenu);
		
		conflictItem = new RadioMenuItem("Show Conflicts");
		conflictItem.setSelected(false);
		propertyMenu.getItems().addAll(conflictItem);
		
		//Source Men� initialisierungen
		sourceMenu = new Menu("Source");
		hintMenuItem = new MenuItem("Hint");
		autoSolveItem = new MenuItem("AutoSolve");
		checkItem = new MenuItem("Check");
		sourceMenu.getItems().addAll(hintMenuItem, autoSolveItem, checkItem);
		
		//SudokuFx Men� initialisierungen
		mainMenu = new Menu("SudokuFX");
		mainMenuItem = new MenuItem("Go to Main Menu");
		mainMenu.getItems().addAll(mainMenuItem);
		
		

		// Help eintrag mit rules menu
		helpMenu = new Menu("Help");
		rules = new MenuItem("Rules");
		rules.setOnAction(e -> {
			rule = new RulesStage();
			rule.showPopUp("Sudoku Rules");
		});
		helpMenu.getItems().add(rules);

		
		menuBar.getMenus().addAll(file, editMenu, sourceMenu, mainMenu, helpMenu);
		toolbox.getChildren().addAll(menuBar);
	
		pane.setTop(toolbox);
		
		
		defineShortCuts();
	}
	
	
	
	/*
	 * definiert Shortcuts f�r Spielfunktionen
	 */
	public void defineShortCuts() {

		KeyCombination hintKc = new KeyCodeCombination(KeyCode.H, KeyCombination.SHORTCUT_DOWN);
		KeyCombination autoSolveKc = new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN);
		KeyCombination checkKc = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
		KeyCombination resetKc = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
		KeyCombination autoConflictsKc = new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN);
		KeyCombination newRoundKc =  new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		KeyCombination importKc = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
		KeyCombination exportKc = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
		KeyCombination saveKc = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		KeyCombination mainMenuKc = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
		
		hintMenuItem.setAccelerator(hintKc);
		autoSolveItem.setAccelerator(autoSolveKc);
		checkItem.setAccelerator(checkKc);
		reset.setAccelerator(resetKc);
		conflictItem.setAccelerator(autoConflictsKc);
		newRoundItem.setAccelerator(newRoundKc);
		importItem.setAccelerator(importKc);
		exportItem.setAccelerator(exportKc);
		save.setAccelerator(saveKc);
		mainMenuItem.setAccelerator(mainMenuKc);
    }
	
	
	

	

	/**
	 * Weist den Buttons und Men�Items Actions zu
	 */
	public void setButtonActions() {
		newRoundItem.setOnAction(controller::newGameHandler);
		autosolve.setOnAction(controller::checkHandler);

		check.setOnAction(controller::checkHandler);
		autosolve.setOnAction(controller::autoSolveHandler);
		hintButton.setOnAction(controller::hintHandeler);

		customNumbersDone.setOnAction(controller::manuelDoneHandler);
		
		if(this instanceof FreeFormGameBuilder) {
			customColorsDone.setOnAction(controller::customColorsDoneHandler);
		}
		
		checkItem.setOnAction(controller::checkHandler);
		hintMenuItem.setOnAction(controller::hintHandeler);
		autoSolveItem.setOnAction(controller::autoSolveHandler);

		
		save.setOnAction(controller::saveGame);
		reset.setOnAction(controller::resetHandler);
		mainMenuItem.setOnAction(controller::switchToMainMenu);

		mainMenuItem.setOnAction(e -> controller.switchToMainMenu(e));

		conflictItem.setOnAction(controller::switchOffConflicts);
		exitItem.setOnAction(e -> GUI.closeProgram());
		exportItem.setOnAction(controller::exportGame);
		importItem.setOnAction(controller::importGame);

		newGame.setOnAction(e -> popover.show(hintButton, -30));
		
		
	}

	public void createStatusBar() {
		gameNotificationLabel = new Label();

		gameInfoLabel = new Label();

		StatusBar statusBar = new StatusBar();
		statusBar.setText("");

		statusBar.getRightItems().add(gameInfoLabel);

		statusBar.getLeftItems().add(gameNotificationLabel);

		Stream.of(gameInfoLabel, gameNotificationLabel, liveTimeLabel)
				.forEach(label -> label.getStyleClass().add("gameLabel"));

		pane.setBottom(statusBar);
	}

	
	public void createNumbers() {
		controller.createGame();
	}
	
	public void disablePlayButtons() {
		Stream.of(check,autosolve,hintButton).forEach(button -> button.setDisable(true));
	}
	
	
	public GameController getController() {
		return controller;
	}
	
	
	/**
	 * 
	 * Getter und Setter f�r die Variablen dieser Klasse
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

	// hat infos �ber punkte, schwierigkeit, und spielzeit
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
	
	
}