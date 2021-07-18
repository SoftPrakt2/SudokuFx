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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.stage.Screen;
import logic.BasicGameLogic;
import logic.FreeFormLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

/**
 * 
 * This class is the controller of the {@link application.MainMenu} class.
 * This controller handles the navigation through the program and ensures that the
 * correct game with the correct difficulty is created.
 * Furthermore this controller handles the navigation to the programs GameOverView
 */

public class ModeController {

	private MainMenu menu;

	/**
	 * Game UI which will be initialized according to the pressed game mode Button
	 */
	private BasicGameBuilder gameScene;
	/**
	 * Game model which will be initialized according to the pressed game mode Button
	 */
	private BasicGameLogic model;

	/**
	 * Auxiliary variable which is used to cache the selected difficulty of the user
	 * Will be handed over to the {@link logic.BasicGameLogic#setDifficulty(int)}
	 * method when {@link application.MainMenu#getPlayButton()} is pressed
	 */
	protected int difficulty = -1;

	public <E extends MainMenu> ModeController(E menu) {
		this.menu = menu;
	}

	/**
	 * instantiates the {@link #model} as SudokuLogic 
	 * instantiates the{@link #gameScene} as SudokuGameBuilder 
	 * the difficulty ToggleButtons {@link application.MainMenu#getPlayModeToggle()} will be enabled
	 */
	public void handleToSudoku(ActionEvent e) {
		model = new SudokuLogic(Gamestate.OPEN, 0, 0);
		gameScene = new SudokuGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * instantiates the {@link #model} as SamuraiLogic 
	 * instantiates the {@link #gameSceme} as SamuraiGameBuilder 
	 * the difficulty ToggleButtons of {@link application.MainMenu#getPlayModeToggle()} will be enabled
	 * @param e action event triggered through actions in the UI from the User
	 */
	public void handleToSamurai(ActionEvent e) {
		model = new SamuraiLogic(Gamestate.OPEN, 0, 0);
		gameScene = new SamuraiGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * instantiates the {@link #model} as FreeFormLogic 
	 * instantiates the {@link #gameSceme} as FreeFormGameBuilder 
	 * the difficulty ToggleButtons of{@link application.MainMenu#getPlayModeToggle()} will be enabled
	 *  @param e action event triggered through actions in the UI from the User
	 */
	public void handleToFreeForm(ActionEvent e) {
		model = new FreeFormLogic(Gamestate.OPEN, 0, 0);
		gameScene = new FreeFormGameBuilder(model);
		gameScene.initializeGame();
		enableDifficultyButtons();
		checkIfManualWasPressed(e);
	}

	/**
	 * instantiates a {@link application.GameOverview} object and opens its stage 
	 * @param e action event triggered through actions in the UI from the User
	 */
	public void handleToLoad(ActionEvent e) {
		GameOverview overview = new GameOverview();
		overview.createStage();
		removeSelectedToggles();
		disableDifficultyButtons();
		disablePlayButton();
	}

	/**
	 * 
	 * closes the program
	 * @param e action event triggered through actions in the UI from the User
	 */
	public void handleExit(ActionEvent e) {
		GUI.getStage().close();
	}

	/**
	 * sets the {@link #difficulty} variable
	 * @param e action event triggered through actions in the UI from the User
	 */
	public void handleHard(ActionEvent e) {
		difficulty = 3;
		unlockGameActionButtons();
		enablePlayButton();
	}

	/**
	 * sets the {@link #difficulty} variable
	 * @param e action event triggered through actions in the UI from the User
	 */
	public void handleEasy(ActionEvent e) {
		difficulty = 7;
		unlockGameActionButtons();
		enablePlayButton();
	}

	/**
	 * sets the {@link #difficulty} variable
	 * @param e action event triggered through actions in the UI from the User
	 */
	public void handleMedium(ActionEvent e) {
		difficulty = 5;
		unlockGameActionButtons();
		enablePlayButton();
	}

	/**
	 * sets the {@link #difficulty} variable 
	 * 
	 * if {@link #model} is a FreeFormLogic
	 * {@link application.BasicGameBuilder#getColorsDoneButton()} and
	 * {@link application.BasicGameBuilder#getColorBox()} will be made visible
	 * 
	 * otherwise {@link application.BasicGameBuilder#getDoneButton()} will be made
	 * visible
	 * uses {@link #lockGameActionButtons()} to lock the games playaction buttons
	 * 
	 * @param e action event triggered through actions in the UI from the User
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
	 * responsible for switching to the selected game UI 
	 */
	public void handleGameStart(ActionEvent e) {
		if (menu.getPlayModeToggle().getSelectedToggle() != null
				&& menu.getDifficultyToggle().getSelectedToggle() != null) {

			
			model.setDifficulty(difficulty);
			//insert numbers into the UI sudokutextfield if the selected difficulty is not manual
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
	 * enables the {@link application.MainMenu#getDifficultyToggle()} ToggleButtons
	 */
	public void enableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(false);
		});
	}

	/**
	 * disables the {@link application.MainMenu#getDifficultyToggle()} ToggleButtons
	 */
	public void disableDifficultyButtons() {
		menu.getDifficultyToggle().getToggles().forEach(toggle -> {
			Node button = (Node) toggle;
			button.setDisable(true);
		});
	}
	
	
	/**
	 * removes the selection of a button inside its ToggleGorup
	 */
	public void removeSelectedToggles() {
		if (menu.getPlayModeToggle().getSelectedToggle() != null) {
			menu.getPlayModeToggle().getSelectedToggle().setSelected(false);
		}
		

		if (menu.getDifficultyToggle().getSelectedToggle() != null) {
			menu.getDifficultyToggle().getSelectedToggle().setSelected(false);
		}
	}

	/**
	 * enables the CreateButton inside the main menu
	 */
	public void enablePlayButton() {
		menu.getPlayButton().setDisable(false);
	}

	/**
	 * disables the CreateButton inside the main menu
	 */
	public void disablePlayButton() {
		menu.getPlayButton().setDisable(true);
	}

	/**
	 * This method unlocks the {@link #gameScene} playfunction Buttons and corresponding menu items and sets the {@link #gameScene} manual Buttons invisible
	 * This is needed to ensure that if a user presses the {@link application.MainMenu#getDifficultyToggle()} manual button
	 * and then presses another button of the buttons togglegroup, the state of the {@link #gameScene} buttons are available accordingly
	 */

	public void unlockGameActionButtons() {
		Stream.of(gameScene.getHintButton(), gameScene.getAutoSolveButton(), gameScene.getCheckButton())
				.forEach(button -> {
					if (button.isDisabled()) {
						button.setDisable(false);
					}
				});
		Stream.of(gameScene.getAutoSolveMenuItem(), gameScene.getHintMenuItem(),gameScene.getCheckMenuItem(),gameScene.getConflictItem()).forEach(menuItem -> {
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
	 * locks the {@link #gameScene} playaction menuitems and buttons
	 */
	public void lockGameActionElements() {
		Stream.of(gameScene.getHintButton(), gameScene.getAutoSolveButton(), gameScene.getCheckButton())
				.forEach(button -> {
					if (!button.isDisabled()) {
						button.setDisable(true);
					}
				});
		Stream.of(gameScene.getAutoSolveMenuItem(), gameScene.getHintMenuItem(), gameScene.getCheckMenuItem(),gameScene.getConflictItem())
			.forEach(menuItem -> {
				if(!menuItem.isDisable()) {
					menuItem.setDisable(true);
				}
			});
	}

	
	/**
	 * 
	 * auxiliary method which is needed for ensuring the correct button state when the user selects the manual difficulty and then decides to switch difficulty
	 * this method ensures that the correct buttons are visible and locked if needed
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
	 * aligns the programs window size to width and height variables defined in {@link #gameScene}
	 * displays the GameUI 
	 * @param game which will be displayed in the UI
	 */
	public void alignGameWithWindow(BasicGameBuilder game) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        GUI.getStage().setHeight(game.getHeight());
        GUI.getStage().setWidth(game.getWidth());
        GUI.getStage().getScene().setRoot(game.getGameUIRoot());

         GUI.getStage().setX((screenBounds.getWidth() - game.getSceneWidth()) / 2);
         GUI.getStage().setY((screenBounds.getHeight() - game.getSceneHeight()) / 2);
	}

	
	
	
	/**
	 * 
	 * The following getter and setters are needed to provide the
	 * {@link application.PopOverController}  with the needed variables
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
	

	public int getSelectedDifficulty() {
		return difficulty;
	}
	
	

}
