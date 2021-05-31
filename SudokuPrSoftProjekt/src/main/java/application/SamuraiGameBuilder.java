package application;

import controller.GameController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
public class SamuraiGameBuilder extends BasicGameBuilder {

	public SamuraiGameBuilder(BasicGameLogic model) {
		super(model);

		textField = new SudokuField[21][21];
		width = 1050;
		height = 1050;
	}

	/**
	 * Zeichnet ein Samurai-Sudoku-Feld
	 */
	@Override
	public GridPane createBoard() {
		controller = new GameController(this, model);
		playBoard = new GridPane();

		pane.setPadding(new Insets(5, 5, 5, 5));

		playBoard.setVgap(1);
		playBoard.setHgap(1);

		for (int row = 0; row < textField.length; row++) {
			for (int col = 0; col < textField[row].length; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(22));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(22));

				StackPane cellEmpty = new StackPane();
				SudokuField empty = new SudokuField("-1");
				empty.setStyle("-fx-pref-width: 2em;");

				
				cellEmpty.getChildren().add(empty);
				cellEmpty.setDisable(true);
				textField[row][col] = empty;

				if ((row == 9 || row == 10 || row == 11) && (col < 6 || col > 14)) {
					textField[row][col].setPlayable(false);

				} else if ((row < 6 || row > 14) && (col == 9 || col == 10 || col == 11)) {
					textField[row][col].setPlayable(false);
				} else {

					textField[row][col] = new SudokuField("");
					textField[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 16));
					textField[row][col].setMaxSize(50, 50);
					textField[row][col].setAlignment(Pos.CENTER);
					textField[row][col].setPlayable(true);
					textField[row][col].setDisable(true);

					cell.getChildren().add(textField[row][col]);

					playBoard.add(cell, row, col);
					drawLines();
				}
			}

		}

		playBoard.setAlignment(Pos.CENTER);
		return playBoard;
	}

	// draws the Line for the Samurai PlayField, PseudoClass is used for the lines
	public void drawLines() {
		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
		Node[][] nodeArray = new Node[21][21];

		for (Node cell : playBoard.getChildren()) {
			int row = GridPane.getRowIndex(cell);
			int col = GridPane.getColumnIndex(cell);
			nodeArray[col][row] = cell;

			nodeArray[col][row].pseudoClassStateChanged(right, col == 2 || col == 5 || col == 11 || col == 17);

			if (col == 8 && (row > 5 && row < 15))
				nodeArray[col][row].pseudoClassStateChanged(right, col == 8);
			if ((col == 14 || col == 17) && (row < 9 || row > 11))
				nodeArray[col][row].pseudoClassStateChanged(right, col == 14 || col == 17);

			if ((row == 17 || row == 14) && (col < 9 || col > 11))
				nodeArray[col][row].pseudoClassStateChanged(bottom, row == 14 || row == 17);

			if (row == 5 || (row == 2 && (col < 9 || col > 11)))
				nodeArray[col][row].pseudoClassStateChanged(bottom, row == 2 || row == 5);

			if (row == 11 || (row == 8 && (col > 5 && col < 15)))
				nodeArray[col][row].pseudoClassStateChanged(bottom, row == 8 || row == 11);
		}

	}

	/**
	 * Befüllt das Spielfeld beim ersten Start mit Zahlen abhängig von der im
	 * Hauptmenü eingestellten Schwierigkeit
	 */
	@Override
	public void createNumbers() {
		// TODO Auto-generated method stub
		controller.createGame();
	}

	/**
	 * Getter und Setter für die Variablen dieser Klasse
	 */

}