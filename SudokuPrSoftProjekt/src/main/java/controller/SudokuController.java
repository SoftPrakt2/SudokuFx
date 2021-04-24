package controller;

import java.io.File;

import application.BasicGameBuilder;
import application.OverviewStage;
import application.SudokuField;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuLogic;

public class SudokuController extends BasicController {

	BasicGameBuilder scene;

	BasicGameLogic model;
	SudokuField[][] fields;

	public SudokuController(BasicGameBuilder scene) {
		this.scene = scene;
		this.model = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		fields = scene.getTextField();

	}

	public void createGameHandler(ActionEvent e) {
		createGame();
	}

	public void newGameHandler(ActionEvent e) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fields[i][j].clear();
				fields[i][j].setText("");
				fields[i][j].setDisable(false);
			}
		}
		scene.setGameTime(10);
	}

	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!fields[i][j].isDisabled())
					fields[i][j].clear();

			}
		}
	}

	public void manuelDoneHandler(ActionEvent e) {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				model.getCells()[i][j].setValue(0);

			}
		}

		boolean help = true;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				if (!fields[i][j].getText().equals("")) {
					fields[i][j].getText();
					if (model.valid(j, i, Integer.parseInt(fields[i][j].getText()))) {
						fields[i][j].setDisable(true);
						model.setCell(j, i, Integer.parseInt(fields[i][j].getText()));

					} else {
						help = false;
					}
				}
			}
		}
		if (!help) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					model.getCells()[i][j].setValue(0);
					fields[i][j].setDisable(false);
				}
			}
		}

		model.printCells();

		model.solveSudoku();
		model.printCells();
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
		model.solveSudoku();

		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
				if (!sudokuField[i][j].getText().equals(Integer.toString(model.getCells()[j][i].getValue()))
						&& !sudokuField[i][j].getText().equals("")) {
					sudokuField[i][j].setStyle("-fx-text-fill: red");
					result = false;
				}
				if (sudokuField[i][j].getText().equals(""))
					result = false;

				 if (sudokuField[i][j].getText().equals("")) {
					sudokuField[i][j].setStyle("-fx-text-fill: black");
				}  if(sudokuField[i][j].getText().equals(Integer.toString(model.getCells()[j][i].getValue()))){
					sudokuField[i][j].setStyle("-fx-text-fill: green");
					sudokuField[i][j].setDisable(true);
				}
			}
		}
		return result;
	}

	public void autoSolveHandler(ActionEvent e) {
		model.solveSudoku();
		model.printCells();
		connectArrays(fields);

	}

	public void checkHandler(ActionEvent e) {
		model.solveSudoku();
		boolean gameState = compareResult(fields);

		if (gameState) {
			scene.setEndTime(System.currentTimeMillis());
			scene.setGameTime((scene.getEndTime() - scene.getStartTime()) / 1000);

			if (scene.getGameTime() < 60) {

				scene.getGameLabel().setText("Congratulations you won! Points: Points: " + scene.getGamePoints()
						+ " Time: " + scene.getGameTime() + "s");
			} else {
				long differenz = scene.getGameTime() / 60;
				long sek = differenz % 60;
				scene.getGameLabel().setText("Congratulations you won! Points: " + scene.getGamePoints() + "Time: "
						+ differenz + "min" + sek + " sek");
			}
		} else if (!gameState) {
			scene.getGameLabel().setText("Sorry your Sudoku is not correct yet");
			if (scene.getGamePoints() > 0)
				scene.setGamePoints(scene.getGamePoints() - 1);
		}
	}

	// test für speicher und lade sachen, sicher besser in anderer klasse
	FileChooser fileChooser = new FileChooser();

	public void saveHandler(ActionEvent e) {

		fileChooser.setInitialDirectory(new File("d:/sudoku"));
		fileChooser.setInitialFileName("sudoku");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));

		// File file= fileChooser.showSaveDialog(Main.getStage());

//	try {
//		FileWriter filew = new FileWriter(file);
//		BufferedWriter buf = new BufferedWriter(filew);
//		buf.write("test");
//		buf.close();
//	} catch (IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//	
//	fileChooser.setInitialDirectory(file.getParentFile());
//	

	}

	public void createGame() {

		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(MainMenuController.difficulty);

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {

				String number = Integer.toString(model.getCells()[j][i].getValue());
				if (fields[i][j].getText().equals("") && !number.equals("0")) {
					fields[i][j].setText(number);

				}

			}

		}

		enableEdit();

		model.solveSudoku();
		model.printCells();
	}

	public void enableEdit() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				if (fields[i][j].getText().equals("")) {
					fields[i][j].setDisable(false);

				} else {
					fields[i][j].setDisable(true);
				}
			}
		}

	}
}
