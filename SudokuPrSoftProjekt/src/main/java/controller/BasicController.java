package controller;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import logic.BasicGameLogic;

public abstract class BasicController {
	
	

	
	public abstract void createGameHandler(ActionEvent e);
	
//	public void timerHandler(MouseEvent e) {
//		System.out.println("test");
//	}
	
	public abstract void enableEditHandler(ActionEvent e);
	
	public abstract void easyHandler(ActionEvent e);
	
	public abstract void mediumHandler(ActionEvent e);
	
	public abstract void hardHandler(ActionEvent e);
	
	public abstract void newGameHandler(ActionEvent e);
	
	public abstract void checkHandler(ActionEvent e);
	
	public abstract void saveHandler(ActionEvent e);
	
	public abstract void autoSolveHandler(ActionEvent e);
	

	
	public void switchToSamurai(ActionEvent e) {
		SamuraiGameBuilder sam = new SamuraiGameBuilder();
		sam.initializeScene();
		GUI.getStage().setScene(sam.getScene());
		
	}
	
	public void switchToSudoku(ActionEvent e) {
		SudokuGameBuilder free = new SudokuGameBuilder();
		free.initializeScene();
		GUI.getStage().setScene(free.getScene());
	}
	
	
	public void switchToFreeForm(ActionEvent e) {
		FreeFormGameBuilder free = new FreeFormGameBuilder();
		free.initializeScene();
		GUI.getStage().setScene(free.getScene());
	}

	
}
