package application;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;


/**
 * @author grube
 * Defines the UI representation of a FreeForm Game
 */
public class FreeFormGameBuilder extends BasicGameBuilder {
	
	
	public FreeFormGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[9][9];
		sceneWidth = 670;
		sceneHeight = 670;
	}

	
	/**
	 * Draws the FreeForm playboard, this playboard is filled with 9x9 Stackpanes with SudokuFields inside them
	 * This container nesting is needed to ensure correct scaling of the playboard 
	 */
	@Override
	public GridPane createBoard() {
		playBoard = new GridPane();
		StackPane cell;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				cell = new StackPane();

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
			

				textField[row][col] = new SudokuField("");
				textField[row][col].setMaxSize(200, 200);
				textField[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 15));
				textField[row][col].setAlignment(Pos.CENTER);
				textField[row][col].addFreeFormColorListener(comboColorBox);

				cell.getChildren().add(textField[row][col]);

				playBoard.add(cell, row, col);
			}
		}

		playBoard.setAlignment(Pos.CENTER);
		
		return playBoard;
	}
	
	
		@Override
		public void createNumbers() {
			controller.createGame();
		}

		/**
		 * Creates the UI components which are needed for a manual FreeForm Game
		 */
		@Override
		public void createManualControls() {
			CustomColorPicker colorPicker = new CustomColorPicker();
			comboColorBox = colorPicker.createColorPicker();
			
			Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
			Glyph colorDoneGraphic = fontAwesome.create(FontAwesome.Glyph.PAINT_BRUSH);
			
			customNumbersDone = new Button("");
			customNumbersDone.setGraphic(doneGraphic);
			customNumbersDone.setVisible(false);
			
			customColorsDone = new Button("");
			customColorsDone.setGraphic(colorDoneGraphic);
			
			customColorsDone.setVisible(false);
			comboColorBox.setVisible(false);
			
			toolBar.getItems().add(3,customColorsDone);
			toolBar.getItems().add(3,comboColorBox);
		}
		
		
}