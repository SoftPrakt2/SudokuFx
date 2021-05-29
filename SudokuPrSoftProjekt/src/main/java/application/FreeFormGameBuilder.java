package application;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;

public class FreeFormGameBuilder extends BasicGameBuilder {

	ColorPicker picker = new ColorPicker(Color.AQUA);

	ToggleButton colorButton;

	protected ArrayList<ChangeListener> colorListeners = new ArrayList<>();

	public FreeFormGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[9][9];
	}

	public GridPane createBoard() {
		playBoard = new GridPane();

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				StackPane cell = new StackPane();

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
//			

				textField[row][col] = new SudokuField("");
				textField[row][col].setPlayable(true);

				textField[row][col].setMaxSize(100, 100);
				textField[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 15));
				textField[row][col].setAlignment(Pos.CENTER);

				SudokuField s = textField[row][col];

				s.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
					if (isNowFocused) {
						s.setStyle("-fx-background-color: #" + picker.getValue().toString().substring(2));
						System.out.println(picker.getValue().toString().substring(2));
					}
				});

				s.setColor(picker.getValue().toString().substring(2));

				cell.getChildren().add(textField[row][col]);

				playBoard.add(cell, row, col);
			}

		}

		playBoard.setAlignment(Pos.CENTER);

		return playBoard;
	}

	@Override
	public SudokuField[][] getTextField() {
		// TODO Auto-generated method stub
		return textField;
	}

	@Override
	public void createNumbers() {
		// TODO Auto-generated method stub

	}

	public ToggleButton getColorButton() {
		return colorButton;
	}

}