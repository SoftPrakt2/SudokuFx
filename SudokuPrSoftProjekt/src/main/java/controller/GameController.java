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
		scene.getGameInfoLabel().setText("Points: " + model.getGamepoints() + " Difficulty: " + model.getDifficultystring());
		scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {

				if (model instanceof FreeFormLogic) {
					sudokuField[i][j].setStyle("-fx-background-color: #" + model.getCells()[j][i].getBoxcolor() + ";");
				}

				String number = Integer.toString(model.getCells()[j][i].getValue());
				// überprüfung: !sudokuField[i][j].getText().equals("") wird vl nicht benötigt
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
			scene.getDoneButton().setVisible(true);
			scene.disablePlayButtons();
			model.initializeCustomGame();
			scene.getLiveTimeLabel().setText("");
			enableEdit();
		} else {
			model.setGameState(Gamestate.OPEN);
		
			
			scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));
		
			createGame();
		}
		scene.getGameNotificationLabel().setText(model.getGameText());
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
	}

	public void customColorsDoneHandler(ActionEvent e) {
		scene.getToolBar().getItems().remove(3);
		scene.getToolBar().getItems().remove(3);
		scene.getToolBar().getItems().add(3, scene.getDoneButton());

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (sudokuField[i][j].isListeningToColors()) {
					sudokuField[i][j].removeFreeFormColorListener();
				}
				model.getCells()[j][i].setBoxcolor(sudokuField[i][j].getColor());
				if (model.isConnected()) {
					System.out.println("isyeet");
				}
			}
		}

		scene.getDoneButton().setVisible(true);
		System.out.println("Drawing completed");
		model.setGameState(Gamestate.CREATING);
	}

	/**
	 * 
	 * Fixiert die manuelle Sudokuerstellung des Spielers und übergibt die
	 * eingegebenen Zahlen der Logik
	 */
	public void manuelDoneHandler(ActionEvent e) {

		if (compareResult()) {
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
		} else {
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					model.getCells()[i][j].setValue(0);
					sudokuField[j][j].setText("");

				}
			}
			model.setGameState(Gamestate.CONFLICT);
			scene.getGameNotificationLabel().setText(model.getGameText());
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
							// sudokuField[col][row].getStyleClass().remove(0);
							sudokuField[col][row].getStyleClass().add("textfieldWrong");
							// sudokuField[col][row].getStyleClass().remove("textfieldBasic");

							model.setGameState(Gamestate.INCORRECT);
//							model.getCells()[row][col].setValue(0);
							result = false;
						} else {
							sudokuField[col][row].getStyleClass().remove("textfieldWrong");
							sudokuField[col][row].getStyleClass().add("textfieldBasic");
							
							// sudokuField[col][row].setStyle("-fx-text-fill: black");
						}
					}
				}
			}
		}
		if(result) {
			model.setGameState(Gamestate.OPEN);
		}
		
		return result;
	}

	/**
	 * 
	 * Hilfsmethode für die Befüllung der TextFields mit den Zahlen aus dem model
	 */
	public void connectArrays(SudokuField[][] sudokuField) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
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
		scene.removeListeners(sudokuField);
		if (compareResult()) {
			connectWithModel();
			model.solveSudoku();
			model.setGameState(Gamestate.AUTOSOLVED);
			
			
			model.setGamePoints(0);
			scene.getGameInfoLabel().setText("Points: " + model.getGamepoints() + " | Difficulty: " + model.getDifficultystring());
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
			System.out.println(model.getGamestate());
			if (!model.getGamestate().equals(Gamestate.AUTOSOLVED)) {
				System.out.println("test");
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

		if (model.getHintsPressed() < model.getHintCounter()) {

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
								// sudokuField[col][row].setStyle("-fx-text-fill: blue");
								sudokuField[col][row].getStyleClass().add("textfieldHint");

							}
						}
					}
				}
				model.setHintsPressed(model.getHintsPressed() + 1);
			} else {
				if (model.getGamepoints() > 0)
					model.setGamePoints(model.getGamepoints() - 1);
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

			if (model.getGametype().equals("Samurai")) {
				scene = new SamuraiGameBuilder(model);
			}
			if (model.getGametype().equals("FreeForm")) {
				scene = new FreeFormGameBuilder(model);
			}

			scene.initializeGame();

			if (!model.getGamestate().equals(Gamestate.CREATING) && !model.getGamestate().equals(Gamestate.DRAWING)) {
				model.initializeTimer();
				model.getLiveTimer().start();
				scene.getLiveTimeLabel().textProperty().bind(Bindings.concat(model.getStringProp()));
			}

			if (model.getGamestate().equals(Gamestate.CREATING)) {
				scene.getDoneButton().setVisible(true);
				scene.disablePlayButtons();
			}

			if (model.getGamestate().equals(Gamestate.DRAWING)) {
				scene.getColorBox().setVisible(true);
				scene.getColorsDoneButton().setVisible(true);
				scene.disablePlayButtons();
				scene.getDoneButton().setVisible(true);
			}

			scene.getGameInfoLabel().setText("Points: " + model.getGamepoints() + " | Difficulty: " + model.getDifficultystring());
			scene.getGameNotificationLabel().setText(model.getGameText());
			
			SudokuField[][] s = scene.getTextField();

			emptyArrays();
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s[i].length; j++) {
					if (model instanceof FreeFormLogic) {
						s[i][j].setStyle("-fx-background-color: #" + model.getCells()[j][i].getBoxcolor() + ";");
					}
					if (model.getCells()[j][i].getValue() != 0) {
						s[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
					}
					if (model.getCells()[j][i].getIsReal()) {
						s[i][j].setDisable(true);
					}
				}
			}

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

}