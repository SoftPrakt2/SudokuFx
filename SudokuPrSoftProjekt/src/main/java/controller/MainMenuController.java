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

/**
 * 
 * MainMenuController regelt die Seuterfunktionen des MainMenüs
 * Entsprechend den ausgewählten Spielknöpfen werden die jeweiligen GameBuilder instanziiert.
 *
 */
public class MainMenuController {

	MainMenu menu;
	Scene playScene;

	BasicGameBuilder game;
	
	OverviewStage overview = new OverviewStage();
	//Stage overviewStage = overview.showOverview("Played", "Played");

	public int difficulty;
	
	public MainMenuController(MainMenu menu) {
		this.menu = menu;
	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als SudokuGameBuilder und ladet die SudokuScene
	 */
	public void handleToSudoku(ActionEvent e) {
		game = new SudokuGameBuilder();
		playScene = game.initializeScene();
	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als SamuraiGameBuilder und ladet die SamuraiScene
	 */
	public void handleToSamurai(ActionEvent e) {
		game = new SamuraiGameBuilder();
		playScene = game.initializeScene();
	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als FreeFormGameBuilder und ladet die FreeFormScene
	 */
	public void handleToFreeForm(ActionEvent e) {
		game = new FreeFormGameBuilder();
		playScene = game.initializeScene();
	}

	/**
	 * 
	 * Instanziiert ein StorageObjekt und ruft die Scene des Storage-Objekts auf
	 */
	public void handleToLoad(ActionEvent e) {
		//overviewStage.show();
	}

	/**
	 * 
	 * Schließt das Programm
	 */
	public void handleExit(ActionEvent e) {
		GUI.getStage().close();
	}

	/**
	 * 
	 * Stellen die schwierigkeit des zuvor ausgewählten Sudoku-Spiels ein
	 */
	public void handleHard(ActionEvent e) {
		if(game instanceof SamuraiGameBuilder) {
			System.out.println("Test");
			game.setDifficulty(4);
		}
		game.setDifficulty(3);
	}
	
	public void handleEasy(ActionEvent e) {
		game.setDifficulty(7);
	
	}

	public void handleMedium(ActionEvent e) {
		game.setDifficulty(5);
	}
	
	public void handleManual(ActionEvent e) {
		game.setDifficulty(0);
	}

	/**
	 * 
	 * Initialisiert das Spiel mit den gewünschten Einstellungen
	 * Befüllt das jeweilige Spielfeld der schwierigkeit entsprechend
	 * Setzt den Startzeitpunkt des Spiels auf 0
	 */
	public void handleGameStart(ActionEvent e) {
			game.createNumbers();
			GUI.getStage().setScene(playScene);
			menu.getPlayModeToggle().getSelectedToggle().setSelected(false);
			menu.getDifficultyToggle().getSelectedToggle().setSelected(false);
			if(game.getDifficulty() > 0) game.getDoneButton().setDisable(true);
	}

	

}
