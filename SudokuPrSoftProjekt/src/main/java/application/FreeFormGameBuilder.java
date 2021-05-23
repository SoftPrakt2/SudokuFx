package application;

import java.util.ArrayList;

import controller.GameController;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;

public class FreeFormGameBuilder extends BasicGameBuilder {

	ColorPicker picker = new ColorPicker(Color.AQUA);
	
	ToggleButton colorButton;
	
	protected ArrayList<ChangeListener> colorListeners = new ArrayList<>();
	

	public FreeFormGameBuilder(BasicGameLogic model) {
		super(model);
		scene = new Scene(pane, 670, 670);
		textField = new SudokuField[9][9];
		gameType = "FreeForm";
	}

	public Scene initializeScene() {
		controller = new GameController(this, model);

		pane.setCenter(createBoard());

		createMenuBar(pane);
		createPlayButtons(pane);

		colorButton = new ToggleButton("Color");
		
		toolbar.getItems().add(picker);
		toolbar.getItems().add(colorButton);

		scene.getStylesheets().add("css/sudoku.css");

		return scene;
	}

	public GridPane createBoard() {
		playBoard = new GridPane();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				StackPane cell = new StackPane();
			

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
//			

				textField[i][j] = new SudokuField("");
				textField[i][j].setPlayable(true);
			
				
				textField[i][j].setMaxSize(100, 100);
				textField[i][j].setFont(Font.font("Arial", FontWeight.BOLD, 15));
				textField[i][j].setAlignment(Pos.CENTER);
				
				SudokuField s = textField[i][j];
				
				s.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
				    if (isNowFocused) {
				        s.setStyle("-fx-background-color: #"+picker.getValue().toString().substring(2));
				        System.out.println(picker.getValue().toString().substring(2));
				    }
				});
				
				s.setColor(picker.getValue().toString().substring(2));
				
				cell.getChildren().add(textField[i][j]);

				playBoard.add(cell, i, j);
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

	@Override
	public Scene getScene() {
		// TODO Auto-generated method stub
		return scene;
	}
	
	public ToggleButton getColorButton() {
		return colorButton;
	}
	

}