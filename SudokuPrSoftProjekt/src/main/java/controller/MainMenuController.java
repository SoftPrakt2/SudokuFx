package controller;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;


public class MainMenuController {
	
	
	MainMenu menu;
	BasicGameBuilder sudokuGame = new SudokuGameBuilder();
	BasicGameBuilder samuraiGame = new SamuraiGameBuilder();
	BasicGameBuilder freeFormGame = new FreeFormGameBuilder();
	
	
	public MainMenuController(MainMenu menu) {
		this.menu = menu;
		sudokuGame.initializeScene();
		samuraiGame.initializeScene();
		freeFormGame.initializeScene();
	}
	
	
	public void handleToSudoku(ActionEvent e) {
		
		
		GUI.getStage().setScene(sudokuGame.getScene());
	
	}
	
	public void handleToSamurai(ActionEvent e) {
		
		GUI.getStage().setScene(samuraiGame.getScene());
	
	}
	
	public void handleToFreeForm(ActionEvent e) {
		
		GUI.getStage().setScene(freeFormGame.getScene());
	}
	
	
//	public void handleRuleWindow(ActionE)
	

}
