package application;

import org.controlsfx.control.PopOver;

import controller.PopOverController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;

public class NewGamePopUp extends MainMenu {

	BasicGameBuilder game;
	BasicGameLogic model;

	

	PopOver popOver;
	

	public PopOver createPopUp() {
		PopOverController popcontrol = new PopOverController(this);

		VBox popOverBox = new VBox();

		Label newGameModeLabel = new Label("Choose new game settings");

		HBox gameModeButtons = new HBox();
		gameModeButtons.setSpacing(2);
		
		
		super.createGameModeButtons();
		gameModeButtons.getChildren().addAll(sudoku,samurai, freeform);
		

		super.createDifficultyButtons();
		HBox difficultyButtons = new HBox();
		difficultyButtons.setSpacing(2);

		difficultyButtons.setSpacing(2);
		difficultyButtons.getChildren().addAll(easy, medium, hard);

		sudoku.setOnAction(popcontrol::handleToSudoku);
		samurai.setOnAction(popcontrol::handleToSamurai);
		freeform.setOnAction(popcontrol::handleToFreeForm);
		easy.setOnAction(popcontrol::handleEasy);
		medium.setOnAction(popcontrol::handleMedium);
		hard.setOnAction(popcontrol::handleHard);
		
		popOverBox.getChildren().addAll(newGameModeLabel, gameModeButtons, difficultyButtons);
		popOverBox.setAlignment(Pos.CENTER);
		gameModeButtons.setAlignment(Pos.CENTER);
		difficultyButtons.setAlignment(Pos.CENTER);

		popOverBox.setPrefSize(250, 150);

		popOver = new PopOver(popOverBox);

		popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);

		return popOver;
	}

}
