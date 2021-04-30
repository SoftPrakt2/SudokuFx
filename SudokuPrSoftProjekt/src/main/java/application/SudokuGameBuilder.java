package application;

import controller.BasicController;
import controller.SudokuController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SudokuGameBuilder extends BasicGameBuilder {
	
	 
	
	
	  BorderPane pane = new BorderPane();
	  Scene sudoku = new Scene(pane,670,670);
	  BasicController controller;
	  
	  
		
	
	public Scene initializeScene() {
		textField = new SudokuField[9][9];
		
		gamePoints = 10;
		pane.setCenter(createBoard());
		pane.setPadding(new Insets(50,50,50,50));
		
		controller =  new SudokuController(this);
	   createPlayButtons(pane);
	   createMenuBar(pane);
	  
	   
	  createGameItem.setOnAction(controller::createGameHandler);
	  
	  autosolve.setOnAction(controller::checkHandler);
	  
	  clearFieldItem.setOnAction(controller::newGameHandler);
	  
	  save.setOnAction(controller::saveHandler);
	  
	  check.setOnAction(controller::checkHandler);
	  
	  autosolve.setOnAction(controller::autoSolveHandler);
	  
	  done.setOnAction(controller::manuelDoneHandler);

	  reset.setOnAction(controller::resetHandler);
	  
	  mainMenuItem.setOnAction(controller::switchToMainMenu);
	 
	  pane.maxWidthProperty().bind(sudoku.widthProperty());
	  
	  hintButton.setOnAction(controller::hintHandeler);
	  
	
	  sudoku.getStylesheets().add("/CSS/sudoku.css");
	  
	  
	return sudoku;
	}
	
	
	//statt createGame in inizializeScene? erstellt die zahlen im spielfeld nach der eingestellten schwierigkeit
	
	

	@Override
	public GridPane createBoard() {
		playBoard = new GridPane();
	
		playBoard.setPadding(new Insets(5, 5, 5, 5));

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");
				
				
				
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(12));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(12));
			
					textField[i][j] = new SudokuField("");
			
				
					textField[i][j].setMaxSize(50, 50);
					textField[i][j].setFont(Font.font("Arial", FontWeight.BOLD,15));
					textField[i][j].setAlignment(Pos.CENTER);
					
					cell.pseudoClassStateChanged(right, i == 2 || i == 5);

					cell.pseudoClassStateChanged(bottom, j == 2 || j == 5);
				
					textField[i][j].setDisable(true);
				
					cell.getChildren().add(textField[i][j]);
				
					
					
					playBoard.add(cell, i, j);
				}
		
			}
		
				playBoard.setAlignment(Pos.CENTER);
				
				
			return playBoard;
		}

		
		
		public void createNumbers() {
			controller.createGame(difficulty);
		}

		@Override
		public Scene getScene() {
			return sudoku;
		}

		public SudokuField[][] getTextField() {
			return textField;
		}
		

}

	