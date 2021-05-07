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
	long loadedSeconds;
    long loadedMinutes;

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

					// vielleicht unnötig
					// model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));

					if (!model.getCells()[row][col].getIsReal()) {
						// nochmal genauer drüberschaun zwecks verständnis
						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {

							model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
							sudokuField[col][row].setStyle("-fx-text-fill: red");
							model.setGameState(Gamestate.INCORRECT);
							model.getCells()[row][col].setValue(0);
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
	 * Löst sofern keine Konflikte vorhanden sind das derzeitige Spielfeld Fals
	 * Konflikte vorhanden sind werden diese markiert und der Benutzer wird darauf
	 * hingewiesen
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
            model.printCells();
            for (int row = 0; row < sudokuField.length; row++) {
                for (int col = 0; col < sudokuField[row].length; col++) {
                    if (model.getCells()[row][col].getValue() == 0) {
                        resetHandler(e);
                        System.out.println("fuck you");
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
                    }
                    //nochmal drüber schaun
//                    else {
//
//                    //    model.getCells()[row][col].setValue(0);
                //    }
                }
            }
            //mögliches duplikat wsl löschen
//            for (int row = 0; row < sudokuField.length; row++) {
//                for (int col = 0; col < sudokuField[row].length; col++) {
//                    if (!sudokuField[col][row].getText().equals("") && !sudokuField[col][row].getText().equals("-")) {
//                        model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
//                    }
//                }
//            }
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

        if (gameState && numberCounter == sudokuField.length * sudokuField.length) {
            long timeHelp = calculateGameTime();
            model.setGameState(Gamestate.DONE);
            if (timeHelp < 60) {
                scene.getGameLabel().setText(model.getGameText() +" Points: " + model.getgamePoints() + " Time: "
                        + model.getSecondsPlayed() + " sek");
            } else {

                scene.getGameLabel().setText(model.getGameText() + " Points: " + model.getgamePoints() + " Time: "
                        + model.getMinutesPlayed() + " min " + model.getSecondsPlayed() + " s ");
            }

        } else if (!gameState || numberCounter != sudokuField.length * sudokuField.length) {
            scene.getGameLabel().setText(model.getGameText());
            if (model.getgamePoints() > 0)
                model.setGamePoints(model.getgamePoints() - 1);
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
        time+= loadedMinutes*60 + loadedSeconds;
        model.setSecondsPlayed(time);
        if(time > 60) {
            model.setMinutesPlayed(time/60);
            model.setSecondsPlayed(time%60);
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
                //überprüfung: !sudokuField[i][j].getText().equals("") wird vl nicht benötigt
                if ((!sudokuField[i][j].getText().equals("") || !sudokuField[i][j].getText().equals("-1"))
                        && !number.equals("0")) {
                    sudokuField[i][j].setText(number);
                    sudokuField[i][j].setPlayable(false);
                }
            }
        }
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
				if (model.getCells()[j][i].getValue()==0) {
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
		GUI.getStage().setScene(GUI.getMainMenu().getScene());
	}

//test
}
