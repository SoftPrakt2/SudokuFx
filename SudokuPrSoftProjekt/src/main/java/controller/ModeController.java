package controller;

import java.util.stream.Stream;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.Storage;
import application.SudokuGameBuilder;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

/**
 * 
 * Die ModeController Klasse stellt den Controller der MainMenu Klasse in der View dar. 
 * Der Aufgabenbereich dieser Klasse liegt darin je nach gedrücktem Knopf in der UI das richtige Spiel aufzurufen
 * Des Weiteren regelt dieser Controller die Einstellungen der Schwierigkeit eines Spiels 
 */

public class ModeController {

	private MainMenu menu;
	protected BasicGameBuilder gameScene;
	protected BasicGameLogic model;

	protected int difficulty;

	public <E extends MainMenu> ModeController(E menu) {
		this.menu = menu;
	}

	/**
	 * Instanziiert die abstrakte BasisLogic Klasse als SudokuLogik und die abstrakte BasicGameBuilder Klasse als SudokuGameBuilder  
	 * 
	 */
	public void handleToSudoku(ActionEvent e) {
		
		model = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
		gameScene = new SudokuGameBuilder(model);
		gameScene.initializeGame();
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
		gameScene = new SamuraiGameBuilder(model);
		gameScene.initializeGame();
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
		gameScene = new FreeFormGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * 
	 * Instanziiert ein StorageObjekt und ruft die Scene des Storage-Objekts auf
	 * 
	 * 
	 */
	public void handleToLoad(ActionEvent e)  {
		Storage overview = new Storage();
		overview.createStage();
	}

	/**
	 *  
	 * 
	 */
	public void handleExit(ActionEvent e) {
		GUI.getStage().close();
	}

	/**
	 * Stellen die schwierigkeit des zuvor ausgewählten Sudoku-Spiels ein
	 */
	public void handleHard(ActionEvent e) {
			difficulty = 3;
			unlockGameActionButtons();
			enablePlayButton();
	}

	public void handleEasy(ActionEvent e) {
			difficulty = 7;
			unlockGameActionButtons();
			enablePlayButton();
		
	}

	public void handleMedium(ActionEvent e) {
			difficulty = 5;
			unlockGameActionButtons();
			enablePlayButton();
	}

	
	public void handleManual(ActionEvent e) {
			difficulty = 0;
			if (model instanceof FreeFormLogic) {
				model.setGameState(Gamestate.DRAWING);
				gameScene.getColorsDoneButton().setVisible(true);
				gameScene.getColorBox().setVisible(true);
			} else {
				model.setGameState(Gamestate.CREATING);
				gameScene.getDoneButton().setVisible(true);
			}
			lockGameActionButtons();
			enablePlayButton();
		}
	

	/**
	 * 
	 * Initialisiert das Spiel mit den gewünschten Einstellungen Befüllt das
	 * jeweilige Spielfeld der schwierigkeit entsprechend Setzt den Startzeitpunkt
	 * des Spiels auf 0
	 */
	public void handleGameStart(ActionEvent e) {
		if(menu.getPlayModeToggle().getSelectedToggle() != null && menu.getDifficultyToggle().getSelectedToggle() != null) {
		
		
		model.setDifficulty(difficulty);
		if (difficulty == 0) {
			model.initializeCustomGame();
		} else {
			gameScene.createNumbers();
			gameScene.getDoneButton().setDisable(true);
		}
		gameScene.getGameNotificationLabel().setText(model.getGameText());
		
		
		GUI.getStage().setHeight(gameScene.getHeight());
		GUI.getStage().setWidth(gameScene.getWidth());
		GUI.getStage().getScene().setRoot(gameScene.getPane());

		
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

	

	/**
	 * Schaltet die Schwierigkeitsbuttons zur Verwendung frei 
	 */
	public void enableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(false);
		});
	}

	/**
	 * Sperrt die Schwierigkeitsbuttons, diese Methode wird benötigt, damit bei einem Wiederaufruf des Hauptmenüs diese nicht verwendet werden können
	 */
	public void disableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(true);
		});
	}

	
	/**
	 * Schaltet den CreateGame Button zur Verwendung frei 
	 */
	public void enablePlayButton() {
		menu.getPlayButton().setDisable(false);
	}

	
	/**
	 * Sperrt die Schwierigkeitsbuttons, diese Methode wird benötigt, damit bei einem Wiederaufruf des Hauptmenüs diese nicht verwendet werden können
	 */
	public void disablePlayButton() {
		menu.getPlayButton().setDisable(true);
	}

	
	
	public void unlockGameActionButtons() {
		Stream.of(gameScene.getHintButton(), gameScene.getAutoSolveButton(), gameScene.getCheckButton()).forEach(button -> {
			if (button.isDisabled()) {
				button.setDisable(false);
			}
		});
		if (gameScene.getDoneButton().isVisible()) {
			gameScene.getDoneButton().setVisible(false);
		}

		if (gameScene instanceof FreeFormGameBuilder
				&& (gameScene.getColorBox().isVisible() && gameScene.getColorsDoneButton().isVisible())) {
			gameScene.getColorBox().setVisible(false);
			gameScene.getColorsDoneButton().setVisible(false);
		}
	}
	
	
	
	public void lockGameActionButtons() {
		Stream.of(gameScene.getHintButton(), gameScene.getAutoSolveButton(), gameScene.getCheckButton()).forEach(button -> {
			if (!button.isDisabled()) {
				button.setDisable(true);
			}
	});
	}
	
	
	public boolean checkIfManualWasPressed(ActionEvent e) {
		if(difficulty == 0 && menu.getDifficultyToggle().getToggles() != null) {
			handleManual(e);
			return true;
		}
		return false;
	}
	

}
