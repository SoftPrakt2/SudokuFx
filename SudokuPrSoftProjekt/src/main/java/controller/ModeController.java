package controller;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

/**
 * 
 * MainMenuController regelt die Seuterfunktionen des MainMenüs Entsprechend den
 * ausgewählten Spielknöpfen werden die jeweiligen GameBuilder instanziiert.
 *
 */

public class ModeController {

	private MainMenu menu;
	protected BasicGameBuilder game;
	protected BasicGameLogic model;



	public <E extends MainMenu> ModeController(E menu) {
		this.menu = menu;
	}
	

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als SudokuGameBuilder und ladet die
	 * SudokuScene
	 */
	public void handleToSudoku(ActionEvent e) {
		model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
		game = new SudokuGameBuilder(model);
		game.initializeGame();

	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als SamuraiGameBuilder und ladet die
	 * SamuraiScene
	 */
	public void handleToSamurai(ActionEvent e) {
		model = new SamuraiLogic(Gamestate.OPEN, 0, 0, false);
		game = new SamuraiGameBuilder(model);
		game.initializeGame();
	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als FreeFormGameBuilder und ladet die
	 * FreeFormScene
	 */
	public void handleToFreeForm(ActionEvent e) {
		game = new FreeFormGameBuilder(new SudokuLogic(Gamestate.OPEN, 0, 0, false));
		game.initializeGame();
	}

	/**
	 * 
	 * Instanziiert ein StorageObjekt und ruft die Scene des Storage-Objekts auf
	 */
	public void handleToLoad(ActionEvent e) {
		Storage overview = new Storage();
		overview.createStage();
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
		if (menu.getPlayModeToggle().getSelectedToggle().isSelected())
			model.setDifficulty(3);

	}

	public void handleEasy(ActionEvent e) {
		if (menu.getPlayModeToggle().getSelectedToggle().isSelected())
			model.setDifficulty(7);
	}

	public void handleMedium(ActionEvent e) {
		if (menu.getPlayModeToggle().getSelectedToggle().isSelected())
			model.setDifficulty(5);
	}

	public void handleManual(ActionEvent e) {
		if (menu.getPlayModeToggle().getSelectedToggle().isSelected())
			model.setDifficulty(0);
			game.getDoneButton().setVisible(true);
	}

	/**
	 * 
	 * Initialisiert das Spiel mit den gewünschten Einstellungen Befüllt das
	 * jeweilige Spielfeld der schwierigkeit entsprechend Setzt den Startzeitpunkt
	 * des Spiels auf 0
	 */
	public void handleGameStart(ActionEvent e) {
		game.createNumbers();
		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getPane());
		menu.getPlayModeToggle().getSelectedToggle().setSelected(false);
		menu.getDifficultyToggle().getSelectedToggle().setSelected(false);

		if (game.getDifficulty() > 0)
			game.getDoneButton().setDisable(true);
	}

}
