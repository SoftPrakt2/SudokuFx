package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Cell;
import logic.Gamestate;
import logic.SudokuLogic;


public class SudokuLogicTest {

	
	SudokuLogic model;
	
	@BeforeEach
	public void setUp(){
		model = new SudokuLogic(Gamestate.OPEN, 0 , 0 , false);
		model.setUpLogicArray();
	}
	
	@Test
	public void testSetUpLogicArray() {
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
			
				if (model.getCells()[i][j].getValue() == 0) {
					count++;
				}
			}
		}
		assertEquals(count, 81);
	}
	
	
	
	@Test
	public void testSetCells() {
		SudokuLogic model2 = new SudokuLogic(Gamestate.OPEN, 0 , 0 , false);
		model2.setUpLogicArray();
		model2.getCells()[1][1].setValue(1);
		 model.setCells(model2.getCells());
		 assertEquals(model.getCells()[1][1], model2.getCells()[1][1]);
	}
	
	
	@Test
	public void testSetCell() {
		model.setCell(1, 1, 1);
		assertEquals(model.getCells()[1][1].getValue(), 1);
		assertTrue(model.getCells()[1][1].getIsReal());
	}
	
	
	@Test
	public void testCheckRowTrue() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][5].setValue(3);
		assertTrue(model.checkRow(0, 1, 2));
	}
	
	@Test
	public void testCheckRowFalse() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[0][5].setValue(3);
		assertFalse(model.checkRow(0, 1, 3));
	}
	
	@Test
	public void testCheckColTrue() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[5][0].setValue(3);
		assertTrue(model.checkCol(1, 0, 2));
	}
	
	@Test
	public void testCheckColFalse() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[5][0].setValue(3);
		assertFalse(model.checkCol(1, 0, 3));
	}
	
	@Test
	public void testCheckBoxTrue() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[2][2].setValue(3);
		assertTrue(model.checkBox(1, 0, 2));
	}
	
	@Test
	public void testCheckBoxFalse() {
		model.getCells()[0][0].setValue(1);
		model.getCells()[2][2].setValue(3);
		assertFalse(model.checkBox(1, 0, 3));
	}
	
	@Test
	public void testValidTrue() {
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
	
	@Test
	public void testValidFalseBox() {
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

	@Test
	public void testValidFalseRow() {
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
	
	@Test
	public void testValidFalseCol() {
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
	
	@Test
	public void testCreate() {
		model.createSudoku();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
			
				if (model.getCells()[i][j].getValue() != 0) {
					count++;
				}
			}
		}
		assertEquals(count, 81);
	}
	
	@Test
	public void testCreate2() {
		assertTrue(model.createSudoku());
	}
	
	@Test
	public void testDifficulty3() {
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
		assertEquals(count, 25);
	}
	
	@Test
	public void testDifficulty5() {
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
		assertEquals(count, 35);
	}
	
	@Test
	public void testDifficulty7() {
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
		assertEquals(count, 45);
	}
	
	@Test
	public void testSolve() {		
	model.createSudoku();
	model.setDifficulty(3);
	model.difficulty();
		assertTrue(model.solveSudoku());
	}
	
	@Test
	public void testPrintCells() {		
	model.printCells();
	}
	
	@Test
	public void testSetGamestate() {		
		model.setGameState(Gamestate.DONE);
		assertEquals(Gamestate.DONE, model.getGamestate());
	}	
	
	
	@Test
	public void testGetGamestate() {		
		model.setGameState(Gamestate.DONE);
		model.getGamestate();
		assertEquals(Gamestate.DONE, model.getGamestate());
	}	
	
	
	@Test
	public void testIfSolvedTrue() {		
		model.createSudoku();
		model.setDifficulty(3);
		model.difficulty();
		model.solveSudoku();
		assertTrue(model.testIfSolved());
	}	
	
	@Test
	public void testIfSolvedFalse() {		
		model.createSudoku();
		model.setDifficulty(3);
		model.difficulty();
		assertFalse(model.testIfSolved());
	}	
	
	
	@Test
    public void testHint() {
        int [] sollutionArray = new int[] {0, 0};
        model.createSudoku();
        model.setDifficulty(3);
        model.difficulty();
//        model.solveSudoku();
        model.setCell(0, 5, 0);
        int [] returnArray = model.hint();
        System.out.println(returnArray[0] + " " + returnArray[1]);

        assertNotEquals(returnArray, null);
    }
//    @Test
//    public void testHint2() {
//        int [] sollutionArray = new int[] {0, 0};
//        model.createSudoku();
//        model.setDifficulty(3);
//        model.difficulty();
//        model.solveSudoku();
//        int [] returnArray = model.hint();
//
//        assertEquals(returnArray, null);
//    }
}


