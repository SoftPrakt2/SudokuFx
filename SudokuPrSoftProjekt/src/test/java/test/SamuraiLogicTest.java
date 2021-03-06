package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.BasicGameLogic;
import logic.Gamestate;
import logic.SamuraiLogic;


class SamuraiLogicTest {


	BasicGameLogic model;
	
	/**
	 * before each test a new empty samurai logic model is set up
	 */
	@BeforeEach
	public void setUp(){
		model = new SamuraiLogic(Gamestate.OPEN, 0 , 0);
		model.setUpLogicArray();
	}
	
	/**
	 * tests if the set up array contains all 369 cells
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
		assertEquals(369, count);
	}
	
	/**
	 * tests setting cells
	 */
	@Test
	void testSetCells() {
		SamuraiLogic model2 = new SamuraiLogic(Gamestate.OPEN, 0 , 0);
		model2.setUpLogicArray();
		model2.getCells()[1][1].setValue(1);
		 model.setCells(model2.getCells());
		 assertEquals(model2.getCells()[1][1], model.getCells()[1][1]);
	}

	/**
	 * part of valid method
	 * tests if all rows are checked in  9 different cases covering all of the 5 fields and the 4 overlapping boxes
	 * tests each constellation without conflicts and with conflicts 
	 */
	@Test
	void TestRowTopLeft() {
		model.getCells()[4][4].setValue(5);
		assertTrue(model.checkRow(4, 3, 3));
		assertFalse(model.checkRow(4, 6, 5));
	}
	
	@Test
	void TestRowTopOverlappingLeft() {
		model.getCells()[7][7].setValue(5);
		assertTrue(model.checkRow(7, 3, 3));
		assertTrue(model.checkRow(7, 12, 4));
		assertFalse(model.checkRow(7, 8, 5));
		assertFalse(model.checkRow(7, 12, 5));
	}
	
	@Test
	void TestRowTopRight() {
		model.getCells()[4][12].setValue(5);
		assertTrue(model.checkRow(4, 15, 3));
		assertFalse(model.checkRow(4, 16, 5));
	}
	
	@Test
	void TestRowTopOverlappingRight() {
		model.getCells()[7][12].setValue(5);
		assertTrue(model.checkRow(6, 12, 3));
		assertFalse(model.checkRow(7, 15, 5));
		assertTrue(model.checkRow(12, 9, 3));
		assertFalse(model.checkRow(7, 9, 5));
	}
	
	@Test
	void TestRowBottomLeft() {
		model.getCells()[20][2].setValue(5);
		assertTrue(model.checkRow(20, 6, 3));
		assertFalse(model.checkRow(20, 6, 5));
	}
	
	@Test
	void TestRowBottomOverlappingLeft() {
		model.getCells()[13][7].setValue(5);
		assertTrue(model.checkRow(13, 3, 3));
		assertTrue(model.checkRow(13, 13, 3));
		assertFalse(model.checkRow(13, 3, 5));
		assertFalse(model.checkRow(13, 13, 5));
	}
	
	@Test
	void TestRowBottomRight() {
		model.getCells()[15][15].setValue(5);
		assertTrue(model.checkRow(15, 17, 3));
		assertFalse(model.checkRow(15, 20, 5));
	}
	
	@Test
	void TestRowBottomOverlappingRight() {
		model.getCells()[14][14].setValue(5);
		assertTrue(model.checkRow(14, 9, 3));
		assertTrue(model.checkRow(14, 20, 4));
		assertFalse(model.checkRow(14, 9, 5));
		assertFalse(model.checkRow(14, 20, 5));
	}
	
	@Test
	void TestRowCenter() {
		model.getCells()[9][9].setValue(5);
		assertTrue(model.checkRow(9, 10, 3));
		assertFalse(model.checkRow(9, 10, 5));
	}

	
	
	/**
	 * part of valid method
	 * tests if all columns are checked in  9 different cases covering all of the 5 fields and the 4 overlapping boxes
	 * tests each constellation without conflicts and with conflicts 
	 */
	@Test
	void TestColopLeft() {
		model.getCells()[4][4].setValue(5);
		assertTrue(model.checkCol(3, 4, 3));
		assertFalse(model.checkCol(6, 4, 5));
	}
	
	@Test
	void TestColTopOverlappingLeft() {
		model.getCells()[7][7].setValue(5);
		assertTrue(model.checkCol(3, 7, 3));
		assertTrue(model.checkCol(12, 7, 4));
		assertFalse(model.checkCol(8, 7, 5));
		assertFalse(model.checkCol(12, 7, 5));
	}
	
	@Test
	void TestColTopRight() {
		model.getCells()[4][12].setValue(5);
		assertTrue(model.checkCol(1, 12, 3));
		assertFalse(model.checkCol(1, 12, 5));
	}
	
	@Test
	void TestColTopOverlappingRight() {
		model.getCells()[7][12].setValue(5);
		assertTrue(model.checkCol(6, 12, 3));
		assertFalse(model.checkCol(6, 12, 5));
		assertTrue(model.checkCol(12, 12, 3));
		assertFalse(model.checkCol(12, 12, 5));
	}
	
	@Test
	void TestColBottomLeft() {
		model.getCells()[20][2].setValue(5);
		assertTrue(model.checkCol(18, 2, 3));
		assertFalse(model.checkCol(18, 2, 5));
	}
	
	@Test
	void TestColBottomOverlappingLeft() {
		model.getCells()[13][7].setValue(5);
		assertTrue(model.checkCol(19, 7, 3));
		assertTrue(model.checkCol(14, 7, 3));
		assertFalse(model.checkCol(19, 7, 5));
		assertFalse(model.checkCol(14, 7, 5));
	}
	
	@Test
	void TestColBottomRight() {
		model.getCells()[15][15].setValue(5);
		assertTrue(model.checkCol(17, 15, 3));
		assertFalse(model.checkCol(20, 15, 5));
	}
	
	@Test
	void TestColBottomOverlappingRight() {
		model.getCells()[14][14].setValue(5);
		assertTrue(model.checkCol(9, 14, 3));
		assertTrue(model.checkCol(20, 14, 4));
		assertFalse(model.checkCol(9, 14, 5));
		assertFalse(model.checkCol(20, 14, 5));
	}
	
	@Test
	void TestColCenter() {
		model.getCells()[9][9].setValue(5);
		assertTrue(model.checkCol(10, 9, 3));
		assertFalse(model.checkCol(10, 9, 5));
	}
	
	/**
	 * part of valid method
	 * tests if all boxes are checked in 5 different cases 
	 * tests each constellation without conflicts and with conflicts 
	 */
	@Test
	void TestBoxTopLeft() {
		model.getCells()[4][4].setValue(5);
		assertTrue(model.checkBox(3, 4, 3));
		assertFalse(model.checkBox(5, 4, 5));
	}
	
	@Test
	void TestBoxTopRight() {
		model.getCells()[4][15].setValue(5);
		assertTrue(model.checkBox(5, 15, 3));
		assertFalse(model.checkBox(5, 15, 5));
	}
	
	@Test
	void TestBoxBottomLeft() {
		model.getCells()[15][6].setValue(5);
		assertTrue(model.checkBox(15, 5, 3));
		assertFalse(model.checkBox(15, 6, 5));
	}
	
	@Test
	void TestBoxBottomRight() {
		model.getCells()[15][15].setValue(5);
		assertTrue(model.checkBox(15, 16, 3));
		assertFalse(model.checkBox(15, 16, 5));
	}
	
	@Test
	void TestBoxCenter() {
		model.getCells()[9][9].setValue(5);
		assertTrue(model.checkBox(10, 9, 3));
		assertFalse(model.checkBox(10, 9, 5));
	}

	
	/**
	 * tests if samurai sudoku is validated when user inserts a correct number 
	 * and if validation is false when a false number is inserted  by user
	 */
    @Test
    void testValid() {
        assertTrue(model.valid(0, 0, 5));

        model.getCells()[0][0].setValue(5);
        assertFalse(model.valid(1, 1, 5));
        assertFalse(model.valid(1, 0, 5));
        assertFalse(model.valid(0, 1, 5));
    }
    
	/**
	 * tests if samurai sudoku is validated without conflicts
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
	 * tests if samurai sudoku is not validated when a conflict occurs in a box
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
	 * tests if samurai sudoku is not validated when a conflict occurs in a row
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
	 * tests if samurai sudoku is not validated when a conflict occurs in a column
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
	 * tests if a playable samurai sudoku cell array is created
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
		assertEquals(441, count);
	}
	
	/**
	 * tests if a playable samurai sudoku cell array is created
	 */
	@Test
	void testCreate2() {
		assertTrue(model.createSudoku());
	}
	
	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty manual -> 0 displayed
	 */
	@Test
	void testDifficulty0() {
		model.createSudoku();
		model.setDifficulty(0);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if(model.getCells()[i][j].getValue() == 0) {
					count++;
				}
			}
		}
		assertEquals(369, count);
	}
	
	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty easy -> 210 displayed, 159 not
	 */
	@Test
	void testDifficulty3() {
		model.createSudoku();
		model.setDifficulty(3);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if(model.getCells()[i][j].getValue() != 0
						&& model.getCells()[i][j].getValue() != -1) {
					count++;
				}
			}
		}
		assertEquals(130, count);
	}


	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty medium -> 190 displayed, 179 not
	 */
	@Test
	void testDifficulty5() {
		model.createSudoku();
		model.setDifficulty(5);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if(model.getCells()[i][j].getValue() != 0
						&& model.getCells()[i][j].getValue() != -1) {
					count++;
				}
			}
		}
		assertEquals(169,count);
	}
	
	/**
	 * tests if the the correct number of cell values are displayed 
	 * difficulty hard -> 170 displayed, 199 not
	 */
	@Test
	void testDifficulty7() {
		model.createSudoku();
		model.setDifficulty(7);
		model.difficulty();
		int count = 0;
		for (int i = 0; i < model.getCells().length; i++) {
			for (int j = 0; j < model.getCells()[i].length; j++) {
				if(model.getCells()[i][j].getValue() != 0
						&& model.getCells()[i][j].getValue() != -1) {
					count++;
				}
			}
		}
		assertEquals(199, count);
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
	 * tests if true->'samurai sudoku solved'  is returned when the sudoku was solved by method
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
	 * tests if false->'samurai sudoku not solved'  is returned when the sudoku was not solved by method
	 */
	@Test
	void testIfSolvedFalse() {
		model.setDifficulty(3);
		model.createSudoku();
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
        model.getCells()[0][5].setValue(0);
        int [] returnArray = model.hint();

        assertNotEquals(null, returnArray);
    }
}


