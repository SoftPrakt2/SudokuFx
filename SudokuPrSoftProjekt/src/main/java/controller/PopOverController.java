package controller;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.NewGamePopUp;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

public class PopOverController extends ModeController {

	public PopOverController(NewGamePopUp popup) {
		super(popup);

	}
	
	
	@Override
	public void handleToSudoku(ActionEvent e) {
		model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
		gameScene = new SudokuGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
	}
	
	
	@Override
	public void handleToSamurai(ActionEvent e) {
		model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
		gameScene = new SamuraiGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
	}
	
	
	@Override
	public void handleToFreeForm(ActionEvent e) {
		model = new FreeFormLogic(Gamestate.OPEN, 0, 0, false);
		gameScene = new FreeFormGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
	}

	public void handleHard(ActionEvent e) {
		model.setDifficulty(3);
		gameScene.createNumbers();
		alignStage();
	}

	public void handleEasy(ActionEvent e) {
		model.setDifficulty(7);
		gameScene.createNumbers();
		alignStage();
	}

	public void handleMedium(ActionEvent e) {
		model.setDifficulty(5);
		gameScene.createNumbers();
		alignStage();
	}

	public void handleManual(ActionEvent e) {
		model.setDifficulty(0);
		gameScene.createNumbers();
		alignStage();
	}

	public void alignStage() {
		GUI.getStage().setHeight(gameScene.getHeight());
		GUI.getStage().setWidth(gameScene.getWidth());
		GUI.getStage().getScene().setRoot(gameScene.getPane());
		
	}

}
