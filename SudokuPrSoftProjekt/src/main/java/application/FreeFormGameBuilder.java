package application;

import java.util.ArrayList;



import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import logic.BasicGameLogic;

public class FreeFormGameBuilder extends BasicGameBuilder {


	ComboBox<Color> cmb;

	protected ArrayList<ChangeListener> colorListeners = new ArrayList<>();

	public FreeFormGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[9][9];
		
	}

	public GridPane createBoard() {
		playBoard = new GridPane();
		createColorBox();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				StackPane cell = new StackPane();

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(10));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(10));
//			

				textField[row][col] = new SudokuField("");
				textField[row][col].setPlayable(true);

				textField[row][col].setMaxSize(200, 200);
				textField[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 15));
				textField[row][col].setAlignment(Pos.CENTER);

			//	textField[row][col].addFreeFormColorListener(cmb);

				
				
				cell.getChildren().add(textField[row][col]);

				playBoard.add(cell, row, col);
			}
			

		}

		playBoard.setAlignment(Pos.CENTER);
		
		return playBoard;
	}
	

	
	
	
	
	
	
	public void createColorBox() {
	
		 
		 cmb = new ComboBox<Color>();
	        cmb.getItems().addAll(Color.RED,Color.GREEN,Color.BLUE);

	        cmb.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {
	            @Override public ListCell<Color> call(ListView<Color> p) {
	                return new ListCell<Color>() {
	                    private final Rectangle rectangle;
	                    { 
	                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
	                        rectangle = new Rectangle(10, 10);
	                    }
	                    
	                    @Override protected void updateItem(Color item, boolean empty) {
	                        super.updateItem(item, empty);
	                        
	                        if (item == null || empty) {
	                            setGraphic(null);
	                        } else {
	                            rectangle.setFill(item);
	                            setGraphic(rectangle);
	                           
	                        }
	                   }
	              };
	          }
	       });
		
		 toolbar.getItems().add(4, cmb);
	}
	

	@Override
	public SudokuField[][] getTextField() {
		
		return textField;
	}
	

	@Override
	public void createNumbers() {
		controller.createGame();

	}

//	public ToggleButton getColorButton() {
//		return colorButton;
//	}

}