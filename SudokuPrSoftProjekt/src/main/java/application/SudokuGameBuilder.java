package application;

import controller.BasicController;
import controller.SudokuController;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Gamestate;
import logic.SudokuLogic;

public class SudokuGameBuilder extends BasicGameBuilder {
	
	  BorderPane pane = new BorderPane();
	  Scene sudoku = new Scene(pane,500,500);
	  BasicController controller;

	
	public void initializeScene() {
		
		
	pane.setCenter(createBoard());
		
	   createPlayButtons(pane);
	   createMenuBar(pane);
	   createBackButton(pane);
	   
	  controller = new SudokuController(this);
	   
	  create.setOnAction(controller::createGameHandler);
	  
	  play.setOnAction(controller::enableEditHandler);
	  autosolve.setOnAction(controller::checkHandler);
	  
	/// if(easy.isSelected()) controller.setDifficulty(6);
	  easy.setOnAction(controller::easyHandler);
	  medium.setOnAction(controller::mediumHandler);
	  hard.setOnAction(controller::hardHandler);
	  
	  newGame.setOnAction(controller::newGameHandler);
	  
	  save.setOnAction(controller::saveHandler);
	  
	
	  
	  
	  check.setOnAction(controller::checkHandler);
	  
	  autosolve.setOnAction(controller::autoSolveHandler);
	  
	  samuraiMode.setOnAction(controller::switchToSamurai);
	  
	  freeFormMode.setOnAction(controller::switchToFreeForm);
	  
	  sudokuMode.setDisable(true);
	  
	  
	  
	  sudoku.getStylesheets().add("main/resources/CSS/sudoku.css");
	//  return sudoku;
	 
	}
	
	
		@Override
		public GridPane createBoard() {
			playBoard = new GridPane();
			playBoard.setPadding(new Insets(5, 5, 5, 5));
			
			playBoard.setVgap(1);
			playBoard.setHgap(1);
			
			textFields = new SudokuField[9][9];
			

			PseudoClass right = PseudoClass.getPseudoClass("right");
			PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					StackPane cell = new StackPane();
					cell.getStyleClass().add("cell");

						
					SudokuField sudokuField = new SudokuField("0");
					
					textFields[i][j] = sudokuField;
					cell.pseudoClassStateChanged(right, i == 2 || i == 5);

					cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);
					sudokuField.setStyle("-fx-pref-width: 2em;");
					textFields[i][j].setDisable(true);
				
					cell.getChildren().add(sudokuField);
					
					
					
					playBoard.add(cell, i, j);
				}
				
			}
		
				playBoard.setAlignment(Pos.CENTER);
			return playBoard;
		}	
			
		
		
	public SudokuField[][] getTextField() {
		return textFields;
	}


	@Override
	public Scene getScene() {
		return sudoku;
	}
			
			
			
			
}

	