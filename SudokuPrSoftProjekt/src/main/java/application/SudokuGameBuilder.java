package application;

import controller.GameController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Gamestate;
import logic.SudokuLogic;

/**
 * 
 * Leitet von BasicGameBuilder ab
 * Buttons und MenuBar werden mit den abtrakten Methoden aus der Basisklasse erstellt
 *
 */
public class SudokuGameBuilder extends BasicGameBuilder {

	public SudokuGameBuilder() {
		super();
		scene = new Scene(pane, 670, 670);
		textField = new SudokuField[9][9];
		gameType = "Sudoku";
	}

	/**
	 * Übergibt dieser Scene den jeweiligen Controller
	 * Erstellt die Scene mit den Buttons, der MenuBar und dem Sudoku-Spielfeld
	 */
	public Scene initializeScene() {
		controller = new GameController(this, new SudokuLogic(Gamestate.OPEN, 0, 0, false));

		pane.setCenter(createBoard());
		pane.setPadding(new Insets(50, 50, 50, 50));

		createMenuBar(pane);
		createPlayButtons(pane);

		scene.getStylesheets().add("/css/sudoku.css");

		return scene;
	}

	/**
	 * Zeichnet ein Sudoku-Feld
	 */
	public GridPane createBoard() {
		playBoard = new GridPane();

		playBoard.setPadding(new Insets(5, 5, 5, 5));

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(12));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(12));

				textField[i][j] = new SudokuField("");

				textField[i][j].setMaxSize(100, 100);
				textField[i][j].setFont(Font.font("Arial", FontWeight.BOLD, 15));
				textField[i][j].setAlignment(Pos.CENTER);

				cell.pseudoClassStateChanged(right, i == 2 || i == 5);

				cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);

				textField[i][j].setDisable(true);

				cell.getChildren().add(textField[i][j]);

				playBoard.add(cell, i, j);
			}

		}

		playBoard.setAlignment(Pos.CENTER);

		return playBoard;
	}

	/**
	 * Befüllt das Spielfeld beim ersten Start mit Zahlen abhängig von der im Hauptmenü eingestellten Schwierigkeit
	 */
	public void createNumbers() {
		controller.createGame(difficulty);
	}

	/**
	 * Getter und Setter für die Variablen dieser Klasse
	 */
	@Override
	public Scene getScene() {
		return scene;
	}

	public SudokuField[][] getTextField() {
		return textField;
	}

}
