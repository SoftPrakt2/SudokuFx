package controller;

import java.util.stream.Stream;

import application.BasicGameBuilder;
import application.FreeFormGameBuilder;
import application.GUI;
import application.MainMenu;
import application.SamuraiGameBuilder;
import application.GameOverview;
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
 * This Class is the Controller of the {@link application.MainMenu} class. This
 * Controller handles the navigation through the program and ensures that the
 * correct game with the correct difficulty is created 
 * Furthermore this controller handles the navigation to the Programms Storage View
 */

public class ModeController {

	private MainMenu menu;

	/**
	 * Game UI which will be initialized according to the pressed Gamemode Button
	 */
	private BasicGameBuilder gameScene;
	/**
	 * Game Model which will be initialized according to the pressed Gamemode Button
	 */
	private BasicGameLogic model;

	/**
	 * auxiliary variable which is used to cache the selected difficulty of the user
	 * Will be handed over to the {@link logic.BasicGameLogic#setDifficulty(int)}
	 * Method when {@link application.MainMenu#getPlayButton()} is pressed
	 */
	protected int difficulty = -1;

	public <E extends MainMenu> ModeController(E menu) {
		this.menu = menu;
	}

	/**
	 * instantiates the {@link #model} as SudokuLogic instantiates the
	 * {@link #gameScene} as SudokuGameBuilder The difficulty ToggleButtons of
	 * {@link application.MainMenu#getPlayModeToggle()} will be enabled
	 */
	public void handleToSudoku(ActionEvent e) {
		model = new SudokuLogic(Gamestate.OPEN, 0, 0);
		gameScene = new SudokuGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * instantiates the {@link #model} as SamuraiLogic instantiates the
	 * {@link #gameSceme} as SamuraiGameBuilder The difficulty ToggleButtons of
	 * {@link application.MainMenu#getPlayModeToggle()} will be enabled
	 */
	public void handleToSamurai(ActionEvent e) {
		model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		gameScene = new SamuraiGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * instantiates the {@link #model} as FreeFormLogic instantiates the
	 * {@link #gameSceme} as FreeFormGameBuilder The difficulty ToggleButtons of
	 * {@link application.MainMenu#getPlayModeToggle()} will be enabled
	 */
	public void handleToFreeForm(ActionEvent e) {
		model = new FreeFormLogic(Gamestate.OPEN, 0, 0);
		gameScene = new FreeFormGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * Instantiates a {@link application.GameOverview} object and opens its stage 
	 * 
	 * 
	 */
	public void handleToLoad(ActionEvent e) {
		GameOverview overview = new GameOverview();
		overview.createStage();
	}

	/**
	 * 
	 * closes the program
	 */
	public void handleExit(ActionEvent e) {
		GUI.getStage().close();
	}

	/**
	 * sets the {@link #difficulty} variable
	 */
	public void handleHard(ActionEvent e) {
		difficulty = 3;
		unlockGameActionButtons();
		enablePlayButton();
	}

	/**
	 * sets the {@link #difficulty} variable
	 */
	public void handleEasy(ActionEvent e) {
		difficulty = 7;
		unlockGameActionButtons();
		enablePlayButton();
	}

	/**
	 * sets the {@link #difficulty} variable
	 */
	public void handleMedium(ActionEvent e) {
		difficulty = 5;
		unlockGameActionButtons();
		enablePlayButton();
	}

	/**
	 * sets the {@link #difficulty} variable if {@link #model} is a FreeFormLogic
	 * {@link application.BasicGameBuilder#getColorsDoneButton()} and
	 * {@link application.BasicGameBuilder#getColorBox()} will be made visible
	 * 
	 * otherwise {@link application.BasicGameBuilder#getDoneButton()} will be made
	 * visible
	 * uses {@link #lockGameActionButtons()} to lock the games playfunction buttons
	 */
	public void handleManual(ActionEvent e) {
		difficulty = 0;
		if (model instanceof FreeFormLogic) {
			model.setGameState(Gamestate.DRAWING);
			gameScene.getColorsDoneButton().setVisible(true);
			gameScene.getColorBox().setVisible(true);
		} else {
			model.setGameState(Gamestate.CREATING);
			gameScene.getCustomNumbersDone().setVisible(true);
		}
		model.initializeCustomGame();
		lockGameActionElements();
		enablePlayButton();
	}

	/**
	 * Responsible for switching to the selected game UI 
	 */
	public void handleGameStart(ActionEvent e) {
		if (menu.getPlayModeToggle().getSelectedToggle() != null
				&& menu.getDifficultyToggle().getSelectedToggle() != null) {

			
			model.setDifficulty(difficulty);
			if (model.getDifficulty() > 0) {
				gameScene.createNumbers();
			}

			gameScene.getGameNotificationLabel().setText(model.getGameText());

			alignGameWithWindow(gameScene);
			removeSelectedToggles();
			disableDifficultyButtons();
			disablePlayButton();
		}
	}

	/**
	 * Enables the {@link application.MainMenu#getDifficultyToggle()} ToggleButtons
	 */
	public void enableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(false);
		});
	}

	/**
	 * Disables the {@link application.MainMenu#getDifficultyToggle()} ToggleButtons
	 */
	public void disableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(true);
		});
	}

	public void removeSelectedToggles() {
		if (menu.getPlayModeToggle().getSelectedToggle() != null) {
			menu.getPlayModeToggle().getSelectedToggle().setSelected(false);
		}

		if (menu.getDifficultyToggle().getSelectedToggle() != null) {
			menu.getDifficultyToggle().getSelectedToggle().setSelected(false);
		}
	}

	/**
	 * Enables the CreateButton inside the mainmenu
	 */
	public void enablePlayButton() {
		menu.getPlayButton().setDisable(false);
	}

	/**
	 * Disables the CreateButton inside the mainmenu
	 */
	public void disablePlayButton() {
		menu.getPlayButton().setDisable(true);
	}

	/**
	 * This method unlocks the {@link #gameScene} playfunction Buttons and corresponding menu items and sets the {@link #gameScene} manual Buttons invisible
	 * This is needed to ensure that if a user presses the {@link application.MainMenu#getDifficultyToggle()} manual button
	 * and then presses another Button of the mentioned button group, the state of the {@link #gameScene} Buttons are available accordingly
	 */

	public void unlockGameActionButtons() {
		Stream.of(gameScene.getHintButton(), gameScene.getAutoSolveButton(), gameScene.getCheckButton())
				.forEach(button -> {
					if (button.isDisabled()) {
						button.setDisable(false);
					}
				});
		Stream.of(gameScene.getAutoSolveMenuItem(), gameScene.getHintMenuItem(),gameScene.getCheckMenuItem()).forEach(menuItem -> {
					if (menuItem.isDisable()) {
						menuItem.setDisable(false);
					}
				});
		
		
		if (gameScene.getCustomNumbersDone().isVisible()) {
			gameScene.getCustomNumbersDone().setVisible(false);
		}

		if (gameScene instanceof FreeFormGameBuilder
				&& (gameScene.getColorBox().isVisible() && gameScene.getColorsDoneButton().isVisible())) {
			gameScene.getColorBox().setVisible(false);
			gameScene.getColorsDoneButton().setVisible(false);
		}
	}

	/**
	 * Locks the {@link #gameScene} playaction menuitems and buttons
	 */
	public void lockGameActionElements() {
		Stream.of(gameScene.getHintButton(), gameScene.getAutoSolveButton(), gameScene.getCheckButton())
				.forEach(button -> {
					if (!button.isDisabled()) {
						button.setDisable(true);
					}
				});
		Stream.of(gameScene.getAutoSolveMenuItem(), gameScene.getHintMenuItem(), gameScene.getCheckMenuItem())
			.forEach(menuItem -> {
				if(!menuItem.isDisable()) {
					menuItem.setDisable(true);
				}
			});
	}

	
	/**
	 * 
	 * auxiliary method which is needed for ensuring the correct button state when the user selects the manual difficulty and then decides to switch difficulty
	 * This method ensures that the correct buttons are visible and locked if needed
	 * @return true if difficulty equals 0 which means the difficulty is manual 
	 */
	public boolean checkIfManualWasPressed(ActionEvent e) {
		if (difficulty == 0 && menu.getDifficultyToggle().getToggles() != null) {
			handleManual(e);
			return true;
		}
		return false;
	}

	/**
	 * Aligns the programs Window size to width and height variables defined in {@link #gameScene}
	 * displays the GameUI 
	 * @param game 
	 */
	public void alignGameWithWindow(BasicGameBuilder game) {
		GUI.getStage().setHeight(game.getHeight());
		GUI.getStage().setWidth(game.getWidth());
		GUI.getStage().getScene().setRoot(game.getGameUIRoot());
	}

	
	
	public int getSelectedDifficulty() {
		return difficulty;
	}
	
	
	/**
	 * 
	 * The following getter and setters are needed to provide the
	 * {@link application.PopOverController with the needed variables}
	 */
	public BasicGameLogic getModel() {
		return model;
	}

	public BasicGameBuilder getGameBuilder() {
		return gameScene;
	}

	public void setModel(BasicGameLogic model) {
		this.model = model;
	}
	
	public void setGameScene(BasicGameBuilder gameScene) {
		this.gameScene = gameScene;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	

}
