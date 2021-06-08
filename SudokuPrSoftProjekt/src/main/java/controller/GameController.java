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

public class GameController {

	BasicGameBuilder scene;
	BasicGameLogic model;
	SudokuField[][] sudokuField;
	static int numberCounter = 1;

	public GameController(BasicGameBuilder scene, BasicGameLogic model) {
		this.scene = scene;
		this.model = model;
		sudokuField = scene.getTextField();
	}

	/**
	 * 
	 * Erstellt abhängig von der Schwierigkeit ein Sudoku-Spiel Setzt die Punkte des
	 * Spiels auf 10 Setzt GameText auf "Game ongoing" Setzt GameState auf OPEN
	 */
	public void createGameHandler(ActionEvent event) {
		createGame();
	}

	/**
	 * 
	 * Erstellt ein Spiel anhand der eingestellten Schwierigkeit
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
		model.printCells();
	}

	/**
	 * 
	 * Löscht sämtliche Zahlen aus dem Spielfeld und setzt die verschiedenen
	 * Spielstatuse auf den Anfangszustand
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

	public void resetCustomGameState() {
		if (model.getGametype().equals("FreeForm")) {
			scene.getToolBar().getItems().add(3, scene.getColorsDoneButton());
			scene.getToolBar().getItems().add(3, scene.getColorBox());
			scene.getColorsDoneButton().setVisible(true);
			scene.getColorBox().setVisible(true);
		} else {
			scene.getDoneButton().setVisible(true);
		}
		model.initializeCustomGame();
		scene.disablePlayButtons();
		scene.getLiveTimeLabel().setText("");
		enableEdit();
	}

	/**
	 * 
	 * Löscht Benutzereingaben für das Spiel
	 */
	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!model.getCells()[j][i].getIsReal() && !sudokuField[i][j].getText().equals("-1")) {
					sudokuField[i][j].clear();
					model.getCells()[j][i].setValue(0);
				}
			}
		}
		model.setGameState(Gamestate.OPEN);
		scene.getGameNotificationLabel().setText(model.getGameText());
	}

	public void customColorsDoneHandler(ActionEvent e) {

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				model.getCells()[j][i].setBoxcolor(sudokuField[i][j].getColor());
			}
		}

		if (model.isConnected()) {
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
	 * 
	 * Fixiert die manuelle Sudokuerstellung des Spielers und übergibt die
	 * eingegebenen Zahlen der Logik
	 */
	public void manuelDoneHandler(ActionEvent e) {

		if (compareResult() && enoughManualNumbers()) {
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					if (model instanceof FreeFormLogic)
						sudokuField[i][j].removeFreeFormColorListener();
					if (!sudokuField[i][j].getText().equals("")) {
						model.setCell(j, i, Integer.parseInt(sudokuField[i][j].getText()));

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
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					model.getCells()[i][j].setValue(0);

				}
			}
			model.setGameState(Gamestate.CONFLICT);
			scene.getGameNotificationLabel().setText(model.getGameText());
		}
	}

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
	 * 
	 * Überprüft ob Konflikte zwischen den dem Benutzer eingegebnen Zahlen herschen
	 * Kennzeichnet diese rot
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
					if (!model.getCells()[row][col].getIsReal()) {
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
	 * 
	 * Hilfsmethode für die Befüllung der TextFields mit den Zahlen aus dem model
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
				if (model.getCells()[j][i].getIsReal()) {
					sudokuField[i][j].setDisable(true);
				}
			}
		}
	}

	/**
	 * 
	 * Löst sofern keine Konflikte vorhanden sind das derzeitige Spielfeld Fals
	 * Konflikte vorhanden sind werden diese markiert und der Benutzer wird darauf
	 * hingewiesen
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
	 * 
	 * Überprüft ob der derzeitige Stand der Eingagen eine gültige Lösung ist und
	 * gibt abhängig davon unterschiedliche Informationen aus
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
	 * Leere Textfelder werden für den Benutzer freigegeben
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

	public void hintHandeler(ActionEvent e) {
		if (compareResult()) {
			if (model.getGamepoints() > 0) {
				model.setGamePoints(model.getGamepoints() - 1);
			}
			connectWithModel();
			scene.getGameInfoLabel()
					.setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
			int[] coordinates = model.hint();
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (coordinates != null) {
						if (coordinates[0] == row && coordinates[1] == col
								&& sudokuField[col][row].getText().equals("")) {
							String number = Integer.toString(model.getCells()[row][col].getValue());
							sudokuField[col][row].setText(number);
							sudokuField[col][row].getStyleClass().add("textfieldHint");

						}
					}
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

	// methode für auto konflikt removen und adden

	public void switchOffConflicts(ActionEvent e) {
		if (scene.getConflictItem().isSelected())
			scene.addConflictListeners();
		else
			scene.removeConflictListeners();
	}

	

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
			GUI.getStage().getScene().setRoot(scene.getPane());
		}

	}

	public void exportGame(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				}
			}
		}

		SudokuStorageModel storageModel = new SudokuStorageModel();
		storageModel.exportGame(model);
	}

	public void switchToMainMenu(ActionEvent e) {
		emptyArrays();

		MainMenu mainmenu = new MainMenu();
		mainmenu.setUpMainMenu();

		GUI.getStage().setHeight(670);
		GUI.getStage().setWidth(670);
		GUI.getStage().getScene().setRoot(GUI.getMainMenu());

	}

	public void emptyArrays() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].setText("");
				sudokuField[i][j].setDisable(false);
			}
		}
	}

	public BasicGameLogic getModel() {
		return this.model;
	}

	public SudokuField[][] getsudokuField() {
		return this.sudokuField;
	}
}