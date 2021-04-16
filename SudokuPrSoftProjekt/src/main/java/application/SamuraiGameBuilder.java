package application;

import controller.BasicController;
import controller.SamuraiController;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class SamuraiGameBuilder extends BasicGameBuilder {
	
	
	
	 BorderPane pane;
	 Scene samurai;
	 BasicController controller;
	
	public void initializeScene() {
		
		pane = new BorderPane();
		samurai = new Scene(pane,800,800);
	
		pane.setCenter(createBoard());
	
	    createPlayButtons(pane);
	    createMenuBar(pane);
	    createBackButton(pane);
	    
	    controller = new SamuraiController(this);
	    
	    play.setOnAction(controller::createGameHandler);
	    
	    sudokuMode.setOnAction(controller::switchToSudoku);
	    
	    
	    samuraiMode.setDisable(true);
	    samurai.getStylesheets().add("main/resources/CSS/sudoku.css");
	    
	   // return samurai;
	}
	


	@Override
	public GridPane createBoard() {
		playBoard = new GridPane();
		
		playBoard.setPadding(new Insets(5, 5, 5, 5));
		
		playBoard.setVgap(1);
		playBoard.setHgap(1);
		
		textFields = new SudokuField[21][21];
		
		
		PseudoClass right = PseudoClass.getPseudoClass("right");
	    PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
	   // PseudoClass left = PseudoClass.getPseudoClass("left");
		
		for(int spalte = 0; spalte <21; spalte++) {
			for(int zeile = 0; zeile < 21; zeile++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");
				
				StackPane cellEmpty = new StackPane();
				cellEmpty.getStyleClass().add("hidden");
				SudokuField empty = new SudokuField("-");
				empty.setStyle("-fx-pref-width: 2em;");
				cellEmpty.getChildren().add(empty);
				cellEmpty.setDisable(true);
				textFields[spalte][zeile] = empty;
				
			if((spalte == 9 || spalte == 10 || spalte ==11) && (zeile < 6 || zeile > 14)) {
				
				playBoard.add(cellEmpty,spalte,zeile);
				 
			} else if((spalte <6 || spalte > 14) && (zeile ==9 || zeile == 10 || zeile == 11)) {

				playBoard.add(cellEmpty,spalte,zeile);

			} else {
				
				SudokuField sudokuField = new SudokuField("0");
				textFields[spalte][zeile] = sudokuField;
				
				sudokuField.setDisable(true);
				cell.pseudoClassStateChanged(right, spalte == 2 || spalte == 5 || spalte == 8 || spalte == 11 || spalte ==14 || spalte == 17);
	            cell.pseudoClassStateChanged(bottom, zeile == 2 || zeile == 5 ||zeile ==8 ||zeile == 11|| zeile == 14 || zeile == 17);
	            cellEmpty.pseudoClassStateChanged(right, spalte == 5);
	           
				
				 sudokuField.setStyle("-fx-pref-width: 2em;");
				 cell.getChildren().add(sudokuField);
				 playBoard.add(cell,spalte,zeile);
			}
			}
			
		}
		
		playBoard.setAlignment(Pos.CENTER);
		playBoard.add(new Line(0,0,0,0),1,1);   
		
		
		return playBoard;
	}

	@Override
	public SudokuField[][] getTextField() {
		// TODO Auto-generated method stub
		return textFields;
	}



	@Override
	public Scene getScene() {
		// TODO Auto-generated method stub
		return samurai;
	}



	
	
	

}