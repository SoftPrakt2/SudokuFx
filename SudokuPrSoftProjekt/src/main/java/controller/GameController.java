package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.BasicGameBuilder;
import application.GUI;
import application.Storage;
import application.SudokuField;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuStorageModel;

public class GameController {

	BasicGameBuilder scene;
	BasicGameLogic model;
	SudokuField[][] sudokuField;
	static int numberCounter = 0;
	 int countHintsPressed = 0;
	

	Storage storage = new Storage();

	//File file = storage.getSaveFile();
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
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].clear();
				sudokuField[i][j].setText("");
				sudokuField[i][j].setDisable(false);
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
	
	//	model.setGameState(Gamestate.DONE);
	}

	/**
	 * 
	 * Überprüft ob Konflikte zwischen den dem Benutzer eingegebnen Zahlen herschen
	 * Kennzeichnet diese rot
	 */
	public boolean compareResult(SudokuField[][] sudokuField) {
		boolean result = true;
		numberCounter = 0;

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
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				sudokuField[row][col].setText(Integer.toString(model.getCells()[col][row].getValue()));
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
			model.setGameState(Gamestate.AutoSolved);
			model.setGamePoints(0);
			scene.getGameInfoLabel().setText("Points: " + model.getgamePoints() + " | Difficulty: " + getDifficulty());
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
			//wsl unnötig
//			for (int i = 0; i < sudokuField.length; i++) {
//				for (int j = 0; j < sudokuField[i].length; j++) {
//					sudokuField[j][i].setStyle("-fx-text-fill: black");
//				}
//			}
		}

		if (gameState && numberCounter == sudokuField.length * sudokuField.length) {
			calculateGameTime();

			if (!model.getGameState().equals(Gamestate.AutoSolved)) {
				model.setGameState(Gamestate.DONE);
				scene.getGameNotificationLabel().setText(model.getGameText());
			}
			scene.getPlayTimeLabel().setText(
					" | Playtime: " + model.getMinutesPlayed() + " minutes " + model.getSecondsPlayed() + " seconds ");

		} else if (!gameState || numberCounter != sudokuField.length * sudokuField.length) {

			scene.getGameNotificationLabel().setText(model.getGameText());
		}

	}

	/**
	 * 
	 * Berechnet die benötigte Spielzeit
	 */
	//hier raus ins model
	public long calculateGameTime() {
		long time;
		long endTime = System.currentTimeMillis();
		time = (endTime - model.getStartTime()) / 1000;
		time += model.getLoadedMinutes() * 60 + model.getLoadedSeconds();
		model.setSecondsPlayed(time);
		if (time > 60) {
			model.setMinutesPlayed(time / 60);
			model.setSecondsPlayed(time % 60);
		}
		return time;
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
				// überprüfung: !sudokuField[i][j].getText().equals("") wird vl nicht benötigt
				if ((!sudokuField[i][j].getText().equals("") || !sudokuField[i][j].getText().equals("-1"))
						&& !number.equals("0")) {
					sudokuField[i][j].setText(number);
					sudokuField[i][j].setPlayable(false);

				}
			}
		}
	//	scene.addListeners(sudokuField);
		model.setGamePoints(10);
		scene.getGameInfoLabel().setText("Points: " + model.getgamePoints() + " Difficulty: " + getDifficulty());
		model.setGameState(Gamestate.OPEN);
		enableEdit();
		model.printCells();
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
		
		
		if(countHintsPressed < model.getHintCounter()) {
		
		if (compareResult(sudokuField)) {
			if (model.getgamePoints() > 0)
				model.setGamePoints(model.getgamePoints() - 1);
			connectWithModel();

			int[] coordinates = model.hint();
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (coordinates[0] == row && coordinates[1] == col && sudokuField[col][row].getText().equals("")) {
						String number = Integer.toString(model.getCells()[row][col].getValue());
						sudokuField[col][row].setText(number);
						sudokuField[col][row].setStyle("-fx-text-fill: blue");
						sudokuField[col][row].setFont(Font.font("Verdana", FontWeight.BOLD, 16));
					}
				}
			}
			countHintsPressed++;
		} else {
			if (model.getgamePoints() > 0)
				model.setGamePoints(model.getgamePoints() - 1);
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					}
				}
			}
			model.setGameState(Gamestate.CONFLICT);
			scene.getGameInfoLabel().setText("Points: " + model.getgamePoints() + " Difficulty: " + getDifficulty());
		}
		
	} else {
		scene.getGameNotificationLabel().setText("No more hints left");
	}
		
	}

	public void switchToMainMenu(ActionEvent e) {
		GUI.getStage().setScene(GUI.getMainMenu());
		
	}
	
	
	//methode für auto konflikt removen und adden
	
	public void switchOffConflicts(ActionEvent e) {
		if(scene.getConflictItem().isSelected())
		scene.addListeners(sudokuField);
		else
			scene.removeListeners(sudokuField);
	}
	
	
	
	//methode für hintcount erhöhen
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
		calculateGameTime();
		
		SudokuStorageModel storageModel = new SudokuStorageModel(model);
		storageModel.saveGame();
		
		//model.saveGame();
	}
	
	public void importGame(ActionEvent e) {
		SudokuStorageModel storageModel = new SudokuStorageModel(model);
		storageModel.importGame();
		
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].clear();
				sudokuField[i][j].setText("");
				sudokuField[i][j].setDisable(false);
			}
		}
		
		
		
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (model.getCells()[j][i].getValue() != 0) {
					sudokuField[i][j].setText(Integer.toString(model.getCells()[j][i].getValue()));
				}
				if (!model.getCells()[j][i].getIsReal()) {
					sudokuField[i][j].setDisable(false);
				}
			}
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
		calculateGameTime();
		SudokuStorageModel storageModel = new SudokuStorageModel(model);
		storageModel.exportGame();
	}
	
	
	

//	public int getLastGameID(File file) {
//		JSONParser parser = new JSONParser();
//
//		int gameId = 0;
//
//		try {
//			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
//			JSONObject jsonObject = (JSONObject) obj;
//
//			JSONArray gameArray = (JSONArray) jsonObject.get("games");
//
//			JSONObject helper = (JSONObject) gameArray.get(gameArray.size() - 1);
//
//			gameId = (int) (long) helper.get("gameID");
//
//		} catch (FileNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} catch (ParseException ex) {
//			ex.printStackTrace();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		System.out.println(gameId);
//
//		if (gameId == 0)
//			return 0;
//		else
//			return gameId;
//	}

	public int getModelGamePoints() {
		return model.getgamePoints();
	}

	public void setModelGamePoints(int gamePoints) {
		model.setGamePoints(gamePoints);
	}

	public long getModelMinutesPlayed() {
		return model.getMinutesPlayed();
	}

	public long getModelSecondsPlayed() {
		return model.getSecondsPlayed();
	}

	public String getDifficulty() {
		String difficulty = "";
		if (model.getDifficulty() == 3)
			difficulty = "hard";
		if (model.getDifficulty() == 5)
			difficulty = "medium";
		if (model.getDifficulty() == 7)
			difficulty = "easy";
		if (model.getDifficulty() == 0)
			difficulty = "manual";
		return difficulty;
	}
}
