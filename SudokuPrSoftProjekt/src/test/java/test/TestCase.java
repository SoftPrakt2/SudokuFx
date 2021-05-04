package test;

import static org.junit.Assert.*;
import  org.junit.*;

import logic.BasicGameLogic;
import logic.Cell;
import logic.Gamestate;
import logic.SamuraiLogic;
import logic.SudokuLogic;


public class TestCase {

	@Test
	public void test() {
		
		SudokuLogic model = new SudokuLogic(Gamestate.OPEN, 0,false);
		model.setUpLogicArray();
			 assertTrue(model.getCells()[0][0].getValue()  == 0);    
			 assertTrue(model.getCells()[1][7].getValue()  == 0);   
			 assertTrue(model.getCells()[8][8].getValue()  == 0);   
		 
		model.createSudoku();
			 assertTrue(model.getCells()[0][0].getValue()  != 0);    
			 assertTrue(model.getCells()[1][7].getValue()  != 0);   
			 assertTrue(model.getCells()[8][8].getValue()  != 0);   
		
		  model.difficulty(10);
		  assertTrue(model.getCells()[4][4].getValue()  != 0);   
	  
	}
	

	
}
