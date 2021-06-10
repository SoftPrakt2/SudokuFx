package application;

import java.util.stream.Stream;

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
 * @author grube
 * Defines the UI representation of a Sudoku Game
 */
public class SudokuGameBuilder extends BasicGameBuilder {

	
	
	public SudokuGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[9][9];
		sceneWidth = 700;
		sceneHeight = 700;
	}
		

	/**
	 * Draws the Sudoku playboard, this playboard is filled with 9x9 Stackpanes with SudokuFields inside them
	 * This container nesting is needed to ensure correct scaling of the playboard 
	 */
	public GridPane createBoard() {
		final SimpleDoubleProperty textSize = new SimpleDoubleProperty(10);
		
		playBoard = new GridPane();

		playBoard.setPadding(new Insets(5, 5, 5, 5));

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("sudokucell");
				
				//align the size of the cell to the playboards size
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));

				textField[row][col] = new SudokuField("");
				textField[row][col].setMaxSize(200, 200);
				textField[row][col].getStyleClass().add("textfieldBasic");
				textField[row][col].setAlignment(Pos.CENTER);
				
				//draws the lines between the SudokuFields to achieve the classic Sudoku look
				cell.pseudoClassStateChanged(right, row == 2 || row == 5);
				cell.pseudoClassStateChanged(bottom, col == 2 || col == 5);
				
				//align the size of the Text inside a SudokuField to the window size
				textField[row][col].styleProperty().bind(Bindings.concat("-fx-font-size: ", textSize.asString()));
				textSize.bind(gameRoot.widthProperty().add(gameRoot.heightProperty()).divide(50));
				
				cell.getChildren().add(textField[row][col]);

				playBoard.add(cell, row, col);
			}

		}

		playBoard.setAlignment(Pos.CENTER);

		return playBoard;
	}
	
	/**
	 * Creates the UI components which are needed for a manual FreeForm Game
	 */
	@Override
	public void createManualControls() {
		Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
		customNumbersDone = new Button("");
		customNumbersDone.setGraphic(doneGraphic);
		customNumbersDone.setVisible(false);
		toolBar.getItems().add(3,customNumbersDone);
	}
	

}
