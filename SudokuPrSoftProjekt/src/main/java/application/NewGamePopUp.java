package application;

import org.controlsfx.control.PopOver;

import controller.PopOverController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is used to create a PopUP which gives the User the ability to change the gamemode while he/she is in an other game, the popup is shown when the user
 * presses the {@link application.BasicGameBuilder} newGameItem
 * Due to the similarity of the components and stucture this class inherits from the {@link application.MainMenu} class
 * @author grube
 *
 */
public class NewGamePopUp extends MainMenu {

	
	private PopOver popOver;
	
	/**
	 * Container for the game mode buttons
	 */
	private HBox gameModeButtons;
	
	/**
	 * Container for the difficulty buttons
	 */
	private HBox difficultyButtons;
	
	/**
	 * Label which is used as Header of the popup
	 */
	private Label newGameModeLabel;
	
	/**
	 * root container of the popover
	 */
	private VBox popOverBox;
	private PopOverController popcontrol;

	
	/**
	 * Creates a PopOver Object and fills it with the UI Components needed
	 * Furtermore the Objects controller is initialized
	 * @return created PopOver
	 */
	public PopOver createPopUp() {
		popcontrol = new PopOverController(this);

		popOverBox = new VBox();
		createGameModeButtons();
		createDifficultyButtons();
		setButtonActions();

		newGameModeLabel = new Label("Choose new game settings");

		popOverBox.getChildren().addAll(newGameModeLabel, gameModeButtons, difficultyButtons);
		popOverBox.setAlignment(Pos.CENTER);

		popOverBox.setPrefSize(250, 150);

		popOver = new PopOver(popOverBox);

		popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);

		return popOver;
	}
	
	
	/**
	 * This method creates the togglebuttons which are responsible for selecting a gamemode by calling the method from the super class {@link  application.ModeController#createGameModeButtons()}
	 */
	@Override
	public void createGameModeButtons() {
		gameModeButtons = new HBox();
		gameModeButtons.setSpacing(2);
		super.createGameModeButtons();
		gameModeButtons.getChildren().addAll(sudoku, samurai, freeform);
		gameModeButtons.setAlignment(Pos.CENTER);
	}
	
	
	/**
	 * This method creates the togglebuttons which are responsible for selecting a difficulty by calling the method from the super class {@link  application.ModeController#createDifficultyButtons()}
	 */
	@Override
	public void createDifficultyButtons() {
		difficultyButtons = new HBox();
		difficultyButtons.setSpacing(2);
		super.createDifficultyButtons();
		difficultyButtons.setAlignment(Pos.CENTER);
		difficultyButtons.getChildren().addAll(easy, medium, hard);
	}

	
	/**
	 * This method is used to connect the buttons of the popup with methods which are defined in the @see SudokuFx.SudokuFx.application.PopOverController class
	 */
	@Override
	public void setButtonActions() {
		sudoku.setOnAction(popcontrol::handleToSudoku);
		samurai.setOnAction(popcontrol::handleToSamurai);
		freeform.setOnAction(popcontrol::handleToFreeForm);
		easy.setOnAction(popcontrol::handleEasy);
		medium.setOnAction(popcontrol::handleMedium);
		hard.setOnAction(popcontrol::handleHard);
	}

}
