package controller;

import java.io.File;

import application.BasicGameBuilder;
import application.MainMenu;
import application.OverviewStage;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuLogic;

public class SudokuController extends BasicController {

	SudokuGameBuilder scene;

	BasicGameLogic model;
	SudokuField[][] sudokuField;

	public SudokuController(SudokuGameBuilder scene) {
		this.scene = scene;
		this.model = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		sudokuField = scene.getTextField();

	}

	public void createGameHandler(ActionEvent e) {
		createGame(scene.getDifficulty());
	}

	public void newGameHandler(ActionEvent e) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuField[i][j].clear();
				sudokuField[i][j].setText("");
				sudokuField[i][j].setDisable(false);
			}
		}
		scene.setStartTime(System.currentTimeMillis());
		scene.setGamePoints(10);
	}

	public void resetHandler(ActionEvent e) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
			if (!sudokuField[i][j].isDisabled())
				sudokuField[i][j].clear();
				
				
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
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
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
		boolean result = false;
		int count = 0;

        for(int row = 0; row < sudokuField.length; row++) {
            for(int col = 0; col < sudokuField[row].length; col++) {
               if(!sudokuField[col][row].getText().equals("")) {
                   model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
               }
//               else {
//                   model.getCells()[row][col].setValue(0);
//               }
            }
        }

        for(int row = 0; row < sudokuField.length; row++) {
            for(int col = 0; col < sudokuField[row].length; col++) {
                if(!sudokuField[col][row].getText().equals("")) {
                	count++;
                	if(!sudokuField[col][row].isDisabled()) {
                		model.getCells()[row][col].setValue(0);
                        if(!model.valid(row, col, Integer.parseInt(sudokuField[col][row].getText()))) {
//                            model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
                        	model.setCell(row, col, Integer.parseInt(sudokuField[col][row].getText()));
                            sudokuField[col][row].setStyle("-fx-text-fill: red");
                            result = false;
                        }
                        else {
                            sudokuField[col][row].setStyle("-fx-text-fill: black");
                        }
                	}
                }
            }
        }
        if (count == 81 && result) {
        	return true;
        }
        return false;
	}

	public void autoSolveHandler(ActionEvent e) {
		model.solveSudoku();
		model.printCells();
		connectArrays(scene.getTextField());
	}
		
		

	
	
	public void checkHandler(ActionEvent e) {
//		model.solveSudoku();
		boolean gameState = compareResult(scene.getTextField());
		long gameTime;
		long sek;
		long endTime = System.currentTimeMillis();
		
		if(gameState) {
			gameTime = (endTime  - scene.getStartTime())/1000;
			
			if(gameTime < 60) {
				scene.getGameLabel().setText("Congratulations you won! Points: Points: " + scene.getGamePoints()
				+ " Time: " + gameTime + "s");
			} else {
				gameTime = gameTime/60;
				sek = gameTime%60;
				scene.getGameLabel().setText("Congratulations you won! Points: " + scene.getGamePoints() + "Time: "
						+ gameTime + "min" + sek + " sek");
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



	}

	public void createGame(int difficulty) {

		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(difficulty);
		


		for (int i = 0; i < sudokuField.length; i++) {
			for (int j = 0; j < sudokuField[i].length; j++) {
			
					
				String number = Integer.toString(model.getCells()[j][i].getValue());
				if (sudokuField[i][j].getText().equals("") && !number.equals("0")) {
					sudokuField[i][j].setText(number);

				}
				
			}

		}

		enableEdit();
//		model.solveSudoku();
		model.printCells();
	}

	public void enableEdit() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sudokuField[i][j].getText().equals("")) {
					sudokuField[i][j].setDisable(false);

				} else {
					sudokuField[i][j].setDisable(true);
				}
			}
		}
	}

	@Override
	public void hintHandeler(ActionEvent e) {
//		for (int row = 0; row < sudokuField.length; row++) {
//			for (int col = 0; col < sudokuField[row].length; col++) {
//				if (!sudokuField[col][row].getText().equals("")) {
//					model.getCells()[row][col].setValue(Integer.parseInt(sudokuField[col][row].getText()));
//				} else {
//					model.getCells()[row][col].setValue(0);
//				}
//			}
//		}

		model.hint();
		for (int row = 0; row < sudokuField.length; row++) {
			for (int col = 0; col < sudokuField[row].length; col++) {
				if (model.getCells()[row][col].getValue() != 0 && sudokuField[col][row].getText().equals("")) {
					String number = Integer.toString(model.getCells()[row][col].getValue());
					sudokuField[col][row].setText(number);
					sudokuField[col][row].setStyle("-fx-text-fill: blue");
				}

			}
		}
	}
}
