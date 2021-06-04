package application;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;

public class FreeFormGameBuilder extends BasicGameBuilder {
	
	
	public FreeFormGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[9][9];
		width = 670;
		height = 670;
	}

	CustomColorPicker colorPicker;
	StackPane cell;

	@SuppressWarnings("rawtypes")
	protected List<ChangeListener> colorListeners = new ArrayList<>();

	

	public GridPane createBoard() {
		playBoard = new GridPane();
		createColorBox();
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				cell = new StackPane();

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
			

				textField[row][col] = new SudokuField("");
				textField[row][col].setPlayable(true);

				textField[row][col].setMaxSize(200, 200);
				textField[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 15));
				textField[row][col].setAlignment(Pos.CENTER);

				
				textField[row][col].addFreeFormColorListener(cmb);

				cell.getChildren().add(textField[row][col]);

				playBoard.add(cell, row, col);
			}
			

		}

		playBoard.setAlignment(Pos.CENTER);
		
		return playBoard;
	}
	

	public void createColorBox() {
//		  colorPicker = new CustomColorPicker();
//	      cmb = colorPicker.createColorPicker();
	}
	
	
		@Override
		public void createNumbers() {
			// TODO Auto-generated method stub
			controller.createGame();
		}

		@Override
		public void createManualControls() {
			// TODO Auto-generated method stub
		colorPicker = new CustomColorPicker();
		cmb = colorPicker.createColorPicker();
			
			Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
			Glyph colorDoneGraphic = fontAwesome.create(FontAwesome.Glyph.PAINT_BRUSH);
			HBox colorInstructions = new HBox();
			colorInstructions.setPadding(new Insets(0,0,0,20));
			colorInstructions.setAlignment(Pos.CENTER);
			
			customNumbersDone = new Button("");
			customNumbersDone.setGraphic(doneGraphic);
			customNumbersDone.setVisible(false);
			
			customColorsDone = new Button("");
			customColorsDone.setGraphic(colorDoneGraphic);
			
			
			
		//	colorInstructions.getChildren().addAll(customColorsDone, cmb);
			customColorsDone.setVisible(false);
			cmb.setVisible(false);
			
		//	toolBar.getItems().add(3,customNumbersDone);
			toolBar.getItems().add(3,customColorsDone);
			toolBar.getItems().add(4,cmb);
		}
		
}