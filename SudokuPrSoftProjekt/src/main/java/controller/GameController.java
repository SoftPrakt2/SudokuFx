package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import application.BasicGameBuilder;
import application.GUI;
import application.Storage;
import application.SudokuField;
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

	JSONObject myObject = new JSONObject();
	JSONArray testList = new JSONArray();
	JSONArray array = new JSONArray();
	JSONObject game = new JSONObject();
	Storage storage = new Storage();
	FileChooser chooser = storage.setUpFileChooser();

	public GameController(BasicGameBuilder scene, BasicGameLogic model) {
		this.scene = scene;
		this.model = model;
		sudokuField = scene.getTextField();
	}

	/**
	 * 
	 * Erstellt abhängig von der Schwierigkeit ein Sudoku-Spiel
	 * Setzt die Punkte des Spiels auf 10
	 * Setzt GameText auf "Game ongoing"
	 * Setzt GameState auf OPEN
	 */
	public void createGameHandler(ActionEvent e) {
		createGame(scene.getDifficulty());
		model.setGamePoints(10);
		scene.getGameLabel().setText("Game ongoing!");
		model.setGameState(Gamestate.OPEN);
	}

	/**
	 * 
	 * Löscht sämtliche Zahlen aus dem Spielfeld und setzt die verschiedenen Spielstatuse auf den Anfangszustand
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
	}

	/**
	 * 
	 * Löscht Benutzereingaben für das Spiel
	 */
	public void resetHandler(ActionEvent e) {
        for (int i = 0; i < sudokuField.length; i++) {
            for (int j = 0; j < sudokuField[i].length; j++) {
                if (!sudokuField[i][j].isDisabled())
                    sudokuField[i][j].clear();

            }
        }
        model.setStartTime(System.currentTimeMillis());
        model.setGamePoints(10);
        scene.getGameLabel().setText("Game ongoing!");
    }

	/**
	 * 
	 * Fixiert die manuelle Sudokuerstellung des Spielers und übergibt die eingegebenen Zahlen der Logik
	 */
	public void manuelDoneHandler(ActionEvent e) {

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				model.getCells()[i][j].setValue(0);
			}
		}

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
	}

	/**
	 * 
	 * Überprüft ob Konflikte zwischen den dem Benutzer eingegebnen Zahlen herschen
	 * Kennzeichnet diese rot
	 */
	public boolean compareResult(SudokuField[][] sudokuField) {
		boolean result = true;
		numberCounter = 0;

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-")) {
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
				} else {
					model.getCells()[row][col].setValue(0);
				}
			}
		}

		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-")) {
					numberCounter++;
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));

					if (!sudokuField[col][row].isDisabled()) {
						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
							model.setCell(row, col, Integer.parseInt(sudokuField[col][row].getText()));
							sudokuField[col][row].setStyle("-fx-text-fill: red");
							result = false;
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
	 * Löst sofern keine Konflikte vorhanden sind das derzeitige Spielfeld
	 * Fals Konflikte vorhanden sind werden diese markiert und der Benutzer wird darauf hingewiesen
	 */
	public void autoSolveHandler(ActionEvent e) {
		if (compareResult(sudokuField)) {
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					}
				}
			}
			model.solveSudoku();
			model.setGameState(Gamestate.AutoSolved);
			connectArrays(scene.getTextField());
		} else {
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					} else {
						model.getCells()[row][col].setValue(0);
					}
				}
			}
			for (int row = 0; row < sudokuField.length; row++) {
				for (int col = 0; col < sudokuField[row].length; col++) {
					if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-")) {
						model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
					}
				}
			}
			scene.getGameLabel().setText("Please remove the conflicts before autosolving!");
		}
	}

	/**
	 * 
	 * Überprüft ob der derzeitige Stand der Eingagen eine gültige Lösung ist und
	 * gibt abhängig davon unterschiedliche Informationen aus
	 */
	public void checkHandler(ActionEvent e) {

		boolean gameState = compareResult(sudokuField);
		
		long endTime = System.currentTimeMillis();

		if (gameState && numberCounter == sudokuField.length * sudokuField.length) {
			long diff = (endTime - model.getStartTime()) / 1000;
			model.setGameState(Gamestate.DONE);
			scene.getCheckButton().setDisable(true);
			model.setSecondsPlayed(diff);
			System.out.println(model.getMinutesPlayed());
			if (diff < 60) {
				scene.getGameLabel().setText("Congratulations you won! Points: " + model.getgamePoints() + " Time: "
						+ model.getSecondsPlayed() + " sek");
			} else {
				model.setMinutesPlayed(diff / 60);
				model.setSecondsPlayed(diff % 60);
				scene.getGameLabel().setText("Congratulations you won! Points: " + model.getgamePoints() + " Time: "
						+ model.getMinutesPlayed() + " min " + model.getSecondsPlayed() + " s ");
			}

		} else if (!gameState || numberCounter != sudokuField.length * sudokuField.length) {
			scene.getGameLabel().setText("Sorry your Sudoku is not correct yet");
			if (model.getgamePoints() > 0)
				model.setGamePoints(model.getgamePoints() - 1);
		}

	}

	/**
	 * 
	 * Erstellt ein Spiel anhand der eingestellten Schwierigkeit
	 */
	public void createGame(int difficulty) {
		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(difficulty);
		

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				String number = Integer.toString(model.getCells()[j][i].getValue());
				if ((!sudokuField[i][j].getText().equals("") || !sudokuField[i][j].getText().equals("-"))
						&& !number.equals("0")) {
					sudokuField[i][j].setText(number);
				}
			} 
		}
		enableEdit();
		model.printCells();
	}

	/**
	 * Leere Textfelder werden für den Benutzer freigegeben
	 */
	public void enableEdit() {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (sudokuField[i][j].getText().equals("")) {
					sudokuField[i][j].setDisable(false);

				} else {
					sudokuField[i][j].setDisable(true);
				}
			}
		}
	}

	public void hintHandeler(ActionEvent e) {
		model.hint();
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (model.getCells()[row][col].getValue() != 0 && sudokuField[col][row].getText().equals("")
						&& !sudokuField[col][row].getText().equals("-")) {
					String number = Integer.toString(model.getCells()[row][col].getValue());
					sudokuField[col][row].setText(number);
					sudokuField[col][row].setStyle("-fx-text-fill: blue");
					sudokuField[col][row].setFont(Font.font("Verdana", FontWeight.BOLD, 16));
				}
			}
		}
	}

	public void switchToMainMenu(ActionEvent e) {

		// so bleibt das spiel in der scene wenn man einmal auf hauptmenü geht und dann
		// wieder zurück
		GUI.getStage().setScene(GUI.getMainMenu().getScene());

		// wenn man will das es neu startet müsste man mainmenu initialize machen
	}


//test
}


