package application;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;


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

	
	
	
	/**
	 * Constructor of the class
	 * @param model the corresponding GameLogic model of this GameBuilder
	 */
	public SamuraiGameBuilder(BasicGameLogic model) {
		super(model);
		setTextField(new SudokuTextField[21][21]);
		
		setSceneWidth((bounds.getWidth() * 0.53));
		setSceneHeight(bounds.getHeight() * 0.9);
		}

		
	/**
	 * Draws the SamuraiGame playboard, this playboard is filled with 21x21 Stackpanes with SudokuTextFields inside them.
	 * This container nesting is needed to ensure correct scaling of the playboard.
	 * To achieve the desired look of the playboard, 
	 * empty StackPane cells are put into the GridPane on positions where the player should not be able to insert numbers.
	 * These empty StackPane cells are then filled with SudokuTextFields with "-1" as text to ensure that these fields are not recognized by game logic methods
	 * 
	 */
	@Override
	public GridPane createBoard() {
		final SimpleDoubleProperty textSize = new SimpleDoubleProperty(10);
		GridPane playBoard = new GridPane();

		gameRoot.setPadding(new Insets(5, 5, 5, 5));


		for (int row = 0; row < getTextField().length; row++) {
			for (int col = 0; col < getTextField()[row].length; col++) {
				StackPane cell = new StackPane();
				//styles the cell with the associated CSS styleclass in the sudoku css files
				cell.getStyleClass().add("samuraicell");
				//aligns the size of the cells to the current size of the playboard
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(22));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(22));
				
				
				//empty StackPane cells and SudokuFields
				StackPane cellEmpty = new StackPane();
				SudokuTextField emptyField = new SudokuTextField("-1");
				cellEmpty.getChildren().add(emptyField);
				cellEmpty.setDisable(true);
				getTextField()[row][col] = emptyField;
				
				//depending on the position in the matrix the cells will be styled as empty cell 
				//or as playable cell 
				if ((row == 9 || row == 10 || row == 11) && (col < 6 || col > 14)) {
					emptyField.getStyleClass().add("emptySamuraiCell");
				} else if ((row < 6 || row > 14) && (col == 9 || col == 10 || col == 11)) {
					emptyField.getStyleClass().add("emptySamuraiCell");
				} else {
			
					getTextField()[row][col] = new SudokuTextField("");
					//align the size of the Text inside a SudokuTextField to the window size
					getTextField()[row][col].styleProperty().bind(Bindings.concat("-fx-font-size: ", textSize.asString()));
					textSize.bind(gameRoot.widthProperty().add(gameRoot.widthProperty()).divide(100));
					textSize.bind(gameRoot.heightProperty().add(gameRoot.heightProperty()).divide(100));
					
					//style and position the SudokuTextField 
					getTextField()[row][col].getStyleClass().add("textfieldBasic");
					getTextField()[row][col].setMaxSize(500, 500);
					getTextField()[row][col].setMinSize(1, 1);
					getTextField()[row][col].setAlignment(Pos.CENTER);
					
					//add the SudokuTextField to the cell
					cell.getChildren().add(getTextField()[row][col]);
					playBoard.add(cell, row, col);
					
					
				}
			}

		}
		drawColors();
		playBoard.setAlignment(Pos.CENTER);
		return playBoard;
	}

	/**
	 * This method styles the play board
	 * Due to the irregularity of the SudokuTextFields which should be styled, it was needed to manually determine which specific cells should be styled.
	 * It is recommended to improve this method in further versions of the program to ensure better reusability.
	 * The styling of the SudokuTextFields is done by adding a styleclass from the projects CSS File
	 */
	public void drawColors() {
		for(int i = 0; i < getTextField().length; i++) {
			for(int j = 0; j < getTextField()[i].length; j++) {
				if((j < 3 ||(j> 5 && j <9) || (j>11 && j<15) || j>17 && j<21) &&(i != 3&& i!=4 && i !=5 && i!=15 && i!=16 && i!=17)) {
					if(((j > 5 && j<9) || (j >11 && j < 18))  && (i > 8 && i <12)) {
						//These fields should get no custom background color
					} else {
						getTextField()[i][j].getStyleClass().add("coloredSamuraiCell");
					}
			}
			}
		}
		for(int i = 0; i < getTextField().length; i++) {
			for(int j = 0; j < getTextField()[i].length; j++) {
				if( ((j > 2 && j< 6) || (j > 14 && j <18))
						&& ( (i > 2 && i < 6) || (i > 14 && i <18)))	{
					getTextField()[i][j].getStyleClass().add("coloredSamuraiCell");
				}
				if((j > 8 && j < 12) && (i > 8 && i < 12)) {
					getTextField()[i][j].getStyleClass().add("coloredSamuraiCell");
				}
			}
		}
		
		
	}

	/**
	 * Creates the UI components which are needed for a manual Samuari game
	 */
	@Override
	public void createManualControls() {
		Glyph doneGraphic = fontAwesome.create(FontAwesome.Glyph.LOCK);
		setCustomNumbersDone(new Button(""));
		getCustomNumbersDone().setGraphic(doneGraphic);
		getCustomNumbersDone().setVisible(false);
		toolBar.getItems().add(3,getCustomNumbersDone());
	}
	
}