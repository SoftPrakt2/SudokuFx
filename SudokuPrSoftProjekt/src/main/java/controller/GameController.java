package controller;

import java.util.stream.Stream;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.SudokuTextField;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SaveModel;
import logic.SudokuStorage;

/**
 * GameController ist das Bindeglied zwischen der View und dem Model Die
 * Eingaben von der View werden an das Model weitergeleitet Die Eingaben vom
 * Model werden an die View weitergeleitet
 */
public class GameController {

	private BasicGameBuilder scene;
	private BasicGameLogic model;
	private SudokuTextField[][] sudokuField;

	/**
	 * Constructor to create a GameController-Object
	 * 
	 * @param scene : the Game UI currently visible to the user
	 * @param model : is the logic for creating a game and handling game mechanics
	 */
	public GameController(BasicGameBuilder scene, BasicGameLogic model) {
		this.scene = scene;
		this.model = model;
		sudokuField = scene.getTextField();
	}

	/**
	 * Creates, depending on the difficulty that the user chose, a sudoku game uses
	 * the method {@link #createGame()}
	 */
	public void createGameHandler(ActionEvent event) {
		createGame();
	}

	/**
	 * Creates a Sudoku-Game type and difficulty of the game are decides by user
	 * inputs (button-clicks) {@link #enableEdit()} enables the text fields, so that
	 * the user can play
	 */
	public void createGame() {
		scene.removeConflictListeners();
		model.setUpGameField();
		model.setUpGameInformations();
		scene.getGameInfoLabel()
				.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
		scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {

				if (model instanceof FreeFormLogic) {
					sudokuField[i][j].setStyle("-fx-background-color: #" + model.getCells()[j][i].getBoxcolor() + ";");
				}

				String number = Integer.toString(model.getCells()[j][i].getValue());
				if ((!sudokuField[i][j].getText().equals("") || !sudokuField[i][j].getText().equals("-1"))
						&& !number.equals("0")) {
					sudokuField[i][j].setText(number);
				}
			}
		}
		enableEdit();
		if (scene.getConflictItem().isSelected()) {
			scene.addConflictListeners();
		}
	}

	/**
	 * deletes all number from the text field restarts Timer and resets gamestate to
	 * OPEN
	 */
	public void newGameHandler(ActionEvent event) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (sudokuField[i][j].getText().equals("-1") == false) {
					sudokuField[i][j].clear();
				}
				sudokuField[i][j].getStyleClass().remove("textfieldHint");
			}
		}

		model.setGamePoints(10);
		model.setNumbersInsideTextField(0);
		model.setMinutesPlayed(0);
		model.setSecondsPlayed(0);
		if (model.timerIsRunning()) {
			model.getLiveTimer().stop();
		}
		scene.getLiveTimeLabel().textProperty().unbind();

		if (model.getDifficulty() == 0 && model.getGametype().equals("FreeForm")) {
			newManualFreeFormGame();
		}

		if (model.getDifficulty() == 0 && !model.getGametype().equals("FreeForm")) {
			resetManualSudokuOrSamurai();

		} else if (model.getDifficulty() > 0) {
			createGame();
		}
		scene.getGameNotificationLabel().setText(model.getGameText());
	}

	/**
	 * This method is used to start a new manual freeform game while inside this
	 * type of game configuration to do so the the colorlisteners defined in the
	 * {@link application.SudokuTextField} class will be added again to the
	 * textfields Depending on the gamestate when starting a new round different
	 * buttons have to be disabled or enabled again
	 */
	public void newManualFreeFormGame() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].setStyle("");
				sudokuField[i][j].setColor("");
				if (!sudokuField[i][j].isListeningToColors()) {
					sudokuField[i][j].addFreeFormColorListener(scene.getColorBox());
				}
			}
		}

		if (model.getGamestate().equals(Gamestate.DRAWING)) {
			scene.getColorsDoneButton().setVisible(true);
			scene.getColorBox().setVisible(true);
		} else if (model.getGamestate().equals(Gamestate.CREATING)) {
			scene.getToolBar().getItems().remove(scene.getCustomNumbersDone());
			scene.getToolBar().getItems().remove(scene.getColorsDoneButton());
			scene.getToolBar().getItems().remove(scene.getColorBox());
			scene.getToolBar().getItems().add(3, scene.getColorsDoneButton());
			scene.getToolBar().getItems().add(3, scene.getColorBox());
			scene.getColorBox().setVisible(true);
			scene.getColorsDoneButton().setVisible(true);
		} else {
			scene.getToolBar().getItems().remove(scene.getCustomNumbersDone());
			scene.getToolBar().getItems().remove(scene.getColorsDoneButton());
			scene.getToolBar().getItems().remove(scene.getColorBox());
			scene.getToolBar().getItems().add(3, scene.getColorsDoneButton());
			scene.getToolBar().getItems().add(3, scene.getColorBox());
			scene.getColorBox().setVisible(true);
			scene.getColorsDoneButton().setVisible(true);
		}
		model.initializeCustomGame();
		// model.setGamePoints(10);
		scene.disablePlayButtons();
		scene.getLiveTimeLabel().setText("");
		scene.getGameInfoLabel().setText("");
		enableEdit();
	}

	/**
	 * This method is used to start a new manual sudoku or samurai game while inside
	 * this type of game configuration to do so the
	 * {@link application.BasicGameBuilder #getCustomNumbersDone()} will be enabled
	 * and visible again and the play buttons will be disabled
	 */
	public void resetManualSudokuOrSamurai() {

		scene.getToolBar().getItems().remove(scene.getCustomNumbersDone());
		scene.getToolBar().getItems().add(3, scene.getCustomNumbersDone());
		scene.getCustomNumbersDone().setVisible(true);
		scene.disablePlayButtons();

		if (!model.getGamestate().equals(Gamestate.CREATING)) {
			model.setGameState(Gamestate.CREATING);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
		// model.setGamePoints(10);
		scene.getGameInfoLabel().setText("");
		model.initializeCustomGame();
		enableEdit();
		scene.getLiveTimeLabel().setText("");
	}

	/**
	 * deletes user input values that were created at the start of the game do not
	 * get deleted
	 */
	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!model.getCells()[j][i].getFixedNumber() && !sudokuField[i][j].getText().equals("-1")) {
					sudokuField[i][j].getStyleClass().remove("textfieldHint");
					sudokuField[i][j].clear();
					model.getCells()[j][i].setValue(0);
				}
			}
		}
		model.setGameState(Gamestate.OPEN);
		scene.getGameNotificationLabel().setText(model.getGameText());
	}

	/**
	 * Method which is needed to set the form of the Sudokutextfields the user has
	 * created during a manual freeform game to the model if the forms are created
	 * correctly the
	 * {@link application.SudokuTextField#addFreeFormColorListener(javafx.scene.control.ComboBox)}
	 * are removed from the Sudokutextfields Afterwards the UI Control Components
	 * are updated
	 * 
	 * @param e
	 */
	public void customColorsDoneHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {

				model.getCells()[j][i].setBoxcolor(sudokuField[i][j].getColor());

			}
		}

		if (model.isConnected() && model.proofFilledOut()) {
			for (SudokuTextField coloredArray[] : sudokuField) {
				for (SudokuTextField coloredField : coloredArray) {
					coloredField.removeFreeFormColorListener();
				}
			}
			scene.getToolBar().getItems().remove(scene.getColorsDoneButton());
			scene.getToolBar().getItems().remove(scene.getColorBox());
			scene.getToolBar().getItems().add(3, scene.getCustomNumbersDone());
			scene.getCustomNumbersDone().setVisible(true);
			model.setGameState(Gamestate.CREATING);
			scene.getGameNotificationLabel().setText(model.getGameText());
		} else {
			model.setGameState(Gamestate.NOFORMS);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}

	}

	/**
	 * fixes the numbers that the user wants to create a sudoku with checks for
	 * conflicts and asks the user to remove them if any occur checks if the
	 * manually created sudoku is solvable - if it is not solvable the user needs to
	 * create a new sudoku {@link #compareResult()} test for conflicts
	 * {@link ##enoughManualNumbers()} test if there are enough numbers to create a
	 * sudoku
	 * 
	 */
	public void manuelDoneHandler(ActionEvent e) {
		if (compareResult() && enoughManualNumbers()) {

			this.connectWithModel();
			model.solveSudoku();
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					model.getSavedResults()[i][j] = model.getCells()[i][j].getValue();
				}
			}
			if (!model.testIfSolved()) {
				model.setGameState(Gamestate.MANUALCONFLICT);
				scene.getGameNotificationLabel().setText(model.getGameText());
			} else {

				for (int i = 0; i < sudokuField.length; i++) {
					for (int j = 0; j < sudokuField[i].length; j++) {
						if (model instanceof FreeFormLogic)
							sudokuField[i][j].removeFreeFormColorListener();
						if (!sudokuField[i][j].getText().equals("")) {
							model.getCells()[j][i].setValue(Integer.parseInt(sudokuField[i][j].getText()));
							model.getCells()[j][i].setFixedNumber(true);
							sudokuField[i][j].setDisable(true);
						}
					}
				}
				model.setUpGameInformations();
				scene.getGameNotificationLabel().setText(model.getGameText());
				scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));

				scene.getToolBar().getItems().remove(scene.getCustomNumbersDone());
				Stream.of(scene.getHintButton(), scene.getAutoSolveButton(), scene.getCheckButton())
						.forEach(button -> button.setDisable(false));
				Stream.of(scene.getAutoSolveMenuItem(), scene.getHintMenuItem(), scene.getCheckMenuItem())
						.forEach(menuItem -> menuItem.setDisable(false));

				scene.getGameInfoLabel()
						.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			}
		} else if (!compareResult()) {
			model.removeValues();
			model.setGameState(Gamestate.MANUALCONFLICT);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * test if there are enough numbers inserted by the user to create a sudoku
	 * 
	 * @return returns false if there are not enough numbers and returns true
	 *         otherwise
	 */
	public boolean enoughManualNumbers() {
		int count = 0;
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!sudokuField[i][j].getText().equals("") && !sudokuField[i][j].getText().equals("-1")) {
					count++;
				}
			}
		}
		model.setManualNumbersInserted(count);
		if (count < model.getNumbersToBeSolvable()) {
			model.setGameState(Gamestate.NOTENOUGHNUMBERS);
			scene.getGameNotificationLabel().setText(model.getGameText());
			return false;
		} else {
			return true;
		}
	}

	/**
	 * checks for conflicts and colors them red if there are any returns true if
	 * there are no conflicts returns false if there are conflicts
	 * {@link #connectWithModel()} connects model-array with text field-array
	 */
	public boolean compareResult() {
		boolean result = true;

		if (model instanceof SamuraiLogic)
			model.setNumbersInsideTextField(72);
		else
			model.setNumbersInsideTextField(0);

		connectWithModel();
		System.out.println(model.getNumbersInsideTextField());
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!model.getCells()[row][col].getFixedNumber()) {
					model.getCells()[row][col].setValue(0);
				}
				sudokuField[col][row].getStyleClass().remove("textfieldWrong");

				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.setNumbersInsideTextField(model.getNumbersInsideTextField() + 1);

					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));

					if (!model.getCells()[row][col].getFixedNumber()) {

						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
							model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));

							sudokuField[col][row].getStyleClass().add("textfieldWrong");

							model.setGameState(Gamestate.INCORRECT);

							result = false;
						} else {
							sudokuField[col][row].getStyleClass().remove("textfieldWrong");
							sudokuField[col][row].getStyleClass().add("textfieldBasic");
						}
					}
				}
			}
		}
		if (result && model.getGamestate() != Gamestate.AUTOSOLVED && model.getGamestate() != Gamestate.UNSOLVABLE
				&& model.getGamestate() != Gamestate.MANUALCONFLICT
				&& model.getGamestate() != Gamestate.NOTENOUGHNUMBERS) {
			model.setGameState(Gamestate.OPEN);
		}

		return result;
	}

	/**
	 * connects text field-array with model-array Fills the text field-array with
	 * the values of the model-array
	 */
	public void connectArrays() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (model instanceof FreeFormLogic && !model.getCells()[j][i].getBoxcolor().equals("")) {
					sudokuField[i][j].setColor(model.getCells()[j][i].getBoxcolor());

				}
				if (model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}
				if (model.getCells()[j][i].getFixedNumber()) {
					sudokuField[i][j].setDisable(true);
				}
			}
		}

		// only calculate the solution for a game if it is applyingly created to the
		// rules
		if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)
				&& !model.getGamestate().equals(Gamestate.NOFORMS)
				&& !model.getGamestate().equals(Gamestate.MANUALCONFLICT)
				&& !model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS))
			model.setSavedResults(model.alignWithHelper());

	}

	/**
	 * solves the sudoku if there are no conflicts Asks the user to remove the
	 * conflicts if there are any uses the following methods
	 * {@link #connectArrays(), #compareResult()}
	 */
	public void autoSolveHandler(ActionEvent e) {
		scene.removeConflictListeners();
		if (compareResult()) {
			connectWithModel();
			model.solveSudoku();
			// if (model.getGamestate() != Gamestate.UNSOLVABLE) {

			// }
			model.setGamePoints(0);
			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " | Difficulty: " + model.getDifficultystring());
			model.getLiveTimer().stop();

			if (!model.testIfSolved()) {
				resetHandler(e);
				model.setGameState(Gamestate.UNSOLVABLE);
				model.solveSudoku();

			}
			connectArrays();
			model.setGameState(Gamestate.AUTOSOLVED);
			scene.getGameNotificationLabel().setText(model.getGameText());
		} else {
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					}
				}
			}
			model.setGameState(Gamestate.CONFLICT);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * checks if the sudoku is solved (without conflicts) {@link #compareResult()}
	 * checks for conflicts
	 */
	public void checkHandler(ActionEvent e) {
		boolean gameState = compareResult();
		if (gameState) {
			// wsl unnötig
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					sudokuField[j][i].getStyleClass().add("textfieldBasic");
				}
			}
		}
		if (gameState && (model.getNumbersInsideTextField() == sudokuField.length * sudokuField.length)) {

			if (!model.getGamestate().equals(Gamestate.AUTOSOLVED)) {
				model.setGameState(Gamestate.DONE);
				scene.getGameNotificationLabel().setText(model.getGameText());
				model.getLiveTimer().stop();
			}
		} else if (!gameState || model.getNumbersInsideTextField() != sudokuField.length * sudokuField.length) {

			model.setGameState(Gamestate.INCORRECT);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * empty text fields get enabled so that the user can input his numbers into
	 * them
	 */
	public void enableEdit() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (model.getCells()[j][i].getValue() == 0) {
					sudokuField[i][j].setDisable(false);
				} else {
					sudokuField[i][j].setDisable(true);
				}
			}
		}
	}

	/**
	 * connect the text field-array with the model-array
	 */
	public void connectWithModel() {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				} else if (!sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(0);
				}
//				if(sudokuField[col][row].getText().equals("")) {
//					model.getCells()[row][col].setValue(0);
//				}
			}
		}
	}

	/**
	 * gives the user a hint {@link #connectWithModel()}
	 * 
	 * @param e
	 */

	public void hintHandeler(ActionEvent e) {
		if (compareResult()) {
			if (model.getGamepoints() > 0 && (!model.getGamestate().equals(Gamestate.UNSOLVABLE)
					&& !model.getGamestate().equals(Gamestate.CONFLICT))) {
				model.setGamePoints(model.getGamepoints() - 1);
			}
			connectWithModel();
			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			int[] coordinates = model.hint();
			// checks if coordinates are null
			if (coordinates != null) {
				// checks if there is already a userinput in this textfield with these
				// coordinates
				// this check exists so taht userinputs do not get overwritten
				if (sudokuField[coordinates[1]][coordinates[0]].getText().equals("")) {
					String number = Integer.toString(model.getCells()[coordinates[0]][coordinates[1]].getValue());
					sudokuField[coordinates[1]][coordinates[0]].setText(number);
					sudokuField[coordinates[1]][coordinates[0]].getStyleClass().add("textfieldHint");
				}
			} else {
				for (int row = 0; row < sudokuField.length; row++) {
					for (int col = 0; col < sudokuField[row].length; col++) {
						if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")
								&& model.getSavedResults()[row][col] != Integer
										.parseInt(sudokuField[col][row].getText())) {
							sudokuField[col][row].getStyleClass().add("textfieldWrong");
						}
					}
				}
				if (!model.testIfSolved()) {
					model.setGameState(Gamestate.UNSOLVABLE);
					scene.getGameNotificationLabel().setText(model.getGameText());
				}
			}
		} else {
			if (model.getGamepoints() > 0)
				model.setGamePoints(model.getGamepoints() - 1);
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					}
				}
			}
			model.setGameState(Gamestate.CONFLICT);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * adds or removes Listener to the text field the listeners call the
	 * {@link #compareResult()} method and show conflicts
	 * 
	 * @param e
	 */
	public void switchOffConflicts(ActionEvent e) {
		if (scene.getConflictItem().isSelected()) {
			scene.addConflictListeners();
		} else {
			scene.removeConflictListeners();
		}
	}

	/**
	 * saves the game stops the timer
	 * 
	 * @param e
	 */
	public void saveGame(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				}
				if (sudokuField[col][row].getText().equals("")) {
					model.getCells()[row][col].setValue(0);
				}

				if (!sudokuField[col][row].getColor().equals("")) {
					model.getCells()[row][col].setBoxcolor(sudokuField[col][row].getColor());
				}
			}
		}
		if (model.timerIsRunning()) {
			model.getLiveTimer().stop();
		}
		SudokuStorage storageModel = new SudokuStorage();
		storageModel.saveGame(model);
	}

	/**
	 * Imports a game This import must be a JSON file a new scene is created
	 * depending on the import Depending on the saved games gamestate different UI
	 * objects have to be enabled or disabled
	 * 
	 * @param e
	 */
	public void importGame(ActionEvent e) {
		SudokuStorage storageModel = new SudokuStorage();

		SaveModel saveModel = storageModel.getImportedFile();

		if (storageModel.fileExists()) {
			model = storageModel.loadIntoModel(model, saveModel);

			if (model.getGametype().equals("Sudoku")) {
				scene = new SudokuGameBuilder(model);
			}

			else if (model.getGametype().equals("Samurai")) {
				scene = new SamuraiGameBuilder(model);
			} else if (model.getGametype().equals("FreeForm")) {
				scene = new FreeFormGameBuilder(model);
			}

			scene.initializeGame();

			if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)
					&& !model.getGamestate().equals(Gamestate.MANUALCONFLICT)
					&& !model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)
					&& !model.getGamestate().equals(Gamestate.NOFORMS)) {

				model.initializeTimer();
				model.getLiveTimer().start();
				scene.getGameInfoLabel()
						.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
				scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));
			}

			if (model.getGamestate().equals(Gamestate.CREATING) || model.getGamestate().equals(Gamestate.MANUALCONFLICT)
					|| model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)) {
				scene.getToolBar().getItems().remove(scene.getCustomNumbersDone());
				scene.getToolBar().getItems().add(3, scene.getCustomNumbersDone());
				scene.getCustomNumbersDone().setVisible(true);
				scene.disablePlayButtons();

			}

			if (model.getGamestate().equals(Gamestate.DRAWING) || model.getGamestate().equals(Gamestate.NOFORMS)) {
				scene.getColorBox().setVisible(true);
				scene.getColorsDoneButton().setVisible(true);
				scene.disablePlayButtons();
			}

			scene.getGameNotificationLabel().setText(model.getGameText());

			sudokuField = scene.getTextField();
			emptyArrays();

			connectArrays();

			GUI.getStage().setHeight(scene.getHeight());
			GUI.getStage().setWidth(scene.getWidth());
			GUI.getStage().getScene().setRoot(scene.getGameUIRoot());
		}
	}

	/**
	 * exports the game as a JSON-File user can choose the storage path
	 * 
	 * @param e
	 */
	public void exportGame(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				}
				if (model instanceof FreeFormLogic && !sudokuField[col][row].getColor().equals("")) {
					model.getCells()[row][col].setBoxcolor(sudokuField[col][row].getColor());
				}
				if (sudokuField[col][row].getText().equals("")) {
					model.getCells()[row][col].setValue(0);
				}
			}
		}
		SudokuStorage storageModel = new SudokuStorage();
		storageModel.exportGame(model);
	}

	/**
	 * switches the scene to the menu-scene
	 * 
	 * @param e
	 */
	public void switchToMainMenu(ActionEvent e) {
		emptyArrays();

		MainMenu mainmenu = new MainMenu();
		mainmenu.setUpMainMenu();

		GUI.getStage().setHeight(670);
		GUI.getStage().setWidth(670);
		GUI.getStage().getScene().setRoot(GUI.getMainMenu());

	}

	/**
	 * deletes all inputs from the text fields-array enables all text fields
	 */
	public void emptyArrays() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].setText("");
				sudokuField[i][j].setDisable(false);
			}
		}
	}

	/**
	 * getter and setter
	 */
	public BasicGameLogic getModel() {
		return this.model;
	}

	public SudokuTextField[][] getsudokuField() {
		return this.sudokuField;
	}
}