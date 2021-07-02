package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Gamestate;
import logic.SudokuLogic;

class SudokuLogicTest {
	
	SudokuLogic model;
	
	/**
	 * before each test a new empty logic model is set up
	 */
	@BeforeEach
	public void setUp(){
		model = new SudokuLogic(Gamestate.OPEN, 0 , 0);
		model.setUpLogicArray();
	}
	
	/**
	 * tests if the set up array contains all 81 cells
	 */
	@Test
	void testSetUpLogicArray() {
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
			
				if (model.getCells()[i][j].getValue() == 0) {
					count++;
				}
			}
		}
		assertEquals(81,count);
	}
	
	
	
	/**
	 * tests if setting cells works
	 */
	@Test
	void testSetCells() {
		SudokuLogic model2 = new SudokuLogic(Gamestate.OPEN, 0 , 0);
		model2.setUpLogicArray();
		model2.getCells()[1][1].setValue(1);
		 model.setCells(model2.getCells());
		 assertEquals(model2.getCells()[1][1],model.getCells()[1][1]);
	}
	


//	@Test
//	void testSetCell() {
//		model.setCell(1, 1, 1);
//		assertEquals(model.getCells()[1][1].getValue(), 1);
//		assertTrue(model.getCells()[1][1].getIsReal());
//	}
	
	
	/**
	 * part of valid method
	 * tests if all rows are checked 
	 * without conflicts 
	 */
	@Test
	void testCheckRowTrue() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][5].setValue(3);
		assertTrue(model.checkRow(0, 1, 2));
	}
	
	/**
	 * part of valid method
	 * tests if all rows are checked 
	 * with conflict
	 */
	@Test
	void testCheckRowFalse() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][5].setValue(3);
		assertFalse(model.checkRow(0, 1, 3));
	}
	
	/**
	 * part of valid method
	 * tests if all columns are checked 
	 * without conflicts 
	 */
	@Test
	void testCheckColTrue() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[5][0].setValue(3);
		assertTrue(model.checkCol(1, 0, 2));
	}
	
	/**
	 * part of valid method
	 * tests if all columns are checked 
	 * with conflict
	 */
	@Test
	void testCheckColFalse() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[5][0].setValue(3);
		assertFalse(model.checkCol(1, 0, 3));
	}
	
	/**
	 * part of valid method
	 * tests if all boxes are checked 
	 * without conflicts 
	 */
	@Test
	void testCheckBoxTrue() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[2][2].setValue(3);
		assertTrue(model.checkBox(1, 0, 2));
	}
	
	/**
	 * part of valid method
	 * tests if all boxes are checked 
	 * with conflict
	 */
	@Test
	void testCheckBoxFalse() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[2][2].setValue(3);
		assertFalse(model.checkBox(1, 0, 3));
	}
	
	/**
	 * tests if a correct array is validated
	 */
	@Test
	void testValidTrue() {
		//ROW
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][7].setValue(2);
		//COL
		model.getCells()[0][1].setValue(2);
		model.getCells()[0][5].setValue(6);		
		//BOX
		model.getCells()[1][1].setValue(3);
		model.getCells()[2][2].setValue(4);			
		
		assertTrue(model.valid(0, 2, 5));
	}
	
	/**
	 * tests if a false array is not validated
	 */
	@Test
	void testValidFalseBox() {
		//ROW
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][7].setValue(2);
		//COL
		model.getCells()[0][1].setValue(2);
		model.getCells()[0][5].setValue(6);		
		//BOX
		model.getCells()[1][1].setValue(3);
		model.getCells()[2][2].setValue(4);			
		
		assertFalse(model.valid(2, 1, 4));
	}

	/**
	 * tests if a false array is not validated when a number conflict occurs in a row
	 */
	@Test
	void testValidFalseRow() {
		//ROW
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][7].setValue(2);
		//COL
		model.getCells()[0][1].setValue(2);
		model.getCells()[0][5].setValue(6);		
		//BOX
		model.getCells()[1][1].setValue(3);
		model.getCells()[2][2].setValue(4);			
		
		assertFalse(model.valid(0, 1, 1));
	}
	
	/**
	 * tests if a false array is not validated when a number conflict occurs in a column
	 */
	@Test
	void testValidFalseCol() {
		//ROW
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][7].setValue(2);
		//COL
		model.getCells()[0][1].setValue(2);
		model.getCells()[0][5].setValue(6);		
		//BOX
		model.getCells()[1][1].setValue(3);
		model.getCells()[2][2].setValue(4);			
		
		assertFalse(model.valid(1, 0, 2));
	}
	
	/**
	 * tests if a playable sudoku cell array is created
	 */
	@Test
	void testCreate() {
		model.createSudoku();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
			
				if (model.getCells()[i][j].getValue() != 0) {
					count++;
				}
			}
		}
		assertEquals(81,count);
	}
	
	/**
	 * tests if a playable sudoku cell array is created
	 */
	@Test
	void testCreate2() {
		assertTrue(model.createSudoku());
	}
	
	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty easy -> 56 displayed, 25 not
	 */
	@Test
	void testDifficulty3() {
		model.createSudoku();
		model.setDifficulty(3);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if (model.getCells()[i][j].getValue() != 0) {
					count++;
				}
			}
		}
		assertEquals(25,count);
	}
	
	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty medium -> 46 displayed, 35 not
	 */
	@Test
	void testDifficulty5() {
		model.createSudoku();
		model.setDifficulty(5);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if (model.getCells()[i][j].getValue() != 0) {
					count++;
				}
			}
		}
		assertEquals(35,count);
	}
	
	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty hard -> 36 displayed, 45 not
	 */
	@Test
	void testDifficulty7() {
		model.createSudoku();
		model.setDifficulty(7);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if (model.getCells()[i][j].getValue() != 0) {
					count++;
				}
			}
		}
		assertEquals(45,count);
	}
	
	/**
	 * tests if a sudoku with difficulty easy is solveable
	 */
	@Test
	void testSolve() {		
	model.createSudoku();
	model.setDifficulty(3);
	model.difficulty();
		assertTrue(model.solveSudoku());
	}
	
//	@Test
//	public void testPrintCells() {		
//	model.printCells();
//	}
//	
	/**
	 * tests setting Gamestate
	 */
	@Test
	void testSetGamestate() {		
		model.setGameState(Gamestate.DONE);
		assertEquals(Gamestate.DONE, model.getGamestate());
	}	
	
	
	/**
	 * tests getting Gamestate
	 */
	@Test
	void testGetGamestate() {		
		model.setGameState(Gamestate.DONE);
		model.getGamestate();
		assertEquals(Gamestate.DONE, model.getGamestate());
	}	
	
	
	/**
	 * tests if true->sudoku solved  is returned when the sudoku was solved by method
	 */
	@Test
	void testIfSolvedTrue() {		
		model.createSudoku();
		model.setDifficulty(3);
		model.difficulty();
		model.solveSudoku();
		assertTrue(model.testIfSolved());
	}	
	
	/**
	 * tests if false->sudoku not solved  is returned when the sudoku was not solved by method
	 */
	@Test
	void testIfSolvedFalse() {		
		model.createSudoku();
		model.setDifficulty(3);
		model.difficulty();
		assertFalse(model.testIfSolved());
	}	
	
	
	/**
	 * tests if the hint method displays a new number in the array
	 */
	@Test
    void testHint() {
        int [] sollutionArray = new int[] {0, 0};
        model.createSudoku();
        model.setDifficulty(3);
        model.difficulty();
//        model.solveSudoku();
//        model.setCell(0, 5, 0);
        model.getCells()[0][5].setValue(0);
        int [] returnArray = model.hint();
        System.out.println(returnArray[0] + " " + returnArray[1]);

        assertNotEquals(null,returnArray);
    }
	
//	/**
//	 * 
//	 */
//    @Test
//    void testHint2() {
//        int [] sollutionArray = new int[] {0, 0};
//        model.createSudoku();
//        model.setDifficulty(3);
//        model.difficulty();
//        model.solveSudoku();
//        int [] returnArray = model.hint();
//
//        assertEquals(null, returnArray);
//    }
}