package logic;

public class Main {

	public static void main(String[]args) {
		
		SudokuLogic test = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		SudokuLogic test2 = new SudokuLogic(Gamestate.OPEN, 0.0, false);
		
		test.setUpLogicArray();
//		test.createSudoku();
//		test.printCells();
		
//		x.setCell(int col, int row, int guess);
		test.setCell(0, 1, 1);
		test.setCell(0, 2, 2);
		test.setCell(0, 6, 5);
		test.setCell(0, 7, 7);
		
		test.setCell(1, 0, 6);
		test.setCell(1, 3, 5);
		test.setCell(1, 5, 1);
		test.setCell(1, 8, 4);
		
	//	test.setCell(2, 0, 4);
		test.setCell(2, 4, 2);
		test.setCell(2, 8, 8);
		
		test.setCell(3, 1, 2);
		test.setCell(3, 4, 1);
		test.setCell(3, 7, 5);
		
		test.setCell(4, 2, 4);
		test.setCell(4, 3, 9);
		test.setCell(4, 5, 7);
		test.setCell(4, 6, 8);
		
		test.setCell(5, 1, 7);
		test.setCell(5, 4, 8);
		test.setCell(5, 7, 1);
		
		test.setCell(6, 0, 7);
		test.setCell(6, 4, 9);
		test.setCell(6, 8, 5);
		
		test.setCell(7, 0, 5);
		test.setCell(7, 3, 4);
		test.setCell(7, 5, 8);
		test.setCell(7, 8, 6);
		
		test.setCell(8, 1, 3);
		test.setCell(8, 2, 8);
		test.setCell(8, 6, 9);
		test.setCell(8, 7, 4);
		
//		test.manualSolve();
//		
//		System.out.println("=====================");
//		test.printCells2();
		
		System.out.println("===================");
		test.printCells();
		System.out.println("===================");
		test.hint();
		System.out.println("===================");
		test.printCells();
		System.out.println("===================");
		test.solveSudoku();
		System.out.println("===================");
		test.printCells();
		System.out.println("===================");
		
		
		
//		test2.autofill();
		//test.autoSolve();
//		test.printCells();
	}
}
