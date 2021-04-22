package application;

import controller.BasicController;
import controller.SudokuController;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SudokuGameBuilder extends BasicGameBuilder {
	
	  BorderPane pane = new BorderPane();
	  Scene sudoku = new Scene(pane,800,800);
	  BasicController controller;
	  
	  
	 
	
	public void initializeScene() {
		pane.setCenter(createBoard());

		
		controller =  new SudokuController(this);
	   createPlayButtons(pane);
	   createMenuBar(pane);
	  
	  controller.createGame();
	  
	   
	  create.setOnAction(controller::createGameHandler);
	  
	  play.setOnAction(controller::enableEditHandler);
	  autosolve.setOnAction(controller::checkHandler);
	  
	
	  easy.setOnAction(controller::easyHandler);
	  medium.setOnAction(controller::mediumHandler);
	  hard.setOnAction(controller::hardHandler);
	  
	  newGame.setOnAction(controller::newGameHandler);
	  
	  save.setOnAction(controller::saveHandler);
	  
	  check.setOnAction(controller::checkHandler);
	  
	  autosolve.setOnAction(controller::autoSolveHandler);
	  


	  
	  
	  mainMenuItem.setOnAction(controller::switchToMainMenu);
	 
	  pane.maxWidthProperty().bind(sudoku.widthProperty());
	  
	
	  sudoku.getStylesheets().add("/CSS/sudoku.css");
	  
	// return sudoku;
	}
	
	
		@Override
		public GridPane createBoard() {
			playBoard = new GridPane();
			
		
		
			playBoard.prefHeightProperty().bind(pane.heightProperty());
			
			playBoard.prefWidthProperty().bind(pane.widthProperty());
		
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
				
					
				
					
					
					
					cell.prefHeightProperty().bind(playBoard.heightProperty().divide(11));
					cell.prefWidthProperty().bind(playBoard.widthProperty().divide(11));

						
					SudokuField sudokuField = new SudokuField("");
				
					
					textFields[i][j] = sudokuField;
					textFields[i][j].setMaxSize(50, 50);
					textFields[i][j].setFont(Font.font("Arial", FontWeight.BOLD,15));
					textFields[i][j].setAlignment(Pos.CENTER);
					
					
					cell.pseudoClassStateChanged(right, i == 2 || i == 5);

					cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);
				
					textFields[i][j].setDisable(true);
				
					cell.getChildren().add(sudokuField);
					
					
					
					playBoard.add(cell, i, j);
				}
				
			}
		
				playBoard.setAlignment(Pos.CENTER);
		
				
			return playBoard;
		}


		@Override
		public Scene getScene() {
			// TODO Auto-generated method stub
			return sudoku;
		}	
			
		
		public BasicController getController() {
			return controller;
		}




			
			
			
			
}

	