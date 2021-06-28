//package test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import logic.BasicGameLogic;
//import logic.FreeFormLogic;
//import logic.Gamestate;
//import logic.SudokuLogic;
//
//
//class FreeFormLogicTest {
//
//
//	BasicGameLogic model;
//	
//	@BeforeEach
//	public void setUp(){
//		model = new FreeFormLogic(Gamestate.OPEN, 0 , 0);
//		model.setUpLogicArray();
//	}
//	
//	/**
//	 * tests if the set up array contains all 81 cells
//	 */
//	@Test
//	void testSetUpLogicArray() {
//		int count = 0;
//		for (int i = 0; i < model.getCells().length; i++) {
//			for (int j = 0; j < model.getCells()[i].length; j++) {
//			
//				if (model.getCells()[i][j].getValue() == 0) {
//					count++;
//				}
//			}
//		}
//		assertEquals(81,count);
//	}
//	
//	
//	
//	/**
//	 * tests if setting cells works
//	 */
//	@Test
//	void testSetCells() {
//		SudokuLogic model2 = new SudokuLogic(Gamestate.OPEN, 0 , 0);
//		model2.setUpLogicArray();
//		model2.getCells()[1][1].setValue(1);
//		 model.setCells(model2.getCells());
//		 assertEquals(model2.getCells()[1][1],model.getCells()[1][1]);
//	}
//	
//	
//	/**
//	 * part of valid method
//	 * tests if all rows are checked 
//	 * without conflicts 
//	 */
//	@Test
//	void testCheckRowTrue() {
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[0][5].setValue(3);
//		assertTrue(model.checkRow(0, 1, 2));
//	}
//	
//	/**
//	 * part of valid method
//	 * tests if all rows are checked 
//	 * with conflict
//	 */
//	@Test
//	void testCheckRowFalse() {
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[0][5].setValue(3);
//		assertFalse(model.checkRow(0, 1, 3));
//	}
//	
//	/**
//	 * part of valid method
//	 * tests if all columns are checked 
//	 * without conflicts 
//	 */
//	@Test
//	void testCheckColTrue() {
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[5][0].setValue(3);
//		assertTrue(model.checkCol(1, 0, 2));
//	}
//	
//	/**
//	 * part of valid method
//	 * tests if all columns are checked 
//	 * with conflict
//	 */
//	@Test
//	void testCheckColFalse() {
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[5][0].setValue(3);
//		assertFalse(model.checkCol(1, 0, 3));
//	}
//	
//	/**
//	 * part of valid method
//	 * tests if all boxes are checked 
//	 * without conflicts 
//	 */
//	@Test
//	void testCheckBoxTrue() {
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[2][2].setValue(3);
//		assertTrue(model.checkBox(1, 0, 2));
//	}
//	
//	/**
//	 * part of valid method
//	 * tests if all boxes are checked 
//	 * with conflict
//	 */
//	@Test
//	void testCheckBoxFalse() {
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[2][2].setValue(3);
//		assertFalse(model.checkBox(1, 0, 3));
//	}
//	
//	/**
//	 * tests if a correct array is validated
//	 */
//	@Test
//	void testValidTrue() {
//		//ROW
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[0][7].setValue(2);
//		//COL
//		model.getCells()[0][1].setValue(2);
//		model.getCells()[0][5].setValue(6);		
//		//BOX
//		model.getCells()[1][1].setValue(3);
//		model.getCells()[2][2].setValue(4);			
//		
//		assertTrue(model.valid(0, 2, 5));
//	}
//	
//	/**
//	 * tests if a false array is not validated
//	 */
//	@Test
//	void testValidFalseBox() {
//		//ROW
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[0][7].setValue(2);
//		//COL
//		model.getCells()[0][1].setValue(2);
//		model.getCells()[0][5].setValue(6);		
//		//BOX
//		model.getCells()[1][1].setValue(3);
//		model.getCells()[2][2].setValue(4);			
//		
//		assertFalse(model.valid(2, 1, 4));
//	}
//
//	/**
//	 * tests if a false array is not validated when a number conflict occurs in a row
//	 */
//	@Test
//	void testValidFalseRow() {
//		//ROW
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[0][7].setValue(2);
//		//COL
//		model.getCells()[0][1].setValue(2);
//		model.getCells()[0][5].setValue(6);		
//		//BOX
//		model.getCells()[1][1].setValue(3);
//		model.getCells()[2][2].setValue(4);			
//		
//		assertFalse(model.valid(0, 1, 1));
//	}
//	
//	/**
//	 * tests if a false array is not validated when a number conflict occurs in a column
//	 */
//	@Test
//	void testValidFalseCol() {
//		//ROW
//		model.getCells()[0][0].setValue(1);
//		model.getCells()[0][7].setValue(2);
//		//COL
//		model.getCells()[0][1].setValue(2);
//		model.getCells()[0][5].setValue(6);		
//		//BOX
//		model.getCells()[1][1].setValue(3);
//		model.getCells()[2][2].setValue(4);			
//		
//		assertFalse(model.valid(1, 0, 2));
//	}
//	
//	/**
//	 * tests if a playable sudoku cell array is created
//	 */
//	@Test
//	void testCreate() {
//		model.createSudoku();
//		int count = 0;
//		for (int i = 0; i < model.getCells().length; i++) {
//			for (int j = 0; j < model.getCells()[i].length; j++) {
//			
//				if (model.getCells()[i][j].getValue() != 0) {
//					count++;
//				}
//			}
//		}
//		assertEquals(81,count);
//	}
//	
//	/**
//	 * tests if a playable sudoku cell array is created
//	 */
//	@Test
//	void testCreate2() {
//		assertTrue(model.createSudoku());
//	}
//	
//	
//	/**
//	 * tests if the the correct number of cell values are displayed 
//	 * difficulty easy -> 56 displayed, 25 not
//	 */
//	@Test
//	void testDifficulty3() {
//		model.createSudoku();
//		model.setDifficulty(3);
//		model.difficulty();
//		int count = 0;
//		for (int i = 0; i < model.getCells().length; i++) {
//			for (int j = 0; j < model.getCells()[i].length; j++) {
//				if(model.getCells()[i][j].getValue() != 0 && model.getCells()[i][j].getFixedNumber() && model.getCells()[i][j].getValue() != -1) {
//					count++;
//				}
//			}
//		}
//		assertEquals(25,count );
//	}
//
//	/**
//	 * tests if the the correct number of cell values are displayed 
//	 * difficulty medium -> 46 displayed, 35 not
//	 */
//	@Test
//	void testDifficulty5() {
//		model.createSudoku();
//		model.setDifficulty(5);
//		model.difficulty();
//		int count = 0;
//		for (int i = 0; i < model.getCells().length; i++) {
//			for (int j = 0; j < model.getCells()[i].length; j++) {
//				if(model.getCells()[i][j].getValue() != 0 && model.getCells()[i][j].getFixedNumber() && model.getCells()[i][j].getValue() != -1) {
//					count++;
//				}
//			}
//		}
//		assertEquals(35,count);
//	}
//	
//	/**
//	 * tests if the the correct number of cell values are displayed 
//	 * difficulty hard -> 36 displayed, 45 not
//	 */
//	@Test
//	void testDifficulty7() {
//		model.createSudoku();
//		model.setDifficulty(7);
//		model.difficulty();
//		int count = 0;
//		for (int i = 0; i < model.getCells().length; i++) {
//			for (int j = 0; j < model.getCells()[i].length; j++) {
//				if(model.getCells()[i][j].getValue() != 0 && model.getCells()[i][j].getFixedNumber() && model.getCells()[i][j].getValue() != -1) {
//					count++;
//				}
//			}
//		}
//		assertEquals(45, count);
//	}
//	
//	/**
//	 * tests if a sudoku with difficulty easy is solveable
//	 */
//	@Test
//	void testSolve() {		
//	model.createSudoku();
//	model.setDifficulty(3);
//	model.difficulty();
//		assertTrue(model.solveSudoku());
//	}
//	
////	@Test
////	public void testPrintCells() {		
////	model.printCells();
////	}
//	
//	/**
//	 * tests setting Gamestate
//	 */
//	@Test
//	void testSetGamestate() {		
//		model.setGameState(Gamestate.DONE);
//		assertEquals(model.getGamestate(), Gamestate.DONE);
//	}	
//	
//	/**
//	 * tests getting Gamestate
//	 */
//	@Test
//	void testGetGamestate() {		
//		model.setGameState(Gamestate.DONE);
//		model.getGamestate();
//		assertEquals(model.getGamestate(),Gamestate.DONE);
//	}	
//	
//	/**
//	 * tests if true->'freeform sudoku solved'  is returned when the sudoku was solved by method
//	 */
//	@Test
//	void testIfSolvedTrue() {		
//		model.createSudoku();
//		model.setDifficulty(3);
//		model.difficulty();
//		model.solveSudoku();
//		assertTrue(model.testIfSolved());
//	}	
//	
//	/**
//	 * tests if false->'freeform sudoku not solved'  is returned when the sudoku was not solved by method
//	 */
//	@Test
//	void testIfSolvedFalse() {		
//		model.createSudoku();
//		model.setDifficulty(3);
//		model.difficulty();
//		assertFalse(model.testIfSolved());
//	}	
//	
//	/**
//	 * tests if the hint method displays a new number in the array
//	 */
//	@Test
//    void testHint() {
//        int [] sollutionArray = new int[] {0, 0};
//        model.createSudoku();
//        model.setDifficulty(3);
//        model.difficulty();
////        model.solveSudoku();
////        model.setCell(0, 5, 0);
//        model.getCells()[0][5].setValue(0);
//        int [] returnArray = model.hint();
//        System.out.println(returnArray[0] + " " + returnArray[1]);
//        assertNotEquals(null, returnArray);
//    }
//    
//	
//	/**
//	 * tests if proof-method returns false when no cell is colored
//	 */
//	@Test
//	void testProofFilledOutFalse() {		
//		model.setUpLogicArray();
//		assertFalse(model.proofFilledOut());
//	}	
//	
//	/**
//	 * tests if proof-method returns false when only a few cells are colored
//	 */
//	@Test
//	void testProofFilledOutFalse2() {		
//		model.setUpLogicArray();
//		model.getCells()[0][0].setBoxcolor("adb5be");
//		model.getCells()[0][1].setBoxcolor("adb5be");
//		model.getCells()[0][2].setBoxcolor("adb5be");
//		model.getCells()[1][0].setBoxcolor("eaeee0");
//		model.getCells()[1][1].setBoxcolor("eaeee0");
//		model.getCells()[1][2].setBoxcolor("eaeee0");		
//		assertFalse(model.proofFilledOut());
//	}	
//	
//	/**
//	 * tests if proof-method returns true when all cells are colored correctly with 9 cells each color
//	 */
//	@Test
//	void testProofFilledOutTrue() {		
//		model.setUpLogicArray();
//		model.getCells()[0][0].setBoxcolor("eaeee0");
//		model.getCells()[0][1].setBoxcolor("eaeee0");
//		model.getCells()[0][2].setBoxcolor("eaeee0");
//		model.getCells()[0][3].setBoxcolor("eaeee0");
//		model.getCells()[0][4].setBoxcolor("eaeee0");
//		model.getCells()[0][5].setBoxcolor("eaeee0");		
//		model.getCells()[0][6].setBoxcolor("eaeee0");
//		model.getCells()[0][7].setBoxcolor("eaeee0");
//		model.getCells()[0][8].setBoxcolor("eaeee0");		
//		
//		model.getCells()[1][0].setBoxcolor("cab08b");
//		model.getCells()[1][1].setBoxcolor("cab08b");
//		model.getCells()[1][2].setBoxcolor("cab08b");
//		model.getCells()[1][3].setBoxcolor("cab08b");
//		model.getCells()[1][4].setBoxcolor("cab08b");
//		model.getCells()[1][5].setBoxcolor("cab08b");		
//		model.getCells()[1][6].setBoxcolor("cab08b");
//		model.getCells()[1][7].setBoxcolor("cab08b");
//		model.getCells()[1][8].setBoxcolor("cab08b");	
//		
//		model.getCells()[2][0].setBoxcolor("97c1a9");
//		model.getCells()[2][1].setBoxcolor("97c1a9");
//		model.getCells()[2][2].setBoxcolor("97c1a9");
//		model.getCells()[2][3].setBoxcolor("97c1a9");
//		model.getCells()[2][4].setBoxcolor("97c1a9");
//		model.getCells()[2][5].setBoxcolor("97c1a9");		
//		model.getCells()[2][6].setBoxcolor("97c1a9");
//		model.getCells()[2][7].setBoxcolor("97c1a9");
//		model.getCells()[2][8].setBoxcolor("97c1a9");
//		
//		model.getCells()[3][0].setBoxcolor("dfd8ab");
//		model.getCells()[3][1].setBoxcolor("dfd8ab");
//		model.getCells()[3][2].setBoxcolor("dfd8ab");
//		model.getCells()[3][3].setBoxcolor("dfd8ab");
//		model.getCells()[3][4].setBoxcolor("dfd8ab");
//		model.getCells()[3][5].setBoxcolor("dfd8ab");		
//		model.getCells()[3][6].setBoxcolor("dfd8ab");
//		model.getCells()[3][7].setBoxcolor("dfd8ab");
//		model.getCells()[3][8].setBoxcolor("dfd8ab");
//		
//		model.getCells()[4][0].setBoxcolor("d5a1a3");
//		model.getCells()[4][1].setBoxcolor("d5a1a3");
//		model.getCells()[4][2].setBoxcolor("d5a1a3");
//		model.getCells()[4][3].setBoxcolor("d5a1a3");
//		model.getCells()[4][4].setBoxcolor("d5a1a3");
//		model.getCells()[4][5].setBoxcolor("d5a1a3");		
//		model.getCells()[4][6].setBoxcolor("d5a1a3");
//		model.getCells()[4][7].setBoxcolor("d5a1a3");
//		model.getCells()[4][8].setBoxcolor("d5a1a3");	
//		
//		model.getCells()[5][0].setBoxcolor("80adbc");
//		model.getCells()[5][1].setBoxcolor("80adbc");
//		model.getCells()[5][2].setBoxcolor("80adbc");
//		model.getCells()[5][3].setBoxcolor("80adbc");
//		model.getCells()[5][4].setBoxcolor("80adbc");
//		model.getCells()[5][5].setBoxcolor("80adbc");		
//		model.getCells()[5][6].setBoxcolor("80adbc");
//		model.getCells()[5][7].setBoxcolor("80adbc");
//		model.getCells()[5][8].setBoxcolor("80adbc");	
//		
//		model.getCells()[6][0].setBoxcolor("adb5be");
//		model.getCells()[6][1].setBoxcolor("adb5be");
//		model.getCells()[6][2].setBoxcolor("adb5be");
//		model.getCells()[6][3].setBoxcolor("adb5be");
//		model.getCells()[6][4].setBoxcolor("adb5be");
//		model.getCells()[6][5].setBoxcolor("adb5be");		
//		model.getCells()[6][6].setBoxcolor("adb5be");
//		model.getCells()[6][7].setBoxcolor("adb5be");
//		model.getCells()[6][8].setBoxcolor("adb5be");	
//		
//		model.getCells()[7][0].setBoxcolor("957DAD");
//		model.getCells()[7][1].setBoxcolor("957DAD");
//		model.getCells()[7][2].setBoxcolor("957DAD");
//		model.getCells()[7][3].setBoxcolor("957DAD");
//		model.getCells()[7][4].setBoxcolor("957DAD");
//		model.getCells()[7][5].setBoxcolor("957DAD");		
//		model.getCells()[7][6].setBoxcolor("957DAD");
//		model.getCells()[7][7].setBoxcolor("957DAD");
//		model.getCells()[7][8].setBoxcolor("957DAD");	
//		
//		model.getCells()[8][0].setBoxcolor("FFDFD3");
//		model.getCells()[8][1].setBoxcolor("FFDFD3");
//		model.getCells()[8][2].setBoxcolor("FFDFD3");
//		model.getCells()[8][3].setBoxcolor("FFDFD3");
//		model.getCells()[8][4].setBoxcolor("FFDFD3");
//		model.getCells()[8][5].setBoxcolor("FFDFD3");		
//		model.getCells()[8][6].setBoxcolor("FFDFD3");
//		model.getCells()[8][7].setBoxcolor("FFDFD3");
//		model.getCells()[8][8].setBoxcolor("FFDFD3");	
//		
//		assertTrue(model.proofFilledOut());
//	}	
//}
//
//
