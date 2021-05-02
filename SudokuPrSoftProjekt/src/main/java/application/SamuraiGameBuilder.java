package application;



import controller.BasicController;
import controller.GameController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import logic.Gamestate;
import logic.SamuraiLogic;

public class SamuraiGameBuilder extends BasicGameBuilder {
	
	
	
	 BorderPane pane;
	 Scene samurai;
	 BasicController controller;
	
	 //anders gemacht 2404
	public Scene initializeScene() {
		
		pane = new BorderPane();
		samurai = new Scene(pane,1000,1000);
	
		pane.setCenter(createBoard());
	
	    createPlayButtons(pane);
	    createMenuBar(pane);
	   
	    
	    controller = new GameController(this, new SamuraiLogic(Gamestate.OPEN, 0.0, false));
	    
	    createGameItem.setOnAction(controller::createGameHandler);
		  
		  autosolve.setOnAction(controller::checkHandler);
		  
		  clearFieldItem.setOnAction(controller::newGameHandler);
		  
		  save.setOnAction(controller::saveHandler);
		  
		  check.setOnAction(controller::checkHandler);
		  
		  autosolve.setOnAction(controller::autoSolveHandler);
		  
		  done.setOnAction(controller::manuelDoneHandler);

		  reset.setOnAction(controller::resetHandler);
		  
		  mainMenuItem.setOnAction(controller::switchToMainMenu);
		 
		  pane.maxWidthProperty().bind(samurai.widthProperty());
		  
		  hintButton.setOnAction(controller::hintHandeler);
	
	    samurai.getStylesheets().add("/CSS/sudoku.css");
	    
	  return samurai;
	}
	


	@Override
	public GridPane createBoard() {
		playBoard = new GridPane();
		
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		playBoard.setVgap(1);
		playBoard.setHgap(1);
		
		textField = new SudokuField[21][21];
		
		
		PseudoClass right = PseudoClass.getPseudoClass("right");
	    PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
	
		for(int i = 0; i <21; i++) {
			for(int j = 0; j < 21; j++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");
				
				cell.prefHeightProperty().bind(playBoard.heightProperty().divide(25));
				cell.prefWidthProperty().bind(playBoard.widthProperty().divide(25));
				
				StackPane cellEmpty = new StackPane();
				//cellEmpty.getStyleClass().add("hidden");
				SudokuField empty = new SudokuField("-1");
				empty.setStyle("-fx-pref-width: 2em;");
				
				
				cellEmpty.getChildren().add(empty);
				cellEmpty.setDisable(true);
				textField[i][j] = empty;
				
			if((i == 9 || i == 10 || i ==11) && (j < 6 || j > 14)) {
				
			//	playBoard.add(cellEmpty,spalte,zeile);
				 
			} else if((i <6 || i > 14) && (j ==9 || j == 10 || j == 11)) {

			//	playBoard.add(cellEmpty,spalte,zeile);

			} else {
				
				SudokuField sudokuField = new SudokuField("");
				
				textField[i][j] = sudokuField;
				textField[i][j].setMaxSize(50, 50);
				textField[i][j].setAlignment(Pos.CENTER);
				
			
				
				cell.pseudoClassStateChanged(right, i == 2 || i == 5 || i == 11 || i == 14 || i == 17);
	          //  cell.pseudoClassStateChanged(bottom, j == 2 || j == 5 ||j ==8 ||j == 11|| j == 14 || j == 17);
	            
				
				if(i==8 && (j > 5 && j <= 14)) cell.pseudoClassStateChanged(right, i==8);
	            if(j==14 && (i < 9 || i >11)) cell.pseudoClassStateChanged(bottom,j==14);
	            if(j==17 && (i < 9 || i >11)) cell.pseudoClassStateChanged(bottom,j==17);
	            if(j==2 && (i < 9 || i >11)) cell.pseudoClassStateChanged(bottom,j==2);
	            if(j==5 && (i < 9 || i >11)) cell.pseudoClassStateChanged(bottom,j==5);
	            if(j==8 && i > 5) cell.pseudoClassStateChanged(bottom,j==8);
	            if(j==11) cell.pseudoClassStateChanged(bottom,j==11);
	          //  if(i== 10) cell.pseudoClassStateChanged(bottom,i==10);
	            
	        	sudokuField.setDisable(true);
	            
				
				
			
				 cell.getChildren().add(sudokuField);
				 
				 playBoard.add(cell,i,j);
			}
			
			
			}
			
		}
//		textField[12][4].setText("3");
		playBoard.setAlignment(Pos.CENTER);
	//	playBoard.add(new Line(0,0,0,0),1,1);   
		
		
		return playBoard;
	}

	

	public void createNumbers() {
		controller.createGame(difficulty);
	}

	@Override
	public Scene getScene() {
		return samurai;
	}

	public SudokuField[][] getTextField() {
		return textField;
	}




	
	
	

}