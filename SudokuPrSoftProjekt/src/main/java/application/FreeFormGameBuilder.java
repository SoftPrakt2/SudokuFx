package application;



import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;

/**
 * @author gruber
 *  defines the UI representation of a FreeForm game
 */
public class FreeFormGameBuilder extends BasicGameBuilder {

	public FreeFormGameBuilder(BasicGameLogic model) {
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
	 * Draws the FreeForm playboard, this playboard is filled with 9x9 Stackpanes
	 * with SudokuTextFields inside them.
	 * This container nesting is needed to ensure correct scaling of the playboard
	 */
	@Override
	public GridPane createBoard() {

		GridPane playBoard = new GridPane();
		StackPane cell;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				cell = new StackPane();

				// align the size of the cell to the playboards size
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));

				getTextField()[row][col] = new SudokuTextField("");
				getTextField()[row][col].setMaxSize(500, 500);
				getTextField()[row][col].setAlignment(Pos.CENTER);
				getTextField()[row][col].getStyleClass().add("textfieldBasic");

				// adds a listener to the sudokuTextFields to listen to changes regarding background
				// color
				// this is needed when creating a manual freeform game
				getTextField()[row][col].addFreeFormColorListener(comboColorBox);

				// ensures scaling of the text inside the sudokuTextFields when the window
				// increases in size
				ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(getTextField()[row][col].getFont());
				getTextField()[row][col].fontProperty().bind(fontTracking);
				gameRoot.widthProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth,
							Number newWidth) {
						fontTracking.set(Font.font("Arial", FontWeight.BOLD, newWidth.doubleValue() / 30));
					}
				});

				cell.getChildren().add(getTextField()[row][col]);

				playBoard.add(cell, row, col);
			}
		}

		playBoard.setAlignment(Pos.CENTER);

		return playBoard;
	}

	/**
	 * creates the UI components which are needed for a manual FreeForm game
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

		toolBar.getItems().add(3, getCustomColorsDone());
		toolBar.getItems().add(3, comboColorBox);
	}

}