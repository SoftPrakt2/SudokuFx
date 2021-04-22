package controller;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.OverviewStage;
import application.SamuraiGameBuilder;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SudokuLogic;


public class MainMenuController {
	
	
	MainMenu menu;
	BasicGameBuilder sudokuGame = new SudokuGameBuilder();
	BasicGameBuilder samuraiGame = new SamuraiGameBuilder();
	BasicGameBuilder freeFormGame = new FreeFormGameBuilder();
	OverviewStage overview = new OverviewStage();
	Stage overviewStage = overview.showOverview("Played","Played");
	
	
	
	
	
	
	public static int difficulty;
	
//	int counter = 0;
	
	public MainMenuController(MainMenu menu) {
		this.menu = menu;
		

	}
	
	
	public void handleToSudoku(ActionEvent e) {
	
		
		sudokuGame.initializeScene();
		GUI.getStage().setScene(sudokuGame.getScene());
		
		

	}
		
		
		
		
		
		
		
		
		
		
	
	
	public void handleToSamurai(ActionEvent e) {
		
		samuraiGame.initializeScene();
		GUI.getStage().setScene(samuraiGame.getScene());
	
	}
	
	public void handleToFreeForm(ActionEvent e) {
		
		GUI.getStage().setScene(freeFormGame.getScene());
	}
	
	
	public void handleToLoad(ActionEvent e) {
		overviewStage.show();
	}
	
	public void handleExit(ActionEvent e) {
		GUI.getStage().close();
	}
	
	
	public void handleHard(ActionEvent e) {
		difficulty = 1;
		System.out.println(difficulty);
	
	}
	
	public void handleEasy(ActionEvent e) {
		difficulty = 100;
		System.out.println(difficulty);
	}
	
	public void handleMedium(ActionEvent e) {
		difficulty = 30;
	}
	
	
//	public void handleRuleWindow(ActionE)
	

}
