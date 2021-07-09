package application;

import org.controlsfx.control.PopOver;

import controller.PopOverController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is used to create a pop up which gives the user the ability to change the game mode while he/she is in an other game, the pop up is shown when the user
 * presses the {@link application.BasicGameBuilder} newGameItem.
 * Due to the similarity of the components and stucture this class inherits from the {@link application.MainMenu} class
 * @author grube
 *
 */
public class NewGamePopUp extends MainMenu {

	
	private PopOver popOver;
	
	/**
	 * container for the game mode buttons
	 */
	private HBox gameModeButtons;
	
	/**
	 * container for the difficulty buttons
	 */
	private HBox difficultyButtons;
	
	/**
	 * label which is used as header of the pop up
	 */
	private Label newGameModeLabel;
	
	/**
	 * root container of the popover
	 */
	private VBox popOverBox;
	
	private PopOverController popcontrol;

	
	/**
	 * Creates a PopOver object and fills it with the needed UI components 
	 * Furthermore the class' controller is initialized
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
	 * This method creates the ToggleButtons which are responsible for selecting a game mode by calling the method from the super class {@link  application.ModeController#createGameModeButtons()}
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
	 * This method creates the ToggleButtons which are responsible for selecting a difficulty by calling the method from the super class {@link  application.ModeController#createDifficultyButtons()}
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
	 * This method is used to connect the buttons of the PopOver with methods which are defined in the {@link controller.PopOverController} class
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
