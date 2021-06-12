package application;



import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import logic.BasicGameLogic;


/**
 * @author grube
 * Defines the UI representation of a FreeForm Game
 */
public class FreeFormGameBuilder extends BasicGameBuilder {
	
	
	public FreeFormGameBuilder(BasicGameLogic model) {
		super(model);
		setTextField(new SudokuField[9][9]);
		setSceneWidth(670);
		setSceneHeight(670);
	}

	
	/**
	 * Draws the FreeForm playboard, this playboard is filled with 9x9 Stackpanes with SudokuFields inside them
	 * This container nesting is needed to ensure correct scaling of the playboard 
	 */
	@Override
	public GridPane createBoard() {
		final SimpleDoubleProperty textSize = new SimpleDoubleProperty(10);
		
		GridPane playBoard = new GridPane();
		StackPane cell;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				cell = new StackPane();
				
				//align the size of the cell to the playboards size
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
			

				getTextField()[row][col] = new SudokuField("");
				getTextField()[row][col].setMaxSize(200, 200);
				getTextField()[row][col].setAlignment(Pos.CENTER);
				

				//adds a listener to the textfields to listen to changes regarding background color
				//this is needed when creating a custom freeform game 
				getTextField()[row][col].addFreeFormColorListener(comboColorBox);

			
				Font font = new Font("Arial",textSize.get());
				getTextField()[row][col].setFont(font);
				
				cell.getChildren().add(getTextField()[row][col]);

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
			CustomColorPicker colorPicker = new CustomColorPicker();
			comboColorBox = colorPicker.createColorPicker();
			
			Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
			Glyph colorDoneGraphic = fontAwesome.create(FontAwesome.Glyph.PAINT_BRUSH);
			
			setCustomNumbersDone(new Button(""));
			getCustomNumbersDone().setGraphic(doneGraphic);
			getCustomNumbersDone().setVisible(false);
			
			setCustomColorsDone(new Button(""));
			getCustomColorsDone().setGraphic(colorDoneGraphic);
			
			getCustomColorsDone().setVisible(false);
			comboColorBox.setVisible(false);
			
			toolBar.getItems().add(3,getCustomColorsDone());
			toolBar.getItems().add(3,comboColorBox);
		}
		
		
}