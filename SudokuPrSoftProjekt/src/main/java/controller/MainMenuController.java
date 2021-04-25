package controller;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.OverviewStage;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {

	MainMenu menu;
	Scene playScene;

	BasicGameBuilder game;
	
	OverviewStage overview = new OverviewStage();
	Stage overviewStage = overview.showOverview("Played", "Played");

	


	public int difficulty;
	

	public MainMenuController(MainMenu menu) {
		this.menu = menu;

	}


	public void handleToSudoku(ActionEvent e) {
		game = new SudokuGameBuilder();
		playScene = game.initializeScene();
	}

	public void handleToSamurai(ActionEvent e) {
		game = new SamuraiGameBuilder();
		playScene = game.initializeScene();
	}

	public void handleToFreeForm(ActionEvent e) {
		game = new FreeFormGameBuilder();
		playScene = game.initializeScene();
		
	}

	public void handleToLoad(ActionEvent e) {
		overviewStage.show();
	}

	public void handleExit(ActionEvent e) {
		GUI.getStage().close();
	}

	public void handleHard(ActionEvent e) {
		game.setDifficulty(1);
	}
	
	public void handleEasy(ActionEvent e) {
		game.setDifficulty(6);
	
	}

	public void handleMedium(ActionEvent e) {
		game.setDifficulty(100);
	}
	

	public void handleManual(ActionEvent e) {
		game.setDifficulty(0);
	}

	public void handleGameStart(ActionEvent e) {
			game.createNumbers();
			game.setStartTime(System.currentTimeMillis());
			GUI.getStage().setScene(playScene);
	}

	

}
