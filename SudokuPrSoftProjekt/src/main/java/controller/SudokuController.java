package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.SamuraiGameBuilder;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuLogic;


public class SudokuController extends BasicController {
	
	BasicGameBuilder scene;


	int difficulty = 0;
	BasicGameLogic model;
	SudokuField[][] fields;
	
	public SudokuController(BasicGameBuilder scene) {
		this.scene = scene;
		this.model = new SudokuLogic(Gamestate.OPEN,0.0,false);
		fields = scene.getTextField();
	}
	
	
	
public void createGameHandler(ActionEvent e) {
		
		System.out.println("hello");
		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(difficulty);
		
		
		
		
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				
				
				String number = Integer.toString(model.getCells()[j][i].getValue());
				if(fields[i][j].getText().equals("0")) {
				fields[i][j].setText(number);
				
				
				}
				//scene.startTime = System.currentTimeMillis();
			
			}
		
		}
		
		model.solveSudoku();
		model.printCells();
	}
	
	
	public void enableEditHandler(ActionEvent e) {
		
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
					if(fields[i][j].getText().equals("0"))
						fields[i][j].setDisable(false);
				}
			}
	}
		
	
		public void easyHandler(ActionEvent e) {
			difficulty = 100;
		}
		
	public void mediumHandler(ActionEvent e) {
		difficulty = 5;
	}
		
	public void hardHandler(ActionEvent e) {
		difficulty = 1;
	}
		
		
	public void newGameHandler(ActionEvent e) {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fields[i][j].clear();
				fields[i][j].setText("0");
			}
		}
				
		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(difficulty);
		
				for (int x = 0; x < 9; x++) {
					for (int y = 0; y < 9; y++) {
				String number = Integer.toString(model.getCells()[y][x].getValue());
					if(fields[x][y].getText().equals("0")) {
						fields[x][y].setText(number);
					
				}
					}
				}
	
	
	}
	
	
	
	
	public void autoSolveHandler(ActionEvent e) {
		model.solveSudoku();
		model.printCells();
		model.connectArrays(fields);
		
	}
		
	public void checkHandler(ActionEvent e) {
		model.solveSudoku();
		model.compareResult(fields);
	}	
	

	
	
	
	//setter für difficulty
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
		
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//test für speicher und lade sachen, sicher besser in anderer klasse
	
	FileChooser fileChooser = new FileChooser();
	
	
	
	public void saveHandler(ActionEvent e) {
		
		fileChooser.setInitialDirectory(new File("d:/sudoku"));
		fileChooser.setInitialFileName("sudoku");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file","*.txt"));
		
		
		
	//File file= fileChooser.showSaveDialog(Main.getStage());
		
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



	
	
	
	
	
	
}
	
	
	