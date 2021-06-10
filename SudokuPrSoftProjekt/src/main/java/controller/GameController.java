package controller;

import java.util.stream.Stream;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SaveModel;
import logic.SudokuStorageModel;

/**
 * GameController ist das Bindeglied zwischen der View und dem Model Die
 * Eingaben von der View werden an das Model weitergeleitet Die Eingaben vom
 * Model werden an die View weitergeleitet
 */
public class GameController {

	BasicGameBuilder scene;
	BasicGameLogic model;
	SudokuField[][] sudokuField;
	static int numberCounter = 1;

	/**
	 * Constructor to create a GameController-Object
	 * 
	 * @param scene : is the playfield visible to the user
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
	}

	/**
	 * deletes all number from the text field restarts Timer and resets gamestate to
	 * OPEN
	 */
	public void newGameHandler(ActionEvent event) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].clear();
				sudokuField[i][j].getStyleClass().remove("textfieldHint");
			}
		}

		numberCounter = 0;

		if (model.timerIsRunning()) {
			model.getLiveTimer().stop();
		}
		scene.getLiveTimeLabel().textProperty().unbind();

		if (model.getDifficulty() == 0) {
			resetCustomGameState();
		} else {
			createGame();
		}
		scene.getGameNotificationLabel().setText(model.getGameText());
	}

	/**
	 * resets a manually created game buttons for solve, check and hint get disabled
	 * lock-button gets enabled and becomes visible {@link #enableEdit()}
	 */
	public void resetCustomGameState() {
		if (model.getGametype().equals("FreeForm")) {
			if (model.getGamestate().equals(Gamestate.DRAWING)) {
				scene.getColorsDoneButton().setVisible(true);
				scene.getColorBox().setVisible(true);
			}
			if (model.getGamestate().equals(Gamestate.CREATING)) {
				scene.getToolBar().getItems().add(3, scene.getColorsDoneButton());
				scene.getToolBar().getItems().add(3, scene.getColorBox());
				scene.getDoneButton().setVisible(false);
			}
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					sudokuField[i][j].setStyle("");
					if (!sudokuField[i][j].isListeningToColors()) {
						sudokuField[i][j].addFreeFormColorListener(scene.getColorBox());
					}
				}
			}
		} else {
			scene.getDoneButton().setVisible(true);
		}
		model.initializeCustomGame();
		scene.disablePlayButtons();
		scene.getLiveTimeLabel().setText("");
		enableEdit();
	}

	/**
	 * deletes user inputs values that were created at the start of the game do not
	 * get deleted
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
	 * Colors the text fields, depending on the information from the model is
	 * relevant for FreeForm-Games
	 * 
	 * @param e
	 */
	public void customColorsDoneHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				model.getCells()[j][i].setBoxcolor(sudokuField[i][j].getColor());
			}
		}
		
		boolean helper = true;
			int[] cells1to9 = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			int box;
			for (int d = 0; d < model.getCells().length; d++) {
				for (int h= 0; h < this.model.getCells()[d].length; h++) {
					box = model.getCells()[d][h].getBox();
					cells1to9[box - 1] += 1;
				}
			}
			for (int nk = 0; nk < 9; nk++) {
				if (cells1to9[nk] != 9) {
					helper = false;
				}
			}

		if (model.isConnected() &&  helper == true) {
			for (SudokuField coloredArray[] : sudokuField) {
				for (SudokuField coloredField : coloredArray) {
					coloredField.removeFreeFormColorListener();
				}
			}
			scene.getToolBar().getItems().remove(scene.getColorsDoneButton());
			scene.getToolBar().getItems().remove(scene.getColorBox());
			scene.getToolBar().getItems().add(3, scene.getDoneButton());
			scene.getDoneButton().setVisible(true);
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
			scene.getDoneButton().setVisible(false);
			Stream.of(scene.getHintButton(), scene.getAutoSolveButton(), scene.getCheckButton())
					.forEach(button -> button.setDisable(false));
		} else if (!compareResult()) {
			model.removeValues();
			model.setGameState(Gamestate.CONFLICT);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
	}
	

	/**
	 * test if there are enough numbers to create a sudoku
	 * 
	 * @return returns false if there are not enough numbers and returns true
	 *         otherwise
	 */
	public boolean enoughManualNumbers() {
		int count = 0;
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!sudokuField[i][j].getText().equals("")) {
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
		numberCounter = 0;
		if (model instanceof SamuraiLogic)
			numberCounter = 72;

		connectWithModel();

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				sudokuField[col][row].getStyleClass().remove("textfieldWrong");
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					numberCounter++;

					// vielleicht unnötig
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					if (!model.getCells()[row][col].getFixedNumber()) {
						// nochmal genauer drüberschaun zwecks verständnis
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
		if (result && model.getGamestate() != Gamestate.AUTOSOLVED && model.getGamestate() != Gamestate.UNSOLVABLE) {
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
				if (model instanceof FreeFormLogic) {
					sudokuField[i][j].setStyle("-fx-background-color: #" + model.getCells()[j][i].getBoxcolor() + ";");
				}
				if (model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}
				if (model.getCells()[j][i].getFixedNumber()) {
					sudokuField[i][j].setDisable(true);
				}
			}
		}
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
			if (model.getGamestate() != Gamestate.UNSOLVABLE) {
				model.setGameState(Gamestate.AUTOSOLVED);
			}
			model.setGamePoints(0);
			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " | Difficulty: " + model.getDifficultystring());
			model.getLiveTimer().stop();
			scene.getGameNotificationLabel().setText(model.getGameText());

			if (!model.testIfSolved()) {
				resetHandler(e);
				model.setGameState(Gamestate.UNSOLVABLE);
				model.solveSudoku();

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
		if (gameState && (numberCounter == sudokuField.length * sudokuField.length)) {
			if (!model.getGamestate().equals(Gamestate.AUTOSOLVED)) {
				model.setGameState(Gamestate.DONE);
				scene.getGameNotificationLabel().setText(model.getGameText());
				model.getLiveTimer().stop();
			}
		} else if (!gameState) {
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
			if (model.getGamepoints() > 0) {
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
		}
	}

	/**
	 * adds or removes Listener to the text field the listeners call the
	 * {@link #compareResult()} method and show conflicts
	 * 
	 * @param e
	 */
	public void switchOffConflicts(ActionEvent e) {
		if (scene.getConflictItem().isSelected())
			scene.addConflictListeners();
		else
			scene.removeConflictListeners();
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
				if (!sudokuField[col][row].getColor().equals(""))
					model.getCells()[row][col].setBoxcolor(sudokuField[col][row].getColor());
			}
		}
		if (model.timerIsRunning()) {
			model.getLiveTimer().stop();
		}
		SudokuStorageModel storageModel = new SudokuStorageModel();
		storageModel.saveGame(model);
	}

	/**
	 * Imports a game This import must be a JSON file a new scene is created
	 * depending on the import
	 * 
	 * @param e
	 */
	public void importGame(ActionEvent e) {
		SudokuStorageModel storageModel = new SudokuStorageModel();

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

			if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)) {
				model.initializeTimer();
				model.getLiveTimer().start();
				scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));
			}

			if (model.getGamestate().equals(Gamestate.CREATING)) {
				if (!scene.getToolBar().getItems().get(3).equals(scene.getDoneButton())) {
					scene.getToolBar().getItems().add(3, scene.getDoneButton());
				}
				scene.getDoneButton().setVisible(true);
				scene.disablePlayButtons();
			}

			if (model.getGamestate().equals(Gamestate.DRAWING)) {
				scene.getColorBox().setVisible(true);
				scene.getColorsDoneButton().setVisible(true);
				scene.disablePlayButtons();

			}

			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " | Difficulty: " + model.getDifficultystring());
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
            }
        }
        SudokuStorageModel storageModel = new SudokuStorageModel();
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

	public SudokuField[][] getsudokuField() {
		return this.sudokuField;
	}
}