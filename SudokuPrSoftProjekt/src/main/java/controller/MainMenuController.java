package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.SudokuField;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.BasicGameLogic;


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
	
	
	
	

}
