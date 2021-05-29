package application;

import org.controlsfx.control.PopOver;

import controller.PopOverController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.BasicGameLogic;

public class NewGamePopUp extends MainMenu {

	BasicGameBuilder game;
	BasicGameLogic model;

	public NewGamePopUp(BasicGameBuilder game, BasicGameLogic model) {
		this.game = game;
		this.model = model;
	}

	PopOver popOver;

	public PopOver createPopUp() {
		PopOverController popcontrol = new PopOverController(game, model);

		VBox popOverBox = new VBox();

		Label newGameModeLabel = new Label("Choose new game settings");

		HBox gameModeButtons = new HBox();
		gameModeButtons.setSpacing(2);
		ToggleButton sudoku = new ToggleButton("Sudoku");
		ToggleButton samurai = new ToggleButton("Samurai");
		ToggleButton freeform = new ToggleButton("Freeform");
		gameModeButtons.getChildren().addAll(sudoku, samurai, freeform);

		HBox difficultyButtons = new HBox();
		difficultyButtons.setSpacing(2);
		ToggleButton easy = new ToggleButton("Easy");
		ToggleButton medium = new ToggleButton("Medium");
		ToggleButton hard = new ToggleButton("Hard");
		difficultyButtons.setSpacing(2);
		difficultyButtons.getChildren().addAll(easy, medium, hard);

		sudoku.setOnAction(popcontrol::handleToSudoku);
		samurai.setOnAction(popcontrol::handleToSamurai);
		easy.setOnAction(popcontrol::handleEasy);
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
