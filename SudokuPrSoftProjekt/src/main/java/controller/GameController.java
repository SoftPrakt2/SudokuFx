package controller;

import application.BasicGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;
import logic.SudokuStorageModel;

public class GameController {

	BasicGameBuilder scene;
	BasicGameLogic model;
	SudokuField[][] sudokuField;
	static int numberCounter = 0;
	int countHintsPressed = 0;

	int gameID = 1;
	IntegerProperty helper = new SimpleIntegerProperty();

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
	public void createGameHandler(ActionEvent e) {
		createGame();
	}

	/**
	 * 
	 * Löscht sämtliche Zahlen aus dem Spielfeld und setzt die verschiedenen
	 * Spielstatuse auf den Anfangszustand
	 */
	public void newGameHandler(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				sudokuField[row][col].clear();

			}
		}
		model.setStartTime(System.currentTimeMillis());
		model.setGamePoints(10);
		scene.getGameLabel().setText("");
		model.setGameState(Gamestate.OPEN);
		scene.getCheckButton().setDisable(false);
		numberCounter = 0;
		countHintsPressed = 0;
		createGame();
	}

	/**
	 * 
	 * Löscht Benutzereingaben für das Spiel
	 */
	public void resetHandler(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!model.getCells()[col][row].getIsReal() && !sudokuField[row][col].getText().equals("-1")) {
					sudokuField[row][col].clear();
					model.getCells()[col][row].setValue(0);
				}
			}
		}
		model.setStartTime(System.currentTimeMillis());
		model.setGamePoints(10);

		model.setGameState(Gamestate.OPEN);
		scene.getGameInfoLabel().setText(model.getGameText());
	}

	/**
	 * 
	 * Fixiert die manuelle Sudokuerstellung des Spielers und übergibt die
	 * eingegebenen Zahlen der Logik
	 */
	public void manuelDoneHandler(ActionEvent e) {
		boolean help = true;

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[row][col].getText().equals("")) {
					sudokuField[row][col].getText();
					if (model.valid(col, row, Integer.parseInt(sudokuField[row][col].getText()))) {
						sudokuField[row][col].setDisable(true);
						model.setCell(col, row, Integer.parseInt(sudokuField[row][col].getText()));
					} else {
						help = false;
					}
				}
			}
		}
		if (!help) {
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					model.getCells()[row][col].setValue(0);
					sudokuField[col][row].setText("");
					sudokuField[col][row].setDisable(false);
				}
			}
			scene.getGameNotificationLabel().setText("Your game had conflicts");
		}

		// model.setGameState(Gamestate.DONE);
	}

	public void setColorsHandler(ActionEvent e) {

	}

	/**
	 * 
	 * Überprüft ob Konflikte zwischen den dem Benutzer eingegebnen Zahlen herschen
	 * Kennzeichnet diese rot
	 */
	public boolean compareResult(SudokuField[][] sudokuField) {
		boolean result = true;
		numberCounter = 0;
		if (model instanceof SamuraiLogic)
			numberCounter = 72;

		connectWithModel();

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {

				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					numberCounter++;

					// vielleicht unnötig
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					if (!model.getCells()[row][col].getIsReal()) {
						// nochmal genauer drüberschaun zwecks verständnis
						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
							model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
							sudokuField[col][row].setStyle("-fx-text-fill: red");
							model.setGameState(Gamestate.INCORRECT);
//							model.getCells()[row][col].setValue(0);
							result = false;
						} else {
							sudokuField[col][row].setStyle("-fx-text-fill: black");
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * Hilfsmethode für die Befüllung der TextFields mit den Zahlen aus dem model
	 */
	public void connectArrays(SudokuField[][] sudokuField) {
//		for (int row = 0; row < sudokuField.length; row++) {
//			for (int col = 0; col < sudokuField[row].length; col++) {
//				sudokuField[row][col].setText(Integer.toString(model.getCells()[col][row].getValue()));
//			}
//		}

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (model.getCells()[col][row].getValue() != 0) {
					sudokuField[row][col].setText(Integer.toString(model.getCells()[col][row].getValue()));
				}
				if (model.getCells()[col][row].getIsReal()) {
					sudokuField[row][col].setDisable(true);
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
		scene.removeListeners(sudokuField);
		if (compareResult(sudokuField)) {
			connectWithModel();
			model.solveSudoku();
			model.setGameState(Gamestate.AUTOSOLVED);
			model.setGamePoints(0);
			scene.getGameInfoLabel()
					.setText("Points: " + model.getgamePoints() + " | Difficulty: " + model.getDifficultyString());
			model.getLiveTimer().stop();
			scene.getGameNotificationLabel().setText(model.getGameText());
			if (!model.testIfSolved()) {
				resetHandler(e);
				model.solveSudoku();
				model.setGameState(Gamestate.UNSOLVABLE);
			}
			connectArrays(scene.getTextField());
		} else {
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					}
					// nochmal drüber schaun
//					else {
//						model.getCells()[row][col].setValue(0);
//					}
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

		boolean gameState = compareResult(sudokuField);
		if (gameState) {
			// wsl unnötig
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					sudokuField[col][row].setStyle("-fx-text-fill: black");
				}
			}
		}

		if (gameState && numberCounter == sudokuField.length * sudokuField.length) {
			model.calculateGameTime();
			// scene.getTimer().stop();

			if (!model.getGameState().equals(Gamestate.AUTOSOLVED)) {
				model.setGameState(Gamestate.DONE);
				scene.getGameNotificationLabel().setText(model.getGameText());
				model.getLiveTimer().stop();
			}
			scene.getPlayTimeLabel().setText(
					" | Playtime: " + model.getMinutesPlayed() + " minutes " + model.getSecondsPlayed() + " seconds ");
		} else if (!gameState || numberCounter != sudokuField.length * sudokuField.length) {
			scene.getGameNotificationLabel().setText(model.getGameText());
		}

	}

	/**
	 * 
	 * Erstellt ein Spiel anhand der eingestellten Schwierigkeit
	 */
	public void createGame() {
		model.setUpLogicArray();
		model.createSudoku();
		model.setDifficultyString();

		model.difficulty();
		model.setStartTime(System.currentTimeMillis());

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				String number = Integer.toString(model.getCells()[col][row].getValue());
				// überprüfung: !sudokuField[i][j].getText().equals("") wird vl nicht benötigt
				if ((!sudokuField[row][col].getText().equals("") || !sudokuField[row][col].getText().equals("-1"))
						&& !number.equals("0")) {
					sudokuField[row][col].setText(number);
					sudokuField[row][col].setPlayable(false);
				}
			}
		}
		// scene.addListeners(sudokuField);
		model.setGamePoints(10);
		scene.getGameInfoLabel()
				.setText("Points: " + model.getgamePoints() + " Difficulty: " + model.getDifficultyString());
		model.setGameState(Gamestate.OPEN);
		enableEdit();
		model.printCells();
		setUpLiveTimer();
	}

	public void setUpLiveTimer() {
		model.initializeTimer();
		model.getLiveTimer().start();
		scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));
	}

	/**
	 * Leere Textfelder werden für den Benutzer freigegeben
	 */
	public void enableEdit() {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (model.getCells()[col][row].getValue() == 0) {
					sudokuField[row][col].setDisable(false);
				} else {
					sudokuField[row][col].setDisable(true);
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

		if (countHintsPressed < model.getHintCounter()) {

			if (compareResult(sudokuField)) {
				if (model.getgamePoints() > 0)
					model.setGamePoints(model.getgamePoints() - 1);
				connectWithModel();
				scene.getGameInfoLabel()
						.setText("Points: " + model.getgamePoints() + " Difficulty: " + model.getDifficultyString());
				int[] coordinates = model.hint();
				for (int row = 0; row < sudokuField.length; row++) {
					for (int col = 0; col < sudokuField[row].length; col++) {
						if (coordinates != null) {
							if (coordinates[0] == row && coordinates[1] == col
									&& sudokuField[col][row].getText().equals("")) {
								String number = Integer.toString(model.getCells()[row][col].getValue());
								sudokuField[col][row].setText(number);
								sudokuField[col][row].setStyle("-fx-text-fill: blue");
								sudokuField[col][row].setFont(Font.font("Verdana", FontWeight.BOLD, 16));
							}
						}
					}
				}
				countHintsPressed++;
			} else {
				if (model.getgamePoints() > 0)
					model.setGamePoints(model.getgamePoints() - 1);
				for (int row = 0; row < sudokuField.length; row++) {
					for (int col = 0; col < sudokuField[row].length; col++) {
						if (!sudokuField[col][row].getText().equals("")
								&& !sudokuField[col][row].getText().equals("-1")) {
							model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
						}
					}
				}
				model.setGameState(Gamestate.CONFLICT);

			}

		} else {
			scene.getGameNotificationLabel().setText("No more hints left");
		}

	}

	// methode für auto konflikt removen und adden

	public void switchOffConflicts(ActionEvent e) {
		if (scene.getConflictItem().isSelected())
			scene.addListeners(sudokuField);
		else
			scene.removeListeners(sudokuField);
	}

	// methode für hintcount erhöhen
	public void handleMoreHints(ActionEvent e) {
		model.setHintCounter(model.getHintCounter() * 2);
		scene.getGameNotificationLabel().setText("Extra supplies yo");
	}

	public void saveGame(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				}
			}
		}
		model.calculateGameTime();

		SudokuStorageModel storageModel = new SudokuStorageModel();
		storageModel.saveGame(model);

	}

	public void importGame(ActionEvent e) {
		SudokuStorageModel storageModel = new SudokuStorageModel();

		storageModel.setImportedFile();

		if (storageModel.getGametype().equals("Sudoku")) {
			storageModel.setLoadedLogic(new SudokuLogic(Gamestate.OPEN, 0, 0, false));
			scene = new SudokuGameBuilder(storageModel.getLoadedLogic());
		}

		if (storageModel.getGametype().equals("Samurai")) {
			storageModel.setLoadedLogic(new SamuraiLogic(Gamestate.OPEN, 0, 0, false));
			scene = new SamuraiGameBuilder(storageModel.getLoadedLogic());
		}

		storageModel.setStoredInformations();
		storageModel.loadIntoModel();
		model = storageModel.getLoadedLogic();

		sudokuField = scene.getTextField();
		scene.initializeGame();

		emptyArrays();

		GUI.getStage().setHeight(scene.getHeight());
		GUI.getStage().setWidth(scene.getWidth());
		GUI.getStage().getScene().setRoot(scene.getPane());

		connectArrays(sudokuField);
	}

	public void exportGame(ActionEvent e) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				}
			}
		}
		model.calculateGameTime();
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
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				sudokuField[row][col].setText("");
				sudokuField[row][col].setDisable(false);

			}
		}
	}
}
