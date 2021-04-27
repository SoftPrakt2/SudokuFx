package application;



import controller.BasicController;
import controller.SamuraiController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

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
	   
	    
	    controller = new SamuraiController(this);
	    
	    hintButton.setOnAction(controller::createGameHandler);
	    
	    mainMenuItem.setOnAction(controller::switchToMainMenu);
	    autosolve.setOnAction(controller::newGameHandler);
	
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
				SudokuField empty = new SudokuField("-");
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
	            cell.pseudoClassStateChanged(bottom, j == 2 || j == 5 ||j ==8 ||j == 11|| j == 14 || j == 17);
	         
	        	sudokuField.setDisable(true);
	            
				
				
			
				 cell.getChildren().add(sudokuField);
				 
				 playBoard.add(cell,i,j);
			}
			}
			
		}
		textField[12][4].setText("3");
		playBoard.setAlignment(Pos.CENTER);
	//	playBoard.add(new Line(0,0,0,0),1,1);   
		
		
		return playBoard;
	}

	



	@Override
	public Scene getScene() {
		return samurai;
	}



	



	@Override
	public void createNumbers() {
		// TODO Auto-generated method stub
		System.out.println("just test");
	}




	
	
	

}