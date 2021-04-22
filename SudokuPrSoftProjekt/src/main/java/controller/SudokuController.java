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
		this.model = new SudokuLogic(Gamestate.OPEN,0.0,false);
		fields = scene.getTextField();
		
	}
	

public void createGameHandler(ActionEvent e) {
		createGame();
	}
	
	
	public void enableEditHandler(ActionEvent e) {
		
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
					if(fields[i][j].getText().equals(""))
						fields[i][j].setDisable(false);
				}
			}
	}
		
	
	public void easyHandler(ActionEvent e) {
		MainMenuController.difficulty = 100;
	}
		
	public void mediumHandler(ActionEvent e) {
		MainMenuController.difficulty = 5;
	}
		
	public void hardHandler(ActionEvent e) {
		MainMenuController.difficulty = 1;
	}
		
		
	public void newGameHandler(ActionEvent e) {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fields[i][j].clear();
				fields[i][j].setText("");
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



	
public void createGame() {
		
		model.setUpLogicArray();
		model.createSudoku();
		model.difficulty(MainMenuController.difficulty);
		
		
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				
				
				String number = Integer.toString(model.getCells()[j][i].getValue());
				if(fields[i][j].getText().equals("") && !number.equals("0")) {
				fields[i][j].setText(number);
				
				
				}
			
			}
		
		}
		
		model.solveSudoku();
		model.printCells();
	}
	
	
}
	
	
	