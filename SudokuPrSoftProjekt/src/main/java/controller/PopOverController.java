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
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

public class PopOverController {

		Scene playScene;

		BasicGameBuilder game;
		BasicGameLogic model;
		
		PopOver popover;
	

		public int difficulty;
		
		public PopOverController(PopOver popover) {
			this.popover = popover;
		}

		/**
		 * 
		 * Instanziiert den BasicGameBuilder als SudokuGameBuilder und ladet die SudokuScene
		 */
		public void handleToSudoku(ActionEvent e) {
			model = new SudokuLogic(Gamestate.OPEN,0,0,false);
			game = new SudokuGameBuilder(model);
			playScene = game.initializeScene();
		}
		

		/**
		 * 
		 * Instanziiert den BasicGameBuilder als SamuraiGameBuilder und ladet die SamuraiScene
		 */
		public void handleToSamurai(ActionEvent e) {
			model = new SamuraiLogic(Gamestate.OPEN,0,0,false);
			game = new SamuraiGameBuilder(model);
			playScene = game.initializeScene();
		}

		/**
		 * 
		 * Instanziiert den BasicGameBuilder als FreeFormGameBuilder und ladet die FreeFormScene
		 */
		public void handleToFreeForm(ActionEvent e) {
			game = new FreeFormGameBuilder(new SudokuLogic(Gamestate.OPEN,0,0,false));
			playScene = game.initializeScene();
		}

		
		public void handleHard(ActionEvent e) {
			if(game instanceof SamuraiGameBuilder) {
				System.out.println("Test");
				model.setDifficulty(4);
			}
			model.setDifficulty(3);
			game.createNumbers();
			GUI.getStage().setScene(playScene);
		}
		
		public void handleEasy(ActionEvent e) {
			model.setDifficulty(7);
			game.createNumbers();
			GUI.getStage().setScene(playScene);
		}

		public void handleMedium(ActionEvent e) {
			model.setDifficulty(5);
			game.createNumbers();
			GUI.getStage().setScene(playScene);
		}
		
		public void handleManual(ActionEvent e) {
			model.setDifficulty(0);
			game.createNumbers();
			game.getDoneButton().setVisible(true);
			GUI.getStage().setScene(playScene);
		}
		
		
		
		
		
		
		
		
		
}
