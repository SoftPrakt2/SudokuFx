package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.BasicGameBuilder;
import application.GUI;
import application.Storage;
import application.SudokuField;
import application.SudokuSaveModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import logic.BasicGameLogic;
import logic.Gamestate;

public class GameController {

	BasicGameBuilder scene;
	BasicGameLogic model;
	SudokuField[][] sudokuField;
	static int numberCounter = 0;
	
	

	Storage storage = new Storage();

	File file = storage.getSaveFile();

	long loadedSeconds;
	long loadedMinutes;
	int gameID = 1;
	int gameIDhelper;

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
		createGame(scene.getDifficulty());
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
		createGame(scene.getDifficulty());
	}

	/**
	 * 
	 * Löscht Benutzereingaben für das Spiel
	 */
	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!model.getCells()[j][i].getIsReal()) {
					sudokuField[i][j].clear();
					model.getCells()[j][i].setValue(0);
				}
			}
		}
		model.setStartTime(System.currentTimeMillis());
		model.setGamePoints(10);
		model.setGameState(Gamestate.OPEN);
		scene.getGameLabel().setText(model.getGameText());
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
					sudokuField[i][j].setDisable(false);
				}
			}
		}
		model.setGameState(Gamestate.DONE);
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
//			for (int row = 0; row < sudokuField.length; row++) {
//				for (int col = 0; col < sudokuField[row].length; col++) {
//					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
//						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
//					}
//					else 
//				}
//			}
			
			connectWithModel();
			model.solveSudoku();
			model.printCells();
			model.setGameState(Gamestate.AutoSolved);
			if (!model.testIfSolved()) {
				resetHandler(e);
				model.solveSudoku();
				model.setGameState(Gamestate.UNSOLVABLE);
			}
			scene.getGameLabel().setText(model.getGameText());
			model.setGamePoints(0);
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
			scene.getGameLabel().setText(model.getGameText());
		}
	}

	/**
	 * 
	 * Überprüft ob der derzeitige Stand der Eingagen eine gültige Lösung ist und
	 * gibt abhängig davon unterschiedliche Informationen aus
	 */
	public void checkHandler(ActionEvent e) {

		boolean gameState = compareResult(sudokuField);
//		if (gameState == true) {
//			for (int i = 0; i < sudokuField.length; i++) {
//				for (int j = 0; j < sudokuField[i].length; j++) {
//					sudokuField[j][i].setStyle("-fx-text-fill: black");
//				}
//			}
//		}

		if (gameState && numberCounter == sudokuField.length * sudokuField.length) {
			long timeHelp = calculateGameTime();
			model.setGameState(Gamestate.DONE);
			if (timeHelp < 60) {
				scene.getGameLabel().setText(model.getGameText() + " Points: " + model.getgamePoints() + " Time: "
						+ model.getSecondsPlayed() + " sek");
			} else {

				scene.getGameLabel().setText(model.getGameText() + " Points: " + model.getgamePoints() + " Time: "
						+ model.getMinutesPlayed() + " min " + model.getSecondsPlayed() + " s ");
			}

		} else if (!gameState || numberCounter != sudokuField.length * sudokuField.length) {
			scene.getGameLabel().setText(model.getGameText());
		}

	}

	/**
	 * 
	 * Berechnet die benötigte Spielzeit
	 */
	public long calculateGameTime() {
		long time;
		long endTime = System.currentTimeMillis();
		time = (endTime - model.getStartTime()) / 1000;
		time += loadedMinutes * 60 + loadedSeconds;
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
	public void createGame(int difficulty) {
		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(difficulty);
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
//				sudokuField[i][j].textProperty().addListener(new ChangeListener<String>() {
//					@Override
//					public void changed(ObservableValue<? extends String> observable, String oldValue,
//							String newValue) {
//						compareResult(sudokuField);
//					}
//				});
			}
		}
		scene.addListeners(sudokuField);
//		scene.removeListeners(sudokuField);
		model.setGamePoints(10);
		scene.getGameLabel().setText("Game ongoing!");
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

				SudokuField s = sudokuField[j][i];
				final int help = i;
				final int help2 = j;

//				sudokuField[j][i].textProperty().addListener(new ChangeListener<String>() {
//
//					@Override
//					public void changed(ObservableValue<? extends String> observable, String oldValue,
//							String newValue) {
//						// TODO Auto-generated method stub
//						if (!newValue.equals("")) {
//							checkIfok();
//						}
//					}
//				});
			}
		}
	}

//	public void checkIfok() {
//		connectWithModel();
//
//		for (int row = 0; row < sudokuField.length; row++) {
//			for (int col = 0; col < sudokuField[row].length; col++) {
//				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-1")) {
//					numberCounter++;
//					// vielleicht unnötig
//					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
//					if (!model.getCells()[row][col].getIsReal()) {
//						// nochmal genauer drüberschaun zwecks verständnis
//						model.getCells()[row][col].setValue(0);
//						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
//							model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
//							sudokuField[col][row].setStyle("-fx-text-fill: red");
//							model.setGameState(Gamestate.INCORRECT);
////							model.getCells()[row][col].setValue(0);
//						}
//					}
//				}
//			}
//		}
//	}

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
			scene.getGameLabel().setText(model.getGameText());
		}
	}

	public void switchToMainMenu(ActionEvent e) {
		GUI.getStage().setScene(GUI.getMainMenu());
	}

	// speicher funktionen
	public void loadIntoSudokuField(JSONArray json, JSONArray json2) {
		Iterator<String> iterator = json.iterator();
		Iterator<Boolean> booleanIterator = json2.iterator();

		for (SudokuField[] sss : scene.getTextField()) {
			for (SudokuField field : sss) {
				field.setText(iterator.next());
				if (booleanIterator.next() == true) {
					field.setDisable(true);
				} else {
					field.setDisable(false);
				}

			}
		}

		model.setUpLogicArray();

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[row][col].getText().equals("") && !sudokuField[row][col].getText().equals("-1")) {

					model.setCell(col, row, Integer.parseInt(sudokuField[row][col].getText()));
				} else {
					model.setCell(col, row, 0);

				}

			}

		}

	}

	public void saveGame(ActionEvent e) {

		JSONObject jsonFile = storage.convertToJSON(file);
		JSONArray jsonArray = (JSONArray) jsonFile.get("games");

		SudokuSaveModel sudokuSave = new SudokuSaveModel();

		ArrayList<String> gameArray = new ArrayList<>();
		ArrayList<Boolean> playableArray = new ArrayList<>();

		sudokuSave.type = scene.getGameType();
		for (SudokuField[] sss : scene.getTextField()) {
			for (SudokuField field : sss) {

				gameArray.add(field.getText());
				playableArray.add(field.isDisable());
			}
		}

		calculateGameTime();

		JSONObject newJSONGameData = new JSONObject();

		if (gameIDexists()) {
			JSONArray savedGamesArray = (JSONArray) storage.convertToJSON(file).get("games");
			for (int i = 0; i < savedGamesArray.size(); i++) {
				JSONObject overwrittenGame = (JSONObject) savedGamesArray.get(i);
				if ((int) (long) overwrittenGame.get("gameID") == gameIDhelper) {

					System.out.println("hiiiiiiiiiiiiii");
					overwrittenGame.remove("gameNumbers");
					overwrittenGame.remove("playAble");

					overwrittenGame.put("gameNumbers", gameArray);
					overwrittenGame.put("playAble", playableArray);
					jsonArray.remove(i);
					jsonArray.add(overwrittenGame);
				}
			}
		} else {

			newJSONGameData.put("type", scene.getGameType());
			newJSONGameData.put("gameNumbers", gameArray);
			newJSONGameData.put("playAble", playableArray);
			newJSONGameData.put("difficulty", "easy");
			newJSONGameData.put("points", model.getgamePoints());
			newJSONGameData.put("gameState", model.getGameState());
			newJSONGameData.put("minutesPlayed", model.getMinutesPlayed());
			newJSONGameData.put("secondsPlayed", model.getSecondsPlayed());
			jsonArray.add(newJSONGameData);

			newJSONGameData.put("gameID", getLastGameID(file) + 1);
			gameID++;

		}
		jsonFile.put("games", jsonArray);

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(file, jsonFile);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public int getLastGameID(File file) {
		JSONParser parser = new JSONParser();

		int gameId = 0;

		try {
			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray gameArray = (JSONArray) jsonObject.get("games");

			JSONObject helper = (JSONObject) gameArray.get(gameArray.size() - 1);

			gameId = (int) (long) helper.get("gameID");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(gameId);

		if (gameId == 0)
			return 0;
		else
			return gameId;

	}

	public boolean gameIDexists() {
		JSONObject obj = storage.convertToJSON(file);

		JSONArray gameArray = (JSONArray) obj.get("games");

		for (int i = 0; i < gameArray.size(); i++) {
			JSONObject helper = (JSONObject) gameArray.get(i);
			int id = (int) (long) helper.get("gameID");
//		System.out.println(id);
//		System.out.println(model.getGameID());
			if (id == model.getGameID()) {
				gameIDhelper = id;
				return true;
			}
		}
		return false;
	}

	public void setModelID(int id) {
		model.setGameID(id);
	}

	public void setUpLogic(ActionEvent e) {
		model.setUpLogicArray();
	}

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
		if (scene.getDifficulty() == 3)
			difficulty = "hard";
		if (scene.getDifficulty() == 5)
			difficulty = "medium";
		if (scene.getDifficulty() == 7)
			difficulty = "easy";
		if (scene.getDifficulty() == 0)
			difficulty = "manual";
		return difficulty;
	}

	public void setModellStartTime() {
		model.setStartTime(System.currentTimeMillis());
	}

	public void setLoadedMinutes(long loadedMinutes) {
		this.loadedMinutes = loadedMinutes;
	}

	public long getLoadedMinutes() {
		return loadedMinutes;
	}

	public void setLoadedSeconds(long loadedSeconds) {
		this.loadedSeconds = loadedSeconds;
	}

	public long getLoadedSeconds() {
		return loadedSeconds;
	}

//test
}
