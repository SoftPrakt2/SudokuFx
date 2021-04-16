package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class FreeFormGameBuilder extends BasicGameBuilder {
	
	 static BorderPane pane;
	 static Scene freeform;
	 static ToggleButton hint;
	
	
		public void initializeScene() {
		
		pane = new BorderPane();
		freeform = new Scene(pane,500,500);

	
		
		pane.setCenter(createBoard());
	
		
	
	    createPlayButtons(pane);
	    createMenuBar(pane);
	    createBackButton(pane);
	    
	    freeFormMode.setDisable(true);
	   
	    
	    freeform.getStylesheets().add("main/resources/CSS/sudoku.css");
	
	//  return freeform;
	}
	

	public  Scene getScene() {
		return freeform;
	}


	@Override
	public GridPane createBoard() {
		playBoard = new GridPane();
		
		playBoard.setPadding(new Insets(5, 5, 5, 5));
		
		playBoard.setVgap(1);
		playBoard.setHgap(1);
		
		textFields = new SudokuField[9][9];
		
		playBoard.setVgap(15);
		playBoard.setHgap(15);
		
		int countFields = 0;
		String colores = "000000";

		for (int blockC = 0; blockC < 9 ; blockC++) {
	        for (int blockRow = 0; blockRow < 9; blockRow++) {
	        	
	        	 countFields ++;
	        	 
	               if(countFields ==1) {colores = "268a53";} 
	               if(countFields ==2) {colores = "268a53";} 
	               if(countFields ==3) {colores = "268a53";} 
	               if(countFields ==4) {colores = "268a53";} 
	               if(countFields ==5) {colores = "147d9e";} 
	               if(countFields ==6) {colores = "147d9e";} 
	               if(countFields ==7) {colores = "d6a138";} 
	               if(countFields ==8) {colores = "d6a138";} 
	               if(countFields ==9) {colores = "d6a138";} 
	               if(countFields ==10) {colores = "268a53";} 
	               if(countFields ==11) {colores = "268a53";} 
	               if(countFields ==12) {colores = "268a53";} 
	               if(countFields ==13) {colores = "147d9e";} 
	               if(countFields ==14) {colores = "147d9e";} 
	               if(countFields ==15) {colores = "147d9e";} 
	               if(countFields ==16) {colores = "d6a138";} 
	               if(countFields ==17) {colores = "d6a138";} 
	               if(countFields ==18) {colores = "d6a138";} 
	               if(countFields ==19) {colores = "268a53";} 
	               if(countFields ==20) {colores = "268a53";} 
	               if(countFields ==21) {colores = "139c90";} 
	               if(countFields ==22) {colores = "139c90";} 
	               if(countFields ==23) {colores = "147d9e";} 
	               if(countFields ==24) {colores = "147d9e";} 
	               if(countFields ==25) {colores = "147d9e";} 
	               if(countFields ==26) {colores = "d6a138";} 
	               if(countFields ==27) {colores = "d6a138";} 
	               if(countFields ==28) {colores = "9c1372";} 
	               if(countFields ==29) {colores = "9c1372";} 
	               if(countFields ==30) {colores = "139c90";} 
	               if(countFields ==31) {colores = "139c90";} 
	               if(countFields ==32) {colores = "139c90";} 
	               if(countFields ==33) {colores = "139c90";} 
	               if(countFields ==34) {colores = "147d9e";} 
	               if(countFields ==35) {colores = "d6a138";} 
	               if(countFields ==36) {colores = "9c1314";} 
	               if(countFields ==37) {colores = "9c1372";} 
	               if(countFields ==38) {colores = "9c1372";} 
	               if(countFields ==39) {colores = "9c1372";} 
	               if(countFields ==40) {colores = "139c90";} 
	               if(countFields ==41) {colores = "139c90";} 
	               if(countFields ==42) {colores = "139c90";} 
	               if(countFields ==43) {colores = "9c1314";} 
	               if(countFields ==44) {colores = "9c1314";} 
	               if(countFields ==45) {colores = "9c1314";} 
	               if(countFields ==46) {colores = "9c1372";} 
	               if(countFields ==47) {colores = "9c1372";} 
	               if(countFields ==48) {colores = "525252";} 
	               if(countFields ==49) {colores = "525252";} 
	               if(countFields ==50) {colores = "525252";} 
	               if(countFields ==51) {colores = "525252";} 
	               if(countFields ==52) {colores = "9c1314";} 
	               if(countFields ==53) {colores = "9c1314";} 
	               if(countFields ==54) {colores = "9c1314";} 
	               if(countFields ==55) {colores = "9c1372";} 
	               if(countFields ==56) {colores = "7f9c13";} 
	               if(countFields ==57) {colores = "525252";} 
	               if(countFields ==58) {colores = "7f9c13";} 
	               if(countFields ==59) {colores = "525252";} 
	               if(countFields ==60) {colores = "525252";} 
	               if(countFields ==61) {colores = "b1df71";} 
	               if(countFields ==62) {colores = "9c1314";} 
	               if(countFields ==63) {colores = "9c1314";} 
	               if(countFields ==64) {colores = "9c1372";} 
	               if(countFields ==65) {colores = "7f9c13";} 
	               if(countFields ==66) {colores = "7f9c13";} 
	               if(countFields ==67) {colores = "7f9c13";} 
	               if(countFields ==68) {colores = "525252";} 
	               if(countFields ==69) {colores = "b1df71";} 
	               if(countFields ==70) {colores = "b1df71";} 
	               if(countFields ==71) {colores = "b1df71";} 
	               if(countFields ==72) {colores = "b1df71";} 
	               if(countFields ==73) {colores = "7f9c13";} 
	               if(countFields ==74) {colores = "7f9c13";} 
	               if(countFields ==75) {colores = "7f9c13";} 
	               if(countFields ==76) {colores = "7f9c13";} 
	               if(countFields ==77) {colores = "525252";} 
	               if(countFields ==78) {colores = "b1df71";} 
	               if(countFields ==79) {colores = "b1df71";} 
	               if(countFields ==80) {colores = "b1df71";} 
	               if(countFields ==81) {colores = "b1df71";} 
	               
	            	StackPane cell = new StackPane();
		            SudokuField sudokuField = new SudokuField("");
		            textFields[blockC][blockRow] = sudokuField;

	                cell.setStyle("-fx-pref-width: 2em;");
	                sudokuField.setStyle("-fx-pref-margin: 0em;");
	                sudokuField.setAlignment(Pos.CENTER);
	                sudokuField.setStyle("-fx-background-color: #"+colores+";");
	                
	                cell.getChildren().add(sudokuField);
	                 
	                   playBoard.add(cell, blockC, blockRow);
	                    
	                }
	            }


	        
		playBoard.setAlignment(Pos.CENTER);
		playBoard.add(new Line(0,0,0,0),1,1);     
		
		
		return playBoard;
		
	}
		
		
		
		
		
		

	


	@Override
	public SudokuField[][] getTextField() {
		// TODO Auto-generated method stub
		return null;
	}
	

}