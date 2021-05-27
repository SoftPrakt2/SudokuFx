package application;

import controller.GameController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;
import javafx.scene.Node;

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

		for (int i = 0; i < textField.length; i++) {
			for (int j = 0; j < textField[i].length; j++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(22));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(22));

				StackPane cellEmpty = new StackPane();
				SudokuField empty = new SudokuField("-1");
				empty.setStyle("-fx-pref-width: 2em;");

				cellEmpty.getChildren().add(empty);
				cellEmpty.setDisable(true);
				textField[i][j] = empty;

				if ((i == 9 || i == 10 || i == 11) && (j < 6 || j > 14)) {
					textField[i][j].setPlayable(false);

				} else if ((i < 6 || i > 14) && (j == 9 || j == 10 || j == 11)) {
					textField[i][j].setPlayable(false);
				} else {

					textField[i][j] = new SudokuField("");
					textField[i][j].setMaxSize(50, 50);
					textField[i][j].setAlignment(Pos.CENTER);
					textField[i][j].setPlayable(true);
					textField[i][j].setDisable(true);

					cell.getChildren().add(textField[i][j]);

					playBoard.add(cell, i, j);
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
			int j = GridPane.getRowIndex(cell);
			int i = GridPane.getColumnIndex(cell);
			nodeArray[i][j] = cell;

			nodeArray[i][j].pseudoClassStateChanged(right, i == 2 || i == 5 || i == 11 || i == 17);

			if (i == 8 && (j > 5 && j < 15))
				nodeArray[i][j].pseudoClassStateChanged(right, i == 8);
			if ((i == 14 || i ==17) && (j < 9 || j > 11))
				nodeArray[i][j].pseudoClassStateChanged(right, i == 14 || i == 17);

			if ((j == 17 || j == 14) && (i < 9 || i > 11))
				nodeArray[i][j].pseudoClassStateChanged(bottom, j == 14 || j == 17);
			
			

			if (j == 5 || (j == 2 && (i < 9 || i > 11)))
				nodeArray[i][j].pseudoClassStateChanged(bottom, j == 2 || j == 5);

			if (j == 11 || (j == 8 && (i > 5 && i < 15)))
				nodeArray[i][j].pseudoClassStateChanged(bottom, j == 8 || j == 11);
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