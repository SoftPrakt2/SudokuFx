package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.BasicGameBuilder;
import application.GUI;
import application.MainMenu;
import application.OverviewStage;
import application.Storage;
import application.SudokuField;
import application.SudokuGameBuilder;
import application.SudokuSaveModel;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuLogic;

public class GameController {

	BasicGameBuilder scene;
	BasicGameLogic model;
	SudokuField[][] sudokuField;
	static int count = 0;

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

	public void createGameHandler(ActionEvent e) {
		createGame(scene.getDifficulty());
		scene.setStartTime(System.currentTimeMillis());
		scene.setGamePoints(10);
		scene.getGameLabel().setText("Game ongoing!");
		model.setGameState(Gamestate.OPEN);
	}

	public void newGameHandler(ActionEvent e) {
		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				sudokuField[i][j].clear();
				sudokuField[i][j].setText("");
				sudokuField[i][j].setDisable(false);
			}
		}
		scene.setStartTime(System.currentTimeMillis());
		scene.setGamePoints(10);
		scene.getGameLabel().setText("");
		model.setGameState(Gamestate.OPEN);
		scene.getCheckButton().setDisable(false);
		count = 0;
	}

	public void resetHandler(ActionEvent e) {
        for (int i = 0; i < sudokuField.length; i++) {
            for (int j = 0; j < sudokuField[i].length; j++) {
                if (!sudokuField[i][j].isDisabled())
                    sudokuField[i][j].clear();

            }
        }
        scene.setStartTime(System.currentTimeMillis());
        scene.setGamePoints(10);
        scene.getGameLabel().setText("Game ongoing!");
    }

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

//		model.printCells();
//		model.solveSudoku();
//		model.printCells();
	}

	// in abstrakte klasse
	public void connectArrays(SudokuField[][] sudokuField) {
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				sudokuField[row][col].setText(Integer.toString(model.getCells()[col][row].getValue()));
			}
		}
	}

	// same
	public boolean compareResult(SudokuField[][] sudokuField) {
		boolean result = true;
		count = 0;

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
					count++;
					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));

					if (!sudokuField[col][row].isDisabled()) {
						model.getCells()[row][col].setValue(0);
						if (!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
//	                            model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
							model.setCell(row, col, Integer.parseInt(sudokuField[col][row].getText()));
							sudokuField[col][row].setStyle("-fx-text-fill: red");
							result = false;
						}
//	                        if(model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
//	                            result = true;
//	                        }
					}
				}
			}
		}
		return result;
	}

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
//			model.printCells();
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

	public void checkHandler(ActionEvent e) {
//      model.solveSudoku();
//      compareResult(sudokuField);
		boolean gameState = compareResult(sudokuField);
		long gameTime;
		long sek;
		long endTime = System.currentTimeMillis();

		if (gameState && count == sudokuField.length * sudokuField.length) {
			long diff = (endTime - scene.getStartTime()) / 1000;
			model.setGameState(Gamestate.DONE);
			scene.getCheckButton().setDisable(true);
			scene.setSecondsPlayed(diff);
			System.out.println(scene.getMinPlayed());
			if (diff < 60) {
				scene.getGameLabel().setText("Congratulations you won! Points: " + scene.getGamePoints() + " Time: "
						+ scene.getSecondsPlayed() + " sek");
			} else {
				scene.setMinPlayed(diff / 60);
				scene.setSecondsPlayed(diff % 60);
				scene.getGameLabel().setText("Congratulations you won! Points: " + scene.getGamePoints() + " Time: "
						+ scene.getMinPlayed() + " min " + scene.getSecondsPlayed() + " s ");
			}

		} else if (!gameState || count != sudokuField.length * sudokuField.length) {
			scene.getGameLabel().setText("Sorry your Sudoku is not correct yet");
			if (scene.getGamePoints() > 0)
				scene.setGamePoints(scene.getGamePoints() - 1);
		}

	}



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
//		model.solveSudoku();
		model.printCells();
	}

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

		// so bleibt das spiel in der scene wenn man einmal auf hauptmen� geht und dann
		// wieder zur�ck
		GUI.getStage().setScene(GUI.getMainMenu().getScene());

		// wenn man will das es neu startet m�sste man mainmenu initialize machen
	}



}


