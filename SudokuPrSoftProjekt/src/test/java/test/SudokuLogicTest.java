package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import logic.Cell;
import logic.Gamestate;
import logic.SudokuLogic;

public class SudokuLogicTest {

	
	//testCases
	@Test
	public void test() {
		//test4
		SudokuLogic model = new SudokuLogic(Gamestate.OPEN, 0,0 , false);

		// *setUpLogicArray() *			
		model.setUpLogicArray();
		assertTrue(model.getCells()[0][0].getValue() == 0);
		assertTrue(model.getCells()[1][7].getValue() == 0);
		assertTrue(model.getCells()[8][8].getValue() == 0);
//
//	// *createSudoku() *		
//	model.createSudoku();
//	assertFalse(model.getCells()[0][0].getValue() == 0);
//	assertFalse(model.getCells()[1][7].getValue() == 0);
//	assertFalse(model.getCells()[8][8].getValue() == 0);
//	
//	// *difficulty() *		
//model.difficulty();
//	assertTrue(model.getCells()[4][4].getValue() != 0);
//	assertTrue(model.getCells()[4][4].getIsReal());
//	
//
//	// *setCell() *		 
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(0);
//		assertTrue(model.getCells()[4][4].getValue() == 0);
//		assertFalse(model.getCells()[4][4].getIsReal());
//
//	model.setCell(1, 1, 2);
//		assertTrue(model.getCells()[1][1].getValue() == 2);
//		assertFalse(model.getCells()[1][1].getGuess() == 2);
//		assertTrue(model.getCells()[1][1].getIsReal() == true);
//		
//		
//	// *hint() *
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(100);	
//	model.hint();
//		assertTrue(model.solveSudoku());
//	
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(10);	
//	model.hint();
//		assertTrue(model.solveSudoku());
//	
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(0);	
//	model.hint();
//		assertTrue(model.solveSudoku());
//		
//	
//	// *solve() *	-> FALSE
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(100);	
//		assertTrue(model.solveSudoku());
//	
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(10);	
//		assertTrue(model.solveSudoku());
//	
//	model.setUpLogicArray();
//	model.createSudoku();
//	model.difficulty(0);	
//		assertTrue(model.solveSudoku());
//		
//		model.setUpLogicArray();
//		model.createSudoku();
//		model.difficulty(5);	
//		model.setGameState(Gamestate.DONE);
//			assertTrue(model.getGameState() == Gamestate.DONE);		
//			
//
//			// *print() *	
//			model.setUpLogicArray();
//			model.createSudoku();
//			model.difficulty(10);	
//			model.printCells();
//			
//			
//			// *setCells() *	
//			SudokuLogic model2 = new SudokuLogic(Gamestate.OPEN, 0, 0, false);
//			Cell[][] cellArray = new Cell [9][9];
//			for (int row = 0; row < 9; row++) {
//				for (int col = 0; col < 9; col++) {
//					cellArray[row][col]=new Cell (0,0,0,0,1,9);
//					
//				}
//			}
	}
}