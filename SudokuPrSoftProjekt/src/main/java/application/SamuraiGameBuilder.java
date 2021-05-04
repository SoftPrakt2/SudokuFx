package application;

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
import logic.SudokuLogic;

public class SamuraiGameBuilder extends BasicGameBuilder {

	 public SamuraiGameBuilder () {
		  	super();
		  	scene = new Scene(pane,1000,1000);
		  	textField = new SudokuField[21][21];
		  	gameType = "Sudoku";
		   
	  }
	
	public Scene initializeScene() {
	 		 controller =  new GameController(this, new SamuraiLogic(Gamestate.OPEN, 0.0, false));
	 		   
		        pane.setCenter(createBoard());
		        pane.setPadding(new Insets(50,50,50,50));

		       createMenuBar(pane);
		       createPlayButtons(pane);

		      scene.getStylesheets().add("/CSS/sudoku.css");


		    return scene;
	}
	

	@Override
	public GridPane createBoard() {
		playBoard = new GridPane();
		
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		playBoard.setVgap(1);
		playBoard.setHgap(1);
		
		//textField = new SudokuField[21][21];
		
		
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
				textField[i][j].setPlayable(false); 
				
			} else if((i <6 || i > 14) && (j ==9 || j == 10 || j == 11)) {
				textField[i][j].setPlayable(false); 
			//	playBoard.add(cellEmpty,spalte,zeile);

			} else {
				
			
				textField[i][j] = new SudokuField("");	
				textField[i][j].setMaxSize(50, 50);
				textField[i][j].setAlignment(Pos.CENTER);
				textField[i][j].setPlayable(true);
			
				
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
	            
	            textField[i][j].setDisable(true);
	            
				
				
			
				 cell.getChildren().add(textField[i][j]);
				 
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
		return scene;
	}



	



	@Override
	public void createNumbers() {
		// TODO Auto-generated method stub
		controller.createGame(difficulty);
	}




	
	
	

}