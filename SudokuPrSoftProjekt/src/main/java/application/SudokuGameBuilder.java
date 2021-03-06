package application;



import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import logic.BasicGameLogic;

/**
 * @author grube defines the UI representation of a 9x9 Sudoku Game
 */
public class SudokuGameBuilder extends BasicGameBuilder {

	/**
	 * constructor of the class
	 * 
	 * @param model the corresponding GameLogic model of this GameBuilder
	 */
	public SudokuGameBuilder(BasicGameLogic model) {
		super(model);
		setTextField(new SudokuTextField[9][9]);
		
		//define size of the window in the UI 
		if(bounds.getWidth() * 0.45 < 700) {
            setSceneWidth(bounds.getWidth() * 0.45);
        } else {
            setSceneWidth(700);
        }

        if(bounds.getHeight() * 0.85 < 700) {
            setSceneHeight(bounds.getHeight() * 0.85);
        } else {
            setSceneHeight(700);
        }
	}

	/**
	 * Draws the Sudoku playboard, this playboard is filled with 9x9 Stackpanes with
	 * SudokuTextFields inside them This container nesting is needed to ensure
	 * correct scaling of the playboard
	 */
	@Override
	public GridPane createBoard() {
		final SimpleDoubleProperty textSize = new SimpleDoubleProperty(10);

		GridPane playBoard = new GridPane();

		playBoard.setPadding(new Insets(5, 5, 5, 5));

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("sudokucell");

				// align the size of the cell to the playboards size
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
				

				// position and style the textfields
				getTextField()[row][col] = new SudokuTextField("");
				getTextField()[row][col].setMaxSize(500, 500);
				getTextField()[row][col].setMinSize(1, 1);
				getTextField()[row][col].getStyleClass().add("textfieldBasic");
				getTextField()[row][col].setAlignment(Pos.CENTER);

				// draws the lines between the SudokuFields to achieve the classic Sudoku look
				cell.pseudoClassStateChanged(right, row == 2 || row == 5);
				cell.pseudoClassStateChanged(bottom, col == 2 || col == 5);

				// align the size of the Text inside a SudokuField to the window size
				getTextField()[row][col].styleProperty().bind(Bindings.concat("-fx-font-size: ", textSize.asString()));
				textSize.bind(gameRoot.widthProperty().add(gameRoot.widthProperty()).divide(50));
				textSize.bind(gameRoot.heightProperty().add(gameRoot.heightProperty()).divide(50));

				cell.getChildren().add(getTextField()[row][col]);

				playBoard.add(cell, row, col);
			}

		}

		playBoard.setAlignment(Pos.CENTER);

		return playBoard;
	}

	/**
	 * Creates the UI components which are needed for a manual Sudoku game.
	 */
	@Override
	public void createManualControls() {
		FontAwesome fontAwesome = new FontAwesome();
		Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
		setCustomNumbersDone(new Button(""));
		getCustomNumbersDone().setGraphic(doneGraphic);
		getCustomNumbersDone().setVisible(false);
		toolBar.getItems().add(3, getCustomNumbersDone());
	}

}
