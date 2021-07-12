package controller;

import java.util.stream.Stream;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import application.SudokuTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SaveModel;
import logic.SudokuStorage;

/**
 * The GameController class is a connection between the View-Components (GUI)
 * and the Model (sudoku game logic).
 * 
 * User Inputs in the View-Component get passed on to the Model. The same is
 * also true the other way round. The generated sudoku game and other variables
 * or inputs also get passed on to the View-Component.
 */
public class GameController {

	/**
	 * This is the View-Component (GUI) that the user gets to see
	 */
	private BasicGameBuilder gameBuilder;
	/**
	 * This is the Model-Component that handles all the game related logic
	 */
	private BasicGameLogic model;
	/**
	 * This text field has all the auto generated values.
	 * the user can also input his own values into these text fields.
	 */
	private SudokuTextField[][] sudokuField;


	/**
	 * Constructor to create a GameController-Object
	 * 
	 * @param scene : the Game UI currently visible to the user.
	 * @param model : is the logic for creating a game and handling game mechanics.
	 */
	public GameController(BasicGameBuilder gameBuilder, BasicGameLogic model) {
		this.gameBuilder = gameBuilder;
		this.model = model;
		sudokuField = gameBuilder.getTextField();
	}

	/**
	 * Creates, depending on the difficulty that the user chooses, a sudoku game.
	 * Calls the method {@link #createGame()}.
	 */
	public void createGameHandler(ActionEvent event) {
		createGame();
	}

	/**
	 * Creates a Sudoku-Game. Type and difficulty of the game get decided by the
	 * user inputs (button-clicks). {@link #enableEdit()} enables the text fields,
	 * so that the user can play. Timer gets started. The {@link #sudokuField}-array
	 * values get aligned with the {@link logic.BasicGameLogic#getCells()}-array
	 * values
	 */
	public void createGame() {
		gameBuilder.removeConflictListeners();
		model.setUpGameField();
		model.setUpGameInformations();
		gameBuilder.getGameInfoLabel()
				.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
		gameBuilder.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getTimeProperty()));

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (model instanceof FreeFormLogic) {
					sudokuField[i][j].setColor(model.getCells()[j][i].getBoxcolor());
				}
				String number = Integer.toString(model.getCells()[j][i].getValue());
				if ((!sudokuField[i][j].getText().equals("") || !sudokuField[i][j].getText().equals("-1"))
						&& !number.equals("0")) {
					sudokuField[i][j].setText(number);
				}
			}
		}

		enableEdit();
	
		if (gameBuilder.getConflictItem().isSelected()) {
			gameBuilder.addConflictListeners();
		}
	}

	/**
	 * Deletes all numbers from the text field and resets all the necessary
	 * variables for a new game to their default state/values. Restarts Timer and
	 * resets game state to OPEN.
	 * 
	 * Depending on the selected game in the main menu either {@link #createGame()}
	 * {@link #resetManualSudokuOrSamurai()} or {@link #newManualFreeFormGame()} are
	 * called and responsible for creating a new game.
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
		model.setGameID(0);

		if (model.timerIsRunning()) {
			model.getLiveTimer().stop();
		}
		gameBuilder.getLiveTimeLabel().textProperty().unbind();

		if (model.getDifficulty() == 0 && model.getGametype().equals("FreeForm")) {
			newManualFreeFormGame();
		}

		if (model.getDifficulty() == 0 && !model.getGametype().equals("FreeForm")) {
			newManualSudokuOrSamurai();

		} else if (model.getDifficulty() > 0) {
			createGame();
		}
		gameBuilder.getGameNotificationLabel().setText(model.getGameText());
	}

	/**
	 * This method is used to start a new manual freeform game while inside this
	 * type of game configuration. To do so the the colorlisteners defined in the
	 * {@link application.SudokuTextField} class will be added again to the
	 * text fields.
	 * Depending on the game state when starting a new round different
	 * buttons have to be disabled or enabled again.
	 */
	public void newManualFreeFormGame() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].setStyle("");
				sudokuField[i][j].setColor("");
				if (!sudokuField[i][j].isListeningToColors()) {
					sudokuField[i][j].addFreeFormColorListener(gameBuilder.getColorBox());
				}
			}
		}

		if (model.getGamestate().equals(Gamestate.DRAWING)) {
			gameBuilder.getColorsDoneButton().setVisible(true);
			gameBuilder.getColorBox().setVisible(true);
		} else if (model.getGamestate().equals(Gamestate.CREATING)) {
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getCustomNumbersDone());
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getColorsDoneButton());
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getColorBox());
			gameBuilder.getToolBar().getItems().add(3, gameBuilder.getColorsDoneButton());
			gameBuilder.getToolBar().getItems().add(3, gameBuilder.getColorBox());
			gameBuilder.getColorBox().setVisible(true);
			gameBuilder.getColorsDoneButton().setVisible(true);
		} else {
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getCustomNumbersDone());
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getColorsDoneButton());
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getColorBox());
			gameBuilder.getToolBar().getItems().add(3, gameBuilder.getColorsDoneButton());
			gameBuilder.getToolBar().getItems().add(3, gameBuilder.getColorBox());
			gameBuilder.getColorBox().setVisible(true);
			gameBuilder.getColorsDoneButton().setVisible(true);
		}
		model.initializeCustomGame();
		gameBuilder.disablePlayButtons();
		gameBuilder.getLiveTimeLabel().setText("");
		gameBuilder.getGameInfoLabel().setText("");
		enableEdit();
	}

	/**
	 * This method is used to start a new manual sudoku or samurai game while inside
	 * this type of game configuration.
	 * To do so the {@link application.BasicGameBuilder #getCustomNumbersDone()} will be enabled
	 * and visible again and the play buttons will be disabled
	 */
	public void newManualSudokuOrSamurai() {

		gameBuilder.getToolBar().getItems().remove(gameBuilder.getCustomNumbersDone());
		gameBuilder.getToolBar().getItems().add(3, gameBuilder.getCustomNumbersDone());
		gameBuilder.getCustomNumbersDone().setVisible(true);
		gameBuilder.disablePlayButtons();

		if (!model.getGamestate().equals(Gamestate.CREATING)) {
			model.setGameState(Gamestate.CREATING);
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
		}
		gameBuilder.getGameInfoLabel().setText("");
		model.initializeCustomGame();
		enableEdit();
		
		gameBuilder.getLiveTimeLabel().setText("");
	}

	/**
	 * Deletes user inputs and hints.
	 * Fixed values that were created automatically do not get deleted.
	 */
	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!model.getCells()[j][i].getFixedNumber() && !sudokuField[i][j].getText().equals("-1")) {
					sudokuField[i][j].clear();
					model.getCells()[j][i].setValue(0);
				}
				if (model.getCells()[j][i].isHint()) {
					sudokuField[i][j].setDisable(false);
					model.getCells()[j][i].setIsHint(false);
				}

			}
		}
		// only sets the game state to OPEN again if a game is successfully created or was not automatically solved before
		if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)
				&& !model.getGamestate().equals(Gamestate.MANUALCONFLICT)
				&& !model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)
				&& !model.getGamestate().equals(Gamestate.NOFORMS) && !model.getGamestate().equals(Gamestate.AUTOSOLVED)) {
			model.setGameState(Gamestate.OPEN);
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * Fixes the numbers that the user wants to create a sudoku with.
	 * Checks for conflicts and asks the user to remove them if any occur.
	 * Checks if the manually created sudoku is solvable - if it is not solvable the user needs to
	 * create a new sudoku. The method {@link #compareResult()} test if there are any conflicts.
	 * {@link #enoughManualNumbers()} tests if there are enough numbers to create a sudoku.
	 */
	public void manuelDoneHandler(ActionEvent e) {
		/**
		 * Checks if there are any conflict in the sudoku that the user wants to create.
		 * Checks if there are enough numbers in the sudoku that the user wants to
		 * create.
		 * 
		 * The If statement gets entered if both these conditions are met, or else the user gets
		 * asked to either remove the conflicts ore input more numbers to create his
		 * sudoku game.
		 */

		if (compareResult() && enoughManualNumbers()) {
			this.connectWithModel();

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
			
			//after the alignment of the UI SudokuTextField Array and the model Cell array the remaining game informations
			//from a model have to be connected with the UI
			model.setUpGameInformations();

			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
			gameBuilder.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getTimeProperty()));
			gameBuilder.getGameInfoLabel()
			.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			
			gameBuilder.getToolBar().getItems().remove(gameBuilder.getCustomNumbersDone());
			
			//enable the components which are needed for the game functionalities 
			Stream.of(gameBuilder.getHintButton(), gameBuilder.getAutoSolveButton(), gameBuilder.getCheckButton())
					.forEach(button -> button.setDisable(false));
			Stream.of(gameBuilder.getAutoSolveMenuItem(), gameBuilder.getHintMenuItem(), gameBuilder.getCheckMenuItem(),gameBuilder.getConflictItem())
					.forEach(menuItem -> menuItem.setDisable(false));
		} else if (!compareResult()) {
			model.removeValues();
			model.setGameState(Gamestate.MANUALCONFLICT);
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * Tests if there are enough numbers inserted by the user to create a manual sudoku.
	 * 
	 * @return returns false if there are not enough numbers and returns true
	 *         otherwise.
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
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method is needed to connect the form of the sudoku text fields the user has
	 * created during a manual freeform game to the model.
	 * If the forms are created correctly the
	 * {@link application.SudokuTextField#addFreeFormColorListener(javafx.scene.control.ComboBox)}
	 * get removed from the sudoku text fields. Afterwards the UI Control Components
	 * get updated
	 * 
	 * @param e event which is fired through user actions in the UI
	 */
	public void customColorsDoneHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				model.getCells()[j][i].setBoxcolor(sudokuField[i][j].getColor());
			}
		}

		if(model instanceof FreeFormLogic) {
			if (((FreeFormLogic)model).isConnected() && ((FreeFormLogic)model).proofFilledOut()) {
				for (SudokuTextField coloredArray[] : sudokuField) {
					for (SudokuTextField coloredField : coloredArray) {
						coloredField.removeFreeFormColorListener();
					}
				}
				gameBuilder.getToolBar().getItems().remove(gameBuilder.getColorsDoneButton());
				gameBuilder.getToolBar().getItems().remove(gameBuilder.getColorBox());
				gameBuilder.getToolBar().getItems().add(3, gameBuilder.getCustomNumbersDone());
				gameBuilder.getCustomNumbersDone().setVisible(true);
				model.setGameState(Gamestate.CREATING);
				gameBuilder.getGameNotificationLabel().setText(model.getGameText());
			} else {
				model.setGameState(Gamestate.NOFORMS);
				gameBuilder.getGameNotificationLabel().setText(model.getGameText());
			}
		}
	}

	/**
	 * Checks for conflicts and colors them red if there are any. Returns true if
	 * there are no conflicts and returns false otherwise.
	 * {@link #connectWithModel()} connects the model-array with the text field-array.
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
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				/**
				 * a style class gets removes from the current text field
				 */
				sudokuField[col][row].getStyleClass().remove("textfieldWrong");

				/**
				 * checks if the current text field is empty or has a value of -1
				 */
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.setNumbersInsideTextField(model.getNumbersInsideTextField() + 1);
					/**
					 * checks if the current number is a fixed number (auto generated number)
					 */
					if (!model.getCells()[row][col].getFixedNumber() && !model.getCells()[row][col].isHint()) {
						/**
						 * sets the current number to 0, so that the valid method does not return a
						 * wrong return value
						 */
						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
							/**
							 * if the number within the current text field with the row and column
							 * coordinates is wrong, a new style class is added (number gets shown in red),
							 * so that the user knows that there is a conflict with this number
							 */
							model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
							sudokuField[col][row].getStyleClass().remove("textfieldHint");
							sudokuField[col][row].getStyleClass().add("textfieldWrong");
							model.setGameState(Gamestate.INCORRECT);
							result = false;
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
	 * The current sudoku game gets solved if there are no conflicts. Asks the user
	 * to remove the conflicts if there are any. Uses the following methods
	 * {@link #connectArrays() {@link #compareResult() {@link #connectWithModel()}
	 * {@link lapplication.BasicGameBuilder#removeConflictListeners()}
	 */
	public void autoSolveHandler(ActionEvent e) {
		/**
		 * Conflict listeners get removed so that no problems occur while the sudoku
		 * gets solved.
		 */
		gameBuilder.removeConflictListeners();
		/**
		 * Checks if there are any conflict in the current game.
		 */
		if (compareResult()) {
			connectWithModel();
			model.solveSudoku();
			model.setGamePoints(0);
			gameBuilder.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			model.getLiveTimer().stop();

			/**
			 * Checks if the current sudoku is solved after the solve method gets called. If
			 * it is not solved, all user inputs get deleted and a completely new solution
			 * gets generated.
			 * If the current game is a manually created game, and the sudoku was unsolvable
			 * since the beginning, the sudoku does not get solved, because it can not delete
			 * fixed numbers after they get set while creating a game.
			 */
			if (!model.testIfSolved()) {
				resetHandler(e);
				model.setGameState(Gamestate.UNSOLVABLE);
				gameBuilder.getGameNotificationLabel().setText(model.getGameText() + " New solution was generated.");
				model.solveSudoku();				
			} else {
				model.setGameState(Gamestate.AUTOSOLVED);
				gameBuilder.getGameNotificationLabel().setText(model.getGameText());
			}
			connectArrays();
		} else {
//			for (int row = 0; row < sudokuField.length; row++) {
//				for (int col = 0; col < sudokuField[row].length; col++) {
//					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
//						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
//					}
//				}
//			}
			model.setGameState(Gamestate.CONFLICT);
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
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
				gameBuilder.getGameNotificationLabel().setText(model.getGameText());
				if(model.timerIsRunning())
				model.getLiveTimer().stop();
			
			}
		} else if (!gameState || model.getNumbersInsideTextField() != sudokuField.length * sudokuField.length) {
			model.setGameState(Gamestate.INCORRECT);
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * Gives the user a hint. Uses the methods {@link #connectWithModel(),
	 * #compareResult()}
	 * 
	 * @param e event which is fired through user actions in the UI
	 */
	public void hintHandeler(ActionEvent e) {
		/**
		 * Checks if the current sudoku has any conflicts. The User needs to remove the
		 * conflicts if there are any.
		 */
		if (compareResult()) {
			if (model.getGamepoints() < 0) model.setGamePoints(0);
			/**
			 * Checks if points need to be deducted after the user presses the hint button.
			 */
			if (model.getGamepoints() > 0 && (!model.getGamestate().equals(Gamestate.UNSOLVABLE)
					&& !model.getGamestate().equals(Gamestate.CONFLICT))) {
				
				if(model.getGametype().equals("Sudoku")) model.setGamePoints(model.getGamepoints() - 3);
				if(model.getGametype().equals("Samurai")) model.setGamePoints(model.getGamepoints() - 1);
				if(model.getGametype().equals("FreeForm")) model.setGamePoints(model.getGamepoints() - 2);
			}
			connectWithModel();
			gameBuilder.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			int[] coordinates = model.hint();
			/**
			 * checks if coordinates are null
			 */
			if (coordinates != null) {
				/**
				 * Checks if there is already a user input in this text field with these
				 * coordinates.
				 * This check exists so that user inputs do not get overwritten
				 */
				if (sudokuField[coordinates[1]][coordinates[0]].getText().equals("")) {
					String number = Integer.toString(model.getCells()[coordinates[0]][coordinates[1]].getValue());
					model.getCells()[coordinates[0]][coordinates[1]].setIsHint(true);
					sudokuField[coordinates[1]][coordinates[0]].setText(number);
					sudokuField[coordinates[1]][coordinates[0]].getStyleClass().add("textfieldHint");
					sudokuField[coordinates[1]][coordinates[0]].setDisable(true);

					model.setGameState(Gamestate.OPEN);
					gameBuilder.getGameNotificationLabel().setText(model.getGameText());
				}
			} else if (!model.testIfSolved()) {
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
				gameBuilder.getGameNotificationLabel()
						.setText(model.getGameText() + " Please remove the red numbers to continue.");
			}
		} else {
			if (model.getGamepoints() > 0)
				model.setGamePoints(model.getGamepoints() - 1);

			model.setGameState(Gamestate.CONFLICT);
			gameBuilder.getGameNotificationLabel().setText(model.getGameText());
		}
	}

	/**
	 * this method is used when the game is saved in a Game UI Scene. The GameModels
	 * Cell-Array is aligned with the numbers inside the UI SudokuTextFieldArray. The
	 * timer of the UI will be stopped and afterwards the game will be saved with
	 * the {@link logic.SudokuStorage#saveGame(BasicGameLogic)} method.
	 * 
	 * @param e event which is fired through user actions in the UI
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
		
		gameBuilder.getGameNotificationLabel().setText("Game saved! ID: " + model.getGameid());
	}

	/**
	 * Imports a game. This import must be a JSON file. A new scene is created
	 * depending on the import. Depending on the saved games game state different UI
	 * objects have to be enabled or disabled.
	 * 
	 * @param e
	 */
	public void importGame(ActionEvent e) {
		SudokuStorage storageModel = new SudokuStorage();

		SaveModel saveModel = storageModel.getImportedFile();

		if (storageModel.fileExists()) {
			model = storageModel.loadIntoModel(model, saveModel);

			if (model.getGametype().equals("Sudoku")) {
				gameBuilder = new SudokuGameBuilder(model);
			}

			else if (model.getGametype().equals("Samurai")) {
				gameBuilder = new SamuraiGameBuilder(model);
			} else if (model.getGametype().equals("FreeForm")) {
				gameBuilder = new FreeFormGameBuilder(model);
			}

			gameBuilder.initializeGame();
			
			//depending on the gamestate UI components have to be configured differentely 
			if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)
					&& !model.getGamestate().equals(Gamestate.MANUALCONFLICT)
					&& !model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)
					&& !model.getGamestate().equals(Gamestate.NOFORMS)) {
				
				gameBuilder.getGameInfoLabel().setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
				
				//when the game has already been solved then just set the text of the play time and dont initialize a timer
				if(model.getGamestate().equals(Gamestate.AUTOSOLVED) || model.getGamestate().equals(Gamestate.DONE)) {
					gameBuilder.getLiveTimeLabel().setText(String.format("%02d:%02d", model.getMinutesplayed(),model.getSecondsplayed()));
				} else {

				model.initializeTimer();
				model.getLiveTimer().start();
				gameBuilder.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getTimeProperty()));

				}
			}

			//needed configurations for a manual sudoku or samurai game whose numbers have not been fixed yet
			if (model.getGamestate().equals(Gamestate.CREATING) || model.getGamestate().equals(Gamestate.MANUALCONFLICT)
					|| model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS)) {
				gameBuilder.getToolBar().getItems().remove(gameBuilder.getCustomNumbersDone());
				gameBuilder.getToolBar().getItems().add(3, gameBuilder.getCustomNumbersDone());
				gameBuilder.getCustomNumbersDone().setVisible(true);
				gameBuilder.disablePlayButtons();
			}
			
			//needed configurations for a manual freeform game whose forms have not been fixed yet
			if (model.getGamestate().equals(Gamestate.DRAWING) || model.getGamestate().equals(Gamestate.NOFORMS)) {
				gameBuilder.getColorBox().setVisible(true);
				gameBuilder.getColorsDoneButton().setVisible(true);
				gameBuilder.disablePlayButtons();
			}

			gameBuilder.getGameNotificationLabel().setText(model.getGameText());

			sudokuField = gameBuilder.getTextField();
			emptyArrays();

			connectImportedArray();

		
			alignWindowToGameSize();
		}
	}
	
	/**
     * This method is used to align the programs window size to the size variables
     * defined in the {@link #gameBuilder} object
     */
    public void alignWindowToGameSize() {
         Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            GUI.getStage().setHeight(gameBuilder.getHeight());
            GUI.getStage().setWidth(gameBuilder.getWidth());
            GUI.getStage().getScene().setRoot(gameBuilder.getGameUIRoot());

             GUI.getStage().setX((screenBounds.getWidth() - gameBuilder.getSceneWidth()) / 2);
             GUI.getStage().setY((screenBounds.getHeight() - gameBuilder.getSceneHeight()) / 2);
            
    }

	

	/**
	 * This auxiliary method is used during the import process. Its use is to align
	 * the imported array of numbers with the UI text field array. Furthermore
	 * depending on the state of a field in the model array different colors are
	 * shown in the text field array
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

				else if (model.getCells()[j][i].isHint() && model.getCells()[j][i].getValue() != 0) {

					sudokuField[i][j].getStyleClass().add("textfieldHint");
					sudokuField[i][j].setDisable(true);
				}
			}
		}
		// depending on the game state a solution of the game should be created in the
		// background
		if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)
				&& !model.getGamestate().equals(Gamestate.NOFORMS)
				&& !model.getGamestate().equals(Gamestate.MANUALCONFLICT)
				&& !model.getGamestate().equals(Gamestate.NOTENOUGHNUMBERS))
			model.setSavedResults(model.createBackgroundSolution());
	}

	/**
	 * exports the game as a JSON-File user can choose the storage path
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

		if (model.timerIsRunning()) {
			model.getLiveTimer().stop();
		}

		SudokuStorage storageModel = new SudokuStorage();
		storageModel.exportGame(model);
	}

	/**
	 * switches the scene to the menu-scene
	 * 
	 * @param e ActionEvent which is fired when the user performs an Action in the UI
	 */
	  public void switchToMainMenu(ActionEvent e) {
	        emptyArrays();
	        MainMenu mainmenu = new MainMenu();
	        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
	        mainmenu.setUpMainMenu();


	        GUI.getStage().setHeight(mainmenu.getScreenHeight());
	        GUI.getStage().setWidth(mainmenu.getScreenWidth());
	        GUI.getStage().setX((bounds.getWidth() - mainmenu.getScreenWidth()) / 2);
	        GUI.getStage().setY((bounds.getHeight() - mainmenu.getScreenHeight()) / 2);
	        
	        
	        GUI.getStage().getScene().setRoot(GUI.getMainMenu());
	        
	        
	    }

	/**
	 * deletes all inputs from the text fields-array and enables all text fields
	 */
	public void emptyArrays() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!sudokuField[i][j].getText().equals("-1")) {
					sudokuField[i][j].setText("");
					sudokuField[i][j].setDisable(false);
				}
			}
		}
	}

	/**
	 * Connects the text field-array with the model-array. Fills the text field-array with
	 * the values of the model-array.
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
	 * Empty text fields get enabled so that the user can input his numbers into them.
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

	/**
	 * This auxiliary method is used when creating a new game inside a GameUI. Sets
	 * back the colors of the numbers inside the playing field.
	 */
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
	 * Connect the text field-array with the model-array.
	 */
	public void connectWithModel() {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				} else if (!sudokuField[col][row].getText().equals("-1")
						|| sudokuField[col][row].getText().equals("")) {
					model.getCells()[row][col].setValue(0);
				}
			}
		}
	}



	/**
	 * Adds or removes Listener to the text field. The listeners call the
	 * {@link #compareResult()} method and show conflicts.
	 * 
	 * @param e event which is fired through user actions in the UI
	 */
	public void switchOffConflicts(ActionEvent e) {
		if (gameBuilder.getConflictItem().isSelected()) {
			gameBuilder.addConflictListeners();
		} else {
			gameBuilder.removeConflictListeners();
		}
	}

	/**
	 * getter and setter get used by JUnit-Tests
	 */
	public BasicGameLogic getModel() {
		return this.model;
	}

	public SudokuTextField[][] getsudokuField() {
		return this.sudokuField;
	}

	public BasicGameBuilder getScene() {
		return this.gameBuilder;
	}
}