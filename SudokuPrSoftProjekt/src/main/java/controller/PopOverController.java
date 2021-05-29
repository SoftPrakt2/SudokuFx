package controller;

import org.controlsfx.control.PopOver;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;
import logic.Cell;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

public class PopOverController {

	private Scene playScene;

	
	private BasicGameLogic standardlogic;

	private BasicGameBuilder standard;
	
	private PopOver popover;

	private boolean oldGame;

	private int difficulty;
	
	public PopOverController(BasicGameBuilder standard, BasicGameLogic standardlogic) {
		this.standard = standard;
		this.standardlogic = standardlogic;
	}
	
	

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als SudokuGameBuilder und ladet die
	 * SudokuScene
	 */
	public void handleToSudoku(ActionEvent e) {

		standardlogic = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
		standard = new SudokuGameBuilder(standardlogic);
		standard.initializeGame();
	//	game2.getPane().setCenter(game.createBoard());
		
	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als SamuraiGameBuilder und ladet die
	 * SamuraiScene
	 */
	public void handleToSamurai(ActionEvent e) {
		standardlogic = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
		standard = new SamuraiGameBuilder(standardlogic);
	//	standard.createBoard();
		standard.initializeGame();

	}
	

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als FreeFormGameBuilder und ladet die
	 * FreeFormScene
	 */
	public void handleToFreeForm(ActionEvent e) {
	
	}

	public void handleHard(ActionEvent e) {
		
		standardlogic.setDifficulty(5);
		
		
		standard.getPane().setCenter(standard.createBoard());
		
		standard.createNumbers();
		
		GUI.getStage().setHeight(standard.getHeight());
		GUI.getStage().setWidth(standard.getWidth());
		GUI.getStage().getScene().setRoot(standard.getPane());
	}

	public void handleEasy(ActionEvent e) {
		standardlogic.setDifficulty(7);
		standard.createNumbers();
		
		GUI.getStage().getScene().setRoot(standard.getPane());
	}

	public void handleMedium(ActionEvent e) {
		standardlogic.setDifficulty(5);
		standard.createNumbers();
		GUI.getStage().setHeight(standard.getHeight());
		GUI.getStage().setWidth(standard.getWidth());
		GUI.getStage().getScene().setRoot(standard.getPane());
	}

	public void handleManual(ActionEvent e) {
		standardlogic.setDifficulty(0);
		standard.createNumbers();
		standard.getDoneButton().setVisible(true);
		GUI.getStage().setHeight(standard.getHeight());
		GUI.getStage().setWidth(standard.getWidth());
		GUI.getStage().getScene().setRoot(standard.getPane());
	}

}
