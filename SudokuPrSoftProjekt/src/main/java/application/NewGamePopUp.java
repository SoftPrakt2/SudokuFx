package application;

import org.controlsfx.control.PopOver;

import controller.PopOverController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;

public class NewGamePopUp extends MainMenu {

	PopOver popOver;
	HBox gameModeButtons;
	HBox difficultyButtons;
	Label newGameModeLabel;
	VBox popOverBox;
	PopOverController popcontrol;

	

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

	@Override
	public void createGameModeButtons() {
		gameModeButtons = new HBox();
		gameModeButtons.setSpacing(2);
		super.createGameModeButtons();
		gameModeButtons.getChildren().addAll(sudoku, samurai, freeform);
		gameModeButtons.setAlignment(Pos.CENTER);
	}

	@Override
	public void createDifficultyButtons() {
		difficultyButtons = new HBox();
		difficultyButtons.setSpacing(2);
		super.createDifficultyButtons();
		difficultyButtons.setAlignment(Pos.CENTER);
		difficultyButtons.getChildren().addAll(easy, medium, hard);
	}

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
