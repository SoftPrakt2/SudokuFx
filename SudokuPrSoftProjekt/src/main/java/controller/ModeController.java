package controller;

import java.io.IOException;
import java.util.stream.Stream;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuGameBuilder;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
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

	protected int difficulty;

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
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
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
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * 
	 * Instanziiert den BasicGameBuilder als FreeFormGameBuilder und ladet die
	 * FreeFormScene
	 */
	public void handleToFreeForm(ActionEvent e) {
		model = new FreeFormLogic(Gamestate.OPEN, 0, 0, false);
		game = new FreeFormGameBuilder(model);
		game.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * 
	 * Instanziiert ein StorageObjekt und ruft die Scene des Storage-Objekts auf
	 * 
	 * @throws IOException
	 */
	public void handleToLoad(ActionEvent e) throws IOException {
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
		if (menu.getDifficultyToggle().getSelectedToggle().isSelected() ) {
			difficulty = 3;
			setGameButtonState();
			enablePlayButton();
			
		}
	}

	public void handleEasy(ActionEvent e) {
		difficulty = 7;
		if (menu.getDifficultyToggle().getSelectedToggle().isSelected()) {
			
			setGameButtonState();
			enablePlayButton();
		}
	}

	public void handleMedium(ActionEvent e) {
		if (menu.getDifficultyToggle().getSelectedToggle().isSelected()) {
			difficulty = 5;
			setGameButtonState();
			enablePlayButton();
		}
	}

	
	
	
	public void handleManual(ActionEvent e) {
		if (menu.getDifficultyToggle().getSelectedToggle() != null && menu.getDifficultyToggle().getSelectedToggle().isSelected()) {
			difficulty = 0;
			if (model instanceof FreeFormLogic) {
				model.setGameState(Gamestate.DRAWING);
				game.getColorsDoneButton().setVisible(true);
				game.getColorBox().setVisible(true);
				lockPlayButtons();

			} else {
				System.out.println("yyyyyyyyyy");
				model.setGameState(Gamestate.CREATING);
				game.getDoneButton().setVisible(true);
			
				lockPlayButtons();
			
				
				
				Stream.of(game.getHintButton(), game.getAutoSolveButton(), game.getCheckButton())
						.forEach(button -> button.setDisable(true));

			}
			game.getGameNotificationLabel().setText(model.getGameText());
			enablePlayButton();
		}
	}

	/**
	 * 
	 * Initialisiert das Spiel mit den gewünschten Einstellungen Befüllt das
	 * jeweilige Spielfeld der schwierigkeit entsprechend Setzt den Startzeitpunkt
	 * des Spiels auf 0
	 */
	public void handleGameStart(ActionEvent e) {
		if(menu.getPlayModeToggle().getSelectedToggle() != null) {
		
		
		model.setDifficulty(difficulty);
		if (difficulty == 0) {
			model.initializeCustomGame();

		} else {
			game.createNumbers();
			game.getDoneButton().setDisable(true);
		}
		game.getGameNotificationLabel().setText(model.getGameText());
		
		
		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getPane());

		if (menu.getPlayModeToggle().getSelectedToggle() != null) {
			menu.getPlayModeToggle().getSelectedToggle().setSelected(false);
		}

		if (menu.getDifficultyToggle().getSelectedToggle() != null) {
			menu.getDifficultyToggle().getSelectedToggle().setSelected(false);
		}

		disableDifficultyButtons();
		disablePlayButton();
		}
	}

	
	
	public void enableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(false);
		});
	}

	public void disableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(true);
		});
	}

	public void enablePlayButton() {
		menu.getPlayButton().setDisable(false);
	}

	public void disablePlayButton() {
		menu.getPlayButton().setDisable(true);
	}

	
	
	public void setGameButtonState() {

		Stream.of(game.getHintButton(), game.getAutoSolveButton(), game.getCheckButton()).forEach(button -> {
			if (button.isDisabled()) {
				button.setDisable(false);
			}
		});
		if (game.getDoneButton().isVisible()) {
			game.getDoneButton().setVisible(false);
		}

		if (game instanceof FreeFormGameBuilder
				&& (game.getColorBox().isVisible() && game.getColorsDoneButton().isVisible())) {
			game.getColorBox().setVisible(false);
			game.getColorsDoneButton().setVisible(false);
		}
	}
	
	
	
	public void lockPlayButtons() {
		Stream.of(game.getHintButton(), game.getAutoSolveButton(), game.getCheckButton()).forEach(button -> {
			if (!button.isDisabled()) {
				button.setDisable(true);
			}
	}
	);
	}
	
	
	public boolean checkIfManualWasPressed(ActionEvent e) {
		if(difficulty == 0 && menu.getDifficultyToggle().getToggles() != null) {
			handleManual(e);
			return true;
		}
		return false;
	}
	

}
