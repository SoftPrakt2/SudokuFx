package controller;

import org.json.simple.JSONObject;

import application.BasicGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
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
	

	// Storage storage = new Storage();


	IntegerProperty helper = new SimpleIntegerProperty();

	public GameController(BasicGameBuilder scene, BasicGameLogic model) {
		this.scene = scene;
		this.model = model;
		sudokuField = scene.getTextField();
	}
	

	/**
	 * 
	 * Erstellt abh�ngig von der Schwierigkeit ein Sudoku-Spiel Setzt die Punkte des
	 * Spiels auf 10 Setzt GameText auf "Game ongoing" Setzt GameState auf OPEN
	 */
	public void createGameHandler(ActionEvent e) {
		createGame();
	}

	/**
	 * 
	 * L�scht s�mtliche Zahlen aus dem Spielfeld und setzt die verschiedenen
	 * Spielstatuse auf den Anfangszustand
	 */
	public void newGameHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].clear();

				
			}
		}
		model.setStartTime(System.currentTimeMillis());
		model.setGamePoints(10);
		scene.getGameLabel().setText("");
		model.setGameState(Gamestate.OPEN);
		scene.getCheckButton().setDisable(false);
		numberCounter = 0;
		
		createGame();
	}

	/**
	 * 
	 * L�scht Benutzereingaben f�r das Spiel
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
		model.setStartTime(System.currentTimeMillis());
		model.setGamePoints(10);
	
		model.setGameState(Gamestate.OPEN);
		scene.getGameInfoLabel().setText(model.getGameText());
	}

	/**
	 * 
	 * Fixiert die manuelle Sudokuerstellung des Spielers und �bergibt die
	 * eingegebenen Zahlen der Logik
	 */
	public void manuelDoneHandler(ActionEvent e) {
		boolean help = true;

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!sudokuField[i][j].getText().equals("")) {
					sudokuField[i][j].getText();
					if (model.valid(j, i, Integer.parseInt(sudokuField[i][j].getText()))) {
						sudokuField[i][j].setDisable(true);
						model.setCell(j, i, Integer.parseInt(sudokuField[i][j].getText()));
					} else {
						help = false;
					}
				}
			}
		}
		if (!help) {
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					model.getCells()[i][j].setValue(0);
					sudokuField[j][j].setText("");
					sudokuField[j][i].setDisable(false);
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
	 * �berpr�ft ob Konflikte zwischen den dem Benutzer eingegebnen Zahlen herschen
	 * Kennzeichnet diese rot
	 */
	public boolean compareResult(SudokuField[][] sudokuField) {
		boolean result = true;
		numberCounter = 0;
		if(model instanceof SamuraiLogic) numberCounter = 72;

		connectWithModel();

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
			
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
					numberCounter++;
					
							// vielleicht unn�tig
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					if (!model.getCells()[row][col].getIsReal()) {
						// nochmal genauer dr�berschaun zwecks verst�ndnis
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
	 * Hilfsmethode f�r die Bef�llung der TextFields mit den Zahlen aus dem model
	 */
	public void connectArrays(SudokuField[][] sudokuField) {
//		for (int row = 0; row < sudokuField.length; row++) {
//			for (int col = 0; col < sudokuField[row].length; col++) {
//				sudokuField[row][col].setText(Integer.toString(model.getCells()[col][row].getValue()));
//			}
//		}

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
	 * L�st sofern keine Konflikte vorhanden sind das derzeitige Spielfeld Fals
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
					// nochmal dr�ber schaun
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
	 * �berpr�ft ob der derzeitige Stand der Eingagen eine g�ltige L�sung ist und
	 * gibt abh�ngig davon unterschiedliche Informationen aus
	 */
	public void checkHandler(ActionEvent e) {

		boolean gameState = compareResult(sudokuField);
		if (gameState) {
			// wsl unn�tig
			for (int i = 0; i < sudokuField.length; i++) {
				for (int j = 0; j < sudokuField[i].length; j++) {
					sudokuField[j][i].setStyle("-fx-text-fill: black");
				}
			}
		}

		if (gameState && numberCounter == sudokuField.length * sudokuField.length) {
			if (!model.getGameState().equals(Gamestate.AUTOSOLVED)) {
				model.setGameState(Gamestate.DONE);
				scene.getGameNotificationLabel().setText(model.getGameText());
				model.getLiveTimer().stop();
			}
			
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

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				String number = Integer.toString(model.getCells()[j][i].getValue());
				// �berpr�fung: !sudokuField[i][j].getText().equals("") wird vl nicht ben�tigt
				if ((!sudokuField[i][j].getText().equals("") || !sudokuField[i][j].getText().equals("-1"))
						&& !number.equals("0")) {
					sudokuField[i][j].setText(number);
					sudokuField[i][j].setPlayable(false);

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
	 * Leere Textfelder werden f�r den Benutzer freigegeben
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

			if (compareResult(sudokuField)) {
				if (model.getgamePoints() > 0)
					model.setGamePoints(model.getgamePoints() - 1);
				connectWithModel();
				scene.getGameInfoLabel()
				.setText("Points: " + model.getgamePoints() + " Difficulty: " + model.getDifficultyString());
				int[] coordinates = model.hint();
				for (int row = 0; row < sudokuField.length; row++) {
					for (int col = 0; col < sudokuField[row].length; col++) {
						if(coordinates != null) {
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
				model.setHintsPressed(model.getHintsPressed()+1);
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

	// methode f�r auto konflikt removen und adden

	public void switchOffConflicts(ActionEvent e) {
		if (scene.getConflictItem().isSelected())
			scene.addListeners(sudokuField);
		else
			scene.removeListeners(sudokuField);
	}

	// methode f�r hintcount erh�hen
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

		model.getLiveTimer().stop();
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
