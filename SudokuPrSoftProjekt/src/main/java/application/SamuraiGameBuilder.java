package application;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.GameController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.BasicGameLogic;

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
		sceneWidth = 1050;
		sceneHeight = 1050;
	}

	
	
	/**
	 * Zeichnet ein Samurai-Sudoku-Feld
	 */
	
	@Override
	public GridPane createBoard() {
		controller = new GameController(this, model);
		playBoard = new GridPane();

		pane.setPadding(new Insets(5, 5, 5, 5));


		for (int row = 0; row < textField.length; row++) {
			for (int col = 0; col < textField[row].length; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("samuraicell");

				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(22));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(22));

				StackPane cellEmpty = new StackPane();
				SudokuField emptyField = new SudokuField("-1");
				

				
				cellEmpty.getChildren().add(emptyField);
				cellEmpty.setDisable(true);
				textField[row][col] = emptyField;

				if ((row == 9 || row == 10 || row == 11) && (col < 6 || col > 14)) {
					emptyField.getStyleClass().add("emptySamuraiCell");
				} else if ((row < 6 || row > 14) && (col == 9 || col == 10 || col == 11)) {
					emptyField.getStyleClass().add("emptySamuraiCell");
				} else {

					textField[row][col] = new SudokuField("");
			
					textField[row][col].getStyleClass().add("samuraiFont");
					
					textField[row][col].setMaxSize(100, 100);
					textField[row][col].setAlignment(Pos.CENTER);


					cell.getChildren().add(textField[row][col]);

					playBoard.add(cell, row, col);
					
				}
			}

		}
	
		drawColors();
		playBoard.setAlignment(Pos.CENTER);
		return playBoard;
	}

	// draws the Line for the Samurai PlayField, PseudoClass is used for the lines
	
	
	public void drawColors() {
		for(int i = 0; i < textField.length; i++) {
			for(int j = 0; j < textField[i].length; j++) {
				if((j < 3 ||(j> 5 && j <9) || (j>11 && j<15) || j>17 && j<21) &&(i != 3&& i!=4 && i !=5 && i!=15 && i!=16 && i!=17)) {
					if(((j > 5 && j<9) || (j >11 && j < 18))  && ((i > 8 && i <12))) {
				
					} else {
						textField[i][j].getStyleClass().add("coloredSamuraiCell");
					}
			}
			}
		}
		for(int i = 0; i < textField.length; i++) {
			for(int j = 0; j < textField[i].length; j++) {
				if( ((j > 2 && j< 6) || (j > 14 && j <18))
						&& ( ((i > 2 && i < 6) || (i > 14 && i <18))))	{
					textField[i][j].getStyleClass().add("coloredSamuraiCell");
				}
				if((j > 8 && j < 12) && (i > 8 && i < 12)) {
					textField[i][j].getStyleClass().add("coloredSamuraiCell");
				}
			}
		}
		
		
	}


	@Override
	public void createManualControls() {
		// TODO Auto-generated method stub
		Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
		customNumbersDone = new Button("");
		customNumbersDone.setGraphic(doneGraphic);
		customNumbersDone.setVisible(false);
		toolBar.getItems().add(3,customNumbersDone);
	}
	

	

	/**
	 * Getter und Setter für die Variablen dieser Klasse
	 */

}