package application;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import controller.GameController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import logic.BasicGameLogic;

/**
 * @author grube
 * Defines the UI representation of a Samurai Game
 */
public class SamuraiGameBuilder extends BasicGameBuilder {

	public SamuraiGameBuilder(BasicGameLogic model) {
		super(model);
		textField = new SudokuField[21][21];
		sceneWidth = 1050;
		sceneHeight = 1050;
	}

	
	
	
	/**
	 * Draws the SamuraiGame playboard, this playboard is filled with 21x21 Stackpanes with SudokuFields inside them
	 * This container nesting is needed to ensure correct scaling of the playboard 
	 * To achieve the desired look of the playboard, 
	 * empty StackPane cells are put into the GridPane on positions where the player should not be able to insert numbers
	 * These StackPane cells are then filled with SudokuField with "-1" as Text to ensure that these Fields are not recognized by game logic methods
	 * 
	 */
	@Override
	public GridPane createBoard() {
		final SimpleDoubleProperty textSize = new SimpleDoubleProperty(10);
		controller = new GameController(this, model);
		playBoard = new GridPane();

		gameRoot.setPadding(new Insets(5, 5, 5, 5));


		for (int row = 0; row < textField.length; row++) {
			for (int col = 0; col < textField[row].length; col++) {
				StackPane cell = new StackPane();
				//Styles the cell with the assoziated CSS styleclass in the sudoku css files
				cell.getStyleClass().add("samuraicell");
				//aligns the size of the cells to the current size of the playboard
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(22));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(22));
				
				
				//empty StackPane cells and SudokuFields
				StackPane cellEmpty = new StackPane();
				SudokuField emptyField = new SudokuField("-1");
				cellEmpty.getChildren().add(emptyField);
				cellEmpty.setDisable(true);
				textField[row][col] = emptyField;
				
				//depending on the position in the matrix the cells will be styled as empty cell 
				//or as playable cell 
				if ((row == 9 || row == 10 || row == 11) && (col < 6 || col > 14)) {
					emptyField.getStyleClass().add("emptySamuraiCell");
				} else if ((row < 6 || row > 14) && (col == 9 || col == 10 || col == 11)) {
					emptyField.getStyleClass().add("emptySamuraiCell");
				} else {
			
					textField[row][col] = new SudokuField("");
					//align the size of the Text inside a SudokuField to the window size
					textField[row][col].styleProperty().bind(Bindings.concat("-fx-font-size: ", textSize.asString()));
					textSize.bind(gameRoot.widthProperty().add(gameRoot.heightProperty()).divide(100));
					
					//style the textfield with the values defined in the styleclass
					textField[row][col].getStyleClass().add("samuraiFont");
					textField[row][col].setMaxSize(100, 100);
					textField[row][col].setAlignment(Pos.CENTER);
					
					//add the textfield to the cell
					cell.getChildren().add(textField[row][col]);
					playBoard.add(cell, row, col);
					
					
				}
			}

		}
		drawColors();
		playBoard.setAlignment(Pos.CENTER);
		return playBoard;
	}

	/**
	 * This method styles the PlayBoard
	 * Due to the irregularity of the SudokuFields which should be styled, it was needed to manually determine which specific cells should be styled
	 * It is recommended to improve this method in further Versions of the program to ensure better reusability
	 * The styling of the SudokuFields by adding a Styleclass from the projects CSS File
	 */
	public void drawColors() {
		for(int i = 0; i < textField.length; i++) {
			for(int j = 0; j < textField[i].length; j++) {
				if((j < 3 ||(j> 5 && j <9) || (j>11 && j<15) || j>17 && j<21) &&(i != 3&& i!=4 && i !=5 && i!=15 && i!=16 && i!=17)) {
					if(((j > 5 && j<9) || (j >11 && j < 18))  && (i > 8 && i <12)) {
						//These fields should get no background color
					} else {
						textField[i][j].getStyleClass().add("coloredSamuraiCell");
					}
			}
			}
		}
		for(int i = 0; i < textField.length; i++) {
			for(int j = 0; j < textField[i].length; j++) {
				if( ((j > 2 && j< 6) || (j > 14 && j <18))
						&& ( (i > 2 && i < 6) || (i > 14 && i <18)))	{
					textField[i][j].getStyleClass().add("coloredSamuraiCell");
				}
				if((j > 8 && j < 12) && (i > 8 && i < 12)) {
					textField[i][j].getStyleClass().add("coloredSamuraiCell");
				}
			}
		}
		
		
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