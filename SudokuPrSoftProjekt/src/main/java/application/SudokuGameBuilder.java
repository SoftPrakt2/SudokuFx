package application;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;

/**
 * 
 * Leitet von BasicGameBuilder ab Buttons und MenuBar werden mit den abtrakten
 * Methoden aus der Basisklasse erstellt
 *
 */
public class SudokuGameBuilder extends BasicGameBuilder {

	public SudokuGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[9][9];
		width = 700;
		height = 700;
	}

	/**
	 * * Übergibt dieser Scene den jeweiligen Controller Erstellt die Scene mit den
	 * Buttons, der MenuBar und dem Sudoku-Spielfeld
	 */

	/**
	 * Zeichnet ein Sudoku-Feld
	 */
	public GridPane createBoard() {

		playBoard = new GridPane();

		playBoard.setPadding(new Insets(5, 5, 5, 5));

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));

				textField[row][col] = new SudokuField("");

				textField[row][col].setMaxSize(100, 100);
				textField[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 23));
				textField[row][col].setAlignment(Pos.CENTER);

				cell.pseudoClassStateChanged(right, row == 2 || row == 5);

				cell.pseudoClassStateChanged(bottom, col == 2 || col == 5);

				textField[row][col].setDisable(true);

				cell.getChildren().add(textField[row][col]);

				playBoard.add(cell, row, col);
			}

		}

		playBoard.setAlignment(Pos.CENTER);

		return playBoard;
	}

	/**
	 * Befüllt das Spielfeld beim ersten Start mit Zahlen abhängig von der im
	 * Hauptmenü eingestellten Schwierigkeit
	 */
	public void createNumbers() {
		controller.createGame();
	}

	/**
	 * Getter und Setter für die Variablen dieser Klasse
	 */

	public SudokuField[][] getTextField() {
		return textField;
	}

}
