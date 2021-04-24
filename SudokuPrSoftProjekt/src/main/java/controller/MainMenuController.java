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
	SudokuGameBuilder sudokuGame = new SudokuGameBuilder();
	Scene playScene;
	
	BasicGameBuilder samuraiGame = new SamuraiGameBuilder();
	BasicGameBuilder freeFormGame = new FreeFormGameBuilder();
	OverviewStage overview = new OverviewStage();
	Stage overviewStage = overview.showOverview("Played","Played");
	
	String selected;
	
	public static int difficulty;
	
	
	public MainMenuController(MainMenu menu) {
		this.menu = menu;
		
	}
	
	
	public void handleToSudoku(ActionEvent e) {
		selected = "Sudoku";
		playScene = sudokuGame.initializeScene();
	//	sudokuGame.initializeScene();
		//GUI.getStage().setScene(sudokuGame.getScene());
		sudokuGame.setStartTime(System.currentTimeMillis());
	}
		
		
	
	public void handleToSamurai(ActionEvent e) {
		selected = "Samurai";
		playScene = samuraiGame.initializeScene();
		//GUI.getStage().setScene(samuraiGame.getScene());
	
	}
	
	public void handleToFreeForm(ActionEvent e) {
		freeFormGame.initializeScene();
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
	
	public void handleManual(ActionEvent e) {
		difficulty = 0;
		System.out.println(difficulty);
	
//		for(int i = 0; i <9; i++) {
//			for(int j = 0; j <9; j++) {
//				System.out.println(sudokuGame.getTextField()[i][j].getText());
//			}
//		}
	
	}
	
	public void handleTest(ActionEvent e) {
		if(selected.equals("Sudoku")) sudokuGame.createNumbers();
		
		GUI.getStage().setScene(playScene);
	}
	
	
	
	
	public void handleEasy(ActionEvent e) {
		difficulty = 6;
		System.out.println(difficulty);
	}
	
	public void handleMedium(ActionEvent e) {
		difficulty = 100;
	}
	
	


}
