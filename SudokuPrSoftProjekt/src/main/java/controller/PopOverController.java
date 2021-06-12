package controller;

import application.FreeFormGameBuilder;
import application.NewGamePopUp;
import application.SamuraiGameBuilder;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;


/**
 * This class defines the controller of the {@link application.NewGamePopUp} class
 * The class inherits from the {@link controller.ModeController} class and overrides its methods if needed
 * @author grube
 *
 */
public class PopOverController extends ModeController {
	
	

	public PopOverController(NewGamePopUp popup) {
		super(popup);
	}
	
	
	
	@Override
	public void handleToSudoku(ActionEvent e) {
		setModel(new SudokuLogic(Gamestate.OPEN, 0, 0, false));
		setGameScene(new SudokuGameBuilder(getModel()));
		getGameBuilder().initializeGame();
		enableDifficultyButtons();
	}
	
	@Override
	public void handleToSamurai(ActionEvent e) {
		setModel(new SamuraiLogic(Gamestate.OPEN, 0, 0, false));
		setGameScene(new SamuraiGameBuilder(getModel()));
		getGameBuilder().initializeGame();
		enableDifficultyButtons();
	}

	
	
	
	@Override
	public void handleToFreeForm(ActionEvent e) {
		setModel(new FreeFormLogic(Gamestate.OPEN, 0, 0, false));
		setGameScene(new FreeFormGameBuilder(getModel()));
		getGameBuilder().initializeGame();
		enableDifficultyButtons();
	}

	@Override
	public void handleHard(ActionEvent e) {
		getModel().setDifficulty(3);
		getGameBuilder().createNumbers();
		alignGameWithWindow(getGameBuilder());
	}

	@Override
	public void handleEasy(ActionEvent e) {
		getModel().setDifficulty(7);
		getGameBuilder().createNumbers();
		alignGameWithWindow(getGameBuilder());
	}

	@Override
	public void handleMedium(ActionEvent e) {
		getModel().setDifficulty(5);
		getGameBuilder().createNumbers();
		alignGameWithWindow(getGameBuilder());
	}

	@Override
	public void handleManual(ActionEvent e) {
		getModel().setDifficulty(0);
		getGameBuilder().createNumbers();
		alignGameWithWindow(getGameBuilder());
	}

	
}
