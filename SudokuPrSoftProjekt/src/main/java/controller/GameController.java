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
 * The GameController class is a connection between the
 * View-Components (GUI) and the Model (sudoku game logic)
 * 
 * User Inputs in the View-Component get passed on to the Model.
 * The same is also true the other way round. The generated sudoku game and
 * other variables or inputs also get passed on to the View-Component
 */
public class GameController {

	/**
	 * This is the View-Component (GUI) that the user gets to see
	 */
	private BasicGameBuilder scene;
	/**
	 * This is the Model-Component that handles all the game related logic
	 */
	private BasicGameLogic model;
	/**
	 * This text field has all the auto generated values
	 * the user can also input his own values in these text fields
	 */
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
	 * Creates, depending on the difficulty that the user chose, a sudoku game
	 * uses the method {@link #createGame()}
	 */
	public void createGameHandler(ActionEvent event) {
		createGame();
	}

	/**
	 * Creates a Sudoku-Game. 
	 * Type and difficulty of the game get decided by the user inputs (button-clicks).
	 * {@link #enableEdit()} enables the text fields, so that the user can play.
	 * Timer gets started.
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
	 * Deletes all numbers from the text field and resets all the
	 * necessary variable for a new game to their default state/values.
	 * Restarts Timer and resets game state to OPEN.
	 * 
	 * Removes all unnecessary styles from the text field.
	 */
	public void newGameHandler(ActionEvent event) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!sudokuField[i][j].getText().equals("-1")) {
					sudokuField[i][j].clear();
					sudokuField[i][j].setColor("");

				}
			}
		}
		resetTextFills();

		model.setNumbersInsideTextField(0);

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
		scene.getGameInfoLabel().setText("");
		model.initializeCustomGame();
		enableEdit();
		scene.getLiveTimeLabel().setText("");
	}

	/**
	 * deletes user inputs 
	 * values (fixed values that were created automatically)
	 * that were created at the start of the game do not get deleted
	 */
	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!model.getCells()[j][i].getFixedNumber() && !sudokuField[i][j].getText().equals("-1")) {

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
		/**
		 * Checks if there are any conflict in the sudoku that the user wants to create.
		 * Checks if there are enough numbers in the sudoku that the user wants to create.
		 * 
		 * the If gets entered if both these conditions are met, else the user gets asked
		 * to either remove the conflicts ore input more numbers to create his sudoku game
		 */
		if (compareResult() && enoughManualNumbers()) {
			this.connectWithModel();
			model.solveSudoku();
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					model.getSavedResults()[i][j] = model.getCells()[i][j].getValue();
				}
			}
			/**
			 * Test if the manually created sudoku game is solvable.
			 * User gets asked to create a new game if his current inputs
			 * lead to an unsolvable sudoku.
			 */
			if (!model.testIfSolved()) {
				model.setGameState(Gamestate.MANUALCONFLICT);
				scene.getGameNotificationLabel().setText("This sudoku game is unsolvable! Please create a new one.");
			} 
			/**
			 * If all conditions are met (no conflicts, enough numbers 
			 * and solvable sudoku) the sudoku gets created and the numbers
			 * of the user get fixed (can not be changed by the user afterwards).
			 * 
			 * The the buttons and menu-items get enabled so that the user can
			 * play with his manually created game
			 */
			else {
				for (int i = 0; i < sudokuField.length; i++) {
					for (int j = 0; j < sudokuField[i].length; j++) {
						if (model instanceof FreeFormLogic)
							sudokuField[i][j].removeFreeFormColorListener();
						if (!sudokuField[i][j].getText().equals("")) {
							model.getCells()[j][i].setValue(Integer.parseInt(sudokuField[i][j].getText()));
							model.getCells()[j][i].setFixedNumber(true);
							sudokuField[i][j].getStyleClass().remove("textfieldBasic");
							sudokuField[i][j].getStyleClass().remove("textfieldBasic");
							sudokuField[i][j].getStyleClass().remove("textfieldWrong");

							sudokuField[i][j].getStyleClass().add("textfieldLocked");
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
		/**
		 * boolean value that gets returned at the end of this method
		 */
		boolean result = true;

		if (model instanceof SamuraiLogic)
			model.setNumbersInsideTextField(72);
		else
			model.setNumbersInsideTextField(0);

		connectWithModel();
		System.out.println(model.getNumbersInsideTextField());
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				/**
				 * sets the current value to 0 is it is not a fixed number (auto generated number)
				 */
//				if (!model.getCells()[row][col].getFixedNumber()) {
//					model.getCells()[row][col].setValue(0);
//				}
				/**
				 * a style class gets removes from the current text field
				 */
				sudokuField[col][row].getStyleClass().remove("textfieldWrong");

				/**
				 * checks if the current text field is empty or has a value of -1
				 */
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.setNumbersInsideTextField(model.getNumbersInsideTextField() + 1);
//					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					/**
					 * checks if the current number is a fixed number (auto generated number)
					 */
					if (!model.getCells()[row][col].getFixedNumber()) {
						/**
						 * sets the current number to 0, so that the valid method does not return a wrong return value
						 */
						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
							/**
							 * if the number with in the current text field with the row and column
							 * coordinates is wrong, a new style class is added (number gets shown in red),
							 * so that the user knows that there is a conflict with this number
							 */
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
	 * Connects text field-array with model-array.
	 * Fills the text field-array with the values of the model-array.
	 */
	public void connectArrays() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}
			}
		}
	}

	/**
	 * The current sudoku game gets solved if there are no conflicts.
	 * Asks the user to remove the conflicts if there are any.
	 * Uses the following methods {@link #connectArrays(), #compareResult(),
	 * #connectWithModel(), application.BasicGameBuilder#removeConflictListeners()}.
	 */
	public void autoSolveHandler(ActionEvent e) {
		/**
		 * Conflict listeners get removed so that no problems occur while the sudoku gets solved.
		 */
		scene.removeConflictListeners();
		/**
		 * Checks if the are any conflict in the current game.
		 */
		if (compareResult()) {
			connectWithModel();
			model.solveSudoku();
			model.setGamePoints(0);
			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " | Difficulty: " + model.getDifficultystring());
			model.getLiveTimer().stop();

			/**
			 * Checks if the current sudoku is solved after the solve method gets called.
			 * If it is not solved, all user inputs get deleted and a completely new
			 * solution gets generated.
			 */
			if (!model.testIfSolved()) {
				resetHandler(e);
				model.setGameState(Gamestate.UNSOLVABLE);
				scene.getGameNotificationLabel().setText(model.getGameText() + " New solution was generated.");
				model.solveSudoku();
			}
			else {
				model.setGameState(Gamestate.AUTOSOLVED);
				scene.getGameNotificationLabel().setText(model.getGameText());
			}
			connectArrays();
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
	 */
	public void checkHandler(ActionEvent e) {
		boolean gameState = compareResult();
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
	 * Empty text fields get enabled so that the user can input his
	 * numbers inside them.
	 */
	public void enableEdit() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!model.getCells()[j][i].getFixedNumber()) {
					sudokuField[i][j].setDisable(false);
				} else {
					sudokuField[i][j].setDisable(true);
					sudokuField[i][j].getStyleClass().remove("textfieldBasic");
					sudokuField[i][j].getStyleClass().add("textfieldLocked");
				}
			}
		}
	}

	
	public void resetTextFills() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (sudokuField[i][j].isDisabled()) {
					sudokuField[i][j].getStyleClass().remove("textfieldLocked");
					sudokuField[i][j].getStyleClass().add("textfieldBasic");
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
			}
		}
	}

	/**
	 * Gives the user a hint.
	 * Uses the methods {@link #connectWithModel(), #compareResult()}
	 * 
	 * @param e
	 */
	public void hintHandeler(ActionEvent e) {
		/**
		 * Checks if the current sudoku has any conflicts.
		 * The User needs to remove the conflicts if there are any.
		 */
		if (compareResult()) {
			/**
			 * Checks if points need to be deducted after the user presses the hint button.
			 */
			if (model.getGamepoints() > 0 && (!model.getGamestate().equals(Gamestate.UNSOLVABLE)
					&& !model.getGamestate().equals(Gamestate.CONFLICT))) {
				model.setGamePoints(model.getGamepoints() - 1);
			}
			connectWithModel();
			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			int[] coordinates = model.hint();
			/**
			 *  checks if coordinates are null
			 */
			if (coordinates != null) {
				/**
				 * checks if there is already a user input in this text field with these coordinates
				 * this check exists so that user inputs do not get overwritten
				 */
				if (sudokuField[coordinates[1]][coordinates[0]].getText().equals("")) {
					String number = Integer.toString(model.getCells()[coordinates[0]][coordinates[1]].getValue());
					sudokuField[coordinates[1]][coordinates[0]].setText(number);
					sudokuField[coordinates[1]][coordinates[0]].getStyleClass().add("textfieldHint");

					model.setGameState(Gamestate.OPEN);
					scene.getGameNotificationLabel().setText(model.getGameText());
				}
			} else if(!model.testIfSolved()) {
				for (int row = 0; row < sudokuField.length; row++) {
					for (int col = 0; col < sudokuField[row].length; col++) {
						if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")
								&& model.getSavedResults()[row][col] != Integer
										.parseInt(sudokuField[col][row].getText())) {
							sudokuField[col][row].getStyleClass().add("textfieldWrong");
						}
					}
				}
				model.setGameState(Gamestate.UNSOLVABLE);
				scene.getGameNotificationLabel().setText(model.getGameText() + " Please remove the red numbers to continue.");
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

			connectImportedArray();

			GUI.getStage().setHeight(scene.getHeight());
			GUI.getStage().setWidth(scene.getWidth());
			GUI.getStage().getScene().setRoot(scene.getGameUIRoot());
		}
	}
	
	
	
	
	/**
	 * This auxiliary method is used during the import process, its use is 
	 * to align the imported array of numbers with the UI textfield array
	 * Furthermore depending on the state of a field in the model array
	 * different colors are shown in the textfield array
	 */
	public void connectImportedArray() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (model instanceof FreeFormLogic && !model.getCells()[j][i].getBoxcolor().equals("")) {
					sudokuField[i][j].setColor(model.getCells()[j][i].getBoxcolor());
				}
				if (model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}

				if (model.getCells()[j][i].getFixedNumber() && model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setDisable(true);
					sudokuField[i][j].getStyleClass().remove("textfieldBasic");
					sudokuField[i][j].getStyleClass().add("textfieldLocked");
				}

				 if (model.getCells()[j][i].isHint() && model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].getStyleClass().add("textfieldHint");
				}
			}
		}
		// depending on the game state a solution of the game should be created in the
		// background
		if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)
				&& !model.getGamestate().equals(Gamestate.NOFORMS)
				&& !model.getGamestate().equals(Gamestate.MANUALCONFLICT)
				&& !model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS))
			model.setSavedResults(model.alignWithHelper());
	}
	

	
	/**
	 * exports the game as a JSON-File user can choose the storage path
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